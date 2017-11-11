import org.junit.*;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;

import syntaxtree.*;
import visitor.*;

public class MainTest {

    private static MiniJavaParser parser = null;
    public static MiniJavaParser getParser(String s) { 
        ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
        if (parser == null) {
            parser = new MiniJavaParser(is);
        }
        else {
            parser.ReInit(is);
        }
        return parser;
    }

    // @Test public void testMethodDeclaration() throws ParseException {
    //     // VarDeclaration v = getParser("boolean x;").VarDeclaration();
    //     // AssignmentStatement a = getParser("x = false;").AssignmentStatement();
    //     // PrintStatement p = getParser("System.out.println(x);").PrintStatement();

    //     ClassDeclaration cl = getParser("class A { int x; boolean y; int w; boolean z; (w, x, y, z) ").ClassDeclaration();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", cl.accept(pv, c));
    //     // assertEquals("valid", a.accept(pv, c));
    //     // assertEquals("invalid", p.accept(pv, c));         
    // }


    // @Test public void testClassExtends() throws ParseException {
    //     // VarDeclaration v = getParser("boolean x;").VarDeclaration();
    //     // AssignmentStatement a = getParser("x = false;").AssignmentStatement();
    //     // PrintStatement p = getParser("System.out.println(x);").PrintStatement();

    //     ClassDeclaration cl = getParser("class A { int x; boolean y; public int B (boolean x) { A z; int y; if (x) x = false; else y = 1; return y; } } ").ClassDeclaration();
    //     ClassExtendsDeclaration cl1 = getParser("class B extends A { int x; boolean y; public int B (boolean x, int y) { return 2;} }").ClassExtendsDeclaration();
       
    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", cl.accept(pv, c));
    //     assertEquals("invalid", cl1.accept(pv, c));
    //     // assertEquals("valid", a.accept(pv, c));
    //     // assertEquals("invalid", p.accept(pv, c));         
    // }

    // @Test public void testMethodDeclaration() throws ParseException {
    //     // VarDeclaration v = getParser("boolean x;").VarDeclaration();
    //     // AssignmentStatement a = getParser("x = false;").AssignmentStatement();
    //     // PrintStatement p = getParser("System.out.println(x);").PrintStatement();

    //     ClassDeclaration cl = getParser("class A { int x; boolean y; public int B (boolean x) { A z; int y; if (x) x = false; else y = 1; return y; } } ").ClassDeclaration();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", cl.accept(pv, c));
    //     // assertEquals("valid", a.accept(pv, c));
    //     // assertEquals("invalid", p.accept(pv, c));         
    // }

    // @Test public void testClassDeclaration() throws ParseException {
    //     // VarDeclaration v = getParser("boolean x;").VarDeclaration();
    //     // AssignmentStatement a = getParser("x = false;").AssignmentStatement();
    //     // PrintStatement p = getParser("System.out.println(x);").PrintStatement();

    //     ClassDeclaration cl = getParser("class A { int x; int y; boolean z; }").ClassDeclaration();
    //     ClassDeclaration cl2 = getParser("class B { int x; }").ClassDeclaration();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", cl.accept(pv, c));  
    //     assertEquals("valid", cl2.accept(pv, c));  
    //     // assertEquals("valid", a.accept(pv, c));
    //     // assertEquals("invalid", p.accept(pv, c));         
    // }

    // @Test public void testFormalParameterFail() throws ParseException {
    //     FormalParameterList f = getParser("int id, int x, boolean y, boolean x").FormalParameterList();
    //     assertEquals("invalid", f.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testFormalParameterPass() throws ParseException {
    //     FormalParameterList f = getParser("int id, int x, boolean y, int z").FormalParameterList();
    //     assertEquals("valid", f.accept(new PrimitiveVisitor(), new Context()));
    // }

    // @Test public void testPrintFail() throws ParseException {
    //     VarDeclaration v = getParser("boolean x;").VarDeclaration();
    //     AssignmentStatement a = getParser("x = false;").AssignmentStatement();
    //     PrintStatement p = getParser("System.out.println(x);").PrintStatement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));    
    //     assertEquals("valid", a.accept(pv, c));
    //     assertEquals("invalid", p.accept(pv, c));         
    // }
    // @Test public void testPrintPass() throws ParseException {
    //     VarDeclaration v = getParser("int x;").VarDeclaration();
    //     AssignmentStatement a = getParser("x = 3;").AssignmentStatement();
    //     PrintStatement p = getParser("System.out.println(x);").PrintStatement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));    
    //     assertEquals("valid", a.accept(pv, c));
    //     assertEquals("valid", p.accept(pv, c));         
    // }


