package ar.edu.itba.paw.webapp.viewmodels;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

/**
 * Created by Tomi on 4/22/16.
 */
public class TweetViewModel {

    private final String msg;
    private final String id;
    private final User owner;
    private final String timestamp;

    public TweetViewModel(Tweet tweet){
        this.msg = parseToHTMLString(tweet.getMsg());
        this.id = tweet.getId();
        this.owner = tweet.getOwner();
        this.timestamp = tweet.getTimestamp();
    }

    static public TweetViewModel transformTweet(Tweet tweet) {
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

    public User getOwner() {
        return owner;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // WE NEED TO MOVE ALL THIS SOMEPLACE ELSE

    private String parseURL(String s) {
        String [] parts = s.split("\\s+");

        for(int i=0; i<parts.length; i++) try {
            URL url = new URL(parts[i]);
            parts[i] = "<a href=\"" + url + "\">"+ url + "</a> ";
        } catch (MalformedURLException e) {
            // Ignore
        }

        return Arrays.stream(parts).collect(Collectors.joining(" "));
    }

    private String parseHashtags(String s) {
        String patternStr = "(?:\\s|\\A)[##]+([A-Za-z0-9-_]+)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(s);
        String result = "";

        while (matcher.find()) {
            result = matcher.group();
            result = result.replace(" ", "");
            String search = result.replace("#", "");
            String searchHTML="<a href='/search?searchText=%23" + search + "'>" + result + "</a>";
            s = s.replace(result,searchHTML);
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
