package org.openuss.seminarpool.util;

import java.io.Serializable;
import java.util.Comparator;

import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;

public class CourseSeminarpoolAllocationCourseNameComparator implements
		Comparator<CourseSeminarpoolAllocationInfo>, Serializable {

	private final boolean isAscending;
	
	public CourseSeminarpoolAllocationCourseNameComparator(boolean isAscending){
		this.isAscending = isAscending;
	}

	
	public int compare(CourseSeminarpoolAllocationInfo objectOne, CourseSeminarpoolAllocationInfo objectTwo) {
		if (isAscending) {
			return objectOne.getCourseName().compareToIgnoreCase(objectTwo.getCourseName());
		} else {
			return objectTwo.getCourseName().compareToIgnoreCase(objectOne.getCourseName());
		}
	}

}
