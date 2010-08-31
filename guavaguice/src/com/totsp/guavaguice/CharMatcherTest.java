package com.totsp.guavaguice;

import static junit.framework.Assert.assertEquals;

import com.google.common.base.CharMatcher;

import org.junit.Test;

public class CharMatcherTest {   
   
   // what to mach against
   // what to do with the match
   
   @Test
   public void charMatcherTest() { 
      
      // this barely scratches the surface, a TON of useful stuff in CharMatcher
      
      String input = "onomatopoeia";
      assertEquals(12, input.length());
      String vowels = CharMatcher.anyOf("aeiouy").retainFrom(input);
      int vowelCount = CharMatcher.anyOf("aeiouy").countIn(input);
      assertEquals("ooaooeia", vowels);
      assertEquals(8, vowelCount);

      int nonVowelCount = CharMatcher.anyOf("aeiouy").negate().countIn(input);
      assertEquals(4, nonVowelCount);
      
      input = "123abc123";
      int letterCount = CharMatcher.inRange('a', 'z').countIn(input);
      assertEquals(3, letterCount);
      int numberCount = CharMatcher.DIGIT.countIn(input);
      assertEquals(6, numberCount);      
   }   
}
