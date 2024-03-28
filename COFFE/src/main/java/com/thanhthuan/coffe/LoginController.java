package com.thanhthuan.coffe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent; // Thêm import cho MouseEvent
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    private Scene scene;
    private Stage stage;

    private double x = 0;
    private double y = 0;

    @FXML
    public void initialize() {

    }

    public void loginButtonOnAction(ActionEvent e) {
        if (!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Vui lòng nhập tài khoản và mật khẩu!");
        }
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() {
        DatabaseConnection connecNow = new DatabaseConnection();
        Connection connectDb = connecNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM useraccounts WHERE username = '" + usernameTextField.getText() + "' AND Password = '" + passwordPasswordField.getText() + "'";
        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewMenu.fxml"));
                    scene = new Scene(fxmlLoader.load());
                    stage = new Stage();

                    scene.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    scene.setOnMouseDragged(event -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);

                        stage.setOpacity(.8f);
                    });
                    scene.setOnMousePressed((MouseEvent event) -> {
                        stage.setOpacity(1);
                            });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    loginMessageLabel.setText("Sai tài khoản hoặc mật khẩu");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
