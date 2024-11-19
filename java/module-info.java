module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires opencsv;

    opens org.example.demo to javafx.fxml;
    exports org.example.demo;

    opens Code to javafx.fxml;
    exports Code;
}