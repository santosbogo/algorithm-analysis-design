package anaydis.sort;

import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sorter tests should subclass this abstract implementation
 */
abstract class SorterTest extends AbstractSorterTest {

    @Override protected DataSetGenerator<String> createStringDataSetGenerator() {
        return new StringDataSetGenerator();
    }

    @Override protected DataSetGenerator<Integer> createIntegerDataSetGenerator() {
        return new IntegerDataSetGenerator();
    }

    @Override protected SorterProvider getSorterProvider() {
        return new SorterProviderSort();
    }
    @Test
    public void QuickSorterRandomOrderTest(){
        List<Integer> random = new IntegerDataSetGenerator().createRandom(100);

        //Sort with Java.util that i am sure it works
        List<Integer> javaRandom = random;
        javaRandom.sort(Comparator.naturalOrder());
        Iterator<Integer> javaIter = javaRandom.iterator();

        //Now sort with the method i want to test
        QuickSorter sorter = new QuickSorter();
        sorter.sort(Comparator.naturalOrder(), random);

        //Compare the results
        for (Integer integer : random) {
            assertThat(integer).isEqualTo(javaIter.next());
        }
    }



    @Test
    public void QuickSorterNonRecursiveRandomOrderTest(){
        List<Integer> random = new IntegerDataSetGenerator().createRandom(100);

        //Sort with Java.util that i am sure it works
        List<Integer> javaRandom = random;
        javaRandom.sort(Comparator.naturalOrder());
        Iterator<Integer> javaIter = javaRandom.iterator();

        //Now sort with the method i want to test
        QuickSorterNonRecursive sorter = new QuickSorterNonRecursive();
        sorter.sort(Comparator.naturalOrder(), random);

        //Compare the results
        for (Integer integer : random) {
            assertThat(integer).isEqualTo(javaIter.next());
        }
    }

    @Test
    public void HybridSorterRandomRandomOrderTest(){
        List<Integer> random = new IntegerDataSetGenerator().createRandom(100);

        //Sort with Java.util that i am sure it works
        List<Integer> javaRandom = random;
        javaRandom.sort(Comparator.naturalOrder());
        Iterator<Integer> javaIter = javaRandom.iterator();

        //Now sort with the method i want to test
        HybridQuickSorter sorter = new HybridQuickSorter();
        sorter.sort(Comparator.naturalOrder(), random);

        //Compare the results
        for (Integer integer : random) {
            assertThat(integer).isEqualTo(javaIter.next());
        }
    }

    @Test
    public void QuickSorterMedianOfThreeRandomOrderTest(){
        List<Integer> random = new IntegerDataSetGenerator().createRandom(100);

        //Sort with Java.util that i am sure it works
        List<Integer> javaRandom = random;
        javaRandom.sort(Comparator.naturalOrder());
        Iterator<Integer> javaIter = javaRandom.iterator();

        //Now sort with the method i want to test
        QuickSorterMedianOfThree sorter = new QuickSorterMedianOfThree();
        sorter.sort(Comparator.naturalOrder(), random);

        //Compare the results
        for (Integer integer : random) {
            assertThat(integer).isEqualTo(javaIter.next());
        }
    }

    @Test
    public void QuickSorterThreeWayPartitioningRandomOrderTest(){
        List<Integer> random = new IntegerDataSetGenerator().createRandom(100);

        //Sort with Java.util that i am sure it works
        List<Integer> javaRandom = random;
        javaRandom.sort(Comparator.naturalOrder());
        Iterator<Integer> javaIter = javaRandom.iterator();

        //Now sort with the method i want to test
        QuickSorterThreeWayPartitioning sorter = new QuickSorterThreeWayPartitioning();
        sorter.sort(Comparator.naturalOrder(), random);

        //Compare the results
        for (Integer integer : random) {
            assertThat(integer).isEqualTo(javaIter.next());
        }
    }

    @Test
    public void BottomUpMergeSorterRandomOrderTest(){
        List<Integer> random = new IntegerDataSetGenerator().createRandom(100);

        //Sort with Java.util that i am sure it works
        List<Integer> javaRandom = random;
        javaRandom.sort(Comparator.naturalOrder());
        Iterator<Integer> javaIter = javaRandom.iterator();

        //Now sort with the method i want to test
        BottomUpMergeSorter sorter = new BottomUpMergeSorter();
        sorter.sort(Comparator.naturalOrder(), random);

        //Compare the results
        for (Integer integer : random) {
            assertThat(integer).isEqualTo(javaIter.next());
        }
    }

    @Test
    public void TopDownMergeSorterRandomOrderTest(){
        List<Integer> random = new IntegerDataSetGenerator().createRandom(100);

        //Sort with Java.util that i am sure it works
        List<Integer> javaRandom = random;
        javaRandom.sort(Comparator.naturalOrder());
        Iterator<Integer> javaIter = javaRandom.iterator();

        //Now sort with the method i want to test
        TopDownMergeSorter sorter = new TopDownMergeSorter();
        sorter.sort(Comparator.naturalOrder(), random);

        //Compare the results
        for (Integer integer : random) {
            assertThat(integer).isEqualTo(javaIter.next());
        }
    }
}
