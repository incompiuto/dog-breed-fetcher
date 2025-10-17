package dogapi;

import java.util.*;

/**
 * Decorator that caches successful sub-breed lookups to avoid redundant API calls.
 */
public class CachingBreedFetcher implements BreedFetcher {

    // Underlying fetcher that actually does the work.
    private final BreedFetcher delegate;

    // Simple in-memory cache from breed -> list of sub-breeds.
    private final Map<String, List<String>> cache = new HashMap<>();

    // TODO: track how many times we actually hit the underlying fetcher
    // (i.e., the number of cache MISSES that invoked delegate.getSubBreeds).
    private int callsMade = 0;

    public CachingBreedFetcher(BreedFetcher delegate) {
        this.delegate = delegate;
    }

    /** TODO: required by tests – return how many times the delegate was called. */
    public int getCallsMade() {
        return callsMade;
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        if (breed == null) {
            return Collections.emptyList();
        }

        // Return from cache if present (cache HIT).
        List<String> cached = cache.get(breed);
        if (cached != null) {
            return cached;
        }

        // Cache MISS → call the underlying fetcher exactly once and count it.
        List<String> result = delegate.getSubBreeds(breed);
        callsMade++;

        // Normalize nulls to an immutable empty list to avoid NPEs later.
        if (result == null) {
            result = Collections.emptyList();
        } else {
            // Store an unmodifiable copy to keep cache safe from external mutation.
            result = Collections.unmodifiableList(new ArrayList<>(result));
        }

        cache.put(breed, result);
        return result;
    }
}