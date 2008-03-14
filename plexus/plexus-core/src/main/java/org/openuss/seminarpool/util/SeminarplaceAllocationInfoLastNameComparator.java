package org.openuss.seminarpool.util;

import java.io.Serializable;
import java.util.Comparator;

import org.openuss.seminarpool.SeminarPlaceAllocationInfo;

public class SeminarplaceAllocationInfoLastNameComparator implements Comparator<SeminarPlaceAllocationInfo>, Serializable {

	private final boolean isAscending;
	
	public SeminarplaceAllocationInfoLastNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}

	
	public int compare(SeminarPlaceAllocationInfo nameOne, SeminarPlaceAllocationInfo nameTwo) {
		if (isAscending) {
			return nameOne.getLastName().compareToIgnoreCase(nameTwo.getLastName());
		} else {
			return nameTwo.getLastName().compareToIgnoreCase(nameOne.getLastName());
		}
	}

}
