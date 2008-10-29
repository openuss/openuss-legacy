package org.openuss.messaging;

/**
 * @author Ingo Dueppe
 */
public interface TemplateParameter extends org.openuss.foundation.DomainObject {

	public Long getId();

	public void setId(Long id);

	public String getValue();

	public void setValue(String value);

	public String getName();

	public void setName(String name);

	public org.openuss.messaging.TemplateMessage getTemplate();

	public void setTemplate(org.openuss.messaging.TemplateMessage template);

}