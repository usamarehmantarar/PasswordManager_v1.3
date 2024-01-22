package com.base.passwordmanager;

import java.util.HashMap;
import java.util.Map;

public class UserAuthentication {

    private static final Map<String, String> userCredentials = new HashMap<>();

    // Initialize username and password (replace with your actual credentials)
    static {
        userCredentials.put("admin", "admin");
        userCredentials.put("user1", "user1password");
        // Add more usernames and passwords as needed
    }

    public static boolean authenticateUser(String username, String password) {
        // Check if the provided username exists
        if (userCredentials.containsKey(username)) {
            // Check if the provided password matches the stored password for the username
            return userCredentials.get(username).equals(password);
        }

        return false; // Username not found
    }
}
