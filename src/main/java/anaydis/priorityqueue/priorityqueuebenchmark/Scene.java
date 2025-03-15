package anaydis.priorityqueue.priorityqueuebenchmark;

import anaydis.search.PriorityQueue;

public class Scene {
    private final int size;
    private final PriorityQueue<Integer> PQ;

    public Scene(int size, PriorityQueue<Integer> PQ){
        this.size = size;
        this.PQ = PQ;
    }

    public PriorityQueue<Integer> getPQ() {
        return PQ;
    }

    public int getSize() {
        return size;
    }
}
