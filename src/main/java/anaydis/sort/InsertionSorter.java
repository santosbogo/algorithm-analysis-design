package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class InsertionSorter extends AbstractSorter{

    public InsertionSorter() {
        super(SorterType.INSERTION);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        int size = list.size();
        for (int i = 1; i < size; i++){
            for (int j = i; j > 0; j--){
                notifyBox(j-1, j);
                if (less(list, j, j-1, comparator)){
                    exch(list, j, j-1);
                }
                else {
                    break;
                }
            }
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.INSERTION;
    }
}
