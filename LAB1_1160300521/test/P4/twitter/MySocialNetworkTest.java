package P4.twitter;

import static org.junit.Assert.*;


import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class MySocialNetworkTest {

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-18T12:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-18T13:00:00Z");
    private static final Instant d5 = Instant.parse("2016-02-18T14:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much #hit?", d1);
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", " sup  #abc?", d3);
    private static final Tweet tweet4 = new Tweet(4, "alyssa", " not much, #abc super tired", d4);
    private static final Tweet tweet5 = new Tweet(5, "mike", " message me at #abc mike@gmail.com!", d5);
    private static final Tweet tweet6 = new Tweet(6, "lee", " I messaged myself #abc", d5);
    
    
    //test a followgraph with one hashtag
    @Test
    public void testGuessFollowsGraphnoHashtag() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet3));
        Map<String, Set<String>> followsGraphLower = new HashMap<>();
        Set<String> values = new HashSet<String>();
        for (String key : followsGraph.keySet()) {
            Set<String> value = ExtractTest.toLowerCase(followsGraph.get(key));
            followsGraphLower.put(key.toLowerCase(), value);
        }
        
        assertTrue("expected graph containing keys", followsGraphLower.keySet().containsAll(Arrays.asList("alyssa", "bbitdiddle")));
        assertEquals("expected key alyssa contaning values", values, followsGraphLower.get("alyssa"));
        assertEquals("expected key bbitdiddle contaning values", values, followsGraphLower.get("bbitdiddle"));
    }
    
  //test a followgraph with two hashtag
    @Test
    public void testGuessFollowsGraphMultipleHashtag() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4, tweet5, tweet6));
        Map<String, Set<String>> followsGraphLower = new HashMap<>();
        
        for (String key : followsGraph.keySet()) {
            Set<String> value = ExtractTest.toLowerCase(followsGraph.get(key));
            followsGraphLower.put(key.toLowerCase(), value);
        }
        
        Set<String> values1 = new HashSet<String>();
        values1.addAll(Arrays.asList("alyssa", "mike"));
        Set<String> values2 = new HashSet<String>();
        values2.addAll(Arrays.asList("mike","lee"));
        Set<String> values3 = new HashSet<String>();
        values3.addAll(Arrays.asList("alyssa", "lee"));
        
        assertTrue("expected graph containing keys", followsGraphLower.keySet().containsAll(Arrays.asList("alyssa", "lee", "mike")));
        assertEquals("expected key 'alyssa' contaning values", values1, followsGraphLower.get("lee"));
        assertEquals("expected key 'alyssa' contaning values", values2, followsGraphLower.get("alyssa"));
        assertEquals("expected key 'mike' contaning values", values3, followsGraphLower.get("mike"));
    }

}
