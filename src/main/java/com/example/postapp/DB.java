package com.example.postapp;
import java.sql.*;
public class DB {
    String dbHost = "localhost";
    String dbPort = "3306";
    String dbUser = "root";
    String dbPassword = "12345A!e";
    String dbName = "dbapp";


    private Connection dBconn = null;

    private Connection getDBConnection() {
        String str = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            dBconn = DriverManager.getConnection(str, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dBconn;
    }

    public void isConnected() throws SQLException {
        dBconn = getDBConnection();
        System.out.println(dBconn.isValid(1000));
    }

    public void regUser(String login, String email, String pass) {
        String sql = "INSERT INTO dbapp.users (login,email,password) VALUES (?,?,?)";

        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1, login);
            prSt.setString(2, email);
            prSt.setString(3, pass);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isExistsUser(String login) {
        String sql = "SELECT id FROM dbapp.users WHERE login = ?";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1, login);
            ResultSet resultSet = prSt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authUser(String login, String pass) {
        String sql = "SELECT id FROM dbapp.users WHERE login = ? AND password = ?";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1, login);
            prSt.setString(2, pass);
            ResultSet resultSet = prSt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void regAdmin() {
        String sql = "INSERT INTO dbapp.users (login,email,password) VALUES (?,?,?)";

        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1, "Admin");
            prSt.setString(2, "Admin@gmail.com");
            prSt.setString(3, "12345");
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAdminLogin() {
        String sql = "SELECT login FROM dbapp.users WHERE id = 1";
        String login = "";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            ResultSet resultSet = prSt.executeQuery();
            if (resultSet.next()) {
                login = resultSet.getString("login");
            }
            return login;
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    public String getAdminEmail() {
        String sql = "SELECT email FROM dbapp.users WHERE login = ?";
        String email = "";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1, "Admin");
            ResultSet resultSet = prSt.executeQuery();
            if (resultSet.next())
                email = resultSet.getString("email");
            return email;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearDB() {
        String clear = "TRUNCATE dbapp.users";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(clear);
            prSt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAdminLogin(String login) {
        String sql = "UPDATE dbapp.users SET login = ? WHERE id = 1";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1,login);

            prSt.executeUpdate();

        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateAdminEmail(String email) {
        String sql = "UPDATE dbapp.users SET email = ? WHERE id = 1";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1,email);
            prSt.executeUpdate();

        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateAdminPassword(String pass) {
        String sql = "UPDATE dbapp.users SET password = ? WHERE id = 1";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1,pass);

            prSt.executeUpdate();

        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getArticles() {
        String sql = "SELECT * FROM dbapp.articles";
        Statement statement = null;
        try {
            statement = getDBConnection().createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addArticle(String title, String intro, String fullText) {

        String sql = "INSERT INTO dbapp.articles (title,intro,text) VALUES (?,?,?)";

        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1, title);
            prSt.setString(2, intro);
            prSt.setString(3, fullText);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ResultSet getArticle(String idOfArticle) {
        String sql = "SELECT text FROM dbapp.articles WHERE id = " + idOfArticle;
        try {
            PreparedStatement statement = getDBConnection().prepareStatement(sql);
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
