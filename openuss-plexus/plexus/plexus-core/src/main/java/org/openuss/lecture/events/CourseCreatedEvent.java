package org.openuss.lecture.events;

import org.openuss.lecture.Course;

/**
 * Published after the course entity is created.
 * @author Ingo Dueppe
 *
 */
public class CourseCreatedEvent extends AbstractCourseEvent {

	private static final long serialVersionUID = 9207329086569273817L;

	public CourseCreatedEvent(Course source) {
		super(source);
	}

}
