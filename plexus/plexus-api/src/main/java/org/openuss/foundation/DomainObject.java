package org.openuss.foundation;

/**
 * DomainObject is the central interface of all domain entity object
 * 
 * @author Ingo Dueppe
 *
 */
public interface DomainObject {
	
	/**
	 * Unique identifier of the domain object
	 * @return Long - object if object has an identifier
	 */
	public Long getId(); 
	
	/**
	 * Unique identifier of the domain object.
	 * @param id - unique domain object identifier
	 */
	public void setId(Long id);

}
