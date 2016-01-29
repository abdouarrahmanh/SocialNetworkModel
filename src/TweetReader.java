/* $Id: GradeReader.java,v 1.2 2008/01/28 17:45:08 ekstrand Exp $ */
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class for reading tweets from a data file. Adapted by
 * Jacob Thebault-Spieker <thebault@cs.umn.edu>
 * from the original GradeReader class by:
 * @author Michael Ekstrand <ekstrand@cs.umn.edu>
 *
 */
public class TweetReader {
    private Scanner scanner;
    
    private boolean valid;
    private String curID;
    private String curTweet;
    
    /**
     * Construct a new TweetReader to read tweets from a file.
     * @param filename The file to read tweets from.
     */
    public TweetReader(String filename) {
        valid = false;
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            // convert the exception to a runtime exception so the client doesn't
            // need exception handling
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Advances the TweetReader to the next tweet.
     * @return {@code true} if there is another tweet to access, false
     * otherwise.
     */
    public boolean advance() {
        valid = false;
        try {
        	do {
	            curID = scanner.next();
	            curTweet = scanner.nextLine().trim();
	            if (curTweet.startsWith("\"") && curTweet.endsWith("\"")) {
	            	curTweet = curTweet.substring(1, curTweet.length()-2);
	            }
        	} while (curID.trim().equals("FROM_USER") && curTweet.trim().equals("TEXT"));
            valid = true;
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    /**
     * Get the current tweeter's ID.
     * @return The ID of the current tweeter
     */
    public String getTweeterID() {
        assertValid();
        return curID;
    }
    /**
     * Get the current tweet's content
     * @return The content of the current tweet
     */
    public String getTweet() {
    	assertValid();
    	return curTweet;
    }
    
    /**
     * Check to see if there are more tweets left to be read
     * @return True if there are more tweets left in the data file, false otherwise
     */
    public boolean isMore() {
    	assertValid();
    	return scanner.hasNext();
    }
    
    private void assertValid() {
        if (!valid)
            throw new RuntimeException("Tweet reader not in valid state.");
    }
}
