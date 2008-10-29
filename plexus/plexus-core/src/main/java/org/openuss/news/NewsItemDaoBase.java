package org.openuss.news;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.news.NewsItem</code>.
 * </p>
 *
 * @see org.openuss.news.NewsItem
 */
public abstract class NewsItemDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.news.NewsItemDao
{


    /**
     * @see org.openuss.news.NewsItemDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "NewsItem.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.news.NewsItemImpl.class, id);
        return transformEntity(transform, (org.openuss.news.NewsItem)entity);
    }

    /**
     * @see org.openuss.news.NewsItemDao#load(java.lang.Long)
     */
    public org.openuss.news.NewsItem load(java.lang.Long id)
    {
        return (org.openuss.news.NewsItem)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.news.NewsItemDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.news.NewsItem> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.news.NewsItemDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.news.NewsItemImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.news.NewsItemDao#create(org.openuss.news.NewsItem)
     */
    public org.openuss.news.NewsItem create(org.openuss.news.NewsItem newsItem)
    {
        return (org.openuss.news.NewsItem)this.create(TRANSFORM_NONE, newsItem);
    }

    /**
     * @see org.openuss.news.NewsItemDao#create(int transform, org.openuss.news.NewsItem)
     */
    public java.lang.Object create(final int transform, final org.openuss.news.NewsItem newsItem)
    {
        if (newsItem == null)
        {
            throw new IllegalArgumentException(
                "NewsItem.create - 'newsItem' can not be null");
        }
        this.getHibernateTemplate().save(newsItem);
        return this.transformEntity(transform, newsItem);
    }

    /**
     * @see org.openuss.news.NewsItemDao#create(java.util.Collection<org.openuss.news.NewsItem>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.news.NewsItem> create(final java.util.Collection<org.openuss.news.NewsItem> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.news.NewsItemDao#create(int, java.util.Collection<org.openuss.news.NewsItem>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.news.NewsItem> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "NewsItem.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.news.NewsItem)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.news.NewsItemDao#update(org.openuss.news.NewsItem)
     */
    public void update(org.openuss.news.NewsItem newsItem)
    {
        if (newsItem == null)
        {
            throw new IllegalArgumentException(
                "NewsItem.update - 'newsItem' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(newsItem);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(newsItem);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.news.NewsItemDao#update(java.util.Collection<org.openuss.news.NewsItem>)
     */
    public void update(final java.util.Collection<org.openuss.news.NewsItem> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "NewsItem.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.news.NewsItem)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.news.NewsItemDao#remove(org.openuss.news.NewsItem)
     */
    public void remove(org.openuss.news.NewsItem newsItem)
    {
        if (newsItem == null)
        {
            throw new IllegalArgumentException(
                "NewsItem.remove - 'newsItem' can not be null");
        }
        this.getHibernateTemplate().delete(newsItem);
    }

    /**
     * @see org.openuss.news.NewsItemDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "NewsItem.remove - 'id can not be null");
        }
        org.openuss.news.NewsItem entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.news.NewsItemDao#remove(java.util.Collection<org.openuss.news.NewsItem>)
     */
    public void remove(java.util.Collection<org.openuss.news.NewsItem> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "NewsItem.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.news.NewsItemDao#findByCriteria(org.openuss.news.NewsCriteria)
     */
    public java.util.List findByCriteria(org.openuss.news.NewsCriteria criteria)
    {
        return this.findByCriteria(TRANSFORM_NONE, criteria);
    }

    /**
     * @see org.openuss.news.NewsItemDao#findByCriteria(int, java.lang.String, org.openuss.news.NewsCriteria)
     */
    public java.util.List findByCriteria(final int transform, final org.openuss.news.NewsCriteria criteria)
    {
        try
        {
            org.andromda.spring.CriteriaSearch criteriaSearch = new org.andromda.spring.CriteriaSearch(super.getSession(false), org.openuss.news.NewsItemImpl.class);
            criteriaSearch.getConfiguration().setFirstResult(criteria.getFirstResult());
            criteriaSearch.getConfiguration().setFetchSize(criteria.getFetchSize());
            criteriaSearch.getConfiguration().setMaximumResultSize(criteria.getMaximumResultSize());
            org.andromda.spring.CriteriaSearchParameter parameter1 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getPublisherIdentifier(),
                    "publisherIdentifier");
            criteriaSearch.addParameter(parameter1);
            org.andromda.spring.CriteriaSearchParameter parameter2 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getCategory(),
                    "category");
            criteriaSearch.addParameter(parameter2);
            org.andromda.spring.CriteriaSearchParameter parameter3 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getPublishDate(),
                    "publishDate", org.andromda.spring.CriteriaSearchParameter.LESS_THAN_OR_EQUAL_COMPARATOR);
            parameter3.setOrderDirection(org.andromda.spring.CriteriaSearchParameter.ORDER_DESC);
            parameter3.setOrderRelevance(-1);
            criteriaSearch.addParameter(parameter3);
            org.andromda.spring.CriteriaSearchParameter parameter4 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getExpireDate(),
                    "expireDate", true, org.andromda.spring.CriteriaSearchParameter.GREATER_THAN_COMPARATOR);
            criteriaSearch.addParameter(parameter4);
            java.util.List results = criteriaSearch.executeAsList();
            transformEntities(transform, results);
            return results;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.news.NewsItemDao#findByCategory(org.openuss.news.NewsCategory)
     */
    public java.util.List findByCategory(org.openuss.news.NewsCategory category)
    {
        return this.findByCategory(TRANSFORM_NONE, category);
    }

    /**
     * @see org.openuss.news.NewsItemDao#findByCategory(java.lang.String, org.openuss.news.NewsCategory)
     */
    public java.util.List findByCategory(final java.lang.String queryString, final org.openuss.news.NewsCategory category)
    {
        return this.findByCategory(TRANSFORM_NONE, queryString, category);
    }

    /**
     * @see org.openuss.news.NewsItemDao#findByCategory(int, org.openuss.news.NewsCategory)
     */
    public java.util.List findByCategory(final int transform, final org.openuss.news.NewsCategory category)
    {
        return this.findByCategory(transform, "from org.openuss.news.NewsItem as newsItem where newsItem.category = ?", category);
    }

    /**
     * @see org.openuss.news.NewsItemDao#findByCategory(int, java.lang.String, org.openuss.news.NewsCategory)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByCategory(final int transform, final java.lang.String queryString, final org.openuss.news.NewsCategory category)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, category.getValue());
            java.util.List results = queryObject.list();
            transformEntities(transform, results);
            return results;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.news.NewsItemDao#findByPublisher(java.lang.Long)
     */
    public java.util.List findByPublisher(java.lang.Long publisherIdentifier)
    {
        return this.findByPublisher(TRANSFORM_NONE, publisherIdentifier);
    }

    /**
     * @see org.openuss.news.NewsItemDao#findByPublisher(java.lang.String, java.lang.Long)
     */
    public java.util.List findByPublisher(final java.lang.String queryString, final java.lang.Long publisherIdentifier)
    {
        return this.findByPublisher(TRANSFORM_NONE, queryString, publisherIdentifier);
    }

    /**
     * @see org.openuss.news.NewsItemDao#findByPublisher(int, java.lang.Long)
     */
    public java.util.List findByPublisher(final int transform, final java.lang.Long publisherIdentifier)
    {
        return this.findByPublisher(transform, "from org.openuss.news.NewsItem as newsItem where newsItem.publisherIdentifier = ?", publisherIdentifier);
    }

    /**
     * @see org.openuss.news.NewsItemDao#findByPublisher(int, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByPublisher(final int transform, final java.lang.String queryString, final java.lang.Long publisherIdentifier)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, publisherIdentifier);
            java.util.List results = queryObject.list();
            transformEntities(transform, results);
            return results;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.news.NewsItemDao#countByCategory(org.openuss.news.NewsCategory)
     */
    public long countByCategory(final org.openuss.news.NewsCategory category)
    {
        try
        {
            return this.handleCountByCategory(category);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.news.NewsItemDao.countByCategory(org.openuss.news.NewsCategory category)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #countByCategory(org.openuss.news.NewsCategory)}
      */
    protected abstract long handleCountByCategory(org.openuss.news.NewsCategory category)
        throws java.lang.Exception;

    /**
     * @see org.openuss.news.NewsItemDao#countByPublisher(java.lang.Long)
     */
    public long countByPublisher(final java.lang.Long publisherIdentifier)
    {
        try
        {
            return this.handleCountByPublisher(publisherIdentifier);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.news.NewsItemDao.countByPublisher(java.lang.Long publisherIdentifier)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #countByPublisher(java.lang.Long)}
      */
    protected abstract long handleCountByPublisher(java.lang.Long publisherIdentifier)
        throws java.lang.Exception;

    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.news.NewsItemDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.news.NewsItem} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.news.NewsItemInfo} - {@link TRANSFORM_NEWSITEMINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.news.NewsItemDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.news.NewsItem entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_NEWSITEMINFO :
                    target = toNewsItemInfo(entity);
                    break;
                case TRANSFORM_NONE : // fall-through
                default:
                    target = entity;
            }
        }
        return target;
    }

    /**
     * Transforms a collection of entities using the
     * {@link #transformEntity(int,org.openuss.news.NewsItem)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.news.NewsItemDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.news.NewsItem)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_NEWSITEMINFO :
                toNewsItemInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.news.NewsItemDao#toNewsItemInfoCollection(java.util.Collection)
     */
    public final void toNewsItemInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, NEWSITEMINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link NewsItemDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.news.NewsItemDao#toNewsItemInfo(org.openuss.news.NewsItem)
     */
    protected org.openuss.news.NewsItemInfo toNewsItemInfo(java.lang.Object[] row)
    {
        org.openuss.news.NewsItemInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.news.NewsItem)
                {
                    target = this.toNewsItemInfo((org.openuss.news.NewsItem)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.news.NewsItemInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer NEWSITEMINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.news.NewsItem)
                {
                    result = toNewsItemInfo((org.openuss.news.NewsItem)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toNewsItemInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.news.NewsItemDao#newsItemInfoToEntityCollection(java.util.Collection)
     */
    public final void newsItemInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.news.NewsItemInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, NewsItemInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer NewsItemInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return newsItemInfoToEntity((org.openuss.news.NewsItemInfo)input);
            }
        };

    /**
     * @see org.openuss.news.NewsItemDao#toNewsItemInfo(org.openuss.news.NewsItem, org.openuss.news.NewsItemInfo)
     */
    public void toNewsItemInfo(
        org.openuss.news.NewsItem source,
        org.openuss.news.NewsItemInfo target)
    {
        target.setId(source.getId());
        target.setAuthor(source.getAuthor());
        target.setPublishDate(source.getPublishDate());
        target.setExpireDate(source.getExpireDate());
        target.setText(source.getText());
        target.setTitle(source.getTitle());
        target.setPublisherName(source.getPublisherName());
        target.setPublisherIdentifier(source.getPublisherIdentifier());
        target.setCategory(source.getCategory());
        target.setPublisherType(source.getPublisherType());
    }

    /**
     * @see org.openuss.news.NewsItemDao#toNewsItemInfo(org.openuss.news.NewsItem)
     */
    public org.openuss.news.NewsItemInfo toNewsItemInfo(final org.openuss.news.NewsItem entity)
    {
        final org.openuss.news.NewsItemInfo target = new org.openuss.news.NewsItemInfo();
        this.toNewsItemInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.news.NewsItemDao#newsItemInfoToEntity(org.openuss.news.NewsItemInfo, org.openuss.news.NewsItem)
     */
    public void newsItemInfoToEntity(
        org.openuss.news.NewsItemInfo source,
        org.openuss.news.NewsItem target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getCategory() != null)
        {
            target.setCategory(source.getCategory());
        }
        if (copyIfNull || source.getPublisherIdentifier() != null)
        {
            target.setPublisherIdentifier(source.getPublisherIdentifier());
        }
        if (copyIfNull || source.getText() != null)
        {
            target.setText(source.getText());
        }
        if (copyIfNull || source.getPublisherName() != null)
        {
            target.setPublisherName(source.getPublisherName());
        }
        if (copyIfNull || source.getTitle() != null)
        {
            target.setTitle(source.getTitle());
        }
        if (copyIfNull || source.getPublishDate() != null)
        {
            target.setPublishDate(source.getPublishDate());
        }
        if (copyIfNull || source.getExpireDate() != null)
        {
            target.setExpireDate(source.getExpireDate());
        }
        if (copyIfNull || source.getAuthor() != null)
        {
            target.setAuthor(source.getAuthor());
        }
        if (copyIfNull || source.getPublisherType() != null)
        {
            target.setPublisherType(source.getPublisherType());
        }
    }
    
}