package org.openuss.docmanagement;

import org.openuss.lecture.Enrollment;

public interface ExamArea {

	public abstract Enrollment getEnrollment();

	public abstract void setEnrollment(Enrollment enrollment);

}