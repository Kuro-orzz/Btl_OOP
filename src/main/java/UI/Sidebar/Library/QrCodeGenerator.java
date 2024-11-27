package UI.Sidebar.Library;

import UI.Sidebar.Library.BookData.Book;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class QrCodeGenerator {
    public QrCodeGenerator() {}

    /**
     * Generate QR code to access book's information page on Web.
     * @param content Content of QR (URL link)
     * @param width width
     * @param height height
     * @return QR Image
     * @throws WriterException writer exception when writing barcode
     */
    public Image generateQRCode(String content, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);

        WritableImage qrImage = new WritableImage(width, height);
        PixelWriter pixelWriter = qrImage.getPixelWriter();

        // Draw matrix to create QR Code
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelWriter.setColor(x, y, bitMatrix.get(x, y) ? javafx.scene.paint.Color.BLACK : javafx.scene.paint.Color.WHITE);
            }
        }

        return qrImage;
    }

    /**
     * Show qr of book.
     * @param book book
     */
    protected void showQrCode(Book book) {
        try {
            // URL that link to the book
            String bookUrl = "https://books.google.com/books?vid=ISBN" + book.getIsbn();

            // Generate QR code image
            Image qrImage = generateQRCode(bookUrl, 360, 360);

            // New Stage to display QR Code
            Stage qrStage = new Stage();
            qrStage.setTitle("QR Code for " + book.getTitle());

            ImageView qrImageView = new ImageView(qrImage);
            qrImageView.setFitWidth(360);
            qrImageView.setFitHeight(360);

            StackPane root = new StackPane(qrImageView);
            root.setPadding(new Insets(20));

            Scene scene = new Scene(root, 320, 320);
            qrStage.setScene(scene);
            qrStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
