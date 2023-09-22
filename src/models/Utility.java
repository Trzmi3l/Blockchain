package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utility {
    public static String stringToBase64(String inputText) {
        byte[] encodedBytes = Base64.getEncoder().encode(inputText.getBytes());
        return new String(encodedBytes);
    }
    public static String base64ToString(String base64Text) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Text);
        return new String(decodedBytes);
    }

    public static String hashSHA256(String inputText) {
        try {
            // Utwórz instancję algorytmu SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Przekształć ciąg znaków na tablicę bajtów i oblicz skrót
            byte[] hashBytes = digest.digest(inputText.getBytes());

            // Konwersja skrótu na reprezentację szesnastkową
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

}
