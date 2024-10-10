package StockProject.example.StockProject.services;

import StockProject.example.StockProject.Helpers.Common;
import StockProject.example.StockProject.Helpers.CsvOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;


@RestController
public class PredictorService {
    @RequestMapping("/predictor")
    public ResponseEntity<List<Common>> Predict(){
        try{
        List<Common> data = new ArrayList<>();
        data = CsvOperations.readCSV(Common.OUTPUT_FILE);
        List<Common> sol = Common.simplePredict(data);
        return ResponseEntity.ok(sol);
        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
