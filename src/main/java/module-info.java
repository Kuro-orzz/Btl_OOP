module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires opencsv;
    requires java.desktop;
    requires org.json;
    requires com.google.zxing;

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
    exports UI.Sidebar.Library.BookData to javafx.fxml;
    opens  UI.Sidebar.Library.BookData to javafx.fxml;
    opens Testing to javafx.graphics;
    exports Testing;
    opens UI.Sidebar.UserManagement.AccountData to javafx.fxml;
    exports UI.Sidebar.UserManagement.AccountData to javafx.fxml;
    exports Optimize to javafx.fxml;
    opens Optimize to javafx.fxml;
    exports UI.Sidebar.Library.ContextMenu;
    opens UI.Sidebar.Library.ContextMenu to javafx.fxml;
}