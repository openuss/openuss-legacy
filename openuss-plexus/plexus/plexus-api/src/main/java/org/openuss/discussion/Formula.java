package org.openuss.discussion;

/**
 * @author Ingo Dueppe
 */
public interface Formula extends org.openuss.foundation.DomainObject {

	public Long getId();

	public void setId(Long id);

	public String getFormula();

	public void setFormula(String formula);

}