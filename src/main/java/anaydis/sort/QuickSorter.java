package anaydis.sort;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class QuickSorter extends AbstractSorter {
    public QuickSorter() {
        super(SorterType.QUICK);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        sort(list, comparator, 0, list.size() - 1);
    }

    private <T> void sort(List<T> list, Comparator<T> comparator, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(list, comparator, low, high);
            sort(list, comparator, low, partitionIndex - 1);
            sort(list, comparator, partitionIndex + 1, high);
        }
    }

    private <T> int partition(List<T> list, Comparator<T> comparator, int low, int high) {
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (less(list, j, high, comparator)) {
                i++;
                exch(list, i, j);
            }
        }
        exch(list, i + 1, high);
        return i + 1;
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.QUICK;
    }
}
