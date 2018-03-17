/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

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
    private static final Tweet tweet6 = new Tweet(6, "abc", " @lee @goerwa are all ok ",d4);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    public static Set<String> toLowerCase(Set<String> strings) {
        Set<String> result = new HashSet<>();
        
        for (String string: strings) {
            result.add(string.toLowerCase());
        }
        
        return result;
    }
    
    //test one tweet and the timespan have same start and end
    @Test
    public void testGetTimespanOneTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    //test two tweet and the timespan have different start and end 
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    //test a null tweet
    @Test
    public void testGetTimespannullTweets() {
        Timespan timespan = Extract.getTimespan(new ArrayList<Tweet>());
        
        assertEquals("Expect the start of time equal to the end", timespan.getEnd(), timespan.getStart());
    }
    
    //test a tweet with no mentioned
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    //test a tweet with one mentioned
    @Test
    public void testGetMentionedUsersOne() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));
        assertTrue(mentionedUsers.contains("goerwa"));
    }
    
    //test a tweet with two mentioned
    @Test
    public void testGetMentionedUsersTwo() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        assertTrue(mentionedUsers.contains("goerwa"));
        assertTrue(mentionedUsers.contains("lee"));
    }
    
    //test two tweet with two mentioned
    @Test
    public void testGetMentionedUsersTwoTweet() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4,tweet5));
        assertTrue(mentionedUsers.contains("goerwa"));
        assertTrue(mentionedUsers.contains("lee"));
    }
    
    //test a tweet with a email address
    @Test
    public void testGetMentionedUsersEmailTweet() {         
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        assertTrue(mentionedUsers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
