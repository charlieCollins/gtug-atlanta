package com.totsp.guavaguice;

import static junit.framework.Assert.*;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.List;

public class SplittersJoinersTest {

   @Test
   public void splitterBasicTest() {      
      String csv = "first, second, third, fourth, fifth, sixth";
      List<String> parts = Lists.newArrayList(Splitter.on(',').split(csv));
      assertEquals(6, parts.size());           
   }
   
   
   @Test
   public void joinerBasicTest() {
      List<String> list = Lists.newArrayList("first", "second", "third", "fourth", "fifth", "sixth");
      String csv = Joiner.on(',').join(list);
      assertEquals("first,second,third,fourth,fifth,sixth", csv);           
   }
}
