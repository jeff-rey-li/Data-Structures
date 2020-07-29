package li.jeffrey.binarytrees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBinarySearchTree {

    BinarySearchTree<Integer> test;

    @BeforeEach
    public void setup() {
        test = new BinarySearchTree<Integer>();
    }

    @Test
    public void testAdd() {
        assertTrue(test.add(0));
        assertTrue(test.add(5));
        assertTrue(test.add(-1));
        assertFalse(test.add(0));
    }

    @Test
    public void testRemove() {
        test.add(0);
        test.add(2);
        test.add(-1);
        assertTrue(test.remove(0));
        assertFalse(test.remove(0));
        assertTrue(test.remove(-1));
        assertTrue(test.remove(2));
    }

    @Test
    public void testContains() {
        test.add(0);
        test.add(5);
        test.add(10);
        assertTrue(test.contains(5));
        assertTrue(test.contains(10));
        assertTrue(test.contains(0));
        test.remove(5);
        assertFalse(test.contains(5));
    }

    @Test
    public void testSize() {
        assertEquals(test.size(), 0);
        test.add(0);
        test.add(5);
        test.add(-5);
        assertEquals(test.size(), 3);
        test.remove(0);
        test.remove(100);
        assertEquals(test.size(), 2);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(test.isEmpty());
        test.add(0);
        test.add(3);
        test.add(-1);
        assertFalse(test.isEmpty());
        test.remove(0);
        test.remove(3);
        assertFalse(test.isEmpty());
        test.remove(-1);
        assertTrue(test.isEmpty());
    }

    @Test
    public void testHeight() {
        assertEquals(test.height(), 0);
        test.add(0);
        assertEquals(test.height(), 1);
        test.add(5);
        test.add(10);
        test.add(15);
        test.add(20);
        assertEquals(test.height(), 5);
        test.add(-5);
        assertEquals(test.height(), 5);
        test.remove(10);
        assertEquals(test.height(), 4);
    }

}
