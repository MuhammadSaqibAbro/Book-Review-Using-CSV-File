package UI;

import main.DashboardUI;
import readGeneralCSVbyUser.ReadGenaeralCSVEditorGUI;
import personalCsvbyUser.UserCSVEditorGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class WelcomeUserPanel extends JFrame {

    private Map<String, String> adminDatabase;

    public WelcomeUserPanel() {
        setTitle("Welcome User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 450);
        setLocationRelativeTo(null); // Center the frame on screen

        // Simulated admin credentials database
        adminDatabase = new HashMap<>();
        adminDatabase.put("admin", "adminpass");

        // Create the background panel with the image
        ImagePanel backgroundPanel = new ImagePanel("/image/henry-be-lc7xcWebECc-unsplash.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        // Create a back button with the specified image
        ImageIcon backIcon = new ImageIcon(getClass().getResource("/UI/back.png"));
        JButton backButton = createStyledImageButton(backIcon);

        // Panel for the back button (top left corner)
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setOpaque(false); // Make panel transparent
        topLeftPanel.add(backButton);
        backgroundPanel.add(topLeftPanel, BorderLayout.NORTH); // Add the panel to the top left

        JLabel welcomeLabel = new JLabel("Welcome User Panel!");
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
                ReadGenaeralCSVEditorGUI r2 = new ReadGenaeralCSVEditorGUI();
                WelcomeUserPanel.this.setVisible(false);
                r2.setVisible(true);
                WelcomeUserPanel.this.dispose();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserCSVEditorGUI r2 = new UserCSVEditorGUI();
                WelcomeUserPanel.this.setVisible(false);
                r2.setVisible(true);
                WelcomeUserPanel.this.dispose();
            }
        });

        // Back button action listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new instance of DashboardUI with adminDatabase parameter
                DashboardUI dashboardUI = new DashboardUI(adminDatabase);
                WelcomeUserPanel.this.setVisible(false);
                dashboardUI.setVisible(true);
                WelcomeUserPanel.this.dispose();
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
        button.setBorder(BorderFactory.createLineBorder(color.darker(), 2));
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
                new WelcomeUserPanel();
            }
        });
    }
}
