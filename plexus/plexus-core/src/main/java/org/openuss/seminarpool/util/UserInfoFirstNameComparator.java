package org.openuss.seminarpool.util;

import java.util.Comparator;

import org.openuss.security.UserInfo;

public class UserInfoFirstNameComparator implements Comparator<UserInfo> {

	private boolean isAscending;
	
	public UserInfoFirstNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}



	public int compare(UserInfo f1, UserInfo f2) {
		if (isAscending) {
			return f1.getFirstName().compareToIgnoreCase(f2.getFirstName());
		} else {
			return f2.getFirstName().compareToIgnoreCase(f1.getFirstName());
		}
	}

}
