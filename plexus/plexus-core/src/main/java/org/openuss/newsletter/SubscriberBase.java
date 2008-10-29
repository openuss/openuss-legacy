package org.openuss.newsletter;

/**
 * 
 */
public abstract class SubscriberBase
    implements Subscriber, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -7222328325065408963L;

	private org.openuss.newsletter.SubscriberPK subscriberPk;
	
	public org.openuss.newsletter.SubscriberPK getSubscriberPk() {
        return this.subscriberPk;
    }

    public void setSubscriberPk(org.openuss.newsletter.SubscriberPK subscriberPk) {
        this.subscriberPk = subscriberPk;
    }

    private boolean blocked = false;

    /**
     * @see org.openuss.newsletter.Subscriber#isBlocked()
     */
    public boolean isBlocked()
    {
        return this.blocked;
    }

    /**
     * @see org.openuss.newsletter.Subscriber#setBlocked(boolean blocked)
     */
    public void setBlocked(boolean blocked)
    {
        this.blocked = blocked;
    }

    /**
     * Returns <code>true</code> if the argument is an Subscriber instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Subscriber))
        {
            return false;
        }
        final Subscriber that = (Subscriber)object;
        if (this.subscriberPk == null || that.getSubscriberPk() == null || !this.subscriberPk.equals(that.getSubscriberPk()))
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
        hashCode = 29 * hashCode + (subscriberPk == null ? 0 : subscriberPk.hashCode());

        return hashCode;
    }

	public static class Factory extends Subscriber.Factory {
		
		public Subscriber createSubscriber() {
			return new SubscriberImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}