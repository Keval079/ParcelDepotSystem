package test;

import model.Log;

public class TestLog {
    public static void main(String[] args) {
        // Get the singleton instance of Log
        Log log = Log.getInstance();

        // Write log entries using the new method signature
        log.writeLog("Alice", "X001", "Customer added to the queue.");
        log.writeLog("System", "X001", "Parcel processed.");
        log.writeLog("Bob", "X002", "Customer removed from the queue.");

        // Print log content
        System.out.println("Log Content:\n" + log);

        // Save log to a file
        String logFilePath = "resources/log.txt"; // Adjust path as needed
        log.saveToFile(logFilePath);
        System.out.println("Log saved to: " + logFilePath);
    }
}
