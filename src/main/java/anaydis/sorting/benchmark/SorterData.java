package anaydis.sorting.benchmark;

import anaydis.sort.Sorter;

public class SorterData {
    private BenchmarkRunCase runCase;
    private int size;
    private Sorter sorter;


    public SorterData(Sorter sorter, BenchmarkRunCase runCase, int size) {
        this.sorter = sorter;
        this.runCase = runCase;
        this.size = size;
    }


    public BenchmarkRunCase getRunCase() {
        return runCase;
    }

    public int getSize() {
        return size;
    }

    public Sorter getSorter() {
        return sorter;
    }
}
