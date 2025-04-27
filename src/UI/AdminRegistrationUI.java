package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import main.DashboardUI;

public class AdminRegistrationUI extends JFrame {

    private JTextField fullNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private Map<String, String> adminDatabase; // Simulated database for storing admin credentials

    public AdminRegistrationUI() {
        setTitle("Admin Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400); // Set larger frame size
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240)); // Set background color

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(65, 105, 225)); // Dark blue background color
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(65, 105, 225)); // Match background color
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open DashboardUI when "Back" button is clicked
                new DashboardUI(adminDatabase).setVisible(true);
                // Close the current registration window
                dispose();
            }
        });
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Admin Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32)); // Increase font size
        titleLabel.setForeground(Color.WHITE); // White text color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        formPanel.setBackground(new Color(240, 240, 240)); // Light gray background color

        adminDatabase = new HashMap<>();

        // Adding components with larger size
        formPanel.add(new JLabel("Full Name:"));
        fullNameField = new JTextField(60); // Increase text field size
        formPanel.add(fullNameField);

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(60);
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(60);
        formPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 20)); // Increase button font size
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = fullNameField.getText().trim();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (!fullName.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    // Simulate storing admin credentials in the database
                    adminDatabase.put(username, password);
                    JOptionPane.showMessageDialog(AdminRegistrationUI.this,
                            "Registration successful for " + fullName,
                            "Registration Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(AdminRegistrationUI.this,
                            "Please fill in all fields",
                            "Registration Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        formPanel.add(registerButton);

        JButton loginPageButton = new JButton("Go to Admin Login");
        loginPageButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Adjust font size
        loginPageButton.setForeground(Color.BLUE);
        loginPageButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Increase button padding
        loginPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current registration window
                new AdminLoginUI(adminDatabase).setVisible(true); // Open the login window
            }
        });
        formPanel.add(loginPageButton);

        add(formPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void clearFields() {
        fullNameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminRegistrationUI::new);
    }
}
