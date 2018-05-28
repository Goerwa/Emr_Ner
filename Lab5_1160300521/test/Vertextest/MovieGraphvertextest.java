package Vertextest;

import static org.junit.Assert.*;
import org.junit.Test;
import vertex.Actor;
import vertex.Computer;
import vertex.Director;
import vertex.Movie;

public class MovieGraphvertextest {

  Actor a1 = new Actor("a1");
  Actor a2 = new Actor("a2");
  Actor a3 = new Actor("a1");
  Director d1 = new Director("d1");
  Movie m1 = new Movie("v1");

  // Testing strategy
  //
  // equasl():
  // label of vertex = same, not same
  // type of vertex = same, not same
  @Test
  public void testequals1() {
    assertTrue("expected true", a1.equals(a3));
  }

  @Test
  public void testequals2() {
    assertFalse("expected false", d1.equals(a3));
  }

  @Test
  public void testequals3() {
    assertFalse("expected false", d1.equals(m1));
  }

  @Test
  public void testequals4() {
    assertFalse("expected false", a1.equals(m1));
  }

  // Testing strategy
  //
  // checkRep():
  // year = right,wrong
  // gender = right,wrong
  // nation = null ,not null
  //
  // year = wrong
  @Test(expected = AssertionError.class)
  public void testcheckrep1() {
    Movie m = new Movie("m", 1899, "china", 8.88);
  }

  // year = right
  //
  @Test
  public void testcheckrep2() {
    Movie m = new Movie("m", 1999, "china", 8.88);
    Movie m1 = new Movie("m1");
  }

  // nation = null
  @Test(expected = AssertionError.class)
  public void testcheckrep4() {
    Actor m = new Actor(null, 92, "M");
  }

  // year = wrong
  // gender = right
  @Test(expected = AssertionError.class)
  public void testcheckrep5() {
    Actor m = new Actor("aa", -92, "M");
  }

  // year = right
  // gender = wrong
  @Test(expected = AssertionError.class)
  public void testcheckrep6() {
    Director m = new Director("aa", 92, "K");
  }

}
