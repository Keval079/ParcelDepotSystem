package controller;

import model.*;
import view.DepotView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DepotController {
    private QueueofCustomers customerQueue;
    private ParcelMap parcelMap;
    private Log log;
    private Worker worker;
    private DepotView view;

    public DepotController(QueueofCustomers customerQueue, ParcelMap parcelMap, Log log, DepotView view) {
        this.customerQueue = customerQueue;
        this.parcelMap = parcelMap;
        this.log = log;
        this.worker = new Worker(parcelMap, customerQueue);
        this.view = view;

        // Attach listeners to buttons
        attachButtonListeners();

        // Initial GUI update
        updateView();
    }

    private void attachButtonListeners() {
        view.addProcessNextButtonListener(new ProcessNextCustomerListener());
        view.addAddCustomerButtonListener(new AddCustomerListener());
        view.addAddParcelButtonListener(new AddParcelListener());
        view.addSaveLogButtonListener(new SaveLogListener());
        view.addCustomerSearchListener(new CustomerSearchListener());
        view.addParcelSearchListener(new ParcelSearchListener());
        view.addResetSearchListener(new ResetSearchListener());
    }

    private void updateView() {
        // Update customer queue table
        List<Customer> customers = new ArrayList<>(customerQueue.getQueue());
        Object[][] customerData = new Object[customers.size()][3];
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            customerData[i][0] = i + 1; // Sequence number
            customerData[i][1] = customer.getName();
            customerData[i][2] = customer.getParcelId();
        }
        view.updateCustomerQueueTable(customerData);

        // Update parcel list table
        List<Parcel> parcels = new ArrayList<>(parcelMap.getParcels());
        Object[][] parcelData = new Object[parcels.size()][5];
        for (int i = 0; i < parcels.size(); i++) {
            Parcel parcel = parcels.get(i);
            parcelData[i][0] = parcel.getParcelId();
            parcelData[i][1] = parcel.getDimensions();
            parcelData[i][2] = parcel.getWeight();
            parcelData[i][3] = parcel.getDaysInDepot();
            parcelData[i][4] = parcel.getStatus();
        }
        view.updateParcelListTable(parcelData);

        // Update log area
        view.setLogText(log.toString());

        // Update total fees label
        view.updateTotalFees(worker.getTotalFees());

        // Update current parcel display
        Parcel currentParcel = worker.getCurrentParcel();
        if (currentParcel != null) {
            view.updateCurrentParcelDetails(new Object[][]{
                    {
                            currentParcel.getParcelId(),
                            currentParcel.getDimensions(),
                            currentParcel.getWeight(),
                            currentParcel.getDaysInDepot(),
                            currentParcel.getStatus()
                    }
            });
        } else {
            view.updateCurrentParcelDetails(new Object[][]{});
        }
    }

    // Listener for "Process Next Customer"
    class ProcessNextCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String result = worker.processNextCustomer();
            log.writeLog("N/A", "N/A", result);
            JOptionPane.showMessageDialog(view, result);
            updateView();
        }
    }

    // Listener for "Add Customer"
    class AddCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = JOptionPane.showInputDialog(view, "Enter Customer Name:");
                if (name == null || name.trim().isEmpty()) {
                    throw new IllegalArgumentException("Customer name cannot be empty!");
                }

                String parcelId = JOptionPane.showInputDialog(view, "Enter Parcel ID:");
                if (parcelId == null || parcelId.trim().isEmpty()) {
                    throw new IllegalArgumentException("Parcel ID cannot be empty!");
                }

                // Check if the Parcel ID exists; if not, allow adding a new Parcel
                if (!parcelMap.containsParcel(parcelId)) {
                    int choice = JOptionPane.showConfirmDialog(view, "Parcel ID does not exist. Do you want to add this Parcel?", "Confirm Add Parcel", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        // Gather parcel details to create a new Parcel
                        String dimensions = JOptionPane.showInputDialog(view, "Enter Dimensions (e.g., 10x5x5):");
                        if (dimensions == null || dimensions.trim().isEmpty()) {
                            throw new IllegalArgumentException("Dimensions cannot be empty!");
                        }

                        String weightStr = JOptionPane.showInputDialog(view, "Enter Weight (in kg):");
                        if (weightStr == null || weightStr.trim().isEmpty()) {
                            throw new IllegalArgumentException("Weight cannot be empty!");
                        }
                        double weight = Double.parseDouble(weightStr.trim());

                        String daysInDepotStr = JOptionPane.showInputDialog(view, "Enter Days in Depot:");
                        if (daysInDepotStr == null || daysInDepotStr.trim().isEmpty()) {
                            throw new IllegalArgumentException("Days in Depot cannot be empty!");
                        }
                        int daysInDepot = Integer.parseInt(daysInDepotStr.trim());

                        String status = JOptionPane.showInputDialog(view, "Enter Status (e.g., waiting):");
                        if (status == null || status.trim().isEmpty()) {
                            throw new IllegalArgumentException("Status cannot be empty!");
                        }

                        // Add the new Parcel to ParcelMap
                        Parcel newParcel = new Parcel(parcelId.trim(), dimensions.trim(), weight, daysInDepot, status.trim());
                        parcelMap.addParcel(newParcel);
                        log.writeLog("System", parcelId.trim(), "Added new parcel to ParcelMap.");
                    } else {
                        return; // Cancel operation if user chooses not to add a new Parcel
                    }
                }

                // Add the customer to the queue
                Customer newCustomer = new Customer(customerQueue.getQueue().size() + 1, name.trim(), parcelId.trim());
                customerQueue.addCustomer(newCustomer);
                log.writeLog(name.trim(), parcelId.trim(), "Added customer to the queue.");
                updateView();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Listener for "Add Parcel"
    class AddParcelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String parcelId = JOptionPane.showInputDialog(view, "Enter Parcel ID:");
                if (parcelId == null || parcelId.trim().isEmpty()) {
                    throw new IllegalArgumentException("Parcel ID cannot be empty!");
                }

                String dimensions = JOptionPane.showInputDialog(view, "Enter Dimensions (e.g., 10x5x5):");
                if (dimensions == null || dimensions.trim().isEmpty()) {
                    throw new IllegalArgumentException("Dimensions cannot be empty!");
                }

                String weightStr = JOptionPane.showInputDialog(view, "Enter Weight (in kg):");
                if (weightStr == null || weightStr.trim().isEmpty()) {
                    throw new IllegalArgumentException("Weight cannot be empty!");
                }
                double weight = Double.parseDouble(weightStr.trim());

                String daysInDepotStr = JOptionPane.showInputDialog(view, "Enter Days in Depot:");
                if (daysInDepotStr == null || daysInDepotStr.trim().isEmpty()) {
                    throw new IllegalArgumentException("Days in Depot cannot be empty!");
                }
                int daysInDepot = Integer.parseInt(daysInDepotStr.trim());

                String status = JOptionPane.showInputDialog(view, "Enter Status (e.g., waiting):");
                if (status == null || status.trim().isEmpty()) {
                    throw new IllegalArgumentException("Status cannot be empty!");
                }

                Parcel newParcel = new Parcel(parcelId.trim(), dimensions.trim(), weight, daysInDepot, status.trim());
                parcelMap.addParcel(newParcel);
                log.writeLog("N/A", parcelId.trim(), "Added parcel to ParcelMap.");
                updateView();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid numeric value for weight or days in depot!", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Listener for "Save Log"
    class SaveLogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                log.saveToFile("resources/log.txt");
                JOptionPane.showMessageDialog(view, "Logs saved successfully to resources/log.txt.", "Save Log", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error saving log: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Listener for "Search Customer"
    class CustomerSearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchName = view.getCustomerSearchInput().trim().toLowerCase();
            if (searchName.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Enter a name to search.", "Search Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Customer> matchedCustomers = new ArrayList<>();
            for (Customer customer : customerQueue.getQueue()) {
                if (customer.getName().toLowerCase().contains(searchName)) {
                    matchedCustomers.add(customer);
                }
            }

            if (matchedCustomers.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No matching customers found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Object[][] customerData = new Object[matchedCustomers.size()][3];
                for (int i = 0; i < matchedCustomers.size(); i++) {
                    Customer customer = matchedCustomers.get(i);
                    customerData[i][0] = i + 1;
                    customerData[i][1] = customer.getName();
                    customerData[i][2] = customer.getParcelId();
                }
                view.updateCustomerQueueTable(customerData);
            }
        }
    }

    // Listener for "Search Parcel"
    class ParcelSearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchId = view.getParcelSearchInput().trim();
            if (searchId.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Enter a Parcel ID to search.", "Search Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Parcel parcel = parcelMap.findParcel(searchId);
            if (parcel == null) {
                JOptionPane.showMessageDialog(view, "No matching parcel found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Object[][] parcelData = new Object[1][5];
                parcelData[0][0] = parcel.getParcelId();
                parcelData[0][1] = parcel.getDimensions();
                parcelData[0][2] = parcel.getWeight();
                parcelData[0][3] = parcel.getDaysInDepot();
                parcelData[0][4] = parcel.getStatus();
                view.updateParcelListTable(parcelData);
            }
        }
    }

    // Listener for "Reset Search"
    class ResetSearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateView();
            view.clearInputFields();
        }
    }
}
