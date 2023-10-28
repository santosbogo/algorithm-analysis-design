package anaydis.string;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class SearcherProvider implements StringSearcherProvider{

    private Map<StringSearcherType, StringSearcher> stringSearchers = new EnumMap<>(StringSearcherType.class);

    public SearcherProvider(String text){
        stringSearchers.put(StringSearcherType.BRUTE_FORCE, new BruteForce(text));
        stringSearchers.put(StringSearcherType.RABIN_KARP, new RabinKarp(text));
    }
    @Override
    public @NotNull StringSearcher getForType(@NotNull StringSearcherType type, @NotNull String text) {
        return stringSearchers.get(type);
    }
}
