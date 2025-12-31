package com.mycompany.gymtracker.controller;

import com.mycompany.gymtracker.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller Class: AdminVerificationController
 * Purpose: Verifies Admin credentials before allowing access to Sign Up.
 */
public class AdminVerificationController {

    @FXML private TextField adminUsernameField;
    @FXML private PasswordField adminPasswordField;
    @FXML private Label errorLabel;

    private final AuthService authService = new AuthService();

    /**
     * Logic: Validates if the user is an Admin.
     */
    @FXML
    public void handleVerifyAdmin() {
        String user = adminUsernameField.getText();
        String pass = adminPasswordField.getText();

        // Logic: Check credentials using existing AuthService
        String role = authService.authenticate(user, pass);

        if (role != null && role.equalsIgnoreCase("Admin")) {
            // Success: Proceed to Sign Up screen
            navigateToSignUp();
        } else {
            // Failure: Not an admin or wrong password
            errorLabel.setText("Verification Failed: Admin access required.");
            errorLabel.setStyle("-fx-text-fill: #ff4d4d;");
        }
    }

    private void navigateToSignUp() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/SignUpView.fxml"));
            Stage stage = (Stage) adminUsernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gym Tracker - Register New Staff");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancel() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
            Stage stage = (Stage) adminUsernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}