package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Represents a User's password.
 * Guarantees: immutable; meets specified constraints.
 */
public class Password {
    public static final String DEFAULT_PASSWORD = "Password123!";
    public static final String MESSAGE_CONSTRAINTS =
            "Password must be at least 8 characters long and contain at least one digit, one lowercase letter," +
                    " one uppercase letter, and one special character.\n" +
                    "Default password: " + DEFAULT_PASSWORD;
    public static final String VALIDATION_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
    public static final String filename = "pword.txt";
    public final String password;

    /**
     * Constructs a {@code Password}.
     *
     * @param password A valid password.
     */
    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_CONSTRAINTS);
        this.password = password;
    }

    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    private static final String SALT = "YourFixedSaltHere";

    public static String hashPassword(String password) {
        return password.hashCode() + "";
    }

    public static void savePasswordToFile(String hashedPassword, String filename) {
        try {
            Files.write(Path.of(filename), hashedPassword.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readPasswordFromFile(String filename) {
        try {
            byte[] bytes = Files.readAllBytes(Path.of(filename));
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verifyPassword(String enteredPassword) {
        System.out.println(enteredPassword);
        System.out.println(enteredPassword.equals(DEFAULT_PASSWORD));
        String storedHashedPassword = readPasswordFromFile(filename);
        System.out.println(storedHashedPassword);
        if (storedHashedPassword == null) {
            storedHashedPassword = hashPassword(DEFAULT_PASSWORD);
            savePasswordToFile(storedHashedPassword, filename);
        }

        String enteredHashedPassword = hashPassword(enteredPassword);
        System.out.println(enteredHashedPassword);
        System.out.println(storedHashedPassword.equals(enteredHashedPassword));
        return storedHashedPassword.equals(enteredHashedPassword);
    }

    public static String changePassword(String currentPassword, String newPassword) {
        if (verifyPassword(currentPassword)) {
            // Verify that the new password meets the constraints
            if (isValidPassword(newPassword)) {
                // Hash the new password
                String newHashedPassword = hashPassword(newPassword);
                // Save the new hashed password to the file
                savePasswordToFile(newHashedPassword, filename);
                return "Password changed successfully.";
            } else {
                return "New password doesn't meet the constraints.\n" + MESSAGE_CONSTRAINTS;
            }
        } else {
            return "Current password is incorrect.\n" + MESSAGE_CONSTRAINTS;
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Password)) {
            return false;
        }

        Password otherPassword = (Password) other;
        return password.equals(otherPassword.password);
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }
}