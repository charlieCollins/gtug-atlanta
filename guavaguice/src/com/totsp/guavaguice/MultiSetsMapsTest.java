package com.totsp.guavaguice;

import static junit.framework.Assert.assertEquals;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiSetsMapsTest {
   
   // Multiset is a "Bag"
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
      
      // OLD WAY
      Map<String, List<String>> namesToNicknames = new HashMap<String, List<String>>();
      List<String> nicknames = namesToNicknames.get("Jack");
      if (nicknames == null) {
         nicknames = new ArrayList<String>();
         namesToNicknames.put("Jack", nicknames);
      }
      nicknames.add("John");
      nicknames.add("Jake");
      nicknames.add("Jacob");
      nicknames.add("J-man");
      nicknames.add("Jmeister, making coffee!");
      assertEquals(5, namesToNicknames.get("Jack").size());
      // how do we get a particular nickname, like the longest one?
      // loop over them all one by one and store a temporary variable with longest?
      
      // GUAVA WAY 
      // (note it's Multimap and not MultiMap like Apache Commons, I think Commons name is better ;))
      // (after all it's not a Hashmap or even a Bimap [Google's own naming is inconsistent?], "Multimap" is also a common term though)
      Multimap<String, String> names = ArrayListMultimap.create();
      names.put("Jack", "John");
      names.put("Jack", "Jake");
      names.put("Jack", "Jacob");
      names.put("Jack", "Jzzster");
      names.put("Jack", "Jabulani");
      names.put("Lawrence", "Larry"); // add another with different key, just for good measure
      assertEquals(5, names.get("Jack").size());
      
      // longest
      assertEquals("Jabulani", Collections.max(names.get("Jack"), new Ordering<String>() {
         public int compare(String left, String right) {
            return Ints.compare(left.length(), right.length()); 
         }
      }));
      
      // natural
      assertEquals("Jzzster", Collections.max(names.get("Jack"), Ordering.natural()));      
   }   
}
