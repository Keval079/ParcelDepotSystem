package test;

import model.Parcel;
import model.Customer;
import utils.FileLoader;
import java.util.List;


public class TestFileLoader {

    public static void main(String[] args) {
        // Test parcel loading
        String parcelFilePath = "resources/Parcels.csv"; // Update with your file location
        List<Parcel> parcels = FileLoader.loadParcels(parcelFilePath);
        System.out.println("Loaded Parcels:");
        for (Parcel parcel : parcels) {
            System.out.println(parcel);
        }

        // Test customer loading
        String customerFilePath = "resources/Custs.csv"; // Update with your file location
        List<Customer> customers = FileLoader.loadCustomers(customerFilePath);
        System.out.println("\nLoaded Customers:");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
}
