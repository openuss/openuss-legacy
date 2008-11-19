package org.openuss.registration;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.registration.RegistrationService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.registration.RegistrationService
 */
public abstract class RegistrationServiceBase
    implements org.openuss.registration.RegistrationService
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

    private org.openuss.registration.UserActivationCodeDao userActivationCodeDao;

    /**
     * Sets the reference to <code>userActivationCode</code>'s DAO.
     */
    public void setUserActivationCodeDao(org.openuss.registration.UserActivationCodeDao userActivationCodeDao)
    {
        this.userActivationCodeDao = userActivationCodeDao;
    }

    /**
     * Gets the reference to <code>userActivationCode</code>'s DAO.
     */
    protected org.openuss.registration.UserActivationCodeDao getUserActivationCodeDao()
    {
        return this.userActivationCodeDao;
    }

    private org.openuss.registration.InstituteActivationCodeDao instituteActivationCodeDao;

    /**
     * Sets the reference to <code>instituteActivationCode</code>'s DAO.
     */
    public void setInstituteActivationCodeDao(org.openuss.registration.InstituteActivationCodeDao instituteActivationCodeDao)
    {
        this.instituteActivationCodeDao = instituteActivationCodeDao;
    }

    /**
     * Gets the reference to <code>instituteActivationCode</code>'s DAO.
     */
    protected org.openuss.registration.InstituteActivationCodeDao getInstituteActivationCodeDao()
    {
        return this.instituteActivationCodeDao;
    }

    private org.openuss.registration.UserDeleteCodeDao userDeleteCodeDao;

    /**
     * Sets the reference to <code>userDeleteCode</code>'s DAO.
     */
    public void setUserDeleteCodeDao(org.openuss.registration.UserDeleteCodeDao userDeleteCodeDao)
    {
        this.userDeleteCodeDao = userDeleteCodeDao;
    }

    /**
     * Gets the reference to <code>userDeleteCode</code>'s DAO.
     */
    protected org.openuss.registration.UserDeleteCodeDao getUserDeleteCodeDao()
    {
        return this.userDeleteCodeDao;
    }

    /**
     * @see org.openuss.registration.RegistrationService#generateActivationCode(org.openuss.security.UserInfo)
     */
    public java.lang.String generateActivationCode(org.openuss.security.UserInfo user)
        throws org.openuss.registration.RegistrationException
    {
        try
        {
            return this.handleGenerateActivationCode(user);
        }
        catch (org.openuss.registration.RegistrationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.registration.RegistrationServiceException(
                "Error performing 'org.openuss.registration.RegistrationService.generateActivationCode(org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #generateActivationCode(org.openuss.security.UserInfo)}
      */
    protected abstract java.lang.String handleGenerateActivationCode(org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.registration.RegistrationService#generateInstituteActivationCode(org.openuss.lecture.Institute)
     */
    public java.lang.String generateInstituteActivationCode(org.openuss.lecture.Institute institute)
        throws org.openuss.registration.RegistrationException
    {
        try
        {
            return this.handleGenerateInstituteActivationCode(institute);
        }
        catch (org.openuss.registration.RegistrationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.registration.RegistrationServiceException(
                "Error performing 'org.openuss.registration.RegistrationService.generateInstituteActivationCode(org.openuss.lecture.Institute institute)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #generateInstituteActivationCode(org.openuss.lecture.Institute)}
      */
    protected abstract java.lang.String handleGenerateInstituteActivationCode(org.openuss.lecture.Institute institute)
        throws java.lang.Exception;

    /**
     * @see org.openuss.registration.RegistrationService#registrateUser(org.openuss.security.UserInfo)
     */
    public void registrateUser(org.openuss.security.UserInfo user)
        throws org.openuss.registration.RegistrationException
    {
        try
        {
            this.handleRegistrateUser(user);
        }
        catch (org.openuss.registration.RegistrationException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.registration.RegistrationServiceException(
                "Error performing 'org.openuss.registration.RegistrationService.registrateUser(org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #registrateUser(org.openuss.security.UserInfo)}
      */
    protected abstract void handleRegistrateUser(org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.registration.RegistrationService#activateUserByCode(java.lang.String)
     */
    public void activateUserByCode(java.lang.String code)
        throws org.openuss.registration.RegistrationCodeNotFoundException, org.openuss.registration.RegistrationCodeExpiredException
    {
        try
        {
            this.handleActivateUserByCode(code);
        }
        catch (org.openuss.registration.RegistrationCodeNotFoundException ex)
        {
            throw ex;
        }
        catch (org.openuss.registration.RegistrationCodeExpiredException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.registration.RegistrationServiceException(
                "Error performing 'org.openuss.registration.RegistrationService.activateUserByCode(java.lang.String code)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #activateUserByCode(java.lang.String)}
      */
    protected abstract void handleActivateUserByCode(java.lang.String code)
        throws java.lang.Exception;

    /**
     * @see org.openuss.registration.RegistrationService#getCreateTime(java.lang.String)
     */
    public java.sql.Timestamp getCreateTime(java.lang.String activationCode)
        throws org.openuss.registration.RegistrationCodeNotFoundException
    {
        try
        {
            return this.handleGetCreateTime(activationCode);
        }
        catch (org.openuss.registration.RegistrationCodeNotFoundException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.registration.RegistrationServiceException(
                "Error performing 'org.openuss.registration.RegistrationService.getCreateTime(java.lang.String activationCode)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getCreateTime(java.lang.String)}
      */
    protected abstract java.sql.Timestamp handleGetCreateTime(java.lang.String activationCode)
        throws java.lang.Exception;

    /**
     * @see org.openuss.registration.RegistrationService#loginUserByActivationCode(java.lang.String)
     */
    public org.openuss.security.UserInfo loginUserByActivationCode(java.lang.String activationCode)
        throws org.openuss.registration.RegistrationCodeNotFoundException, org.openuss.registration.RegistrationCodeExpiredException
    {
        try
        {
            return this.handleLoginUserByActivationCode(activationCode);
        }
        catch (org.openuss.registration.RegistrationCodeNotFoundException ex)
        {
            throw ex;
        }
        catch (org.openuss.registration.RegistrationCodeExpiredException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.registration.RegistrationServiceException(
                "Error performing 'org.openuss.registration.RegistrationService.loginUserByActivationCode(java.lang.String activationCode)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #loginUserByActivationCode(java.lang.String)}
      */
    protected abstract org.openuss.security.UserInfo handleLoginUserByActivationCode(java.lang.String activationCode)
        throws java.lang.Exception;

    /**
     * @see org.openuss.registration.RegistrationService#activateInstituteByCode(java.lang.String)
     */
    public void activateInstituteByCode(java.lang.String code)
        throws org.openuss.registration.RegistrationCodeNotFoundException, org.openuss.registration.RegistrationCodeExpiredException, org.openuss.registration.RegistrationParentDepartmentDisabledException
    {
        try
        {
            this.handleActivateInstituteByCode(code);
        }
        catch (org.openuss.registration.RegistrationCodeNotFoundException ex)
        {
            throw ex;
        }
        catch (org.openuss.registration.RegistrationCodeExpiredException ex)
        {
            throw ex;
        }
        catch (org.openuss.registration.RegistrationParentDepartmentDisabledException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.registration.RegistrationServiceException(
                "Error performing 'org.openuss.registration.RegistrationService.activateInstituteByCode(java.lang.String code)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #activateInstituteByCode(java.lang.String)}
      */
    protected abstract void handleActivateInstituteByCode(java.lang.String code)
        throws java.lang.Exception;

    /**
     * @see org.openuss.registration.RegistrationService#removeInstituteCodes(org.openuss.lecture.Institute)
     */
    public void removeInstituteCodes(org.openuss.lecture.Institute institute)
    {
        try
        {
            this.handleRemoveInstituteCodes(institute);
        }
        catch (Throwable th)
        {
            throw new org.openuss.registration.RegistrationServiceException(
                "Error performing 'org.openuss.registration.RegistrationService.removeInstituteCodes(org.openuss.lecture.Institute institute)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #removeInstituteCodes(org.openuss.lecture.Institute)}
      */
    protected abstract void handleRemoveInstituteCodes(org.openuss.lecture.Institute institute)
        throws java.lang.Exception;

    /**
     * @see org.openuss.registration.RegistrationService#generateUserDeleteCode(org.openuss.security.UserInfo)
     */
    public java.lang.String generateUserDeleteCode(org.openuss.security.UserInfo user)
    {
        try
        {
            return this.handleGenerateUserDeleteCode(user);
        }
        catch (Throwable th)
        {
            throw new org.openuss.registration.RegistrationServiceException(
                "Error performing 'org.openuss.registration.RegistrationService.generateUserDeleteCode(org.openuss.security.UserInfo user)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #generateUserDeleteCode(org.openuss.security.UserInfo)}
      */
    protected abstract java.lang.String handleGenerateUserDeleteCode(org.openuss.security.UserInfo user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.registration.RegistrationService#generateDeleteUserCommand(java.lang.String)
     */
    public void generateDeleteUserCommand(java.lang.String code)
        throws org.openuss.registration.RegistrationCodeNotFoundException, org.openuss.registration.RegistrationCodeExpiredException
    {
        try
        {
            this.handleGenerateDeleteUserCommand(code);
        }
        catch (org.openuss.registration.RegistrationCodeNotFoundException ex)
        {
            throw ex;
        }
        catch (org.openuss.registration.RegistrationCodeExpiredException ex)
        {
            throw ex;
        }
        catch (Throwable th)
        {
            throw new org.openuss.registration.RegistrationServiceException(
                "Error performing 'org.openuss.registration.RegistrationService.generateDeleteUserCommand(java.lang.String code)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #generateDeleteUserCommand(java.lang.String)}
      */
    protected abstract void handleGenerateDeleteUserCommand(java.lang.String code)
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