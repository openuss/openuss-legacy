package org.openuss.lecture.events;

import org.openuss.events.DomainEvent;
import org.openuss.lecture.Course;

public abstract class AbstractCourseEvent extends DomainEvent {
	
	private static final long serialVersionUID = -7764633404649031615L;

	public AbstractCourseEvent(Course source) {
		super(source);
	}
	
	public Course getCourse() {
		return (Course) getDomainObject();
	}

}
