package org.openuss.discussion;

/**
 * 
 */
public abstract class ForumWatchBase
    implements ForumWatch, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -3359760830304783682L;

	private org.openuss.discussion.ForumWatchPK forumWatchPk;
	
	public org.openuss.discussion.ForumWatchPK getForumWatchPk() {
        return this.forumWatchPk;
    }

    public void setForumWatchPk(org.openuss.discussion.ForumWatchPK forumWatchPk) {
        this.forumWatchPk = forumWatchPk;
    }

    /**
     * Returns <code>true</code> if the argument is an ForumWatch instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof ForumWatch))
        {
            return false;
        }
        final ForumWatch that = (ForumWatch)object;
        if (this.forumWatchPk == null || that.getForumWatchPk() == null || !this.forumWatchPk.equals(that.getForumWatchPk()))
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
        hashCode = 29 * hashCode + (forumWatchPk == null ? 0 : forumWatchPk.hashCode());

        return hashCode;
    }


}