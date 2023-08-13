package anaydis.sort;

import org.jetbrains.annotations.NotNull;

public class SorterProviderSort implements SorterProvider {
    @Override
    public @NotNull Iterable<Sorter> getAllSorters() {
        return null;//Hacerlo con hash map
    }

    @Override
    public @NotNull Sorter getSorterForType(@NotNull SorterType type) {
        return null;
    }
}
