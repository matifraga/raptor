package ar.edu.itba.paw.webapp.viewmodels;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.postgresql.jdbc2.ArrayAssistantRegistry;

import ar.edu.itba.paw.models.Tweet;
import ar.edu.itba.paw.models.User;

public class UserViewModel {

	private static final String GRAVATAR_END_POINT = "https://www.gravatar.com/avatar/";
	private static final String DEFAULT_PICTURE = "d=http%3A%2F%2Fi.imgur.com%2FgOGINGL.png";
	
	private final String id;
	private final String username;
	private final String email;
	private final String firstName;
	private final String lastName;
	
	private final String profilePicture;

	
	public UserViewModel(User u, final int size) { 
		this.id = u.getId();
		this.username = u.getUsername();
		this.email = u.getEmail();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.profilePicture = generateProfilePictureUrl(u, size);
	}
	
	static public UserViewModel transformUser(User user, final int size) {
        return new UserViewModel(user, size);
    }

    public static List<UserViewModel> transform(List<User> userList,final int size) {
        List<UserViewModel> userMList = new ArrayList<>(userList.size());
    	for (User user : userList) {
			userMList.add(transformUser(user, size));
		}
        return userMList;
    }
	
	private String generateProfilePictureUrl(User u, final int size) {
		return GRAVATAR_END_POINT + md5(u.getEmail()) + "?" + DEFAULT_PICTURE + "&" + "s=" + size;
	}
	
	private String md5(String s) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(StandardCharsets.UTF_8.encode(s));
			String md5hash = String.format("%032x", new BigInteger(1, md5.digest()));
			return md5hash;	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}
	
	
}
