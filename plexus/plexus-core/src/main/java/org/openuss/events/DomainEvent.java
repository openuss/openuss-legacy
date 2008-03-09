package org.openuss.events;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.openuss.foundation.DomainObject;
import org.springframework.context.ApplicationEvent;

public class DomainEvent extends ApplicationEvent {

	private static final long serialVersionUID = -5071294513762603326L;

	public DomainEvent(DomainObject source) {
		super(source);
	}
	
	public DomainObject getDomainObject() {
		return (DomainObject) getSource();
	}
	
	public String getUsername() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null || context.getAuthentication() == null) {
			return "not available";
		} else {
			return context.getAuthentication().getName();
		}
	}

}
