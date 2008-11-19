package org.openuss.security.ldap;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.security.ldap.LdapConfigurationService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.security.ldap.LdapConfigurationService
 */
public abstract class LdapConfigurationServiceBase
    implements org.openuss.security.ldap.LdapConfigurationService
{

    private org.openuss.security.ldap.AttributeMappingDao attributeMappingDao;

    /**
     * Sets the reference to <code>attributeMapping</code>'s DAO.
     */
    public void setAttributeMappingDao(org.openuss.security.ldap.AttributeMappingDao attributeMappingDao)
    {
        this.attributeMappingDao = attributeMappingDao;
    }

    /**
     * Gets the reference to <code>attributeMapping</code>'s DAO.
     */
    protected org.openuss.security.ldap.AttributeMappingDao getAttributeMappingDao()
    {
        return this.attributeMappingDao;
    }

    private org.openuss.security.ldap.RoleAttributeKeyDao roleAttributeKeyDao;

    /**
     * Sets the reference to <code>roleAttributeKey</code>'s DAO.
     */
    public void setRoleAttributeKeyDao(org.openuss.security.ldap.RoleAttributeKeyDao roleAttributeKeyDao)
    {
        this.roleAttributeKeyDao = roleAttributeKeyDao;
    }

    /**
     * Gets the reference to <code>roleAttributeKey</code>'s DAO.
     */
    protected org.openuss.security.ldap.RoleAttributeKeyDao getRoleAttributeKeyDao()
    {
        return this.roleAttributeKeyDao;
    }

    private org.openuss.security.ldap.AuthenticationDomainDao authenticationDomainDao;

    /**
     * Sets the reference to <code>authenticationDomain</code>'s DAO.
     */
    public void setAuthenticationDomainDao(org.openuss.security.ldap.AuthenticationDomainDao authenticationDomainDao)
    {
        this.authenticationDomainDao = authenticationDomainDao;
    }

    /**
     * Gets the reference to <code>authenticationDomain</code>'s DAO.
     */
    protected org.openuss.security.ldap.AuthenticationDomainDao getAuthenticationDomainDao()
    {
        return this.authenticationDomainDao;
    }

    private org.openuss.security.ldap.LdapServerDao ldapServerDao;

    /**
     * Sets the reference to <code>ldapServer</code>'s DAO.
     */
    public void setLdapServerDao(org.openuss.security.ldap.LdapServerDao ldapServerDao)
    {
        this.ldapServerDao = ldapServerDao;
    }

    /**
     * Gets the reference to <code>ldapServer</code>'s DAO.
     */
    protected org.openuss.security.ldap.LdapServerDao getLdapServerDao()
    {
        return this.ldapServerDao;
    }

    private org.openuss.security.ldap.UserDnPatternDao userDnPatternDao;

    /**
     * Sets the reference to <code>userDnPattern</code>'s DAO.
     */
    public void setUserDnPatternDao(org.openuss.security.ldap.UserDnPatternDao userDnPatternDao)
    {
        this.userDnPatternDao = userDnPatternDao;
    }

    /**
     * Gets the reference to <code>userDnPattern</code>'s DAO.
     */
    protected org.openuss.security.ldap.UserDnPatternDao getUserDnPatternDao()
    {
        return this.userDnPatternDao;
    }

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getEnabledLdapServerConfigurations()
     */
    public java.util.List getEnabledLdapServerConfigurations()
    {
        try
        {
            return this.handleGetEnabledLdapServerConfigurations();
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getEnabledLdapServerConfigurations()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getEnabledLdapServerConfigurations()}
      */
    protected abstract java.util.List handleGetEnabledLdapServerConfigurations()
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#createLdapServer(org.openuss.security.ldap.LdapServerInfo)
     */
    public org.openuss.security.ldap.LdapServerInfo createLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer)
    {
        try
        {
            return this.handleCreateLdapServer(ldapServer);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.createLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createLdapServer(org.openuss.security.ldap.LdapServerInfo)}
      */
    protected abstract org.openuss.security.ldap.LdapServerInfo handleCreateLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#deleteLdapServer(org.openuss.security.ldap.LdapServerInfo)
     */
    public void deleteLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer)
    {
        try
        {
            this.handleDeleteLdapServer(ldapServer);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.deleteLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteLdapServer(org.openuss.security.ldap.LdapServerInfo)}
      */
    protected abstract void handleDeleteLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#saveLdapServer(org.openuss.security.ldap.LdapServerInfo)
     */
    public void saveLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer)
    {
        try
        {
            this.handleSaveLdapServer(ldapServer);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.saveLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveLdapServer(org.openuss.security.ldap.LdapServerInfo)}
      */
    protected abstract void handleSaveLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getLdapServersByDomain(org.openuss.security.ldap.AuthenticationDomainInfo)
     */
    public java.util.List getLdapServersByDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)
    {
        try
        {
            return this.handleGetLdapServersByDomain(domain);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getLdapServersByDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getLdapServersByDomain(org.openuss.security.ldap.AuthenticationDomainInfo)}
      */
    protected abstract java.util.List handleGetLdapServersByDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getAllLdapServers()
     */
    public java.util.List getAllLdapServers()
    {
        try
        {
            return this.handleGetAllLdapServers();
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getAllLdapServers()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAllLdapServers()}
      */
    protected abstract java.util.List handleGetAllLdapServers()
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#createDomain(org.openuss.security.ldap.AuthenticationDomainInfo)
     */
    public org.openuss.security.ldap.AuthenticationDomainInfo createDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)
    {
        try
        {
            return this.handleCreateDomain(domain);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.createDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createDomain(org.openuss.security.ldap.AuthenticationDomainInfo)}
      */
    protected abstract org.openuss.security.ldap.AuthenticationDomainInfo handleCreateDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#deleteDomain(org.openuss.security.ldap.AuthenticationDomainInfo)
     */
    public void deleteDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)
    {
        try
        {
            this.handleDeleteDomain(domain);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.deleteDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteDomain(org.openuss.security.ldap.AuthenticationDomainInfo)}
      */
    protected abstract void handleDeleteDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#saveDomain(org.openuss.security.ldap.AuthenticationDomainInfo)
     */
    public void saveDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)
    {
        try
        {
            this.handleSaveDomain(domain);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.saveDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveDomain(org.openuss.security.ldap.AuthenticationDomainInfo)}
      */
    protected abstract void handleSaveDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getAllDomains()
     */
    public java.util.List getAllDomains()
    {
        try
        {
            return this.handleGetAllDomains();
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getAllDomains()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAllDomains()}
      */
    protected abstract java.util.List handleGetAllDomains()
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#addServerToDomain(org.openuss.security.ldap.LdapServerInfo, org.openuss.security.ldap.AuthenticationDomainInfo)
     */
    public void addServerToDomain(org.openuss.security.ldap.LdapServerInfo server, org.openuss.security.ldap.AuthenticationDomainInfo domain)
    {
        try
        {
            this.handleAddServerToDomain(server, domain);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.addServerToDomain(org.openuss.security.ldap.LdapServerInfo server, org.openuss.security.ldap.AuthenticationDomainInfo domain)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addServerToDomain(org.openuss.security.ldap.LdapServerInfo, org.openuss.security.ldap.AuthenticationDomainInfo)}
      */
    protected abstract void handleAddServerToDomain(org.openuss.security.ldap.LdapServerInfo server, org.openuss.security.ldap.AuthenticationDomainInfo domain)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#removeServerFromDomain(org.openuss.security.ldap.LdapServerInfo, org.openuss.security.ldap.AuthenticationDomainInfo)
     */
    public void removeServerFromDomain(org.openuss.security.ldap.LdapServerInfo server, org.openuss.security.ldap.AuthenticationDomainInfo domian)
    {
        try
        {
            this.handleRemoveServerFromDomain(server, domian);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.removeServerFromDomain(org.openuss.security.ldap.LdapServerInfo server, org.openuss.security.ldap.AuthenticationDomainInfo domian)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeServerFromDomain(org.openuss.security.ldap.LdapServerInfo, org.openuss.security.ldap.AuthenticationDomainInfo)}
      */
    protected abstract void handleRemoveServerFromDomain(org.openuss.security.ldap.LdapServerInfo server, org.openuss.security.ldap.AuthenticationDomainInfo domian)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#createAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo)
     */
    public org.openuss.security.ldap.AttributeMappingInfo createAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping)
    {
        try
        {
            return this.handleCreateAttributeMapping(attributeMapping);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.createAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo)}
      */
    protected abstract org.openuss.security.ldap.AttributeMappingInfo handleCreateAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#deleteAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo)
     */
    public void deleteAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping)
    {
        try
        {
            this.handleDeleteAttributeMapping(attributeMapping);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.deleteAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo)}
      */
    protected abstract void handleDeleteAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#saveAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo)
     */
    public void saveAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping)
    {
        try
        {
            this.handleSaveAttributeMapping(attributeMapping);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.saveAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo)}
      */
    protected abstract void handleSaveAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getAllAttributeMappings()
     */
    public java.util.List getAllAttributeMappings()
    {
        try
        {
            return this.handleGetAllAttributeMappings();
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getAllAttributeMappings()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAllAttributeMappings()}
      */
    protected abstract java.util.List handleGetAllAttributeMappings()
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#createRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo)
     */
    public org.openuss.security.ldap.RoleAttributeKeyInfo createRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey)
    {
        try
        {
            return this.handleCreateRoleAttributeKey(roleAttributeKey);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.createRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo)}
      */
    protected abstract org.openuss.security.ldap.RoleAttributeKeyInfo handleCreateRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#deleteRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo)
     */
    public void deleteRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey)
    {
        try
        {
            this.handleDeleteRoleAttributeKey(roleAttributeKey);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.deleteRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo)}
      */
    protected abstract void handleDeleteRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#saveRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo)
     */
    public void saveRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey)
    {
        try
        {
            this.handleSaveRoleAttributeKey(roleAttributeKey);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.saveRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo)}
      */
    protected abstract void handleSaveRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#removeRoleAttributeKeyFromAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo, org.openuss.security.ldap.AttributeMappingInfo)
     */
    public void removeRoleAttributeKeyFromAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyInfo, org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)
    {
        try
        {
            this.handleRemoveRoleAttributeKeyFromAttributeMapping(roleAttributeKeyInfo, attributeMappingInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.removeRoleAttributeKeyFromAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyInfo, org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeRoleAttributeKeyFromAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo, org.openuss.security.ldap.AttributeMappingInfo)}
      */
    protected abstract void handleRemoveRoleAttributeKeyFromAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyInfo, org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#addRoleAttributeKeyToAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo, org.openuss.security.ldap.AttributeMappingInfo)
     */
    public void addRoleAttributeKeyToAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyinfo, org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)
    {
        try
        {
            this.handleAddRoleAttributeKeyToAttributeMapping(roleAttributeKeyinfo, attributeMappingInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.addRoleAttributeKeyToAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyinfo, org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addRoleAttributeKeyToAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo, org.openuss.security.ldap.AttributeMappingInfo)}
      */
    protected abstract void handleAddRoleAttributeKeyToAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyinfo, org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getAllRoleAttributeKeys()
     */
    public java.util.List getAllRoleAttributeKeys()
    {
        try
        {
            return this.handleGetAllRoleAttributeKeys();
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getAllRoleAttributeKeys()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAllRoleAttributeKeys()}
      */
    protected abstract java.util.List handleGetAllRoleAttributeKeys()
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getAllRoleAttributeKeysByMapping(org.openuss.security.ldap.AttributeMappingInfo)
     */
    public java.util.List getAllRoleAttributeKeysByMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)
    {
        try
        {
            return this.handleGetAllRoleAttributeKeysByMapping(attributeMappingInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getAllRoleAttributeKeysByMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAllRoleAttributeKeysByMapping(org.openuss.security.ldap.AttributeMappingInfo)}
      */
    protected abstract java.util.List handleGetAllRoleAttributeKeysByMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#removeDomainFromAttributeMapping(org.openuss.security.ldap.AuthenticationDomainInfo, org.openuss.security.ldap.AttributeMappingInfo)
     */
    public void removeDomainFromAttributeMapping(org.openuss.security.ldap.AuthenticationDomainInfo domain, org.openuss.security.ldap.AttributeMappingInfo mapping)
    {
        try
        {
            this.handleRemoveDomainFromAttributeMapping(domain, mapping);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.removeDomainFromAttributeMapping(org.openuss.security.ldap.AuthenticationDomainInfo domain, org.openuss.security.ldap.AttributeMappingInfo mapping)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeDomainFromAttributeMapping(org.openuss.security.ldap.AuthenticationDomainInfo, org.openuss.security.ldap.AttributeMappingInfo)}
      */
    protected abstract void handleRemoveDomainFromAttributeMapping(org.openuss.security.ldap.AuthenticationDomainInfo domain, org.openuss.security.ldap.AttributeMappingInfo mapping)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#addDomainToAttributeMapping(org.openuss.security.ldap.AuthenticationDomainInfo, org.openuss.security.ldap.AttributeMappingInfo)
     */
    public void addDomainToAttributeMapping(org.openuss.security.ldap.AuthenticationDomainInfo domain, org.openuss.security.ldap.AttributeMappingInfo mapping)
    {
        try
        {
            this.handleAddDomainToAttributeMapping(domain, mapping);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.addDomainToAttributeMapping(org.openuss.security.ldap.AuthenticationDomainInfo domain, org.openuss.security.ldap.AttributeMappingInfo mapping)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addDomainToAttributeMapping(org.openuss.security.ldap.AuthenticationDomainInfo, org.openuss.security.ldap.AttributeMappingInfo)}
      */
    protected abstract void handleAddDomainToAttributeMapping(org.openuss.security.ldap.AuthenticationDomainInfo domain, org.openuss.security.ldap.AttributeMappingInfo mapping)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#createUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo)
     */
    public org.openuss.security.ldap.UserDnPatternInfo createUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)
    {
        try
        {
            return this.handleCreateUserDnPattern(userDnPattern);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.createUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #createUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo)}
      */
    protected abstract org.openuss.security.ldap.UserDnPatternInfo handleCreateUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#saveUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo)
     */
    public void saveUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)
    {
        try
        {
            this.handleSaveUserDnPattern(userDnPattern);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.saveUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo)}
      */
    protected abstract void handleSaveUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#deleteUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo)
     */
    public void deleteUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)
    {
        try
        {
            this.handleDeleteUserDnPattern(userDnPattern);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.deleteUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo)}
      */
    protected abstract void handleDeleteUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#addUserDnPatternToLdapServer(org.openuss.security.ldap.UserDnPatternInfo, org.openuss.security.ldap.LdapServerInfo)
     */
    public void addUserDnPatternToLdapServer(org.openuss.security.ldap.UserDnPatternInfo userDnPatternInfo, org.openuss.security.ldap.LdapServerInfo ldapServerInfo)
    {
        try
        {
            this.handleAddUserDnPatternToLdapServer(userDnPatternInfo, ldapServerInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.addUserDnPatternToLdapServer(org.openuss.security.ldap.UserDnPatternInfo userDnPatternInfo, org.openuss.security.ldap.LdapServerInfo ldapServerInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #addUserDnPatternToLdapServer(org.openuss.security.ldap.UserDnPatternInfo, org.openuss.security.ldap.LdapServerInfo)}
      */
    protected abstract void handleAddUserDnPatternToLdapServer(org.openuss.security.ldap.UserDnPatternInfo userDnPatternInfo, org.openuss.security.ldap.LdapServerInfo ldapServerInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#removeUserDnPatternFromLdapServer(org.openuss.security.ldap.UserDnPatternInfo, org.openuss.security.ldap.LdapServerInfo)
     */
    public void removeUserDnPatternFromLdapServer(org.openuss.security.ldap.UserDnPatternInfo userDnPatternInfo, org.openuss.security.ldap.LdapServerInfo ldapServerInfo)
    {
        try
        {
            this.handleRemoveUserDnPatternFromLdapServer(userDnPatternInfo, ldapServerInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.removeUserDnPatternFromLdapServer(org.openuss.security.ldap.UserDnPatternInfo userDnPatternInfo, org.openuss.security.ldap.LdapServerInfo ldapServerInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeUserDnPatternFromLdapServer(org.openuss.security.ldap.UserDnPatternInfo, org.openuss.security.ldap.LdapServerInfo)}
      */
    protected abstract void handleRemoveUserDnPatternFromLdapServer(org.openuss.security.ldap.UserDnPatternInfo userDnPatternInfo, org.openuss.security.ldap.LdapServerInfo ldapServerInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getAllUserDnPatterns()
     */
    public java.util.List getAllUserDnPatterns()
    {
        try
        {
            return this.handleGetAllUserDnPatterns();
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getAllUserDnPatterns()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAllUserDnPatterns()}
      */
    protected abstract java.util.List handleGetAllUserDnPatterns()
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getAllUserDnPatternsByLdapServer(org.openuss.security.ldap.LdapServerInfo)
     */
    public java.util.List getAllUserDnPatternsByLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServerInfo)
    {
        try
        {
            return this.handleGetAllUserDnPatternsByLdapServer(ldapServerInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getAllUserDnPatternsByLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServerInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAllUserDnPatternsByLdapServer(org.openuss.security.ldap.LdapServerInfo)}
      */
    protected abstract java.util.List handleGetAllUserDnPatternsByLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServerInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getDomainById(java.lang.Long)
     */
    public org.openuss.security.ldap.AuthenticationDomainInfo getDomainById(java.lang.Long authDomainId)
    {
        try
        {
            return this.handleGetDomainById(authDomainId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getDomainById(java.lang.Long authDomainId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getDomainById(java.lang.Long)}
      */
    protected abstract org.openuss.security.ldap.AuthenticationDomainInfo handleGetDomainById(java.lang.Long authDomainId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getLdapServerById(java.lang.Long)
     */
    public org.openuss.security.ldap.LdapServerInfo getLdapServerById(java.lang.Long id)
    {
        try
        {
            return this.handleGetLdapServerById(id);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getLdapServerById(java.lang.Long id)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getLdapServerById(java.lang.Long)}
      */
    protected abstract org.openuss.security.ldap.LdapServerInfo handleGetLdapServerById(java.lang.Long id)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getRoleAttributeKeyById(java.lang.Long)
     */
    public org.openuss.security.ldap.RoleAttributeKeyInfo getRoleAttributeKeyById(java.lang.Long id)
    {
        try
        {
            return this.handleGetRoleAttributeKeyById(id);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getRoleAttributeKeyById(java.lang.Long id)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getRoleAttributeKeyById(java.lang.Long)}
      */
    protected abstract org.openuss.security.ldap.RoleAttributeKeyInfo handleGetRoleAttributeKeyById(java.lang.Long id)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getAttributeMappingById(java.lang.Long)
     */
    public org.openuss.security.ldap.AttributeMappingInfo getAttributeMappingById(java.lang.Long id)
    {
        try
        {
            return this.handleGetAttributeMappingById(id);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getAttributeMappingById(java.lang.Long id)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getAttributeMappingById(java.lang.Long)}
      */
    protected abstract org.openuss.security.ldap.AttributeMappingInfo handleGetAttributeMappingById(java.lang.Long id)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getUserDnPatternById(java.lang.Long)
     */
    public org.openuss.security.ldap.UserDnPatternInfo getUserDnPatternById(java.lang.Long id)
    {
        try
        {
            return this.handleGetUserDnPatternById(id);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.getUserDnPatternById(java.lang.Long id)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getUserDnPatternById(java.lang.Long)}
      */
    protected abstract org.openuss.security.ldap.UserDnPatternInfo handleGetUserDnPatternById(java.lang.Long id)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#isValidRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo)
     */
    public boolean isValidRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyInfo)
    {
        try
        {
            return this.handleIsValidRoleAttributeKey(roleAttributeKeyInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.isValidRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #isValidRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo)}
      */
    protected abstract boolean handleIsValidRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#isValidUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo)
     */
    public boolean isValidUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)
    {
        try
        {
            return this.handleIsValidUserDnPattern(userDnPattern);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.isValidUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #isValidUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo)}
      */
    protected abstract boolean handleIsValidUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo userDnPattern)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#isValidAttributeMappingName(org.openuss.security.ldap.AttributeMappingInfo)
     */
    public boolean isValidAttributeMappingName(org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)
    {
        try
        {
            return this.handleIsValidAttributeMappingName(attributeMappingInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.isValidAttributeMappingName(org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #isValidAttributeMappingName(org.openuss.security.ldap.AttributeMappingInfo)}
      */
    protected abstract boolean handleIsValidAttributeMappingName(org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#isValidAuthenticationDomainName(org.openuss.security.ldap.AuthenticationDomainInfo)
     */
    public boolean isValidAuthenticationDomainName(org.openuss.security.ldap.AuthenticationDomainInfo authenticationDomainInfo)
    {
        try
        {
            return this.handleIsValidAuthenticationDomainName(authenticationDomainInfo);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.isValidAuthenticationDomainName(org.openuss.security.ldap.AuthenticationDomainInfo authenticationDomainInfo)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #isValidAuthenticationDomainName(org.openuss.security.ldap.AuthenticationDomainInfo)}
      */
    protected abstract boolean handleIsValidAuthenticationDomainName(org.openuss.security.ldap.AuthenticationDomainInfo authenticationDomainInfo)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#isValidURL(java.lang.String[], java.lang.String)
     */
    public boolean isValidURL(java.lang.String[] schemes, java.lang.String url)
    {
        try
        {
            return this.handleIsValidURL(schemes, url);
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationService.isValidURL(java.lang.String[] schemes, java.lang.String url)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #isValidURL(java.lang.String[], java.lang.String)}
      */
    protected abstract boolean handleIsValidURL(java.lang.String[] schemes, java.lang.String url)
        throws java.lang.Exception;

    /**
     * Gets the current <code>principal</code> if one has been set,
     * otherwise returns <code>null</code>.
     *
     * @return the current principal
     */
    protected java.security.Principal getPrincipal()
    {
        return org.andromda.spring.PrincipalStore.get();
    }
}