package anaydis.string;

import org.jetbrains.annotations.NotNull;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class SearcherProvider implements StringSearcherProvider {

    private Map<StringSearcherType, Function<String, StringSearcher>> stringSearcherFactories = new EnumMap<>(StringSearcherType.class);

    public SearcherProvider() {
        stringSearcherFactories.put(StringSearcherType.BRUTE_FORCE, BruteForce::new);
        stringSearcherFactories.put(StringSearcherType.RABIN_KARP, RabinKarp::new);
        // You can add more searchers here as needed
    }

    @Override
    public @NotNull StringSearcher getForType(@NotNull StringSearcherType type, @NotNull String text) {
        Function<String, StringSearcher> factory = stringSearcherFactories.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("No StringSearcher implementation available for type: " + type);
        }
        return factory.apply(text);
    }
}
