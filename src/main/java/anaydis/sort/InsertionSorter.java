package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class InsertionSorter extends AbstractSorter{

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        int size = list.size();
        for (int i = 1; i < size; i++){
            for (int j = i; j > 0; j--){
                if (less(list.get(i), list.get(j-1), comparator)){
                    exch(list, i, j);
                }
                else break;
            }
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.INSERTION;
    }
}
