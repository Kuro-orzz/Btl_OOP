module org.example.simple {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens org.example.simple to javafx.fxml;
    exports org.example.simple;

    opens Code to javafx.fxml;
    exports Code;
}