package anaydis.string;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class RabinKarpTest {

    @Test
    public void testCount() {
        RabinKarp searcher = new RabinKarp("banana");
        assertEquals(2, searcher.count("ana"));
    }

    @Test
    public void testFirst() {
        RabinKarp searcher = new RabinKarp("hello world");
        assertEquals(0, searcher.first("hello"));
        assertEquals(6, searcher.first("world"));
        assertEquals(-1, searcher.first("earth"));
    }

    @Test
    public void testAll() {
        RabinKarp searcher = new RabinKarp("banana");
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
        RabinKarp searcher = new RabinKarp("text");
        assertEquals(StringSearcherType.RABIN_KARP, searcher.getType());
    }

    @Test
    public void testText() {
        RabinKarp searcher = new RabinKarp("apple");
        assertEquals("apple", searcher.text());
    }

    @Test
    public void testContains() {
        RabinKarp searcher = new RabinKarp("apple");
        assertTrue(searcher.contains("app"));
        assertFalse(searcher.contains("banana"));
    }
}
