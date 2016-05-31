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
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

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

	@Id
	@GenericGenerator(name = "random_id", strategy = "idgenerators.RandomIdGenerator")
	@GeneratedValue(generator = "random_id")
	@Column(name = "tweetID", length = 12, nullable = false)
	private String id;
	
	@Column(name = "message", length = 256)
	private String msg;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "userID", foreignKey = @ForeignKey(foreignKeyDefinition = "ON UPDATE RESTRICT ON DELETE CASCADE"))
	private User owner;
	
	@Column(name = "timestamp", nullable = false)
	private Timestamp timestamp;
	
	@Column(name = "countRetweets", nullable = false)
	private int countRetweets;
	
	@Column(name = "countFavorites", nullable = false)
	private int countFavorites;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "retweetFrom", foreignKey = @ForeignKey(foreignKeyDefinition = "ON UPDATE RESTRICT ON DELETE CASCADE"))
	private Tweet retweet;
	
	@Transient
	private Boolean isRetweeted;
	
	@Transient
	private Boolean isFavorited;

	/* default */ Tweet(){
		
	}
	
	public Tweet(final String msg, final User owner, final Timestamp timestamp,
				 final int countRetweets, final int countFavorites, final Tweet retweet,
				 final Boolean isRetweeted, final Boolean isFavorited) throws IllegalArgumentException {
		if (msg != null && !isValidLength(msg)) {
			throw new IllegalArgumentException(ERROR_LENGTH);
		}
		this.msg = msg;
		this.owner = owner;
		this.timestamp = new Timestamp(timestamp.getTime());
		this.countRetweets = countRetweets;
		this.countFavorites = countFavorites;
		this.retweet = retweet;
		this.isFavorited = isFavorited;
		this.isRetweeted = isRetweeted;
	}

	public Tweet(final User owner, final Timestamp timestamp, final Tweet retweet) {
		this.msg = null;
		this.owner = owner;
		this.timestamp = new Timestamp(timestamp.getTime());
		this.countRetweets = 0;
		this.countFavorites = 0;
		this.retweet = retweet;
		this.isFavorited = false;
		this.isRetweeted = false;
	}

	public Tweet(final String msg, final User owner, final Timestamp timestamp) throws IllegalArgumentException {
		if (msg != null && !isValidLength(msg)) {
			throw new IllegalArgumentException(ERROR_LENGTH);
		}
		this.msg = msg;
		this.owner = owner;
		this.timestamp = new Timestamp(timestamp.getTime());
		this.countRetweets = 0;
		this.countFavorites = 0;
		this.retweet = null;
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
		return !(getRetweet() == null);
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
		return retweet;
	}

	public Boolean getIsRetweeted() {
		return isRetweeted;
	}

	public Boolean getIsFavorited() {
		return isFavorited;
	}


}
