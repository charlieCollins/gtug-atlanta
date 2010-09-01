package com.totsp.guavaguice;

import static junit.framework.Assert.assertEquals;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import org.junit.Test;

public class BiMapTest {   
   
   @Test
   public void biMapBasicTest() {      
      
      // note FAVORING OF STATIC FACTORY METHODS (like HashBiMap.create or Lists.newArrayList)
      // constructors are horrible at generic type inference (static methods are not)
      // and static factory methods have other advantages (can re-use objects, can use subclasses, etc.)
      // (Guava has public no arg ctors for reflection, etc, but not regular ones you might expect)
      BiMap<String, Integer> biMap = HashBiMap.create();      
      biMap.put("one", 1);
      biMap.put("two", 2);
      biMap.put("three", 3);
      assertEquals(new Integer(1), biMap.get("one"));
      
      BiMap<Integer, String> inverseBiMap = biMap.inverse();
      assertEquals("one", inverseBiMap.get(1));      
   }   
}
