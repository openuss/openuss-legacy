package org.openuss.seminarpool.util;

import java.util.Comparator;

import org.openuss.seminarpool.SeminarpoolInfo;

public class SeminarpoolInfoShortcutComperator implements Comparator<SeminarpoolInfo> {
	
		private boolean isAscending;
		
		public SeminarpoolInfoShortcutComperator(boolean isAscending){
			this.isAscending = isAscending;
		}


	
		public int compare(SeminarpoolInfo f1, SeminarpoolInfo f2) {
			if (isAscending) {
				return f1.getShortcut().compareToIgnoreCase(f2.getShortcut());
			} else {
				return f2.getShortcut().compareToIgnoreCase(f1.getShortcut());
			}
		}
}
