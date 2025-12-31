package com.mycompany.gymtracker.controller;

import com.mycompany.gymtracker.service.AnalyticsService;
import com.mycompany.gymtracker.service.AttendanceService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller Class: InstructorDashboardController
 * Purpose: Handles Instructor-specific features like Attendance and Shortcuts.
 */
public class InstructorDashboardController {

    @FXML private Label todayAttendanceLabel;
    @FXML private Label activeMembersLabel;
    @FXML private Label statusLabel;

    private final AttendanceService attendanceService = new AttendanceService();
    private final AnalyticsService analyticsService = new AnalyticsService();

    @FXML
    public void initialize() {
        refreshDashboardStats();

        // Logic: Attach System Shortcuts (Key Navigation)
        Platform.runLater(() -> {
            Scene scene = todayAttendanceLabel.getScene();
            if (scene != null) {
                scene.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case F1 -> handleViewReports();    // Refreshes the view
                        case F2 -> handleGoToAttendance(); // Opens Attendance screen
                        case ESCAPE -> handleLogout();     // Exits and Logs out
                        default -> {}
                    }
                });
            }
        });
    }

    private void refreshDashboardStats() {
        int todayCount = attendanceService.getTodaysLogs().size();
        int renewalCount = analyticsService.getMembersDueForRenewal().size();

        if (todayAttendanceLabel != null) {
            todayAttendanceLabel.setText(String.valueOf(todayCount));
        }
        if (activeMembersLabel != null) {
            activeMembersLabel.setText(renewalCount + " Members need renewal soon");
        }
    }

    @FXML
    public void handleGoToAttendance() {
        switchScene("/view/Attendance.fxml", "Gym Tracker - Mark Attendance", true);
    }

    @FXML
    public void handleViewReports() {
        refreshDashboardStats();
        if (statusLabel != null) statusLabel.setText("Stats Refreshed.");
    }

    @FXML
    public void handleLogout() {
        // Exiting full screen specifically for the Login window
        switchScene("/view/LoginView.fxml", "Gym Tracker - Login", false);
    }

    /**
     * Logic: Centralized scene switching with Full Screen control.
     */
    private void switchScene(String fxmlPath, String title, boolean shouldBeFullScreen) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            Stage stage = (Stage) todayAttendanceLabel.getScene().getWindow();
            
            // Logic: Adjust stage properties based on destination
            if (!shouldBeFullScreen) {
                stage.setFullScreen(false);
                stage.setMaximized(false);
            }
            
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            
            if (shouldBeFullScreen) {
                stage.setMaximized(true);
                stage.setFullScreen(true);
                stage.setFullScreenExitHint("");
            } else {
                stage.centerOnScreen();
            }
            
            stage.show();
        } catch (IOException e) {
            System.err.println("Critical Error: FXML file not found at " + fxmlPath);
            e.printStackTrace();
        }
    }
}