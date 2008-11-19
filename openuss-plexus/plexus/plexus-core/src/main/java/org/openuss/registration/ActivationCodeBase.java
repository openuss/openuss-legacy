package org.openuss.registration;

/**
 * 
 */
public abstract class ActivationCodeBase
    implements ActivationCode, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -6083194361407901074L;

    private java.lang.Long id;

    /**
     * @see org.openuss.registration.ActivationCode#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.registration.ActivationCode#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.sql.Timestamp createdAt;

    /**
     * @see org.openuss.registration.ActivationCode#getCreatedAt()
     */
    public java.sql.Timestamp getCreatedAt()
    {
        return this.createdAt;
    }

    /**
     * @see org.openuss.registration.ActivationCode#setCreatedAt(java.sql.Timestamp createdAt)
     */
    public void setCreatedAt(java.sql.Timestamp createdAt)
    {
        this.createdAt = createdAt;
    }

    /**
     * Returns <code>true</code> if the argument is an ActivationCode instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof ActivationCode))
        {
            return false;
        }
        final ActivationCode that = (ActivationCode)object;
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

	public static class Factory extends ActivationCode.Factory {
		
		public ActivationCode createActivationCode() {
			return new ActivationCodeImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}