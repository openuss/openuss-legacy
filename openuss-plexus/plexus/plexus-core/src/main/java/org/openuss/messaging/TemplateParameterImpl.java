package org.openuss.messaging;

import org.apache.commons.lang.StringUtils;

/**
 * @see org.openuss.messaging.TemplateParameter
 * 
 * @author Ingo Düppe
 */
public class TemplateParameterImpl extends TemplateParameterBase implements TemplateParameter {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -7417676112831984321L;

	@Override
	public void setValue(String value) {
		super.setValue(StringUtils.abbreviate(value, 1024));
	}
}