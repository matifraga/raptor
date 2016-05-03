package ar.edu.itba.paw.webapp.viewmodels;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ar.edu.itba.paw.models.Tweet;

/**
 * Created by Tomi on 4/22/16.
 */
public class TweetViewModel {
	
	private static final int PIC_SIZE = 150;
	
	private static final String tweetPattern = "(?:\\s|\\A)[##]+([A-Za-z0-9-_]+)";
    private static final Pattern pattern = Pattern.compile(tweetPattern);
	
    private final String msg;
    private final String id;
    private final UserViewModel owner;
    private final String timestamp;
	private final int countRetweets;
	private	final int countFavorites;

    public TweetViewModel(Tweet tweet){
        this.msg = parseToHTMLString(tweet.getMsg());
        this.id = tweet.getId();
        this.owner = new UserViewModel(tweet.getOwner(), PIC_SIZE);
        this.timestamp = tweet.getTimestamp();
        this.countRetweets = tweet.getCountRetweets();
        this.countFavorites = tweet.getCountFavorites();
    }

	public static TweetViewModel transformTweet(Tweet tweet) {
        return new TweetViewModel(tweet);
    }

    public static List<TweetViewModel> transform(List<Tweet> tweetList) {
        return tweetList.stream()
                .map(TweetViewModel::transformTweet)
                .collect(Collectors.toList());
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
        Matcher matcher = pattern.matcher(s);
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
        String patternStr = "(?:\\s|\\A)[@]+([A-Za-z0-9-_]+)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(s);
        String result = "";

        while (matcher.find()) {
            result = matcher.group();
            result = result.replace(" ", "");
            String rawName = result.replace("@", "");
            String userHTML="<a href='/user/" + rawName + "'>" + result + "</a>";
            s = s.replace(result,userHTML);
        }

        return s;
    }

    private String parseToHTMLString(String message) {
        return (parseUsers(parseHashtags(parseURL(message))));
    }
}
