package com.mycompany.gymtracker.controller;
import com.mycompany.gymtracker.service.EmailService;import com.mycompany.gymtracker.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller Class: LoginController
 * Logic: Handles user authentication and redirects to full-screen dashboards 
 * or the admin verification interface.
 */
public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;

    private final AuthService authService = new AuthService();

    /**
     * Logic: Handles the main login process.
     */
@FXML
public void handleLogin() {
    String username = usernameField.getText();
    String role = authService.authenticate(username, passwordField.getText());

    if (role != null) {
        // Logic: Send Security Alert
        new Thread(() -> {
            EmailService.sendEmail(
                "Gym Tracker: Login Alert", 
                "SECURITY NOTIFICATION", 
                "User <b>" + username + "</b> (" + role + ") has just logged into the system."
            );
        }).start();
        
        navigateToDashboard(role);
    }
}

    /**
     * Logic: Switches to the appropriate dashboard based on user role and forces FULL SCREEN.
     */
    private void navigateToDashboard(String role) {
        try {
            String fxmlFile = role.equalsIgnoreCase("Admin") ? 
                             "/view/AdminDashboard.fxml" : "/view/InstructorDashboard.fxml";
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            
            Stage stage = (Stage) loginButton.getScene().getWindow();
            
            // Logic: Create the new scene with the dashboard content
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Gym Tracker - " + role);

            // Logic: Set to Full Screen and Maximize for the primary interface
            stage.setMaximized(true); 
            stage.setFullScreen(true); 
            
            // Optional: Hide the full-screen exit hint for a cleaner professional look
            stage.setFullScreenExitHint(""); 
            
            stage.show();
        } catch (IOException e) { 
            System.err.println("Navigation Error: Check if " + role + " dashboard FXML exists.");
            e.printStackTrace(); 
        }
    }

    /**
     * Logic: Redirects to the Admin Verification screen. 
     * Keeps the verification window at a normal size (not full screen).
     */
    @FXML
    public void handleSignUpRedirect() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AdminVerificationView.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            
            // Ensure we are NOT in full screen for the verification popup
            stage.setFullScreen(false);
            stage.setMaximized(false);
            
            stage.setScene(new Scene(root));
            stage.setTitle("Gym Tracker - Admin Verification");
            stage.centerOnScreen();
        } catch (IOException e) {
            System.err.println("Error: Could not find AdminVerificationView.fxml");
            e.printStackTrace();
        }
    }
}