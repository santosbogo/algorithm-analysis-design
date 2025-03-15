package anaydis.priorityQueue.PriorityQueueBenchmark;
public class Stats {
    private final long insertionTime;
    private final long removalTime;

    public Stats(long insertionTime, long removalTime) {
        this.insertionTime = insertionTime;
        this.removalTime = removalTime;
    }

    // Getters
    public long getInsertionTime() {
        return insertionTime;
    }

    public long getRemovalTime() {
        return removalTime;
    }
}
