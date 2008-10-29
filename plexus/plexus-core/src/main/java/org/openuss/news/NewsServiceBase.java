package org.openuss.news;

/**
 * <p>
 * Spring Service base class for <code>org.openuss.news.NewsService</code>,
 * provides access to all services and entities referenced by this service.
 * </p>
 *
 * @see org.openuss.news.NewsService
 */
public abstract class NewsServiceBase
    implements org.openuss.news.NewsService
{

    private org.openuss.documents.DocumentService documentService;

    /**
     * Sets the reference to <code>documentService</code>.
     */
    public void setDocumentService(org.openuss.documents.DocumentService documentService)
    {
        this.documentService = documentService;
    }

    /**
     * Gets the reference to <code>documentService</code>.
     */
    protected org.openuss.documents.DocumentService getDocumentService()
    {
        return this.documentService;
    }

    private org.openuss.security.SecurityService securityService;

    /**
     * Sets the reference to <code>securityService</code>.
     */
    public void setSecurityService(org.openuss.security.SecurityService securityService)
    {
        this.securityService = securityService;
    }

    /**
     * Gets the reference to <code>securityService</code>.
     */
    protected org.openuss.security.SecurityService getSecurityService()
    {
        return this.securityService;
    }

    private org.openuss.news.NewsItemDao newsItemDao;

    /**
     * Sets the reference to <code>newsItem</code>'s DAO.
     */
    public void setNewsItemDao(org.openuss.news.NewsItemDao newsItemDao)
    {
        this.newsItemDao = newsItemDao;
    }

    /**
     * Gets the reference to <code>newsItem</code>'s DAO.
     */
    protected org.openuss.news.NewsItemDao getNewsItemDao()
    {
        return this.newsItemDao;
    }

    /**
     * @see org.openuss.news.NewsService#getNewsItem(org.openuss.news.NewsItemInfo)
     */
    public org.openuss.news.NewsItemInfo getNewsItem(org.openuss.news.NewsItemInfo item)
    {
        try
        {
            return this.handleGetNewsItem(item);
        }
        catch (Throwable th)
        {
            throw new org.openuss.news.NewsServiceException(
                "Error performing 'org.openuss.news.NewsService.getNewsItem(org.openuss.news.NewsItemInfo item)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getNewsItem(org.openuss.news.NewsItemInfo)}
      */
    protected abstract org.openuss.news.NewsItemInfo handleGetNewsItem(org.openuss.news.NewsItemInfo item)
        throws java.lang.Exception;

    /**
     * @see org.openuss.news.NewsService#saveNewsItem(org.openuss.news.NewsItemInfo)
     */
    public void saveNewsItem(org.openuss.news.NewsItemInfo item)
    {
        try
        {
            this.handleSaveNewsItem(item);
        }
        catch (Throwable th)
        {
            throw new org.openuss.news.NewsServiceException(
                "Error performing 'org.openuss.news.NewsService.saveNewsItem(org.openuss.news.NewsItemInfo item)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #saveNewsItem(org.openuss.news.NewsItemInfo)}
      */
    protected abstract void handleSaveNewsItem(org.openuss.news.NewsItemInfo item)
        throws java.lang.Exception;

    /**
     * @see org.openuss.news.NewsService#deleteNewsItem(org.openuss.news.NewsItemInfo)
     */
    public void deleteNewsItem(org.openuss.news.NewsItemInfo item)
    {
        try
        {
            this.handleDeleteNewsItem(item);
        }
        catch (Throwable th)
        {
            throw new org.openuss.news.NewsServiceException(
                "Error performing 'org.openuss.news.NewsService.deleteNewsItem(org.openuss.news.NewsItemInfo item)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #deleteNewsItem(org.openuss.news.NewsItemInfo)}
      */
    protected abstract void handleDeleteNewsItem(org.openuss.news.NewsItemInfo item)
        throws java.lang.Exception;

    /**
     * @see org.openuss.news.NewsService#getNewsItems(org.openuss.foundation.DomainObject)
     */
    public java.util.List getNewsItems(org.openuss.foundation.DomainObject publisher)
    {
        try
        {
            return this.handleGetNewsItems(publisher);
        }
        catch (Throwable th)
        {
            throw new org.openuss.news.NewsServiceException(
                "Error performing 'org.openuss.news.NewsService.getNewsItems(org.openuss.foundation.DomainObject publisher)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getNewsItems(org.openuss.foundation.DomainObject)}
      */
    protected abstract java.util.List handleGetNewsItems(org.openuss.foundation.DomainObject publisher)
        throws java.lang.Exception;

    /**
     * @see org.openuss.news.NewsService#getCurrentNewsItems(org.openuss.foundation.DomainObject, java.lang.Integer)
     */
    public java.util.List getCurrentNewsItems(org.openuss.foundation.DomainObject publisher, java.lang.Integer count)
    {
        try
        {
            return this.handleGetCurrentNewsItems(publisher, count);
        }
        catch (Throwable th)
        {
            throw new org.openuss.news.NewsServiceException(
                "Error performing 'org.openuss.news.NewsService.getCurrentNewsItems(org.openuss.foundation.DomainObject publisher, java.lang.Integer count)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getCurrentNewsItems(org.openuss.foundation.DomainObject, java.lang.Integer)}
      */
    protected abstract java.util.List handleGetCurrentNewsItems(org.openuss.foundation.DomainObject publisher, java.lang.Integer count)
        throws java.lang.Exception;

    /**
     * @see org.openuss.news.NewsService#getCurrentNewsItems(org.openuss.news.NewsCategory, java.lang.Integer)
     */
    public java.util.List getCurrentNewsItems(org.openuss.news.NewsCategory category, java.lang.Integer count)
    {
        try
        {
            return this.handleGetCurrentNewsItems(category, count);
        }
        catch (Throwable th)
        {
            throw new org.openuss.news.NewsServiceException(
                "Error performing 'org.openuss.news.NewsService.getCurrentNewsItems(org.openuss.news.NewsCategory category, java.lang.Integer count)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getCurrentNewsItems(org.openuss.news.NewsCategory, java.lang.Integer)}
      */
    protected abstract java.util.List handleGetCurrentNewsItems(org.openuss.news.NewsCategory category, java.lang.Integer count)
        throws java.lang.Exception;

    /**
     * @see org.openuss.news.NewsService#getPublishedNewsItems(org.openuss.foundation.DomainObject, java.lang.Integer, java.lang.Integer)
     */
    public java.util.List getPublishedNewsItems(org.openuss.foundation.DomainObject publisher, java.lang.Integer firstResult, java.lang.Integer count)
    {
        try
        {
            return this.handleGetPublishedNewsItems(publisher, firstResult, count);
        }
        catch (Throwable th)
        {
            throw new org.openuss.news.NewsServiceException(
                "Error performing 'org.openuss.news.NewsService.getPublishedNewsItems(org.openuss.foundation.DomainObject publisher, java.lang.Integer firstResult, java.lang.Integer count)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getPublishedNewsItems(org.openuss.foundation.DomainObject, java.lang.Integer, java.lang.Integer)}
      */
    protected abstract java.util.List handleGetPublishedNewsItems(org.openuss.foundation.DomainObject publisher, java.lang.Integer firstResult, java.lang.Integer count)
        throws java.lang.Exception;

    /**
     * @see org.openuss.news.NewsService#getPublishedNewsItems(org.openuss.news.NewsCategory, java.lang.Integer, java.lang.Integer)
     */
    public java.util.List getPublishedNewsItems(org.openuss.news.NewsCategory category, java.lang.Integer firstResult, java.lang.Integer count)
    {
        try
        {
            return this.handleGetPublishedNewsItems(category, firstResult, count);
        }
        catch (Throwable th)
        {
            throw new org.openuss.news.NewsServiceException(
                "Error performing 'org.openuss.news.NewsService.getPublishedNewsItems(org.openuss.news.NewsCategory category, java.lang.Integer firstResult, java.lang.Integer count)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getPublishedNewsItems(org.openuss.news.NewsCategory, java.lang.Integer, java.lang.Integer)}
      */
    protected abstract java.util.List handleGetPublishedNewsItems(org.openuss.news.NewsCategory category, java.lang.Integer firstResult, java.lang.Integer count)
        throws java.lang.Exception;

    /**
     * @see org.openuss.news.NewsService#getPublishedNewsItemsCount(org.openuss.foundation.DomainObject)
     */
    public long getPublishedNewsItemsCount(org.openuss.foundation.DomainObject publisher)
    {
        try
        {
            return this.handleGetPublishedNewsItemsCount(publisher);
        }
        catch (Throwable th)
        {
            throw new org.openuss.news.NewsServiceException(
                "Error performing 'org.openuss.news.NewsService.getPublishedNewsItemsCount(org.openuss.foundation.DomainObject publisher)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getPublishedNewsItemsCount(org.openuss.foundation.DomainObject)}
      */
    protected abstract long handleGetPublishedNewsItemsCount(org.openuss.foundation.DomainObject publisher)
        throws java.lang.Exception;

    /**
     * @see org.openuss.news.NewsService#getPublishedNewsItemsCount(org.openuss.news.NewsCategory)
     */
    public long getPublishedNewsItemsCount(org.openuss.news.NewsCategory category)
    {
        try
        {
            return this.handleGetPublishedNewsItemsCount(category);
        }
        catch (Throwable th)
        {
            throw new org.openuss.news.NewsServiceException(
                "Error performing 'org.openuss.news.NewsService.getPublishedNewsItemsCount(org.openuss.news.NewsCategory category)' --> " + th,
                th);
        }
    }

     /**
      * Performs the core logic for {@link #getPublishedNewsItemsCount(org.openuss.news.NewsCategory)}
      */
    protected abstract long handleGetPublishedNewsItemsCount(org.openuss.news.NewsCategory category)
        throws java.lang.Exception;

    /**
     * Gets the current <code>principal</code> if one has been set,
     * otherwise returns <code>null</code>.
     *
     * @return the current principal
     */
    protected java.security.Principal getPrincipal()
    {
        return org.andromda.spring.PrincipalStore.get();
    }
}