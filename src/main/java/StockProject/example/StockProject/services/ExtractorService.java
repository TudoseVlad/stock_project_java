package StockProject.example.StockProject.services;

import StockProject.example.StockProject.Helpers.Common;
import StockProject.example.StockProject.Helpers.CsvOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.smartcardio.ResponseAPDU;

@RestController
@RequestMapping("/extractor")
public class ExtractorService {
    @PostMapping("/upload")
    public ResponseEntity<List<Common>> uploadCSV(@RequestParam("csvfile") MultipartFile file){
        List<Common> allData = new ArrayList<>();
        try{
            Path tempPath = Files.createTempFile("uploaded-", file.getOriginalFilename());
            file.transferTo(tempPath.toFile());
            allData = CsvOperations.readCSV(tempPath.toString());
            List<Common> sol = randomize(allData);
            CsvOperations.writeCSV(Common.OUTPUT_FILE, sol);
            Files.delete(tempPath);

            return ResponseEntity.ok(sol);

        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    private List<Common> randomize(List<Common> data) {
        if (data.size() < 10) {
            return data;
        }
        Collections.sort(data);
        Random rand = new Random();
        List<Common> sol = new ArrayList<>();
        int startingPos = rand.nextInt(data.size() - 10);
        for (int i = startingPos; i < startingPos + 10; i++) {
            sol.add(data.get(i));
        }
        return sol;
    }
}
