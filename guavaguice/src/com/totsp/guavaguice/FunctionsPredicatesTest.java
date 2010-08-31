package com.totsp.guavaguice;

import static junit.framework.Assert.assertEquals;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.List;

public class FunctionsPredicatesTest {
   
   
   @Test
   public void functionsBasicTest() {
      List<String> list = Lists.newArrayList("one", "tWo", "THREE");
      List<String> upperList = ImmutableList.copyOf(Iterables.transform(list, new Function<String, String>() {
         public String apply(String input) {
            return input.toUpperCase();
         }
      }));
      assertEquals(3, upperList.size());
      assertEquals("ONE", upperList.get(0));
      assertEquals("TWO", upperList.get(1));
      assertEquals("THREE", upperList.get(2));
      
      List<String> lowerList = ImmutableList.copyOf(Iterables.transform(list, new Function<String, String>() {
         public String apply(String input) {
            return input.toLowerCase();
         }
      }));
      assertEquals(3, upperList.size());
      assertEquals("one", lowerList.get(0));
      assertEquals("two", lowerList.get(1));
      assertEquals("three", lowerList.get(2));
   }
   
   @Test
   public void predicatesBasicTest() {
      List<String> list = Lists.newArrayList("one", "tWo", "THREE");
      List<String> upperList = ImmutableList.copyOf(Iterables.filter(list, new Predicate<String>(){
         public boolean apply(String input) {
            return CharMatcher.JAVA_UPPER_CASE.matchesAllOf(input);
         }
      }));
      assertEquals(1, upperList.size());
      assertEquals("THREE", upperList.get(0));      
   }   
}
