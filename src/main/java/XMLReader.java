//package main.java;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class XMLReader {

    private static final String ECB_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    public static Map<String, String > readFXRates() throws ParserConfigurationException, IOException, SAXException {

        Map<String, String > fxRates = new HashMap<>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document xmlDoc = documentBuilder.parse(new URL(ECB_URL).openStream());
//        Document xmlDoc = documentBuilder.parse(new URL("http://127.0.0.1:5000/xml").openStream());

        NodeList nodeList = xmlDoc.getElementsByTagName("Cube");

        for(int i=0; i<nodeList.getLength(); i++){

            Element el = (Element) nodeList.item(i);

            if(el.hasAttribute("currency")) {
                fxRates.put(el.getAttribute("currency"), el.getAttribute("rate"));
            }
        }

        return fxRates;
    }

}