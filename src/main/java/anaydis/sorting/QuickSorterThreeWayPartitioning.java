package anaydis.sorting;

import anaydis.sort.SorterType;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class QuickSorterThreeWayPartitioning extends AbstractSorter {

    private static final int M = 10;
    private final Random random = new Random();

    public QuickSorterThreeWayPartitioning() {
        super(SorterType.QUICK_THREE_PARTITION);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        threeWaySort(list, comparator, 0, list.size() - 1);
        new InsertionSorter().sort(comparator, list);
    }


    private <T> void threeWaySort(List<T> list, Comparator<T> comparator, int low, int high) {

        while (high - low > M) {
            int pivotIndex = low + random.nextInt(high - low + 1);
            exch(list, low, pivotIndex);

            T pivot = list.get(low);
            int lt = low, gt = high, i = low + 1;

            while (i <= gt) {
                int cmp = comparator.compare(list.get(i), pivot);
                if (cmp < 0) exch(list, lt++, i++);
                else if (cmp > 0) exch(list, i, gt--);
                else i++;
            }

            if (lt - low < high - gt) {
                threeWaySort(list, comparator, low, lt - 1);
                low = gt + 1;
            } else {
                threeWaySort(list, comparator, gt + 1, high);
                high = lt - 1;
            }
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.QUICK_THREE_PARTITION;
    }
}
