package model;

import utils.FileLoader;
import java.util.List;

public class Manager {
    private ParcelMap parcelMap;
    private QueueofCustomers customerQueue;
    private Worker worker;

    // Constructor
    public Manager() {
        this.parcelMap = new ParcelMap();
        this.customerQueue = new QueueofCustomers();
        this.worker = new Worker(parcelMap, customerQueue);
    }

    // Load data from CSV files
    public void loadData(String parcelFilePath, String customerFilePath) {
        // Load parcels
        List<Parcel> parcels = FileLoader.loadParcels(parcelFilePath);
        for (Parcel parcel : parcels) {
            parcelMap.addParcel(parcel);
        }
        System.out.println("Loaded parcels: " + parcels.size());

        // Load customers
        List<Customer> customers = FileLoader.loadCustomers(customerFilePath);
        for (Customer customer : customers) {
            customerQueue.addCustomer(customer);
        }
        System.out.println("Loaded customers: " + customers.size());
    }

    // Simulate processing all customers
    public void processAllCustomers() {
        while (!customerQueue.isEmpty()) {
            worker.processNextCustomer();
        }
    }

    // Save logs to a file
    public void saveLogs(String logFilePath) {
        Log.getInstance().saveToFile(logFilePath);
    }
}
