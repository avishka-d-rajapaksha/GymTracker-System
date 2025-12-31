package com.mycompany.gymtracker.controller;

import com.mycompany.gymtracker.model.Member;
import com.mycompany.gymtracker.service.AttendanceService;
import com.mycompany.gymtracker.service.FileHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class AttendanceController {

    @FXML private TextField memberIdField;
    @FXML private Label statusLabel;
    @FXML private Label memberDetailsLabel;

    private final AttendanceService attendanceService = new AttendanceService();
    private final FileHandler fileHandler = new FileHandler();

    @FXML
    public void handleMarkPresent() {
        String id = memberIdField.getText().trim();
        if (id.isEmpty()) {
            updateStatus("Error: Enter Member ID.", "#ff4d4d");
            return;
        }

        Optional<Member> memberOpt = fileHandler.getAllMembers().stream()
                .filter(m -> m.getMemberId().equalsIgnoreCase(id))
                .findFirst();

        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            if (member.isExpired()) {
                updateStatus("ACCESS DENIED: Expired on " + member.getExpiryDate(), "#ff4d4d");
            } else {
                attendanceService.logAttendance(id);
                updateStatus("SUCCESS: Marked present.", "#d4af37");
                memberIdField.clear();
            }
        } else {
            updateStatus("Error: Member not found.", "#ff4d4d");
        }
    }

    @FXML
 
public void handleBack() {
    try {
        // Matches your filename "InstructorDashboard.fxml"
        Parent root = FXMLLoader.load(getClass().getResource("/view/InstructorDashboard.fxml"));
        Stage stage = (Stage) memberIdField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private void updateStatus(String message, String color) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: " + color + ";");
    }
}