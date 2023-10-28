package anaydis.string;

import anaydis.sort.SorterType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BruteForce implements StringSearcher{

    private final String text;

    public BruteForce(@NotNull String text) {
        this.text = text;
    }

    @Override
    public int count(@NotNull String pattern) {
        int count = 0;
        int index = first(pattern);
        while (index != -1) {
            count++;
            index = text().indexOf(pattern, index + 1);
        }
        return count;
    }

    @Override
    public int first(@NotNull String pattern) {
        int textLength = text().length();
        int patternLength = pattern.length();

        for (int i = 0; i <= textLength - patternLength; i++) {
            int j;
            for (j = 0; j < patternLength; j++)
                if (text().charAt(i + j) != pattern.charAt(j))
                    break;

            if (j == patternLength) return i;
        }
        return -1;
    }

    @Override
    public @NotNull Iterator<Integer> all(@NotNull String pattern) {
        List<Integer> indices = new ArrayList<>();
        int index = first(pattern);
        while (index != -1) {
            indices.add(index);
            index = text.indexOf(pattern, index + 1);
        }
        return indices.iterator();
    }

    @Override
    public @NotNull StringSearcherType getType() {
        return StringSearcherType.BRUTE_FORCE;
    }

    @Override
    public @NotNull String text() {
        return text;
    }
}
