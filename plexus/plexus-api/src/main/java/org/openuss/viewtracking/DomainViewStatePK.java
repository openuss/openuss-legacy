package org.openuss.viewtracking;

/**
 * Primary key class for DomainViewState
 */
public class DomainViewStatePK implements java.io.Serializable {

	private static final long serialVersionUID = 5902500691971537682L;

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

	private Long userIdentifier;

	/**
     * 
     */
	public Long getUserIdentifier() {
		return this.userIdentifier;
	}

	public void setUserIdentifier(Long userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof DomainViewStatePK)) {
			return false;
		}
		final DomainViewStatePK that = (DomainViewStatePK) object;
		return new org.apache.commons.lang.builder.EqualsBuilder().append(this.getDomainIdentifier(),
				that.getDomainIdentifier()).append(this.getUserIdentifier(), that.getUserIdentifier()).isEquals();
	}

	public int hashCode() {
		return new org.apache.commons.lang.builder.HashCodeBuilder().append(getDomainIdentifier()).append(
				getUserIdentifier()).toHashCode();
	}
}