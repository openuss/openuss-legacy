// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.messaging.TemplateMessage
 * @author Ingo Dueppe
 */
public class TemplateMessageImpl extends TemplateMessageBase implements TemplateMessage {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -1469121877101237995L;

	/**
	 * @see org.openuss.messaging.TemplateMessage#addParameter(java.lang.String,
	 *      java.lang.String)
	 */
	public void addParameter(String name, String value) {
		Validate.notEmpty(name, "Parameter name must not be null");
		Validate.notEmpty(value, "Parameter value must not be null");
		TemplateParameter parameter = TemplateParameter.Factory.newInstance();
		parameter.setName(name);
		parameter.setValue(value);
		addParameter(parameter);
	}

	/**
	 * @see org.openuss.messaging.TemplateMessage#addParameter(org.openuss.messaging.TemplateParameter)
	 */
	public void addParameter(TemplateParameter parameter) {
		Validate.notNull(parameter, "Parameter must not be null");
		parameter.setTemplate(this);
		getParameters().add(parameter);
	}

	/**
	 * @see org.openuss.messaging.TemplateMessage#removeParameter(org.openuss.messaging.TemplateParameter)
	 */
	public void removeParameter(TemplateParameter parameter) {
		if (parameter != null) {
			getParameters().remove(parameter);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void addParameters(Map parameters) {
		Validate.notNull(parameters, "Parameter parameters must not be null");
		Validate.allElementsOfType(parameters.keySet(), String.class, "Parameter parameters must contain Map<String,String>");
		for(Map.Entry<String, String> entry : (Set<Map.Entry<String,String>>)parameters.entrySet()) {
			addParameter(entry.getKey(),entry.getValue());
		}
	}

}