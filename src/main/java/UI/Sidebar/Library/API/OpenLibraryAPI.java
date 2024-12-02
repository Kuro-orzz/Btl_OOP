package UI.Sidebar.Library.API;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenLibraryAPI {

    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    private static final String LOCAL_FALLBACK_IMAGE = "/GUI/NotFound.png";

    public Task<StackPane> getImageAsync(String isbn) {
        return new Task<>() {
            @Override
            protected StackPane call() throws Exception {
                try {
                    String imageUrl = fetchBookImageUrl(isbn);
                    Image image;
                    if (imageUrl != null) {
                        try {
                            image = new Image(imageUrl, true); // Load image from URL
                        } catch (Exception e) {
                            image = loadFallbackImage(); // Load fallback image if URL loading fails
                        }
                    } else {
                        image = loadFallbackImage(); // Load fallback image if URL is null
                    }

                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(200);
                    imageView.setPreserveRatio(true);

                    return new StackPane(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new StackPane(new ImageView(loadFallbackImage())); // Ensure fallback even on errors
                }
            }
        };
    }

    /**
     * Fetches the cover image URL of a book from the Google Books API using ISBN.
     *
     * @param isbn The ISBN of the book
     * @return The URL of the book's cover image, or null if not found
     * @throws Exception If an error occurs during the API call or JSON parsing
     */
    private String fetchBookImageUrl(String isbn) throws Exception {
        String apiUrl = GOOGLE_BOOKS_API_URL + isbn;

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // Read the response from the API
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.has("items")) {
            JSONArray items = jsonResponse.getJSONArray("items");
            if (items.length() > 0) {
                JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
                if (volumeInfo.has("imageLinks")) {
                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    if (imageLinks.has("thumbnail")) {
                        return imageLinks.getString("thumbnail").replace("http://", "https://"); // Ensure HTTPS
                    }
                }
            }
        }

        return null; // Return null if no image URL is found
    }

    /**
     * Loads the fallback image from resources.
     *
     * @return The fallback Image object
     */
    private Image loadFallbackImage() {
        try {
            return new Image(getClass().getResourceAsStream(LOCAL_FALLBACK_IMAGE));
        } catch (Exception e) {
            System.err.println("Failed to load fallback image: " + e.getMessage());
            return new Image("https://via.placeholder.com/150"); // Final fallback to placeholder
        }
    }
}
