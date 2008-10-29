package org.openuss.security.ldap;

/**
 * 
 */
public abstract class UserDnPatternBase
    implements UserDnPattern, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -1951010366778994812L;

    private java.lang.Long id;

    /**
     * @see org.openuss.security.ldap.UserDnPattern#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.security.ldap.UserDnPattern#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.security.ldap.UserDnPattern#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.security.ldap.UserDnPattern#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.util.Set<org.openuss.security.ldap.LdapServer> ldapServers = new java.util.HashSet<org.openuss.security.ldap.LdapServer>();

    /**
     * 
     */
    public java.util.Set<org.openuss.security.ldap.LdapServer> getLdapServers()
    {
        return this.ldapServers;
    }

    public void setLdapServers(java.util.Set<org.openuss.security.ldap.LdapServer> ldapServers)
    {
        this.ldapServers = ldapServers;
    }

    /**
     * Returns <code>true</code> if the argument is an UserDnPattern instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof UserDnPattern))
        {
            return false;
        }
        final UserDnPattern that = (UserDnPattern)object;
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

	public static class Factory extends UserDnPattern.Factory {
		
		public UserDnPattern createUserDnPattern() {
			return new UserDnPatternImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}