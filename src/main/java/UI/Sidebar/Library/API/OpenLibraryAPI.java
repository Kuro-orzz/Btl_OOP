package UI.Sidebar.Library.API;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenLibraryAPI {

    public StackPane getImage(String isbn) {
        try {
            String apiUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";

            // Fetch the image URL from Open Library API
            String imageUrl = fetchBookImageUrl(apiUrl, isbn);

            if (imageUrl == null) {
                imageUrl = "https://via.placeholder.com/150";  // Placeholder image if no cover is available
            }

            // Load the image from the URL
            Image image = new Image(imageUrl);

            // Create an ImageView to display the image
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200); // Set width to fit
            imageView.setPreserveRatio(true); // Maintain aspect ratio

            // Add the ImageView to a layout pane
            return new StackPane(imageView);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String fetchBookImageUrl(String apiUrl, String isbn) throws Exception {
        // Send a GET request to web
        final var jsonResponse = getJsonResponse(apiUrl);

        // Check if the response contains the expected data
        String isbnKey = "ISBN:" + isbn; // Replace with the actual ISBN
        if (jsonResponse.has(isbnKey)) {
            JSONObject bookData = jsonResponse.getJSONObject(isbnKey);
            if (bookData.has("cover")) {
                JSONObject cover = bookData.getJSONObject("cover");
                // Directly get the URL from the "large" field
                return cover.getString("large"); // Return the URL as a string
            }
        }

        return null;  // Return null if no cover is found
    }

    private static JSONObject getJsonResponse(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // Get the response
        InputStream inputStream = connection.getInputStream();

        // Read the response and parse it
        StringBuilder response = new StringBuilder();
        int byteRead;
        while ((byteRead = inputStream.read()) != -1) {
            response.append((char) byteRead);
        }

        // Convert response to JSON
        return new JSONObject(response.toString());
    }
}
