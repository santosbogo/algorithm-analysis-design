package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class InsertionSorter<T> extends AbstractSorter<T>{

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {

    }

    @Override
    public @NotNull SorterType getType() {
        return null;
    }
}
