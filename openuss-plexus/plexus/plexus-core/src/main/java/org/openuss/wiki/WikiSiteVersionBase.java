package org.openuss.wiki;

/**
 * <p>
 * a WikiSiteVersion is related to a WikiSite and to a
 * CourseMember.
 * </p>
 * <p>
 * It stores the text of the site, the creationDate and a note
 * </p>
 * <p>
 * @author  Projektseminar WS 07/08, Team Collaboration
 * </p>
 */
public abstract class WikiSiteVersionBase
    implements WikiSiteVersion, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -5820715056440081828L;

    private java.lang.Long id;

    /**
     * @see org.openuss.wiki.WikiSiteVersion#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersion#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String text;

    /**
     * @see org.openuss.wiki.WikiSiteVersion#getText()
     */
    public java.lang.String getText()
    {
        return this.text;
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersion#setText(java.lang.String text)
     */
    public void setText(java.lang.String text)
    {
        this.text = text;
    }

    private java.util.Date creationDate;

    /**
     * @see org.openuss.wiki.WikiSiteVersion#getCreationDate()
     */
    public java.util.Date getCreationDate()
    {
        return this.creationDate;
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersion#setCreationDate(java.util.Date creationDate)
     */
    public void setCreationDate(java.util.Date creationDate)
    {
        this.creationDate = creationDate;
    }

    private java.lang.String note;

    /**
     * @see org.openuss.wiki.WikiSiteVersion#getNote()
     */
    public java.lang.String getNote()
    {
        return this.note;
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersion#setNote(java.lang.String note)
     */
    public void setNote(java.lang.String note)
    {
        this.note = note;
    }

    private java.lang.Boolean stable = java.lang.Boolean.valueOf(false);

    /**
     * @see org.openuss.wiki.WikiSiteVersion#getStable()
     */
    public java.lang.Boolean getStable()
    {
        return this.stable;
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersion#setStable(java.lang.Boolean stable)
     */
    public void setStable(java.lang.Boolean stable)
    {
        this.stable = stable;
    }

    private org.openuss.wiki.WikiSite wikiSite;

    /**
     * 
     */
    public org.openuss.wiki.WikiSite getWikiSite()
    {
        return this.wikiSite;
    }

    public void setWikiSite(org.openuss.wiki.WikiSite wikiSite)
    {
        this.wikiSite = wikiSite;
    }

    private org.openuss.security.User author;

    /**
     * 
     */
    public org.openuss.security.User getAuthor()
    {
        return this.author;
    }

    public void setAuthor(org.openuss.security.User author)
    {
        this.author = author;
    }

    /**
     * Returns <code>true</code> if the argument is an WikiSiteVersion instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof WikiSiteVersion))
        {
            return false;
        }
        final WikiSiteVersion that = (WikiSiteVersion)object;
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