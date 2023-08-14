package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class SorterProviderSort implements SorterProvider {

    private Map<SorterType, Sorter> sorters = new EnumMap<>(SorterType.class);

    public SorterProviderSort(){
        sorters.put(SorterType.SELECTION, new SelectionSorter(SorterType.SELECTION));
        sorters.put(SorterType.INSERTION, new InsertionSorter(SorterType.INSERTION));
        sorters.put(SorterType.BUBBLE, new BubbleSorter(SorterType.BUBBLE));
    }

    @Override
    public @NotNull Iterable<Sorter> getAllSorters() {
        return sorters.values();
    }

    @Override
    public @NotNull Sorter getSorterForType(@NotNull SorterType type) {
        return sorters.get(type);
    }
}
