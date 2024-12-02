package UI.Sidebar.Library.API;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenLibraryAPI {
    public Task<StackPane> getImageAsync(String isbn) {
        return new Task<>() {
            @Override
            protected StackPane call() throws Exception {
                try {
                    String apiUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";
                    String imageUrl = fetchBookImageUrl(apiUrl, isbn);
                    if (imageUrl == null) {
                        imageUrl = "https://via.placeholder.com/150"; // Placeholder image
                    }

                    Image image = new Image(imageUrl);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(200);
                    imageView.setPreserveRatio(true);

                    return new StackPane(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    private String fetchBookImageUrl(String apiUrl, String isbn) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        InputStream inputStream = connection.getInputStream();
        StringBuilder response = new StringBuilder();
        int byteRead;
        while ((byteRead = inputStream.read()) != -1) {
            response.append((char) byteRead);
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        String isbnKey = "ISBN:" + isbn;
        if (jsonResponse.has(isbnKey)) {
            JSONObject bookData = jsonResponse.getJSONObject(isbnKey);
            if (bookData.has("cover")) {
                JSONObject cover = bookData.getJSONObject("cover");
                return cover.getString("large");
            }
        }
        return null;
    }
}
