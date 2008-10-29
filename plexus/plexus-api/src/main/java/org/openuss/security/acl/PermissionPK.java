package org.openuss.security.acl;

/**
 * Primary key class for Permission
 */
public class PermissionPK implements java.io.Serializable {

	private static final long serialVersionUID = 7806781281561284729L;

	private org.openuss.security.acl.ObjectIdentity aclObjectIdentity;

	public org.openuss.security.acl.ObjectIdentity getAclObjectIdentity() {
		return this.aclObjectIdentity;
	}

	public void setAclObjectIdentity(org.openuss.security.acl.ObjectIdentity aclObjectIdentity) {
		this.aclObjectIdentity = aclObjectIdentity;
	}

	private org.openuss.security.Authority recipient;

	public org.openuss.security.Authority getRecipient() {
		return this.recipient;
	}

	public void setRecipient(org.openuss.security.Authority recipient) {
		this.recipient = recipient;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof PermissionPK)) {
			return false;
		}
		final PermissionPK that = (PermissionPK) object;
		return new org.apache.commons.lang.builder.EqualsBuilder().append(this.getAclObjectIdentity(),
				that.getAclObjectIdentity()).append(this.getRecipient(), that.getRecipient()).isEquals();
	}

	public int hashCode() {
		return new org.apache.commons.lang.builder.HashCodeBuilder().append(getAclObjectIdentity()).append(
				getRecipient()).toHashCode();
	}
}