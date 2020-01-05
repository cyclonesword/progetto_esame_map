package com.biblioteca.ui.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Utility class that exposes methods for checking if an email is valid and a method to calculate the SHA-1 digest of a string.
 */
public final class Utils {


    private Utils() {

    }

    /**
     * Calculates the SHA-1 digest of the given, using the default JDK implementation.
     * @param string The string to use to calculate the digest.
     * @return the SHA-1 digest of the given string.
     */
    public static String sha1Digest(String string) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(string.getBytes(StandardCharsets.UTF_8));
            return String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (
                NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    // E' necessario compilare l'espressione regolare prima dell'utilizzo, per questioni di performance.
    private static final String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final Pattern pattern = Pattern.compile(emailPattern);

    /**
     * Check if the given email address is valid
     * @param email The email address to be validated
     * @return true in case of a valid email addresss, false otherwise
     */
    public static boolean isValidEmailAddress(String email) {
       return pattern.matcher(email).matches();
    }


    /**
     * Checks if the given string is a number (integer)
     * @param string the string to check
     * @return True if the given string is a number (int), false otherwise
     */
    public static boolean isValidStringNumber(String string) {
        try {
            Long.parseLong(string);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

}
