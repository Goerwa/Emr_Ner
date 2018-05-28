package Vertextest;

import static org.junit.Assert.*;
import org.junit.Test;
import edge.NetworkConnection;
import vertex.Person;
import vertex.Word;

public class SocialNetworkvertextest {

  Person p1 = new Person("p1");
  Person p2 = new Person("p2");
  Person p3 = new Person("p1");
  Word w1 = new Word("w1");
  Word w2 = new Word("p1");


  // Testing strategy
  //
  // equasl():
  // label of vertex = same, not same
  // type of vertex = same, not same

  // label of vertex = same
  @Test
  public void testequals1() {
    assertTrue("expected true", p1.equals(p3));
  }

  // label of vertex = not same
  @Test
  public void testequals2() {
    assertFalse("expected false", p1.equals(p2));
  }

  // type of vertex = not same
  // label of vertex = not same
  @Test
  public void testequals3() {
    assertFalse("expected false", w1.equals(p1));
  }

  // type of vertex = same, not same
  // label of vertex = same
  @Test
  public void testequals4() {
    assertFalse("expected false", w2.equals(p1));
  }

  // Testing strategy
  //
  // checkRep():
  // gender = right,wrong
  // year = right,wrong


  // gender = right
  // year = wrong
  @Test(expected = AssertionError.class)
  public void testcheckrep1() {
    Person p = new Person("p", 0, "M");
  }

  // gender = wrong
  // year = right
  @Test(expected = AssertionError.class)
  public void testcheckrep2() {
    Person p = new Person("p", 10, "k");
  }

  // gender = right
  // year = right
  @Test
  public void testcheckrep3() {
    Person p = new Person("p", 10, "F");
  }

  // gender = right
  // year = right
  @Test
  public void testcheckrep4() {
    Person p = new Person("p");
  }


}
