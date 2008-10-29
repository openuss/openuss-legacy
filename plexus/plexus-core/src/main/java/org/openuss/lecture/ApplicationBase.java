package org.openuss.lecture;

/**
 * <p>
 * Application for an official institute of the department.
 * </p>
 */
public abstract class ApplicationBase
    implements Application, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -8213198387260319693L;

    private java.lang.Long id;

    /**
     * @see org.openuss.lecture.Application#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.lecture.Application#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.util.Date applicationDate;

    /**
     * @see org.openuss.lecture.Application#getApplicationDate()
     */
    public java.util.Date getApplicationDate()
    {
        return this.applicationDate;
    }

    /**
     * @see org.openuss.lecture.Application#setApplicationDate(java.util.Date applicationDate)
     */
    public void setApplicationDate(java.util.Date applicationDate)
    {
        this.applicationDate = applicationDate;
    }

    private java.util.Date confirmationDate;

    /**
     * @see org.openuss.lecture.Application#getConfirmationDate()
     */
    public java.util.Date getConfirmationDate()
    {
        return this.confirmationDate;
    }

    /**
     * @see org.openuss.lecture.Application#setConfirmationDate(java.util.Date confirmationDate)
     */
    public void setConfirmationDate(java.util.Date confirmationDate)
    {
        this.confirmationDate = confirmationDate;
    }

    private java.lang.String description;

    /**
     * @see org.openuss.lecture.Application#getDescription()
     */
    public java.lang.String getDescription()
    {
        return this.description;
    }

    /**
     * @see org.openuss.lecture.Application#setDescription(java.lang.String description)
     */
    public void setDescription(java.lang.String description)
    {
        this.description = description;
    }

    private boolean confirmed = false;

    /**
     * @see org.openuss.lecture.Application#isConfirmed()
     */
    public boolean isConfirmed()
    {
        return this.confirmed;
    }

    /**
     * @see org.openuss.lecture.Application#setConfirmed(boolean confirmed)
     */
    public void setConfirmed(boolean confirmed)
    {
        this.confirmed = confirmed;
    }

    private org.openuss.lecture.Department department;

    /**
     * 
     */
    public org.openuss.lecture.Department getDepartment()
    {
        return this.department;
    }

    public void setDepartment(org.openuss.lecture.Department department)
    {
        this.department = department;
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

    private org.openuss.security.User applyingUser;

    /**
     * 
     */
    public org.openuss.security.User getApplyingUser()
    {
        return this.applyingUser;
    }

    public void setApplyingUser(org.openuss.security.User applyingUser)
    {
        this.applyingUser = applyingUser;
    }

    private org.openuss.security.User confirmingUser;

    /**
     * 
     */
    public org.openuss.security.User getConfirmingUser()
    {
        return this.confirmingUser;
    }

    public void setConfirmingUser(org.openuss.security.User confirmingUser)
    {
        this.confirmingUser = confirmingUser;
    }

    /**
     * 
     */
    public abstract void add(org.openuss.lecture.Department department);

    /**
     * 
     */
    public abstract void add(org.openuss.lecture.Institute institute);

    /**
     * 
     */
    public abstract void remove(org.openuss.lecture.Department department);

    /**
     * 
     */
    public abstract void remove(org.openuss.lecture.Institute institute);

    /**
     * Returns <code>true</code> if the argument is an Application instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Application))
        {
            return false;
        }
        final Application that = (Application)object;
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

}