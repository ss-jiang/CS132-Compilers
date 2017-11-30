import cs132.vapor.ast.*;

import java.util.*;

import javax.net.ssl.ExtendedSSLSession;

public class LA extends VInstr.Visitor<Throwable> { 
    
  public String functionName;
  public LinkedList<String> calleeRegisters = new LinkedList<String>();
  public LinkedList<String> callerRegisters = new LinkedList<String>();

  // Holds (function name, variable) -> (line start, line end)
  public HashMap<String, LinkedHashMap<String, Lines>> liveList = new HashMap<String, LinkedHashMap<String, Lines>>();

  public LA() {
    calleeRegisters.addAll(Arrays.asList("s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7"));
    callerRegisters.addAll(Arrays.asList("t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8"));
  } 

  public void analyze(VaporProgram v) {

    for (VFunction function : v.functions) {
      System.out.println("Liveness Analysis of function: " + function.ident);
      
      if (!this.liveList.containsKey(function.ident)) {
        this.liveList.put(function.ident, new LinkedHashMap<String, Lines>());
      }
      this.functionName = function.ident;

      for (VVarRef ident : function.params) {
        updateLines(function.sourcePos.line, ident.toString());
      }

      for (VInstr instr : function.body) {
        try {
          instr.accept(this);
        }
        catch (Throwable t) {
          System.out.println("ERRRR");
        }
      }
      System.out.println("\n");

      this.PrintLiveIntervals();
      System.out.println("\n");
    }
  }

  public void updateLines(int lineNum, String... idents) {
    for (String i : idents) {
      if (i.contains("null pointer") || i.contains(":vmt_") || i.matches("[0-9]+")) {
        continue;
      }
      else {
        if (liveList.get(functionName).containsKey(i)) {
          liveList.get(functionName).get(i).setEnd(lineNum);
        }
        else {
          System.out.println(String.format("Adding %s with starting line %d", i, lineNum));
          Lines temp = new Lines(lineNum, lineNum);
          liveList.get(functionName).put(i, temp);
        }
      }
    }
  }

  public void updateLinesOfArgs(int lineNum, String argString) {
    for (String i : argString.split(" ")) {
      if (i.contains("null") ||  i.contains("pointer") || i.contains(":vmt_") || i.matches("[0-9]+")) {
        continue;
      }
      else {
        if (liveList.get(functionName).containsKey(i)) {
          liveList.get(functionName).get(i).setEnd(lineNum);
        }
        else {
          Lines temp = new Lines(lineNum, lineNum);
          liveList.get(functionName).put(i, temp);
        }
      }
    }
  }

  public void PrintLiveIntervals() {
    for (String key : liveList.get(functionName).keySet()) {
      System.out.println(String.format("'%s' is live from lines %d - %d", key, liveList.get(functionName).get(key).getStart(), liveList.get(functionName).get(key).getEnd()));
    }
  }

  private class SortStart implements Comparator<Lines> {
    public int compare(Lines l1, Lines l2) {
      if (l1.getStart() < l2.getStart()) {
        return -1;
      }
      else if (l1.getStart() > l2.getStart()) {
        return 1;
      }
      else {
        return 0;
      }
    }
  }

  private class SortEnd implements Comparator<Lines> {
    public int compare(Lines l1, Lines l2) {
      if (l1.getEnd() < l2.getEnd()) {
        return -1;
      }
      else if (l1.getEnd() > l2.getEnd()) {
        return 1;
      }
      else {
        return 0;
      }
    }
  }

  /*
      Register managers
  */






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
    System.out.print("VAssign   - ");
    System.out.println(String.format("Line %d: %s %s", a.dest.sourcePos.line, a.dest.toString(), a.source.toString()));

    updateLines(a.sourcePos.line, a.dest.toString());
  }

  //
  // Call, function 
  // c.dest = c.addr( c.args )
  //
  public void visit(VCall c) throws Throwable {
    System.out.print("VCall     - ");

    String args = "";
    for (VOperand arg : c.args) {
      args += arg.toString() + " ";
    }

    System.out.println(String.format("Line %d: %s %s %s", c.sourcePos.line, c.dest.toString(), c.addr.toString(), args.trim()));
    
    updateLines(c.sourcePos.line, c.dest.toString(), c.addr.toString());
    updateLinesOfArgs(c.sourcePos.line, args.trim());
  }

  //
  // Built-in operation (Add, Sub, MulS, etc.)
  // c.dest = c.op.name( c.args )
  // 
  public void visit(VBuiltIn c) throws Throwable {
    System.out.print("VBuiltIn  - ");
        
    String args = "";
    for (VOperand arg : c.args) {
      args += arg.toString() + " " ;
    }
    
    if (c.dest != null) {
      System.out.println(String.format("Line %d: %s %s", c.sourcePos.line, c.dest.toString(), args.trim()));

      updateLines(c.sourcePos.line, c.dest.toString());
      updateLinesOfArgs(c.sourcePos.line, args.trim());
    }
    else {
      System.out.println(String.format("Line %d: %s", c.sourcePos.line, args.trim()));

      updateLinesOfArgs(c.sourcePos.line, args.trim());
    }
  }

  //
  // Memory write instruction. Ex: "[a+4] = v".
  // w.dest = w.source
  //
  public void visit(VMemWrite w) throws Throwable {
    System.out.print("VMemWrite - ");

    VMemRef.Global dest = (VMemRef.Global) w.dest;
    System.out.println(String.format("Line %d: %s %s", w.sourcePos.line, dest.base.toString(), w.source.toString()));

    updateLines(w.sourcePos.line, dest.base.toString(), w.source.toString());
  }

  //
  // Memory read instructions. Ex: "v = [a+4]"
  //
  public void visit(VMemRead r) throws Throwable {
    System.out.print("VMemRead  - ");

    VMemRef.Global source = (VMemRef.Global) r.source;
    System.out.println(String.format("Line %d: %s %s", r.sourcePos.line, source.base.toString(), r.dest.toString()));

    updateLines(r.sourcePos.line, source.base.toString(), r.dest.toString());
  }

  //
  // Branch instruction (if and if0)
  //
  public void visit(VBranch b) throws Throwable {
    System.out.print("VBranch   - ");
    System.out.println(String.format("Line %d: %s", b.sourcePos.line, b.value.toString()));

    updateLines(b.sourcePos.line, b.value.toString());
  }

  //
  // Jump instruction
  //
  public void visit(VGoto g) throws Throwable {
    System.out.print("VGoto     - ");
    System.out.println(String.format("Line %d: Goto", g.sourcePos.line));
  }

  //
  // Return instruction
  // ret *( r.value )*
  //
  public void visit(VReturn r) throws Throwable {
    System.out.print("VReturn   - ");

    if (r.value != null) {
      System.out.println(String.format("Line %d: %s", r.sourcePos.line, r.value.toString()));
      updateLines(r.sourcePos.line, r.value.toString());
    }
  }


}
