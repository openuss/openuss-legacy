package org.openuss.docmanagement;

import org.openuss.lecture.Enrollment;

public interface ExamArea extends Folder{

	public Enrollment getEnrollment();

	public void setEnrollment(Enrollment enrollment);

}