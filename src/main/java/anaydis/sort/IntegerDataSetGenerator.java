package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class IntegerDataSetGenerator implements DataSetGenerator<Integer> {

    @Override
    public @NotNull List createAscending(int length) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < length; i++){
            list.add(i);
        }
        return list;
    }

    @Override
    public @NotNull List createDescending(int length) {
        List<Integer> list = new ArrayList<>();
        for (int i = length; i > 0; i--){
            list.add(i);
        }
        return list;
    }

    @Override
    public @NotNull List createRandom(int length) {
        Random random = new Random(3);
        List<Integer> list = new ArrayList<>();

        for (int i = length; i > 0; i--){
            int rand = random.nextInt(length*4);
            list.add(rand);
        }
        return list;
    }

    @Override
    public @NotNull Comparator getComparator() {
        return Comparator.naturalOrder();
    }
}
