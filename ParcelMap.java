package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParcelMap {
    private Map<String, Parcel> parcelMap;

    public ParcelMap() {
        this.parcelMap = new HashMap<>();
    }

    // Add a parcel to the map
    public void addParcel(Parcel parcel) {
        parcelMap.put(parcel.getParcelId(), parcel);
        System.out.println("Added parcel: " + parcel); // Debugging
    }

    // Find a parcel by its ID
    public Parcel findParcel(String parcelId) {
        return parcelMap.get(parcelId); // Return parcel or null if not found
    }

    // Check if the map contains a parcel by its ID
    public boolean containsParcel(String parcelId) {
        return parcelMap.containsKey(parcelId);
    }

    // Validate if ParcelMap has parcels
    public boolean isEmpty() {
        return parcelMap.isEmpty();
    }

    // Get all parcels as a list
    public List<Parcel> getParcels() {
        return new ArrayList<>(parcelMap.values());
    }

    // Print all parcels (for debugging)
    public void printAllParcels() {
        if (parcelMap.isEmpty()) {
            System.out.println("No parcels in the map.");
        } else {
            for (Map.Entry<String, Parcel> entry : parcelMap.entrySet()) {
                System.out.println("Parcel ID: " + entry.getKey() + ", Parcel: " + entry.getValue());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Parcels:\n");
        for (Parcel parcel : parcelMap.values()) {
            sb.append(parcel).append("\n");
        }
        return sb.toString();
    }
}
