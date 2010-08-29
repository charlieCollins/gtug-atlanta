package com.totsp.guavaguice;

import static junit.framework.Assert.assertEquals;

import com.google.common.collect.ImmutableList;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

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
   public void testImmutableList() {
      try {
         GUAVA_STRINGS.add("four");
         Assert.fail();
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
         Assert.fail();
      } catch (UnsupportedOperationException e) {
         // expected
      }
      
      // NOTE copyOf will return the SAME OBJECT if it's already immutable 
      // it's ok to make as many defensive copies as you want, just favor immutability to start
      List<String> list4 = ImmutableList.copyOf(list3);
      assertEquals(3, list4.size());
   }
}
