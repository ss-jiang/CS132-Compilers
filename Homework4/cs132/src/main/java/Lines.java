import java.util.*;
import java.lang.*;

public class Lines { 

    public static class Range {
      public int start;
      public int end;

      public Range(int start) {
        this.start = start;
        this.end = start;
      }
    }

    private String var;

    private Boolean call;
    private Boolean crossCall;
    
    // public HashSet<String> tempLabels = new HashSet<String>();
    public HashSet<String> labels = new HashSet<String>();

    public LinkedList<Range> ranges = new LinkedList<Range>();

    // private int start;
    // private int end;

    public Lines(String var, int start) {
      this.var = var;

      ranges.add(new Range(start));
      // this.start = start;
      // this.end = end;
      this.call = false;
      this.crossCall = false;
    }
    
    public void setStart(int start) {
      ranges.getLast().start = start;
    }

    public void setEnd(int end) {
      ranges.getLast().end = end;
      // this.end = end;
    }

    public void setCall(Boolean call) {
      this.call = call;
    }

    public void setCrossCall(Boolean crossCall) {
      this.crossCall = crossCall;
    }

    public String getVar() {
      return this.var;
    }

    public int getStart() {
      return ranges.getLast().start;
    }

    public int getEnd() {
      return ranges.getLast().end;
    }

    public Boolean getCall() {
      return this.call;
    }

    public Boolean getCrossCall() {
      return this.crossCall;
    }

    // public String getInterval() {
    //   return String.format("%d - %d", this.start, this.end);
    // }

    public void addRange(int start) {
      Range r = new Range(start);
      ranges.add(r);
    }

    public String printRanges() {
      StringBuilder s = new StringBuilder();

      for (Range r : ranges) {
        s.append(String.format("%d - %d, ", r.start, r.end));
      }

      return s.toString();
    }

    public Boolean alive(int lineNum){
      if (ranges.getLast().end <= lineNum) {
        return true;
      }
      return false;
    }

}