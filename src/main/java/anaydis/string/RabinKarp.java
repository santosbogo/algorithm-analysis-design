package anaydis.string;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class RabinKarp implements StringSearcher{
    private String text;

    public RabinKarp(String text) {
        this.text = text;
    }

    @Override
    public int count(@NotNull String pattern) {
        return 0;
    }

    @Override
    public int first(@NotNull String pattern) {
        return 0;
    }

    @Override
    public @NotNull Iterator<Integer> all(@NotNull String pattern) {
        return null;
    }

    @Override
    public @NotNull StringSearcherType getType() {
        return null;
    }

    @Override
    public @NotNull String text() {
        return null;
    }
}
