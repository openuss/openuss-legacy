package org.openuss.lecture.events;

import org.openuss.foundation.DomainObject;

/**
 * Fired after a new institute is created.
 * 
 * @author Ingo Dueppe
 *
 */
public class InstituteCreatedEvent extends AbstractInstituteEvent {

	public InstituteCreatedEvent(DomainObject source) {
		super(source);
	}

	private static final long serialVersionUID = -7951030863189047458L;

}
