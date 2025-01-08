package utils;

import model.Customer;
import model.Parcel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    // Load parcels from CSV
    public static List<Parcel> loadParcels(String filePath) {
        List<Parcel> parcels = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line if present
            int lineNumber = 1; // Track line numbers for debugging

            while ((line = br.readLine()) != null) {
                System.out.println("Processing line " + lineNumber + ": " + line); // Debugging
                lineNumber++;

                try {
                    // Split the line into fields
                    String[] data = line.split(",");
                    if (data.length < 5) {
                        System.err.println("Skipping invalid parcel entry: " + line);
                        continue;
                    }

                    // Parse and validate fields
                    String parcelId = data[0].trim();
                    String dimensions = validateDimensions(data[1].trim(), line);
                    double weight = parseDouble(data[2].trim(), "weight", line);
                    int daysInDepot = parseInt(data[3].trim(), "daysInDepot", line);
                    String status = data[4].trim();

                    // Create and add the parcel
                    Parcel parcel = new Parcel(parcelId, dimensions, weight, daysInDepot, status);
                    parcels.add(parcel);
                    System.out.println("Loaded parcel: " + parcel); // Debugging
                } catch (Exception ex) {
                    System.err.println("Error processing line " + lineNumber + ": " + line + ". Reason: " + ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading parcels from file: " + e.getMessage());
        }
        return parcels;
    }

    // Load customers from CSV
    public static List<Customer> loadCustomers(String filePath) {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line if present
            int sequenceNumber = 1; // Track sequence numbers

            while ((line = br.readLine()) != null) {
                try {
                    // Split and validate line data
                    String[] data = line.split(",");
                    if (data.length < 2) {
                        System.err.println("Skipping invalid customer entry: " + line);
                        continue;
                    }

                    // Parse customer data
                    String name = data[0].trim();
                    String parcelId = data[1].trim();

                    // Create and add the customer
                    Customer customer = new Customer(sequenceNumber++, name, parcelId);
                    customers.add(customer);
                    System.out.println("Loaded customer: " + customer); // Debugging
                } catch (Exception ex) {
                    System.err.println("Error parsing customer entry: " + line + ". Reason: " + ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading customers from file: " + e.getMessage());
        }
        return customers;
    }

    // Validate dimensions and default to "1x1x1" if invalid
    private static String validateDimensions(String dimensions, String line) {
        if (dimensions.matches("\\d+x\\d+x\\d+")) {
            return dimensions;
        } else {
            System.err.println("Invalid dimensions in line: " + line + ". Defaulting to '1x1x1'.");
            return "1x1x1";
        }
    }

    // Parse double with error handling
    private static double parseDouble(String value, String fieldName, String line) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid " + fieldName + " in line: " + line + ". Defaulting to 0.0.");
            return 0.0;
        }
    }

    // Parse integer with error handling
    private static int parseInt(String value, String fieldName, String line) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid " + fieldName + " in line: " + line + ". Defaulting to 0.");
            return 0;
        }
    }
}
