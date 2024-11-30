package UI.Sidebar;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Clock {
    public Label renderClock() {
        Label timeLabel = new Label();
        timeLabel.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/mainApp.css")).toExternalForm()
        );
        timeLabel.getStyleClass().add("clock");

        // Create clock using thread
        Thread clockThread = new Thread(() -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            while (true) {
                LocalTime currentTime = LocalTime.now();
                String formattedTime = currentTime.format(formatter);
                Platform.runLater(() -> timeLabel.setText(formattedTime));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        clockThread.setDaemon(true);
        clockThread.start();

        return timeLabel;
    }
}