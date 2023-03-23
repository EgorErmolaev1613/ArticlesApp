package com.example.postapp.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.postapp.DB;
import com.example.postapp.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FullTextArticleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackButton;

    @FXML
    private TextArea articleFullTextArea;

    @FXML
    private Button exitButton;

    @FXML
    void initialize() throws SQLException {
        DB db = new DB();

        ResultSet resultSet = db.getArticle(ArticlesPanelController.id);

        while (resultSet.next()) {
            String text = resultSet.getString("text");
            articleFullTextArea.setText(text);

        }


        BackButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                HelloApplication.setScene("articles-panel.fxml", stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        exitButton.setOnAction(event -> {
            try {
                exitUser(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void exitUser(ActionEvent event) throws IOException {
        File file = new File("user.settings");
        file.delete();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        HelloApplication.setScene("main.fxml", stage);
    }
}
