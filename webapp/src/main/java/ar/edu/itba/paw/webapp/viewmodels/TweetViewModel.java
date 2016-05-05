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
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.FavoriteService;
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
    private Boolean isRetweeted; // is retweeted by the logged user
    private Boolean isFavorited; // is favorited by the logged user


    /*public TweetViewModel(Tweet tweet, TweetService ts) {

    	if(!tweet.isRetweet()){
            this.id = tweet.getId();
	        this.msg = parseToHTMLString(tweet.getMsg());
	        this.owner = new UserViewModel(tweet.getOwner(), PIC_SIZE);
	        this.timestamp = tweet.getTimestamp();
	        this.countRetweets = tweet.getCountRetweets();
	        this.countFavorites = tweet.getCountFavorites();
	        this.retweetedBy = null;
    	} else {
    		Tweet originalTweet = ts.getTweet(tweet.getRetweet());
            this.id = originalTweet.getId();
    		this.msg = parseToHTMLString(originalTweet.getMsg());
    		this.retweetedBy = new StringBuilder(tweet.getOwner().getFirstName()).append(" ").append(tweet.getOwner().getLastName()).toString();
    		this.owner = new UserViewModel(originalTweet.getOwner(), PIC_SIZE);
    		this.timestamp = originalTweet.getTimestamp();
    		this.countRetweets = originalTweet.getCountRetweets();
    		this.countFavorites = originalTweet.getCountFavorites();
    	}
    }*/

    public TweetViewModel(Tweet tweet) {
        this.id = tweet.getId();
        this.msg = parseToHTMLString(tweet.getMsg());
        this.owner = new UserViewModel(tweet.getOwner(), PIC_SIZE);
        this.timestamp = tweet.getTimestamp();
        this.countRetweets = tweet.getCountRetweets();
        this.countFavorites = tweet.getCountFavorites();
        this.retweetedBy = null;
    }

    public TweetViewModel(Tweet tweet, Tweet retweeted) {
        this.id = retweeted.getId();
        this.msg = parseToHTMLString(retweeted.getMsg());
        this.retweetedBy = new StringBuilder(tweet.getOwner().getFirstName()).append(" ").append(tweet.getOwner().getLastName()).toString();
        this.owner = new UserViewModel(retweeted.getOwner(), PIC_SIZE);
        this.timestamp = retweeted.getTimestamp();
        this.countRetweets = retweeted.getCountRetweets();
        this.countFavorites = retweeted.getCountFavorites();
    }

	/*public static TweetViewModel transformTweet(Tweet tweet, final TweetService ts) {
        return new TweetViewModel(tweet, ts);
    }*/

    public static TweetViewModel transformTweet(Tweet tweet) {
        return new TweetViewModel(tweet);
    }

    public static TweetViewModel transformTweet(Tweet tweet, Tweet retweeted) {
        return new TweetViewModel(tweet, retweeted);
    }

    /*public static List<TweetViewModel> transform(List<Tweet> tweetList, final TweetService ts) {
    	List<TweetViewModel> tweetMList = new ArrayList<>(tweetList.size());
    	for (Tweet tweet : tweetList) {
			tweetMList.add(transformTweet(tweet, ts));
		}
        return tweetMList;
    }*/

    public static List<TweetViewModel> transform(List<Tweet> tweetList, final TweetService ts,
                                                 final FavoriteService fs, final User loggedUser) {

        List<TweetViewModel> tweetMList = new ArrayList<>(tweetList.size());
        for (Tweet tweet : tweetList) {

            TweetViewModel tweetView;

            if (tweet.isRetweet()) {
                Tweet retweet = ts.getTweet(tweet.getRetweet());
                tweetView = transformTweet(tweet, retweet);
                if (loggedUser != null) {
                    tweetView.isFavorited = fs.isFavorited(retweet.getId(), loggedUser); // Comentar estos dos
                    tweetView.isRetweeted = ts.isRetweeted(retweet.getId(), loggedUser); //
                }
            } else {
                tweetView = transformTweet(tweet);
                if (loggedUser != null) {
                    tweetView.isFavorited = fs.isFavorited(tweet.getId(), loggedUser); // Y estos dos
                    tweetView.isRetweeted = ts.isRetweeted(tweet.getId(), loggedUser); //
                }
            }

            //tweetView.isFavorited = Boolean.FALSE; // Descomentar estos dos
            //tweetView.isRetweeted = Boolean.FALSE;

            tweetMList.add(tweetView);
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

    public Boolean getFavorited() { return isFavorited; }

    public Boolean getRetweeted() { return isRetweeted; }

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
