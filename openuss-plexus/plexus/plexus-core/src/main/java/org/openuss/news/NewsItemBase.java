package org.openuss.news;

/**
 * 
 */
public abstract class NewsItemBase
    implements NewsItem, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -5144577537263527275L;

    private java.lang.Long id;

    /**
     * @see org.openuss.news.NewsItem#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.news.NewsItem#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private org.openuss.news.NewsCategory category = org.openuss.news.NewsCategory.INSTITUTE;

    /**
     * @see org.openuss.news.NewsItem#getCategory()
     */
    public org.openuss.news.NewsCategory getCategory()
    {
        return this.category;
    }

    /**
     * @see org.openuss.news.NewsItem#setCategory(org.openuss.news.NewsCategory category)
     */
    public void setCategory(org.openuss.news.NewsCategory category)
    {
        this.category = category;
    }

    private java.lang.Long publisherIdentifier;

    /**
     * @see org.openuss.news.NewsItem#getPublisherIdentifier()
     */
    public java.lang.Long getPublisherIdentifier()
    {
        return this.publisherIdentifier;
    }

    /**
     * @see org.openuss.news.NewsItem#setPublisherIdentifier(java.lang.Long publisherIdentifier)
     */
    public void setPublisherIdentifier(java.lang.Long publisherIdentifier)
    {
        this.publisherIdentifier = publisherIdentifier;
    }

    private java.lang.String text;

    /**
     * @see org.openuss.news.NewsItem#getText()
     */
    public java.lang.String getText()
    {
        return this.text;
    }

    /**
     * @see org.openuss.news.NewsItem#setText(java.lang.String text)
     */
    public void setText(java.lang.String text)
    {
        this.text = text;
    }

    private java.lang.String publisherName;

    /**
     * @see org.openuss.news.NewsItem#getPublisherName()
     */
    public java.lang.String getPublisherName()
    {
        return this.publisherName;
    }

    /**
     * @see org.openuss.news.NewsItem#setPublisherName(java.lang.String publisherName)
     */
    public void setPublisherName(java.lang.String publisherName)
    {
        this.publisherName = publisherName;
    }

    private java.lang.String title;

    /**
     * @see org.openuss.news.NewsItem#getTitle()
     */
    public java.lang.String getTitle()
    {
        return this.title;
    }

    /**
     * @see org.openuss.news.NewsItem#setTitle(java.lang.String title)
     */
    public void setTitle(java.lang.String title)
    {
        this.title = title;
    }

    private java.util.Date publishDate;

    /**
     * @see org.openuss.news.NewsItem#getPublishDate()
     */
    public java.util.Date getPublishDate()
    {
        return this.publishDate;
    }

    /**
     * @see org.openuss.news.NewsItem#setPublishDate(java.util.Date publishDate)
     */
    public void setPublishDate(java.util.Date publishDate)
    {
        this.publishDate = publishDate;
    }

    private java.util.Date expireDate;

    /**
     * @see org.openuss.news.NewsItem#getExpireDate()
     */
    public java.util.Date getExpireDate()
    {
        return this.expireDate;
    }

    /**
     * @see org.openuss.news.NewsItem#setExpireDate(java.util.Date expireDate)
     */
    public void setExpireDate(java.util.Date expireDate)
    {
        this.expireDate = expireDate;
    }

    private java.lang.String author;

    /**
     * @see org.openuss.news.NewsItem#getAuthor()
     */
    public java.lang.String getAuthor()
    {
        return this.author;
    }

    /**
     * @see org.openuss.news.NewsItem#setAuthor(java.lang.String author)
     */
    public void setAuthor(java.lang.String author)
    {
        this.author = author;
    }

    private org.openuss.news.PublisherType publisherType;

    /**
     * @see org.openuss.news.NewsItem#getPublisherType()
     */
    public org.openuss.news.PublisherType getPublisherType()
    {
        return this.publisherType;
    }

    /**
     * @see org.openuss.news.NewsItem#setPublisherType(org.openuss.news.PublisherType publisherType)
     */
    public void setPublisherType(org.openuss.news.PublisherType publisherType)
    {
        this.publisherType = publisherType;
    }

    /**
     * 
     */
    public abstract boolean isValidExpireDate();

    /**
     * 
     */
    public abstract boolean isReleased();

    /**
     * 
     */
    public abstract boolean isExpired();

    /**
     * Returns <code>true</code> if the argument is an NewsItem instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof NewsItem))
        {
            return false;
        }
        final NewsItem that = (NewsItem)object;
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

	public static class Factory extends NewsItem.Factory {
		
		public NewsItem createNewsItem() {
			return new NewsItemImpl();
		}
	}
	
// HibernateEntity.vsl merge-point
}