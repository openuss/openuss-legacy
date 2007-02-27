package org.openuss.docmanagement;

import org.openuss.lecture.Enrollment;

public class WorkingPlaceImpl extends Folder{
	
	public Enrollment enrollment;

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
	
}