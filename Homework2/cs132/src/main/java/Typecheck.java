import syntaxtree.*;

public class Typecheck { 
    public static void main (String [] args) {
        
        Goal root = null;

        try {
            root = new MiniJavaParser(System.in).Goal();
        } catch (ParseException e) {
            System.out.println(e.toString()); 
            System.exit(1);
        } 

        Context mc = new Context(); 

        // First pass visitor collects all the variables
        if(root.accept(new FirstPassVisitor(), mc).equals("invalid")) {
            System.out.println("Type error");
            System.exit(1); 
        }
        else { // Primitive visitor takes care of type checking
            if (root.accept(new PrimitiveVisitor(), mc).equals("invalid")) {
                System.out.println("Type error");
            } 
            else {
                System.out.println("Program type checked successfully");
            }
        }
    }

}