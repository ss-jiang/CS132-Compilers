import cs132.util.ProblemException;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VBuiltIn.Op;
import cs132.vapor.ast.*;

import java.io.*;
import java.util.*;

public class VM2M extends VInstr.Visitor<Throwable> { 
  
  public int indent = 0;

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

    System.out.println("Program parsed successfully");

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
    for (VDataSegment data : v.dataSegments) {
      String type = (data.mutable) ? "var" : "const";
      String name = data.ident;
      System.out.println(".data\n");
      System.out.println(name);
      
      for (VOperand.Static method : data.values) {
        String methodName = method.toString();
        System.out.println("  " + methodName.substring(1));
      }
      System.out.println("");

      System.out.println(".text\n");
      System.out.println("  jal Main\n  li $v0 10\n  syscall\n");
    }
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
    "_str0: .asciiz \"null pointer\\n\"\n"
    );
  }

  public void print(String s, Object... a) {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < indent; i++) {
          output.append(" ");
    }
    System.out.println(String.format(output + s, a));
  }


  public void visit(VAssign a) {

  } 
            
  public void visit(VBranch b) {

  }
            
  public void visit(VBuiltIn c) {

  }
            
  public void visit(VCall c) {

  }
            
  public void visit(VGoto g) {

  }
            
  public void visit(VMemRead r) {

  }
            
  public void visit(VMemWrite w) {

  }
            
  public void visit(VReturn r) {
    
  }

}
