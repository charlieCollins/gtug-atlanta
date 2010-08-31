package com.totsp.guavaguice;

import static junit.framework.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;
import com.google.inject.internal.Iterables;

public class IterablesTest {

   // filter
   // concat
   // frequency
   // any/all/
   
   @Test
   public void firstElementTest() {
	   Set<String> set = Sets.newHashSet("one");
	   assertEquals("one", Iterables.getOnlyElement(set));
   }
   
   // TODO
}
