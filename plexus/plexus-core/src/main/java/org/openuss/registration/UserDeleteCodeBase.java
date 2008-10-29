package org.openuss.registration;

/**
 * 
 */
public abstract class UserDeleteCodeBase
    extends org.openuss.registration.ActivationCodeImpl
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -6436678347016214087L;

    private java.lang.String code;

    /**
     * @see org.openuss.registration.UserDeleteCode#getCode()
     */
    public java.lang.String getCode()
    {
        return this.code;
    }

    /**
     * @see org.openuss.registration.UserDeleteCode#setCode(java.lang.String code)
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

	public static class Factory extends UserDeleteCode.Factory {
		
		public UserDeleteCode createUserDeleteCode() {
			return new UserDeleteCodeImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}