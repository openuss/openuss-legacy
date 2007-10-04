// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.lecture.Institute
 * @author Ron Haus
 * @author Florian Dondorf
 */
public class InstituteImpl extends InstituteBase implements Institute {

	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 4524152372902812906L;

	/**
	 * @see org.openuss.lecture.Institute#add(org.openuss.lecture.CourseType)
	 */
	public void add(org.openuss.lecture.CourseType courseType) {
		Validate.notNull(courseType, "Institute.add(CourseType) - courseType cannot be null");

		if (!this.getCourseTypes().contains(courseType)) {
			this.getCourseTypes().add(courseType);
			courseType.setInstitute(this);
		} else {
			courseType.setInstitute(this);
			throw new IllegalArgumentException(
					"Institute.add(CourseType) - the CourseType has already been in the List");
		}
	}

	/**
	 * @see org.openuss.lecture.Institute#remove(org.openuss.lecture.CourseType)
	 */
	public void remove(org.openuss.lecture.CourseType courseType) {
		Validate.notNull(courseType, "Institute.remove(CourseType) - courseType cannot be null");

		if (!this.getCourseTypes().remove(courseType)) {
			if (courseType.getInstitute().equals(this)) {
				courseType.setInstitute(null);
			}
			throw new IllegalArgumentException(
					"Institute.remove(CourseType) - the CourseType has not been in the List");
		}
		courseType.setInstitute(null);
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
		return courses;

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
		return courses;
	}

	@Override
	public boolean isEnabled() {
		if (getDepartment() == null) {
			return false;
		} 
		return super.isEnabled();
		
	}
	
	

}