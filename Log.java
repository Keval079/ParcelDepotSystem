package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static Log instance; // Singleton instance
    private final StringBuilder logBuffer;

    // Private constructor to prevent instantiation
    private Log() {
        logBuffer = new StringBuilder();
    }

    // Thread-safe method to get the singleton instance
    public static synchronized Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    // Add an entry to the log with a timestamp
    public synchronized void writeLog(String customerName, String parcelId, String statusChange) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logEntry = String.format("[%s] Customer: %s, Parcel ID: %s, Status: %s",
                timestamp, customerName != null ? customerName : "N/A",
                parcelId != null ? parcelId : "N/A", statusChange);
        logBuffer.append(logEntry).append("\n");
        System.out.println("Log written: " + logEntry); // Debugging
    }

    // Save the log to a file
    public synchronized void saveToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(logBuffer.toString());
            System.out.println("Log saved to file: " + filePath); // Debugging
        } catch (IOException e) {
            System.err.println("Error saving log to file: " + e.getMessage());
        }
    }

    // Clear the log buffer (optional)
    public synchronized void clear() {
        logBuffer.setLength(0);
    }

    @Override
    public synchronized String toString() {
        return logBuffer.toString();
    }
}
