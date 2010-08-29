package com.totsp.guavaguice;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

import org.junit.Test;

public class ObjectsTest {

   @Test
   public void test() {

      Person person = new Person("First", "Last", 42);
      System.out.println(person.toString());
      System.out.println(person.hashCode());
   }

   // not using Mockito/etc, just being lazy here
   public class Person {
      private String firstName;
      private String lastName;
      private Integer age;

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
      
      @Override
      public int hashCode() {
         return Objects.hashCode(this.firstName, this.lastName, this.age);
      }
      
      @Override 
      public boolean equals(Object other) {
         // TODO use equivalence
         return super.equals(other);
      }

      public String getFirstName() {
         return this.firstName;
      }

      public void setFirstName(String firstName) {
         this.firstName = firstName;
      }

      public String getLastName() {
         return this.lastName;
      }

      public void setLastName(String lastName) {
         this.lastName = lastName;
      }

      public Integer getAge() {
         return this.age;
      }

      public void setAge(Integer age) {
         this.age = age;
      }
   }
}
