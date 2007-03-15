package org.openuss.docmanagement;

import org.openuss.lecture.Enrollment;

public interface WorkingPlace extends Folder {

	public Enrollment getEnrollment();

	public void setEnrollment(Enrollment enrollment);

}