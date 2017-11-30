import cs132.util.ProblemException;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VBuiltIn.Op;
import cs132.vapor.ast.*;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Arrays;

public class V2VM extends VInstr.Visitor<Throwable> { 

    public int indent = 0;
    
    public static void main (String [] args) {

        VaporProgram v = null;
        try {
          v = parseVapor(System.in, System.err);
        }
        catch (Exception e) {
          System.out.println("Error parsing program");
          System.exit(1);
        }

        LA la = new LA();
        la.analyze(v);

        V2VM v2vm = new V2VM();
        v2vm.printDataSegments(v);

        for (VFunction function : v.functions) {

          String params = "";
          for (VVarRef ident : function.params) {
            params += ident.toString() + " ";
          }

          v2vm.print(String.format("func %s(%s)", function.ident, params.trim()));
          v2vm.indent++;

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
      for (int i = 0; i < indent * 2; i++) {
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
      // System.out.println("VAssign");
    
      print(a.dest.toString() + " = " + a.source.toString());
    }

    //
    // Call, function 
    // c.dest = c.addr( c.args )
    //
    public void visit(VCall c) throws Throwable {
      // System.out.println("VCall");

      String args = "";
      for (VOperand arg : c.args) {
        args += arg.toString() + " ";
      }

      print(String.format("%s = call %s(%s)", c.dest.toString(), c.addr.toString(), args.trim()));
      
    }

    //
    // Built-in operation (Add, Sub, MulS, etc.)
    // c.dest = c.op.name( c.args )
    // 
    public void visit(VBuiltIn c) throws Throwable {
      // System.out.println("VBuiltIn");

      String args = "";
      for (VOperand arg : c.args) {
        args += arg.toString() + " " ;
      }

      if (c.dest != null) {
        print(String.format("%s = %s(%s)", c.dest.toString(), c.op.name.toString(), args.trim()));
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
      // System.out.println("VMemWrite");

      VMemRef.Global dest = (VMemRef.Global) w.dest;

      if (dest.byteOffset == 0) {
        print(String.format("[%s] = %s", dest.base.toString(), w.source.toString()));      
      }
      else {
        print(String.format("[%s+%s] = %s", dest.base.toString(), dest.byteOffset, w.source.toString()));
      }
    }

    //
    // Memory read instructions. Ex: "v = [a+4]"
    //
    public void visit(VMemRead r) throws Throwable {
      // System.out.println("VMemRead");

      VMemRef.Global source = (VMemRef.Global) r.source;

      if (source.byteOffset == 0) {
        print(String.format("%s = [%s]", r.dest.toString(), source.base.toString()));
      }
      else {
        print(String.format("%s = [%s+%s]", r.dest.toString(), source.base.toString(), source.byteOffset));
      }

    }

    //
    // Branch instruction (if and if0)
    //
    public void visit(VBranch b) throws Throwable {
      // System.out.println("VBranch");

      indent--;
      if (b.positive) {
        print(String.format("if %s goto %s", b.value.toString(), b.target.toString()));
      }
      else {
        print(String.format("if0 %s goto %s", b.value.toString(), b.target.toString()));
      }
      indent++;
    }

    //
    // Jump instruction
    //
    public void visit(VGoto g) throws Throwable {
      // System.out.println("VGoto");

      print(String.format("goto %s", g.target.toString()));
    }

    //
    // Return instruction
    // ret *( r.value )*
    //
    public void visit(VReturn r) throws Throwable {
      // System.out.println("VReturn");

      if (r.value != null) {
        print("ret " + r.value.toString());
      }
      else {
        print("ret");
      }
    }


}
