package ar.edu.itba.paw.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "notifs") 
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifs_notifid_seq")
    @SequenceGenerator(sequenceName = "notifs_notifid_seq", name = "notifs_notifid_seq", allocationSize = 1)
    @Column(name = "notifID")
    private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "fromID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User from;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "toID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User to;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private NotificationType type;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "tweetID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Tweet tweet;
	
	@Column(name = "seen", nullable = false)
	private Boolean seen;
	
	@Column(name = "timestamp", nullable = false)
	private Date timestamp;
	
	/*default*/ public Notification() {
	}
	
	public Notification(User originUser, User destinyUser, NotificationType type, Tweet tweet, Date timestamp) {
		this.from = originUser;
		this.to = destinyUser;
		this.type = type;
		this.tweet = tweet;
		this.seen = false;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Notification [from=" + from + ", to=" + to + ", type=" + type + ", tweet=" + tweet + ", seen=" + seen + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

	public Boolean getSeen() {
		return seen;
	}

	public void setSeen(Boolean seen) {
		this.seen = seen;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
