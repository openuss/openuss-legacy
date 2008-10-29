package org.openuss.newsletter;

/**
 * 
 */
public abstract class NewsletterBase
    implements Newsletter, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -862867356782893688L;

    private java.lang.Long id;

    /**
     * @see org.openuss.newsletter.Newsletter#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.newsletter.Newsletter#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.newsletter.Newsletter#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.newsletter.Newsletter#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.lang.Long domainIdentifier;

    /**
     * @see org.openuss.newsletter.Newsletter#getDomainIdentifier()
     */
    public java.lang.Long getDomainIdentifier()
    {
        return this.domainIdentifier;
    }

    /**
     * @see org.openuss.newsletter.Newsletter#setDomainIdentifier(java.lang.Long domainIdentifier)
     */
    public void setDomainIdentifier(java.lang.Long domainIdentifier)
    {
        this.domainIdentifier = domainIdentifier;
    }

    private java.util.Set<org.openuss.newsletter.Subscriber> subscribers = new java.util.HashSet<org.openuss.newsletter.Subscriber>();

    /**
     * 
     */
    public java.util.Set<org.openuss.newsletter.Subscriber> getSubscribers()
    {
        return this.subscribers;
    }

    public void setSubscribers(java.util.Set<org.openuss.newsletter.Subscriber> subscribers)
    {
        this.subscribers = subscribers;
    }

    private java.util.Set<org.openuss.newsletter.Mail> mailings = new java.util.HashSet<org.openuss.newsletter.Mail>();

    /**
     * 
     */
    public java.util.Set<org.openuss.newsletter.Mail> getMailings()
    {
        return this.mailings;
    }

    public void setMailings(java.util.Set<org.openuss.newsletter.Mail> mailings)
    {
        this.mailings = mailings;
    }

    /**
     * Returns <code>true</code> if the argument is an Newsletter instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Newsletter))
        {
            return false;
        }
        final Newsletter that = (Newsletter)object;
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

	public static class Factory extends Newsletter.Factory {
		
		public Newsletter createNewsletter() {
			return new NewsletterImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}