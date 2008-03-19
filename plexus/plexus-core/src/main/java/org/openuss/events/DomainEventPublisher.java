package org.openuss.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * Domain Event Publisher is a bridge to encapsulate spring application event publisher; 
 * 
 * @author Ingo Dueppe
 *
 */
public class DomainEventPublisher implements ApplicationEventPublisherAware{

	private ApplicationEventPublisher applicationEventPublisher;
	
	@Override
	public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishEvent(DomainEvent domainEvent) { 
		applicationEventPublisher.publishEvent(domainEvent);
	}

}
