package org.openuss.registration;

/**
 * 
 */
public abstract class InstituteActivationCodeBase
    extends org.openuss.registration.ActivationCodeImpl
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 6920927689882200545L;

    private java.lang.String code;

    /**
     * @see org.openuss.registration.InstituteActivationCode#getCode()
     */
    public java.lang.String getCode()
    {
        return this.code;
    }

    /**
     * @see org.openuss.registration.InstituteActivationCode#setCode(java.lang.String code)
     */
    public void setCode(java.lang.String code)
    {
        this.code = code;
    }

    private org.openuss.lecture.Institute institute;

    /**
     * 
     */
    public org.openuss.lecture.Institute getInstitute()
    {
        return this.institute;
    }

    public void setInstitute(org.openuss.lecture.Institute institute)
    {
        this.institute = institute;
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

	public static class Factory extends InstituteActivationCode.Factory {
		
		public InstituteActivationCode createInstituteActivationCode() {
			return new InstituteActivationCodeImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}