package org.openuss.paperSubmission;

/**
 * Exams can have many paperSubmissions and are attached to a
 * course.
 * The have a name, a deadline and a description
 * @author  Projektseminar WS 07/08, Team Collaboration
 */
public abstract class ExamBase
    implements Exam, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -427177556107018781L;

    private java.lang.Long id;

    /**
     * @see org.openuss.paperSubmission.Exam#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.paperSubmission.Exam#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.Long domainId;

    /**
     * @see org.openuss.paperSubmission.Exam#getDomainId()
     */
    public java.lang.Long getDomainId()
    {
        return this.domainId;
    }

    /**
     * @see org.openuss.paperSubmission.Exam#setDomainId(java.lang.Long domainId)
     */
    public void setDomainId(java.lang.Long domainId)
    {
        this.domainId = domainId;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.paperSubmission.Exam#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.paperSubmission.Exam#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.util.Date deadline;

    /**
     * @see org.openuss.paperSubmission.Exam#getDeadline()
     */
    public java.util.Date getDeadline()
    {
        return this.deadline;
    }

    /**
     * @see org.openuss.paperSubmission.Exam#setDeadline(java.util.Date deadline)
     */
    public void setDeadline(java.util.Date deadline)
    {
        this.deadline = deadline;
    }

    private java.lang.String description;

    /**
     * @see org.openuss.paperSubmission.Exam#getDescription()
     */
    public java.lang.String getDescription()
    {
        return this.description;
    }

    /**
     * @see org.openuss.paperSubmission.Exam#setDescription(java.lang.String description)
     */
    public void setDescription(java.lang.String description)
    {
        this.description = description;
    }

    private java.util.Collection<org.openuss.paperSubmission.PaperSubmission> papersubmissions = new java.util.ArrayList<org.openuss.paperSubmission.PaperSubmission>();

    /**
     * 
     */
    public java.util.Collection<org.openuss.paperSubmission.PaperSubmission> getPapersubmissions()
    {
        return this.papersubmissions;
    }

    public void setPapersubmissions(java.util.Collection<org.openuss.paperSubmission.PaperSubmission> papersubmissions)
    {
        this.papersubmissions = papersubmissions;
    }

    /**
     * Returns <code>true</code> if the argument is an Exam instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Exam))
        {
            return false;
        }
        final Exam that = (Exam)object;
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