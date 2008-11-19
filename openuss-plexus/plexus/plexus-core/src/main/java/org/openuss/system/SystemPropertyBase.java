package org.openuss.system;

/**
 * 
 */
public abstract class SystemPropertyBase
    implements SystemProperty, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 1792403697081230379L;

    private java.lang.Long id;

    /**
     * @see org.openuss.system.SystemProperty#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.system.SystemProperty#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.system.SystemProperty#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.system.SystemProperty#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.lang.String value;

    /**
     * @see org.openuss.system.SystemProperty#getValue()
     */
    public java.lang.String getValue()
    {
        return this.value;
    }

    /**
     * @see org.openuss.system.SystemProperty#setValue(java.lang.String value)
     */
    public void setValue(java.lang.String value)
    {
        this.value = value;
    }

    /**
     * Returns <code>true</code> if the argument is an SystemProperty instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof SystemProperty))
        {
            return false;
        }
        final SystemProperty that = (SystemProperty)object;
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