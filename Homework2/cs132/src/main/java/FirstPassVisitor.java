//
// Generated by JTB 1.3.2
//

import visitor.*;
import syntaxtree.*;
import java.util.*;

public class FirstPassVisitor implements GJVisitor<String, Context> {

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
         String eOutput = null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            eOutput = e.nextElement().accept(this,argu);
            if (eOutput == null) {
                  return "valid";
            }
            if (eOutput.equals("error") || eOutput.equals("invalid")) {
                  return eOutput;
            }
            _count++;
         }
         return eOutput;
      }
      else
         return "valid";
   }

   public String visit(NodeOptional n, Context argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return "valid";
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
      String f0Output = n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      if (f0Output.equals("invalid")) {
            return f0Output;
      }
      if (f1Output.equals("invalid")) {
            return f1Output;
      }

      return new String("valid");
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
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      if (argu.classList.contains(f1Output)) {
            return new String("invalid");
      } else {
            argu.classList.add(f1Output);
            argu.currentClass = f1Output; // add current class to stack
            argu.classFields.put(f1Output, new HashMap<String, String>());
            argu.classLocals.put(f1Output, new HashMap<String, String>());
            argu.classMethods.put(f1Output, new HashMap<String, String>());
            argu.parentClass.put(f1Output, null);
            argu.typeList.add(f1Output);
            argu.inClass = true;
            argu.currentClass = f1Output;
      }

      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);

      // TODO: Store String [] type
      String f11Output = n.f11.accept(this, argu);
      if (!argu.classLocals.get(f1Output).containsKey(f11Output)) {
            argu.classLocals.get(f1Output).put(f11Output, "String []");
      } else {
            return new String("invalid");
      }

      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      String f14Output = n.f14.accept(this, argu);
      n.f15.accept(this, argu);
      n.f16.accept(this, argu);
      n.f17.accept(this, argu);

      if (f14Output.equals("invalid")) {
            return f14Output;
      }

      return new String("valid");
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    // TODO
   public String visit(TypeDeclaration n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      return f0Output;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    // DONE
   public String visit(ClassDeclaration n, Context argu) {
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);

      if (argu.classList.contains(f1Output)) {
            return new String("invalid");
      } else {
            argu.classList.add(f1Output);
            argu.currentClass = f1Output; // add current class to stack
            argu.classFields.put(f1Output, new HashMap<String, String>());
            argu.classLocals.put(f1Output, new HashMap<String, String>());
            argu.classMethods.put(f1Output, new HashMap<String, String>());
            argu.parentClass.put(f1Output, null);
            argu.typeList.add(f1Output);
            argu.inClass = true;
      }

      n.f2.accept(this, argu);
      String f3Output = n.f3.accept(this, argu);
      String f4Output = n.f4.accept(this, argu);
      n.f5.accept(this, argu);

      if (f3Output.equals("invalid")) {
            return f3Output;
      }
      if (f4Output.equals("invalid")) {
            return f4Output;
      }

      argu.inClass = false;
      argu.currentClass = null;

      return new String("valid");
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
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      if (argu.classList.contains(f1Output)) {
            return new String("invalid");
      } else {
            argu.classList.add(f1Output);
            argu.currentClass = f1Output; // add current class to stack
            argu.classFields.put(f1Output, new HashMap<String, String>());
            argu.classLocals.put(f1Output, new HashMap<String, String>());
            argu.classMethods.put(f1Output, new HashMap<String, String>());
            argu.typeList.add(f1Output);
            argu.inClass = true;
      }

      n.f2.accept(this, argu);
      String f3Output = n.f3.accept(this, argu);
      if (!argu.classList.contains(f3Output) || f3Output.equals(f1Output)) {
            return new String("invalid");
      } else {
            argu.parentClass.put(f1Output, f3Output);
      }

      n.f4.accept(this, argu);
      String f5Output = n.f5.accept(this, argu);
      String f6Output = n.f6.accept(this, argu);
      n.f7.accept(this, argu);

      if (f5Output.equals("invalid")) {
            return f5Output;
      }
      if (f6Output.equals("invalid")) {
            return f6Output;
      }

      argu.inClass = false;
      argu.currentClass = null;

      return new String("valid");
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    // DONE
   public String visit(VarDeclaration n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      if (argu.inMethod && argu.currentMethod != null) {
            boolean presentInMethodLocals = false;

            for (int i = 0; i < argu.methodLocalsName.get(argu.currentClass).get(argu.currentMethod).size(); i++) {
                  if (argu.methodLocalsName.get(argu.currentClass).get(argu.currentMethod).contains(f1Output)) {
                        presentInMethodLocals = true;
                  }
            } 

            if (argu.methodFields.get(argu.currentMethod).containsKey(f1Output) || presentInMethodLocals) {
                  return new String("invalid");
            }
            else {
                  argu.methodFields.get(argu.currentMethod).put(f1Output, f0Output); 
                  return new String("valid");                 
            }
      }
      else {
            if (argu.classFields.get(argu.currentClass).containsKey(f1Output)) {
                  return new String("invalid");
            } 
            else {
                  argu.classFields.get(argu.currentClass).put(f1Output, f0Output);
                  return new String("valid");
            }
      }
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
    // DONE
   public String visit(MethodDeclaration n, Context argu) {
      n.f0.accept(this, argu);

      String f1Output = n.f1.accept(this, argu);
      if (!argu.typeList.contains(f1Output) && !argu.classList.contains(f1Output)) {
            return new String("invalid");
      }

      String f2Output = n.f2.accept(this, argu);
      if (argu.classMethods.get(argu.currentClass).containsKey(f2Output)) {
            return new String("invalid");
      } 
      else {
            argu.classMethods.get(argu.currentClass).put(f2Output, f1Output);

            if (argu.methodLocalsName.get(argu.currentClass) == null) {
                  argu.methodLocalsName.put(argu.currentClass, new HashMap<String, ArrayList<String>>());
            }
            if (argu.methodLocalsType.get(argu.currentClass) == null) {
                  argu.methodLocalsType.put(argu.currentClass, new HashMap<String, ArrayList<String>>());                        
            }

            argu.methodLocalsType.get(argu.currentClass).put(f2Output, new ArrayList<String>());
            argu.methodLocalsName.get(argu.currentClass).put(f2Output, new ArrayList<String>());

            argu.methodFields.put(f2Output, new HashMap<String, String>());
            argu.inMethod = true;
            argu.currentMethod = f2Output; 
      }

      n.f3.accept(this, argu);
      String f4Output = n.f4.accept(this, argu);
      if (f4Output != null && f4Output.equals("invalid")) {
            return f4Output;
      }

      if (argu.inClass && argu.currentClass != null) {
            // TODO: check parent method parameters
            if (argu.parentClass.containsKey(argu.currentClass)) {
                  String parentClass = argu.parentClass.get(argu.currentClass);
                  if (parentClass != null && argu.classMethods.get(parentClass).containsKey(f2Output)) {                        
                        if (!argu.classMethods.get(parentClass).get(f2Output).equals(f1Output)) {
                              return new String("invalid");
                        }
                        ArrayList<String> parentFP = argu.methodLocalsType.get(parentClass).get(f2Output);
                        ArrayList<String> childFP  = argu.methodLocalsType.get(argu.currentClass).get(f2Output);
                        
                        if (parentFP.size() != childFP.size()) {
                              return new String("invalid");
                        }
                        else {
                              for (int i = 0; i < parentFP.size(); i++) {
                                    if (!parentFP.get(i).equals(childFP.get(i))) {
                                          return new String("invalid");
                                    }
                              }
                        }
                  }
            }
      }

      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      String f7Output = n.f7.accept(this, argu);
      if (f7Output.equals("invalid")) {
            return f7Output;
      }

      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);      

      argu.inMethod = false;
      argu.currentMethod = null;

      return new String("valid");
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
    // DONE
    public String visit(FormalParameterList n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);

      if (f0Output.equals("invalid") || f1Output.equals("invalid")) {
            return new String("invalid");
      }
      return new String("valid");
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    // DONE
   public String visit(FormalParameter n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      
      boolean foundInMethodLocals = false;
      for (int i = 0; i < argu.methodLocalsName.get(argu.currentClass).get(argu.currentMethod).size(); i++) {
            if (argu.methodLocalsName.get(argu.currentClass).get(argu.currentMethod).contains(f1Output)) {
                  foundInMethodLocals = true;
                  return new String("invalid");
            }
      }
      if (!foundInMethodLocals) {
            argu.methodLocalsType.get(argu.currentClass).get(argu.currentMethod).add(f0Output);
            argu.methodLocalsName.get(argu.currentClass).get(argu.currentMethod).add(f1Output);
            
            return new String("valid");
      }
      return new String("invalid");
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    // DONE
   public String visit(FormalParameterRest n, Context argu) {
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      return f1Output;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    // DONE
    public String visit(Type n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      return f0Output;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */ 

    // DONE
   public String visit(ArrayType n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      return new String("array");
   }

   /**
    * f0 -> "boolean"
    */
    // DONE
   public String visit(BooleanType n, Context argu) {
      n.f0.accept(this, argu);
      return new String("boolean");
   }

   /**
    * f0 -> "int"
    */
    // DONE
   public String visit(IntegerType n, Context argu) {
      n.f0.accept(this, argu);
      return new String("int");
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public String visit(Statement n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return new String("valid");
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
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
   public String visit(PrintStatement n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
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
   public String visit(Expression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return new String("valid");
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
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
   public String visit(PrimaryExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public String visit(IntegerLiteral n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public String visit(TrueLiteral n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public String visit(FalseLiteral n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
    // DONE
    public String visit(Identifier n, Context argu) {
      n.f0.accept(this, argu);
      return (n.f0.tokenImage);
   }

   /**
    * f0 -> "this"
    */
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
   public String visit(BracketExpression n, Context argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

}
