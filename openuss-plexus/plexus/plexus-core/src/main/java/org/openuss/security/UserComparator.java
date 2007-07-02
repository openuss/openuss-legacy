package org.openuss.security;

import java.util.Comparator;

public class UserComparator implements Comparator{

	/**
	 * compares last name of user
	 */
	public int compare(Object o1, Object o2) {		
		if (o1 instanceof User){
			if (o2 instanceof User){
				User u1 = (User) o1;
				User u2 = (User) o2;
				return u1.getLastName().toLowerCase().compareTo(u2.getLastName().toLowerCase());
			}
		}
		return 0;
	}
	
}