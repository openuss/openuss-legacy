package org.openuss.docmanagement;

import org.openuss.lecture.Enrollment;

public class ExamAreaImpl extends Folder{
	
	public Enrollment enrollment;

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
}