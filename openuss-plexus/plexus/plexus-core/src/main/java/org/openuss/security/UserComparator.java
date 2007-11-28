package org.openuss.security;

import java.io.Serializable;
import java.util.Comparator;

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
		if (user1 != null && user1.getContact().getLastName() != null && user2 != null && user2.getContact().getLastName() != null) {
			return user1.getContact().getLastName().toLowerCase().compareTo(user2.getContact().getLastName().toLowerCase());
		} else {
			return 0;
		}
	}
	
}