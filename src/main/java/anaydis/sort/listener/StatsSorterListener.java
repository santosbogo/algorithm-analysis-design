package anaydis.sort.listener;

import anaydis.sort.gui.SorterListener;

public class StatsSorterListener implements SorterListener {
    private long comp = 0;
    private long swap = 0;
    private long copy = 0;

    @Override
    public void box(int from, int to) {}

    @Override
    public void copy(int from, int to, boolean copyToAux) {
        copy++;
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

    public long getComparisons(){
        return comp;
    }

    public long getSwaps(){
        return swap;
    }

    public long getCopies(){
        return copy;
    }

}
