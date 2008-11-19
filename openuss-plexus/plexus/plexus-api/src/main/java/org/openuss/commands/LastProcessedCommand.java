package org.openuss.commands;

import org.openuss.foundation.DomainObject;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public interface LastProcessedCommand extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public Command getLast();

	public void setLast(Command last);

}