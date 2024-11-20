module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires opencsv;

    opens Code to javafx.fxml;
    exports Code;
}