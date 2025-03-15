package anaydis.search.benchmark;

public class Iteration {
    private final Scene scene;
    private final Stats stats;

    public Iteration(Scene scene, Stats stats){
        this.scene = scene;
        this.stats = stats;
    }

    public String toString(){
        return scene.toString() + " " + stats.toString();
    }
}
