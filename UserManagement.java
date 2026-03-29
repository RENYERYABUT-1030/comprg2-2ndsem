package database.guidedexercises;



import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class UserManagement extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtUserID, txtName, txtEmail;
    private JButton btnUpdate, btnDelete;

    // Database Connection Details
    private final String DB_URL = "jdbc:mysql://localhost:3306/UserDatabase";
    private final String USER = "root";
    private final String PASSWORD = "";

    public UserManagement() {
        setTitle("User Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(4, 2));

        // Components
        JLabel lblUserID = new JLabel("User ID:");
        lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblUserID);
        txtUserID = new JTextField();
        txtUserID.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(txtUserID);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblName);
        txtName = new JTextField();
        txtName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(txtName);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(txtEmail);

        btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 12));

        getContentPane().add(btnUpdate);
        getContentPane().add(btnDelete);

        btnUpdate.addActionListener(e -> updateUser());
        btnDelete.addActionListener(e -> deleteUser());

        setVisible(true);
    }

    private void updateUser() {
        int id = Integer.parseInt(txtUserID.getText());
        String name = txtName.getText();
        String email = txtEmail.getText();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String query = "UPDATE Users SET name = ?, email = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "User updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No user found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating user: " + e.getMessage());
        }
    }

    private void deleteUser() {
        int id = Integer.parseInt(txtUserID.getText());

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String query = "DELETE FROM Users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No user found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new UserManagement();
    }
}