package li.jeffrey.binarytrees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRedBlackTree {

    RedBlackTree<Integer> test;

    @BeforeEach
    public void setup() {
        test = new RedBlackTree<Integer>();
    }

    @Test
    public void testInsert() {
        assertTrue(test.insert(0));
        assertTrue(test.insert(5));
        assertTrue(test.insert(-1));
        assertFalse(test.insert(0));
    }

    @Test
    public void testContains() {
        assertFalse(test.contains(5));
        test.insert(0);
        test.insert(5);
        test.insert(10);
        assertTrue(test.contains(5));
        assertTrue(test.contains(10));
        assertTrue(test.contains(0));
    }

    @Test
    public void testSize() {
        assertEquals(test.size(), 0);
        test.insert(0);
        test.insert(5);
        test.insert(-5);
        assertEquals(test.size(), 3);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(test.isEmpty());
        test.insert(0);
        test.insert(3);
        test.insert(-1);
        assertFalse(test.isEmpty());
    }

    @Test
    public void testHeight() {
        assertEquals(test.height(), 0);
        test.insert(0);
        assertEquals(test.height(), 1);
        test.insert(1);
        assertEquals(test.height(), 2);
        test.insert(2);
        assertEquals(test.height(), 2);
        test.insert(4);
        assertEquals(test.height(), 3);
        test.insert(3);
        assertEquals(test.height(), 3);
    }

}
