package org.openuss.statistics;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.statistics.OnlineStatisticService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.statistics.OnlineStatisticService
 */
public abstract class OnlineStatisticServiceBase
    implements org.openuss.statistics.OnlineStatisticService
{

    private org.openuss.security.SecurityService securityService;

    /**
     * Sets the reference to <code>securityService</code>.
     */
    public void setSecurityService(org.openuss.security.SecurityService securityService)
    {
        this.securityService = securityService;
    }

    /**
     * Gets the reference to <code>securityService</code>.
     */
    protected org.openuss.security.SecurityService getSecurityService()
    {
        return this.securityService;
    }

    private org.openuss.statistics.OnlineSessionDao onlineSessionDao;

    /**
     * Sets the reference to <code>onlineSession</code>'s DAO.
     */
    public void setOnlineSessionDao(org.openuss.statistics.OnlineSessionDao onlineSessionDao)
    {
        this.onlineSessionDao = onlineSessionDao;
    }

    /**
     * Gets the reference to <code>onlineSession</code>'s DAO.
     */
    protected org.openuss.statistics.OnlineSessionDao getOnlineSessionDao()
    {
        return this.onlineSessionDao;
    }

    private org.openuss.statistics.SystemStatisticDao systemStatisticDao;

    /**
     * Sets the reference to <code>systemStatistic</code>'s DAO.
     */
    public void setSystemStatisticDao(org.openuss.statistics.SystemStatisticDao systemStatisticDao)
    {
        this.systemStatisticDao = systemStatisticDao;
    }

    /**
     * Gets the reference to <code>systemStatistic</code>'s DAO.
     */
    protected org.openuss.statistics.SystemStatisticDao getSystemStatisticDao()
    {
        return this.systemStatisticDao;
    }

    /**
     * @see org.openuss.statistics.OnlineStatisticService#logSessionStart(java.lang.Long)
     */
    public java.lang.Long logSessionStart(java.lang.Long sessionId)
    {
        try
        {
            return this.handleLogSessionStart(sessionId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.statistics.OnlineStatisticServiceException(
                "Error performing 'org.openuss.statistics.OnlineStatisticService.logSessionStart(java.lang.Long sessionId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #logSessionStart(java.lang.Long)}
      */
    protected abstract java.lang.Long handleLogSessionStart(java.lang.Long sessionId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.statistics.OnlineStatisticService#logSessionEnd(java.lang.Long)
     */
    public void logSessionEnd(java.lang.Long sessionId)
    {
        try
        {
            this.handleLogSessionEnd(sessionId);
        }
        catch (Throwable th)
        {
            throw new org.openuss.statistics.OnlineStatisticServiceException(
                "Error performing 'org.openuss.statistics.OnlineStatisticService.logSessionEnd(java.lang.Long sessionId)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #logSessionEnd(java.lang.Long)}
      */
    protected abstract void handleLogSessionEnd(java.lang.Long sessionId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.statistics.OnlineStatisticService#getOnlineInfo()
     */
    public org.openuss.statistics.OnlineInfo getOnlineInfo()
    {
        try
        {
            return this.handleGetOnlineInfo();
        }
        catch (Throwable th)
        {
            throw new org.openuss.statistics.OnlineStatisticServiceException(
                "Error performing 'org.openuss.statistics.OnlineStatisticService.getOnlineInfo()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getOnlineInfo()}
      */
    protected abstract org.openuss.statistics.OnlineInfo handleGetOnlineInfo()
        throws java.lang.Exception;

    /**
     * @see org.openuss.statistics.OnlineStatisticService#getActiveUsers()
     */
    public java.util.List getActiveUsers()
    {
        try
        {
            return this.handleGetActiveUsers();
        }
        catch (Throwable th)
        {
            throw new org.openuss.statistics.OnlineStatisticServiceException(
                "Error performing 'org.openuss.statistics.OnlineStatisticService.getActiveUsers()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getActiveUsers()}
      */
    protected abstract java.util.List handleGetActiveUsers()
        throws java.lang.Exception;

    /**
     * @see org.openuss.statistics.OnlineStatisticService#getSystemStatistics()
     */
    public org.openuss.statistics.SystemStatisticInfo getSystemStatistics()
    {
        try
        {
            return this.handleGetSystemStatistics();
        }
        catch (Throwable th)
        {
            throw new org.openuss.statistics.OnlineStatisticServiceException(
                "Error performing 'org.openuss.statistics.OnlineStatisticService.getSystemStatistics()' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getSystemStatistics()}
      */
    protected abstract org.openuss.statistics.SystemStatisticInfo handleGetSystemStatistics()
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