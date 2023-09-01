package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class HybridQuickSorter extends AbstractSorter{

    private final static int M = 10;

    public HybridQuickSorter() {
        super(SorterType.QUICK_CUT);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {

        InsertionSorter insertion = new InsertionSorter();

        sort(list, comparator, 0, list.size() - 1);
        insertion.sort(comparator, list);
    }

    private <T> void sort(List<T> list, Comparator<T> comparator, int low, int high) {
        if (M < high - low) {
            int partitionIndex = partition(list, comparator, low, high);
            sort(list, comparator, low, partitionIndex - 1);
            sort(list, comparator, partitionIndex + 1, high);
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.QUICK_CUT;
    }
}
