import java.util.*;

public class Context { 

/* Container variables */
      // Holds all the class names present in the program
      public ArrayList<String> classList = new ArrayList<String>();
      
      // Holds the class -> (method, class) mapping of the program
      public HashMap<String, LinkedHashMap<String, String>> VMT = new HashMap<String, LinkedHashMap<String, String>>();

      // Holds the class -> (field name, type) mapping
      public HashMap<String, LinkedHashMap<String, String>> classFields = new HashMap<String, LinkedHashMap<String, String>>();
      public String getFieldType(String c, String m) {
            if (classFields.containsKey(c)) {
                  if (classFields.get(c).containsKey(m)) {
                        return classFields.get(c).get(m);
                  }
            }
            return null;
      }

      // Holds the class -> (method name, type) mapping
      public HashMap<String, LinkedHashMap<String, String>> classMethods = new HashMap<String, LinkedHashMap<String, String>>();
      public String getMethodType(String c, String m) {
            if (classMethods.containsKey(c)) {
                  if (classMethods.get(c).containsKey(m)) {
                        return classMethods.get(c).get(m);
                  }
            }
            return null;
      }
      
      // Holds the class -> method -> (formals, type) mapping
      public HashMap<String, HashMap<String, LinkedHashMap<String, String>>> methodFormals = new HashMap<String, HashMap<String, LinkedHashMap<String, String>>>();
      public String getFromMethodFormals(String c, String m, String k) {
            if (methodFormals.containsKey(c)) {
                  if (methodFormals.get(c).containsKey(m)) {
                        if (methodFormals.get(c).get(m).containsKey(k)) {
                              return methodFormals.get(c).get(m).get(k);     
                        }
                  }
            }
            return null;
      }


      // Holds the current declared class objects
      public HashMap<String, String> classObject = new HashMap<String, String>();

      // Holds the t.x -> class mapping
      public HashMap<String, String> tempVar2Class = new HashMap<String, String>();

      // Holds the t.x -> string mapping
      public LinkedList<HashMap<String, String>> tempVar2String = new LinkedList<HashMap<String, String>>();

      // Holds the array -> size mapping
      public HashMap<String, Integer> arraySize = new HashMap<String, Integer>();

      public HashMap<String, String> msType = new HashMap<String, String>();

/* End container variables */


/* Counter variables */
      public int T = 0;       // var starts at 0
      public void addT() {
            T++;
      }

      public int N = 1;       // null starts at 1
      public void addN() {
            N++;
      }

      public int I = 1;       // if starts at 1
      public void addI() {
            I++;
      }

      public int B = 1;       // bound starts at 1
      public void addB() {
            B++;
      }

      public int W = 1;       // while starts at 1
      public void addW() {
            W++;
      }

      // keep track of indentations
      public int indent = 0;
      public void addIndent() {
            indent++;
      }
      public void subIndent() {
            indent--;
      }

/* End counter variables */


/* String functions */
      public void print(String s, Object... a) {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < indent * 2; i++) {
                  output.append(" ");
            }
            System.out.println(String.format(output + s, a));
      }

      public void printVMT() {
            for (int i = 0; i < classList.size(); i++) {
                  String className = classList.get(i);
                  if (className.equals("Main")) {
                        continue;
                  }
                  HashMap<String, String> methods = VMT.get(className);
      
                  System.out.println("const vmt_" + className);

                  for (String key : methods.keySet()) {
                        System.out.println("  :" + methods.get(key) + "." + key);
                  }
                  
                  // for (int j = 0; j < methods.size(); j++) {
                  //       System.out.println("  :" + className + "." + methods.get(j));
                  // }
                  System.out.println();
            }
      }

      public void printNullCheck(String value) {
            print("if %s goto :null%d", value, N);
            print("  Error(\"null pointer\")");
            print("null%d:", N);

            addN();
      }

      public void printAllocArray() {
            System.out.println("func AllocArray(size)");
            System.out.println("  bytes = MulS(size 4)");
            System.out.println("  bytes = Add(bytes 4)");
            System.out.println("  v = HeapAllocZ(bytes)");
            System.out.println("  [v] = size");
            System.out.println("  ret v");
      }

      public String formString(String s, Object... a) {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < indent * 2; i++) {
                  output.append(" ");
            }
            return String.format(output + s, a).toString();
      }

/* End print functions */


/* Miscellaneous variables */
      public Boolean arrayAlloc = false;

      public String currentClass = null;
      
      public Boolean inMethod = false;
      public String currentMethod = null;

      public Boolean calledFromAS = false;

      public String currentExpList = "";
      public String getCurrentExpList() {
            return currentExpList.trim();
      }
      public void clearExpList() {
            currentExpList = "";
      }

      public String currentFormals = "";
      public String getCurrentFormals() {
            return currentFormals.trim();
      }
      public void clearFormals() {
            currentFormals = "";
      }

/* End Miscellaneous variables */


/* Typing */
      public class Pair<L,R> {
            private L l;
            private R r;

            public Pair() {}

            public Pair(L l, R r) {
                  this.l = l;
                  this.r = r;
            }
            
            public L getL() { return l; }
            public R getR() { return r; }
            public void setL(L l) { this.l = l; }
            public void setR(R r) { this.r = r; }
      }
/* End Typing */

}