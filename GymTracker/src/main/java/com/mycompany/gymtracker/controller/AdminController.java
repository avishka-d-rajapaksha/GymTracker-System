package com.mycompany.gymtracker.controller;

import com.mycompany.gymtracker.model.Member;
import com.mycompany.gymtracker.service.FileHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class AdminController {
    @FXML private TextField idField, nameField;
    @FXML private ComboBox<String> typeComboBox;

    private FileHandler fileHandler = new FileHandler();

    @FXML
    public void initialize() {
        typeComboBox.getItems().addAll("Monthly", "Quarterly", "Annual");
    }

    @FXML
    public void handleAddMember() {
        String id = idField.getText();
        String name = nameField.getText();
        String type = typeComboBox.getValue();

        if (!id.isEmpty() && !name.isEmpty() && type != null) {
            // Updated: Added LocalDate.now() to match the Member constructor logic
            Member member = new Member(id, name, type, LocalDate.now());
            fileHandler.saveMember(member);
            System.out.println("Member saved successfully.");
        }
    }
}