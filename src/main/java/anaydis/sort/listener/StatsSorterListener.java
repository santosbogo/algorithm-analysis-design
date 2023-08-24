package anaydis.sort.listener;

import anaydis.sort.gui.SorterListener;

public class StatsSorterListener implements SorterListener {
    private int comp = 0;
    private int swap = 0;

    @Override
    public void box(int from, int to) {

    }

    @Override
    public void copy(int from, int to, boolean copyToAux) {

    }

    @Override
    public void equals(int i, int j) {
        comp++;
    }

    @Override
    public void greater(int i, int j) {
        comp++;
    }

    @Override
    public void swap(int i, int j) {
        swap++;
    }

    public int getComparisons(){
        return comp;
    }

    public int getSwaps(){
        return swap;
    }
}
