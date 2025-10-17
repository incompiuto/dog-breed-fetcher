package dogapi;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Local, in-memory implementation of {@link BreedFetcher} used by tests.
 * Returns a fixed set of sub-breeds without doing any network calls.
 *
 * NOTE: For unknown breeds, this returns an empty list (no exceptions).
 * This matches the unit tests' expectations (e.g., "cat" -> 0 sub-breeds).
 */
public class BreedFetcherForLocalTesting implements BreedFetcher {

    // Minimal dataset for tests. "hound" must have size 2 to satisfy assertions.
    private static final Map<String, List<String>> DATA;
    static {
        Map<String, List<String>> m = new HashMap<>();
        m.put("hound", List.of("afghan", "basset")); // exactly 2 sub-breeds
        // add other examples if you like; not required by the tests
        DATA = Collections.unmodifiableMap(m);
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        if (breed == null) {
            return Collections.emptyList();
        }
        List<String> list = DATA.get(breed.toLowerCase(Locale.ROOT));
        return (list == null) ? Collections.emptyList() : list;
    }
}
