package dogapi;

import java.util.List;

/**
 * Interface for fetching sub-breeds for a given dog breed.
 */
public interface BreedFetcher {

    /**
     * Thrown when the provided breed does not exist in the API.
     */
    // TODO: Make this a checked exception
    public static class BreedNotFoundException extends Exception {
        public BreedNotFoundException(String message) {
            super(message);
        }
    }

    /**
     * Return a list of sub-breeds for the given breed name.
     */
    // TODO: Update signature to declare throws
    java.util.List<String> getSubBreeds(String breed) throws BreedNotFoundException;
}