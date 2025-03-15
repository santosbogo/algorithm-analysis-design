package anaydis.sorting;

import anaydis.animation.sort.gui.Main;

public class Animation {
    public static void main(String[] args) {
        Main.animate(new SorterProviderSort());
        //Main.race(new SorterProviderSort());
    }
}