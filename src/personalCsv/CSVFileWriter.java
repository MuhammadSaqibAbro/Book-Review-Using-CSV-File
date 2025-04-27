/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personalCsv;

/**
 *
 * @author SAQIB
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVFileWriter {

    public static void writeCSV(String filePath, List<String[]> data) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String[] record : data) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < record.length; i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(escapeSpecialCharacters(record[i]));
                }
                writer.println(sb.toString());
            }
        }
    }

    private static String escapeSpecialCharacters(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        } else {
            return value;
        }
    }
}

