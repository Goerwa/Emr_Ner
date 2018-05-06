package Wordtest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import graph.GraphPoet;

public class wordgraphtest {

	// Testing strategy
    // 	line = 1,n
	//	num of brige = 0,1,n
	//	weight of brige = same,different
    // TODO tests
    // 		line = n
    @Test
    public void testMultipleLines() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/Wordtest/1.txt"));
        String poem = poet.poem("Rain is all around!");
        //System.out.println(poem);
        assertEquals("test multiple lines", poem, "Rain is falling all around!");
    }
    
    //		line = n
    // 		num of bridge = 0
    @Test
    public void testNoBridge() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/Wordtest/1.txt"));
        String poem = poet.poem("A bridge");
        //System.out.println(poem);
        assertEquals("expected no bridge words",poem, "A bridge");
    }
    //		line = 1
    // 		num of bridge = 1
    @Test
    public void testOneBridge() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/Wordtest/2.txt"));
        String poem = poet.poem("A bridge");
        //System.out.println(poem);
        assertEquals("expected one bridge word",poem, "A new bridge");
    }
    
    // 		num of bridge = n
    //   	weight = same 
    @Test
    public void testMultipleBridgesSameWeightCaseInsensitive() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/Wordtest/3.txt"));
        String poem = poet.poem("A bridge");
        //System.out.println(poem);
        assertTrue("expected multiple bridge words with same weight", 
                   poem.equals("A red bridge") || poem.equals("A blue bridge"));
    }
    
    	//		num of bridge = n
    //   	weight = same 
    @Test
    public void testMultipleBridgesDifferentWeight() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/Wordtest/4.txt"));
        String poem = poet.poem("A bridge");
        //System.out.println(poem);
        assertEquals("expected multiple bridge words woth different weight",
                     poem, "A long bridge");
    }
	
}
