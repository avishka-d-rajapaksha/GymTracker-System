package com.mycompany.gymtracker.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main Class: Gym Tracker Entry Point
 * Requirement: System Architecture - UI Layer (JavaFX).
 * Purpose: Initializes the primary stage and loads the initial FXML view.
 */
public class Main extends Application {

    /**
     * Logic: Sets up the main window and loads the login screen.
     * @param primaryStage The main stage provided by the JavaFX runtime.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Logic: Load the Login FXML from the resources folder
            // Path: src/main/resources/view/LoginView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            Parent root = loader.load();

            // Logic: Create the scene and apply dimensions
            Scene scene = new Scene(root);
            
            // Logic: Application Icon (Ensure logo.png exists in /view/)
            try {
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/view/logo.png")));
            } catch (Exception e) {
                System.err.println("Icon Warning: Logo not found for window taskbar.");
            }

            // Logic: Stage configuration
            primaryStage.setTitle("Gym Tracker - Secure Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Keeps UI layout consistent
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Critical Error: Could not launch the application.");
            e.printStackTrace();
        }
    }

    /**
     * Logic: The standard main method to launch the JavaFX application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}