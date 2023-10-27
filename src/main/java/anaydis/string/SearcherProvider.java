package anaydis.string;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class SearcherProvider implements StringSearcherProvider{

    private Map<StringSearcherType, StringSearcher> stringSearchers = new EnumMap<>(StringSearcherType.class);

    public SearcherProvider(){
        stringSearchers.put(StringSearcherType.BRUTE_FORCE, new BruteForce());
        stringSearchers.put(StringSearcherType.RABIN_KARP, new RabinKarp());
    }
    @Override
    public @NotNull StringSearcher getForType(@NotNull StringSearcherType type, @NotNull String text) {
        return stringSearchers.get(type);
    }
}
