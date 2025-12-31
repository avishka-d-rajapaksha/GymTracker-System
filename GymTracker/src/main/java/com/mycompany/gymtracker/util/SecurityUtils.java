package com.mycompany.gymtracker.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility Class: SecurityUtils
 * Requirement: Mandatory Secured Login (Guideline 10).
 * Purpose: Provides high-level logic for password hashing and verification using jBCrypt.
 */
public class SecurityUtils {

    /**
     * Logic: Hashes a plain text password using a strong salt.
     * Use this when creating new users or updating passwords.
     * @param plainTextPassword The raw password from the user.
     * @return String The secure hashed version for storage in users.txt.
     */
    public static String hashPassword(String plainTextPassword) {
        if (plainTextPassword == null || plainTextPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty for hashing.");
        }
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }

    /**
     * Logic: Compares a plain text input against a stored hash.
     * Use this during the Login process.
     * @param plainTextPassword The input from the Login screen.
     * @param hashedPassword The hash retrieved from users.txt.
     * @return boolean true if the password is correct.
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        if (plainTextPassword == null || hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainTextPassword, hashedPassword);
        } catch (Exception e) {
            System.err.println("Security Error: Invalid hash format detected.");
            return false;
        }
    }

    /**
     * Logic: Helper method to generate a default admin line for users.txt.
     * You can run this file as a Java application to get your first user string.
     */
    public static void main(String[] args) {
        String user = "admin";
        String pass = "admin123";
        String role = "Admin";
        
        String hashed = hashPassword(pass);
        System.out.println("--- USE THIS LINE IN data/users.txt ---");
        System.out.println(user + "," + hashed + "," + role);
    }
}