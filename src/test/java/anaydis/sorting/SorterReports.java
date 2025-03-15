package anaydis.sorting;

import java.util.Comparator;
import java.util.List;

import anaydis.sort.DataSetGenerator;
import anaydis.sort.Sorter;
import org.junit.Test;

public class SorterReports {

    @Test
    public void testBubbleSort() {
        System.out.println("\nBubble:\n");
        testSorter(new SelectionSorter());
    }

    @Test
    public void testInsertionSort() {
        System.out.println("\nInsertion:\n");
        testSorter(new InsertionSorter());
    }

    @Test
    public void testSelectionSort() {
        System.out.println("\nSelection:\n");
        testSorter(new SelectionSorter());
    }


    private void testSorter(Sorter sorter) {
        Comparator comparator = Comparator.naturalOrder();
        DataSetGenerator<Integer> dataSetGenerator = new IntegerDataSetGenerator();

        int[] datasetSizes = {10, 50, 500, 1000, 5000};

        for (int n : datasetSizes) {
            List<Integer> ascendingData = dataSetGenerator.createAscending(n);
            List<Integer> descendingData = dataSetGenerator.createDescending(n);
            List<Integer> randomData = dataSetGenerator.createRandom(n);

            long startTime, endTime;

            // Measure execution time for ascending data
            startTime = System.nanoTime();
            sorter.sort(comparator, ascendingData);
            endTime = System.nanoTime();
            long ascendingTime = endTime - startTime;

            // Measure execution time for descending data
            startTime = System.nanoTime();
            sorter.sort(comparator, descendingData);
            endTime = System.nanoTime();
            long descendingTime = endTime - startTime;

            // Measure execution time for random data
            startTime = System.nanoTime();
            sorter.sort(comparator, randomData);
            endTime = System.nanoTime();
            long randomTime = endTime - startTime;

            System.out.println(ascendingTime);
            System.out.println(descendingTime);
            System.out.println(randomTime);
        }
    }
}
