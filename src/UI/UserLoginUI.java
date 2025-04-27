/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author SAQIB
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class UserLoginUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private Map<String, String> userDatabase; // Simulated database for user credentials

    public UserLoginUI(Map<String, String> userDatabase) {
        this.userDatabase = userDatabase;

        setTitle("User Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(65, 105, 225));
        JLabel titleLabel = new JLabel("User Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        formPanel.setBackground(new Color(240, 240, 240));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
                    JOptionPane.showMessageDialog(UserLoginUI.this,
                            "Login successful",
                            "Login Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    WelcomeUserPanel r2=new WelcomeUserPanel();
                    r2.setVisible(true);
                    UserLoginUI.this.dispose(); // Close the login window
                    clearFields();
                    // Open user-specific UI or perform other actions upon successful login
                    // Example: UserDashboardUI userDashboard = new UserDashboardUI();
                    // userDashboard.setVisible(true);
                    UserLoginUI.this.dispose(); // Close the login window
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(UserLoginUI.this,
                            "Invalid username or password",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        formPanel.add(loginButton);

        JButton registerPageButton = new JButton("Go to Registration");
        registerPageButton.setFont(new Font("Arial", Font.PLAIN, 16));
        registerPageButton.setForeground(Color.BLUE);
        registerPageButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        registerPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserRegistrationUI(userDatabase).setVisible(true);
            }
        });
        formPanel.add(registerPageButton);

        add(formPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        Map<String, String> userDatabase = new HashMap<>();
        // Add some initial user credentials to the database
        userDatabase.put("user", "userpass");

        SwingUtilities.invokeLater(() -> new UserLoginUI(userDatabase));
    }
}

