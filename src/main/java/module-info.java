module org.example.update_ui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.update_ui to javafx.fxml;
    exports org.example.update_ui;

    opens Code to javafx.fxml;
    exports Code;
}