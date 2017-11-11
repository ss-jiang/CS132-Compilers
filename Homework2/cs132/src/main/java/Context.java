import java.util.*;

public class Context { 

    // // <identifier, type>
    // public HashMap<String, String> locals = new HashMap();
    // // <identifier, type>
    // public HashMap<String, String> fields = new HashMap();
    // identifier
    public ArrayList<String> classList = new ArrayList<String>();
 
    // class -> (identifier -> type)
    public HashMap<String, HashMap<String, String>> classLocals = new HashMap<String, HashMap<String, String>>();
    public HashMap<String, HashMap<String, String>> classFields = new HashMap<String, HashMap<String, String>>();
    public HashMap<String, HashMap<String, String>> classMethods = new HashMap<String, HashMap<String, String>>();
    
    // class -> parent class
    public HashMap<String, String> parentClass = new HashMap<String, String>();


    // TODO: Need another hashmap to check if a variable used in subclass is declared in parent class when it is not declared in the subclass

    public ArrayList<String> currentExpressionList = new ArrayList<String>();

    // method -> (identifier -> type)
    public HashMap<String, HashMap<String, String>> methodFields = new HashMap<String, HashMap<String, String>>();    
    public HashMap<String, HashMap<String, ArrayList<String>>> methodLocalsType = new HashMap<String, HashMap<String, ArrayList<String>>>();  
    public HashMap<String, HashMap<String, ArrayList<String>>> methodLocalsName = new HashMap<String, HashMap<String, ArrayList<String>>>();


    // class variables
    public String currentClass = null;
    public Boolean inClass = false;

    // method variables
    public String currentMethod = null;
    public Boolean inMethod = false;


    // public HashSet<String> typeList = new HashSet<String>(Arrays.asList("int", "boolean", "array"));
    public ArrayList<String> typeList = new ArrayList<String>(Arrays.asList("int", "boolean", "array"));



    public String findInMaps(String value) {
        String type = null;

        type = findLimitedScope(value, currentClass);
        if (type != null) {
            return type;
        }
        else {
            Boolean foundInParent = false;
            String pClass = parentClass.get(currentClass);

            while (pClass != null) {
                type = findLimitedScope(value, pClass);
                if (type != null) {
                    return type;
                }
                else {
                    pClass = parentClass.get(pClass);
                }
            }
        }
        return type;
    }

    public String findLimitedScope(String value, String className) {
        Boolean foundInMethod = false;
        int indexInMethodLocals = -1;
        String type = null;
        if (inMethod && !currentMethod.equals(null)) {
            if (methodFields.get(currentMethod).containsKey(value)) {
                type = methodFields.get(currentMethod).get(value);
                foundInMethod = true;
            }

            for (int i = 0; i < methodLocalsName.get(className).get(currentMethod).size(); i++) {
                if (methodLocalsName.get(className).get(currentMethod).get(i).equals(value)) {
                    type = methodLocalsType.get(className).get(currentMethod).get(i);
                    foundInMethod = true;
                    indexInMethodLocals = i;
                }
            }
            // if (indexInMethodLocals != -1) {
            //     String key = null;
            //     for ( String a : methodLocals.get(currentClass).get(currentMethod).get(indexInMethodLocals).keySet()) {
            //         key = a;
            //     }
            //     type = methodLocals.get(currentClass).get(currentMethod).get(indexInMethodLocals).get(key);                
            //     foundInMethod = true;
            // }
        }
        if (!foundInMethod) {
            if (classFields.get(className).containsKey(value)) {
                type = classFields.get(className).get(value);
            }
        }
        return type;
    }
}
