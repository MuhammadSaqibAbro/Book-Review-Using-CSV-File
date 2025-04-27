package generalCsv;
import UI.WelcomeAdminPanel;
import generalCsv.GenaeralCSVFileReader;
import generalCsv.GenaeralCSVFileWriter;
import javax.swing.*;
import javax.swing.border.TitledBorder;
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
import javax.swing.filechooser.FileNameExtensionFilter;

public class GenaeralCSVEditorGUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private File selectedFile;
    private JTextField searchField;
    private int sortStatus = 0; // 0 = original, 1 = ascending, 2 = descending

    public GenaeralCSVEditorGUI() {
        setTitle("CSV Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setBackground(new Color(173, 216, 230)); // Set table background color to light blue

        JScrollPane scrollPane = new JScrollPane(table);

        // Create a custom panel for the titled border
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLUE); // Set background color to blue

        // Create a titled border with centered title
        TitledBorder titledBorder = BorderFactory.createTitledBorder("General CSV Database");
        titledBorder.setTitleColor(Color.WHITE); // Set border title color to white
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 16)); // Set border title font
        titlePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10), // Add padding around the border
                titledBorder)); // Set the titled border to the panel

        // Add "Go Back" button to the titlePanel (top left)
        JButton goBackButton = createStyledButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform actions to go back (e.g., close this window)
                 dispose();
        WelcomeAdminPanel welcomePanel = new WelcomeAdminPanel(); // Assuming WelcomeUserPanel is the panel/frame you want to show
            welcomePanel.setVisible(true);
                 // Close the frame
            }
        });
        titlePanel.add(goBackButton, BorderLayout.WEST); // Align button to the left

        // Add the titlePanel and scrollPane to the content pane
        getContentPane().setBackground(Color.WHITE); // Set main content pane background color to white
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create button panel for CRUD operations
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.BLUE); // Set button panel background color to blue

        // Load CSV button
        JButton loadButton = createStyledButton("Load CSV");
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose CSV File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
            int userSelection = fileChooser.showOpenDialog(GenaeralCSVEditorGUI.this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                try {
                    List<String[]> records = GenaeralCSVFileReader.readCSV(selectedFile.getAbsolutePath());
                    displayRecords(records);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(GenaeralCSVEditorGUI.this, "Error loading CSV file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(loadButton);

        // Add Record button
        JButton addButton = createStyledButton("Add Record");
        addButton.addActionListener(e -> tableModel.addRow(new Object[tableModel.getColumnCount()]));
        buttonPanel.add(addButton);

        // Delete Record button
        JButton deleteButton = createStyledButton("Delete Record");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
            }
        });
        buttonPanel.add(deleteButton);

        // Update CSV button
        JButton updateButton = createStyledButton("Update CSV");
        updateButton.addActionListener(e -> {
            if (selectedFile != null) {
                try {
                    GenaeralCSVFileWriter.writeCSV(selectedFile.getAbsolutePath(), getDataFromTableModel());
                    JOptionPane.showMessageDialog(GenaeralCSVEditorGUI.this, "CSV file updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(GenaeralCSVEditorGUI.this, "Error updating CSV file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(GenaeralCSVEditorGUI.this, "No CSV file loaded", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(updateButton);

        // Sort by Title button
        JButton sortButton = createStyledButton("Sort by Title");
        sortButton.addActionListener(e -> sortDataByTitle());
        buttonPanel.add(sortButton);

        // Search field and Search button
        searchField = new JTextField(20);
        JButton searchButton = createStyledButton("Search");
        searchButton.addActionListener(e -> filterByTitle(searchField.getText().trim()));
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

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
        String[] staticColumns = {"Title", "Author", "Rating", "Reviews"};
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
        data.sort((a, b) -> ascending ? a[columnIndex].compareTo(b[columnIndex]) : b[columnIndex].compareTo(a[columnIndex]));

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

            GenaeralCSVEditorGUI gui = new GenaeralCSVEditorGUI();
            gui.setVisible(true);
        });
    }
}
