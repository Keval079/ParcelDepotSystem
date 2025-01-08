package test;
import model.*;

public class TestCoreClasses {
    public static void main(String[] args) {
        // Test Parcel Class
        Parcel parcel = new Parcel("X001", "10x10x10", 5.0, 3, "waiting");
        System.out.println(parcel);
        System.out.println("Fee: " + parcel.calculateFee());
        parcel.updateStatus("collected");
        System.out.println("Updated Parcel: " + parcel);

        // Test Customer Class
        Customer customer = new Customer(1, "John Doe", "X001");
        System.out.println(customer);

        // Test QueueofCustomers
        QueueofCustomers queue = new QueueofCustomers();
        queue.addCustomer(new Customer(1, "Alice", "X002"));
        queue.addCustomer(new Customer(2, "Bob", "X003"));
        System.out.println("Next Customer: " + queue.getNextCustomer());
        queue.removeCustomer();
        System.out.println("Next Customer after removal: " + queue.getNextCustomer());

        // Test ParcelMap
        ParcelMap parcelMap = new ParcelMap();
        parcelMap.addParcel(new Parcel("X002", "5x5x5", 2.0, 1, "waiting"));
        parcelMap.addParcel(new Parcel("X003", "8x8x8", 3.0, 2, "waiting"));
        System.out.println("Find Parcel X002: " + parcelMap.findParcel("X002"));
        System.out.println("Contains Parcel X003: " + parcelMap.containsParcel("X003"));
    }
}
