/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
	
	
	
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-18T12:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-18T13:00:00Z");
    private static final Instant d5 = Instant.parse("2016-02-18T14:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", "@alyssa sup?", d3);
    private static final Tweet tweet4 = new Tweet(4, "alyssa", "@bbitdiddle not much, super tired", d4);
    private static final Tweet tweet5 = new Tweet(5, "mike", "@Bbitdiddle message me at mike@gmail.com!", d5);
    private static final Tweet tweet6 = new Tweet(6, "mike", "@Mike I messaged myself", d5);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //test a empty graph
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    //test  a followgraph with no mention
    @Test
    public void testGuessFollowsGraphNoMentioned() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        
        Set<String> values = new HashSet<>();
        
        assertEquals("expected key 'alyssa' contaning no values", values, followsGraph.get("alyssa"));
    }
    
    //test a followgraph with mentions
    @Test
    public void testGuessFollowsGraphMentioned() {
        List<Tweet> tweets = Arrays.asList(tweet1, tweet3, tweet4, tweet5);
        SocialNetwork.guessFollowsGraph(tweets);
        
        assertEquals("expected unmodified list", Arrays.asList(tweet1, tweet3, tweet4, tweet5), tweets);
    }
    
    //test an empty followgraph
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    //Test a followgraph with one relationship 
    @Test
    public void testInfluencersOne() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        Set<String> following = new HashSet<>(Arrays.asList("bbitdiddle"));
        followsGraph.put("alyssa", following);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        for (int i = 0; i < influencers.size(); i++) {
            influencers.set(i, influencers.get(i).toLowerCase());
        }
        
        assertEquals("expected list of size 2", 2, influencers.size());
        assertEquals("expected same order", 0, influencers.indexOf("bbitdiddle"));
        assertEquals("expected same order", 1, influencers.indexOf("alyssa"));
    }
    
    //test a followgraph with two relationships 
    @Test
    public void testInfluencersN() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        
        Set<String> Following1 = new HashSet<>(Arrays.asList("alyssa"));
        Set<String> Following2 = new HashSet<>(Arrays.asList("bbitdiddle"));
        
        followsGraph.put("alyssa", Following2);
        followsGraph.put("bbitdiddle", Following1);
        followsGraph.put("mike", Following2);
        
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        for (int i = 0; i < influencers.size(); i++) {
            influencers.set(i, influencers.get(i).toLowerCase());
        }
        
        assertEquals("expected list of size 3", 3, influencers.size());
        assertEquals("expected same order", 0, influencers.indexOf("bbitdiddle"));
        assertEquals("expected same order", 1, influencers.indexOf("alyssa"));
        assertEquals("expected same order", 2, influencers.indexOf("mike"));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
