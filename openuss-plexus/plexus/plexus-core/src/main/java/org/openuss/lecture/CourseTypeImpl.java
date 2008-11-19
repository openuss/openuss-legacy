package org.openuss.lecture;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.lecture.CourseType
 * @author Ingo Dueppe
 * @author Florian Dondorf
 */
public class CourseTypeImpl extends org.openuss.lecture.CourseTypeBase implements org.openuss.lecture.CourseType {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -3094289073434197423L;

	@Override
	public void add(Course course) {
		Validate.notNull(course, "CourseType.add(Course) - course cannot be null.");
		
		if (!this.getCourses().contains(course)) {
			this.getCourses().add(course);
			course.setCourseType(this);
		}
		else {
			course.setCourseType(this);
			throw new IllegalArgumentException(
			"CourseType.add(Course) - the Course has already been in the List");
		}
	}

	@Override
	public void remove(Course course) {
		Validate.notNull(course, "CourseType.remove(course) - the course cannot be null.");
		
		if (!this.getCourses().remove(course)) {
			if (course.getCourseType().equals(this)) {
				course.setCourseType(null);
			}
			throw new IllegalArgumentException(
				"CourseType.remove(Coourse) - the Course has not been in the List");
		}
		course.setCourseType(null);
	}

}