/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2018-03-04T12:00:00Z");
    private static final Instant d4 = Instant.parse("2018-03-04T15:30:00Z");
    
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "lee", "my email is 12345@hit.com is ok ",d4);
    private static final Tweet tweet4 = new Tweet(4, "gogo", " @Goerwa it is ok ",d3);
    private static final Tweet tweet5 = new Tweet(5, "abc", " @lee it is ok ",d4);
    private static final Tweet tweet6 = new Tweet(6, "abc", " another tweet ",d2);
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // test two tweets with a designated author
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    // test two tweets with no designated author
    @Test
    public void testWrittenByMultipleTweetsnullResult() {
        List<Tweet> writtenBy = Filter.writtenBy(new ArrayList<Tweet>(), "alyssa");
        
        assertEquals("expected null list", 0, writtenBy.size());
        assertTrue("expected list to contain no tweet", writtenBy.isEmpty());
    }
    
    // test two tweets with two designated author
    @Test
    public void testWrittenByMultipleTweetsMultipleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        List<Tweet> writtenBy1 = Filter.writtenBy(Arrays.asList(tweet1, tweet3), "lee");
        List<Tweet> writtenBy2 = Filter.writtenBy(Arrays.asList(tweet4, tweet5), "abc");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertEquals("expected singleton list", 1, writtenBy1.size());
        assertTrue("expected list to contain tweet", writtenBy1.contains(tweet3));
        assertEquals("expected singleton list", 1, writtenBy2.size());
        assertTrue("expected list to contain tweet", writtenBy2.contains(tweet5));
    }
    
    // test two tweets with the same designated author
    @Test
    public void testWrittenByMultipleTweetsSameResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet5, tweet6), "abc");
        
        assertEquals("expected singleton list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet5));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet6));
    }
    
    // test two time in the timespan
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    // test no time in the timespan
    @Test
    public void testInTimespanMultipleTweetsnullResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet3, tweet4), new Timespan(testStart, testEnd));
        
        assertTrue("expected empty list", inTimespan.isEmpty());
        assertFalse("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet3, tweet4)));
        //assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    // test only one time in the timespan
    @Test
    public void testInTimespanMultipleTweetsOneResults() {
        Instant testStart = Instant.parse("2016-02-17T11:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet2));
    }
    
    // test a tweet with the designated word
    @Test
    public void testContainingoneresult() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    // test no tweet with the designated word
    @Test
    public void testContainingnullresult() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet3, tweet4), Arrays.asList("talk"));
        
        assertTrue("expected non-empty list", containing.isEmpty());
        assertFalse("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet3, tweet4)));
        //assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    // test five tweet with the designated word
    @Test
    public void testContainingmultipleresult() {
    		List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
    		List<Tweet> containing1 = Filter.containing(Arrays.asList(tweet1, tweet4, tweet5), Arrays.asList("it"));
    		
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
        assertFalse("expected non-empty list", containing1.isEmpty());
        assertTrue("expected list to contain tweets", containing1.containsAll(Arrays.asList(tweet1,tweet4, tweet5)));
        assertEquals("expected same order", 0, containing1.indexOf(tweet1));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
