package readGeneralCSVbyUser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadGeneralCSVFileReader {

    public static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        // Use a FileReader to read the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the CSV line by comma
                String[] fields = line.split(",");
                records.add(fields);
            }
        }
        return records;
    }
}
