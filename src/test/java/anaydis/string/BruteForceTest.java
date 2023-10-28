package anaydis.string;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class BruteForceTest {

    @Test
    public void testCount() {
        BruteForce searcher = new BruteForce("banana");
        assertEquals(2, searcher.count("ana"));
    }

    @Test
    public void testFirst() {
        BruteForce searcher = new BruteForce("hello world");
        assertEquals(0, searcher.first("hello"));
        assertEquals(6, searcher.first("world"));
        assertEquals(-1, searcher.first("earth"));
    }

    @Test
    public void testAll() {
        BruteForce searcher = new BruteForce("banana");
        Iterator<Integer> iterator = searcher.all("ana");
        List<Integer> expectedIndices = Arrays.asList(1, 3);
        int index = 0;

        while (iterator.hasNext()) {
            assertEquals(expectedIndices.get(index++), iterator.next());
        }
        assertEquals(expectedIndices.size(), index);
    }

    @Test
    public void testGetType() {
        BruteForce searcher = new BruteForce("text");
        assertEquals(StringSearcherType.BRUTE_FORCE, searcher.getType());
    }

    @Test
    public void testText() {
        BruteForce searcher = new BruteForce("apple");
        assertEquals("apple", searcher.text());
    }

    @Test
    public void testContains() {
        BruteForce searcher = new BruteForce("apple");
        assertTrue(searcher.contains("app"));
        assertFalse(searcher.contains("banana"));
    }
}
