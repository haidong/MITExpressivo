package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlusTest {
    private final Number n1 = new Number(1);
    private final Number n2 = new Number(3.5);
    private final Variable v1 = new Variable("a");
    private final Variable v2 = new Variable("Foo");

    @Test
    public void testToString1() {
        Plus p = new Plus(n1, n2);
        assertEquals("1.0 + 3.5", p.toString());
    }

    @Test
    public void testToString2() {
        Plus p = new Plus(n1, v1);
        assertEquals("1.0 + a", p.toString());
    }

    @Test
    public void testToString3() {
        Plus p = new Plus(v2, v1);
        assertEquals("Foo + a", p.toString());
    }

    @Test
    public void testEquals1() {
        Plus p1 = new Plus(n1, n2);
        assertTrue(p1.equals(p1));
    }

    @Test
    public void testEquals2() {
        Plus p1 = new Plus(n1, n2);
        Plus p2 = new Plus(n1, n2);
        assertTrue(p1.equals(p2));
    }

    @Test
    public void testEquals3() {
        Plus p1 = new Plus(n1, n2);
        assertFalse(p1.equals(n1));
    }

    @Test
    public void testEquals4() {
        Plus p1 = new Plus(n1, n2);
        Plus p2 = new Plus(n2, n1);
        assertFalse(p1.equals(p2));
    }

    @Test
    public void testHashCode1() {
        Plus p1 = new Plus(n1, n2);
        assertEquals(2147221615, p1.hashCode());
    }

    @Test
    public void testHashCode2() {
        Plus p1 = new Plus(n1, v2);
        assertEquals(1072764181, p1.hashCode());
    }

}
