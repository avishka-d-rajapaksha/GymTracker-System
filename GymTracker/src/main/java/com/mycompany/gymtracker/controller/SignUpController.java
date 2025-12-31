package com.mycompany.gymtracker.controller;

import com.mycompany.gymtracker.util.SecurityUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SignUpController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label errorLabel;
    @FXML private CheckBox termsBox;

    @FXML
    public void initialize() {
        if (roleComboBox != null) {
            roleComboBox.getItems().addAll("Admin", "Instructor");
        }
    }

    @FXML
    public void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            showError("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            return;
        }

        if (!termsBox.isSelected()) {
            showError("Please agree to the Terms.");
            return;
        }

        String hashedPassword = SecurityUtils.hashPassword(password);

        if (saveUserToFile(username, hashedPassword, role)) {
            switchToLogin();
        } else {
            showError("Database Error.");
        }
    }

    private boolean saveUserToFile(String user, String hash, String role) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/users.txt", true))) {
            writer.write(user + "," + hash + "," + role);
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @FXML
    public void switchToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #ff4d4d;");
    }
}