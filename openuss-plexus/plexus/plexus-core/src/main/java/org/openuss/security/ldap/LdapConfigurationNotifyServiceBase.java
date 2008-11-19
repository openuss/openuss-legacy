package org.openuss.security.ldap;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.security.ldap.LdapConfigurationNotifyService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.security.ldap.LdapConfigurationNotifyService
 */
public abstract class LdapConfigurationNotifyServiceBase
    implements org.openuss.security.ldap.LdapConfigurationNotifyService
{

    private org.openuss.commands.CommandService commandService;

    /**
     * Sets the reference to <code>commandService</code>.
     */
    public void setCommandService(org.openuss.commands.CommandService commandService)
    {
        this.commandService = commandService;
    }

    /**
     * Gets the reference to <code>commandService</code>.
     */
    protected org.openuss.commands.CommandService getCommandService()
    {
        return this.commandService;
    }

    /**
     * @see org.openuss.security.ldap.LdapConfigurationNotifyService#reconfigure()
     */
    public void reconfigure()
    {
        try
        {
            this.handleReconfigure();
        }
        catch (Throwable th)
        {
            throw new org.openuss.security.ldap.LdapConfigurationNotifyServiceException(
                "Error performing 'org.openuss.security.ldap.LdapConfigurationNotifyService.reconfigure()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #reconfigure()}
      */
    protected abstract void handleReconfigure()
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