package org.openuss.docmanagement;

import org.openuss.lecture.Enrollment;

public class WorkingPlaceImpl extends FolderImpl implements WorkingPlace {
	
	public Enrollment enrollment;

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.WorkingPlace#getEnrollment()
	 */
	public Enrollment getEnrollment() {
		return enrollment;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.WorkingPlace#setEnrollment(org.openuss.lecture.Enrollment)
	 */
	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
	
}