package org.openuss.commands;

/**
 * 
 */
public abstract class LastProcessedCommandBase
    implements LastProcessedCommand, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -5969753665225915140L;

    private java.lang.Long id;

    /**
     * @see org.openuss.commands.LastProcessedCommand#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.commands.LastProcessedCommand#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private org.openuss.commands.Command last;

    /**
     * 
     */
    public org.openuss.commands.Command getLast()
    {
        return this.last;
    }

    public void setLast(org.openuss.commands.Command last)
    {
        this.last = last;
    }

    /**
     * Returns <code>true</code> if the argument is an LastProcessedCommand instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof LastProcessedCommand))
        {
            return false;
        }
        final LastProcessedCommand that = (LastProcessedCommand)object;
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