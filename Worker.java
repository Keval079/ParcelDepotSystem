package model;

public class Worker {
    private ParcelMap parcelMap; // Reference to parcel data
    private QueueofCustomers customerQueue; // Reference to customer queue
    private Log log; // Singleton instance of the log
    private double totalFees; // Track total fees for all parcels processed
    private Parcel currentParcel; // The parcel currently being processed

    // Constructor
    public Worker(ParcelMap parcelMap, QueueofCustomers customerQueue) {
        this.parcelMap = parcelMap;
        this.customerQueue = customerQueue;
        this.log = Log.getInstance(); // Get the singleton log instance
        this.totalFees = 0.0; // Initialize total fees
        this.currentParcel = null; // No parcel is being processed initially
    }

    // Process the next customer in the queue and update parcel status to "collected"
    public String processNextCustomer() {
        if (customerQueue.isEmpty()) {
            String message = "No customers in the queue to process.";
            log.writeLog("System", "-", message); // Log the message
            currentParcel = null; // Reset current parcel
            return message;
        }

        // Remove the next customer
        Customer customer = customerQueue.removeCustomer();
        log.writeLog(customer.getName(), "-", "Processing customer.");

        // Find the customer's parcel
        Parcel parcel = parcelMap.findParcel(customer.getParcelId());
        if (parcel == null) {
            String errorMessage = "Parcel not found for customer: " + customer.getName() +
                    ", Parcel ID: " + customer.getParcelId();
            log.writeLog(customer.getName(), customer.getParcelId(), errorMessage); // Log the error
            currentParcel = null; // Reset current parcel
            return errorMessage;
        }

        // Calculate the fee for the parcel
        double fee = parcel.calculateFee();
        totalFees += fee; // Accumulate the fee
        String successMessage = "Parcel processed. Fee: $" + String.format("%.2f", fee);
        log.writeLog(customer.getName(), parcel.getParcelId(), successMessage);

        // Update parcel status
        parcel.updateStatus("Collected");
        log.writeLog(customer.getName(), parcel.getParcelId(), "Parcel status updated to 'Collected'.");

        // Set the currently processed parcel
        currentParcel = parcel;

        // Return the processing summary
        return "Processed customer: " + customer.getName() +
                ", Parcel ID: " + parcel.getParcelId() +
                ", Fee: $" + String.format("%.2f", fee) +
                ", Total Fees: $" + String.format("%.2f", totalFees);
    }

    // Calculate fees for the next parcel in the queue without updating its status
    public void calculateFeesForNextParcel() {
        if (customerQueue.isEmpty()) {
            log.writeLog("System", "-", "No customers in the queue to calculate fees.");
            System.out.println("No customers in the queue to calculate fees.");
            return;
        }

        // Get the next customer (without removing)
        Customer customer = customerQueue.getNextCustomer();
        log.writeLog(customer.getName(), "-", "Calculating fees for customer.");

        // Find the customer's parcel
        Parcel parcel = parcelMap.findParcel(customer.getParcelId());
        if (parcel == null) {
            log.writeLog(customer.getName(), "-", "Parcel not found for customer.");
            System.out.println("Parcel not found for customer: " + customer.getName());
            return;
        }

        // Calculate the fee for the parcel
        double fee = parcel.calculateFee();
        totalFees += fee; // Accumulate the fee
        log.writeLog(customer.getName(), parcel.getParcelId(),
                "Fees calculated. Fee: $" + String.format("%.2f", fee));

        // Print the result
        System.out.println("Customer: " + customer.getName());
        System.out.println("Parcel ID: " + parcel.getParcelId());
        System.out.println("Fee: $" + String.format("%.2f", fee));
        System.out.println("Total Fees Accumulated: $" + String.format("%.2f", totalFees));
    }

    // Getter for the current parcel being processed
    public Parcel getCurrentParcel() {
        return currentParcel;
    }

    // Get the total fees
    public double getTotalFees() {
        return totalFees;
    }
}
