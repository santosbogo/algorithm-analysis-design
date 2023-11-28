package anaydis.sort.Benchmark;

import anaydis.immutable.List;
import anaydis.sort.*;

public class BenchmarkMain {
    public static void main(String[] args) {
        int[] sizes = {500, 12500, 25000, 50000, 100000};
        final SorterProviderSort sorterProvider = new SorterProviderSort();
        final DataSetGenerator<Integer> dataSetGenerator = new IntegerDataSetGenerator();
        final BenchmarkRunCase[] runCases = BenchmarkRunCase.values();
        final BenchmarkRunner runner = new BenchmarkRunner();

        final Sorter[] sorters = {//sorterProvider.getSorterForType(SorterType.QUICK_NON_RECURSIVE),
//                sorterProvider.getSorterForType(SorterType.QUICK),
//                sorterProvider.getSorterForType(SorterType.QUICK_CUT),
                sorterProvider.getSorterForType(SorterType.QUICK_MED_OF_THREE)};
//                sorterProvider.getSorterForType(SorterType.QUICK_THREE_PARTITION)};

        //GOOD FOR EXCEL:
        System.out.println("Run case;N;Swaps;Comparisons;Time(ms)");
        for (Sorter sorter : sorters) {
            System.out.println("\n" + sorter.getType() + "\n");
            for (BenchmarkRunCase runCase : runCases) {
                System.out.println(runCase);
                for (int size : sizes) {
                    final SorterData sorterData = new SorterData(sorter, runCase, size);
                    final BenchmarkResult result = runner.run(sorterData, dataSetGenerator);
                    System.out.println(size + ";" +
                            result.getSwaps() + ";" +
                            result.getComparisons() + ";" +
                            result.getTime());
                }
            }
        }
    }

        // GOOD FOR READING:
//        for(Sorter sorter: sorterProvider.getAllSorters()){
//            System.out.println(sorter.getType());
//            for (int size: sizes){
//                System.out.println("\nFor " + size + " elements:");
//                for (BenchmarkRunCase runCase: runCases){
//                    final SorterData sorterData = new SorterData(sorter, runCase, size);
//                    final BenchmarkResult result = runner.run(sorterData, dataSetGenerator);
//                    System.out.println(runCase + ": Swaps = " +
//                            result.getSwaps() + " \tComparisons = " +
//                            result.getComparisons() + "\tTime (ms) = " +
//                            result.getTime());
//                }
//            }
//        }


//        //GOOD FOR EXCEL:
//        System.out.println("Run case;Swaps;Comparisons;Time(ms)");
//        for(Sorter sorter: sorterProvider.getAllSorters()){
//            System.out.println(sorter.getType());
//            for (int size: sizes){
//                System.out.println("Elements:;" + size);
//                for (BenchmarkRunCase runCase: runCases){
//                    final SorterData sorterData = new SorterData(sorter, runCase, size);
//                    final BenchmarkResult result = runner.run(sorterData, dataSetGenerator);
//                    System.out.println(runCase + ";" +
//                            result.getSwaps() + " ;" +
//                            result.getComparisons() + ";" +
//                            result.getTime());
//                }
//            }
//        }
    }