/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        //throw new RuntimeException("not implemented");
    		Timespan n;
    		if (tweets.isEmpty()) {
            n = new Timespan(Instant.now(), Instant.now());
    			return n;
    		}
    		else {
    				List<Tweet> sortedTweets = new ArrayList<>(tweets);            
                sortedTweets.sort(Comparator.comparing(Tweet::getTimestamp));
                Instant start = sortedTweets.get(0).getTimestamp();
                Instant end = sortedTweets.get(sortedTweets.size() - 1).getTimestamp();
                n = new Timespan(start, end);
                return n;
    		}
    		
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        //throw new RuntimeException("not implemented");
    		Set<String> usernamesMentioned = new HashSet<>();
        Pattern p = Pattern.compile("@([A-Za-z0-9_-]+)");
        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            String[] str = text.split("\\s");      
            for (String name : str) {
            		Matcher m = p.matcher(name);
                if (m.matches()) {
                		System.out.println(name);
                    name = name.substring(1).toLowerCase();
                    usernamesMentioned.add(name);
                }
            }             
        }
        return usernamesMentioned;   
    }

}
