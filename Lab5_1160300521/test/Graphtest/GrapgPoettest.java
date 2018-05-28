package Graphtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import graph.GraphPoet;

public class GrapgPoettest {
  // Testing strategy
  // line = 1,n
  // num of brige = 0,1,n
  // weight of brige = same,different
  // TODO tests
  // line = n
  @Test
  public void testMultipleLines() throws IOException, ExceptionofInput, ExceptionofUnproperEdge {
    GraphPoet poet = new GraphPoet(new File("test/Graphtest/1.txt"));
    String poem = poet.poem("Rain is all around!");
    // System.out.println(poem);
    assertEquals("test multiple lines", poem, "Rain is falling all around!");
  }

  // line = n
  // num of bridge = 0
  @Test
  public void testNoBridge() throws IOException, ExceptionofInput, ExceptionofUnproperEdge {
    GraphPoet poet = new GraphPoet(new File("test/Graphtest/1.txt"));
    String poem = poet.poem("A bridge");
    // System.out.println(poem);
    assertEquals("expected no bridge words", poem, "A bridge");
  }

  // line = 1
  // num of bridge = 1
  @Test
  public void testOneBridge() throws IOException, ExceptionofInput, ExceptionofUnproperEdge {
    GraphPoet poet = new GraphPoet(new File("test/Graphtest/2.txt"));
    String poem = poet.poem("A bridge");
    System.out.println(poem);
    assertEquals("expected one bridge word", poem, "A new bridge");
  }

  // num of bridge = n
  // weight = same
  @Test
  public void testMultipleBridgesSameWeightCaseInsensitive()
      throws IOException, ExceptionofInput, ExceptionofUnproperEdge {
    GraphPoet poet = new GraphPoet(new File("test/Graphtest/3.txt"));
    String poem = poet.poem("A bridge");
    // System.out.println(poem);
    assertTrue("expected multiple bridge words with same weight",
        poem.equals("A red bridge") || poem.equals("A blue bridge"));
  }

  // num of bridge = n
  // weight = same
  @Test
  public void testMultipleBridgesDifferentWeight()
      throws IOException, ExceptionofInput, ExceptionofUnproperEdge {
    GraphPoet poet = new GraphPoet(new File("test/Graphtest/4.txt"));
    String poem = poet.poem("A bridge");
    // System.out.println(poem);
    assertEquals("expected multiple bridge words woth different weight", poem, "A long bridge");
  }

}
