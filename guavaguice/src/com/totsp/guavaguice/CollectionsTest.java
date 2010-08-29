package com.totsp.guavaguice;

import static junit.framework.Assert.assertEquals;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

public class CollectionsTest {

   @Test
   public void listsBasicTest() {
      // regular Java
      List<String> list1 = new ArrayList<String>(Arrays.asList(new String[] { "one", "two", "three" }));
      assertEquals(3, list1.size());

      // Guava
      List<String> list2 = Lists.newArrayList("one", "two", "three");
      assertEquals(3, list2.size());

      // an "unmodifiable" list that CAN BE modified
      List<String> list3 = Collections.unmodifiableList(list1);
      list1.remove(2);
      assertEquals(2, list3.size()); // size not 3 any more, backing list was modified

      // an immutable list
      list1.add("three");
      assertEquals(3, list1.size());
      List<String> list4 = ImmutableList.copyOf(list1);
      list1.remove(2);
      assertEquals(3, list4.size());
      try {
         list4.remove(2);
         Assert.fail();
      } catch (UnsupportedOperationException e) {
         // ignore
      }
   }

   @Test
   public void mapsBasicTest() {
      // the same with Maps (and all other Collections)
      Map<String, Integer> map = Maps.newHashMap();
      map.put("key1", 1);
      map.put("key2", 2);
      map.put("key3", 3);
      map.put("key4", null);
      assertEquals(4, map.size());

      // filter (can also filter entries)
      Map<String, Integer> nonNullValuesMap = Maps.filterValues(map, Predicates.notNull());
      assertEquals(3, nonNullValuesMap.size());

      // transform (can also transform entries)
      Map<String, Integer> transformMap = Maps.transformValues(map, Functions.constant(42));
      assertEquals(4, transformMap.size());
      assertEquals(new Integer(42), transformMap.get("key4"));

      // also see difference, fromProperties      
   }

   @Test
   public void mapMakerTest() {
      // more powerful than ever, MapMaker
      // yes, this is fricking cool, look twice at this    
      // (and notice it's a Builder, you don't have to use all the fancy at once)
      ConcurrentMap<Integer, Long> fibMap =
               new MapMaker().concurrencyLevel(32).softKeys().weakValues().expiration(30, TimeUnit.MINUTES)
                        .makeComputingMap(new Function<Integer, Long>() {
                           public Long apply(Integer key) {
                              return fib(key);
                           }
                        });
      assertEquals(new Long(0), fibMap.get(0));
      assertEquals(new Long(1), fibMap.get(1));
      assertEquals(new Long(1), fibMap.get(2));
      assertEquals(new Long(2), fibMap.get(3));
      assertEquals(new Long(3), fibMap.get(4));
      assertEquals(new Long(5), fibMap.get(5));
      assertEquals(new Long(55), fibMap.get(10));
   }

   @Test
   public void biMapTest() {
      // TODO BiMap
   }
   
   @Test
   public void mapConstraintsTest() {
      // TODO MapConstraints
   }

   // NOTE this is NOT the best way to do fibonacci, intentionally a slow algorithm
   private long fib(int n) {
      Preconditions.checkArgument(n >= 0, "only positive integers, please");
      if (n <= 1)
         return n;
      else
         return fib(n - 1) + fib(n - 2);
   }
}
