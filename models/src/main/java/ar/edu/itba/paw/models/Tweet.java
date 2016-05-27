package ar.edu.itba.paw.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "tweets")
public class Tweet {

	private final static String HASHTAG_REGEX = "(?:\\s|\\A)[##]+([A-Za-z0-9-_]+)";
	private final static String MENTION_REGEX = "(?:\\s|\\A)[@]+([A-Za-z0-9-_]+)";
	private final static Pattern HASHTAG_PATTERN = Pattern.compile(HASHTAG_REGEX);
	private final static Pattern MENTION_PATTERN = Pattern.compile(MENTION_REGEX);

	private final static int MAX_LENGTH = 256;

	private final static String DATE_FORMAT = "h:mm a - d MMMM yyyy";

	private final static String ERROR_LENGTH = "A tweet can not have more than " + MAX_LENGTH + " or 0 characters.";

	private final static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	
	/**
	 * The @Basic annotation allows you to declare the fetching strategy for a property:
	 * @Basic(fetch = FetchType.LAZY)
	 * 
	 * 
	 * */

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tweets_tweetid_seq") //TODO what to do with randomID 
    @SequenceGenerator(sequenceName = "tweets_tweetid_seq", name = "tweets_tweetid_seq", allocationSize = 1) //idem 
    @Column(name = "tweetID", length = 12, nullable = false, unique = true)
	private String id;

	@Column(name = "message", length = 256, nullable = true)
	private String msg;
	
	@ManyToOne(fetch = FetchType.EAGER , optional = false)
	private User owner;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp", nullable = false)
	private Timestamp timestamp;
	
	@Column(name = "countRetweets", nullable = false)
	private int countRetweets;
	
	@Column(name = "countFavorites", nullable = false)
	private int countFavorites;

	@ManyToOne(fetch = FetchType.LAZY , optional = true) 
	private Tweet retweetFrom;

	@Transient
	private Boolean isRetweeted;
	
	@Transient
	private Boolean isFavorited;
	
	@Column(name = "replyTo", length = 12, nullable = true)
	private String replyTo;
	
	@Column(name = "replyFrom", length = 12, nullable = true)
	private String replyFrom;
	
	 /* package */ Tweet() {
	        // Just for Hibernate.
	 }

	public Tweet(final String msg, final String id, final User owner, final Timestamp timestamp,
				 final int countRetweets, final int countFavorites, final Tweet retweetFrom,
				 final Boolean isRetweeted, final Boolean isFavorited) throws IllegalArgumentException {
		if (msg != null && !isValidLength(msg)) {
			throw new IllegalArgumentException(ERROR_LENGTH);
		}
		this.msg = msg;
		this.id = id;
		this.owner = owner;
		this.timestamp = new Timestamp(timestamp.getTime());
		this.countRetweets = countRetweets;
		this.countFavorites = countFavorites;
		this.retweetFrom = retweetFrom;
		this.isFavorited = isFavorited;
		this.isRetweeted = isRetweeted;
	}

	public Tweet(final String id, final User owner, final Timestamp timestamp, final Tweet retweetFrom) {
		this.msg = null;
		this.id = id;
		this.owner = owner;
		this.timestamp = new Timestamp(timestamp.getTime());
		this.countRetweets = 0;
		this.countFavorites = 0;
		this.retweetFrom = retweetFrom;
		this.isFavorited = false;
		this.isRetweeted = false;
	}

	public Tweet(final String msg, final String id, final User owner, final Timestamp timestamp) throws IllegalArgumentException {
		if (msg != null && !isValidLength(msg)) {
			throw new IllegalArgumentException(ERROR_LENGTH);
		}
		this.msg = msg;
		this.id = id;
		this.owner = owner;
		this.timestamp = new Timestamp(timestamp.getTime());
		this.countRetweets = 0;
		this.countFavorites = 0;
		this.retweetFrom = null;
		this.isFavorited = false;
		this.isRetweeted = false;
	}

	public static int getMaxLength() {
		return MAX_LENGTH;
	}

	private boolean isValidLength(String msg) {
		return (msg.length() <= MAX_LENGTH && msg.length() > 0);
	}

	public Set<String> getHashtags() {
		return patternFilter(msg, HASHTAG_PATTERN, "#");
	}

	public Set<String> getMentions() {
		return patternFilter(msg, MENTION_PATTERN, "@");
	}

	/**
	 * Filters a String with a given RegEx.
	 *
	 * @param msg The string to be filtered.
	 * @param pt  The pattern.
	 * @param c   A Special character.
	 * @return
	 */
	private Set<String> patternFilter(String msg, Pattern pt, String c) {
		Set<String> ans = new HashSet<>();
		Matcher matcher = pt.matcher(msg);
		String result;

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

	/*
	 *
	 * Getters
	 *
	 * */

	@Override
	public String toString() {
		return "Tweet [" + msg + "]";
	}

	public Boolean isRetweet() {
		return !(retweetFrom == null);
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
		return sdf.format(timestamp); //TODO unsafe for multithread
	}

	public int getCountRetweets() {
		return countRetweets;
	}

	public int getCountFavorites() {
		return countFavorites;
	}

	public Tweet getRetweet() {
		return retweetFrom;
	}

	public Boolean getIsRetweeted() {
		return isRetweeted;
	}

	public Boolean getIsFavorited() {
		return isFavorited;
	}

	/*
     * 
     * Setters (just for hibernate)
     * 
     * */
	
	public void setId(String id) {
		this.id = id;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setCountRetweets(int countRetweets) {
		this.countRetweets = countRetweets;
	}

	public void setCountFavorites(int countFavorites) {
		this.countFavorites = countFavorites;
	}

	public void setRetweetFrom(Tweet retweetFrom) {
		this.retweetFrom = retweetFrom;
	}

	public void setIsRetweeted(Boolean isRetweeted) {
		this.isRetweeted = isRetweeted;
	}

	public void setIsFavorited(Boolean isFavorited) {
		this.isFavorited = isFavorited;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public void setReplyFrom(String replyFrom) {
		this.replyFrom = replyFrom;
	}
}
