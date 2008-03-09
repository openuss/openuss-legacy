package org.openuss.lecture.events;

import org.openuss.lecture.Course;

/**
 * Course update event is published after a course entity is updated.
 * 
 * @author Ingo Dueppe
 *
 */
public class CourseUpdateEvent extends AbstractCourseEvent {

	private static final long serialVersionUID = -8899309615200004666L;

	public CourseUpdateEvent(Course source) {
		super(source);
	}

}
