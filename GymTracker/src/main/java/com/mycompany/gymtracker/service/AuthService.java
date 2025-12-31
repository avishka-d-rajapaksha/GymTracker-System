package com.mycompany.gymtracker.service;

import com.mycompany.gymtracker.util.SecurityUtils;
import java.io.*;

public class AuthService {
    private static final String USER_FILE = "data/users.txt";

    public String authenticate(String username, String password) {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String storedUser = parts[0].trim();
                    String storedHash = parts[1].trim();
                    String role = parts[2].trim();

                    if (storedUser.equalsIgnoreCase(username) && SecurityUtils.checkPassword(password, storedHash)) {
                        return role;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}