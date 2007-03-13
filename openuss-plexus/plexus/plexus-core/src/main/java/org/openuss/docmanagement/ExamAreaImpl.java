package org.openuss.docmanagement;

import org.openuss.lecture.Enrollment;

public class ExamAreaImpl extends FolderImpl implements ExamArea{
	
	public Enrollment enrollment;

	public ExamAreaImpl(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.ExamArea#getEnrollment()
	 */
	public Enrollment getEnrollment() {
		return enrollment;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.ExamArea#setEnrollment(org.openuss.lecture.Enrollment)
	 */
	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
}