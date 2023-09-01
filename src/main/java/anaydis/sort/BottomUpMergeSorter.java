package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class BottomUpMergeSorter extends AbstractSorter{

    public BottomUpMergeSorter(){
        super(SorterType.MERGE_BOTTOM_UP);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        int size = list.size();
        List<T> temp = new java.util.ArrayList<>(list);

        for (int len = 1; len < size; len *= 2) {
            for (int lo = 0; lo < size - len; lo += len + len) {
                int mid = lo + len - 1;
                int hi = Math.min(lo + len + len - 1, size - 1);
                merge(comparator, list, temp, lo, mid, hi);
            }
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.MERGE_BOTTOM_UP;
    }
}
