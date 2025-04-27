package UI;

import generalCsv.GenaeralCSVEditorGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import personalCsv.CSVEditorGUI;
import javax.swing.border.*;

public class WelcomeAdminPanel extends JFrame {

    public WelcomeAdminPanel() {
        setTitle("Welcome Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 450);
        setLocationRelativeTo(null); // Center the frame on screen

        // Create the background panel with the image
        ImagePanel backgroundPanel = new ImagePanel("/image/henry-be-lc7xcWebECc-unsplash.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        // Load the back.png image
        ImageIcon backIcon = new ImageIcon(getClass().getResource("/UI/back.png"));

        // Create a small image button at the top left corner
        JButton topLeftButton = createStyledImageButton(backIcon); 
        topLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle action for the top left button (e.g., go back)
                AdminRegistrationUI r2 = new AdminRegistrationUI();
                WelcomeAdminPanel.this.setVisible(false);
                r2.setVisible(true);
                WelcomeAdminPanel.this.dispose();
            }
        });

        // Panel for the top left button
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false); // Make panel transparent
        topLeftPanel.add(topLeftButton);
        backgroundPanel.add(topLeftPanel, BorderLayout.NORTH); // Add the panel to the top left

        JLabel welcomeLabel = new JLabel("Welcome Admin Panel!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 50));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.BLACK); // Set font color to black
        backgroundPanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make button panel transparent
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton manageUsersButton = createStyledButton("General CSV", Color.BLUE);
        JButton settingsButton = createStyledButton("Personal CSV", Color.GREEN);

        buttonPanel.add(manageUsersButton);
        buttonPanel.add(settingsButton);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(backgroundPanel); // Use the background panel as content pane
        setVisible(true);

        // Button action listeners
        manageUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenaeralCSVEditorGUI r2 = new GenaeralCSVEditorGUI();
                WelcomeAdminPanel.this.setVisible(false);
                r2.setVisible(true);
                WelcomeAdminPanel.this.dispose();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CSVEditorGUI r2 = new CSVEditorGUI();
                WelcomeAdminPanel.this.setVisible(false);
                r2.setVisible(true);
                WelcomeAdminPanel.this.dispose();
            }
        });
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 50));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createStyledImageButton(ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setFocusPainted(false); // Remove focus border
        button.setBorderPainted(false); // Remove border
        button.setContentAreaFilled(false); // Transparent background
        button.setOpaque(false); // Ensure button background is transparent
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WelcomeAdminPanel();
            }
        });
    }
}
