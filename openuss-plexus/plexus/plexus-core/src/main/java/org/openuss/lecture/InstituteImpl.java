// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @see org.openuss.lecture.Institute
 * @author Ron Haus
 */
public class InstituteImpl extends org.openuss.lecture.InstituteBase implements org.openuss.lecture.Institute {

	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 4524152372902812906L;

	/**
	 * @see org.openuss.lecture.Institute#add(org.openuss.lecture.CourseType)
	 */
	public void add(org.openuss.lecture.CourseType courseType) {
		// @todo implement public void add(org.openuss.lecture.CourseType courseType)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.lecture.Institute.add(org.openuss.lecture.CourseType courseType) Not implemented!");
	}

	/**
	 * @see org.openuss.lecture.Institute#remove(org.openuss.lecture.CourseType)
	 */
	public void remove(org.openuss.lecture.CourseType courseType) {
		// @todo implement public void remove(org.openuss.lecture.CourseType courseType)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.lecture.Institute.remove(org.openuss.lecture.CourseType courseType) Not implemented!");
	}

	/**
	 * @see org.openuss.lecture.Institute#getActiveCourses()
	 */
	@SuppressWarnings( { "unchecked" })
	public java.util.List getActiveCourses() {
		List courses = new ArrayList();
		Iterator iterCourseTypes = this.getCourseTypes().iterator();
		while (iterCourseTypes.hasNext()) {
			Iterator iterCourses = ((CourseType) iterCourseTypes.next()).getCourses().iterator();
			while (iterCourses.hasNext()) {
				Course course = (Course) iterCourses.next();
				if (course.isActive()) {
					courses.add(course);
				}
			}
		}
		return null;

	}

	/**
	 * @see org.openuss.lecture.Institute#getAllCourses()
	 */
	@SuppressWarnings( { "unchecked" })
	public List getAllCourses() {
		List courses = new ArrayList();
		Iterator iterCourseTypes = this.getCourseTypes().iterator();
		while (iterCourseTypes.hasNext()) {
			Iterator iterCourses = ((CourseType) iterCourseTypes.next()).getCourses().iterator();
			while (iterCourses.hasNext()) {
				courses.add((Course) iterCourses.next());
			}
		}
		return null;
	}

}