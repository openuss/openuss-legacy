package org.openuss.seminarpool.util;

import java.util.Comparator;

import org.openuss.seminarpool.SeminarpoolInfo;

public class SeminarpoolInfoShortcutComperator implements Comparator<SeminarpoolInfo> {
	
		private final boolean isAscending;
		
		public SeminarpoolInfoShortcutComperator(boolean isAscending){
			this.isAscending = isAscending;
		}


	
		public int compare(SeminarpoolInfo nameOne, SeminarpoolInfo nameTwo) {
			if (isAscending) {
				return nameOne.getShortcut().compareToIgnoreCase(nameTwo.getShortcut());
			} else {
				return nameTwo.getShortcut().compareToIgnoreCase(nameOne.getShortcut());
			}
		}
}
