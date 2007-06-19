// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import org.openuss.lecture.CourseType;

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
	 * @see org.openuss.desktop.Desktop#linkCourse(org.openuss.lecture.Course)
	 */
	public void linkCourse(org.openuss.lecture.Course course) {
		getCourses().add(course);
	}

	/**
	 * @see org.openuss.desktop.Desktop#unlinkCourse(org.openuss.lecture.Course)
	 */
	public void unlinkCourse(org.openuss.lecture.Course course) {
		getCourses().remove(course);
	}

	@Override
	public void linkCourseType(CourseType courseType) {
		getCourseTypes().add(courseType);
		
	}

	@Override
	public void unlinkCourseType(CourseType courseType) {
		getCourseTypes().remove(courseType);
	}

}