package org.openuss.security.ldap;

/**
 * 
 */
public abstract class RoleAttributeKeyBase
    implements RoleAttributeKey, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 7157382970773380374L;

    private java.lang.Long id;

    /**
     * @see org.openuss.security.ldap.RoleAttributeKey#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKey#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.security.ldap.RoleAttributeKey#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKey#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.util.List<org.openuss.security.ldap.AttributeMapping> attributeMappings = new java.util.ArrayList<org.openuss.security.ldap.AttributeMapping>();

    /**
     * 
     */
    public java.util.List<org.openuss.security.ldap.AttributeMapping> getAttributeMappings()
    {
        return this.attributeMappings;
    }

    public void setAttributeMappings(java.util.List<org.openuss.security.ldap.AttributeMapping> attributeMappings)
    {
        this.attributeMappings = attributeMappings;
    }

    /**
     * Returns <code>true</code> if the argument is an RoleAttributeKey instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof RoleAttributeKey))
        {
            return false;
        }
        final RoleAttributeKey that = (RoleAttributeKey)object;
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

	public static class Factory extends RoleAttributeKey.Factory {
		
		public RoleAttributeKey createRoleAttributeKey() {
			return new RoleAttributeKeyImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}