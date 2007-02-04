// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import org.openuss.lecture.Subject;

/**
 * @see org.openuss.desktop.Desktop
 */
public class DesktopImpl extends org.openuss.desktop.DesktopBase implements org.openuss.desktop.Desktop {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 7305422481011599756L;

	/**
	 * @see org.openuss.desktop.Desktop#linkFaculty(org.openuss.lecture.Faculty)
	 */
	public void linkFaculty(org.openuss.lecture.Faculty faculty) {
		getFaculties().add(faculty);
	}

	/**
	 * @see org.openuss.desktop.Desktop#unlinkFaculty(org.openuss.lecture.Faculty)
	 */
	public void unlinkFaculty(org.openuss.lecture.Faculty faculty) {
		getFaculties().remove(faculty);
	}

	/**
	 * @see org.openuss.desktop.Desktop#linkEnrollment(org.openuss.lecture.Enrollment)
	 */
	public void linkEnrollment(org.openuss.lecture.Enrollment enrollment) {
		getEnrollments().add(enrollment);
	}

	/**
	 * @see org.openuss.desktop.Desktop#unlinkEnrollment(org.openuss.lecture.Enrollment)
	 */
	public void unlinkEnrollment(org.openuss.lecture.Enrollment enrollment) {
		getEnrollments().remove(enrollment);
	}

	@Override
	public void linkSubject(Subject subject) {
		getSubjects().add(subject);
		
	}

	@Override
	public void unlinkSubject(Subject subject) {
		getSubjects().remove(subject);
	}

}