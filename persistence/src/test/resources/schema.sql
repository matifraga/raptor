CREATE TABLE IF NOT EXISTS users (
	username 	varchar(100) 	NOT NULL,
	password 	varchar(100) 	NOT NULL,
	email 		varchar(100)	NOT NULL,
	firstName 	varchar(100) 	NOT NULL,
	lastName 	varchar(100) 	NOT NULL,
	userID 		char(12) 		NOT NULL,
	verified	boolean			NOT NULL,

	PRIMARY KEY (userID)
);

CREATE TABLE IF NOT EXISTS tweets (
	tweetID 		char(12) 		NOT NULL,
	message 		varchar(256),
	userID 			char(12) 		NOT NULL,
	timestamp 		TIMESTAMP 		NOT NULL,
	retweetFrom		char(12),
	replyTo			char(12),
	replyFrom		char(12),
	countRetweets	integer			NOT NULL,
	countFavorites	integer			NOT NULL,	

	PRIMARY KEY (tweetID),
	FOREIGN KEY (userID) 		REFERENCES users  ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY (retweetFrom) 	REFERENCES tweets ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY (replyTo) 		REFERENCES tweets ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY (replyFrom) 	REFERENCES tweets ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE IF NOT EXISTS hashtags (
	hashtag 	varchar(256) 	NOT NULL, 
	tweetID 	char(12) 		NOT NULL, 

	PRIMARY KEY (hashtag , tweetID),
	FOREIGN KEY (tweetID) REFERENCES tweets ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE IF NOT EXISTS mentions (
	userID 		char(12) 	NOT NULL, 
	tweetID 	char(12) 	NOT NULL, 

	PRIMARY KEY (userID , tweetID),
	FOREIGN KEY (userID) REFERENCES users ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY (tweetID) REFERENCES tweets ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE IF NOT EXISTS followers (
	followerID 	char(12) 	NOT NULL, 
	followingID char(12) 	NOT NULL, 

	PRIMARY KEY (followerID , followingID),
	FOREIGN KEY (followerID) REFERENCES users  ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY (followingID) REFERENCES users ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE IF NOT EXISTS favorites (
	favoriteID	char(12)	NOT NULL,
	tweetID		char(12)	NOT NULL,

	PRIMARY KEY (favoriteID , tweetID),
	FOREIGN KEY (favoriteID) REFERENCES users ON DELETE CASCADE ON UPDATE RESTRICT,
	FOREIGN KEY (tweetID) REFERENCES tweets ON DELETE CASCADE ON UPDATE RESTRICT
);