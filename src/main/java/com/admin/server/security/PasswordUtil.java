package com.admin.server.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (hashedPassword == null || plainPassword == null) return false;
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
