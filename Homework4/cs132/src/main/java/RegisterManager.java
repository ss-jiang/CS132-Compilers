import java.util.*;

public class RegisterManager {

  public SortingComparators SC = new SortingComparators();

  public int calleeRegisterCount = 0;
  public int outs = 0;

  public LinkedList<String> callees = new LinkedList<String>();
  public LinkedList<String> callers = new LinkedList<String>();

  // Holds label -> (var, line start, line end)
  public LinkedList<Lines> liveList = new LinkedList<Lines>();

  // Holds (var, line start, line end) -> register
  public LinkedHashMap<Lines, String> usedRegisters = new LinkedHashMap<Lines, String>();
  public LinkedHashMap<Lines, String> listLocals = new LinkedHashMap<Lines, String>();  

  public LinkedList<String> freeRegisters = new LinkedList<String>();
  public LinkedList<Lines> inUse;

  public String getRegister(Boolean crossCall) {
    String ret = null; 

    if (crossCall) {
      Iterator<String> i = freeRegisters.iterator();
      while (i.hasNext()) {
        ret = i.next();
        if (ret.startsWith("s")) {
          i.remove();
          return ret;
        }
      }

      calleeRegisterCount++;
      ret = callees.getFirst();
      callees.removeFirst();
      return ret;
    }
    else {
      Iterator<String> i = freeRegisters.iterator();
      if (i.hasNext()) {
        ret = i.next();
        freeRegisters.remove(ret);
      }
      else {
        if (callers.isEmpty()) {
          calleeRegisterCount++;
          ret = callees.getFirst();
          callees.removeFirst();
        }
        else {
          ret = callers.getFirst();
          callers.removeFirst();
        }
      }
      
    }
    return ret;
  }

  public void assignRegisters() {
    inUse = new LinkedList<Lines>();
    LinkedList<Lines> ranges = new LinkedList<Lines>(liveList);
    ranges.sort(SC.new sortS());

    for (Lines l : ranges) {
      deallocateRegisters(l);

      if (inUse.size() == 17 || (l.getCrossCall() && callees.isEmpty())) {
        Lines i = inUse.getLast();
        if (i.ranges.end > l.ranges.end) {
            usedRegisters.put(l, usedRegisters.get(i));
            listLocals.put(i, String.format("local[%d]", calleeRegisterCount++));
            inUse.remove(i);
            inUse.add(l);
            inUse.sort(SC.new sortE());
        } else {
            listLocals.put(l, String.format("local[%d]", calleeRegisterCount++));
        }
      }
      else {
        usedRegisters.put(l, getRegister(l.getCrossCall()));
        inUse.add(l);
      }
    }
  }

  public void deallocateRegisters(Lines l) {
    inUse.sort(SC.new sortE());

    Iterator<Lines> i = inUse.iterator();
    while (i.hasNext()) {
      Lines r = i.next();
      if (r.getEnd() < l.getStart()) {
        i.remove();
        freeRegisters.add(usedRegisters.get(r));
      }
      else {
        return;
      }
    }
  }

  public HashMap<String, String> getRegisters() {
    assignRegisters();

    HashMap<String, String> output = new HashMap<String, String>();

    for (Lines key : usedRegisters.keySet()) {
      output.put(key.getVar(), usedRegisters.get(key));
    }

    for (Lines key : listLocals.keySet()) {
      output.put(key.getVar(), listLocals.get(key));
    }

    return output;
  }
  
}
