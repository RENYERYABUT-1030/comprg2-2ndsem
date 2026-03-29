
package database.guidedexercises;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class UserRegistration extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtName, txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister;

    //DataBase Connection Details
    private final String DB_URL = "jdbc:mysql://localhost:3306/userdatabase";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    public UserRegistration() {
        setTitle("User Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(4, 2));

        //Components
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
        getContentPane().add(lblName);
        txtName = new JTextField();
        txtName.setFont(new Font("Tahoma", Font.BOLD, 12));
        getContentPane().add(txtName);


        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
        getContentPane().add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
        getContentPane().add(txtEmail);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
        getContentPane().add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
        getContentPane().add(txtPassword);

        btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Tahoma", Font.BOLD, 12));
        getContentPane().add(btnRegister);

        btnRegister.addActionListener(e -> registerUser());

        setVisible(true);
    }

    private void registerUser() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "INSERT INTO users(name,email,password) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "User Registration Successful");
        } catch(SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering user: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new UserRegistration();
    }
}