    // @Test public void testWhileBooleanPass2() throws ParseException {
    //     VarDeclaration v = getParser("boolean x;").VarDeclaration();
    //     AssignmentStatement a = getParser("x = false;").AssignmentStatement();
    //     WhileStatement i = getParser("while (x) x = false;").WhileStatement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));    
    //     assertEquals("valid", a.accept(pv, c));
    //     assertEquals("valid", i.accept(pv, c));         
    // }
    // @Test public void testWhileBooleanFail() throws ParseException {
    //     VarDeclaration v = getParser("int x;").VarDeclaration();
    //     AssignmentStatement a = getParser("x = 2;").AssignmentStatement();
    //     VarDeclaration v1 = getParser("boolean y;").VarDeclaration();
    //     AssignmentStatement a1 = getParser("y = true;").AssignmentStatement();
    //     WhileStatement i = getParser("while (x) y = false;").WhileStatement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));    
    //     assertEquals("valid", a.accept(pv, c));    
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("valid", a1.accept(pv, c)); 
    //     assertEquals("invalid", i.accept(pv, c));         
    // }
    // @Test public void testWhileBooleanPass() throws ParseException {
    //     VarDeclaration v = getParser("boolean x;").VarDeclaration();
    //     AssignmentStatement a = getParser("x = false;").AssignmentStatement();
    //     VarDeclaration v1 = getParser("boolean y;").VarDeclaration();
    //     AssignmentStatement a1 = getParser("y = true;").AssignmentStatement();
    //     WhileStatement i = getParser("while (x && y) y = false;").WhileStatement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));    
    //     assertEquals("valid", a.accept(pv, c));    
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("valid", a1.accept(pv, c)); 
    //     assertEquals("valid", i.accept(pv, c));         
    // }
    // @Test public void testWhileIntPass() throws ParseException {
    //     VarDeclaration v = getParser("int x;").VarDeclaration();
    //     AssignmentStatement a = getParser("x = 2;").AssignmentStatement();
    //     VarDeclaration v1 = getParser("int y;").VarDeclaration();
    //     AssignmentStatement a1 = getParser("y = 2;").AssignmentStatement();
    //     WhileStatement i = getParser("while (x < y) y = 0;").WhileStatement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));    
    //     assertEquals("valid", a.accept(pv, c));    
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("valid", a1.accept(pv, c)); 
    //     assertEquals("valid", i.accept(pv, c));         
    // }


    // @Test public void testIfStatementBooleanPass2() throws ParseException {
    //     VarDeclaration v = getParser("boolean x;").VarDeclaration();
    //     AssignmentStatement a = getParser("x = true;").AssignmentStatement();
    //     VarDeclaration v1 = getParser("boolean y;").VarDeclaration();
    //     AssignmentStatement a1 = getParser("y = true;").AssignmentStatement();
    //     IfStatement i = getParser("if (x) y = false; else y = true;").IfStatement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));    
    //     assertEquals("valid", a.accept(pv, c));    
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("valid", a1.accept(pv, c)); 
    //     assertEquals("valid", i.accept(pv, c));         
    // }
    // @Test public void testIfStatementBooleanPass() throws ParseException {
    //     VarDeclaration v = getParser("boolean x;").VarDeclaration();
    //     AssignmentStatement a = getParser("x = true;").AssignmentStatement();
    //     VarDeclaration v1 = getParser("boolean y;").VarDeclaration();
    //     AssignmentStatement a1 = getParser("y = true;").AssignmentStatement();
    //     IfStatement i = getParser("if (x && y) y = false; else y = true;").IfStatement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));    
    //     assertEquals("valid", a.accept(pv, c));    
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("valid", a1.accept(pv, c)); 
    //     assertEquals("valid", i.accept(pv, c));         
    // }
    // @Test public void testIfStatementIntPass() throws ParseException {
    //     VarDeclaration v = getParser("int x;").VarDeclaration();
    //     AssignmentStatement a = getParser("x = 2;").AssignmentStatement();
    //     VarDeclaration v1 = getParser("int y;").VarDeclaration();
    //     AssignmentStatement a1 = getParser("y = 6;").AssignmentStatement();
    //     IfStatement i = getParser("if (x < y) y = 1; else y = 0;").IfStatement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));    
    //     assertEquals("valid", a.accept(pv, c));    
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("valid", a1.accept(pv, c)); 
    //     assertEquals("valid", i.accept(pv, c));         
    // }

