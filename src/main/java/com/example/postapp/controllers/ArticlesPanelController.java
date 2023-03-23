package com.example.postapp.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.postapp.DB;
import com.example.postapp.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ArticlesPanelController {
   public static String id = "";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton,addPostButton;

    @FXML
    private AnchorPane regRigths;

    @FXML
    private VBox panelVBox;



    @FXML
    void initialize() throws SQLException, IOException {
        DB db = new DB();
        ResultSet resultSet = db.getArticles();
        while(resultSet.next()) {
            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("article.fxml")));
            node.setId(resultSet.getString("id"));
            Label title = (Label) node.lookup("#title");
            title.setText(resultSet.getString("title"));

            Label intro = (Label) node.lookup("#intro");
            intro.setText(resultSet.getString("intro"));

            node.setOnMouseEntered(event -> {
                node.setStyle("-fx-background-color: #707173");
            });
            node.setOnMouseClicked(event -> {
                ArticlesPanelController.id = node.getId();
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                try {
                    HelloApplication.setScene("full-text-article.fxml",stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
            node.setOnMouseExited(event -> {
                node.setStyle("-fx-background-color: #343434");
            });

            panelVBox.getChildren().add(node);
            panelVBox.setSpacing(10);
        }

        exitButton.setOnAction(event -> {
            try {
                exitUser(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addPostButton.setOnAction(event -> {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                HelloApplication.setScene("add-article.fxml",stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void exitUser(ActionEvent event) throws IOException {
        File file = new File ("user.settings");
        file.delete();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        HelloApplication.setScene("main.fxml",stage);

    }

}
