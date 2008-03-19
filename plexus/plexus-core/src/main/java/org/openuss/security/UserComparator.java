package org.openuss.security;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;

/**
 * User Comparator 
 * @author Ingo Dueppe
 */
public class UserComparator implements Comparator<UserInfo>, Serializable{

	private static final long serialVersionUID = 5540642549247630562L;

	/**
	 * compares last name of user
	 */
	public int compare(UserInfo user1, UserInfo user2) {
		if (user1 != null && user1.getLastName() != null && user2 != null && user2.getLastName() != null) {
			return user1.getLastName().toLowerCase(Locale.ENGLISH).compareTo(user2.getLastName().toLowerCase(Locale.ENGLISH));
		} else {
			return 0;
		}
	}
	
}