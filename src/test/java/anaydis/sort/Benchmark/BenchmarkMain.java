package anaydis.sort.Benchmark;

import anaydis.sort.DataSetGenerator;
import anaydis.sort.IntegerDataSetGenerator;
import anaydis.sort.Sorter;
import anaydis.sort.SorterProviderSort;

public class BenchmarkMain {
    public static void main(String[] args){
        final int[] sizes ={10,50,500,1000, 5000, 10000, 100000};
        final SorterProviderSort sorterProvider = new SorterProviderSort();
        final DataSetGenerator<Integer> dataSetGenerator = new IntegerDataSetGenerator();
        final BenchmarkRunCase[]  runCases= BenchmarkRunCase.values();
        final BenchmarkRunner runner = new BenchmarkRunner();
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


        //GOOD FOR EXCEL:
        System.out.println("Run case;Swaps;Comparisons;Time(ms)");
        for(Sorter sorter: sorterProvider.getAllSorters()){
            System.out.println(sorter.getType());
            for (int size: sizes){
                System.out.println("Elements:;" + size);
                for (BenchmarkRunCase runCase: runCases){
                    final SorterData sorterData = new SorterData(sorter, runCase, size);
                    final BenchmarkResult result = runner.run(sorterData, dataSetGenerator);
                    System.out.println(runCase + ";" +
                            result.getSwaps() + " ;" +
                            result.getComparisons() + ";" +
                            result.getTime());
                }
            }
        }
    }
}