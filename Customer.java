package model;

public class Customer {
    private int sequenceNumber;
    private String name;
    private String parcelId;

    // Constructor
    public Customer(int sequenceNumber, String name, String parcelId) {
        this.sequenceNumber = sequenceNumber;
        this.name = name;
        this.parcelId = parcelId;
    }

    // Getters
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public String getName() {
        return name;
    }

    public String getParcelId() {
        return parcelId;
    }

    // Return customer details
    public String getDetails() {
        return "Customer{" +
                "sequenceNumber=" + sequenceNumber +
                ", name='" + name + '\'' +
                ", parcelId='" + parcelId + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return getDetails();
    }
}
