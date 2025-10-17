import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dogapi.BreedFetcher;

/**
 * A caching decorator for a BreedFetcher.
 * Wraps an underlying fetcher and caches results by breed name.
 */
public class CachingBreedFetcher implements BreedFetcher {

    // Underlying fetcher that actually does the work.
    private final BreedFetcher delegate;

    // In-memory cache from breed -> list of sub-breeds.
    private final Map<String, List<String>> cache = new HashMap<>();

    // TODO: track how many times we actually hit the underlying fetcher
    // (i.e., number of cache MISSES that invoked delegate.getSubBreeds).
    private int callsMade = 0;

    public CachingBreedFetcher(BreedFetcher delegate) {
        this.delegate = delegate;
    }

    /** TODO: required by tests – return how many times the delegate was called. */
    public int getCallsMade() {
        return callsMade;
    }

    /** Only non-null, non-blank breeds should be cached. */
    private static boolean isCacheable(String breed) {
        return breed != null && !breed.isBlank();
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        // If cacheable and present → return cached (do NOT call delegate).
        if (isCacheable(breed)) {
            List<String> cached = cache.get(breed);
            if (cached != null) {
                return cached;
            }
        }

        // Otherwise, always call the delegate (even for invalid breeds).
        List<String> result = delegate.getSubBreeds(breed);
        callsMade++;

        // Normalize and protect against mutation.
        if (result == null) {
            result = Collections.emptyList();
        } else {
            result = Collections.unmodifiableList(new ArrayList<>(result));
        }

        // Cache ONLY valid, cacheable breeds.
        if (isCacheable(breed)) {
            cache.put(breed, result);
        }

        return result;
    }
}
