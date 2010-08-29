package com.totsp.guavaguice;

import static junit.framework.Assert.*;

import com.google.common.base.Objects;

import org.junit.Test;

public class ObjectsTest {

   @Test
   public void toStringTest() {
      Person person = new Person("First", "Last", 42);
      assertEquals("Person{firstName=First, lastName=Last, age=42}", person.toString());
   }

   @Test
   public void hashCodeTest() {
      Person person1 = new Person("First", "Last", 42);
      Person person2 = new Person("First", "Last", 42);
      Person person3 = new Person("First", "Last", 17);
      assertTrue(person1.hashCode() == person2.hashCode());
      assertFalse(person1.hashCode() == person3.hashCode());
   }

   @Test
   public void equalsTest() {
      Person person1 = new Person("First", "Last", 42);
      Person person2 = new Person("First", "Last", 42);
      Person person3 = new Person("First", "Last", 17);
      assertEquals(person1, person2);
      assertFalse(person1.equals(person3));
   }
   
   // TODO firstNonNull is also interesting
   

   // not using Mockito/etc, just being lazy here
   // always favor immutability, and if need be use Builder for mutators (not setters though)
   public final class Person {
      private final String firstName;
      private final String lastName;
      private final Integer age;

      public Person(final String firstName, final String lastName, final Integer age) {
         this.firstName = firstName;
         this.lastName = lastName;
         this.age = age;
      }

      @Override
      public String toString() {
         return Objects.toStringHelper(this).add("firstName", this.firstName).add("lastName", this.lastName).add("age",
                  this.age).toString();
      }

      // Eclipse generated hashCode
      /*
      @Override
      public int hashCode() {
         final int prime = 31;
         int result = 1;
         result = prime * result + getOuterType().hashCode();
         result = prime * result + ((this.age == null) ? 0 : this.age.hashCode());
         result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
         result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
         return result;
      }
      */

      // Guava hashCode
      @Override
      public int hashCode() {
         return Objects.hashCode(this.firstName, this.lastName, this.age);
      }

      // TODO used to using equalsBuilder or letting Eclipse handle this
      // almost *everyone* gets this wrong, including Eclipse gen (uses mutable fields)
      // (http://www.artima.com/lejava/articles/equality.html)
      // is there a quick/solid way with Guava/Equivalence, etc? 
      @Override
      public boolean equals(Object obj) {
         if (this == obj)
            return true;
         if (obj == null)
            return false;
         if (!(obj instanceof Person))
            return false;
         Person other = (Person) obj;        
         if (this.age == null) {
            if (other.age != null)
               return false;
         } else if (!this.age.equals(other.age))
            return false;
         if (this.firstName == null) {
            if (other.firstName != null)
               return false;
         } else if (!this.firstName.equals(other.firstName))
            return false;
         if (this.lastName == null) {
            if (other.lastName != null)
               return false;
         } else if (!this.lastName.equals(other.lastName))
            return false;
         return true;
      }

      public String getFirstName() {
         return this.firstName;
      }

      public String getLastName() {
         return this.lastName;
      }

      public Integer getAge() {
         return this.age;
      }
   }
}
