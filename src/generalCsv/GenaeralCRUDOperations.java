/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generalCsv;

/**
 *
 * @author SAQIB
 */
import java.io.IOException;
import java.util.List;

public class GenaeralCRUDOperations {

    private static final String CSV_FILE_PATH = System.getProperty("user.home") + "/Desktop/bookreview.csv";

    public static List<String[]> getAllRecords() throws IOException {
        return GenaeralCSVFileReader.readCSV(CSV_FILE_PATH);
    }

    public static void addRecord(String[] newRecord) throws IOException {
        List<String[]> records = getAllRecords();
        records.add(newRecord);
        GenaeralCSVFileWriter.writeCSV(CSV_FILE_PATH, records);
    }

    public static void updateRecord(int index, String[] updatedRecord) throws IOException {
        List<String[]> records = getAllRecords();
        records.set(index, updatedRecord);
        GenaeralCSVFileWriter.writeCSV(CSV_FILE_PATH, records);
    }

    public static void deleteRecord(int index) throws IOException {
        List<String[]> records = getAllRecords();
        records.remove(index);
        GenaeralCSVFileWriter.writeCSV(CSV_FILE_PATH, records);
    }
}

