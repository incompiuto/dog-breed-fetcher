package dogapi;

import java.util.List;

public class Main {

    // TODO: Implement this method
    public static int getNumberOfSubBreeds(BreedFetcher fetcher, String breed)
            throws BreedFetcher.BreedNotFoundException {
        return fetcher.getSubBreeds(breed).size();
    }

    // TODO: Implement example usage
    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("Usage: java Main <breed>");
            return;
        }
        String breed = args[0];
        BreedFetcher fetcher = new DogApiBreedFetcher();
        try {
            int count = getNumberOfSubBreeds(fetcher, breed);
            System.out.println(breed.toLowerCase() + " has " + count + " sub-breed(s).");
        } catch (BreedFetcher.BreedNotFoundException e) {
            System.out.println("Breed not found: " + breed);
        }
    }
}