module com.example.hms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires com.dlsc.formsfx;

    opens com.example.hms to javafx.fxml;
    exports com.example.hms;
    exports connectionDB;
    opens connectionDB to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
    exports Controller;
    opens Controller to javafx.fxml;
    exports DTO;
    opens DTO to javafx.fxml;
}