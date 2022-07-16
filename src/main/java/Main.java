//package main.java;

//import main.java.CSVGenerator;
//import main.java.CurrencyRateCalculator;
//import main.java.XMLReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final BigDecimal spread = new BigDecimal(0.05);

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Map<String, String> fxRates = XMLReader.readFXRates();
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Choose from currencies:");
        System.out.println(fxRates.keySet());
        System.out.println("Choose first currency:");
        String fromCurrency = myScanner.nextLine().trim().toUpperCase();
        System.out.println("Choose second currency:");
        String toCurrency = myScanner.nextLine().trim().toUpperCase();


        CSVGenerator.createCsvData(spread, fxRates);

        try {
            System.out.println("Your exchange rate is: " + CurrencyRateCalculator.rate(fromCurrency, toCurrency, fxRates, spread));
        }catch (NullPointerException e){
            System.out.println("Please enter a currency form the list");
            main(args);
        }

    }


}