package model;

import java.util.HashSet;
import java.util.Set;

public class Parcel {
    private String parcelId; // Unique identifier for the parcel
    private String dimensions; // Dimensions in format "LxWxH"
    private double weight; // Weight in kilograms
    private int daysInDepot; // Number of days in depot
    private String status; // Status of the parcel (e.g., "Waiting", "Collected", etc.)

    // Constants for fee calculation
    private static final double RATE_PER_KG = 2.5; // Rate per kilogram
    private static final double SURCHARGE_PER_DAY = 1.0; // Surcharge per day

    // Set of valid statuses
    private static final Set<String> VALID_STATUSES = new HashSet<>();

    static {
        VALID_STATUSES.add("Waiting");
        VALID_STATUSES.add("Collected");
        VALID_STATUSES.add("Damaged");
        VALID_STATUSES.add("Returned");
        VALID_STATUSES.add("In Transit");
        VALID_STATUSES.add("Ready for Collection");
        VALID_STATUSES.add("Lost");
    }

    // Constructor
    public Parcel(String parcelId, String dimensions, double weight, int daysInDepot, String status) {
        if (parcelId == null || parcelId.trim().isEmpty()) {
            throw new IllegalArgumentException("Parcel ID cannot be null or empty.");
        }
        this.parcelId = parcelId;

        setDimensions(dimensions);
        setWeight(weight);
        setDaysInDepot(daysInDepot);
        setStatus(status);
    }

    // Getters
    public String getParcelId() {
        return parcelId;
    }

    public String getDimensions() {
        return dimensions;
    }

    public double getWeight() {
        return weight;
    }

    public int getDaysInDepot() {
        return daysInDepot;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setDimensions(String dimensions) {
        if (dimensions == null || !dimensions.matches("\\d+x\\d+x\\d+")) {
            System.err.println("Invalid dimensions: " + dimensions + ". Defaulting to '1x1x1'.");
            this.dimensions = "1x1x1";
        } else {
            this.dimensions = dimensions;
        }
    }

    public void setWeight(double weight) {
        if (weight <= 0) {
            System.err.println("Invalid weight: " + weight + ". Defaulting to 0.0.");
            this.weight = 0.0;
        } else {
            this.weight = weight;
        }
    }

    public void setDaysInDepot(int daysInDepot) {
        if (daysInDepot < 0) {
            System.err.println("Invalid days in depot: " + daysInDepot + ". Defaulting to 0.");
            this.daysInDepot = 0;
        } else {
            this.daysInDepot = daysInDepot;
        }
    }

    public void setStatus(String status) {
        if (status == null || !isValidStatus(status)) {
            System.err.println("Invalid status: " + status + ". Defaulting to 'Unknown'.");
            this.status = "Unknown";
        } else {
            this.status = status;
        }
    }

    // Validate status
    private boolean isValidStatus(String status) {
        return VALID_STATUSES.contains(status);
    }

    // Calculate fee
    public double calculateFee() {
        return (weight * RATE_PER_KG) + (daysInDepot * SURCHARGE_PER_DAY);
    }

    // Update parcel status
    public void updateStatus(String newStatus) {
        setStatus(newStatus); // Reuse status validation logic
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "parcelId='" + parcelId + '\'' +
                ", dimensions='" + dimensions + '\'' +
                ", weight=" + weight +
                ", daysInDepot=" + daysInDepot +
                ", status='" + status + '\'' +
                '}';
    }
}
