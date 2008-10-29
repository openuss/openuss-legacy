package org.openuss.newsletter;

/**
 * 
 */
public class NewsletterInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -1262614404158405733L;

	public NewsletterInfo() {
		this.id = null;
		this.name = null;
		this.subscribed = false;
		this.domainIdentifier = null;
	}

	public NewsletterInfo(Long id, String name, boolean subscribed, Long domainIdentifier) {
		this.id = id;
		this.name = name;
		this.subscribed = subscribed;
		this.domainIdentifier = domainIdentifier;
	}

	/**
	 * Copies constructor from other NewsletterInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public NewsletterInfo(NewsletterInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.isSubscribed(), otherBean.getDomainIdentifier());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(NewsletterInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setSubscribed(otherBean.isSubscribed());
		this.setDomainIdentifier(otherBean.getDomainIdentifier());
	}

	private Long id;

	/**
     * 
     */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String name;

	/**
     * 
     */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private boolean subscribed;

	/**
     * 
     */
	public boolean isSubscribed() {
		return this.subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	private Long domainIdentifier;

	/**
     * 
     */
	public Long getDomainIdentifier() {
		return this.domainIdentifier;
	}

	public void setDomainIdentifier(Long domainIdentifier) {
		this.domainIdentifier = domainIdentifier;
	}

	/**
	 * Returns <code>true</code> if the argument is an NewsletterInfo instance
	 * and all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof NewsletterInfo)) {
			return false;
		}
		final NewsletterInfo that = (NewsletterInfo) object;
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