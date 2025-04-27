package main;




import UI.AdminRegistrationUI;
import UI.UserRegistrationUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DashboardUI extends JFrame {

    private Map<String, String> adminDatabase;

    public DashboardUI(Map<String, String> adminDatabase) {
        this.adminDatabase = adminDatabase;

        setTitle("Book Management System Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

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

        // Create ImagePanel with the desired background image path
        ImagePanel backgroundPanel = new ImagePanel("/image/henry-be-lc7xcWebECc-unsplash.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel);

        // Royal blue title panel with "Book Review System" text
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(65, 105, 225)); // Set background color to royal blue
        JLabel titleLabel = new JLabel("Book Review System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE); // Set text color to white for contrast
        titlePanel.add(titleLabel);

        // Add title panel to the top (North) of the background panel
        backgroundPanel.add(titlePanel, BorderLayout.NORTH);

        // Panel for buttons using GridBagLayout for center alignment
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Transparent background
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        // Stylish button for Admin Panel (royal blue)
        JButton adminButton = createStyledButton("Admin Panel", new Color(65, 105, 225));
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminRegistrationUI().setVisible(true);
            }
        });

        // Stylish button for User Panel (royal blue)
        JButton userButton = createStyledButton("User Panel", new Color(65, 105, 225));
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserRegistrationUI(adminDatabase).setVisible(true);
            }
        });

        // Create GridBagConstraints for button positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Padding between buttons

        // Add Admin Panel button
        buttonPanel.add(adminButton, gbc);

        // Update GridBagConstraints for User Panel button
        gbc.gridy = 1; // Next row
        buttonPanel.add(userButton, gbc);

        setVisible(true);
    }

    // Method to create a styled button with specified background color
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor); // Set button background color
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        button.setFocusPainted(false); // Remove focus border
        button.setPreferredSize(new Dimension(200, 60)); // Set button size
        return button;
    }

    public static void main(String[] args) {
        // Simulated admin credentials database
        Map<String, String> adminDatabase = new HashMap<>();
        adminDatabase.put("admin", "adminpass");

        SwingUtilities.invokeLater(() -> new DashboardUI(adminDatabase));
    }
}
