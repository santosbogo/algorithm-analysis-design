package anaydis.sorting;

import anaydis.sort.SorterType;
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
        sort(list, comparator, 0, list.size() - 1);
        new InsertionSorter().sort(comparator, list);
    }

    private <T> void sort(List<T> list, Comparator<T> comparator, int low, int high) {
        while (high - low > M) {
            int pivotIndex = medianOfThree(list, comparator, low, high);
            exch(list, pivotIndex, high - 1);

            int partitionIndex = partition(list, comparator, low, high - 1);

            if (partitionIndex - low < high - partitionIndex) {
                sort(list, comparator, low, partitionIndex - 1);
                low = partitionIndex + 1;
            } else {
                sort(list, comparator, partitionIndex + 1, high);
                high = partitionIndex - 1;
            }
        }
    }

    private <T> int medianOfThree(List<T> list, Comparator<T> comparator, int low, int high) {
        int mid = (low + high) / 2;
        compExch(list, low, mid, comparator);
        compExch(list, low, high, comparator);
        compExch(list, mid, high, comparator);
        return mid;
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.QUICK_MED_OF_THREE;
    }
}
