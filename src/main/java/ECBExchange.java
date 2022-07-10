import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.opencsv.CSVWriter;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ECBExchange {

    public static Double rate(String from, String to, HashMap map, Double spread){
        Double rate;

        if(Objects.equals(from, "EUR")){
            rate = Double.parseDouble(map.get(to).toString());
            rate = calculateBuyRate(rate, spread);
        }else if(Objects.equals(to, "EUR")){
            rate = Double.parseDouble(map.get(from).toString());
            rate = calculateSellRate(rate, spread);
        }else{
            Double rate1 = Double.parseDouble(map.get(from).toString());
            rate1 = calculateSellRate(rate1, spread);
            rate = Double.parseDouble(map.get(to).toString());
            rate = calculateBuyRate(rate, spread) * rate1;
        }

        return rate;

    }

    private static Double calculateBuyRate(Double rate, Double spread){

        return rate * (1-spread);
    }

    private static Double calculateSellRate(Double rate, Double spread){

        return 1/rate * (1+spread);

    }

    public static void createCsvData(Double spread, HashMap currencyAndRate) throws ParserConfigurationException, IOException, SAXException {
        String[] header = {"symbol", "buy", "sell"};

        ArrayList<String[]> output = new ArrayList<>();
        output.add(header);

        List<Object> currencyAndRateArr = List.of(currencyAndRate.keySet().toArray());

        for (int i = 0; i < currencyAndRate.size(); i++) {

            String currency = (String) currencyAndRateArr.get(i);
            Double rate = Double.parseDouble((String) currencyAndRate.get(currency));
            String[] row = new String[]{"EUR" + currency, calculateBuyRate(rate, spread).toString(), calculateSellRate(rate, spread).toString()};
            output.add(row);

        }

        System.out.println(output);

        String currDirectory = System.getProperty("user.dir");
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        CSVWriter writer = new CSVWriter(new FileWriter(currDirectory + "\\" + date + ".csv"));
        writer.writeAll(output);
        writer.close();

    }


    public static HashMap<String, String > readXML() throws ParserConfigurationException, IOException, SAXException {

        HashMap<String, String > output = new HashMap<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document xmlDoc = db.parse(new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml").openStream());
        Document xmlDoc = db.parse(new URL("http://127.0.0.1:5000/xml").openStream());

        NodeList nodeList = xmlDoc.getElementsByTagName("Cube");

        for(int i=0; i<nodeList.getLength(); i++){

            Element el = (Element) nodeList.item(i);

            if(el.hasAttribute("currency")) {
                String rate = el.getAttribute("rate");
                String currency = el.getAttribute("currency");
                output.put(currency, rate);
            }


        }

        return output;
    }


}
