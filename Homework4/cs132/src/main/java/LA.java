import cs132.vapor.ast.*;

import java.util.*;

import javax.net.ssl.ExtendedSSLSession;

public class LA extends VInstr.Visitor<Throwable> { 
    
  public String functionName;
  public String currentLabel;
  public int labelLine;

  public LinkedList<String> calleeRegisters = new LinkedList<String>();
  public LinkedList<String> callerRegisters = new LinkedList<String>();

  // Holds function -> label -> (var, line start, line end)
  public HashMap<String, LinkedList<Lines>> liveList = new HashMap<String, LinkedList<Lines>>();

  // Holds register -> (var, line start, line end)
  public LinkedHashMap<String, Lines> usedRegisters = new LinkedHashMap<String, Lines>();

  public TreeSet<String> freeRegisters = new TreeSet<String>();

  public LinkedList<String> waitingVars = new LinkedList<String>();

  // Holds label -> list of variables
  public HashMap<String, LinkedList<String>> liveAtIf = new HashMap<String, LinkedList<String>>(); 

  /*
      Functions
  */

  public LA() {
    currentLabel = null;
    calleeRegisters.addAll(Arrays.asList("s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7"));
    callerRegisters.addAll(Arrays.asList("t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8"));
  } 

  public void analyze(VaporProgram v) {

    for (VFunction function : v.functions) {
      System.out.println("Liveness Analysis of function: " + function.ident);
      
      if (!this.liveList.containsKey(function.ident)) {
        this.liveList.put(function.ident, new LinkedList<Lines>());
      }
      this.functionName = function.ident;
      this.currentLabel = function.ident;
      this.labelLine = function.sourcePos.line;

      // this.liveAtIf.put(this.currentLabel, new LinkedList<String>());

      for (VVarRef ident : function.params) {
        queueLines(ident.toString());

        // this.liveAtIf.get(this.currentLabel).add(ident.toString());
      }


      LinkedList<VCodeLabel> l = new LinkedList<VCodeLabel>(Arrays.asList(function.labels));
      for (VInstr instr : function.body) {
        try {
          while (!l.isEmpty() && l.peek().sourcePos.line < instr.sourcePos.line) {
            labelLine = l.peek().sourcePos.line;
            currentLabel = l.pop().ident;
          }

          instr.accept(this);
        }
        catch (Throwable t) {
          System.out.println("ERRRR");
        }
      }
      System.out.println("\n");

      CrossCallChecker ccc = new CrossCallChecker();
      this.liveList = ccc.analyze(v, this.liveList);

      this.PrintLiveIntervals();
      System.out.println("\n");
    }
  }

  public void extendLines(String fName, String lName, int lNum) {
    if (liveList.containsKey(fName)) {
      for (Lines l : liveList.get(fName)) {
        if (l.labels.contains(lName)) {
          l.setEnd(lNum);
        }
      }
    }
  }

  public void queueLines(String... idents) {
    for (String i : idents) {
      waitingVars.add(i);
    }
  }

  public void updateLines(int lineNum, String... idents) {
    for (String i : idents) {
      waitingVars.add(i);
    }

    for (String i : waitingVars) {
      Boolean added = false;      
      if (i.contains("null") || i.contains("pointer") || i.contains(":vmt_") || i.matches("[0-9]+")) {
        continue;
      }
      else {
        if (liveList.containsKey(functionName)) {
          for (Lines l : liveList.get(functionName)) {
            if (l.getVar().equals(i)) {
              if (!l.labels.contains(currentLabel)) {
                l.addRange(lineNum);
                l.labels.add(currentLabel);

                if (liveAtIf.containsKey(currentLabel)) {
                  for (String s : liveAtIf.get(currentLabel)) {
                    if (l.getVar().equals(s)) {
                      l.setStart(labelLine + 1);
                      liveAtIf.get(currentLabel).remove(s);
                    }
                  }
                }
                
              }
              else {
                l.setEnd(lineNum);                
              }
              added = true;

              // if (l.getCall()) {
              //   l.setCrossCall(true);
              // }
            }
          }
          if (!added) {
            System.out.println(String.format("Adding %s with starting line %d", i, lineNum)); 
            Lines temp = new Lines(i, lineNum);
            
            if (liveAtIf.containsKey(currentLabel)) {
              for (String s : liveAtIf.get(currentLabel)) {
                if (i.equals(s)) {
                  temp.setStart(labelLine + 1);
                  liveAtIf.get(currentLabel).remove(s);                  
                }
              }
            }

            temp.labels.add(currentLabel);
            liveList.get(functionName).add(temp);
          }
        }
      }
    }

    waitingVars.clear();
  }

