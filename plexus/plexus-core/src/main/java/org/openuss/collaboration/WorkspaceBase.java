package org.openuss.collaboration;

/**
 * <p>
 * A workspace is a specific collaboration workspace that is made
 * linked to an existing course  defined period of time (like a
 * specific semester) by one institute. A Course can have as much
 * workspaces as they want.
 * </p>
 * <p>
 * @author  Projektseminar WS 07/08, Team Collaboration
 * </p>
 */
public abstract class WorkspaceBase
    implements Workspace, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -1324752243550544754L;

    private java.lang.Long id;

    /**
     * @see org.openuss.collaboration.Workspace#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.collaboration.Workspace#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.Long domainId;

    /**
     * @see org.openuss.collaboration.Workspace#getDomainId()
     */
    public java.lang.Long getDomainId()
    {
        return this.domainId;
    }

    /**
     * @see org.openuss.collaboration.Workspace#setDomainId(java.lang.Long domainId)
     */
    public void setDomainId(java.lang.Long domainId)
    {
        this.domainId = domainId;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.collaboration.Workspace#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.collaboration.Workspace#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.util.Set<org.openuss.security.User> user = new java.util.HashSet<org.openuss.security.User>();

    /**
     * 
     */
    public java.util.Set<org.openuss.security.User> getUser()
    {
        return this.user;
    }

    public void setUser(java.util.Set<org.openuss.security.User> user)
    {
        this.user = user;
    }

    /**
     * Returns <code>true</code> if the argument is an Workspace instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Workspace))
        {
            return false;
        }
        final Workspace that = (Workspace)object;
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