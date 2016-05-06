package ar.edu.itba.paw.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tweet {
	
	private final static String HASHTAG_REGEX = "(?:\\s|\\A)[##]+([A-Za-z0-9-_]+)";
	private final static String MENTION_REGEX = "(?:\\s|\\A)[@]+([A-Za-z0-9-_]+)";
	private final static Pattern HASHTAG_PATTERN = Pattern.compile(HASHTAG_REGEX);
	private final static Pattern MENTION_PATTERN = Pattern.compile(MENTION_REGEX);
	
	private final static int MAX_LENGTH=256;
	
	private final static String DATE_FORMAT = "h:mm a - d MMMM yyyy";
	
	private final static String ERROR_LENGTH = "A tweet can not have more than "+ MAX_LENGTH +" or 0 characters.";
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	
	private final String msg;
	private final String id;
	private final User owner;
	private final Timestamp timestamp;
	private final int countRetweets;
	private	final int countFavorites;
	private final String retweetID;
	private final Boolean isRetweeted;
	private final Boolean isFavorited;
	
	public Tweet(final String msg, final String id, final User owner, final Timestamp timestamp,
			final int countRetweets, final int countFavorites, final String retweetID, 
			final Boolean isRetweeted, final Boolean isFavorited) throws IllegalArgumentException {
		if (msg!=null && !isValidLength(msg)) {
			throw new IllegalArgumentException(ERROR_LENGTH);
		}
		this.msg = msg;
		this.id = id;
		this.owner = owner;
		this.timestamp = timestamp;
		this.countRetweets = countRetweets;
		this.countFavorites = countFavorites;
		this.retweetID = retweetID;
		this.isFavorited = isFavorited;
		this.isRetweeted = isRetweeted;
	}	
	
	public Tweet(final String id, final User owner, final Timestamp timestamp, final String retweetID) throws IllegalArgumentException {
		this.msg = null;
		this.id = id;
		this.owner = owner;
		this.timestamp = timestamp;
		this.countRetweets = 0;
		this.countFavorites = 0;
		this.retweetID = retweetID;
		this.isFavorited = false;
		this.isRetweeted = false;
	}
	
	//ASI NO LLORAN LOS TESTS POR AHORA.
	public Tweet(final String msg, final String id, final User owner, final Timestamp timestamp) throws IllegalArgumentException {
		if (!isValidLength(msg)) {
			throw new IllegalArgumentException(ERROR_LENGTH);
		}
		this.msg = msg;
		this.id = id;
		this.owner = owner;
		this.timestamp = timestamp;
		this.countRetweets = 0;
		this.countFavorites = 0;
		this.retweetID = null;
		this.isFavorited = false;
		this.isRetweeted = false;
	}     
	
	
	
	private boolean isValidLength(String msg) {
		return (msg.length() <= MAX_LENGTH && msg.length() > 0); 
	}
	
	public Set<String> getHashtags(){
		return patternFilter(msg, HASHTAG_PATTERN, "#");
	}
	
	public Set<String> getMentions(){
		return patternFilter(msg, MENTION_PATTERN ,"@");
	}
	
	/**
	 * Filters a String with a given RegEx.
	 * 
	 * @param msg The string to be filtered. 
	 * @param pattern The RegEx.
	 * @param c A Special character.
	 * @return
	 */
	private Set<String> patternFilter(String msg, Pattern pt, String c) { 
		 Set<String> ans = new HashSet<String>();
	     Matcher matcher = pt.matcher(msg);
	     String result = "";
	 
	     while (matcher.find()) {
	         result = matcher.group();
	         result = result.replace(" ", "");
	         String elem = result.replace(c, "");
	         ans.add(elem);
	     }
		
		return ans;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweet other = (Tweet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tweet [" + msg + "]";
	}
	
	/*
	 * 
	 * Getters
	 * 
	 * */
	
	public Boolean isRetweet(){
		return !(retweetID==null);		
	}

	public static int getMaxLength() {
		return MAX_LENGTH;
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
	
	public String getTimestamp(){
		return sdf.format(timestamp);
	}

	public int getCountRetweets() {
		return countRetweets;
	}

	public int getCountFavorites() {
		return countFavorites;
	}
	
	public String getRetweet() {
		return retweetID;
	}

	public Boolean getIsRetweeted() {
		return isRetweeted;
	}

	public Boolean getIsFavorited() {
		return isFavorited;
	}
	
	
}
