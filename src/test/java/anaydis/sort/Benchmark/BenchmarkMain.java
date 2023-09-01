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

        System.out.println("Type:\t|\tSize\t|\tRun Case\t|\tSwaps\t|\tComparisons\t|\tTime (microseconds) ");
        for(int size: sizes){
            for (Sorter sorter: sorterProvider.getAllSorters()){
                for (BenchmarkRunCase runCase: runCases){
                    final SorterData sorterData = new SorterData(sorter, runCase, size);
                    final BenchmarkResult result = runner.run(sorterData, dataSetGenerator);
                    System.out.println(sorterData.getSorter().getType() + "\t|\t" +
                            size + "\t|\t" +
                            runCase + "\t|\t" +
                            result.getSwaps() + "\t|\t" +
                            result.getComparisons() + "\t|\t" +
                            result.getTime());
                    System.out.println();
                }
            }
        }

    }
}