package Code;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoadImage {
    private static final String IMG_LOGIN_PATH = "/GUI/loginImage.jpg";
    public ImageView loadLoginImage() {
        ImageView imageView = new ImageView();
        try {
            Image login = new Image(getClass().getResourceAsStream(IMG_LOGIN_PATH));
            imageView.setImage(login);
            imageView.setFitWidth(1280);
            imageView.setFitHeight(720);
            imageView.setStyle("-fx-opacity: 0.6");
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
        return imageView;
    }

}