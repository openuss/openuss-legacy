package org.openuss.docmanagement;

import org.openuss.lecture.Enrollment;

public interface ExamArea extends Folder{

	public abstract Enrollment getEnrollment();

	public abstract void setEnrollment(Enrollment enrollment);

}