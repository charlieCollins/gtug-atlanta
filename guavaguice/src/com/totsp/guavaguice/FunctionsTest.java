package com.totsp.guavaguice;

import static junit.framework.Assert.assertEquals;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.junit.Test;

import java.util.Map;

public class FunctionsTest {
   
   
   @Test
   public void transformValuesTest() {
      // base currency US Dollar
      ImmutableMap<String, Double> usdAssetPrices = ImmutableMap.of("IBM", 123.40, "CSCO", 20.31, "GOOG", 451.0);      

      // Canadian Dollar
      Map<String, Double> cadAssetPrices = Maps.transformValues(usdAssetPrices, new ExchangeRateFunction(1.06));

      // Australian Dollar
      Map<String, Double> audAssetPrices = Maps.transformValues(usdAssetPrices, new ExchangeRateFunction(1.11));

      // Mexican Peso
      Map<String, Double> mxnAssetPrices = Maps.transformValues(usdAssetPrices, new ExchangeRateFunction(13.13));
      
      assertEquals(130.804, cadAssetPrices.get("IBM"), 0.1);
      assertEquals(136.974, audAssetPrices.get("IBM"), 0.1);
      assertEquals(1620.242, mxnAssetPrices.get("IBM"), 0.1);
   }
   
   @Test
   public void transformValuesViewTest() {
      // base currency US Dollar
      Map<String, Double> usdAssetPrices = Maps.newHashMap();
      usdAssetPrices.put("IBM", 123.40);
      usdAssetPrices.put("CSCO", 20.31);
      usdAssetPrices.put("GOOG", 451.40);           

      // Canadian Dollar
      Map<String, Double> cadAssetPrices = Maps.transformValues(usdAssetPrices, new ExchangeRateFunction(1.06));

      // Australian Dollar
      Map<String, Double> audAssetPrices = Maps.transformValues(usdAssetPrices, new ExchangeRateFunction(1.11));

      // Mexican Peso
      Map<String, Double> mxnAssetPrices = Maps.transformValues(usdAssetPrices, new ExchangeRateFunction(13.13));
      
      assertEquals(130.804, cadAssetPrices.get("IBM"), 0.1);
      assertEquals(136.974, audAssetPrices.get("IBM"), 0.1);
      assertEquals(1620.242, mxnAssetPrices.get("IBM"), 0.1);
      
      // NOTE - the transformed Maps are only VIEWS, edit the original Map and they are updated
      // (can also copy them, but default are views)
      usdAssetPrices.put("IBM", 100.0);
      
      assertEquals(106.0, cadAssetPrices.get("IBM"), 0.1);
      assertEquals(111.0, audAssetPrices.get("IBM"), 0.1);
      assertEquals(1313.0, mxnAssetPrices.get("IBM"), 0.1);
   }

   class ExchangeRateFunction implements Function<Double, Double> {
      private double exchangeRate;
      public ExchangeRateFunction(final double exchangeRate) {
         this.exchangeRate = exchangeRate;
      }
      public Double apply(final Double input) {
         return input * this.exchangeRate;
      }
   }
}
