package anaydis.sort.Benchmark;

import anaydis.sort.*;

public class BenchmarkMain {
    public static void main(String[] args) {
        int[] sizes = {500, 12500, 25000, 50000, 100000};
        final SorterProviderSort sorterProvider = new SorterProviderSort();
        final DataSetGenerator<Integer> dataSetGenerator = new IntegerDataSetGenerator();
        final BenchmarkRunCase[] runCases = BenchmarkRunCase.values();
        final BenchmarkRunner runner = new BenchmarkRunner();

        for (Sorter sorter : sorterProvider.getAllSorters()) {
            System.out.println(sorter.getType());
            for (int size : sizes) {
                System.out.println("\nFor " + size + " elements:");
                for (BenchmarkRunCase runCase : runCases) {
                    final SorterData sorterData = new SorterData(sorter, runCase, size);
                    final BenchmarkResult result = runner.run(sorterData, dataSetGenerator);
                    System.out.println(runCase + ": Swaps = " +
                            result.getSwaps() + " \tComparisons = " +
                            result.getComparisons() + "\tTime (ms) = " +
                            result.getTime());
                }
            }
        }
    }
}