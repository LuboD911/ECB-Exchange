import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    private static final Double spread = 0.05;

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        HashMap<String, String> currencyAndRate = ECBExchange.readXML();
        ECBExchange.createCsvData(spread, currencyAndRate);
        System.out.println(ECBExchange.rate("USD","BGN", currencyAndRate, spread));


    }


}


