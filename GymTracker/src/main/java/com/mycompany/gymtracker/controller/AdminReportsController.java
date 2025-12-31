package com.mycompany.gymtracker.controller;
import com.mycompany.gymtracker.service.EmailService;
import com.mycompany.gymtracker.model.Member;
import com.mycompany.gymtracker.service.FileHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox; // ADD THIS MISSING IMPORT
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AdminReportsController {

    @FXML private TableView<Member> reportsTable;
    @FXML private TableColumn<Member, String> colId, colName, colType;
    @FXML private TableColumn<Member, LocalDate> colExpiry;
    
    // Report Variables
    @FXML private Label activeCountLabel, expiredCountLabel, revenueLabel;
    @FXML private VBox printableArea; // Now this will be recognized

    private final FileHandler fileHandler = new FileHandler();

    @FXML
    public void initialize() {
        // Map Columns to Member properties
        colId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("membershipType"));
        colExpiry.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        
        loadReportData();
    }

    private void loadReportData() {
        List<Member> allMembers = fileHandler.getAllMembers();
        ObservableList<Member> memberList = FXCollections.observableArrayList(allMembers);
        reportsTable.setItems(memberList);

        // Calculate dynamic report variables
        long activeCount = allMembers.stream().filter(m -> !m.isExpired()).count();
        long expiredCount = allMembers.size() - activeCount;
        
        // Estimated Revenue logic
        double estimatedRevenue = allMembers.stream().mapToDouble(m -> {
            if (m.getMembershipType().equalsIgnoreCase("Monthly")) return 3000;
            if (m.getMembershipType().equalsIgnoreCase("Quarterly")) return 8000;
            return 25000;
        }).sum();

        activeCountLabel.setText(String.valueOf(activeCount));
        expiredCountLabel.setText(String.valueOf(expiredCount));
        revenueLabel.setText("Rs. " + String.format("%.2f", estimatedRevenue));
    }

    /**
     * Logic: Implements the Print functionality.
     * This opens the system print dialog to print the designated printableArea.
     */
   @FXML
public void handlePrint() {
    // 1. Logic: Standard Printing Process
    PrinterJob job = PrinterJob.createPrinterJob();
    if (job != null && job.showPrintDialog(reportsTable.getScene().getWindow())) {
        if (printableArea != null) {
            boolean success = job.printPage(printableArea);
            if (success) {
                job.endJob();
                
                // 2. Logic: AUTOMATIC EMAIL (Triggered by successful print)
                String reportSummary = "Report contains status for " + reportsTable.getItems().size() + " members. " +
                                     "Estimated Revenue: " + revenueLabel.getText();
                
                // Logic: Run in a separate thread so the UI doesn't freeze while sending
                new Thread(() -> {
                    EmailService.sendEmail(
                        "Gym Tracker: System Report Generated", 
                        "OFFICIAL SYSTEM REPORT", 
                        reportSummary
                    );
                }).start();
            }
        }
    }
}

    @FXML
    public void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"));
            Stage stage = (Stage) reportsTable.getScene().getWindow();
            stage.setFullScreen(true); // Maintain full screen as requested
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}