package com.mycompany.gymtracker.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class InstructorController {

    @FXML
    public void handleAttendance() {
        switchScene("/view/Attendance.fxml");
    }

    @FXML
    public void handleLogout() {
        switchScene("/view/LoginView.fxml");
    }

    private void switchScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            // Logic: This assumes the controller is used in a scene context
            Stage stage = new Stage(); 
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
}