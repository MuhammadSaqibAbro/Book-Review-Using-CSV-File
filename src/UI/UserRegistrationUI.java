package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import main.DashboardUI;

public class UserRegistrationUI extends JFrame {

    private JTextField fullNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private Map<String, String> userDatabase; // Simulated database for storing user credentials
    private Map<String, String> adminDatabase; 

    public UserRegistrationUI(Map<String, String> userDatabase) {
        this.userDatabase = userDatabase;

        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(65, 105, 225));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(65, 105, 225));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DashboardUI(adminDatabase).setVisible(true);
                // Close the current registration window
                dispose();
            }
        });
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("User Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        formPanel.setBackground(new Color(240, 240, 240));

        formPanel.add(new JLabel("Full Name:"));
        fullNameField = new JTextField();
        formPanel.add(fullNameField);

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = fullNameField.getText().trim();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(UserRegistrationUI.this,
                            "Please fill in all fields",
                            "Registration Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    if (userDatabase.containsKey(username)) {
                        JOptionPane.showMessageDialog(UserRegistrationUI.this,
                                "Username already exists. Please choose a different one.",
                                "Registration Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Simulate storing user credentials in the database
                        userDatabase.put(username, password);
                        JOptionPane.showMessageDialog(UserRegistrationUI.this,
                                "Registration successful for " + fullName,
                                "Registration Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    }
                }
            }
        });
        formPanel.add(registerButton);

        JButton loginPageButton = new JButton("Go to User Login");
        loginPageButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginPageButton.setForeground(Color.BLUE);
        loginPageButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        loginPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserLoginUI(userDatabase).setVisible(true);
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
        SwingUtilities.invokeLater(() -> {
            Map<String, String> userDatabase = new HashMap<>();
            new UserRegistrationUI(userDatabase).setVisible(true);
        });
    }
}
