/**
 * 
 */
package org.openuss.foundation;

/**
 * Default implementation of NamedDomainObject interface.
 * 
 * @author Ingo Dueppe
 */
public class DefaultDomainObject implements DomainObject {

	private static final long serialVersionUID = -3558659260549118307L;

	private Long id;

	private String name;

	public DefaultDomainObject() {
	};

	public DefaultDomainObject(Long id) {
		super();
		this.id = id;
	}

	public DefaultDomainObject(Long id, String name) {
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