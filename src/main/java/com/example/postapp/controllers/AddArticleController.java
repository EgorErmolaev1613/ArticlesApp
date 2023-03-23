package com.example.postapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.postapp.DB;
import com.example.postapp.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddArticleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea fullTextField;

    @FXML
    private TextArea introField;

    @FXML
    private AnchorPane regRigths;

    @FXML
    private TextField titleField;

    @FXML
    private Button BackButton;

    @FXML
    void addArticle(ActionEvent event) {
        String title =  titleField.getCharacters().toString();
        String intro = introField.getText();
        String fullText =  fullTextField.getText();

        titleField.setStyle("-fx-border-color: #fafafa");
        introField.setStyle("-fx-border-color: #fafafa");
        fullTextField.setStyle("-fx-border-color: #fafafa");

        if(title.length()<=5) {
            titleField.setStyle("-fx-border-color: #e06249");
        } else if (intro.length()<=10)  {
            introField.setStyle("-fx-border-color: #e06249");
        }
        else if (fullText.length()<=15)  {
            fullTextField.setStyle("-fx-border-color: #e06249");
        }
        else {
            DB db = new DB();
            db.addArticle(title,intro,fullText);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                HelloApplication.setScene("articles-panel.fxml",stage);
            } catch (IOException e) {
                throw new RuntimeException(e) ;
            }

        }

        }


    @FXML
    void initialize() {
        BackButton.setOnAction(event -> {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                HelloApplication.setScene("articles-panel.fxml", stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
