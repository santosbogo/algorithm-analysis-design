package anaydis.sort;

import anaydis.animation.sort.gui.Main;

public class Animation {
    static void main(String[] args) {
        Main.animate(new SorterProviderSort());
        Main.race(new SorterProviderSort());
    }
}