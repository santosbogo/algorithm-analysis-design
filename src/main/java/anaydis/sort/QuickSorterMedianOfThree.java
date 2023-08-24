package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class QuickSorterMedianOfThree extends AbstractSorter{

    private final static int M = 10;

    public QuickSorterMedianOfThree() {
        super(SorterType.QUICK_MED_OF_THREE);
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

            exch(list, (low + high) / 2, high-1);
            compExch(list, low, high - 1, comparator);
            compExch(list, low, high, comparator);
            compExch(list, high-1, high, comparator);
            int i = partition(list, comparator, low + 1, high - 1);

            sort(list, comparator, low, partitionIndex - 1);
            sort(list, comparator, partitionIndex + 1, high);
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.QUICK_MED_OF_THREE;
    }
}
