/**
 * 
 */
package org.openuss.foundation;

/**
 * DomainObject for testing   
 * @author Ingo Dueppe
 */
public class DefaultDomainObject implements DomainObject {
	private Long id;

	public DefaultDomainObject() {};
	
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