package de.mightysarabor.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        // Load the file from the project root
        try (FileInputStream fis = new FileInputStream("/home/sarabor/dev/AirTrafficTracking/src/main/java/de/mightysarabor/resources/.env")) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Warning: .env not found. Falling back to system environment.");
        }
    }

    public static String getSecret(String key) {
        // Look in the file first, fallback to OS environment if missing
        String value = properties.getProperty(key);
        return (value != null) ? value : System.getenv(key);
    }
}