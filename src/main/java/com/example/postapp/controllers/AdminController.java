package com.example.postapp.controllers;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.example.postapp.DB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AdminController {

    DB db = new DB();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField adminEmail;

    @FXML
    private TextField adminLogin;

    @FXML
    private PasswordField adminPassword;

    @FXML
    private AnchorPane regRigths;

    @FXML
    private Button updateAdminButton;

    @FXML
    void initialize() {
        db.clearDB();
        db.regAdmin();
        adminLogin.setText(db.getAdminLogin());
        adminEmail.setText(db.getAdminEmail());
        updateAdminButton.setOnAction(event -> {
            updateAdmin();
        });

    }

    private void updateAdmin() {
            String login =  adminLogin.getCharacters().toString();
            String pass =  adminEmail.getCharacters().toString();
            String email = adminEmail.getCharacters().toString();

            adminLogin.setStyle("-fx-border-color: #fafafa");
            adminPassword.setStyle("-fx-border-color: #fafafa");
            adminEmail.setStyle("-fx-border-color: #fafafa");

            if(login.length()<=1 || db.isExistsUser(login)) {
                updateAdminButton.setText("Введите другого пользвателя");
                adminLogin.setStyle("-fx-border-color: #e06249");
            }
            else if (pass.length()<=3)  {
                adminPassword.setStyle("-fx-border-color: #e06249");
            }
            else {
                db.updateAdminLogin(login);
                db.updateAdminEmail(email);
                db.updateAdminPassword(md5String(pass));
                adminLogin.setText("");
                adminEmail.setText("");
                adminPassword.setText("");
                updateAdminButton.setText("Данные обновлены!");
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