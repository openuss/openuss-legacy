package org.openuss.lecture.events;

import org.openuss.foundation.DomainObject;

/**
 * Published after an institute is updated.
 *  
 * @author Ingo Dueppe
 */
public class InstituteUpdatedEvent extends AbstractInstituteEvent {

	public InstituteUpdatedEvent(DomainObject source) {
		super(source);
	}

	private static final long serialVersionUID = 6046086104897534091L;

}
