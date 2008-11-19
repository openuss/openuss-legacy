package org.openuss.security;

/**
 * 
 */
public abstract class AuthorityBase
    implements Authority, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 4124692126161138124L;

    private java.lang.Long id;

    /**
     * @see org.openuss.security.Authority#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.security.Authority#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.security.Authority#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.security.Authority#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
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

    /**
     * <p>
     * Get all groups where this groups is a direct or indirect member.
     * </p>
     */
    public abstract java.util.Set getGrantedGroups();

    /**
     * <p>
     * Adds the given role to the users authority list.
     * </p>
     */
    public abstract void addGroup(org.openuss.security.Group group);

    /**
     * <p>
     * Removes the given authority from the users authority list.
     * </p>
     */
    public abstract void removeGroup(org.openuss.security.Group group);

    /**
     * Returns <code>true</code> if the argument is an Authority instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Authority))
        {
            return false;
        }
        final Authority that = (Authority)object;
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

	
// HibernateEntity.vsl merge-point
}