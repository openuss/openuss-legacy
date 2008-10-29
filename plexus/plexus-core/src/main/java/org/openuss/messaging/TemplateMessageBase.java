package org.openuss.messaging;

/**
 * 
 */
public abstract class TemplateMessageBase
    extends org.openuss.messaging.MessageImpl
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -5732723391918392147L;

    private java.lang.String template;

    /**
     * @see org.openuss.messaging.TemplateMessage#getTemplate()
     */
    public java.lang.String getTemplate()
    {
        return this.template;
    }

    /**
     * @see org.openuss.messaging.TemplateMessage#setTemplate(java.lang.String template)
     */
    public void setTemplate(java.lang.String template)
    {
        this.template = template;
    }

    private java.util.Set<org.openuss.messaging.TemplateParameter> parameters = new java.util.HashSet<org.openuss.messaging.TemplateParameter>();

    /**
     * 
     */
    public java.util.Set<org.openuss.messaging.TemplateParameter> getParameters()
    {
        return this.parameters;
    }

    public void setParameters(java.util.Set<org.openuss.messaging.TemplateParameter> parameters)
    {
        this.parameters = parameters;
    }

    /**
     * 
     */
    public abstract void addParameter(java.lang.String name, java.lang.String value);

    /**
     * 
     */
    public abstract void addParameter(org.openuss.messaging.TemplateParameter parameter);

    /**
     * 
     */
    public abstract void removeParameter(org.openuss.messaging.TemplateParameter parameter);

    /**
     * <p>
     * Add a Map<String,String> parameter map as parameter entities to
     * the template message.
     * </p>
     * <p>
     * @Param parameters - Map<String,String>
     * </p>
     */
    public abstract void addParameters(java.util.Map parameters);

    /**
     * 
     */
    public abstract java.util.Map getParameterMap();

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