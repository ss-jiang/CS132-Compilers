import cs132.util.ProblemException;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VBuiltIn.Op;
import cs132.vapor.ast.*;

import java.io.*;
import java.util.*;

public class VM2M extends VInstr.Visitor<Throwable> { 
  
  public int indent = 0;
  public int stackIndex;

  public static void main(String [] args) {
    VaporProgram v = null;
    try {
      v = parseVapor(System.in, System.err);      
    }
    catch (Throwable t) {
      System.out.println("Error parsing vaporm program");
      System.exit(1);
    }

    VM2M vm2m = new VM2M();
    vm2m.printDataSegments(v);

    for (VFunction function : v.functions) {
      vm2m.print(String.format("%s:", function.ident));
      vm2m.indent++;

      vm2m.print("sw $fp -8($sp)");
      vm2m.print("move $fp $sp");
      vm2m.stackIndex = 2 + function.stack.local + function.stack.out;
      vm2m.print(String.format("subu $sp $sp %d", vm2m.stackIndex * 4));
      vm2m.print("sw $ra -4($fp)");

      LinkedList<VCodeLabel> l = new LinkedList<VCodeLabel>(Arrays.asList(function.labels));
      for (VInstr instr : function.body) {
        try {
          vm2m.indent--;
          while (!l.isEmpty() && l.peek().sourcePos.line < instr.sourcePos.line) {
            vm2m.print(String.format("%s:", l.pop().ident));
          }
  
          vm2m.indent++;
          instr.accept(vm2m);
        }
        catch (Throwable t) {
          System.out.println("ERRRR");
        }
      }
      vm2m.indent--;
      System.out.println();
    }

    vm2m.printEnding();
  } 

  public static VaporProgram parseVapor(InputStream in, PrintStream err)
  throws IOException
  {
    Op[] ops = {
      Op.Add, Op.Sub, Op.MulS, Op.Eq, Op.Lt, Op.LtS,
      Op.PrintIntS, Op.HeapAllocZ, Op.Error,
    };
    boolean allowLocals = false;
    String[] registers = {
      "v0", "v1",
      "a0", "a1", "a2", "a3",
      "t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7",
      "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7",
      "t8",
    };
    boolean allowStack = true;

    VaporProgram program;
    try {
      program = VaporParser.run(new InputStreamReader(in), 1, 1,
                                java.util.Arrays.asList(ops),
                                allowLocals, registers, allowStack);
    }
    catch (ProblemException ex) {
      err.println(ex.getMessage());
      return null;
    }

    return program;
  }

  // Prints the VMT of the functions
  public void printDataSegments(VaporProgram v) {
    System.out.println(".data\n");
    
    for (VDataSegment data : v.dataSegments) {
      String type = (data.mutable) ? "var" : "const";
      String name = data.ident;
      System.out.println(name + ":");
      
      for (VOperand.Static method : data.values) {
        String methodName = method.toString();
        System.out.println("  " + methodName.substring(1));
      }
      System.out.println("");
    }
    System.out.println(".text\n");
    System.out.println("  jal Main\n  li $v0 10\n  syscall\n");
  }

  public void printEnding() {
    System.out.println( 
    "_print:\n" + 
    "  li $v0 1   # syscall: print integer\n" +
    "  syscall\n" +
    "  la $a0 _newline\n" +
    "  li $v0 4   # syscall: print string\n" +
    "  syscall\n" +
    "  jr $ra\n\n" + 
    
    "_error:\n" +
    "  li $v0 4   # syscall: print string\n" +
    "  syscall\n" +
    "  li $v0 10  # syscall: exit\n" +
    "  syscall\n\n" +
    
    "_heapAlloc:\n" +
    "  li $v0 9   # syscall: sbrk\n" +
    "  syscall\n" +
    "  jr $ra\n\n" +
    
    ".data\n" +
    ".align 0\n" +
    "_newline: .asciiz \"\\n\"\n" +
    "_str0: .asciiz \"null pointer\\n\"\n" +
    "_str1: .asciiz \"array index out of bounds\\n\""
    );
  }

