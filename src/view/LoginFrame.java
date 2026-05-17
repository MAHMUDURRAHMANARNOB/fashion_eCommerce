package view;

import model.*;
import controller.ECommerceController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private ECommerceController controller;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeCombo;

    public LoginFrame() {
        controller = new ECommerceController();
        initUI();
        // Add sample data
        controller.initializeSampleData();
    }

    private void initUI() {
        setTitle("Fashion E-Commerce - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Welcome to Fashion Store");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; add(new JLabel("User Type:"), gbc);
        userTypeCombo = new JComboBox<>(new String[]{"Customer", "Admin", "Seller"});
        gbc.gridx = 1; add(userTypeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1; add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1; add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 123, 255));
        // loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(loginButton, gbc);

        loginButton.addActionListener(this::handleLogin);
    }

    private void handleLogin(ActionEvent e) {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String userType = (String) userTypeCombo.getSelectedItem();

        User user = controller.login(email, password, userType);

        if (user != null) {
            dispose(); // Close login window
            if (user instanceof Customer) {
                new MainCustomerFrame((Customer) user, controller).setVisible(true);
            } else if (user instanceof Admin) {
                new AdminFrame((Admin) user, controller).setVisible(true);
            } else if (user instanceof Seller) {
                new SellerFrame((Seller) user, controller).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}