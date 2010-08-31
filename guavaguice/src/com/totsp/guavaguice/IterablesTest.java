package com.totsp.guavaguice;

import static junit.framework.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


public class IterablesTest {

   // concat
   // frequency
   // cycle
   // reverse
   // partition
   // transform
   // filter 
   // any/all/
   
   @Test
   public void firstElementTest() {
	   Set<String> set = Sets.newHashSet("one");
	   assertEquals("one", Iterables.getOnlyElement(set));
   }
   
   @Test
   public void concatTest() {
      List<String> list1 = Lists.newArrayList("one", "two", "three");
      List<String> list2 = Lists.newArrayList("four", "five", "six");
      List<String> list3 = Lists.newArrayList(Iterables.concat(list1, list2)); // varargs
      assertEquals(6, list3.size());      
   }
   
   @Test
   public void partitionTest() {
      List<String> list1 = Lists.newArrayList("one", "two", "three");
      List<List<String>> subLists = Lists.newArrayList(Iterables.partition(list1, 1));
      assertEquals(3, subLists.size());
   }   
}
