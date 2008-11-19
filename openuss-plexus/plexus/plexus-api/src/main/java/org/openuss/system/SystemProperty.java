package org.openuss.system;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 */
public interface SystemProperty extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public String getName();

	public void setName(String name);

	public String getValue();

	public void setValue(String value);

}