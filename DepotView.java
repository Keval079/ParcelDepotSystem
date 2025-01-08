package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class DepotView extends JFrame {
    private JTable customerQueueTable;
    private JTable parcelListTable;
    private JTable currentParcelTable; // Table for current parcel details
    private JTextArea logArea;
    private JTextField customerNameField;
    private JTextField parcelIdField;
    private JTextField customerSearchField;
    private JTextField parcelSearchField;
    private JLabel totalFeesLabel;
    private JButton processNextButton;
    private JButton addCustomerButton;
    private JButton addParcelButton;
    private JButton saveLogButton;
    private JButton customerSearchButton;
    private JButton parcelSearchButton;
    private JButton resetSearchButton;

    private DefaultTableModel customerQueueModel;
    private DefaultTableModel parcelListModel;
    private DefaultTableModel currentParcelModel; // Model for current parcel details

    public DepotView() {
        // Frame setup
        setTitle("Parcel Depot System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Add spacing between components

        // Panels
        JPanel topPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // Adjusted for 3 tables
        JPanel eastPanel = new JPanel(new BorderLayout(10, 10));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Table Models
        customerQueueModel = new DefaultTableModel(new String[]{"Sequence Number", "Name", "Parcel ID"}, 0);
        parcelListModel = new DefaultTableModel(new String[]{"Parcel ID", "Dimensions", "Weight", "Days in Depot", "Status"}, 0);
        currentParcelModel = new DefaultTableModel(new String[]{"Parcel ID", "Dimensions", "Weight", "Days in Depot", "Status"}, 0);

        // Customer Queue Table
        customerQueueTable = new JTable(customerQueueModel);
        JScrollPane customerScroll = new JScrollPane(customerQueueTable);
        customerScroll.setBorder(BorderFactory.createTitledBorder("Customer Queue"));

        // Parcel List Table
        parcelListTable = new JTable(parcelListModel);
        JScrollPane parcelScroll = new JScrollPane(parcelListTable);
        parcelScroll.setBorder(BorderFactory.createTitledBorder("Parcel List"));

        // Current Parcel Table
        currentParcelTable = new JTable(currentParcelModel);
        JScrollPane currentParcelScroll = new JScrollPane(currentParcelTable);
        currentParcelScroll.setBorder(BorderFactory.createTitledBorder("Current Parcel"));

        // Log Area
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Log Area"));
        logScroll.setPreferredSize(new Dimension(300, 400));

        // Input Fields
        customerNameField = new JTextField();
        parcelIdField = new JTextField();

        // Search Fields
        customerSearchField = new JTextField();
        parcelSearchField = new JTextField();
        customerSearchButton = new JButton("Search Customer");
        parcelSearchButton = new JButton("Search Parcel");
        resetSearchButton = new JButton("Reset Search");

        // Add components to the top panel
        topPanel.add(new JLabel("Customer Name:"));
        topPanel.add(customerNameField);
        topPanel.add(new JLabel("Search Customer:"));
        topPanel.add(customerSearchField);

        topPanel.add(new JLabel("Parcel ID:"));
        topPanel.add(parcelIdField);
        topPanel.add(new JLabel("Search Parcel:"));
        topPanel.add(parcelSearchField);

        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(customerSearchButton);
        topPanel.add(parcelSearchButton);

        // Buttons
        processNextButton = new JButton("Process Next Customer");
        addCustomerButton = new JButton("Add Customer");
        addParcelButton = new JButton("Add Parcel");
        saveLogButton = new JButton("Save Log");
        buttonPanel.add(processNextButton);
        buttonPanel.add(addCustomerButton);
        buttonPanel.add(addParcelButton);
        buttonPanel.add(saveLogButton);
        buttonPanel.add(resetSearchButton);

        // Summary Panel
        totalFeesLabel = new JLabel("Total Fees Calculated: $0.00");
        summaryPanel.add(totalFeesLabel);

        // Add components to the center panel
        centerPanel.add(customerScroll);
        centerPanel.add(parcelScroll);
        centerPanel.add(currentParcelScroll); // Add current parcel table

        // Add components to the east panel
        eastPanel.add(logScroll, BorderLayout.CENTER);
        eastPanel.add(summaryPanel, BorderLayout.SOUTH);

        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Update customer queue table
    public void updateCustomerQueueTable(Object[][] data) {
        customerQueueModel.setRowCount(0);
        for (Object[] row : data) {
            customerQueueModel.addRow(row);
        }
    }

    // Update parcel list table
    public void updateParcelListTable(Object[][] data) {
        parcelListModel.setRowCount(0);
        for (Object[] row : data) {
            parcelListModel.addRow(row);
        }
    }

    // Update current parcel table
    public void updateCurrentParcelDetails(Object[][] data) {
        currentParcelModel.setRowCount(0);
        for (Object[] row : data) {
            currentParcelModel.addRow(row);
        }
    }

    // Update log area
    public void setLogText(String text) {
        logArea.setText(text);
    }

    // Update total fees label
    public void updateTotalFees(double totalFees) {
        totalFeesLabel.setText("Total Fees Calculated: $" + String.format("%.2f", totalFees));
    }

    // Retrieve input from the fields
    public String getCustomerNameInput() {
        return customerNameField.getText();
    }

    public String getParcelIdInput() {
        return parcelIdField.getText();
    }

    public String getCustomerSearchInput() {
        return customerSearchField.getText();
    }

    public String getParcelSearchInput() {
        return parcelSearchField.getText();
    }

    // Clear input fields
    public void clearInputFields() {
        customerNameField.setText("");
        parcelIdField.setText("");
        customerSearchField.setText("");
        parcelSearchField.setText("");
    }

    // Button listeners
    public void addProcessNextButtonListener(ActionListener listener) {
        processNextButton.addActionListener(listener);
    }

    public void addAddCustomerButtonListener(ActionListener listener) {
        addCustomerButton.addActionListener(listener);
    }

    public void addAddParcelButtonListener(ActionListener listener) {
        addParcelButton.addActionListener(listener);
    }

    public void addSaveLogButtonListener(ActionListener listener) {
        saveLogButton.addActionListener(listener);
    }

    public void addCustomerSearchListener(ActionListener listener) {
        customerSearchButton.addActionListener(listener);
    }

    public void addParcelSearchListener(ActionListener listener) {
        parcelSearchButton.addActionListener(listener);
    }

    public void addResetSearchListener(ActionListener listener) {
        resetSearchButton.addActionListener(listener);
    }
}