  public void print(String s, Object... a) {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < indent * 2; i++) {
          output.append(" ");
    }
    System.out.println(String.format(output + s, a));
  }

  public boolean argCheckInt(VOperand arg) {
    if (arg instanceof VLitInt) {
      return true;
    }
    return false;
  }

  public void visit(VAssign a) {
    if (a.source instanceof VLitInt) {
      print(String.format("li %s %s", a.dest.toString(), a.source.toString()));
    }
    else if (a.source.toString().startsWith(":")) {
      print(String.format("la %s %s", a.dest.toString(), a.source.toString().substring(1)));      
    }
    else {
      print(String.format("move %s %s", a.dest.toString(), a.source.toString()));
    }
  } 
            
  public void visit(VBranch b) {
    if (b.positive) {
      print(String.format("bnez %s %s", b.value.toString(), b.target.toString().substring(1)));
    }
    else {
      print(String.format("beqz %s %s", b.value.toString(), b.target.toString().substring(1)));
    }
  }
            
  public void visit(VBuiltIn c) {
    String args = "";
    for (VOperand arg : c.args) {
      args += arg.toString() + " ";
    }

    switch(c.op.name) {
      case "HeapAllocZ": 
        if (argCheckInt(c.args[0])) {
          print(String.format("li $a0 %s", args.trim()));          
        }
        else {
          print(String.format("move $a0 %s", args.trim()));
        }
        print(String.format("jal _heapAlloc"));
        print(String.format("move %s $v0", c.dest.toString()));
        break;
      case "Error":
        print(String.format("la $a0 %s", c.args[0].toString().contains("null pointer") ? "_str0" : "_str1"));      
        print(String.format("j _error"));
        break;
      case "PrintIntS":
        if (argCheckInt(c.args[0])) {
          print(String.format("li $a0 %s", c.args[0].toString()));
          print(String.format("jal _print"));
        }
        else {
          print(String.format("move $a0 %s", c.args[0].toString()));
          print(String.format("jal _print"));
        }
        break;
      case "LtS":
        if (argCheckInt(c.args[1])) {
          print(String.format("slti %s %s", c.dest.toString(), args.trim()));          
        }
        else {
          print(String.format("slt %s %s", c.dest.toString(), args.trim()));          
        }
        break;
      case "Lt":
        if (argCheckInt(c.args[0])) {
          print(String.format("li $t9 %s", c.args[0].toString()));
          print(String.format("sltu %s $t9 %s", c.dest.toString(), c.args[1].toString()));
        }
        else {
          print(String.format("sltu %s %s", c.dest.toString(), args.trim()));
        }
        break;
      case "Sub": 
        if (argCheckInt(c.args[0]) && argCheckInt(c.args[1])) {
          print(String.format("li %s %s", c.dest.toString(), Integer.parseInt(c.args[0].toString()) - Integer.parseInt(c.args[1].toString())));
          break;      
        }
        else if (argCheckInt(c.args[0])) {
          print(String.format("li $t9 %s", c.args[0].toString()));
          print(String.format("subu %s $t9 %s", c.dest.toString(), c.args[1].toString()));          
        }
        else {
          print(String.format("subu %s %s", c.dest.toString(), args.trim()));          
        }
        break;
      case "MulS":
        if (argCheckInt(c.args[0]) && argCheckInt(c.args[1])) {
          print(String.format("li %s %s", c.dest.toString(), Integer.parseInt(c.args[0].toString()) * Integer.parseInt(c.args[1].toString())));
          break;      
        }
        else if (argCheckInt(c.args[0])) {
          print(String.format("mul %s %s %s", c.dest.toString(), c.args[1].toString(), c.args[0].toString()));
        }
        else {
          print(String.format("mul %s %s", c.dest.toString(), args.trim()));          
        }
        break;
      case "Add":
        if (argCheckInt(c.args[0]) && argCheckInt(c.args[1])) {
          print(String.format("li %s %s", c.dest.toString(), Integer.parseInt(c.args[0].toString()) + Integer.parseInt(c.args[1].toString())));
          break;      
        }
        else if (argCheckInt(c.args[0])) {
          print(String.format("li $t9 %s", c.args[0].toString()));
          print(String.format("addu %s $t9 %s", c.dest.toString(), c.args[1].toString()));          
        }
        else {
          print(String.format("addu %s %s", c.dest.toString(), args.trim()));          
        }
        break;
      default: 
        break;
    }

  }
            
  public void visit(VCall c) {
    if (c.addr.toString().startsWith("$")) {
      print(String.format("jalr %s", c.addr.toString()));
    }
    else {
      print(String.format("jal %s", c.addr.toString().substring(1)));      
    }
  }
            
  public void visit(VGoto g) {
    print(String.format("j %s", g.target.toString().substring(1)));
  }
            
  public void visit(VMemRead r) {
    if (r.source instanceof VMemRef.Stack) {
      VMemRef.Stack source = (VMemRef.Stack) r.source;
      
      if (source.region.toString().equals("Local")) {
        print(String.format("lw %s %d($sp)", r.dest.toString(), source.index * 4));  
      }
      else if (source.region.toString().equals("In")) {
        print(String.format("lw %s %d($fp)", r.dest.toString(), source.index * 4));
      }
    }
    else if (r.source instanceof VMemRef.Global) {
      VMemRef.Global source = (VMemRef.Global) r.source;
      
      print(String.format("lw %s %d(%s)", r.dest.toString(), source.byteOffset, source.base.toString()));      
    }
  }
            
  public void visit(VMemWrite w) {
    if (w.dest instanceof VMemRef.Stack) {
      VMemRef.Stack dest = (VMemRef.Stack) w.dest;

      if (w.source instanceof VVarRef.Register) {
        print(String.format("sw %s %d($sp)", w.source.toString(), dest.index * 4));
      }
      else  {
        print(String.format("li $t9 %s", w.source.toString()));
        print(String.format("sw $t9 %d($sp)", dest.index * 4));
      }
    }
    else {
      VMemRef.Global dest = (VMemRef.Global) w.dest;

      if (w.source instanceof VLabelRef) {
        print(String.format("la $t9 %s", ((VLabelRef) w.source).ident.toString()));
        print(String.format("sw $t9 %d(%s)", dest.byteOffset, dest.base));
      }
      else {
        if (w.source instanceof VLitInt) {
          print(String.format("li $t9 %s", w.source.toString()));
          print(String.format("sw $t9 %d(%s)", dest.byteOffset, dest.base));          
        }
        else {
          print(String.format("sw %s %d(%s)", w.source.toString(), dest.byteOffset, dest.base));
        }
      }
    }
  }
            
  public void visit(VReturn r) {
    print("lw $ra -4($fp)");
    print("lw $fp -8($fp)");
    print(String.format("addu $sp $sp %d", stackIndex * 4));
    print("jr $ra");
  }

}
