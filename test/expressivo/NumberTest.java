package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberTest {

    @Test
    public void testToString1() {
        Number n = new Number(3);
        assertEquals("3.0", n.toString());
    }

    @Test
    public void testToString2() {
        Number n = new Number(1.2345);
        assertEquals("1.2345", n.toString());
    }

    @Test
    public void testEquals1() {
        Number n = new Number(1.2345);
        Number m = new Number(1.23450);
        assertTrue(n.equals(m));
    }

    @Test
    public void testEquals2() {
        Number n = new Number(1.2346);
        Number m = new Number(1.2345);
        assertFalse(n.equals(m));
    }

    @Test
    public void testHashCode1() {
        Number n = new Number(1.2345);
        assertEquals(765286158, n.hashCode());
    }

    @Test
    public void testHashCode2() {
        Number n = new Number(5);
        assertEquals(1075052544, n.hashCode());
    }

}
