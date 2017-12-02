import cs132.vapor.ast.*;

import java.util.*;

public class LA extends VInstr.Visitor<Throwable> { 
  
  private RegisterManager RM;

  public LA() {
    RM = new RegisterManager();
    RM.callees.addAll(Arrays.asList("$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7"));
    RM.callers.addAll(Arrays.asList("$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7", "$t8"));
  } 

  public void analyze(VFunction v) {

    for (VVarRef ident : v.params) {
      RM.liveList.add(new Lines(ident.toString(), ident.sourcePos.line));
    }

    LinkedList<VCodeLabel> l = new LinkedList<VCodeLabel>(Arrays.asList(v.labels));
    for (VInstr instr : v.body) {
      try {
        while (!l.isEmpty() && l.peek().sourcePos.line < instr.sourcePos.line) {
          String currentLabel = l.pop().ident;

          for (Lines line : RM.liveList) {
            line.tempLabels.add(currentLabel);
          }
        }
        instr.accept(this);
      }
      catch (Throwable t) {
      }
    }
  }

  /*
      Helper Functions
  */

  public Lines getVar(String var) {
    for (Lines l : RM.liveList) {
      if (l.getVar().equals(var)) {
        return l;
      }
    }
    return null;
  }

  public void PrintLiveIntervals() {
    for (Lines l : RM.liveList) {
      System.out.println(String.format("'%s' is live from lines %s, Labels: %s, Cross call = %s", l.getVar(), l.printRanges(), l.labels.toString(), l.getCrossCall().toString()));        
    }
  }

  public RegisterManager getRM() {
    return this.RM;
  }

  /* 
      Visitors

      function-local variable (VVarRef.Local) 
      global register (VVarRef.Register)

  */

  public void updateLines(Lines line, int lineNum, String mode) {
    if (line != null) {
      if (mode.equals("save")) {
        line.ranges.end = lineNum;
        line.labels.addAll(line.tempLabels);
        line.tempLabels.clear();
        if (line.getCall()) {
          line.setCrossCall(true);
        }
      }

      if (mode.equals("use")) {
        line.ranges.end = lineNum;
        line.tempLabels.clear();
      }  
    }  
  }

  public void visit(VAssign a) throws Throwable {
    updateLines(getVar(a.source.toString()), a.sourcePos.line, "save");

    Lines l = getVar(a.dest.toString());
    if (l == null) {
        RM.liveList.add(new Lines(a.dest.toString(), a.sourcePos.line));
    } else {
      updateLines(l, a.sourcePos.line, "use");
    }

  }

  public void visit(VCall c) throws Throwable {
    String destination = c.dest.ident;
    boolean dEqualsS = false;

    Lines l = getVar(c.addr.toString());
    if (l != null) {
      if (destination.equals(c.addr.toString()))
          dEqualsS = true;

      updateLines(l, c.sourcePos.line, "save");
    }

    for (VOperand operand : c.args) {
      if (destination.equals(operand.toString()))
          dEqualsS = true;

      updateLines(getVar(operand.toString()), c.sourcePos.line, "save");
    }

    for (Lines li : RM.liveList)
        li.setCall(true);

    if (!dEqualsS) {
      l = getVar(destination);
      if (l == null) {
          RM.liveList.add(new Lines(destination, c.sourcePos.line));
      } else {
          updateLines(l, c.sourcePos.line, "save");
      }
    }

    if (c.args.length - 4 > RM.outs) {
      RM.outs = c.args.length - 4;
    }

  }

  public void visit(VBuiltIn c) throws Throwable {
    String destination = "";
    boolean dEqualsS = true;;

    if (c.dest != null) {
        destination = c.dest.toString();
        dEqualsS = false;
    }

    for (VOperand operand : c.args) {
      if (operand.toString().equals(destination))
          dEqualsS = true;

      updateLines(getVar(operand.toString()), c.sourcePos.line, "save");
    }

    if (!dEqualsS) {
        Lines l = getVar(destination);
        if (l == null) {
            RM.liveList.add(new Lines(destination, c.sourcePos.line));
        } else {
            updateLines(l, c.sourcePos.line, "use");
        }
    }
  }

  public void visit(VMemWrite w) throws Throwable {    
    updateLines(getVar(w.source.toString()), w.sourcePos.line, "save");

    VMemRef.Global dest = (VMemRef.Global) w.dest;
    updateLines(getVar(dest.base.toString()), w.sourcePos.line, "save");              
  }

  public void visit(VMemRead r) throws Throwable {
    VMemRef.Global src = (VMemRef.Global) r.source;
    updateLines(getVar(src.base.toString()), r.sourcePos.line, "save");

    Lines l = getVar(r.dest.toString());
    if (l == null) {
        RM.liveList.add(new Lines(r.dest.toString(), r.sourcePos.line));
    } else {
        updateLines(l, r.sourcePos.line, "use");
    }
  }

  public void visit(VBranch b) throws Throwable {
    for (Lines l : RM.liveList) {
      if (l.labels.contains(b.target.toString().substring(1))) {
          l.ranges.end = b.sourcePos.line;
          if (l.getCall())
              l.setCrossCall(true);
      }
    }

    updateLines(getVar(b.value.toString()), b.sourcePos.line, "save");
  }

  public void visit(VGoto g) throws Throwable {
    for (Lines l : RM.liveList) {
      if (l.labels.contains(g.target.toString().substring(1))) {
          l.ranges.end = g.sourcePos.line;
          if (l.getCall())
              l.setCrossCall(true);
      }
    }
  }

  public void visit(VReturn r) throws Throwable {
    if (r.value != null) {
      updateLines(getVar(r.value.toString()), r.sourcePos.line, "save");
    }
  }


}
