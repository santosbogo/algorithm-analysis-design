package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class ShellSorter extends AbstractSorter{
    public ShellSorter(){
        super(SorterType.SHELL);
    }
    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {

    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.SHELL;
    }
}
