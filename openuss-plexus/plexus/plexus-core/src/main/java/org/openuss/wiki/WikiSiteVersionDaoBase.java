package org.openuss.wiki;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.wiki.WikiSiteVersion</code>.
 * </p>
 *
 * @see org.openuss.wiki.WikiSiteVersion
 */
public abstract class WikiSiteVersionDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.wiki.WikiSiteVersionDao
{


    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "WikiSiteVersion.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.wiki.WikiSiteVersionImpl.class, id);
        return transformEntity(transform, (org.openuss.wiki.WikiSiteVersion)entity);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#load(java.lang.Long)
     */
    public org.openuss.wiki.WikiSiteVersion load(java.lang.Long id)
    {
        return (org.openuss.wiki.WikiSiteVersion)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.wiki.WikiSiteVersion> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.wiki.WikiSiteVersionImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#create(org.openuss.wiki.WikiSiteVersion)
     */
    public org.openuss.wiki.WikiSiteVersion create(org.openuss.wiki.WikiSiteVersion wikiSiteVersion)
    {
        return (org.openuss.wiki.WikiSiteVersion)this.create(TRANSFORM_NONE, wikiSiteVersion);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#create(int transform, org.openuss.wiki.WikiSiteVersion)
     */
    public java.lang.Object create(final int transform, final org.openuss.wiki.WikiSiteVersion wikiSiteVersion)
    {
        if (wikiSiteVersion == null)
        {
            throw new IllegalArgumentException(
                "WikiSiteVersion.create - 'wikiSiteVersion' can not be null");
        }
        this.getHibernateTemplate().save(wikiSiteVersion);
        return this.transformEntity(transform, wikiSiteVersion);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#create(java.util.Collection<org.openuss.wiki.WikiSiteVersion>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.wiki.WikiSiteVersion> create(final java.util.Collection<org.openuss.wiki.WikiSiteVersion> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#create(int, java.util.Collection<org.openuss.wiki.WikiSiteVersion>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.wiki.WikiSiteVersion> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "WikiSiteVersion.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.wiki.WikiSiteVersion)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#update(org.openuss.wiki.WikiSiteVersion)
     */
    public void update(org.openuss.wiki.WikiSiteVersion wikiSiteVersion)
    {
        if (wikiSiteVersion == null)
        {
            throw new IllegalArgumentException(
                "WikiSiteVersion.update - 'wikiSiteVersion' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(wikiSiteVersion);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(wikiSiteVersion);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#update(java.util.Collection<org.openuss.wiki.WikiSiteVersion>)
     */
    public void update(final java.util.Collection<org.openuss.wiki.WikiSiteVersion> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "WikiSiteVersion.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.wiki.WikiSiteVersion)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#remove(org.openuss.wiki.WikiSiteVersion)
     */
    public void remove(org.openuss.wiki.WikiSiteVersion wikiSiteVersion)
    {
        if (wikiSiteVersion == null)
        {
            throw new IllegalArgumentException(
                "WikiSiteVersion.remove - 'wikiSiteVersion' can not be null");
        }
        this.getHibernateTemplate().delete(wikiSiteVersion);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "WikiSiteVersion.remove - 'id can not be null");
        }
        org.openuss.wiki.WikiSiteVersion entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#remove(java.util.Collection<org.openuss.wiki.WikiSiteVersion>)
     */
    public void remove(java.util.Collection<org.openuss.wiki.WikiSiteVersion> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "WikiSiteVersion.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#findByWikiSite(org.openuss.wiki.WikiSite)
     */
    public java.util.List findByWikiSite(org.openuss.wiki.WikiSite wikiSite)
    {
        return this.findByWikiSite(TRANSFORM_NONE, wikiSite);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#findByWikiSite(java.lang.String, org.openuss.wiki.WikiSite)
     */
    public java.util.List findByWikiSite(final java.lang.String queryString, final org.openuss.wiki.WikiSite wikiSite)
    {
        return this.findByWikiSite(TRANSFORM_NONE, queryString, wikiSite);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#findByWikiSite(int, org.openuss.wiki.WikiSite)
     */
    public java.util.List findByWikiSite(final int transform, final org.openuss.wiki.WikiSite wikiSite)
    {
        return this.findByWikiSite(transform, "from org.openuss.wiki.WikiSiteVersion as wikiSiteVersion where wikiSiteVersion.wikiSite = ?", wikiSite);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#findByWikiSite(int, java.lang.String, org.openuss.wiki.WikiSite)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByWikiSite(final int transform, final java.lang.String queryString, final org.openuss.wiki.WikiSite wikiSite)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, wikiSite);
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
     * @see org.openuss.wiki.WikiSiteVersionDao#findByAuthor(org.openuss.security.User)
     */
    public java.util.List findByAuthor(org.openuss.security.User author)
    {
        return this.findByAuthor(TRANSFORM_NONE, author);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#findByAuthor(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findByAuthor(final java.lang.String queryString, final org.openuss.security.User author)
    {
        return this.findByAuthor(TRANSFORM_NONE, queryString, author);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#findByAuthor(int, org.openuss.security.User)
     */
    public java.util.List findByAuthor(final int transform, final org.openuss.security.User author)
    {
        return this.findByAuthor(transform, "from org.openuss.wiki.WikiSiteVersion as wikiSiteVersion where wikiSiteVersion.author = ?", author);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#findByAuthor(int, java.lang.String, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByAuthor(final int transform, final java.lang.String queryString, final org.openuss.security.User author)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, author);
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
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.wiki.WikiSiteVersionDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.wiki.WikiSiteVersion} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.wiki.WikiSiteContentInfo} - {@link TRANSFORM_WIKISITECONTENTINFO}</li>
     *   <li>{@link org.openuss.wiki.WikiSiteInfo} - {@link TRANSFORM_WIKISITEINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.wiki.WikiSiteVersionDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.wiki.WikiSiteVersion entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_WIKISITECONTENTINFO :
                    target = toWikiSiteContentInfo(entity);
                    break;
                case TRANSFORM_WIKISITEINFO :
                    target = toWikiSiteInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.wiki.WikiSiteVersion)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.wiki.WikiSiteVersionDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.wiki.WikiSiteVersion)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_WIKISITECONTENTINFO :
                toWikiSiteContentInfoCollection(entities);
                break;
            case TRANSFORM_WIKISITEINFO :
                toWikiSiteInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteContentInfoCollection(java.util.Collection)
     */
    public final void toWikiSiteContentInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, WIKISITECONTENTINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link WikiSiteVersionDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteContentInfo(org.openuss.wiki.WikiSiteVersion)
     */
    protected org.openuss.wiki.WikiSiteContentInfo toWikiSiteContentInfo(java.lang.Object[] row)
    {
        org.openuss.wiki.WikiSiteContentInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.wiki.WikiSiteVersion)
                {
                    target = this.toWikiSiteContentInfo((org.openuss.wiki.WikiSiteVersion)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.wiki.WikiSiteContentInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer WIKISITECONTENTINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.wiki.WikiSiteVersion)
                {
                    result = toWikiSiteContentInfo((org.openuss.wiki.WikiSiteVersion)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toWikiSiteContentInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteContentInfoToEntityCollection(java.util.Collection)
     */
    public final void wikiSiteContentInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.wiki.WikiSiteContentInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, WikiSiteContentInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer WikiSiteContentInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return wikiSiteContentInfoToEntity((org.openuss.wiki.WikiSiteContentInfo)input);
            }
        };

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteContentInfo(org.openuss.wiki.WikiSiteVersion, org.openuss.wiki.WikiSiteContentInfo)
     */
    public void toWikiSiteContentInfo(
        org.openuss.wiki.WikiSiteVersion source,
        org.openuss.wiki.WikiSiteContentInfo target)
    {
        target.setText(source.getText());
        target.setId(source.getId());
        target.setCreationDate(source.getCreationDate());
        target.setNote(source.getNote());
        target.setStable((source.getStable() == null ? false : source.getStable().booleanValue()));
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteContentInfo(org.openuss.wiki.WikiSiteVersion)
     */
    public org.openuss.wiki.WikiSiteContentInfo toWikiSiteContentInfo(final org.openuss.wiki.WikiSiteVersion entity)
    {
        final org.openuss.wiki.WikiSiteContentInfo target = new org.openuss.wiki.WikiSiteContentInfo();
        this.toWikiSiteContentInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteContentInfoToEntity(org.openuss.wiki.WikiSiteContentInfo, org.openuss.wiki.WikiSiteVersion)
     */
    public void wikiSiteContentInfoToEntity(
        org.openuss.wiki.WikiSiteContentInfo source,
        org.openuss.wiki.WikiSiteVersion target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getText() != null)
        {
            target.setText(source.getText());
        }
        if (copyIfNull || source.getCreationDate() != null)
        {
            target.setCreationDate(source.getCreationDate());
        }
        if (copyIfNull || source.getNote() != null)
        {
            target.setNote(source.getNote());
        }
	    target.setStable(new java.lang.Boolean(source.isStable()));
    }
    
    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteInfoCollection(java.util.Collection)
     */
    public final void toWikiSiteInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, WIKISITEINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link WikiSiteVersionDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteInfo(org.openuss.wiki.WikiSiteVersion)
     */
    protected org.openuss.wiki.WikiSiteInfo toWikiSiteInfo(java.lang.Object[] row)
    {
        org.openuss.wiki.WikiSiteInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.wiki.WikiSiteVersion)
                {
                    target = this.toWikiSiteInfo((org.openuss.wiki.WikiSiteVersion)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.wiki.WikiSiteInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer WIKISITEINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.wiki.WikiSiteVersion)
                {
                    result = toWikiSiteInfo((org.openuss.wiki.WikiSiteVersion)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toWikiSiteInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteInfoToEntityCollection(java.util.Collection)
     */
    public final void wikiSiteInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.wiki.WikiSiteInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, WikiSiteInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer WikiSiteInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return wikiSiteInfoToEntity((org.openuss.wiki.WikiSiteInfo)input);
            }
        };

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteInfo(org.openuss.wiki.WikiSiteVersion, org.openuss.wiki.WikiSiteInfo)
     */
    public void toWikiSiteInfo(
        org.openuss.wiki.WikiSiteVersion source,
        org.openuss.wiki.WikiSiteInfo target)
    {
        target.setId(source.getId());
        target.setCreationDate(source.getCreationDate());
        target.setNote(source.getNote());
        target.setStable((source.getStable() == null ? false : source.getStable().booleanValue()));
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteInfo(org.openuss.wiki.WikiSiteVersion)
     */
    public org.openuss.wiki.WikiSiteInfo toWikiSiteInfo(final org.openuss.wiki.WikiSiteVersion entity)
    {
        final org.openuss.wiki.WikiSiteInfo target = new org.openuss.wiki.WikiSiteInfo();
        this.toWikiSiteInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo, org.openuss.wiki.WikiSiteVersion)
     */
    public void wikiSiteInfoToEntity(
        org.openuss.wiki.WikiSiteInfo source,
        org.openuss.wiki.WikiSiteVersion target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getCreationDate() != null)
        {
            target.setCreationDate(source.getCreationDate());
        }
        if (copyIfNull || source.getNote() != null)
        {
            target.setNote(source.getNote());
        }
	    target.setStable(new java.lang.Boolean(source.isStable()));
    }
    
}