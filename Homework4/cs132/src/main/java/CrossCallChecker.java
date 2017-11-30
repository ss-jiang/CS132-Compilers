import cs132.vapor.ast.*;
import java.util.*;

public class CrossCallChecker extends VInstr.Visitor<Throwable> { 

  public String functionName;
  public String currentLabel;
  public HashMap<String, LinkedList<Lines>> list = new HashMap<String, LinkedList<Lines>>();

  public HashMap<String, LinkedList<Lines>> analyze(VaporProgram v, HashMap<String, LinkedList<Lines>> liveList) {

    list = liveList;

    for (VFunction function : v.functions) {
      System.out.println("CrossCallChecker: " + function.ident);

      this.functionName = function.ident;
      this.currentLabel = function.ident;

      for (VInstr instr : function.body) {
        try {
          instr.accept(this);
        }
        catch (Throwable t) {
        }
      }
    }

    return list;
  }

  /* 
      Visitors

      function-local variable (VVarRef.Local) 
      global register (VVarRef.Register)

  */

  public void visit(VAssign a) throws Throwable {}

  public void visit(VCall c) throws Throwable {
    for (Lines l : list.get(functionName)) {
      if (l.labels.contains(currentLabel) && l.getStart() < c.sourcePos.line && l.getEnd() > c.sourcePos.line) {
        l.setCrossCall(true);        
      }
    }
  }

  public void visit(VBuiltIn c) throws Throwable {}

  public void visit(VMemWrite w) throws Throwable {}

  public void visit(VMemRead r) throws Throwable {}

  public void visit(VBranch b) throws Throwable {}

  public void visit(VGoto g) throws Throwable {}

  public void visit(VReturn r) throws Throwable {}

}
