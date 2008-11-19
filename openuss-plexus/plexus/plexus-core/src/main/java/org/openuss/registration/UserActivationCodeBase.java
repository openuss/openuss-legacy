package org.openuss.registration;

/**
 * <p>
 * The RegistrationKey is generated during the registration process
 * of a new user. To verify the user email adress. After the
 * registration forms were completed a registration key is
 * generated and send to the users email adress. Within the email
 * is a link to submit the generated registration key. After the
 * user has submitted the key the user account is activated.
 * </p>
 */
public abstract class UserActivationCodeBase
    extends org.openuss.registration.ActivationCodeImpl
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 1505175020488304262L;

    private java.lang.String code;

    /**
     * @see org.openuss.registration.UserActivationCode#getCode()
     */
    public java.lang.String getCode()
    {
        return this.code;
    }

    /**
     * @see org.openuss.registration.UserActivationCode#setCode(java.lang.String code)
     */
    public void setCode(java.lang.String code)
    {
        this.code = code;
    }

    private org.openuss.security.User user;

    /**
     * 
     */
    public org.openuss.security.User getUser()
    {
        return this.user;
    }

    public void setUser(org.openuss.security.User user)
    {
        this.user = user;
    }

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.registration.ActivationCodeImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.registration.ActivationCode#equals(Object)
     */
    public boolean equals(Object object)
    {
        return super.equals(object);
    }

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.registration.ActivationCodeImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.registration.ActivationCode#hashCode()
     */
    public int hashCode()
    {
        return super.hashCode();
    }

	public static class Factory extends UserActivationCode.Factory {
		
		public UserActivationCode createUserActivationCode() {
			return new UserActivationCodeImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}