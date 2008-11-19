package org.openuss.paperSubmission;

/**
 * <p>
 * A PaperSubmission symbolizes a submission by a user of one or
 * many files. It is attached to a courseMember and is for a exam.
 * </p>
 * <p>
 * @author  Projektseminar WS 07/08, Team Collaboration
 * </p>
 */
public abstract class PaperSubmissionBase
    implements PaperSubmission, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 4356967411461997457L;

    private java.lang.Long id;

    /**
     * @see org.openuss.paperSubmission.PaperSubmission#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmission#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.util.Date deliverDate;

    /**
     * @see org.openuss.paperSubmission.PaperSubmission#getDeliverDate()
     */
    public java.util.Date getDeliverDate()
    {
        return this.deliverDate;
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmission#setDeliverDate(java.util.Date deliverDate)
     */
    public void setDeliverDate(java.util.Date deliverDate)
    {
        this.deliverDate = deliverDate;
    }

    private java.lang.String comment;

    /**
     * @see org.openuss.paperSubmission.PaperSubmission#getComment()
     */
    public java.lang.String getComment()
    {
        return this.comment;
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmission#setComment(java.lang.String comment)
     */
    public void setComment(java.lang.String comment)
    {
        this.comment = comment;
    }

    private org.openuss.paperSubmission.Exam exam;

    /**
     * 
     */
    public org.openuss.paperSubmission.Exam getExam()
    {
        return this.exam;
    }

    public void setExam(org.openuss.paperSubmission.Exam exam)
    {
        this.exam = exam;
    }

    private org.openuss.security.User sender;

    /**
     * 
     */
    public org.openuss.security.User getSender()
    {
        return this.sender;
    }

    public void setSender(org.openuss.security.User sender)
    {
        this.sender = sender;
    }

    /**
     * Returns <code>true</code> if the argument is an PaperSubmission instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof PaperSubmission))
        {
            return false;
        }
        final PaperSubmission that = (PaperSubmission)object;
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

	public static class Factory extends PaperSubmission.Factory {
		
		public PaperSubmission createPaperSubmission() {
			return new PaperSubmissionImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}