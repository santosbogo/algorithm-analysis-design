package anaydis.sorting;

import anaydis.sort.SorterType;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class ShellSorter extends AbstractSorter{
    public ShellSorter(){
        super(SorterType.SHELL);
    }
    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        int size = list.size();
        int h = 1;

        while (h <= size / 9) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < size; i++) {
                for (int j = i; j >= h && less(list, j, j - h, comparator); j -= h) {
                    exch(list, j, j - h);
                }
            }
            h = h / 3;
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.SHELL;
    }
}
