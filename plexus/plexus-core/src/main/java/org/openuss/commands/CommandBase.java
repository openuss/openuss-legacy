package org.openuss.commands;

/**
 * 
 */
public abstract class CommandBase
    implements Command, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 199254251414002066L;

    private java.lang.Long id;

    /**
     * @see org.openuss.commands.Command#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.commands.Command#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.Long domainIdentifier;

    /**
     * @see org.openuss.commands.Command#getDomainIdentifier()
     */
    public java.lang.Long getDomainIdentifier()
    {
        return this.domainIdentifier;
    }

    /**
     * @see org.openuss.commands.Command#setDomainIdentifier(java.lang.Long domainIdentifier)
     */
    public void setDomainIdentifier(java.lang.Long domainIdentifier)
    {
        this.domainIdentifier = domainIdentifier;
    }

    private java.lang.String command;

    /**
     * @see org.openuss.commands.Command#getCommand()
     */
    public java.lang.String getCommand()
    {
        return this.command;
    }

    /**
     * @see org.openuss.commands.Command#setCommand(java.lang.String command)
     */
    public void setCommand(java.lang.String command)
    {
        this.command = command;
    }

    private org.openuss.commands.CommandState state;

    /**
     * @see org.openuss.commands.Command#getState()
     */
    public org.openuss.commands.CommandState getState()
    {
        return this.state;
    }

    /**
     * @see org.openuss.commands.Command#setState(org.openuss.commands.CommandState state)
     */
    public void setState(org.openuss.commands.CommandState state)
    {
        this.state = state;
    }

    private java.lang.String commandType;

    /**
     * @see org.openuss.commands.Command#getCommandType()
     */
    public java.lang.String getCommandType()
    {
        return this.commandType;
    }

    /**
     * @see org.openuss.commands.Command#setCommandType(java.lang.String commandType)
     */
    public void setCommandType(java.lang.String commandType)
    {
        this.commandType = commandType;
    }

    private java.util.Date startTime;

    /**
     * @see org.openuss.commands.Command#getStartTime()
     */
    public java.util.Date getStartTime()
    {
        return this.startTime;
    }

    /**
     * @see org.openuss.commands.Command#setStartTime(java.util.Date startTime)
     */
    public void setStartTime(java.util.Date startTime)
    {
        this.startTime = startTime;
    }

    private java.util.Date executionTime;

    /**
     * @see org.openuss.commands.Command#getExecutionTime()
     */
    public java.util.Date getExecutionTime()
    {
        return this.executionTime;
    }

    /**
     * @see org.openuss.commands.Command#setExecutionTime(java.util.Date executionTime)
     */
    public void setExecutionTime(java.util.Date executionTime)
    {
        this.executionTime = executionTime;
    }

    /**
     * Returns <code>true</code> if the argument is an Command instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Command))
        {
            return false;
        }
        final Command that = (Command)object;
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