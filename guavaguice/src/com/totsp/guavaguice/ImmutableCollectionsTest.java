package com.totsp.guavaguice;

import static junit.framework.Assert.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ImmutableCollectionsTest {

   // old way (<Java5)
   public static final List<String> OLD_STYLE_STRINGS;
   static {
      List<String> list = new ArrayList<String>(3);
      list.add("one");
      list.add("two");
      list.add("three");
      // note that it's very common to FORGET to do this and not lock down the "static final" member
      // often you'll see a static block edit the static member directly, or create it
      OLD_STYLE_STRINGS = Collections.unmodifiableList(list);
   }

   // better way (>=Java5)
   public static final List<String> NEW_STYLE_STRINGS =
            Collections.unmodifiableList(new ArrayList<String>(Arrays.asList("one", "two", "three")));

   // Guava way
   public static final List<String> GUAVA_STRINGS = ImmutableList.of("one", "two", "three");

   @Test
   public void immutableListTest() {
      try {
         GUAVA_STRINGS.add("four");
         fail();
      } catch (UnsupportedOperationException e) {
         // expected
      }

      // regular Java
      List<String> list1 = new ArrayList<String>(Arrays.asList(new String[] { "one", "two", "three" }));
      assertEquals(3, list1.size());

      // an "unmodifiable" list that CAN BE modified
      List<String> list2 = Collections.unmodifiableList(list1);
      list1.remove(2);
      assertEquals(2, list2.size()); // size not 3 any more, backing list was modified

      // an immutable list based off of a copy 
      list1.add("three");
      assertEquals(3, list1.size());
      List<String> list3 = ImmutableList.copyOf(list1);
      list1.remove(2);
      assertEquals(3, list3.size());
      try {
         list3.remove(2);
         fail();
      } catch (UnsupportedOperationException e) {
         // expected
      }
      
      // NOTE copyOf will return the SAME OBJECT if it's already immutable 
      // it's ok to make as many defensive copies as you want, just favor immutability to start
      List<String> list4 = ImmutableList.copyOf(list3);
      assertEquals(3, list4.size());
   }  
   
   @Test
   public void immutableSortedSetTest() {
      
      Set<String> set = ImmutableSortedSet.of("alpha", "beta", "gamma", "delta", "epsilon", "zeta");
      assertEquals(6, set.size());
      try {
         set.add("theta");
         fail();
      } catch (UnsupportedOperationException e) {
         // expected
      }
      
      // get particular items at an index in a Set (in this case a SortedSet)
      // note that SortedSet.of by DEFAULT uses natural ordering
      assertEquals("alpha", Iterables.get(set, 0));
      assertEquals("beta", Iterables.get(set, 1));
      assertEquals("delta", Iterables.get(set, 2));
      assertEquals("epsilon", Iterables.get(set, 3));
      assertEquals("gamma", Iterables.get(set, 4));
      assertEquals("zeta", Iterables.get(set, 5));
      
      // you can also use withExplicitOrder
      set = ImmutableSortedSet.withExplicitOrder("alpha", "beta", "gamma", "delta", "epsilon", "zeta");
      assertEquals(6, set.size());
      assertEquals("alpha", Iterables.get(set, 0));
      assertEquals("beta", Iterables.get(set, 1));
      assertEquals("gamma", Iterables.get(set, 2));
      assertEquals("delta", Iterables.get(set, 3));
      assertEquals("epsilon", Iterables.get(set, 4));
      assertEquals("zeta", Iterables.get(set, 5));
      
      // or your own Comparator -- which with Guava involves Ordering
      Ordering<String> lengthOrdering = new Ordering<String>() {
         public int compare(String left, String right) {
           return Ints.compare(left.length(), right.length()); // notice Ints
         }
       };
       // NOTE delta/epsilon/zeta aren't added because
       // they don't compare via the ordering (they are the same length as existing elements)
      set = ImmutableSortedSet.orderedBy(lengthOrdering).addAll(set).build();  // notice Builder (common, your friend)
      assertEquals(3, set.size());
      assertEquals("beta", Iterables.get(set, 0));
      assertEquals("alpha", Iterables.get(set, 1));
      assertEquals("epsilon", Iterables.get(set, 2));       
   }
   
   @Test
   public void smallImmutableMapTest() {
      // great for small "Constants Maps" 
      ImmutableMap<String, Integer> assetPrices = ImmutableMap.of("IBM", 100, "GOOG", 451);
      assertEquals(new Integer(451), assetPrices.get("GOOG"));
   }
}
