package anaydis.search;


import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

public class TriesTest {

    final String[] keys = {"monkey", "money"};//, "theater", "mandala"};

    @Test
    public void RWayTrieMapTest(){
        RWayTrieMap<Integer> rWay = new RWayTrieMap<>();

        Iterator<String> wordsIter = Arrays.stream(keys).iterator();
        int value = 0;
        while (wordsIter.hasNext()){
            rWay.put(wordsIter.next(), value++);
        }

        rWay.put("El", value++);


        assertThat(rWay.containsKey("monkey")).isTrue();
        assertThat(rWay.containsKey("mon")).isFalse();

        wordsIter = Arrays.stream(keys).iterator();
        value = 0;
        while (wordsIter.hasNext()){
            assertThat(rWay.get(wordsIter.next())).isEqualTo(value++);
        }
    }

    @Test
    public void BinaryTrieMapTest() {
        BinaryTrieMap<Integer> binary = new BinaryTrieMap<>();

        Iterator<String> wordsIter = Arrays.stream(keys).iterator();
        int value = 0;
        while (wordsIter.hasNext()) {
            binary.put(wordsIter.next(), value++);
        }


        assertThat(binary.containsKey("monkey")).isTrue();
        assertThat(binary.containsKey("mon")).isFalse();

//        wordsIter = Arrays.stream(keys).iterator();
//        value = 0;
//        while (wordsIter.hasNext()){
//            assertThat(binary.get(wordsIter.next())).isEqualTo(value++);
//        }
    }


}