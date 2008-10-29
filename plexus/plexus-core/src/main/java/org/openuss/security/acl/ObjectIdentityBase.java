package org.openuss.security.acl;

/**
 * 
 */
public abstract class ObjectIdentityBase
    implements ObjectIdentity, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 7224483741225237794L;

    private java.lang.Long id;

    /**
     * @see org.openuss.security.acl.ObjectIdentity#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentity#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private org.openuss.security.acl.ObjectIdentity parent;

    /**
     * 
     */
    public org.openuss.security.acl.ObjectIdentity getParent()
    {
        return this.parent;
    }

    public void setParent(org.openuss.security.acl.ObjectIdentity parent)
    {
        this.parent = parent;
    }

    private java.util.Set<org.openuss.security.acl.ObjectIdentity> child = new java.util.HashSet<org.openuss.security.acl.ObjectIdentity>();

    /**
     * 
     */
    public java.util.Set<org.openuss.security.acl.ObjectIdentity> getChild()
    {
        return this.child;
    }

    public void setChild(java.util.Set<org.openuss.security.acl.ObjectIdentity> child)
    {
        this.child = child;
    }

    private java.util.Set<org.openuss.security.acl.Permission> permissions = new java.util.HashSet<org.openuss.security.acl.Permission>();

    /**
     * 
     */
    public java.util.Set<org.openuss.security.acl.Permission> getPermissions()
    {
        return this.permissions;
    }

    public void setPermissions(java.util.Set<org.openuss.security.acl.Permission> permissions)
    {
        this.permissions = permissions;
    }

    /**
     * 
     */
    public abstract void addPermission(org.openuss.security.acl.Permission permission);

    /**
     * 
     */
    public abstract void removePermission(org.openuss.security.acl.Permission permission);

    /**
     * Returns <code>true</code> if the argument is an ObjectIdentity instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof ObjectIdentity))
        {
            return false;
        }
        final ObjectIdentity that = (ObjectIdentity)object;
        if (this.id == null || that.getId() == null || !this.id.equals(that.getId()))
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
        hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

        return hashCode;
    }

	public static class Factory extends ObjectIdentity.Factory {
		
		public ObjectIdentity createObjectIdentity() {
			return new ObjectIdentityImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}