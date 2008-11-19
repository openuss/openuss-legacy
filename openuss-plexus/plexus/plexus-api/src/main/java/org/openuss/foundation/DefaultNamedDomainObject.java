/**
 * 
 */
package org.openuss.foundation;

/**
 * Default implementation of NamedDomainObject interface.
 * 
 * @author Sebastian Roekens
 */
public class DefaultNamedDomainObject implements NamedDomainObject {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7776152368697029697L;

	private Long id;

	private String name;

	public DefaultNamedDomainObject() {
	};

	public DefaultNamedDomainObject(Long id) {
		super();
		this.id = id;
	}

	public DefaultNamedDomainObject(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}