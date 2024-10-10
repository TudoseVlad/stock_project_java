package  StockProject.example.StockProject.Helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Common implements Comparable<Common> {
    private Date timestamp;
    private String name;
    private double price;

    public static final String TIME_LAYOUT = "dd-MM-yyyy";
    public static final String OUTPUT_FILE =  "src/main/java/StockProject/example/StockProject/data/output.csv";
    public static final String LOCATION = Paths.get("").toAbsolutePath().toString();

    public Common(Date timestamp, String name, double price) {
        this.timestamp = timestamp;
        this.name = name;
        this.price = price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public static void printData(List<Common> data) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        for (Common val : data) {
            System.out.printf("Timestamp: %s, Name: %s, Price: %.2f%n", 
                    sdf.format(val.getTimestamp()), val.getName(), val.getPrice());
        }
    }

    @Override
    public int compareTo(Common other) {
        int timestampComparison = this.getTimestamp().compareTo(other.getTimestamp());
        if (timestampComparison == 0) {
            if (this.getPrice() == other.getPrice()) {
                return this.getName().compareTo(other.getName());
            }
            return Double.compare(this.getPrice(), other.getPrice());
        }
        return timestampComparison;
    }


    public static int findMax(List<Common> data) {
        int index = 0;
        double max = data.get(0).getPrice();
        for (int i = 1; i < data.size(); i++) {
            if (max < data.get(i).getPrice()) {
                max = data.get(i).getPrice();
                index = i;
            }
        }
        return index;
    }

    public static String[] find2ndMax(List<Common> data) {
        int index = findMax(data);
        double max = data.get(0).getPrice() * -1;
        String name = "";
        for (Common val : data) {
            if (max < val.getPrice() && val.getPrice() != data.get(index).getPrice()) {
                name = val.getName();
                max = val.getPrice();
            }
        }
        return new String[]{name, String.valueOf(max)};
    }

    public static List<Common> simplePredict(List<Common> data) {
        var timestamp = data.get(data.size() - 1).getTimestamp();
        double nPrice = data.get(data.size() - 1).getPrice();
        String[] secondMaxInfo = find2ndMax(data);
        String name = secondMaxInfo[0];
        double n1Price = Double.parseDouble(secondMaxInfo[1]);

        double n2Price = n1Price + (nPrice - n1Price) / 2;
        double n3Price = n2Price + (n1Price - n2Price) / 4;

        List<Common> predictions = data;
        predictions.add(new Common(new java.util.Date(timestamp.getTime() + 24 * 60 * 60 * 1000), name, n1Price));
        predictions.add(new Common(new java.util.Date(timestamp.getTime() + 48 * 60 * 60 * 1000), name, n2Price));
        predictions.add(new Common(new java.util.Date(timestamp.getTime() + 72 * 60 * 60 * 1000), name, n3Price));

        return predictions;
    }
}