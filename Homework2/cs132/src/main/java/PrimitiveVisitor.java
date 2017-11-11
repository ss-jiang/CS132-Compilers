//
// Generated by JTB 1.3.2
//

import visitor.*;
import syntaxtree.*;
import java.util.*;

public class PrimitiveVisitor implements GJVisitor<String, Context> {

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
    // DONE
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
    // DONE
   public String visit(MainClass n, Context argu) {
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      if (argu.classList.contains(f1Output)) {
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
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      String f15Output = n.f15.accept(this, argu);
      if (f15Output.equals("invalid")) {
            return f15Output;
      }

      n.f16.accept(this, argu);
      n.f17.accept(this, argu);

      return new String("valid");
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    // DONE
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
            argu.inClass = true;
            argu.currentClass = f1Output;
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
   // DONE
   public String visit(ClassExtendsDeclaration n, Context argu) {
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      if (argu.classList.contains(f1Output)) {
            argu.inClass = true;
            argu.currentClass = f1Output;
      }

      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
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
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      if (f0Output.equals("invalid")) {
            return new String("invalid");
      }

      if (!argu.typeList.contains(f0Output)) {
            return new String("invalid");
      }

      return new String("valid");
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
      String f2Output = n.f2.accept(this, argu);
      argu.inMethod = true;
      argu.currentMethod = f2Output; 

      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);

      String f8Output = n.f8.accept(this, argu);
      if (f8Output.equals("invalid")) {
            return f8Output;
      }

      n.f9.accept(this, argu);
      String f10Output = n.f10.accept(this, argu);
      if (f10Output.equals("error")) {
            return new String("invalid");
      } 
      else {
            if (!argu.typeList.contains(f10Output) && argu.findInMaps(f10Output) != null) {
                  f10Output = argu.findInMaps(f10Output);
            }            
            if (f10Output == null || !f10Output.equals(f1Output)) {
                  return new String("invalid");
            }
      }

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
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return new String("valid");
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

      if (!argu.typeList.contains(f0Output)) {
            return new String("invalid");
      }

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
    // DONE
   public String visit(Statement n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      return f0Output;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
    // DONE
   public String visit(Block n, Context argu) {
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return f1Output;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    // DONE
   public String visit(AssignmentStatement n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);
      n.f3.accept(this, argu);

      f0Output = argu.findInMaps(f0Output);

      if (!argu.typeList.contains(f2Output) && argu.findInMaps(f2Output) == null) {
            return new String("invalid");
      }
      else if (argu.findInMaps(f2Output) != null) {
            f2Output = argu.findInMaps(f2Output);
      }
      if (f0Output == null) {
            return new String("invalid");
      }
      else {
            return (f0Output.equals(f2Output)) ? new String("valid") : new String("invalid");
      }
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
    // DONE
   public String visit(ArrayAssignmentStatement n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      String f5Output = n.f5.accept(this, argu);
      n.f6.accept(this, argu);

      f0Output = argu.findInMaps(f0Output);
      
      if (f0Output == null) {
            return new String("invalid");
      }
      else {
            if (f0Output.equals("array")) {
                  if (!argu.typeList.contains(f2Output) && argu.findInMaps(f2Output) == null) {
                        return new String("invalid");
                  }
                  else if (argu.findInMaps(f2Output) != null) {
                        f2Output = argu.findInMaps(f2Output);
                  }

                  if (!argu.typeList.contains(f5Output) && argu.findInMaps(f5Output) == null) {
                        return new String("invalid");
                  }
                  else if (argu.findInMaps(f5Output) != null) {
                        f5Output = argu.findInMaps(f5Output);
                  }

                  return (f2Output.equals("int") && f5Output.equals("int")) ? new String("valid") : new String("invalid");
            }
      }
      return new String("invalid");
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
    // DONE
   public String visit(IfStatement n, Context argu) {
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);

      if (!argu.typeList.contains(f2Output) && argu.findInMaps(f2Output) == null) {
            return new String("invalid");
      }
      else if (argu.findInMaps(f2Output) != null) {
            f2Output = argu.findInMaps(f2Output);
      }
      
      n.f3.accept(this, argu);
      String f4Output = n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      String f6Output = n.f6.accept(this, argu);

      return (f2Output.equals("boolean") && f4Output.equals("valid") && f6Output.equals("valid")) ? new String("valid") : new String("invalid");
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
    // DONE
   public String visit(WhileStatement n, Context argu) {
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);

      if (!argu.typeList.contains(f2Output) && argu.findInMaps(f2Output) == null) {
            return new String("invalid");
      }
      else if (argu.findInMaps(f2Output) != null) {
            f2Output = argu.findInMaps(f2Output);
      }


      n.f3.accept(this, argu);
      String f4Output = n.f4.accept(this, argu);

      // if (argu.classFields.get(argu.currentClass).containsKey(f2Output)) {
      //       f2Output = argu.classFields.get(argu.currentClass).get(f2Output);
      // }

      return (f2Output.equals("boolean") && f4Output.equals("valid")) ? new String("valid") : new String("invalid");
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    // DONE
   public String visit(PrintStatement n, Context argu) {
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      if (!argu.typeList.contains(f2Output) && argu.findInMaps(f2Output) == null) {
            return new String("invalid");
      }
      else if (argu.findInMaps(f2Output) != null) {
            f2Output = argu.findInMaps(f2Output);
      }

      return (f2Output.equals("int")) ? new String("valid") : new String("invalid");
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
    // DONE
   public String visit(Expression n, Context argu) {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
    // DONE
   public String visit(AndExpression n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);

      if (argu.findInMaps(f0Output) != null) {
            f0Output = argu.findInMaps(f0Output);
      }
      if (argu.findInMaps(f2Output) != null) {
            f2Output = argu.findInMaps(f2Output);
      }

      return (f0Output.equals("boolean") && f2Output.equals("boolean")) ? new String("boolean") : new String("error");
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    // DONE
   public String visit(CompareExpression n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);

      if (argu.findInMaps(f0Output) != null) {
            f0Output = argu.findInMaps(f0Output);
      }
      if (argu.findInMaps(f2Output) != null) {
            f2Output = argu.findInMaps(f2Output);
      }

      return (f0Output.equals("int") && f2Output.equals("int")) ? new String("boolean") : new String("error");
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    // DONE
   public String visit(PlusExpression n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);

      if (argu.findInMaps(f0Output) != null) {
            f0Output = argu.findInMaps(f0Output);
      }
      if (argu.findInMaps(f2Output) != null) {
            f2Output = argu.findInMaps(f2Output);
      }

      return (f0Output.equals("int") && f2Output.equals("int")) ? new String("int") : new String("error");
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    // DONE
   public String visit(MinusExpression n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);

      // if (argu.classFields.get(argu.currentClass).containsKey(f0Output)) {
      //       f0Output = argu.classFields.get(argu.currentClass).get(f0Output);
      // }
      // if (argu.classFields.get(argu.currentClass).containsKey(f2Output)) {
      //       f2Output = argu.classFields.get(argu.currentClass).get(f2Output);
      // }

      if (argu.findInMaps(f0Output) != null) {
            f0Output = argu.findInMaps(f0Output);
      }
      if (argu.findInMaps(f2Output) != null) {
            f2Output = argu.findInMaps(f2Output);
      }

      return (f0Output.equals("int") && f2Output.equals("int")) ? new String("int") : new String("error");
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    // DONE
   public String visit(TimesExpression n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);
      
      // if (argu.classFields.get(argu.currentClass).containsKey(f0Output)) {
      //       f0Output = argu.classFields.get(argu.currentClass).get(f0Output);
      // }
      // if (argu.classFields.get(argu.currentClass).containsKey(f2Output)) {
      //       f2Output = argu.classFields.get(argu.currentClass).get(f2Output);
      // }

      if (argu.findInMaps(f0Output) != null) {
            f0Output = argu.findInMaps(f0Output);
      }
      if (argu.findInMaps(f2Output) != null) {
            f2Output = argu.findInMaps(f2Output);
      }

      return (f0Output.equals("int") && f2Output.equals("int")) ? new String("int") : new String("error");
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    // DONE
   public String visit(ArrayLookup n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      
      // if (argu.classFields.get(argu.currentClass).containsKey(f0Output)) {
      //       f0Output = argu.classFields.get(argu.currentClass).get(f0Output);
      //       return (f0Output.equals("array") && f2Output.equals("int")) ? new String("int") : new String("error");            
      // }

      if (argu.findInMaps(f0Output) != null) {
            f0Output = argu.findInMaps(f0Output);
      }
      if (argu.findInMaps(f2Output) != null) {
            f2Output = argu.findInMaps(f2Output);
      }

      return (f0Output.equals("array") && f2Output.equals("int")) ? new String("int") : new String("error");            
      

      // return new String("error");
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    // DONE
   public String visit(ArrayLength n, Context argu) {
      String f0Output = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      // if (argu.classFields.get(argu.currentClass).containsKey(f0Output)) {
      //       return (argu.classFields.get(argu.currentClass).get(f0Output).equals("array")) ? new String("int") : new String("error");            
      // }

      if (argu.findInMaps(f0Output) != null) {
            f0Output = argu.findInMaps(f0Output);
      }     

      return (f0Output.equals("array")) ? new String("int") : new String("error");

      // return new String("error");
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
      String f0Output = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String f2Output = n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      argu.currentExpressionList.clear();
      String f4Output = n.f4.accept(this, argu);
      n.f5.accept(this, argu);

      String methodReturnType = null;

      if (argu.inMethod && argu.currentMethod != null && !argu.typeList.contains(f0Output)) {
            f0Output = argu.findInMaps(f0Output);
      }
      if (!argu.classList.contains(f0Output)) {
            return new String("invalid");
      }
      else if (!argu.classMethods.get(f0Output).containsKey(f2Output)) {
            return new String("invalid");
      } 
      else {
            methodReturnType = argu.classMethods.get(f0Output).get(f2Output);

            ArrayList<String> currentFP = argu.methodLocalsType.get(f0Output).get(f2Output);
            
            if (currentFP != null && f4Output != null) {
                  if (currentFP.size() != argu.currentExpressionList.size()) {
                        return new String("invalid");
                  }
                  else {
                        for (int i = 0; i < currentFP.size(); i++) {
                              
                              if (!currentFP.get(i).equals(argu.currentExpressionList.get(i))) {
                                    // Check if currentExpressionList element is a subclass of currentFP type

                                    boolean isSubClass = false;
                                    String pClass = argu.parentClass.get(argu.currentExpressionList.get(i));
                                    while (pClass != null) {
                                          if (currentFP.get(i).equals(pClass)) {
                                                isSubClass = true;
                                                break;
                                          }
                                          pClass = argu.parentClass.get(pClass);
                                    }

                                    if (isSubClass) {
                                          break;
                                    }
                                    else {
                                          return new String("invalid");
                                    }
                              }
                        }
                  }
            }
      }

      return (methodReturnType == null) ? new String("invalid") : methodReturnType;
   }

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
    // DONE
   public String visit(ExpressionList n, Context argu) {
      String f0Output = n.f0.accept(this, argu);

      if (argu.findInMaps(f0Output) != null) {
            f0Output = argu.findInMaps(f0Output);
      }
      if (!argu.typeList.contains(f0Output) && !argu.classList.contains(f0Output)) {
            return new String("invalid");
      }

      argu.currentExpressionList.add(f0Output);

      String f1Output = n.f1.accept(this, argu);      
      if (argu.findInMaps(f1Output) != null) {
            f1Output = argu.findInMaps(f1Output);
      }

      return new String("valid");
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
    // DONE
   public String visit(ExpressionRest n, Context argu) {
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);

      // if (argu.classFields.get(argu.currentClass).containsKey(f1Output)) {
      //       f1Output = argu.classFields.get(argu.currentClass).get(f1Output);
      // }
      
      if (argu.findInMaps(f1Output) != null) {
            f1Output = argu.findInMaps(f1Output);
      }

      argu.currentExpressionList.add(f1Output);
      return f1Output;
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
    // DONE
   public String visit(PrimaryExpression n, Context argu) {
      // n.f0.accept(this, argu);
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
    // DONE
   public String visit(IntegerLiteral n, Context argu) {
      n.f0.accept(this, argu);
      return new String("int");
   }

   /**
    * f0 -> "true"
    */
    // DONE
   public String visit(TrueLiteral n, Context argu) {
      n.f0.accept(this, argu);
      return new String("boolean");
   }

   /**
    * f0 -> "false"
    */
     // DONE
   public String visit(FalseLiteral n, Context argu) {
      n.f0.accept(this, argu);
      return new String("boolean");
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
    // DONE
   public String visit(ThisExpression n, Context argu) {
      n.f0.accept(this, argu);
      if (argu.currentClass == null) {
            return new String("invalid");
      }
      else {
            return argu.currentClass;
      }
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    // DONE
   public String visit(ArrayAllocationExpression n, Context argu) {
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String f3Output = n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      if (argu.findInMaps(f3Output) != null) {
            f3Output = argu.findInMaps(f3Output);
      }

      return (f3Output.equals("int")) ? new String("array") : new String("error");
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    // DONE
   public String visit(AllocationExpression n, Context argu) {
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);

      if (argu.typeList.contains(f1Output)) {
            ;
      }
      if (argu.classList.contains(f1Output)) {
            return f1Output;
      }

      return new String("error");
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
    // DONE
   public String visit(NotExpression n, Context argu) {
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);

      if (argu.findInMaps(f1Output) != null) {
            f1Output = argu.findInMaps(f1Output);
      }

      return (f1Output.equals("boolean")) ? new String("boolean") : new String("error");
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
    // TODO: idk if this works lol
   public String visit(BracketExpression n, Context argu) {
      n.f0.accept(this, argu);
      String f1Output = n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      if (argu.findInMaps(f1Output) != null) {
            f1Output = argu.findInMaps(f1Output);
      }

      return f1Output;
   }

}
