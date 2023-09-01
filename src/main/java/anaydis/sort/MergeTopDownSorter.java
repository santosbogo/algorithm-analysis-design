package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeTopDownSorter extends AbstractSorter{

    public MergeTopDownSorter(){
        super(SorterType.MERGE_TOP_DOWN);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        List<T> temp = new ArrayList<>(list);
        sort(comparator, list, temp, 0, list.size() - 1);
    }

    private <T> void sort(Comparator<T> comparator, List<T> list, List<T> temp, int low, int high){

        if (low < high){

            int mid = (low + high) / 2;

            sort(comparator, list, temp, low, mid);
            sort(comparator, list, temp, mid + 1, high);

            merge(comparator, list, temp, low, mid, high);
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.MERGE_TOP_DOWN;
    }
}
