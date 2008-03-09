package org.openuss.lecture.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class CourseCreatedEventListener implements ApplicationListener {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof CourseCreatedEvent) {
			
		}
	}

}
