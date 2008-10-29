package org.openuss.security.ldap;

/**
 * 
 */
public abstract class AuthenticationDomainBase
    implements AuthenticationDomain, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 6843332651786423591L;

    private java.lang.Long id;

    /**
     * @see org.openuss.security.ldap.AuthenticationDomain#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomain#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.security.ldap.AuthenticationDomain#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomain#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.lang.String description;

    /**
     * @see org.openuss.security.ldap.AuthenticationDomain#getDescription()
     */
    public java.lang.String getDescription()
    {
        return this.description;
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomain#setDescription(java.lang.String description)
     */
    public void setDescription(java.lang.String description)
    {
        this.description = description;
    }

    private java.lang.String changePasswordUrl;

    /**
     * @see org.openuss.security.ldap.AuthenticationDomain#getChangePasswordUrl()
     */
    public java.lang.String getChangePasswordUrl()
    {
        return this.changePasswordUrl;
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomain#setChangePasswordUrl(java.lang.String changePasswordUrl)
     */
    public void setChangePasswordUrl(java.lang.String changePasswordUrl)
    {
        this.changePasswordUrl = changePasswordUrl;
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

    private org.openuss.security.ldap.AttributeMapping attributeMapping;

    /**
     * 
     */
    public org.openuss.security.ldap.AttributeMapping getAttributeMapping()
    {
        return this.attributeMapping;
    }

    public void setAttributeMapping(org.openuss.security.ldap.AttributeMapping attributeMapping)
    {
        this.attributeMapping = attributeMapping;
    }

    /**
     * Returns <code>true</code> if the argument is an AuthenticationDomain instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof AuthenticationDomain))
        {
            return false;
        }
        final AuthenticationDomain that = (AuthenticationDomain)object;
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

}