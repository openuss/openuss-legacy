package org.openuss.security.acl;

/**
 * <p>
 * @{inheritDoc}
 * </p>
 */
public abstract class PermissionBase
    implements Permission, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 7806781281561284729L;

	private org.openuss.security.acl.PermissionPK permissionPk;
	
	public org.openuss.security.acl.PermissionPK getPermissionPk() {
        return this.permissionPk;
    }

    public void setPermissionPk(org.openuss.security.acl.PermissionPK permissionPk) {
        this.permissionPk = permissionPk;
    }

    private java.lang.Integer mask;

    /**
     * @see org.openuss.security.acl.Permission#getMask()
     */
    public java.lang.Integer getMask()
    {
        return this.mask;
    }

    /**
     * @see org.openuss.security.acl.Permission#setMask(java.lang.Integer mask)
     */
    public void setMask(java.lang.Integer mask)
    {
        this.mask = mask;
    }

    /**
     * Returns <code>true</code> if the argument is an Permission instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Permission))
        {
            return false;
        }
        final Permission that = (Permission)object;
        if (this.permissionPk == null || that.getPermissionPk() == null || !this.permissionPk.equals(that.getPermissionPk()))
        {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code based on this entity's identifiers.
     */
    public int hashCode()
    {
        int hashCode = 0;
        hashCode = 29 * hashCode + (permissionPk == null ? 0 : permissionPk.hashCode());

        return hashCode;
    }

	public static class Factory extends Permission.Factory {
		
		public Permission createPermission() {
			return new PermissionImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}