package anaydis.sort.benchmark;

public class BenchmarkResult {
    private long comparisons;
    private long swaps;
    private long time;

    BenchmarkResult(long time, long comparisons, long swaps){
        this.comparisons=comparisons;
        this.swaps=swaps;
        this.time = time;
    }

    long getComparisons(){
        return comparisons;
    }
    long getSwaps(){
        return swaps;
    }
    long getTime(){
        return time;
    }
}
