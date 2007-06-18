/**
 * 
 */
package org.openuss.foundation;

/**
 * Default DomainObject
 * 
 * @author Ingo Dueppe
 */
public class DefaultDomainObject implements DomainObject {
	
	private static final long serialVersionUID = -3558659260549118307L;
	
	private Long id;

	public DefaultDomainObject() {
	};

	public DefaultDomainObject(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}