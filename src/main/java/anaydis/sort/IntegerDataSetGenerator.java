package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class IntegerDataSetGenerator implements DataSetGenerator<Integer> {

    @Override
    public @NotNull List createAscending(int length) {
        return null;
    }

    @Override
    public @NotNull List createDescending(int length) {
        return null;
    }

    @Override
    public @NotNull List createRandom(int length) {
        return null;
    }

    @Override
    public @NotNull Comparator getComparator() {
        return Comparator.naturalOrder();
    }
}