    // @Test public void testAssignmentDeclarationIntFail() throws ParseException {
    //     VarDeclaration v = getParser("int x;").VarDeclaration();
    //     Statement s = getParser("y = 3;").Statement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();
    //     assertEquals("valid", v.accept(pv, c));
    //     assertEquals("invalid", s.accept(pv, c));
    // }
    // @Test public void testAssignmentDeclarationIntPass() throws ParseException {
    //     VarDeclaration v = getParser("int x;").VarDeclaration();
    //     Statement s = getParser("x = 3;").Statement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();
    //     assertEquals("valid", v.accept(pv, c));
    //     assertEquals("valid", s.accept(pv, c));
    // }
    // @Test public void testVarDeclarationInt() throws ParseException {
    //     VarDeclaration v = getParser("int x;").VarDeclaration();
    //     assertEquals("valid", v.accept(new PrimitiveVisitor(), new Context()));
    // }


    // @Test public void testArrayLookupPass() throws ParseException {
    //     VarDeclaration v = getParser("int[] x;").VarDeclaration();
    //     Statement a = getParser("x = new int[14];").Statement();
    //     Statement a1 = getParser("x[1] = 12;").Statement();
    //     Expression e = getParser("x.length").Expression();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));        
    //     assertEquals("valid", a.accept(pv, c));
    //     assertEquals("valid", a1.accept(pv, c));
    //     assertEquals("int", e.accept(pv, c));
    // }


