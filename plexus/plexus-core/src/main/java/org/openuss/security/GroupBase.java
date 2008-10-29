package org.openuss.security;

/**
 * 
 */
public abstract class GroupBase
    extends org.openuss.security.AuthorityImpl
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 7396282439635192523L;

    private java.lang.String label;

    /**
     * @see org.openuss.security.Group#getLabel()
     */
    public java.lang.String getLabel()
    {
        return this.label;
    }

    /**
     * @see org.openuss.security.Group#setLabel(java.lang.String label)
     */
    public void setLabel(java.lang.String label)
    {
        this.label = label;
    }

    private org.openuss.security.GroupType groupType = org.openuss.security.GroupType.UNDEFINED;

    /**
     * @see org.openuss.security.Group#getGroupType()
     */
    public org.openuss.security.GroupType getGroupType()
    {
        return this.groupType;
    }

    /**
     * @see org.openuss.security.Group#setGroupType(org.openuss.security.GroupType groupType)
     */
    public void setGroupType(org.openuss.security.GroupType groupType)
    {
        this.groupType = groupType;
    }

    private java.lang.String password;

    /**
     * @see org.openuss.security.Group#getPassword()
     */
    public java.lang.String getPassword()
    {
        return this.password;
    }

    /**
     * @see org.openuss.security.Group#setPassword(java.lang.String password)
     */
    public void setPassword(java.lang.String password)
    {
        this.password = password;
    }

    private java.util.List<org.openuss.security.Authority> members = new java.util.ArrayList<org.openuss.security.Authority>();

    /**
     * 
     */
    public java.util.List<org.openuss.security.Authority> getMembers()
    {
        return this.members;
    }

    public void setMembers(java.util.List<org.openuss.security.Authority> members)
    {
        this.members = members;
    }

    /**
     * <p>
     * Removes the given user from the user list of the authority.
     * </p>
     */
    public abstract void removeMember(org.openuss.security.Authority authority);

    /**
     * <p>
     * Add the given user to the user list of the authority.
     * </p>
     */
    public abstract void addMember(org.openuss.security.Authority authority);

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.security.AuthorityImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.security.Authority#equals(Object)
     */
    public boolean equals(Object object)
    {
        return super.equals(object);
    }

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.security.AuthorityImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.security.Authority#hashCode()
     */
    public int hashCode()
    {
        return super.hashCode();
    }

	public static class Factory extends Group.Factory {
		
		public Group createGroup() {
			return new GroupImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}