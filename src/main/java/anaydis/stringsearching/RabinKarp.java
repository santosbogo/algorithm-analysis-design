package anaydis.stringsearching;

import anaydis.string.StringSearcher;
import anaydis.string.StringSearcherType;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class RabinKarp implements StringSearcher {

    private final String text;
    static final int MODULE = 3355439;
    static final int RADIX = 256;

    public RabinKarp(String text) {
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
        int m = pattern.length();
        int n = text.length();
        int radixMod, patternHash, textHash = 0;

        if (m > n) return -1;

        radixMod = 1;
        for (int i = 1; i < m; i++) {
            radixMod = (RADIX * radixMod) % MODULE;
        }

        patternHash = 0;
        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * RADIX + pattern.charAt(i)) % MODULE;
            textHash = (textHash * RADIX + text.charAt(i)) % MODULE;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textHash) {
                if (text.substring(i, i + m).equals(pattern)) {
                    return i;
                }
            }

            if (i < n - m) {
                textHash = (textHash - text.charAt(i) * radixMod) % MODULE;
                textHash = (textHash * RADIX + text.charAt(i + m)) % MODULE;

                if (textHash < 0) {
                    textHash += MODULE;
                }
            }
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
        return StringSearcherType.RABIN_KARP;
    }

    @Override
    public @NotNull String text() {
        return text;
    }
}
