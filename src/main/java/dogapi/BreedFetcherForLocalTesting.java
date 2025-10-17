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
 * NOTE:
 *  - For unknown/invalid breeds, return an empty list (no exceptions).
 *  - Tests also expect to see how many times this fetcher was invoked,
 *    via getCallCount(), even for invalid inputs.
 */
public class BreedFetcherForLocalTesting implements BreedFetcher {

    // Minimal dataset for tests. "hound" must have size 2 to satisfy assertions.
    private static final Map<String, List<String>> DATA;
    static {
        Map<String, List<String>> m = new HashMap<>();
        m.put("hound", List.of("afghan", "basset")); // exactly 2 sub-breeds
        DATA = Collections.unmodifiableMap(m);
    }

    // TODO: count how many times getSubBreeds has been called (for tests).
    private int callCount = 0;

    /** TODO: required by tests – return number of times getSubBreeds was called. */
    public int getCallCount() {
        return callCount;
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        // Increment on EVERY call, even for invalid breeds.
        callCount++;

        if (breed == null || breed.isBlank()) {
            return Collections.emptyList();
        }
        List<String> list = DATA.get(breed.toLowerCase(Locale.ROOT));
        return (list == null) ? Collections.emptyList() : list;
    }
}
