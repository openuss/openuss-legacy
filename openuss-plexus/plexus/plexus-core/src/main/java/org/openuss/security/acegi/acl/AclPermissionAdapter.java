package org.openuss.security.acegi.acl;

import java.lang.reflect.InvocationTargetException;

import org.acegisecurity.acl.basic.AclObjectIdentity;
import org.acegisecurity.acl.basic.BasicAclEntry;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.security.acl.ObjectIdentity;
import org.openuss.security.acl.Permission;

/**
 * Adapter class between Permission Entity and Acegi BasicAclEntry class. 
 * @author Ingo Dueppe
 *
 */
public class AclPermissionAdapter implements BasicAclEntry {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AclPermissionAdapter.class);

	private static final long serialVersionUID = -5816801870720669885L;
	
	private Object recipient;
	
	private int mask;
	
	private long identifier;
	
	private AclObjectIdentity aclObjectIdentity;
	
	private AclObjectIdentity aclObjectParentIdentity;
	
	/**
	 * @param permission
	 */
	public AclPermissionAdapter(Permission permission) {
		Validate.notNull(permission,"Permission must not be null");
		
		this.recipient = permission.getRecipient().toString();
		this.mask = permission.getMask();
		this.identifier = permission.getId();
		this.aclObjectIdentity = fetchAclObjectIdentity(permission);
		this.aclObjectParentIdentity = fetchAclObjectParentIdentity(permission);
	}
	
	private AclObjectIdentity fetchAclObjectParentIdentity(Permission permission) {
		final ObjectIdentity objectIdentity = permission.getAclObjectIdentity().getParent();
		try {
			return (objectIdentity == null) ? null : new EntityObjectIdentity(objectIdentity);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		}
		return null;
	}

	private AclObjectIdentity fetchAclObjectIdentity(Permission permission) {
		try {
			return new EntityObjectIdentity(permission.getAclObjectIdentity());
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		}
		return null;
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
		this.aclObjectIdentity = aclObjectIdentity;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAclObjectParentIdentity(AclObjectIdentity aclObjectParentIdentity) {
		this.aclObjectParentIdentity = aclObjectParentIdentity;
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
		return new ToStringBuilder(this)
			.append("recipient", recipient)
			.append("mask", mask)
			.append("id", identifier)
			.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-623867371, 1205873953)
			.append(recipient)
			.append(mask)
			.append(identifier)
			.toHashCode();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object) {
		if(!(object instanceof AclPermissionAdapter)) {
			return false;
		} 
		if (object == this) {
			return true;
		}
		AclPermissionAdapter rhs = (AclPermissionAdapter) object;
		return new EqualsBuilder()
			.append(this.recipient, rhs.recipient)
			.append(this.mask, rhs.mask)
			.append(this.identifier, rhs.identifier)
			.isEquals();
	}
}
