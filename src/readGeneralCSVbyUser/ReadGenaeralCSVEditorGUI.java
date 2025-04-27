package readGeneralCSVbyUser;

import UI.WelcomeUserPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.border.TitledBorder;

public class ReadGenaeralCSVEditorGUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public ReadGenaeralCSVEditorGUI() {
        setTitle("CSV Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setBackground(new Color(173, 216, 230));

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLUE);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("General CSV Database");
        titledBorder.setTitleColor(Color.WHITE);
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                titledBorder));

        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton loadButton = new JButton("Load CSV");
        loadButton.addActionListener(e -> {
            try {
                // Get the URL of the CSV file
                File file = new File(getClass().getResource("/readGeneralCSVbyUser/bookreview.csv").getFile());
                List<String[]> records = ReadGeneralCSVFileReader.readCSV(file.getAbsolutePath());
                displayRecords(records);

                // Add default sorting by first column (Title)
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);
                List<RowSorter.SortKey> sortKeys = List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ReadGenaeralCSVEditorGUI.this, "Error loading CSV file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton sortButton = new JButton("Sort by Title (ASC)");
        sortButton.addActionListener(e -> {
            TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
            if (sorter == null) {
                sorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);
            }

            List<RowSorter.SortKey> sortKeys = (List<RowSorter.SortKey>) sorter.getSortKeys();
            if (sortKeys.size() > 0 && sortKeys.get(0).getColumn() == 0 && sortKeys.get(0).getSortOrder() == SortOrder.ASCENDING) {
                sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.DESCENDING)));
                sortButton.setText("Sort by Title (DESC)");
            } else {
                sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
                sortButton.setText("Sort by Title (ASC)");
            }
        });

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> filterByTitle(searchField.getText().trim()));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.BLUE);
        buttonPanel.add(loadButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(new JLabel("Search:"));
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Create "Go Back" button
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            dispose(); // Close the current frame
            WelcomeUserPanel welcomePanel = new WelcomeUserPanel(); // Assuming WelcomeUserPanel is the panel/frame you want to show
            welcomePanel.setVisible(true); // Show the WelcomeUserPanel
        });

        // Add "Go Back" button to titlePanel (top-left)
        titlePanel.add(goBackButton, BorderLayout.WEST);

        setLocationRelativeTo(null);
    }

    private void displayRecords(List<String[]> records) {
        String[] columnNames = {"Title", "Author", "Rating", "Reviews"};
        tableModel.setColumnIdentifiers(columnNames);

        tableModel.setRowCount(0); // Clear existing table data

        for (String[] rowData : records) {
            tableModel.addRow(rowData);
        }
    }

    private void filterByTitle(String searchText) {
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
        if (sorter == null) {
            sorter = new TableRowSorter<>(tableModel);
            table.setRowSorter(sorter);
        }

        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            try {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
            } catch (java.util.regex.PatternSyntaxException e) {
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

            ReadGenaeralCSVEditorGUI gui = new ReadGenaeralCSVEditorGUI();
            gui.setVisible(true);
        });
    }
}
