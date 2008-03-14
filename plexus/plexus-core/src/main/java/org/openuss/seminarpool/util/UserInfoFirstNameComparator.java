package org.openuss.seminarpool.util;

import java.util.Comparator;

import org.openuss.security.UserInfo;

public class UserInfoFirstNameComparator implements Comparator<UserInfo> {

	private final boolean isAscending;
	
	public UserInfoFirstNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}

	public int compare(UserInfo nameOne, UserInfo nameTwo) {
		if (isAscending) {
			return nameOne.getFirstName().compareToIgnoreCase(nameTwo.getFirstName());
		} else {
			return nameTwo.getFirstName().compareToIgnoreCase(nameOne.getFirstName());
		}
	}

}
