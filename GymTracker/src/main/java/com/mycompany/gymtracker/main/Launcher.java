package com.mycompany.gymtracker.main;

/**
 * Logic: Simple wrapper class to launch the JavaFX application.
 * This bypasses the JavaFX runtime component check.
 */
public class Launcher {
    public static void main(String[] args) {
        // Logic: Redirect to the Main class launch method
        Main.main(args);
    }
}