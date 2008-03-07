package org.openuss.seminarpool.util;

import java.util.Comparator;

import org.openuss.seminarpool.SeminarPlaceAllocationInfo;

public class SeminarplaceAllocationInfoCourseNameComparator implements
				Comparator<SeminarPlaceAllocationInfo> {


	private boolean isAscending;
	
	public SeminarplaceAllocationInfoCourseNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}

	
	public int compare(SeminarPlaceAllocationInfo f1, SeminarPlaceAllocationInfo f2) {
		if (isAscending) {
			return f1.getCourseName().compareToIgnoreCase(f2.getCourseName());
		} else {
			return f2.getCourseName().compareToIgnoreCase(f1.getCourseName());
		}
	}

}
