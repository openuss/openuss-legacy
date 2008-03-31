// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import org.openuss.lecture.CourseType;
import org.openuss.seminarpool.Seminarpool;

/**
 * @see org.openuss.desktop.Desktop
 */
public class DesktopImpl extends org.openuss.desktop.DesktopBase implements org.openuss.desktop.Desktop {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 7305422481011599756L;

	/**
	 * @see org.openuss.desktop.Desktop#linkInstitute(org.openuss.lecture.Institute)
	 */
	public void linkInstitute(org.openuss.lecture.Institute institute) {
		getInstitutes().add(institute);
	}
	
	/**
	 * @see org.openuss.desktop.Desktop#linkUniversity(org.openuss.lecture.University)
	 */
	public void linkUniversity(org.openuss.lecture.University university) {
		getUniversities().add(university);
	}

	/**
	 * @see org.openuss.desktop.Desktop#unlinkInstitute(org.openuss.lecture.Institute)
	 */
	public void unlinkInstitute(org.openuss.lecture.Institute institute) {
		getInstitutes().remove(institute);
	}
	
	/**
	 * @see org.openuss.desktop.Desktop#unlinkUniversity(org.openuss.lecture.University)
	 */
	public void unlinkUniversity(org.openuss.lecture.University university) {
		getUniversities().remove(university);
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

	@Override
	public void linkSeminarpool(Seminarpool seminarpool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlinkSeminarpool(Seminarpool seminarpool) {
		// TODO Auto-generated method stub
		
	}

}