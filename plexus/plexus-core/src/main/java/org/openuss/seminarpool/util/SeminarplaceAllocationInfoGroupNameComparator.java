package org.openuss.seminarpool.util;

import java.io.Serializable;
import java.util.Comparator;

import org.openuss.seminarpool.SeminarPlaceAllocationInfo;

public class SeminarplaceAllocationInfoGroupNameComparator implements
		Comparator<SeminarPlaceAllocationInfo>, Serializable {


	private final boolean isAscending;
	
	public SeminarplaceAllocationInfoGroupNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}

	
	public int compare(SeminarPlaceAllocationInfo objectOne, SeminarPlaceAllocationInfo objectTwo) {
		if (isAscending) {
			return objectOne.getGroupName().compareToIgnoreCase(objectTwo.getGroupName());
		} else {
			return objectTwo.getGroupName().compareToIgnoreCase(objectOne.getGroupName());
		}
	}

}
