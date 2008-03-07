package org.openuss.seminarpool.util;

import java.util.Comparator;

import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;

public class CourseSeminarpoolAllocationCourseNameComparator implements
		Comparator<CourseSeminarpoolAllocationInfo> {

	private boolean isAscending;
	
	public CourseSeminarpoolAllocationCourseNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}

	
	public int compare(CourseSeminarpoolAllocationInfo f1, CourseSeminarpoolAllocationInfo f2) {
		if (isAscending) {
			return f1.getCourseName().compareToIgnoreCase(f2.getCourseName());
		} else {
			return f2.getCourseName().compareToIgnoreCase(f1.getCourseName());
		}
	}

}
