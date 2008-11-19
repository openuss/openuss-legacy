package org.openuss.security.ldap;

/**
 * 
 */
public abstract class LdapServerBase
    implements LdapServer, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -403333462882641416L;

    private java.lang.Long id;

    /**
     * @see org.openuss.security.ldap.LdapServer#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String providerUrl;

    /**
     * @see org.openuss.security.ldap.LdapServer#getProviderUrl()
     */
    public java.lang.String getProviderUrl()
    {
        return this.providerUrl;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setProviderUrl(java.lang.String providerUrl)
     */
    public void setProviderUrl(java.lang.String providerUrl)
    {
        this.providerUrl = providerUrl;
    }

    private java.lang.Integer port = java.lang.Integer.valueOf(389);

    /**
     * @see org.openuss.security.ldap.LdapServer#getPort()
     */
    public java.lang.Integer getPort()
    {
        return this.port;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setPort(java.lang.Integer port)
     */
    public void setPort(java.lang.Integer port)
    {
        this.port = port;
    }

    private java.lang.String rootDn;

    /**
     * @see org.openuss.security.ldap.LdapServer#getRootDn()
     */
    public java.lang.String getRootDn()
    {
        return this.rootDn;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setRootDn(java.lang.String rootDn)
     */
    public void setRootDn(java.lang.String rootDn)
    {
        this.rootDn = rootDn;
    }

    private java.lang.String authenticationType = "DIGEST-MD5";

    /**
     * @see org.openuss.security.ldap.LdapServer#getAuthenticationType()
     */
    public java.lang.String getAuthenticationType()
    {
        return this.authenticationType;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setAuthenticationType(java.lang.String authenticationType)
     */
    public void setAuthenticationType(java.lang.String authenticationType)
    {
        this.authenticationType = authenticationType;
    }

    private java.lang.String managerDn;

    /**
     * @see org.openuss.security.ldap.LdapServer#getManagerDn()
     */
    public java.lang.String getManagerDn()
    {
        return this.managerDn;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setManagerDn(java.lang.String managerDn)
     */
    public void setManagerDn(java.lang.String managerDn)
    {
        this.managerDn = managerDn;
    }

    private java.lang.String managerPassword;

    /**
     * @see org.openuss.security.ldap.LdapServer#getManagerPassword()
     */
    public java.lang.String getManagerPassword()
    {
        return this.managerPassword;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setManagerPassword(java.lang.String managerPassword)
     */
    public void setManagerPassword(java.lang.String managerPassword)
    {
        this.managerPassword = managerPassword;
    }

    private java.lang.Boolean useConnectionPool;

    /**
     * @see org.openuss.security.ldap.LdapServer#getUseConnectionPool()
     */
    public java.lang.Boolean getUseConnectionPool()
    {
        return this.useConnectionPool;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setUseConnectionPool(java.lang.Boolean useConnectionPool)
     */
    public void setUseConnectionPool(java.lang.Boolean useConnectionPool)
    {
        this.useConnectionPool = useConnectionPool;
    }

    private java.lang.Boolean useLdapContext;

    /**
     * @see org.openuss.security.ldap.LdapServer#getUseLdapContext()
     */
    public java.lang.Boolean getUseLdapContext()
    {
        return this.useLdapContext;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setUseLdapContext(java.lang.Boolean useLdapContext)
     */
    public void setUseLdapContext(java.lang.Boolean useLdapContext)
    {
        this.useLdapContext = useLdapContext;
    }

    private java.lang.String description;

    /**
     * @see org.openuss.security.ldap.LdapServer#getDescription()
     */
    public java.lang.String getDescription()
    {
        return this.description;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setDescription(java.lang.String description)
     */
    public void setDescription(java.lang.String description)
    {
        this.description = description;
    }

    private org.openuss.security.ldap.LdapServerType ldapServerType = org.openuss.security.ldap.LdapServerType.OTHER;

    /**
     * @see org.openuss.security.ldap.LdapServer#getLdapServerType()
     */
    public org.openuss.security.ldap.LdapServerType getLdapServerType()
    {
        return this.ldapServerType;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setLdapServerType(org.openuss.security.ldap.LdapServerType ldapServerType)
     */
    public void setLdapServerType(org.openuss.security.ldap.LdapServerType ldapServerType)
    {
        this.ldapServerType = ldapServerType;
    }

    private boolean enabled = true;

    /**
     * @see org.openuss.security.ldap.LdapServer#isEnabled()
     */
    public boolean isEnabled()
    {
        return this.enabled;
    }

    /**
     * @see org.openuss.security.ldap.LdapServer#setEnabled(boolean enabled)
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    private java.util.List<org.openuss.security.ldap.UserDnPattern> userDnPatterns = new java.util.ArrayList<org.openuss.security.ldap.UserDnPattern>();

    /**
     * 
     */
    public java.util.List<org.openuss.security.ldap.UserDnPattern> getUserDnPatterns()
    {
        return this.userDnPatterns;
    }

    public void setUserDnPatterns(java.util.List<org.openuss.security.ldap.UserDnPattern> userDnPatterns)
    {
        this.userDnPatterns = userDnPatterns;
    }

    private org.openuss.security.ldap.AuthenticationDomain authenticationDomain;

    /**
     * 
     */
    public org.openuss.security.ldap.AuthenticationDomain getAuthenticationDomain()
    {
        return this.authenticationDomain;
    }

    public void setAuthenticationDomain(org.openuss.security.ldap.AuthenticationDomain authenticationDomain)
    {
        this.authenticationDomain = authenticationDomain;
    }

    /**
     * Returns <code>true</code> if the argument is an LdapServer instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof LdapServer))
        {
            return false;
        }
        final LdapServer that = (LdapServer)object;
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

	public static class Factory extends LdapServer.Factory {
		
		public LdapServer createLdapServer() {
			return new LdapServerImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}