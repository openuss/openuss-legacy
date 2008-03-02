package org.openuss.seminarpool.util;

import java.util.Comparator;

import org.openuss.seminarpool.SeminarpoolInfo;

public class SeminarpoolInfoNameComparator implements Comparator<SeminarpoolInfo>{
	
		private boolean isAscending;
		
		public SeminarpoolInfoNameComparator(boolean isAscending){
			this.isAscending = isAscending;
		}

		public int compare(SeminarpoolInfo f1, SeminarpoolInfo f2) {
			if (isAscending) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
}
