package org.openuss.lecture.events;

import org.openuss.lecture.Course;

/**
 * Published before the course is deleted. 
 * @author Ingo Dueppe
 */
public class CourseRemoveEvent extends AbstractCourseEvent {

	private static final long serialVersionUID = -682325221943943027L;

	public CourseRemoveEvent(Course source) {
		super(source);
	}


}
