//package main.java;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;



public class CSVGenerator {

    public static void createCsvData(BigDecimal spread, Map currencyAndRate) throws ParserConfigurationException, IOException, SAXException {
        String header = "symbol,buy,sell";

//        StringBuilder csvRows = new StringBuilder();


        List<Object> currencyAndRateArr = List.of(currencyAndRate.keySet().toArray());

        String currentDirectory = System.getProperty("user.dir");
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String fileName = "rates-"+ currentDate + ".csv";
        if(Files.notExists(Paths.get(currentDirectory + "\\" + fileName))){
            FileWriter fileWriter = new FileWriter(currentDirectory + "\\" + fileName);
            fileWriter.write(header);

            for (int i = 0; i < currencyAndRate.size(); i++) {
                String currency = (String) currencyAndRateArr.get(i);
                BigDecimal rate = new BigDecimal((String) currencyAndRate.get(currency));
                String row = "EUR" + currency + "," + CurrencyRateCalculator.calculateBuyRate(rate, spread) + "," + CurrencyRateCalculator.calculateSellRate(rate, spread);
                fileWriter.write(row);
            }

            fileWriter.close();
        }
    }

}