package anaydis.priorityQueue.PriorityQueueBenchmark;

public class Iteration {
    private final Scene scene;
    private final Stats stats;

    public Iteration(Scene scene, Stats stats) {
        this.scene = scene;
        this.stats = stats;
    }

    public Scene getScene() {
        return scene;
    }

    public Stats getStats() {
        return stats;
    }
}

