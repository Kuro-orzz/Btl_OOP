module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires opencsv;

    opens Controller to javafx.fxml;
    exports Controller;
    exports UI.AccessApp;
    opens UI.AccessApp to javafx.fxml;
    exports UI.Sidebar.Home;
    opens UI.Sidebar.Home to javafx.fxml;
    exports UI.Sidebar.Library;
    opens UI.Sidebar.Library to javafx.fxml;
    exports UI.Sidebar.UserManagement;
    opens UI.Sidebar.UserManagement to javafx.fxml;
    exports UI.Sidebar;
    opens UI.Sidebar to javafx.fxml;
    exports UI.Sidebar.BorrowBook;
    opens UI.Sidebar.BorrowBook to javafx.fxml;
    exports UI.Sidebar.BorrowBook.BorrowedData;
    opens UI.Sidebar.BorrowBook.BorrowedData to javafx.fxml;
}