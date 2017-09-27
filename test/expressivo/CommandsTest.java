/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy
    // TODO

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testDifferentiate1() {
        String deriv = Commands.differentiate("x", "x");
        assertEquals(
                "(after removing whitespace, parens, \"\\.0*\", and permutations of \"\\+0\\*x\" from actual output \""
                        + deriv + "\")",
                "1", deriv.replaceAll("[ ()]", "").replaceAll("\\.0*", "")
                        .replaceFirst("\\+0\\*x|\\+x\\*0|\\+0|0\\*x\\+|x\\*0\\+|0\\+", ""));
    }

}
