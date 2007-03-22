package org.openuss.docmanagement;

import java.sql.Timestamp;

import org.openuss.lecture.Enrollment;

public interface ExamArea extends Folder{

	public Timestamp getDeadline();
	
	public void setDeadline(Timestamp deadline);
	
}