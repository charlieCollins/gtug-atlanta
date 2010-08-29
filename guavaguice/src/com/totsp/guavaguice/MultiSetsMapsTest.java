package com.totsp.guavaguice;

import static junit.framework.Assert.*;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import org.junit.Test;

public class MultiSetsMapsTest {

   
   @Test
   public void mulitSetBasicTest() {      
      // it's a "bag" useful when you want some set semantics, but may have duplicatexs elements
      // better than a List, because you don't have to iterate to find out count, and not linear contains, etc.
      // it's the *right* abstraction
      Multiset<String> set = HashMultiset.create();
      set.add("one");
      set.add("one");
      set.add("one");      
      set.add("two");
      set.add("three");
      
      assertEquals(3, set.count("one"));
      assertEquals(3, set.elementSet().size()); // distinct elements
      assertEquals(5, set.size());      
   }
   
   @Test
   public void mulitMapBasicTest() {
      
      
      
   }
   
}
