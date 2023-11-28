package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class QuickSorterThreeWayPartitioning extends AbstractSorter {
    public QuickSorterThreeWayPartitioning() {
        super(SorterType.QUICK_THREE_PARTITION);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        threeWaySort(list, comparator, 0, list.size() - 1);
    }

    private <T> void threeWaySort(List<T> list, Comparator<T> comparator, int low, int high) {

        if (high <= low) return;

        int lt = low, gt = high;
        int i = low + 1;

        while (i <= gt) {
            if (less(list, i, low, comparator)) exch(list, lt++, i++);
            else if (less(list, low, i, comparator)) exch(list, i, gt--);
            else i++;
        }

        threeWaySort(list, comparator, low, lt - 1);
        threeWaySort(list, comparator, gt + 1, high);
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.QUICK_THREE_PARTITION;
    }
}
