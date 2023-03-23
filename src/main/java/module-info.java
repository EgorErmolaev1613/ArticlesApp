module com.example.postapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens com.example.postapp to javafx.fxml;
    exports com.example.postapp;
    exports com.example.postapp.controllers;
    opens com.example.postapp.controllers to javafx.fxml;
    exports com.example.postapp.models;
    opens com.example.postapp.models to javafx.fxml;
}