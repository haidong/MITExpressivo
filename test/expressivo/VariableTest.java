package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

public class VariableTest {

    @Test
    public void testToString1() {
        Variable v = new Variable("a");
        assertEquals("a", v.toString());
    }

    @Test
    public void testToString2() {
        Variable v = new Variable("Foo");
        assertEquals("Foo", v.toString());
    }

    @Test
    public void testEquals1() {
        Variable v = new Variable("Foo");
        Variable w = new Variable("Foo");
        assertTrue(v.equals(w));
    }

    @Test
    public void testHashCode1() {
        Variable v = new Variable("Foo");
        assertEquals("Foo".hashCode(), v.hashCode());
    }
}
