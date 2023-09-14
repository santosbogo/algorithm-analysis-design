package anaydis.search.benchmark;

public class Stats {
    private final long time;
    private final int misses;
    private final int successes;


    public Stats(long time, int misses, int successes){
        this.time = time;
        this.misses = misses;
        this.successes = successes;
    }

    public long getTime(){
        return time;
    }

    public int getMisses(){
        return misses;
    }

    public int getSuccesses(){
        return successes;
    }

    public String toString(){
        return "Successes= " + successes + " Misses= " + misses + " Time= " + time;
    }
}
