package org.openuss.security.ldap;

/**
 * 
 */
public abstract class AttributeMappingBase
    implements AttributeMapping, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -4341826863475415295L;

    private java.lang.Long id;

    /**
     * @see org.openuss.security.ldap.AttributeMapping#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.security.ldap.AttributeMapping#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String mappingName;

    /**
     * @see org.openuss.security.ldap.AttributeMapping#getMappingName()
     */
    public java.lang.String getMappingName()
    {
        return this.mappingName;
    }

    /**
     * @see org.openuss.security.ldap.AttributeMapping#setMappingName(java.lang.String mappingName)
     */
    public void setMappingName(java.lang.String mappingName)
    {
        this.mappingName = mappingName;
    }

    private java.lang.String usernameKey;

    /**
     * @see org.openuss.security.ldap.AttributeMapping#getUsernameKey()
     */
    public java.lang.String getUsernameKey()
    {
        return this.usernameKey;
    }

    /**
     * @see org.openuss.security.ldap.AttributeMapping#setUsernameKey(java.lang.String usernameKey)
     */
    public void setUsernameKey(java.lang.String usernameKey)
    {
        this.usernameKey = usernameKey;
    }

    private java.lang.String firstNameKey;

    /**
     * @see org.openuss.security.ldap.AttributeMapping#getFirstNameKey()
     */
    public java.lang.String getFirstNameKey()
    {
        return this.firstNameKey;
    }

    /**
     * @see org.openuss.security.ldap.AttributeMapping#setFirstNameKey(java.lang.String firstNameKey)
     */
    public void setFirstNameKey(java.lang.String firstNameKey)
    {
        this.firstNameKey = firstNameKey;
    }

    private java.lang.String lastNameKey;

    /**
     * @see org.openuss.security.ldap.AttributeMapping#getLastNameKey()
     */
    public java.lang.String getLastNameKey()
    {
        return this.lastNameKey;
    }

    /**
     * @see org.openuss.security.ldap.AttributeMapping#setLastNameKey(java.lang.String lastNameKey)
     */
    public void setLastNameKey(java.lang.String lastNameKey)
    {
        this.lastNameKey = lastNameKey;
    }

    private java.lang.String emailKey;

    /**
     * @see org.openuss.security.ldap.AttributeMapping#getEmailKey()
     */
    public java.lang.String getEmailKey()
    {
        return this.emailKey;
    }

    /**
     * @see org.openuss.security.ldap.AttributeMapping#setEmailKey(java.lang.String emailKey)
     */
    public void setEmailKey(java.lang.String emailKey)
    {
        this.emailKey = emailKey;
    }

    private java.lang.String groupRoleAttributeKey;

    /**
     * @see org.openuss.security.ldap.AttributeMapping#getGroupRoleAttributeKey()
     */
    public java.lang.String getGroupRoleAttributeKey()
    {
        return this.groupRoleAttributeKey;
    }

    /**
     * @see org.openuss.security.ldap.AttributeMapping#setGroupRoleAttributeKey(java.lang.String groupRoleAttributeKey)
     */
    public void setGroupRoleAttributeKey(java.lang.String groupRoleAttributeKey)
    {
        this.groupRoleAttributeKey = groupRoleAttributeKey;
    }

    private java.util.List<org.openuss.security.ldap.RoleAttributeKey> roleAttributeKeys = new java.util.ArrayList<org.openuss.security.ldap.RoleAttributeKey>();

    /**
     * 
     */
    public java.util.List<org.openuss.security.ldap.RoleAttributeKey> getRoleAttributeKeys()
    {
        return this.roleAttributeKeys;
    }

    public void setRoleAttributeKeys(java.util.List<org.openuss.security.ldap.RoleAttributeKey> roleAttributeKeys)
    {
        this.roleAttributeKeys = roleAttributeKeys;
    }

    private java.util.Set<org.openuss.security.ldap.AuthenticationDomain> authenticationDomains = new java.util.HashSet<org.openuss.security.ldap.AuthenticationDomain>();

    /**
     * 
     */
    public java.util.Set<org.openuss.security.ldap.AuthenticationDomain> getAuthenticationDomains()
    {
        return this.authenticationDomains;
    }

    public void setAuthenticationDomains(java.util.Set<org.openuss.security.ldap.AuthenticationDomain> authenticationDomains)
    {
        this.authenticationDomains = authenticationDomains;
    }

    /**
     * Returns <code>true</code> if the argument is an AttributeMapping instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof AttributeMapping))
        {
            return false;
        }
        final AttributeMapping that = (AttributeMapping)object;
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

	public static class Factory extends AttributeMapping.Factory {
		
		public AttributeMapping createAttributeMapping() {
			return new AttributeMappingImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}