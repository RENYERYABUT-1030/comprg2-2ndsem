package database.guidedexercises;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class machineproblemusrmngmtsys extends JFrame {

    // --- Database Configuration ---
    // Change "user_management_db" to your actual database name if different
    private static final String DB_URL = "jdbc:mysql://localhost:3306/userdatabase";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASS = "";     // Your MySQL password (leave blank for XAMPP default)

    // --- GUI Components ---
    private JTextField txtId, txtName, txtEmail, txtPassword;
    private JButton btnRegister, btnUpdate, btnDelete, btnRefresh;
    private JTable table;
    private DefaultTableModel tableModel;

    // --- Constructor matches the new class name ---
    public machineproblemusrmngmtsys() {
        // Frame Setup
        setTitle("User Management System");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- TOP PANEL (Inputs and Buttons) ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Labels and TextFields
        gbc.gridx = 0; gbc.gridy = 0; topPanel.add(new JLabel("User ID:"), gbc);
        txtId = new JTextField(10);
        gbc.gridx = 0; gbc.gridy = 1; topPanel.add(txtId, gbc);

        gbc.gridx = 1; gbc.gridy = 0; topPanel.add(new JLabel("Name:"), gbc);
        txtName = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1; topPanel.add(txtName, gbc);

        gbc.gridx = 2; gbc.gridy = 0; topPanel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(15);
        gbc.gridx = 2; gbc.gridy = 1; topPanel.add(txtEmail, gbc);

        gbc.gridx = 3; gbc.gridy = 0; topPanel.add(new JLabel("Password:"), gbc);
        txtPassword = new JPasswordField(15);
        gbc.gridx = 3; gbc.gridy = 1; topPanel.add(txtPassword, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnRegister = new JButton("Register");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(buttonPanel, gbc);

        add(topPanel, BorderLayout.NORTH);

        // --- BOTTOM PANEL (Table) ---
        String[] columns = {"ID", "Name", "Email"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // --- EVENT LISTENERS ---
        btnRegister.addActionListener(e -> registerUser());
        btnUpdate.addActionListener(e -> updateUser());
        btnDelete.addActionListener(e -> deleteUser());
        btnRefresh.addActionListener(e -> loadData());

        // Load data initially when app starts
        loadData();
    }

    // --- DATABASE OPERATIONS ---

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private void loadData() {
        tableModel.setRowCount(0); // Clear existing table data
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, email FROM Users")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                tableModel.addRow(new Object[]{id, name, email});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerUser() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in Name, Email, and Password.");
            return;
        }

        String sql = "INSERT INTO Users (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User registered successfully!");
            clearFields();
            loadData(); // Refresh table
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error registering user: " + ex.getMessage());
        }
    }

    private void updateUser() {
        String idStr = txtId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();

        if (idStr.isEmpty() || name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide User ID, new Name, and new Email to update.");
            return;
        }

        String sql = "UPDATE Users SET name = ?, email = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, Integer.parseInt(idStr));

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User updated successfully!");
                clearFields();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "User ID not found.");
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error updating user. Make sure ID is a number. Details: " + ex.getMessage());
        }
    }

    private void deleteUser() {
        String idStr = txtId.getText();

        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the User ID to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM Users WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(idStr));
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
                clearFields();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "User ID not found.");
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting user. Make sure ID is a number.");
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    // --- MAIN METHOD ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Instantiate the new class name here
            new machineproblemusrmngmtsys().setVisible(true);
        });
    }
}