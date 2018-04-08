/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    // 		line = 1,n
	//		num of brige = 0,1,n
	//		weight of brige = same,different
    //
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO tests
    // 		line = n
    @Test
    public void testMultipleLines() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/P1/poet/1.txt"));
        String poem = poet.poem("Rain is all around!");
        
        assertEquals("test multiple lines", poem, "Rain is falling all around!");
    }
    //		line = n
    // 		num of bridge = 0
    @Test
    public void testNoBridge() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/P1/poet/1.txt"));
        String poem = poet.poem("A bridge");
        
        assertEquals("expected no bridge words",poem, "A bridge");
    }
    //		line = 1
    // 		num of bridge = 1
    @Test
    public void testOneBridge() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/P1/poet/2.txt"));
        String poem = poet.poem("A bridge");
        
        assertEquals("expected one bridge word",poem, "A new bridge");
    }
    
    // 		num of bridge = n
    //   	weight = same 
    @Test
    public void testMultipleBridgesSameWeightCaseInsensitive() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/P1/poet/3.txt"));
        String poem = poet.poem("A bridge");
        
        assertTrue("expected multiple bridge words with same weight", 
                   poem.equals("A red bridge") || poem.equals("A bule bridge"));
    }
    
    	//		num of bridge - n
    //   	weight = same 
    @Test
    public void testMultipleBridgesDifferentWeight() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/P1/poet/4.txt"));
        String poem = poet.poem("A bridge");
        
        assertEquals("expected multiple bridge words woth different weight",
                     poem, "A long bridge");
    }
}
