//package main.java;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;
import java.util.Objects;

public class CurrencyRateCalculator {

    private static BigDecimal one = new BigDecimal(1);
    public static BigDecimal rate(String from, String to, Map map, BigDecimal spread){
        BigDecimal rate;

        if(Objects.equals(from, "EUR")){
            rate = new BigDecimal(map.get(to).toString());
            rate = calculateBuyRate(rate, spread);
        }else if(Objects.equals(to, "EUR")){
            rate = new BigDecimal(map.get(from).toString());
            rate = calculateSellRate(rate, spread);
        }else{
            BigDecimal rate1 = new BigDecimal(map.get(from).toString());
            rate1 = calculateSellRate(rate1, spread);
            rate = new BigDecimal(map.get(to).toString());
            rate = calculateBuyRate(rate, spread).multiply(rate1, MathContext.DECIMAL128);
        }

        return rate;

    }

    public static BigDecimal calculateBuyRate(BigDecimal rate, BigDecimal spread){

        return rate.multiply(one.subtract(spread), MathContext.DECIMAL128);
    }

    public static BigDecimal calculateSellRate(BigDecimal rate, BigDecimal spread){

        return one.divide(rate).multiply(one.add(spread), MathContext.DECIMAL128);
//        WTF JAVA??
//        return  1/rate * (1+spread);

    }



}