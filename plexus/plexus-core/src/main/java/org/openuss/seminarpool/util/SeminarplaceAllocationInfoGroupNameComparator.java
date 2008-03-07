package org.openuss.seminarpool.util;

import java.util.Comparator;

import org.openuss.seminarpool.SeminarPlaceAllocationInfo;

public class SeminarplaceAllocationInfoGroupNameComparator implements
		Comparator<SeminarPlaceAllocationInfo> {


	private boolean isAscending;
	
	public SeminarplaceAllocationInfoGroupNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}

	
	public int compare(SeminarPlaceAllocationInfo f1, SeminarPlaceAllocationInfo f2) {
		if (isAscending) {
			return f1.getGroupName().compareToIgnoreCase(f2.getGroupName());
		} else {
			return f2.getGroupName().compareToIgnoreCase(f1.getGroupName());
		}
	}

}
