package personalCsvbyUser;

import UI.WelcomeUserPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

public class UserCSVEditorGUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton sortButton;
    private JTextField searchField;
    private JButton searchButton;
    private int sortStatus = 0; // 0 = original, 1 = ascending, 2 = descending

    public UserCSVEditorGUI() {
        setTitle("CSV Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setBackground(new Color(173, 216, 230)); // Light blue background color for the table
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLUE);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Personal CSV Database");
        titledBorder.setTitleColor(Color.WHITE);
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                titledBorder));

        getContentPane().setBackground(Color.WHITE);
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
            String filePath = "src/personalCsvbyUser/personal.csv"; // Relative path to CSV file

            try {
                List<String[]> records = UserCSVFileReader.readCSV(filePath);
                displayRecords(records);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(UserCSVEditorGUI.this, "Error loading CSV file", "Error", JOptionPane.ERROR_MESSAGE);
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
            try {
                UserCSVFileWriter.writeCSV("src/personalCsvbyUser/personal.csv", getDataFromTableModel());
                JOptionPane.showMessageDialog(UserCSVEditorGUI.this, "CSV file updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(UserCSVEditorGUI.this, "Error updating CSV file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        sortButton.addActionListener(e -> sortDataByTitle());

        searchButton.addActionListener(e -> filterByTitle(searchField.getText().trim()));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLUE);
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
        goBackButton.addActionListener(e -> {
                dispose();
        WelcomeUserPanel welcomePanel = new WelcomeUserPanel(); // Assuming WelcomeUserPanel is the panel/frame you want to show
            welcomePanel.setVisible(true);
        });

        // Add "Go Back" button to titlePanel (top-left)
        titlePanel.add(goBackButton, BorderLayout.WEST);

        setLocationRelativeTo(null);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLUE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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
                rowDataFiltered[j] = (j < rowData.length) ? rowData[j] : "";
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
                sortData(0, true);
                break;
            case 2: // Descending order
                sortData(0, false);
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
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
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

            UserCSVEditorGUI gui = new UserCSVEditorGUI();
            gui.setVisible(true);
        });
    }
}