    // @Test public void testExpressionListPass4() throws ParseException {
    //     VarDeclaration v = getParser("int x;").VarDeclaration();
    //     AssignmentStatement a = getParser("x = 2;").AssignmentStatement();
    //     VarDeclaration v1 = getParser("int y;").VarDeclaration();
    //     ExpressionList e = getParser("(x - y), (3 * 7), (2 + 3)").ExpressionList();
        
    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));    
    //     assertEquals("valid", a.accept(pv, c));    
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("int", e.accept(pv, c));
    // }
    // @Test public void testExpressionListFail() throws ParseException {
    //     VarDeclaration v = getParser("int[] x;").VarDeclaration();
    //     VarDeclaration v1 = getParser("int y;").VarDeclaration();
    //     ExpressionList e = getParser("x, y").ExpressionList();
        
    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));        
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("error", e.accept(pv, c));
    // }
    // @Test public void testExpressionListPass3() throws ParseException {
    //     VarDeclaration v = getParser("boolean x;").VarDeclaration();
    //     AssignmentStatement a = getParser("x = false;").AssignmentStatement();
    //     VarDeclaration v1 = getParser("boolean y;").VarDeclaration();
    //     ExpressionList e = getParser("x, y").ExpressionList();
        
    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));    
    //     assertEquals("valid", a.accept(pv, c));    
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("boolean", e.accept(pv, c));
    // }
    // @Test public void testExpressionListPass2() throws ParseException {
    //     VarDeclaration v = getParser("int[] x;").VarDeclaration();
    //     VarDeclaration v1 = getParser("int[] y;").VarDeclaration();
    //     ExpressionList e = getParser("x, y").ExpressionList();
        
    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));        
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("array", e.accept(pv, c));
    // }
    // @Test public void testExpressionListPass() throws ParseException {
    //     VarDeclaration v = getParser("int x;").VarDeclaration();
    //     VarDeclaration v1 = getParser("int y;").VarDeclaration();
    //     ExpressionList e = getParser("x, y").ExpressionList();
        
    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));        
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("int", e.accept(pv, c));
    // }


    // @Test public void testArrayAssignmentPass2() throws ParseException {
    //     VarDeclaration v = getParser("int[] x;").VarDeclaration();
    //     VarDeclaration v1 = getParser("int y;").VarDeclaration();
    //     Statement a = getParser("x = new int[14];").Statement();
    //     Statement a1 = getParser("x[1] = 2;").Statement();
    //     Statement a2 = getParser("y = x[1];").Statement();
        

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));        
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("valid", a.accept(pv, c));
    //     assertEquals("valid", a1.accept(pv, c));
    //     assertEquals("valid", a2.accept(pv, c));
    // }

    // @Test public void testArrayAssignmentPass() throws ParseException {
    //     VarDeclaration v = getParser("int[] x;").VarDeclaration();
    //     VarDeclaration v1 = getParser("int y;").VarDeclaration();        
    //     Statement a = getParser("x = new int[14];").Statement();
    //     Statement a1 = getParser("y = 3;").Statement();
    //     Statement a2 = getParser("x[1] = y;").Statement();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));        
    //     assertEquals("valid", v1.accept(pv, c));
    //     assertEquals("valid", a.accept(pv, c));
    //     assertEquals("valid", a1.accept(pv, c));
    //     assertEquals("valid", a2.accept(pv, c));
    // }

    // @Test public void testArrayLengthFail() throws ParseException {
    //     VarDeclaration v = getParser("int x;").VarDeclaration();
    //     Statement a = getParser("x = 14;").Statement();
    //     Expression e = getParser("x.length").Expression();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));        
    //     assertEquals("valid", a.accept(pv, c));
    //     assertEquals("error", e.accept(pv, c));
    // }
    // @Test public void testArrayLengthPass() throws ParseException {
    //     VarDeclaration v = getParser("int[] x;").VarDeclaration();
    //     Statement a = getParser("x = new int[14];").Statement();
    //     Expression e = getParser("x.length").Expression();

    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));        
    //     assertEquals("valid", a.accept(pv, c));
    //     assertEquals("int", e.accept(pv, c));
    // }



    // @Test public void testArrayAllocationFail() throws ParseException {
    //     ArrayAllocationExpression a = getParser("new int[false];").ArrayAllocationExpression();
    //     assertEquals("error", a.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testArrayAllocationPass() throws ParseException {
    //     ArrayAllocationExpression a = getParser("new int[14];").ArrayAllocationExpression();
    //     assertEquals("array", a.accept(new PrimitiveVisitor(), new Context()));
    // }



    // @Test public void testArrayType() throws ParseException {
    //     VarDeclaration v = getParser("int[] x;").VarDeclaration();
    //     Statement e = getParser("x = new int[3];").Statement();
        
    //     PrimitiveVisitor pv = new PrimitiveVisitor();
    //     Context c = new Context();

    //     assertEquals("valid", v.accept(pv, c));
    //     assertEquals("valid", e.accept(pv, c));
    // }
    // @Test public void testArrayDeclaration() throws ParseException {
    //     VarDeclaration v = getParser("int[] x;").VarDeclaration();
        
    //     assertEquals("valid", v.accept(new PrimitiveVisitor(), new Context()));
    // }


    // @Test public void testMath() throws ParseException {
    //     Expression e = getParser("1 + 1 - 5 * (8 + 1)").Expression();
    //     assertEquals("int", e.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testPlusExpr() throws ParseException {
    //     Expression e = getParser("1 + 1").Expression();
    //     assertEquals("int", e.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testBoolOpsr1() throws ParseException {
    //     Expression e = getParser("true && !false && ((4+5) < (7*5))").Expression();
    //     assertEquals("boolean", e.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testBoolOps() throws ParseException {
    //     Expression e = getParser("true && (!false && (4 < 7))").Expression();
    //     assertEquals("boolean", e.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testBoolFail() throws ParseException {
    //     Expression e = getParser("6 && (!false && (4 < 7))").Expression();
    //     assertEquals("error", e.accept(new PrimitiveVisitor(), new Context()));
    // }
    // // @Test public void testArrayLookupPass() throws ParseException {
    // //     Expression e = getParser("").Expression();
    // //     assertEquals("boolean", e.accept(new PrimitiveVisitor(), new Context()));
    // // }
    // @Test public void texstNotExprPass() throws ParseException {
    //     Expression e = getParser("!false").Expression();
    //     assertEquals("boolean", e.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testCompExprPass() throws ParseException {
    //     Expression e = getParser("2 < 1").Expression();
    //     assertEquals("boolean", e.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testCompExprFail() throws ParseException {
    //     Expression e = getParser("1 < false").Expression();
    //     assertEquals("error", e.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testMinusExprFail() throws ParseException {
    //     Expression e = getParser("1 - false").Expression();
    //     assertEquals("error", e.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testPlusExprPass() throws ParseException {
    //     Expression e = getParser("1 + 3 + 2").Expression();
    //     assertEquals("int", e.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testPlusExprFail() throws ParseException {
    //     Expression e = getParser("1 + true").Expression();
    //     assertEquals("error", e.accept(new PrimitiveVisitor(), new Context()));
    // }
    // @Test public void testFalseExpr() throws ParseException {
    //     Expression e = getParser("false").Expression();
    //     assertEquals("boolean", e.accept(new PrimitiveVisitor(), new Context()));
    // }
}