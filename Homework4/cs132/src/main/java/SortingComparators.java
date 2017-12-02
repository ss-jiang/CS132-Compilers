import java.util.*;

public class SortingComparators {

  public class sortS implements Comparator<Lines> {
    public int compare(Lines l1, Lines l2) {
      if (l1.ranges.start < l2.ranges.start) {
        return -1;
      }
      else if (l1.ranges.start > l2.ranges.start) {
        return 1;
      }
      else {
        return 0;
      }
    }
  }
  
  public class sortE implements Comparator<Lines> {
    public int compare(Lines l1, Lines l2) {
      if (l1.ranges.end < l2.ranges.end) {
        return -1;
      }
      else if (l1.ranges.end > l2.ranges.end) {
        return 1;
      }
      else {
        return 0;
      }
    }
  }
  
}
