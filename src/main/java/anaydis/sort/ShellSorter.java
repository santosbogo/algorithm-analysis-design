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
        HSorter Hsorter = new HSorter();
        for (int h = list.size()/2; h > 1; h/=2){
            Hsorter.sort(comparator, list, h);
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.SHELL;
    }
}
