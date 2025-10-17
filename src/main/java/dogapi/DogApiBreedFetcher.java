package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Implementation of BreedFetcher that calls the Dog CEO API.
 *
 * Uses OkHttp for HTTP and Gson for JSON parsing (both are already included in pom.xml).
 */
public class DogApiBreedFetcher implements BreedFetcher {

    // TODO: Implement API call to Dog CEO and parse JSON
    @Override
    public java.util.List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        final String url = "https://dog.ceo/api/breed/" + breed.toLowerCase() + "/list";

        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).get().build();

        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();

            if (response.body() == null) {
                throw new RuntimeException("Empty response body for " + url);
            }

            if (!response.isSuccessful()) {
                // HTTP-level error (e.g., 404). The API also encodes errors in JSON,
                // so we still parse the body below to check "status".
                // We won't throw here; we'll parse and interpret the JSON status.
            }

            String body = response.body().string();

            com.google.gson.JsonObject root =
                    com.google.gson.JsonParser.parseString(body).getAsJsonObject();

            String status = root.get("status").getAsString();
            if (!"success".equals(status)) {
                // The API uses status "error" for unknown breeds.
                throw new BreedNotFoundException("Breed not found: " + breed);
            }

            com.google.gson.JsonArray arr = root.getAsJsonArray("message");
            java.util.List<String> result = new java.util.ArrayList<>(arr.size());
            for (com.google.gson.JsonElement e : arr) {
                result.add(e.getAsString());
            }
            return result;

        } catch (java.io.IOException ioe) {
            // Network/IO problems are not part of the interface contract: wrap as unchecked.
            throw new RuntimeException("Network error fetching sub-breeds for: " + breed, ioe);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}