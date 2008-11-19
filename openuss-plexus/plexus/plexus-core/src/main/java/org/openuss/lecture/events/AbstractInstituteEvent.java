package org.openuss.lecture.events;

import org.openuss.events.DomainEvent;
import org.openuss.foundation.DomainObject;
import org.openuss.lecture.Institute;

public abstract class AbstractInstituteEvent extends DomainEvent {

	private static final long serialVersionUID = -5607607815738572034L;

	public AbstractInstituteEvent(DomainObject source) {
		super(source);
	}
	
	public Institute getInstitute() {
		return (Institute) getSource();
	}

}
