package com.totsp.guavaguice;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.Constraints;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.MapConstraint;
import com.google.common.collect.MapConstraints;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class CollectionsTest {

   @Test
   public void listsBasicTest() {
      // regular Java
      List<String> list1 = new ArrayList<String>(Arrays.asList("one", "two", "three"));
      assertEquals(3, list1.size());

      // Guava
      List<String> list2 = Lists.newArrayList("one", "two", "three");
      assertEquals(3, list2.size());
   }

   @Test
   public void listConstraintsTest() {
      List<String> backingList = Lists.newArrayList();
      List<String> list = Constraints.constrainedList(backingList, Constraints.notNull());
      list.add("one");
      list.add("two");
      list.add("three");
      try {
         list.add(null);
         fail();
      } catch (NullPointerException e) {
         // expected
      }
      assertEquals(3, list.size());
   }

   @Test
   public void listTransformTest() {
      List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
      assertEquals(new Integer(5), list.get(4));
      
      List<Integer> transformedList = Lists.newArrayList(Iterables.transform(list, new Function<Integer, Integer>() {
         public Integer apply(final Integer input) {
            return input * 10;
         }
      }));
      assertEquals(5, transformedList.size());      
      assertEquals(new Integer(50), transformedList.get(4));
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

   // NOTE this is NOT the best way to do fibonacci, intentionally a slow algorithm
   private long fib(int n) {
      Preconditions.checkArgument(n >= 0, "only positive integers, please");
      if (n <= 1)
         return n;
      else
         return fib(n - 1) + fib(n - 2);
   }

   // Constraints are very impressive too
   @Test
   public void mapConstraintsTest() {
      Map<String, String> backingMap = Maps.newHashMap();
      Map<String, String> map = MapConstraints.constrainedMap(backingMap, new MapConstraint<String, String>() {
         public void checkKeyValue(String key, String value) {
            Preconditions.checkNotNull(value, "value cannot be null, by constraint");
         }
      });
      map.put("key1", "value1");
      map.put("key2", "value2");
      map.put("key3", "value3");
      try {
         map.put("key4", null);
         fail();
      } catch (NullPointerException e) {
         // expected
      }
      assertEquals(3, map.size());
   }
}
