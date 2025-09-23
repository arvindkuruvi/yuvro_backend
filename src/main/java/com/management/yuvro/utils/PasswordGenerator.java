package com.management.yuvro.utils;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String SPECIAL = "!@#$*&^";
    private static final String ALL_CHARS = ALPHANUMERIC + SPECIAL;

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = RANDOM.nextInt(ALL_CHARS.length());
            password.append(ALL_CHARS.charAt(idx));
        }
        return password.toString();
    }
}