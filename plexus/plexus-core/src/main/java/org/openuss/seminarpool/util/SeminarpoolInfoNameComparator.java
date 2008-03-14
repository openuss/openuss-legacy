package org.openuss.seminarpool.util;

import java.io.Serializable;
import java.util.Comparator;

import org.openuss.seminarpool.SeminarpoolInfo;

public class SeminarpoolInfoNameComparator implements Comparator<SeminarpoolInfo>, Serializable{
	
		private final boolean isAscending;
		
		public SeminarpoolInfoNameComparator(boolean isAscending){
			this.isAscending = isAscending;
		}

		public int compare(SeminarpoolInfo nameOne, SeminarpoolInfo nameTwo) {
			if (isAscending) {
				return nameOne.getName().compareToIgnoreCase(nameTwo.getName());
			} else {
				return nameTwo.getName().compareToIgnoreCase(nameOne.getName());
			}
		}
}
