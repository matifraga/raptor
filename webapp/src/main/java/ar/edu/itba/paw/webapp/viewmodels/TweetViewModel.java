package ar.edu.itba.paw.webapp.viewmodels;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ar.edu.itba.paw.models.Tweet;

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
    private Boolean isRetweeted; // is retweeted by the logged user
    private Boolean isFavorited; // is favorited by the logged user


    public TweetViewModel(Tweet tweet) {
        this.id = tweet.getId();
        this.msg = parseToHTMLString(tweet.getMsg());
        this.owner = new UserViewModel(tweet.getOwner(), PIC_SIZE);
        this.timestamp = tweet.getTimestamp();
        this.countRetweets = tweet.getCountRetweets();
        this.countFavorites = tweet.getCountFavorites();
        this.retweetedBy = null;
        this.isFavorited = tweet.getIsFavorited();
        this.isRetweeted = tweet.getIsRetweeted();
    }

    public TweetViewModel(Tweet tweet, Tweet retweeted) {
        this.id = retweeted.getId();
        this.msg = parseToHTMLString(retweeted.getMsg());
        this.retweetedBy = new StringBuilder(tweet.getOwner().getFirstName()).append(" ").append(tweet.getOwner().getLastName()).toString();
        this.owner = new UserViewModel(retweeted.getOwner(), PIC_SIZE);
        this.timestamp = retweeted.getTimestamp();
        this.countRetweets = retweeted.getCountRetweets();
        this.countFavorites = retweeted.getCountFavorites();
        this.isFavorited = retweeted.getIsFavorited();
        this.isRetweeted = retweeted.getIsRetweeted();
    }

    public static TweetViewModel transformTweet(Tweet tweet) {
        return new TweetViewModel(tweet);
    }

    public static TweetViewModel transformTweet(Tweet tweet, Tweet retweeted) {
        return new TweetViewModel(tweet, retweeted);
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

    public Boolean getFavorited() { return isFavorited; }

    public Boolean getRetweeted() { return isRetweeted; }

    private String parseURL(String s) {
        String [] parts = s.split("\\s+");

        for(int i=0; i<parts.length; i++) try {
            URL url = new URL(parts[i]);
            parts[i] = new StringBuilder("<a target=\"_blank\" href=\"").append(url).append("\">").append(url).append("</a> ").toString();
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
            StringBuilder searchHTML = new StringBuilder("<a href='search?searchText=%23").append(search).
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
            StringBuilder userHTML=new StringBuilder("<a href='user/").append(rawName).append("'>").append(result).append("</a>");
            s = s.replace(result,userHTML.toString());
        }

        return s;
    }

    private String parseToHTMLString(String message) {
        return (parseUsers(parseHashtags(parseURL(message))));
    }
}
