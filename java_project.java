import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class java_project {
    private Connection con;

    public java_project(Connection connection) {
        this.con = connection;
        JFrame frame = new JFrame("Property Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1, 15, 15));

        JLabel heading = new JLabel("Property Management Dashboard", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));

        JButton propertyBtn = new JButton("Manage Properties");
        JButton leaseBtn = new JButton("Manage Leases");
        JButton tenantBtn = new JButton("Manage Tenants");
        JButton paymentBtn = new JButton("Manage Payments");

        propertyBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        leaseBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        tenantBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        paymentBtn.setFont(new Font("Arial", Font.PLAIN, 16));

        panel.add(heading);
        panel.add(propertyBtn);
        panel.add(leaseBtn);
        panel.add(tenantBtn);
        panel.add(paymentBtn);

        frame.add(panel);
        frame.setVisible(true);

        propertyBtn.addActionListener(e -> showPropertyMenu());
        leaseBtn.addActionListener(e -> insertLease());
        tenantBtn.addActionListener(e -> insertTenant());
        paymentBtn.addActionListener(e -> insertPayment());
    }

    private void showPropertyMenu() {
        String[] options = {"Insert Property", "Update Property", "Delete Property"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an option", "Property Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0 -> insertProperty();
            case 1 -> updateProperty();
            case 2 -> deleteProperty();
        }
    }

    private void insertProperty() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField sizeField = new JTextField();

        Object[] fields = {
                "Property ID:", idField,
                "Name:", nameField,
                "Location:", locationField,
                "Property Type:", typeField,
                "Property Size:", sizeField
        };

        int result = JOptionPane.showConfirmDialog(null, fields, "Insert Property", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO Property VALUES (?, ?, ?, ?, ?)");
                ps.setInt(1, Integer.parseInt(idField.getText()));
                ps.setString(2, nameField.getText());
                ps.setString(3, locationField.getText());
                ps.setString(4, typeField.getText());
                ps.setString(5, sizeField.getText());
                int count = ps.executeUpdate();
                JOptionPane.showMessageDialog(null, count > 0 ? "Insert successful" : "Insert failed");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        }
    }

    private void updateProperty() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField sizeField = new JTextField();

        Object[] fields = {
                "Property ID to update:", idField,
                "New Name:", nameField,
                "New Location:", locationField,
                "New Property Type:", typeField,
                "New Property Size:", sizeField
        };

        int result = JOptionPane.showConfirmDialog(null, fields, "Update Property", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                PreparedStatement ps = con.prepareStatement("UPDATE Property SET Name = ?, Location = ?, Property_Type = ?, Property_Size = ? WHERE Property_ID = ?");
                ps.setString(1, nameField.getText());
                ps.setString(2, locationField.getText());
                ps.setString(3, typeField.getText());
                ps.setString(4, sizeField.getText());
                ps.setInt(5, Integer.parseInt(idField.getText()));
                int count = ps.executeUpdate();
                JOptionPane.showMessageDialog(null, count > 0 ? "Record updated successfully." : "Record not found.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        }
    }

    private void deleteProperty() {
        JTextField idField = new JTextField();
        Object[] fields = {"Property ID to delete:", idField};

        int result = JOptionPane.showConfirmDialog(null, fields, "Delete Property", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                PreparedStatement ps = con.prepareStatement("DELETE FROM Property WHERE Property_ID = ?");
                ps.setInt(1, Integer.parseInt(idField.getText()));
                int count = ps.executeUpdate();
                JOptionPane.showMessageDialog(null, count > 0 ? "Property deleted successfully." : "Property not found.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        }
    }

    private void insertTenant() {
        // Input fields for tenant data
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField emailField = new JTextField();

        // Creating the form
        Object[] fields = {
                "Tenant ID:", idField,
                "Name:", nameField,
                "Contact No:", contactField,
                "Email:", emailField
        };

        // Show form and wait for OK/Cancel
        int result = JOptionPane.showConfirmDialog(null, fields, "Insert Tenant", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Create SQL INSERT statement
                PreparedStatement ps = con.prepareStatement("INSERT INTO Tenant VALUES (?, ?, ?, ?)");
                ps.setInt(1, Integer.parseInt(idField.getText()));              // Tenant ID
                ps.setString(2, nameField.getText());                           // Name
                ps.setDouble(3, Double.parseDouble(contactField.getText()));    // Contact Number
                ps.setString(4, emailField.getText());                          // Email

                // Execute the insert
                int count = ps.executeUpdate();

                // Feedback
                JOptionPane.showMessageDialog(null, count > 0 ? "Insert successful" : "Insert failed");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        }
    }


    private void insertLease() {
        JTextField leaseIdField = new JTextField();
        JTextField propertyIdField = new JTextField();
        JTextField tenantIdField = new JTextField();
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        Object[] fields = {
                "Lease ID:", leaseIdField,
                "Property ID:", propertyIdField,
                "Tenant ID:", tenantIdField,
                "Start Date (yyyy-MM-dd):", startDateField,
                "End Date (yyyy-MM-dd):", endDateField
        };

        int result = JOptionPane.showConfirmDialog(null, fields, "Insert Lease", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO Lease VALUES (?, ?, ?, ?, ?)");
                ps.setInt(1, Integer.parseInt(leaseIdField.getText()));
                ps.setInt(2, Integer.parseInt(propertyIdField.getText()));
                ps.setInt(3, Integer.parseInt(tenantIdField.getText()));
                ps.setDate(4, java.sql.Date.valueOf(startDateField.getText()));
                ps.setDate(5, java.sql.Date.valueOf(endDateField.getText()));
                int count = ps.executeUpdate();
                JOptionPane.showMessageDialog(null, count > 0 ? "Insert successful" : "Insert failed");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        }
    }

    private void insertPayment() {
        JTextField paymentIdField = new JTextField();
        JTextField leaseIdField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField();

        Object[] fields = {
                "Payment ID:", paymentIdField,
                "Lease ID:", leaseIdField,
                "Amount:", amountField,
                "Payment Date (yyyy-MM-dd):", dateField
        };

        int result = JOptionPane.showConfirmDialog(null, fields, "Insert Payment", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO Payement VALUES (?, ?, ?, ?)");
                ps.setInt(1, Integer.parseInt(paymentIdField.getText()));
                ps.setInt(2, Integer.parseInt(leaseIdField.getText()));
                ps.setInt(3, Integer.parseInt(amountField.getText()));
                ps.setDate(4, java.sql.Date.valueOf(dateField.getText()));
                int count = ps.executeUpdate();
                JOptionPane.showMessageDialog(null, count > 0 ? "Insert successful" : "Insert failed");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        }
    }

    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "shubham");
            new java_project(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e);
        }
    }
}
