package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class for {@link MyMap} class
 * <p>This class provides test cases to basic functions of the Map implementation.</p>
 */
class MyMapTest {
    private MyMap<String, Integer> m;

    /**
     * Sets up a new MyMap before every test.
     */
    @BeforeEach
    public void setUp() {m = new MyMap();}

    /**
     * Tests if put function add a key with proper value.
     */
    @Test
    void put(){
        m.put("Czekolada", 13);
        m.put("pasta", 10);
        assertEquals(13, (int) m.get("Czekolada"));
        assertEquals(10, (int) m.get("pasta"));
        assertTrue(m.put("Czekolada", 20));
        assertTrue(m.put("polska", 21));
    }

    /**
     * Tests if the remove function removes correct element and it actually disappears.
     */
    @Test
    void remove(){
        m.put("Czekolada", 13);
        m.put("pasta", 10);
        m.put("makaron", 21);
        m.remove("Czekolada");
        assertEquals(10, (int) m.get("pasta"));
        assertTrue(m.remove("makaron"));
        assertFalse(m.remove("Polska"));
        assertNull(m.get("Czekolada"));
    }

    /**
     * Tests get function and if it returns correct value.
     */
    @Test
    void get(){
        m.put("Czekolada", 13);
        m.put("pasta", 10);
        assertEquals(13, (int) m.get("Czekolada"));
    }

    /**
     * Tests if function contains returns correct value depend on whether a key is in a map or not.
     */
    @Test
    void contains(){
        m.put("Czekolada", 13);
        m.put("pasta", 10);
        m.put("ser", 11);
        assertTrue(m.contains("Czekolada"));
        assertTrue(m.contains("ser"));
        assertFalse(m.contains("past"));
    }

    /**
     * Tests if a function keys returns correct List of keys
     */
    @Test
    void keys() {
        m.put("Czekolada", 13);
        m.put("pasta", 10);
        m.put("ser", 11);
        assertEquals(3, m.keys().size());
        assertTrue(m.keys().contains("Czekolada"));
        assertTrue(m.keys().contains("ser"));
        assertFalse(m.keys().contains("past"));
    }
}