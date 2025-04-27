package UI;


import personalCsv.CSVEditorGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AdminLoginUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private Map<String, String> adminDatabase; // Simulated database for admin credentials

    public AdminLoginUI(Map<String, String> adminDatabase) {
        this.adminDatabase = adminDatabase;

        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300); // Set larger size for the frame
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240)); // Set background color
        
        // Create ImagePanel with the desired background image path
        ImagePanel backgroundPanel = new ImagePanel("/image/henry-be-lc7xcWebECc-unsplash.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel);

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(65, 105, 225)); // Dark blue background color
        JLabel titleLabel = new JLabel("Admin Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Increase font size
        titleLabel.setForeground(Color.WHITE); // White text color
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        formPanel.setBackground(new Color(240, 240, 240)); // Light gray background color

        // Adding components with larger size
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

                if (adminDatabase.containsKey(username) && adminDatabase.get(username).equals(password)) {
                    JOptionPane.showMessageDialog(AdminLoginUI.this,
                            "Login successful",
                            "Login Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Open another UI or perform other actions upon successful login
                    WelcomeAdminPanel csvEditor = new WelcomeAdminPanel();
                    csvEditor.setVisible(true);
                    AdminLoginUI.this.dispose(); // Close the login window
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(AdminLoginUI.this,
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
                dispose(); // Close the current login window
                new AdminRegistrationUI().setVisible(true); // Open the registration window
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

    // Inner class ImagePanel for displaying background image
    class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) {
            // Load the image from the given path
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        Map<String, String> adminDatabase = new HashMap<>();
        // Add some initial admin credentials to the database
        adminDatabase.put("admin", "adminpass");

        SwingUtilities.invokeLater(() -> new AdminLoginUI(adminDatabase));
    }
}
