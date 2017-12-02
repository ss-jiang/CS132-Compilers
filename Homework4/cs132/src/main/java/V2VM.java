import cs132.util.ProblemException;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VBuiltIn.Op;
import cs132.vapor.ast.*;

import java.io.*;
import java.util.*;

public class V2VM extends VInstr.Visitor<Throwable> { 

    public int indent = 0;
    public int calleeRegisterCount;
    public HashMap<String, String> registers;
    
    public static void main (String [] args) {

        VaporProgram v = null;
        try {
          v = parseVapor(System.in, System.err);
        }
        catch (Exception e) {
          System.out.println("Error parsing program");
          System.exit(1);
        }

        V2VM v2vm = new V2VM();
        v2vm.printDataSegments(v);

        for (VFunction function : v.functions) {

          LA la = new LA();
          la.analyze(function);                                                

          v2vm.registers = la.getRM().getRegisters();          
          v2vm.calleeRegisterCount = la.getRM().calleeRegisterCount; 

          int in = function.params.length;
          v2vm.print(String.format("func %s [in %d, out %d, local %d]", function.ident, (in < 4) ? 0 : in - 4, la.getRM().outs, v2vm.calleeRegisterCount));
          v2vm.indent++;

          for (int i = 0; i < v2vm.calleeRegisterCount && i < 8; i++) {
            v2vm.print(String.format("local[%d] = $s%d", i, i));
          }
          
          for (int i = 0; i < function.params.length; i++) {
            String param = v2vm.registers.get(function.params[i].ident);

            if (i < 4) {
              v2vm.print(String.format("%s = $a%d", param, i));
            }
            else {
              v2vm.print(String.format("%s = in[%d]", param, i - 4));
            }
          }

          LinkedList<VCodeLabel> l = new LinkedList<VCodeLabel>(Arrays.asList(function.labels));
          for (VInstr instr : function.body) {
            try {
              v2vm.indent--;
              while (!l.isEmpty() && l.peek().sourcePos.line < instr.sourcePos.line) {
                v2vm.print(String.format("%s:", l.pop().ident));
              }

              v2vm.indent++;
              instr.accept(v2vm);
            }
            catch (Throwable t) {
              System.out.println("ERRRR");
            }
          }
          System.out.println();
          v2vm.indent--;
        }
    }

    public static VaporProgram parseVapor(InputStream in, PrintStream err)
      throws IOException
    {
      Op[] ops = {
        Op.Add, Op.Sub, Op.MulS, Op.Eq, Op.Lt, Op.LtS,
        Op.PrintIntS, Op.HeapAllocZ, Op.Error,
      };
      boolean allowLocals = true;
      String[] registers = null;
      boolean allowStack = false;
    
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
        System.out.println(type + " " + name);
        
        for (VOperand.Static method : data.values) {
          String methodName = method.toString();
          System.out.println("  " + methodName);
        }
        System.out.println("");
      }
    }

    public void print(String s, Object... a) {
      StringBuilder output = new StringBuilder();
      for (int i = 0; i < indent; i++) {
            output.append(" ");
      }
      System.out.println(String.format(output + s, a));
    }

    /* 
        Visitors

        function-local variable (VVarRef.Local) 
        global register (VVarRef.Register)

    */

    //
    // Assignment instruction. This is only used for assignments of simple operands to registers and local variables.
    // a.dest = a.source
    //
    public void visit(VAssign a) throws Throwable {
      String s = registers.get(a.source.toString());
      if (s == null) {
        s = a.source.toString();
      }
      print(String.format("%s = %s", registers.get(a.dest.toString()), s));
    }

    //
    // Call, function 
    // c.dest = c.addr( c.args )
    //
    public void visit(VCall c) throws Throwable {
      for (int i = 0; i < c.args.length; i++) {
        String s = registers.get(c.args[i].toString());
        if ( s == null) {
          s = c.args[i].toString();
        }

        if (i < 4) {
          print(String.format("$a%d = %s", i, s));
        }
        else {
          print(String.format("out[%d] = %s", i - 4, s));
        }
      }

      String s = registers.get(c.addr.toString());
      if (s == null) {
        s = c.addr.toString();
      }

      print(String.format("call %s", s));
      if (registers.get(c.dest.toString()) != null) {
        print(String.format("%s = $v0", registers.get(c.dest.toString())));
      }

    }

    //
    // Built-in operation (Add, Sub, MulS, etc.)
    // c.dest = c.op.name( c.args )
    // 
    public void visit(VBuiltIn c) throws Throwable {
      String args = "";
      for (VOperand arg : c.args) {
        String s = registers.get(arg.toString());
        args += (s == null ? arg.toString() : s) + " ";
      }

      if (c.dest != null) {
        print(String.format("%s = %s(%s)", registers.get(c.dest.toString()), c.op.name.toString(), args.trim()));
      }
      else {
        print(String.format("%s(%s)", c.op.name.toString(), args.trim()));    
      }
    }

    //
    // Memory write instruction. Ex: "[a+4] = v".
    // w.dest = w.source
    //
    public void visit(VMemWrite w) throws Throwable {
      String s = registers.get(w.source.toString());
      if (s == null) {
        s = w.source.toString();
      }

      VMemRef.Global dest = (VMemRef.Global) w.dest;

      if (dest.byteOffset == 0) {
        print(String.format("[%s] = %s", registers.get(dest.base.toString()), s));      
      }
      else {
        print(String.format("[%s+%s] = %s", registers.get(dest.base.toString()), dest.byteOffset, s));
      }
    }

    //
    // Memory read instructions. Ex: "v = [a+4]"
    //
    public void visit(VMemRead r) throws Throwable {
      VMemRef.Global source = (VMemRef.Global) r.source;

      if (source.byteOffset == 0) {
        print(String.format("%s = [%s]", registers.get(r.dest.toString()), registers.get(source.base.toString())));
      }
      else {
        print(String.format("%s = [%s+%s]", registers.get(r.dest.toString()), registers.get(source.base.toString()), source.byteOffset));
      }

    }

    //
    // Branch instruction (if and if0)
    //
    public void visit(VBranch b) throws Throwable {
      if (b.positive) {
        print(String.format("if %s goto %s", registers.get(b.value.toString()), b.target.toString()));
      }
      else {
        print(String.format("if0 %s goto %s", registers.get(b.value.toString()), b.target.toString()));
      }
    }

    //
    // Jump instruction
    //
    public void visit(VGoto g) throws Throwable {
      print(String.format("goto %s", g.target.toString()));
    }

    //
    // Return instruction
    // ret *( r.value )*
    //
    public void visit(VReturn r) throws Throwable {
      if (r.value != null) {
        String s = registers.get(r.value.toString());
        if (s == null) {
          s = r.value.toString();
        }

        print(String.format("$v0 = %s", s));
      }

      for (int i = 0; i < calleeRegisterCount && i < 8; i++) {
        print(String.format("$s%d = local[%d]", i, i));
      }
      print("ret");
    }

}
