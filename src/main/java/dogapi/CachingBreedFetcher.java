package dogapi;

import java.util.*;

/**
 * Decorator that caches successful sub-breed lookups to avoid redundant API calls.
 */
public class CachingBreedFetcher implements BreedFetcher {

    private final BreedFetcher delegate;

    public CachingBreedFetcher(BreedFetcher delegate) {
        this.delegate = delegate;
    }

    // TODO: Add cache map
    private final java.util.Map<String, java.util.List<String>> cache = new java.util.HashMap<>();

    // TODO: Implement caching behaviour (do not cache exceptions)
    @Override
    public java.util.List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        String key = breed.toLowerCase();

        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        try {
            java.util.List<String> fetched = delegate.getSubBreeds(breed);
            // Store an unmodifiable copy to prevent accidental external mutation.
            java.util.List<String> copy = java.util.Collections.unmodifiableList(
                    new java.util.ArrayList<>(fetched));
            cache.put(key, copy);
            return copy;
        } catch (BreedNotFoundException e) {
            // Do not cache failures; allow callers to retry later.
            throw e;
        }
    }
}