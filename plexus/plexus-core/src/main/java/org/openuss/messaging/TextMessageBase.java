package org.openuss.messaging;

/**
 * 
 */
public abstract class TextMessageBase
    extends org.openuss.messaging.MessageImpl
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -3289330900571876680L;

    private java.lang.String text;

    /**
     * @see org.openuss.messaging.TextMessage#getText()
     */
    public java.lang.String getText()
    {
        return this.text;
    }

    /**
     * @see org.openuss.messaging.TextMessage#setText(java.lang.String text)
     */
    public void setText(java.lang.String text)
    {
        this.text = text;
    }

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.messaging.MessageImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.messaging.Message#equals(Object)
     */
    public boolean equals(Object object)
    {
        return super.equals(object);
    }

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.messaging.MessageImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.messaging.Message#hashCode()
     */
    public int hashCode()
    {
        return super.hashCode();
    }

}