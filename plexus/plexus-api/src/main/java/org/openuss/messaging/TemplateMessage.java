package org.openuss.messaging;

import java.util.Map;
import java.util.Set;

import org.openuss.foundation.DomainObject;

/**
 * 
 * @author Ingo Dueppe
 *
 */
public interface TemplateMessage extends Message, DomainObject {

	public String getTemplate();

	public void setTemplate(String template);

	public Set<org.openuss.messaging.TemplateParameter> getParameters();

	public void setParameters(Set<org.openuss.messaging.TemplateParameter> parameters);

	public abstract void addParameter(String name, String value);

	public abstract void addParameter(org.openuss.messaging.TemplateParameter parameter);

	public abstract void removeParameter(org.openuss.messaging.TemplateParameter parameter);

	/**
	 * Add a Map<String,String> parameter map as parameter entities to the
	 * template message.
	 * 
	 * @Param parameters - Map<String,String>
	 */
	public void addParameters(Map parameters);

	public abstract Map getParameterMap();

}