package StockProject.example.StockProject.Helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CsvOperations {

    // Method to read a CSV file and return a list of Info objects
    public static List<Common> readCSV(String filePath) throws IOException {
        List<Common> result = new ArrayList<>();
        String line;
        SimpleDateFormat dateFormat = new SimpleDateFormat(Common.TIME_LAYOUT);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length != 3) {
                    throw new IOException("Invalid data from CSV file");
                }

                String name = fields[0];
                String dateStr = fields[1];
                double price;

                try {
                    price = Double.parseDouble(fields[2]);
                } catch (NumberFormatException e) {
                    System.out.printf("Price invalid: %s%n", e.getMessage());
                    throw new IOException("Invalid price format in CSV file", e);
                }

                try {
                    Common info = new Common(dateFormat.parse(dateStr), name, price);
                    result.add(info);
                } catch (ParseException e) {
                    System.out.printf("Timestamp invalid: %s%n", e.getMessage());
                    throw new IOException("Invalid timestamp format in CSV file", e);
                }
            }
        }
        return result;
    }


    public static void writeCSV(String filePath, List<Common> data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Common info : data) {
                String record = String.format("%s,%s,%.2f%n", info.getName(),
                        new SimpleDateFormat(Common.TIME_LAYOUT).format(info.getTimestamp()), info.getPrice());
                bw.write(record);
            }
        }
    }
}