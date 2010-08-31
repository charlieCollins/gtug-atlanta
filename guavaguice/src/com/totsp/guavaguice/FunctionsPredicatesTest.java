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
   
   // A Function does a "transformation" from one object to another, has 1 input, 1 output -- f(x)
   // often does with anonymous inner classes, for now (Java 7 closures will make this even better)
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
   
   // A Predicate determines true or false for a given input
   // again often done with anonymous inner classes, 
   // when you start combining this this stuff it gets powerful
   // Function/Predicate/Collections/Iterables/CharMatcher/etc
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
