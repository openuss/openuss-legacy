package org.openuss.security;

/**
 * 
 */
public abstract class MembershipBase
    implements Membership, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -9153719745588892287L;

    private java.lang.Long id;

    /**
     * @see org.openuss.security.Membership#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.security.Membership#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.util.List<org.openuss.security.Group> groups = new java.util.ArrayList<org.openuss.security.Group>();

    /**
     * 
     */
    public java.util.List<org.openuss.security.Group> getGroups()
    {
        return this.groups;
    }

    public void setGroups(java.util.List<org.openuss.security.Group> groups)
    {
        this.groups = groups;
    }

    private java.util.List<org.openuss.security.User> members = new java.util.ArrayList<org.openuss.security.User>();

    /**
     * 
     */
    public java.util.List<org.openuss.security.User> getMembers()
    {
        return this.members;
    }

    public void setMembers(java.util.List<org.openuss.security.User> members)
    {
        this.members = members;
    }

    private java.util.List<org.openuss.security.User> aspirants = new java.util.ArrayList<org.openuss.security.User>();

    /**
     * 
     */
    public java.util.List<org.openuss.security.User> getAspirants()
    {
        return this.aspirants;
    }

    public void setAspirants(java.util.List<org.openuss.security.User> aspirants)
    {
        this.aspirants = aspirants;
    }

    /**
     * Returns <code>true</code> if the argument is an Membership instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Membership))
        {
            return false;
        }
        final Membership that = (Membership)object;
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

	public static class Factory extends Membership.Factory {
		
		public Membership createMembership() {
			return new MembershipImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}