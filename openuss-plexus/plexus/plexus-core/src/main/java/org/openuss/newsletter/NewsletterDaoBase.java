package org.openuss.newsletter;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.newsletter.Newsletter</code>.
 * </p>
 *
 * @see org.openuss.newsletter.Newsletter
 */
public abstract class NewsletterDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.newsletter.NewsletterDao
{


    /**
     * @see org.openuss.newsletter.NewsletterDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Newsletter.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.newsletter.NewsletterImpl.class, id);
        return transformEntity(transform, (org.openuss.newsletter.Newsletter)entity);
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#load(java.lang.Long)
     */
    public org.openuss.newsletter.Newsletter load(java.lang.Long id)
    {
        return (org.openuss.newsletter.Newsletter)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.newsletter.Newsletter> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.newsletter.NewsletterImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.newsletter.NewsletterDao#create(org.openuss.newsletter.Newsletter)
     */
    public org.openuss.newsletter.Newsletter create(org.openuss.newsletter.Newsletter newsletter)
    {
        return (org.openuss.newsletter.Newsletter)this.create(TRANSFORM_NONE, newsletter);
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#create(int transform, org.openuss.newsletter.Newsletter)
     */
    public java.lang.Object create(final int transform, final org.openuss.newsletter.Newsletter newsletter)
    {
        if (newsletter == null)
        {
            throw new IllegalArgumentException(
                "Newsletter.create - 'newsletter' can not be null");
        }
        this.getHibernateTemplate().save(newsletter);
        return this.transformEntity(transform, newsletter);
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#create(java.util.Collection<org.openuss.newsletter.Newsletter>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.newsletter.Newsletter> create(final java.util.Collection<org.openuss.newsletter.Newsletter> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#create(int, java.util.Collection<org.openuss.newsletter.Newsletter>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.newsletter.Newsletter> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Newsletter.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.newsletter.Newsletter)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#update(org.openuss.newsletter.Newsletter)
     */
    public void update(org.openuss.newsletter.Newsletter newsletter)
    {
        if (newsletter == null)
        {
            throw new IllegalArgumentException(
                "Newsletter.update - 'newsletter' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(newsletter);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(newsletter);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#update(java.util.Collection<org.openuss.newsletter.Newsletter>)
     */
    public void update(final java.util.Collection<org.openuss.newsletter.Newsletter> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Newsletter.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.newsletter.Newsletter)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#remove(org.openuss.newsletter.Newsletter)
     */
    public void remove(org.openuss.newsletter.Newsletter newsletter)
    {
        if (newsletter == null)
        {
            throw new IllegalArgumentException(
                "Newsletter.remove - 'newsletter' can not be null");
        }
        this.getHibernateTemplate().delete(newsletter);
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Newsletter.remove - 'id can not be null");
        }
        org.openuss.newsletter.Newsletter entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#remove(java.util.Collection<org.openuss.newsletter.Newsletter>)
     */
    public void remove(java.util.Collection<org.openuss.newsletter.Newsletter> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Newsletter.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.newsletter.NewsletterDao#findByDomainIdentifier(java.lang.Long)
     */
    public java.util.List findByDomainIdentifier(java.lang.Long domainIdentifier)
    {
        return this.findByDomainIdentifier(TRANSFORM_NONE, domainIdentifier);
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#findByDomainIdentifier(java.lang.String, java.lang.Long)
     */
    public java.util.List findByDomainIdentifier(final java.lang.String queryString, final java.lang.Long domainIdentifier)
    {
        return this.findByDomainIdentifier(TRANSFORM_NONE, queryString, domainIdentifier);
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#findByDomainIdentifier(int, java.lang.Long)
     */
    public java.util.List findByDomainIdentifier(final int transform, final java.lang.Long domainIdentifier)
    {
        return this.findByDomainIdentifier(transform, "from org.openuss.newsletter.Newsletter as newsletter where newsletter.domainIdentifier = ?", domainIdentifier);
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#findByDomainIdentifier(int, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByDomainIdentifier(final int transform, final java.lang.String queryString, final java.lang.Long domainIdentifier)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, domainIdentifier);
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
     * flag is set to one of the constants defined in <code>org.openuss.newsletter.NewsletterDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.newsletter.Newsletter} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.newsletter.NewsletterInfo} - {@link TRANSFORM_NEWSLETTERINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.newsletter.NewsletterDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.newsletter.Newsletter entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_NEWSLETTERINFO :
                    target = toNewsletterInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.newsletter.Newsletter)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.newsletter.NewsletterDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.newsletter.Newsletter)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_NEWSLETTERINFO :
                toNewsletterInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#toNewsletterInfoCollection(java.util.Collection)
     */
    public final void toNewsletterInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, NEWSLETTERINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link NewsletterDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.newsletter.NewsletterDao#toNewsletterInfo(org.openuss.newsletter.Newsletter)
     */
    protected org.openuss.newsletter.NewsletterInfo toNewsletterInfo(java.lang.Object[] row)
    {
        org.openuss.newsletter.NewsletterInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.newsletter.Newsletter)
                {
                    target = this.toNewsletterInfo((org.openuss.newsletter.Newsletter)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.newsletter.NewsletterInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer NEWSLETTERINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.newsletter.Newsletter)
                {
                    result = toNewsletterInfo((org.openuss.newsletter.Newsletter)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toNewsletterInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.newsletter.NewsletterDao#newsletterInfoToEntityCollection(java.util.Collection)
     */
    public final void newsletterInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.newsletter.NewsletterInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, NewsletterInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer NewsletterInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return newsletterInfoToEntity((org.openuss.newsletter.NewsletterInfo)input);
            }
        };

    /**
     * @see org.openuss.newsletter.NewsletterDao#toNewsletterInfo(org.openuss.newsletter.Newsletter, org.openuss.newsletter.NewsletterInfo)
     */
    public void toNewsletterInfo(
        org.openuss.newsletter.Newsletter source,
        org.openuss.newsletter.NewsletterInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDomainIdentifier(source.getDomainIdentifier());
    }

    /**
     * @see org.openuss.newsletter.NewsletterDao#toNewsletterInfo(org.openuss.newsletter.Newsletter)
     */
    public org.openuss.newsletter.NewsletterInfo toNewsletterInfo(final org.openuss.newsletter.Newsletter entity)
    {
        final org.openuss.newsletter.NewsletterInfo target = new org.openuss.newsletter.NewsletterInfo();
        this.toNewsletterInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.newsletter.NewsletterDao#newsletterInfoToEntity(org.openuss.newsletter.NewsletterInfo, org.openuss.newsletter.Newsletter)
     */
    public void newsletterInfoToEntity(
        org.openuss.newsletter.NewsletterInfo source,
        org.openuss.newsletter.Newsletter target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
        if (copyIfNull || source.getDomainIdentifier() != null)
        {
            target.setDomainIdentifier(source.getDomainIdentifier());
        }
    }
    
}