/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    // TODO

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalid() {
        Expression.parse("!haha");
    }

    @Test
    public void testParseEquals1() {
        Object expr1 = Expression.parse("z * z");
        Object expr2 = Expression.parse("z * z");
        assertEquals("Expected identical expressions to be equal", expr1, expr2);
    }

    @Test
    public void testParseEquals2() {
        Object expr1 = Expression.parse("z");
        Object expr2 = Expression.parse("z");
        assertEquals("Expected identical expressions to be equal", expr1, expr2);
    }

    @Test
    public void testParseEquals3() {
        Object expr1 = Expression.parse("z * z * z");
        Object expr2 = Expression.parse("z * z * z");
        assertEquals("Expected identical expressions to be equal", expr1, expr2);
    }

    @Test
    public void testParseEquals4() {
        Object expr1 = Expression.parse("(2+3)*4");
        Object expr2 = Expression.parse("2+3*4");
        assertNotEquals(expr1, expr2);
    }

    @Test
    public void testParseToString1() {
        Expression expr = Expression.parse("2 + 3");
        String out = expr.toString();
        assertEquals("(after removing whitespace, parens, and \"\\.0*\" from actual output \"" + out + "\")", "2+3",
                out.replaceAll("[ ()]", "").replaceAll("\\.0*", ""));
    }

    @Test
    public void testParseToString2() {
        Expression expr = Expression.parse("2.1 + 3");
        String out = expr.toString();
        assertEquals("2.1 + 3.0", out);
    }

    @Test
    public void testParseToString3() {
        Expression expr = Expression.parse("a + b");
        String out = expr.toString();
        assertEquals("(after removing whitespace, parens, and \"\\.0*\" from actual output \"" + out + "\")", "a+b",
                out.replaceAll("[ ()]", "").replaceAll("\\.0*", ""));
    }

    @Test
    public void testParseToString4() {
        Expression expr = Expression.parse("(2) + (3)");
        String out = expr.toString();
        assertEquals("2.0 + 3.0", out);
    }

    @Test
    public void testParseToString5() {
        Expression expr = Expression.parse("b * b * b");
        String out = expr.toString();
        assertEquals("b * b * b", out);
    }

    @Test
    public void testParseToString6() {
        Expression expr = Expression.parse("2 * b + c");
        String out = expr.toString();
        assertEquals("2.0 * b + c", out);
    }

    @Test
    public void testParseToString7() {
        Expression expr = Expression.parse("(((     x)   ))");
        String out = expr.toString();
        assertEquals("x", out);
    }

    @Test
    public void testDifferentiate1() {
        Expression expr = Expression.parse("x");
        Expression derivative = expr.differentiate("x");
        assertEquals(Expression.parse("1"), derivative);
    }

    @Test
    public void testDifferentiate2() {
        Expression expr = Expression.parse("1");
        Expression derivative = expr.differentiate("x");
        assertEquals(Expression.parse("0"), derivative);
    }

    @Test
    public void testDifferentiate3() {
        Expression expr = Expression.parse("x * x");
        Expression derivative = expr.differentiate("x");
        assertEquals(Expression.parse("x * 1.0 + x * 1.0"), derivative);
    }

    @Test
    public void testSimplify1() {
        Map<String, Double> environment = new HashMap<String, Double>();
        Expression expr = Expression.parse("x");
        environment.put("x", 2.0);
        Expression simple = expr.simplify("x", environment);
        assertEquals(Expression.parse("2.0"), simple);
    }

    @Test
    public void testSimplify2() {
        Map<String, Double> environment = new HashMap<String, Double>();
        Expression expr = Expression.parse("x + x");
        environment.put("x", 2.0);
        Expression simple = expr.simplify("x * 1.0 + x * 1.0", environment);
        assertEquals(Expression.parse("4"), simple);
    }

    @Test
    public void testSimplify3() {
        Map<String, Double> environment = new HashMap<String, Double>();
        Expression expr = Expression.parse("x * x");
        environment.put("x", 3.0);
        Expression simple = expr.simplify("x * x", environment);
        assertEquals(Expression.parse("9"), simple);
    }

    @Test
    public void testSimplify4() {
        Map<String, Double> environment = new HashMap<String, Double>();
        Expression expr = Expression.parse("x * x * x");
        environment.put("x", 2.0);
        Expression simple = expr.simplify("x * x * x", environment);
        assertEquals(Expression.parse("8"), simple);
    }

    @Test
    public void testSimplifyTwice() {
        Map<String, Double> environment = new HashMap<String, Double>();
        environment.put("x", 2.0);
        environment.put("y", 5.0);
        String partiallySimple = Commands.simplify("x + z", environment);
        Map<String, Double> environmentZ = new HashMap<String, Double>();
        environmentZ.put("z", 7.0);
        String completelySimple = Commands.simplify(partiallySimple, environmentZ);
        assertEquals(
                "(from " + partiallySimple + " with z = " + environmentZ.get("z")
                        + " after removing whitespace and \"\\.0*\" from actual output \"" + completelySimple + "\")",
                "9", completelySimple.replace(" ", "").replaceAll("\\.0*", ""));
    }

    @Test
    public void testToStringConsistent() {
        Object a = Expression.parse("((3 * 5) + 7) * 2");
        Object b = Expression.parse("3 * 5 + 7 * 2");
//        assertFalse(a.toString().equals(b.toString()));
        assertEquals(a, b);

    }
}
