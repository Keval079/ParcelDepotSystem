import model.*;
import utils.FileLoader;
import view.DepotView;
import controller.DepotController;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        // Initialize components
        ParcelMap parcelMap = new ParcelMap();
        QueueofCustomers customerQueue = new QueueofCustomers();
        Log log = Log.getInstance();

        // File paths
        String parcelFilePath = "resources/Parcels.csv";
        String customerFilePath = "resources/Custs.csv";

        // Load parcels and customers
        System.out.println("Loading data...");
        try {
            long startTime = System.currentTimeMillis();

            // Load parcels
            for (Parcel parcel : FileLoader.loadParcels(parcelFilePath)) {
                parcelMap.addParcel(parcel);
            }

            // Load customers
            for (Customer customer : FileLoader.loadCustomers(customerFilePath)) {
                customerQueue.addCustomer(customer);
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Data loaded successfully in " + (endTime - startTime) + " ms.");

            // Log dataset size
            log.writeLog("System", "-", "Loaded " + parcelMap.getParcels().size() + " parcels.");
            log.writeLog("System", "-", "Loaded " + customerQueue.getQueue().size() + " customers.");
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Failed to load data. Please check the log and input files.", "Data Load Error", JOptionPane.ERROR_MESSAGE);
        }

        // Debugging: Verify the contents of ParcelMap
        System.out.println("Verifying loaded parcels...");
        parcelMap.printAllParcels();

        // Create the GUI view
        DepotView view = new DepotView();

        // Attach a WindowListener to save logs on application close
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String logFilePath = "resources/log.txt";
                try {
                    log.saveToFile(logFilePath);
                    System.out.println("Log saved to " + logFilePath);
                } catch (Exception ex) {
                    System.err.println("Error saving log: " + ex.getMessage());
                }
            }
        });

        // Create the controller
        new DepotController(customerQueue, parcelMap, log, view);

        // Confirm application start
        System.out.println("Parcel Depot System is running...");
    }
}
