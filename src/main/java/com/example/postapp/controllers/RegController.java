package com.example.postapp.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.example.postapp.DB;
import com.example.postapp.HelloApplication;
import com.example.postapp.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class  RegController{

    DB db = new DB();
@FXML
private ResourceBundle resources;

@FXML
private URL location;

@FXML
private Button authButton;

@FXML
private TextField authLogin;

@FXML
private PasswordField authPass;

@FXML
private Button regButton;

@FXML
private TextField regEmail;

@FXML
private TextField regLogin;

@FXML
private PasswordField regPass;

@FXML
private CheckBox regRights;

@FXML
private AnchorPane regRigths;

@FXML
    void initialize() {

                regButton.setOnAction(event -> {
                    registrationUser();
                });

                authButton.setOnAction(event -> {
                    try {
                        authUser (event);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

            }

    private void authUser(ActionEvent event) throws IOException {
        String login =  authLogin.getCharacters().toString();
        String pass =  authPass.getCharacters().toString();

        authLogin.setStyle("-fx-border-color: #fafafa");
        authLogin.setStyle("-fx-border-color: #fafafa");

        if(login.length()<=1) {
            authLogin.setStyle("-fx-border-color: #e06249");
        }
        else if (pass.length()<=3)  {
            authPass.setStyle("-fx-border-color: #e06249");
        }
        else if (!db.authUser(login,md5String(pass))){
            authButton.setText("Пользователя нет");
        }
        else {
            db.authUser(login,md5String(pass));
            authLogin.setText("");
            authPass.setText("");
            authButton.setText("Вы авторизованы");
            FileOutputStream fos = new FileOutputStream("user.settings");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new User(login));
            oos.close();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            HelloApplication.setScene("articles-panel.fxml",stage);

        }
    }

    private void registrationUser() {
        String login =  regLogin.getCharacters().toString();
        String email = regEmail.getCharacters().toString();
        String pass =  regPass.getCharacters().toString();

        regLogin.setStyle("-fx-border-color: #fafafa");
        regEmail.setStyle("-fx-border-color: #fafafa");
        regPass.setStyle("-fx-border-color: #fafafa");

        if(login.length()<=1) {
            regLogin.setStyle("-fx-border-color: #e06249");
        } else if (email.length()<=5 || !email.contains("@") || !email.contains("."))  {
            regEmail.setStyle("-fx-border-color: #e06249");
        }
        else if (pass.length()<=3)  {
            regPass.setStyle("-fx-border-color: #e06249");
        } else if (!regRights.isSelected()) {

            regButton.setText("Поставьте галочку");
        }
        else if (db.isExistsUser(login)){
            regButton.setText("Введите другой логин");
        }
        else {
            db.regUser(login,email,md5String(pass));
            regLogin.setText("");
            regEmail.setText("");
            regPass.setText("");
            regButton.setText("Вы зарегистрировались");
        }
    }

    public static String md5String (String pass) {
        MessageDigest messageDigest = null;

        byte[] digest = new byte[0];

        try {
            messageDigest = messageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        BigInteger bigInteger = new BigInteger(1,digest);
        String md5Hex = bigInteger.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }

}