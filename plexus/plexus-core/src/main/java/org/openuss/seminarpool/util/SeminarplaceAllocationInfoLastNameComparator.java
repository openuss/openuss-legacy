package org.openuss.seminarpool.util;

import java.util.Comparator;

import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.SeminarPlaceAllocationInfo;

public class SeminarplaceAllocationInfoLastNameComparator implements Comparator<SeminarPlaceAllocationInfo> {

	private boolean isAscending;
	
	public SeminarplaceAllocationInfoLastNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}

	
	public int compare(SeminarPlaceAllocationInfo f1, SeminarPlaceAllocationInfo f2) {
		if (isAscending) {
			return f1.getLastName().compareToIgnoreCase(f2.getLastName());
		} else {
			return f2.getLastName().compareToIgnoreCase(f1.getLastName());
		}
	}

}
