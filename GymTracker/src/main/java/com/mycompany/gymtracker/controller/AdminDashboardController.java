package com.mycompany.gymtracker.controller;

import com.mycompany.gymtracker.model.Member;
import com.mycompany.gymtracker.service.AnalyticsService;
import com.mycompany.gymtracker.service.FileHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller Class: AdminDashboardController
 * Purpose: Handles Admin logic, shortcuts (F1-F3, ESC), and member registration.
 */
public class AdminDashboardController {

    @FXML private TextField idField, nameField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private Label totalMembersLabel, weeklyAttendanceLabel, expiredCountLabel, statusLabel;

    private final FileHandler fileHandler = new FileHandler();
    private final AnalyticsService analyticsService = new AnalyticsService();

    @FXML
    public void initialize() {
        // Setup Plan Options
        if (typeComboBox != null) {
            typeComboBox.getItems().addAll("Monthly", "Quarterly", "Annual");
        }
        
        // Load initial analytics
        refreshAnalytics();

        // Logic: Attach System Shortcuts (Key Navigation)
        Platform.runLater(() -> {
            if (totalMembersLabel.getScene() != null) {
                totalMembersLabel.getScene().setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case F1 -> handleDashboard();
                        case F2 -> handleMembers();
                        case F3 -> handleReports();
                        case ESCAPE -> handleLogout();
                        default -> {}
                    }
                });
            }
        });
    }

    @FXML
    public void handleAddMember() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String type = typeComboBox.getValue();

        if (id.isEmpty() || name.isEmpty() || type == null) {
            statusLabel.setText("Error: Missing fields.");
            statusLabel.setStyle("-fx-text-fill: #ff4d4d;");
            return;
        }

        Member newMember = new Member(id, name, type, LocalDate.now());
        fileHandler.saveMember(newMember);
        
        statusLabel.setText("Success: " + name + " registered.");
        statusLabel.setStyle("-fx-text-fill: #d4af37;");
        
        refreshAnalytics();
        clearFields();
        idField.requestFocus(); // Ready for next entry
    }

    @FXML
    public void handleReports() {
        navigateTo("/view/AdminReportsView.fxml", "Gym Tracker - System Reports");
    }

    @FXML
    public void handleMembers() {
        // Logic: Can be linked to a member management list later
        statusLabel.setText("Navigating to Members List...");
    }

    @FXML
    public void handleDashboard() {
        refreshAnalytics();
        statusLabel.setText("Dashboard Refreshed.");
    }

    private void refreshAnalytics() {
        // Logic: Protect against NullPointer if FXML IDs mismatch
        if (totalMembersLabel != null)
            totalMembersLabel.setText(String.valueOf(fileHandler.getAllMembers().size()));
        
        if (weeklyAttendanceLabel != null)
            weeklyAttendanceLabel.setText(String.valueOf(analyticsService.getAttendanceCountForPeriod(7)));
        
        if (expiredCountLabel != null)
            expiredCountLabel.setText(String.valueOf(analyticsService.getExpiredMembers().size()));
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        typeComboBox.getSelectionModel().clearSelection();
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) totalMembersLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setFullScreen(true); 
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
            Stage stage = (Stage) totalMembersLabel.getScene().getWindow();
            
            // Logic: Exit Full Screen to return to Login window size
            stage.setFullScreen(false);
            stage.setMaximized(false);
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    }
}