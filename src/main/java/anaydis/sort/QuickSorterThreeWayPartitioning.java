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

        T pivot = list.get(low);
        int lt = low, gt = high;
        int i = low + 1;

        while (i <= gt) {
            int cmp = comparator.compare(list.get(i), pivot);
            if (cmp < 0) exch(list, lt++, i++);
            else if (cmp > 0) exch(list, i, gt--);
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
