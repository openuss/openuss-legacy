package org.openuss.seminarpool.util;

import java.io.Serializable;
import java.util.Comparator;

import org.openuss.security.UserInfo;

public class UserInfoLastNameComparator implements Comparator<UserInfo>, Serializable{

	private final boolean isAscending;
	
	public UserInfoLastNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}

	public int compare(UserInfo nameOne, UserInfo nameTwo) {
		if (isAscending) {
			return nameOne.getLastName().compareToIgnoreCase(nameTwo.getLastName());
		} else {
			return nameTwo.getLastName().compareToIgnoreCase(nameOne.getLastName());
		}
	}

}