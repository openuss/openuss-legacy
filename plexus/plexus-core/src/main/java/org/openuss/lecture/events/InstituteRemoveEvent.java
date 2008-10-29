package org.openuss.lecture.events;

import org.openuss.foundation.DomainObject;

/**
 * Published before an institute is removed.
 * 
 * @author Ingo Dueppe
 *
 */
public class InstituteRemoveEvent extends AbstractInstituteEvent {

	private static final long serialVersionUID = 6442905802063605024L;

	public InstituteRemoveEvent(DomainObject source) {
		super(source);
	}

}
