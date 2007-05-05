// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.messaging.TemplateMessage
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
		TemplateParameter parameter = TemplateParameter.Factory.newInstance();
		parameter.setModelName(name);
		parameter.setModelValue(value);
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

}