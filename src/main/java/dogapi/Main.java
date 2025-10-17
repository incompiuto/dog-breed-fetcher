package dogapi;

import java.util.List;

public class Main {

    /**
     * TODO: FIXED parameter order to match the tests:
     * The tests call: Main.getNumberOfSubBreeds("hound", mock)
     * so the signature must be (String breed, BreedFetcher fetcher).
     */
    public static int getNumberOfSubBreeds(String breed, BreedFetcher fetcher) {
        List<String> subs = fetcher.getSubBreeds(breed);
        return (subs == null) ? 0 : subs.size();
    }

    // Optional back-compat overload in case any other code used the old order.
    public static int getNumberOfSubBreeds(BreedFetcher fetcher, String breed) {
        return getNumberOfSubBreeds(breed, fetcher);
    }

    public static void main(String[] args) {
        // no-op
    }
}