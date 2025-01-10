package com.kost.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Pattern;

public class PasswordUtils {
    public static boolean checkPassword(String plainPassword, String encryptedPassword) {
        if (encryptedPassword.startsWith("$2y$")) {
            encryptedPassword = encryptedPassword.replaceFirst("^\\$2y\\$", "\\$2a\\$");
        }

        return BCrypt.checkpw(plainPassword, encryptedPassword);
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean isPasswordValid(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).{8,}$";
        return Pattern.matches(pattern, password);
    }
}
