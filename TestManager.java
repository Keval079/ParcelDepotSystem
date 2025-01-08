package test;

import model.Manager;

public class TestManager {
    public static void main(String[] args) {
        // File paths for data
        String parcelFilePath = "resources/Parcels.csv";
        String customerFilePath = "resources/Custs.csv";
        String logFilePath = "resources/log.txt";

        // Initialize the manager
        Manager manager = new Manager();

        // Load data
        manager.loadData(parcelFilePath, customerFilePath);

        // Process all customers
        System.out.println("\nProcessing all customers...");
        manager.processAllCustomers();

        // Save logs
        manager.saveLogs(logFilePath);
        System.out.println("\nLogs saved to: " + logFilePath);
    }
}
