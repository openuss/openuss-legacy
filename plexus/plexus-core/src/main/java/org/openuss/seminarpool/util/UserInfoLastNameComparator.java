package org.openuss.seminarpool.util;

import java.util.Comparator;

import org.openuss.security.UserInfo;

public class UserInfoLastNameComparator implements Comparator<UserInfo>{

	private boolean isAscending;
	
	public UserInfoLastNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}

	public int compare(UserInfo f1, UserInfo f2) {
		if (isAscending) {
			return f1.getLastName().compareToIgnoreCase(f2.getLastName());
		} else {
			return f2.getLastName().compareToIgnoreCase(f1.getLastName());
		}
	}

}