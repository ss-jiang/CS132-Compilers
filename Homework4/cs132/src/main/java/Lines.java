import java.util.*;

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
    
    public HashSet<String> tempLabels = new HashSet<String>();
    public HashSet<String> labels = new HashSet<String>();

    // public LinkedList<Range> ranges = new LinkedList<Range>();

    public Range ranges; 

    public Lines(String var, int start) {
      this.var = var;

      ranges = new Range(start);

      this.call = false;
      this.crossCall = false;
    }
    
    public void setStart(int start) {
      ranges.start = start;
    }

    public void setEnd(int end) {
      ranges.end = end;
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
      return ranges.start;
    }

    public int getEnd() {
      return ranges.end;
    }

    public Boolean getCall() {
      return this.call;
    }

    public Boolean getCrossCall() {
      return this.crossCall;
    }

    public String printRanges() {
      StringBuilder s = new StringBuilder();

      // for (Range r : ranges) {
      s.append(String.format("%d - %d", ranges.start, ranges.end));
      // }

      return s.toString();
    }

    public Boolean alive(int lineNum){
      if (ranges.end <= lineNum) {
        return true;
      }
      return false;
    }

    public void save(int lineNum) {
      ranges.end = lineNum;
      labels.addAll(tempLabels);
      tempLabels.clear();
      if (call) {
        crossCall = true;
      }
    }

    public void use(int lineNum) {
      ranges.end = lineNum;
      tempLabels.clear();
    }

}