package ar.edu.itba.paw.webapp.viewmodels;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.services.TweetService;

public class TweetViewModel {
	
	private static final int PIC_SIZE = 150;
	
	private static final String hashtagPattern = "(?:\\s|\\A)[##]+([A-Za-z0-9-_]+)";
    private static final Pattern Hpattern = Pattern.compile(hashtagPattern);
    private static final String mentionPattern = "(?:\\s|\\A)[@]+([A-Za-z0-9-_]+)";
    private static final Pattern Mpattern = Pattern.compile(mentionPattern);
	
    private final String msg;
    private final String id;
    private final UserViewModel owner;
    private final String timestamp;
	private final int countRetweets;
	private	final int countFavorites;
	private final String retweetedBy;

    public TweetViewModel(Tweet tweet, TweetService ts){
    	this.id = tweet.getId();
    	
    	if(!tweet.isRetweet()){
	        this.msg = parseToHTMLString(tweet.getMsg());
	        this.owner = new UserViewModel(tweet.getOwner(), PIC_SIZE);
	        this.timestamp = tweet.getTimestamp();
	        this.countRetweets = tweet.getCountRetweets();
	        this.countFavorites = tweet.getCountFavorites();
	        this.retweetedBy = null;
    	} else {
    		Tweet originalTweet = ts.getTweet(tweet.getRetweet());
    		this.msg = parseToHTMLString(originalTweet.getMsg());
    		this.retweetedBy = new StringBuilder(tweet.getOwner().getFirstName()).append(" ").append(tweet.getOwner().getLastName()).toString();
    		this.owner = new UserViewModel(originalTweet.getOwner(), PIC_SIZE);
    		this.timestamp = originalTweet.getTimestamp();
    		this.countRetweets = originalTweet.getCountRetweets();
    		this.countFavorites = originalTweet.getCountFavorites();
    	}
    }

	public static TweetViewModel transformTweet(Tweet tweet, final TweetService ts) {
        return new TweetViewModel(tweet, ts);
    }

    public static List<TweetViewModel> transform(List<Tweet> tweetList, final TweetService ts) {
    	List<TweetViewModel> tweetMList = new ArrayList<>(tweetList.size());
    	for (Tweet tweet : tweetList) {
			tweetMList.add(transformTweet(tweet, ts));
		}
        return tweetMList;
    }

    public String getMsg() {
        return msg;
    }

    public String getId() {
        return id;
    }

    public UserViewModel getOwner() {
        return owner;
    }

    public String getTimestamp() {
        return timestamp;
    }
    
    public int getCountRetweets() {
		return countRetweets;
	}

	public int getCountFavorites() {
		return countFavorites;
	}

	public String getRetweetedBy() {
		return retweetedBy;
	}

	private String parseURL(String s) {
        String [] parts = s.split("\\s+");

        for(int i=0; i<parts.length; i++) try {
            URL url = new URL(parts[i]);
            parts[i] = new StringBuilder("<a href=\"").append(url).append("\">").append(url).append("</a> ").toString();
        } catch (MalformedURLException e) {
            // Ignore
        }

        return Arrays.stream(parts).collect(Collectors.joining(" "));
    }

    private String parseHashtags(String s) {
        Matcher matcher = Hpattern.matcher(s);
        String result = "";

        while (matcher.find()) {
            result = matcher.group();
            result = result.replace(" ", "");
            String search = result.replace("#", "");
            StringBuilder searchHTML = new StringBuilder("<a href='/search?searchText=%23").append(search).
            		append("'>").append(result).append("</a>");
            s = s.replace(result,searchHTML.toString());
        }

        return s;
    }

    private String parseUsers(String s) {
        Matcher matcher = Mpattern.matcher(s);
        String result = "";

        while (matcher.find()) {
            result = matcher.group();
            result = result.replace(" ", "");
            String rawName = result.replace("@", "");
            StringBuilder userHTML=new StringBuilder("<a href='/user/").append(rawName).append("'>").append(result).append("</a>");
            s = s.replace(result,userHTML.toString());
        }

        return s;
    }

    private String parseToHTMLString(String message) {
        return (parseUsers(parseHashtags(parseURL(message))));
    }
}
