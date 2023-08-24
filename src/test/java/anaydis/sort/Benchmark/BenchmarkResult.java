package anaydis.sort.Benchmark;

public class BenchmarkResult {
    private int comparisons;
    private int swaps;
    private long time;

    BenchmarkResult(long time, int comparisons, int swaps){
        this.comparisons=comparisons;
        this.swaps=swaps;
        this.time = time;
    }

    int getComparisons(){
        return comparisons;
    }
    int getSwaps(){
        return swaps;
    }
    long getTime(){
        return time;
    }
}
