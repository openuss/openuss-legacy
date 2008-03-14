package org.openuss.seminarpool.util;

import java.util.Comparator;

import org.openuss.seminarpool.SeminarPlaceAllocationInfo;

public class SeminarplaceAllocationInfoCourseNameComparator implements
				Comparator<SeminarPlaceAllocationInfo> {


	private final boolean isAscending;
	
	public SeminarplaceAllocationInfoCourseNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}

	
	public int compare(SeminarPlaceAllocationInfo objectOne, SeminarPlaceAllocationInfo objectTwo) {
		if (isAscending) {
			return objectOne.getCourseName().compareToIgnoreCase(objectTwo.getCourseName());
		} else {
			return objectTwo.getCourseName().compareToIgnoreCase(objectOne.getCourseName());
		}
	}

}
