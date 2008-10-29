package org.openuss.security.acegi.acl;

import java.lang.reflect.InvocationTargetException;

import org.acegisecurity.acl.basic.AclObjectIdentity;
import org.acegisecurity.acl.basic.BasicAclEntry;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openuss.security.SecurityServiceException;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.security.acl.ObjectIdentityImpl;

/**
 * Adapter class between Permission Entity and Acegi BasicAclEntry class.
 * 
 * @author Ingo Dueppe
 * 
 */
public class AclPermissionAdapter implements BasicAclEntry {

	private static final long serialVersionUID = -5816801870720669885L;

	private Object recipient;
	private int mask;
	private EntityObjectIdentity aclObjectIdentity;
	private EntityObjectIdentity aclObjectParentIdentity;

	public AclPermissionAdapter(String recipient, int mask, Long objectIdentity, Long objectParentIdentity) {
		this.recipient = recipient;
		this.mask = mask;
		this.aclObjectIdentity = new EntityObjectIdentity(objectIdentity);
		if (objectParentIdentity != null) {
			this.aclObjectParentIdentity = new EntityObjectIdentity(objectParentIdentity);
		} else if (ObjectIdentityImpl.SYSTEM_OBJECT_IDENTITY != objectIdentity){ 
			this.aclObjectParentIdentity = new EntityObjectIdentity(ObjectIdentityImpl.SYSTEM_OBJECT_IDENTITY);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public AclObjectIdentity getAclObjectIdentity() {
		return aclObjectIdentity;
	}

	/**
	 * {@inheritDoc}
	 */
	public AclObjectIdentity getAclObjectParentIdentity() {
		return aclObjectParentIdentity;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getMask() {
		return mask;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getRecipient() {
		return recipient;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		try {
			this.aclObjectIdentity = new EntityObjectIdentity(aclObjectIdentity);
		} catch (IllegalAccessException e) {
			throw new SecurityServiceException(e);
		} catch (InvocationTargetException e) {
			throw new SecurityServiceException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAclObjectParentIdentity(AclObjectIdentity aclObjectParentIdentity) {
		try {
			this.aclObjectParentIdentity = new EntityObjectIdentity(aclObjectParentIdentity);
		} catch (IllegalAccessException e) {
			throw new SecurityServiceException(e);
		} catch (InvocationTargetException e) {
			throw new SecurityServiceException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMask(int mask) {
		this.mask = mask;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRecipient(Object recipient) {
		this.recipient = recipient;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isPermitted(int permissionToCheck) {
		LectureAclEntry aclEntry = new LectureAclEntry();
		aclEntry.setMask(this.getMask());
		return aclEntry.isPermitted(permissionToCheck);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("recipient", recipient).append("mask", mask).append("identity",
				aclObjectIdentity.getIdentifier()).toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-623867371, 1205873953).append(recipient).append(mask).append(
				aclObjectIdentity.getIdentifier()).toHashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AclPermissionAdapter)) {
			return false;
		}
		if (object == this) {
			return true;
		}
		AclPermissionAdapter rhs = (AclPermissionAdapter) object;
		return new EqualsBuilder().append(this.recipient, rhs.recipient).append(this.mask, rhs.mask).append(
				this.aclObjectIdentity.getIdentifier(), rhs.aclObjectIdentity.getIdentifier()).isEquals();
	}
}