  public void PrintLiveIntervals() {
    for (Lines l : liveList.get(functionName)) {
      System.out.println(String.format("'%s' is live from lines %s, Labels: %s, Cross call = %s", l.getVar(), l.printRanges(), l.labels.toString(), l.getCrossCall().toString()));        
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

  public void allocateRegisters() {
    
  }

  public void deallocateRegisters(Lines l) {
    for (String r : usedRegisters.keySet()) {
      if (usedRegisters.get(r).getEnd() < l.getStart()) {
        usedRegisters.remove(r);
        freeRegisters.add(r);
      }
    }
  }


  private class Var {

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
    System.out.print("VAssign   - ");
    System.out.println(String.format("Line %d: %s %s", a.dest.sourcePos.line, a.dest.toString(), a.source.toString()));

    queueLines(a.dest.toString());
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
    
    updateLines(c.sourcePos.line, c.addr.toString());
    for (String i : args.trim().split(" ")) {
      updateLines(c.sourcePos.line, i);
    }

    queueLines(c.dest.toString());

    // for (Lines l : liveList.get(functionName)) {
    //   if (l.labels.contains(currentLabel) && l.getStart() < c.sourcePos.line) {
    //     l.setCall(true);        
    //   }
    // }
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

      for (String i : args.trim().split(" ")) {
        updateLines(c.sourcePos.line, i);
      }
      queueLines(c.dest.toString());
    }
    else {
      System.out.println(String.format("Line %d: %s", c.sourcePos.line, args.trim()));

      for (String i : args.trim().split(" ")) {
        updateLines(c.sourcePos.line, i);
      }
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

    updateLines(w.sourcePos.line, w.source.toString());
    queueLines(dest.base.toString());
  }

  //
  // Memory read instructions. Ex: "v = [a+4]"
  //
  public void visit(VMemRead r) throws Throwable {
    System.out.print("VMemRead  - ");

    VMemRef.Global source = (VMemRef.Global) r.source;
    System.out.println(String.format("Line %d: %s %s", r.sourcePos.line, source.base.toString(), r.dest.toString()));

    updateLines(r.sourcePos.line, source.base.toString());
    queueLines(r.dest.toString());
  }

  //
  // Branch instruction (if and if0)
  //
  public void visit(VBranch b) throws Throwable {
    System.out.print("VBranch   - ");
    System.out.println(String.format("Line %d: %s", b.sourcePos.line, b.value.toString()));

    updateLines(b.sourcePos.line, b.value.toString());
    extendLines(functionName, currentLabel, b.sourcePos.line);

    String label = b.target.toString().substring(1);

    for (Lines l : liveList.get(functionName)) {
      if (l.labels.contains(currentLabel) && l.alive(b.sourcePos.line)) {
        if (!liveAtIf.containsKey(label)) {
          liveAtIf.put(label, new LinkedList<String>());
        }
        liveAtIf.get(label).add(l.getVar());
      }
    }
  }

  //
  // Jump instruction
  //
  public void visit(VGoto g) throws Throwable {
    System.out.print("VGoto     - ");
    System.out.println(String.format("Line %d: Goto", g.sourcePos.line));

    updateLines(g.sourcePos.line);
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
