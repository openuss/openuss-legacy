package org.openuss.security.acegi.acl;

import java.lang.reflect.InvocationTargetException;

import org.acegisecurity.acl.basic.AclObjectIdentity;
import org.acegisecurity.acl.basic.BasicAclEntry;
import org.acegisecurity.acl.basic.SimpleAclEntry;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
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
	
	private Permission permission;
	
	/**
	 * @param permission
	 */
	public AclPermissionAdapter(Permission permission) {
		this.permission = permission;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public AclObjectIdentity getAclObjectIdentity() {
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
	public AclObjectIdentity getAclObjectParentIdentity() {
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

	/**
	 * {@inheritDoc}
	 */
	public int getMask() {
		return permission.getMask();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getRecipient() {
		return permission.getRecipient().toString();
	}


	/**
	 * {@inheritDoc}
	 */
	public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		throw new UnsupportedOperationException("setRecipient must be set over the associated permission object");
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAclObjectParentIdentity(AclObjectIdentity aclObjectParentIdentity) {
		throw new UnsupportedOperationException("setRecipient must be set over the associated permission object");
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMask(int mask) {
		throw new UnsupportedOperationException("setRecipient must be set over the associated permission object");
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRecipient(Object recipient) {
		throw new UnsupportedOperationException("setRecipient must be set over the associated permission object");
	}

	/**
	 * Get the associated permission object.
	 * @return
	 */
	public Permission getPermission() {
		return permission;
	}

	/**
	 * Set the associated permission object.
	 * @param permission
	 */
	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isPermitted(int permissionToCheck) {
		//FIXME The AclEntry implementation should be inject by dependency injection
		
//		LectureAclEntry aclEntry = new LectureAclEntry();
		SimpleAclEntry aclEntry = new SimpleAclEntry();
		aclEntry.setMask(this.getMask());
		return aclEntry.isPermitted(permissionToCheck);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (permission == null)
			return super.toString()+" permission not set";
		else {
			return new ToStringBuilder(this)
				.append("recipient", permission.getRecipient())
				.append("mask", permission.getMask())
				.append("id", permission.getId())
				.toString();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-623867371, 1205873953)
			.append(permission.getRecipient())
			.append(permission.getMask())
			.append(permission.getId())
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
			.append(this.permission.getRecipient(), rhs.permission.getRecipient())
			.append(this.permission.getMask(), rhs.permission.getMask())
			.append(this.permission.getId(), rhs.permission.getId())
			.isEquals();
	}
}
