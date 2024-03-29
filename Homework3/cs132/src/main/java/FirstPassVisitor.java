//
// Generated by JTB 1.3.2
//

import visitor.*;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class FirstPassVisitor implements GJVisitor<String, Context> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   public String visit(NodeList n, Context argu) {
      String _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeListOptional n, Context argu) {
      if ( n.present() ) {
         String _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public String visit(NodeOptional n, Context argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public String visit(NodeSequence n, Context argu) {
      String _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeToken n, Context argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    // TODO
   public String visit(Goal n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> ( VarDeclaration() )*
    * f15 -> ( Statement() )*
    * f16 -> "}"
    * f17 -> "}"
    */
    // TODO
   public String visit(MainClass n, Context argu) {
      String _ret=null;

      argu.currentClass = "Main";
      argu.inMethod = true;
      argu.classFields.put(argu.currentClass, new LinkedHashMap<String, String>());

      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      n.f15.accept(this, argu);
      n.f16.accept(this, argu);
      n.f17.accept(this, argu);

      argu.inMethod = false;
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    // TODO
   public String visit(TypeDeclaration n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    // TODO
   public String visit(ClassDeclaration n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);

      argu.currentClass = f1Output;
      if (!argu.classList.contains(f1Output)) {
            argu.classList.add(f1Output);
            argu.classFields.put(f1Output, new LinkedHashMap<String, String>());
            argu.VMT.put(f1Output, new LinkedHashMap<String, String>());     
            argu.classMethods.put(argu.currentClass, new LinkedHashMap<String, String>());   
            argu.methodFields.put(argu.currentClass, new HashMap<String, LinkedHashMap<String, String>>());            
      }
      else {
            return "error";
      }

      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      String f4Output = n.f4.accept(this, argu);



      n.f5.accept(this, argu);

      if (!argu.VMT.get(f1Output).isEmpty()) {
            LinkedHashMap<String, String> hm = argu.classFields.get(argu.currentClass);
            argu.classFields.put(argu.currentClass, new LinkedHashMap<String, String>());
            argu.classFields.get(argu.currentClass).put("vmt", null);

            for (String key : hm.keySet()) {
                  argu.classFields.get(argu.currentClass).put(key, hm.get(key));
            }
            // argu.classSpace.put(argu.currentClass, argu.classSpace.get(argu.currentClass) + 1);
            // argu.classFields.put(argu.currentClass, )
      }
      // argu.print("      There are %s fields in class %s", argu.classSpace.get(argu.currentClass), f1Output);
      
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
    // TODO
   public String visit(ClassExtendsDeclaration n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);

      argu.currentClass = f1Output;

      n.f2.accept(this, argu);
      String f3Output = n.f3.accept(this, argu);

      if (argu.classList.contains(f1Output) || !argu.classList.contains(f3Output)) {
            return "error";
      }
      else {
            argu.classList.add(f1Output);
            argu.classFields.put(f1Output, new LinkedHashMap<String, String>(argu.classFields.get(f3Output)));
            argu.VMT.put(f1Output, new LinkedHashMap<String,String>(argu.VMT.get(f3Output)));
            argu.classMethods.put(argu.currentClass, new LinkedHashMap<String, String>(argu.classMethods.get(f3Output)));
            argu.methodFields.put(argu.currentClass, new HashMap<String, LinkedHashMap<String, String>>());            
      }

      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);

      if (!argu.VMT.get(f1Output).isEmpty()) {
            LinkedHashMap<String, String> hm = argu.classFields.get(argu.currentClass);
            argu.classFields.put(argu.currentClass, new LinkedHashMap<String, String>());
            argu.classFields.get(argu.currentClass).put("vmt", null);

            for (String key : hm.keySet()) {
                  argu.classFields.get(argu.currentClass).put(key, hm.get(key));
            }
            // argu.classSpace.put(argu.currentClass, argu.classSpace.get(argu.currentClass) + 1);
      }
      // argu.print("      There are %s fields in class %s", argu.classSpace.get(argu.currentClass), f1Output);

      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    // TODO
   public String visit(VarDeclaration n, Context argu) {
      String _ret=null;
      String f0Output = n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      if (!argu.inMethod) {
            // argu.classSpace.put(argu.currentClass, argu.classSpace.get(argu.currentClass) + 1);
            // System.out.println("added " + f1Output + " to classFields" );
            argu.classFields.get(argu.currentClass).put(f1Output, f0Output);
      }
      if (f0Output.equals("array")) {
            argu.arraySize.put(f1Output, null);
      }
      if (!f0Output.equals("int") && !f0Output.equals("boolean") && !f0Output.equals("array")) {
            argu.classObject.put(f1Output, f0Output);                  
      }
      else {
            if (argu.inMethod && argu.currentMethod != null)  {
                  argu.methodFields.get(argu.currentClass).get(argu.currentMethod).put(f1Output, f0Output);
            }
      }
      return _ret;
   }

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
    // TODO
   public String visit(MethodDeclaration n, Context argu) {
      argu.inMethod = true;
      String _ret=null;

      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);

      if (argu.currentClass != null) {
            if (argu.VMT.containsKey(argu.currentClass)) {
                  argu.VMT.get(argu.currentClass).put(f2Output, argu.currentClass);   
                  argu.classMethods.get(argu.currentClass).put(f2Output, f1Output);
                  argu.methodFields.get(argu.currentClass).put(f2Output,  new LinkedHashMap<String, String>());    
            }
      }

      argu.currentMethod = f2Output;

      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);

      argu.inMethod = false;
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
    // TODO
   public String visit(FormalParameterList n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    // TODO
   public String visit(FormalParameter n, Context argu) {
      String _ret=null;
      String f0Output = n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);

      if (!argu.methodFormals.containsKey(argu.currentClass)) {
            argu.methodFormals.put(argu.currentClass, new HashMap<String, LinkedHashMap<String, String>>());
      }
      if (!argu.methodFormals.get(argu.currentClass).containsKey(argu.currentMethod)) {
            argu.methodFormals.get(argu.currentClass).put(argu.currentMethod, new LinkedHashMap<String, String>());
      }
      
      argu.methodFormals.get(argu.currentClass).get(argu.currentMethod).put(f1Output, f0Output);

      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    // TODO
   public String visit(FormalParameterRest n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    // TODO
   public String visit(Type n, Context argu) {
      String _ret=null;
      String f0Output = n.f0.accept(this, argu);
      return f0Output;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    // TODO
   public String visit(ArrayType n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return "array";
   }

   /**
    * f0 -> "boolean"
    */
    // TODO
   public String visit(BooleanType n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return "boolean";
   }

   /**
    * f0 -> "int"
    */
    // TODO
   public String visit(IntegerType n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return "int";
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
    // TODO
   public String visit(Statement n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
    // TODO
   public String visit(Block n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    // TODO
   public String visit(AssignmentStatement n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
    // TODO
   public String visit(ArrayAssignmentStatement n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
    // TODO
   public String visit(IfStatement n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
    // TODO
   public String visit(WhileStatement n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    // TODO
   public String visit(PrintStatement n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      // argu.print("PrintIntS(%s)", )

      return _ret;
   }

   /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
    // TODO
   public String visit(Expression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
    // TODO
   public String visit(AndExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    // TODO
   public String visit(CompareExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    // TODO
   public String visit(PlusExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    // TODO
   public String visit(MinusExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    // TODO
   public String visit(TimesExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    // TODO
   public String visit(ArrayLookup n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    // TODO
   public String visit(ArrayLength n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
    // TODO
   public String visit(MessageSend n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
    // TODO
   public String visit(ExpressionList n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
    // TODO
   public String visit(ExpressionRest n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
    // TODO
   public String visit(PrimaryExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
    // TODO
   public String visit(IntegerLiteral n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "true"
    */
    // TODO
   public String visit(TrueLiteral n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "false"
    */
    // TODO
   public String visit(FalseLiteral n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
    // TODO
   public String visit(Identifier n, Context argu) {
      n.f0.accept(this, argu);
      return (n.f0.tokenImage);
   }

   /**
    * f0 -> "this"
    */
    // TODO
   public String visit(ThisExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    // TODO
   public String visit(ArrayAllocationExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    // TODO
   public String visit(AllocationExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
    // TODO
   public String visit(NotExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
    // TODO
   public String visit(BracketExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

}
