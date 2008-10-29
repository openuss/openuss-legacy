package org.openuss.messaging;

/**
 * 
 * @author Ingo Dueppe
 *
 */
public abstract class TemplateParameterBase implements TemplateParameter, java.io.Serializable {

	private static final long serialVersionUID = -2237382078561181961L;

	private java.lang.Long id;

	/**
	 * @see org.openuss.messaging.TemplateParameter#getId()
	 */
	public java.lang.Long getId() {
		return this.id;
	}

	/**
	 * @see org.openuss.messaging.TemplateParameter#setId(java.lang.Long id)
	 */
	public void setId(java.lang.Long id) {
		this.id = id;
	}

	private java.lang.String value;

	/**
	 * @see org.openuss.messaging.TemplateParameter#getValue()
	 */
	public java.lang.String getValue() {
		return this.value;
	}

	/**
	 * @see org.openuss.messaging.TemplateParameter#setValue(java.lang.String
	 *      value)
	 */
	public void setValue(java.lang.String value) {
		this.value = value;
	}

	private java.lang.String name;

	/**
	 * @see org.openuss.messaging.TemplateParameter#getName()
	 */
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * @see org.openuss.messaging.TemplateParameter#setName(java.lang.String
	 *      name)
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	private org.openuss.messaging.TemplateMessage template;

	/**
     * 
     */
	public org.openuss.messaging.TemplateMessage getTemplate() {
		return this.template;
	}

	public void setTemplate(org.openuss.messaging.TemplateMessage template) {
		this.template = template;
	}

	/**
	 * Returns <code>true</code> if the argument is an TemplateParameter
	 * instance and all identifiers for this entity equal the identifiers of the
	 * argument entity. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof TemplateParameter)) {
			return false;
		}
		final TemplateParameter that = (TemplateParameter) object;
		if (this.id == null || that.getId() == null || !this.id.equals(that.getId())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

		return hashCode;
	}

}