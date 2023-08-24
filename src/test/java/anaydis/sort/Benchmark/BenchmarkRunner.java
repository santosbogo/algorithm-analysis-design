package anaydis.sort.Benchmark;

import anaydis.sort.DataSetGenerator;
import anaydis.sort.gui.ObservableSorter;
import anaydis.sort.listener.StatsSorterListener;

import java.util.List;

public class BenchmarkRunner {
    <T> BenchmarkResult run (final SorterData sorterData, final DataSetGenerator<T> dataSetGenerator){
        final List<T> list = getList(dataSetGenerator, sorterData.getRunCase(), sorterData.getSize());
        final ObservableSorter sorter = (ObservableSorter) sorterData.getSorter();
        final StatsSorterListener stats = new StatsSorterListener();
        sorter.addSorterListener(stats);
        long startTime = System.nanoTime();
        sorter.sort(dataSetGenerator.getComparator(), list);
        long time = (System.nanoTime()-startTime)/1000;
        sorter.removeSorterListener(stats);
        return new BenchmarkResult(time, stats.getSwaps(), stats.getComparisons());
    }

    private <T> List<T> getList(final DataSetGenerator<T> dataSetGenerator, final BenchmarkRunCase benchmarkRunCase, final int size){
        switch(benchmarkRunCase){
            case ASC: return dataSetGenerator.createAscending(size);
            case DESC: return dataSetGenerator.createDescending(size);
            case RANDOM: return dataSetGenerator.createRandom(size);
        }
        throw new NullPointerException("Wrong Case Run");

    }
}
