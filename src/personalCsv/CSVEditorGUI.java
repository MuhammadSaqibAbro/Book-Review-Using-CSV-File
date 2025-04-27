package personalCsv;

import UI.WelcomeAdminPanel;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.swing.border.TitledBorder;

public class CSVEditorGUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private File selectedFile;
    private JButton sortButton;
    private JButton searchButton;
    private JTextField searchField;
    private int sortStatus = 0; // 0 = original, 1 = ascending, 2 = descending

    public CSVEditorGUI() {
        setTitle("CSV Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setBackground(new Color(173, 216, 230)); // Light blue background color for the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a custom panel for the titled border
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLUE); // Set background color to blue

        // Create a titled border with centered title
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Personal CSV Database");
        titledBorder.setTitleColor(Color.WHITE); // Set border title color to white
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 16)); // Set border title font
        titlePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10), // Add padding around the border
                titledBorder)); // Set the titled border to the panel

        // Add the titlePanel and scrollPane to the content pane
        getContentPane().setBackground(Color.WHITE); // Set main content pane background color to white
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton loadButton = createStyledButton("Load CSV");
        JButton addButton = createStyledButton("Add Record");
        JButton deleteButton = createStyledButton("Delete Record");
        JButton updateButton = createStyledButton("Update CSV");
        sortButton = createStyledButton("Sort by Title");
        searchField = new JTextField(20);
        searchButton = createStyledButton("Search");

        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose CSV File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

            int userSelection = fileChooser.showOpenDialog(CSVEditorGUI.this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                try {
                    List<String[]> records = CSVFileReader.readCSV(selectedFile.getAbsolutePath());
                    displayRecords(records);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CSVEditorGUI.this, "Error loading CSV file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addButton.addActionListener(e -> tableModel.addRow(new Object[tableModel.getColumnCount()]));

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
            }
        });

        updateButton.addActionListener(e -> {
            if (selectedFile != null) {
                try {
                    CSVFileWriter.writeCSV(selectedFile.getAbsolutePath(), getDataFromTableModel());
                    JOptionPane.showMessageDialog(CSVEditorGUI.this, "CSV file updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CSVEditorGUI.this, "Error updating CSV file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(CSVEditorGUI.this, "No CSV file loaded", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        sortButton.addActionListener(e -> sortDataByTitle());

        searchButton.addActionListener(e -> filterByTitle(searchField.getText().trim()));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLUE); // Set button panel background color to blue
        buttonPanel.add(loadButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Create "Go Back" button
        JButton goBackButton = createStyledButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform actions to go back (e.g., close this window)
                dispose(); // Close the frame
                 WelcomeAdminPanel welcomePanel = new WelcomeAdminPanel(); // Assuming WelcomeUserPanel is the panel/frame you want to show
            welcomePanel.setVisible(true);
            }
        });

        // Add "Go Back" button to titlePanel (top-left)
        titlePanel.add(goBackButton, BorderLayout.WEST);

        // Center the JFrame on the screen
        setLocationRelativeTo(null);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLUE); // Set button background color to blue
        button.setForeground(Color.BLACK); // Set button text color to black
        button.setFocusPainted(false); // Remove focus border
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Set button font
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding
        return button;
    }

    private void displayRecords(List<String[]> records) {
        String[] staticColumns = {"Title", "Author", "Rating", "Reviews", "Status", "Time spent", "Start Date", "End Date", "User Rating", "User Review"};
        tableModel.setColumnIdentifiers(staticColumns);

        if (records.isEmpty() || records.size() <= 1) {
            JOptionPane.showMessageDialog(this, "No data found in the CSV file", "Empty CSV", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (int i = 1; i < records.size(); i++) {
            String[] rowData = records.get(i);
            Object[] rowDataFiltered = new Object[staticColumns.length];

            for (int j = 0; j < staticColumns.length; j++) {
                rowDataFiltered[j] = (j < rowData.length) ? rowData[j] : ""; // Prevent IndexOutOfBoundsException
            }

            tableModel.addRow(rowDataFiltered);
        }
    }

    private List<String[]> getDataFromTableModel() {
        List<String[]> data = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String[] row = new String[tableModel.getColumnCount()];
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                row[j] = String.valueOf(tableModel.getValueAt(i, j));
            }
            data.add(row);
        }
        return data;
    }

    private void sortDataByTitle() {
        switch (sortStatus) {
            case 0: // Original order (do nothing)
                break;
            case 1: // Ascending order
                sortData(0, true); // Sort by first column (title) in ascending order
                break;
            case 2: // Descending order
                sortData(0, false); // Sort by first column (title) in descending order
                break;
        }

        sortStatus = (sortStatus + 1) % 3;
    }

    private void sortData(int columnIndex, boolean ascending) {
        List<String[]> data = getDataFromTableModel();
        data.sort((a, b) -> (ascending) ? a[columnIndex].compareTo(b[columnIndex]) : b[columnIndex].compareTo(a[columnIndex]));

        tableModel.setRowCount(0);
        data.forEach(row -> tableModel.addRow(row));
    }

    private void filterByTitle(String searchText) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        if (searchText.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            try {
                RowFilter<DefaultTableModel, Object> rowFilter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        String title = entry.getStringValue(0);
                        return title.toLowerCase().contains(searchText.toLowerCase());
                    }
                };
                sorter.setRowFilter(rowFilter);

                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);
            } catch (PatternSyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            CSVEditorGUI gui = new CSVEditorGUI();
            gui.setVisible(true);
        });
    }
}
