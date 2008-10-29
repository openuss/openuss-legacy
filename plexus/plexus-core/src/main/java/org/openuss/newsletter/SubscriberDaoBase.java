package org.openuss.newsletter;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.newsletter.Subscriber</code>.
 * </p>
 *
 * @see org.openuss.newsletter.Subscriber
 */
public abstract class SubscriberDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.newsletter.SubscriberDao
{


    /**
     * @see org.openuss.newsletter.SubscriberDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final org.openuss.newsletter.SubscriberPK subscriberPk)
    {
        if (subscriberPk == null)
        {
            throw new IllegalArgumentException(
                "Subscriber.load - 'subscriberPk' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.newsletter.SubscriberImpl.class, subscriberPk);
        return transformEntity(transform, (org.openuss.newsletter.Subscriber)entity);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#load(org.openuss.newsletter.SubscriberPK)
     */
    public org.openuss.newsletter.Subscriber load(org.openuss.newsletter.SubscriberPK subscriberPk)
    {
        return (org.openuss.newsletter.Subscriber)this.load(TRANSFORM_NONE, subscriberPk);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.newsletter.Subscriber> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.newsletter.SubscriberImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.newsletter.SubscriberDao#create(org.openuss.newsletter.Subscriber)
     */
    public org.openuss.newsletter.Subscriber create(org.openuss.newsletter.Subscriber subscriber)
    {
        return (org.openuss.newsletter.Subscriber)this.create(TRANSFORM_NONE, subscriber);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#create(int transform, org.openuss.newsletter.Subscriber)
     */
    public java.lang.Object create(final int transform, final org.openuss.newsletter.Subscriber subscriber)
    {
        if (subscriber == null)
        {
            throw new IllegalArgumentException(
                "Subscriber.create - 'subscriber' can not be null");
        }
        this.getHibernateTemplate().save(subscriber);
        return this.transformEntity(transform, subscriber);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#create(java.util.Collection<org.openuss.newsletter.Subscriber>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.newsletter.Subscriber> create(final java.util.Collection<org.openuss.newsletter.Subscriber> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#create(int, java.util.Collection<org.openuss.newsletter.Subscriber>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.newsletter.Subscriber> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Subscriber.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.newsletter.Subscriber)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#update(org.openuss.newsletter.Subscriber)
     */
    public void update(org.openuss.newsletter.Subscriber subscriber)
    {
        if (subscriber == null)
        {
            throw new IllegalArgumentException(
                "Subscriber.update - 'subscriber' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(subscriber);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(subscriber);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#update(java.util.Collection<org.openuss.newsletter.Subscriber>)
     */
    public void update(final java.util.Collection<org.openuss.newsletter.Subscriber> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Subscriber.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.newsletter.Subscriber)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#remove(org.openuss.newsletter.Subscriber)
     */
    public void remove(org.openuss.newsletter.Subscriber subscriber)
    {
        if (subscriber == null)
        {
            throw new IllegalArgumentException(
                "Subscriber.remove - 'subscriber' can not be null");
        }
        this.getHibernateTemplate().delete(subscriber);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#remove(java.lang.Long)
     */
    public void remove(org.openuss.newsletter.SubscriberPK subscriberPk)
    {
        if (subscriberPk == null)
        {
            throw new IllegalArgumentException(
                "Subscriber.remove - 'subscriberPk can not be null");
        }
        org.openuss.newsletter.Subscriber entity = this.load(subscriberPk);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#remove(java.util.Collection<org.openuss.newsletter.Subscriber>)
     */
    public void remove(java.util.Collection<org.openuss.newsletter.Subscriber> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Subscriber.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.newsletter.SubscriberDao#findByNewsletter(org.openuss.newsletter.Newsletter, boolean)
     */
    public java.util.List findByNewsletter(org.openuss.newsletter.Newsletter newsletter, boolean blocked)
    {
        return this.findByNewsletter(TRANSFORM_NONE, newsletter, blocked);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByNewsletter(java.lang.String, org.openuss.newsletter.Newsletter, boolean)
     */
    public java.util.List findByNewsletter(final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter, final boolean blocked)
    {
        return this.findByNewsletter(TRANSFORM_NONE, queryString, newsletter, blocked);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByNewsletter(int, org.openuss.newsletter.Newsletter, boolean)
     */
    public java.util.List findByNewsletter(final int transform, final org.openuss.newsletter.Newsletter newsletter, final boolean blocked)
    {
        return this.findByNewsletter(transform, "from org.openuss.newsletter.Subscriber as subscriber where subscriber.newsletter = ? and subscriber.blocked = ?", newsletter, blocked);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByNewsletter(int, java.lang.String, org.openuss.newsletter.Newsletter, boolean)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByNewsletter(final int transform, final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter, final boolean blocked)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, newsletter);
            queryObject.setParameter(1, new java.lang.Boolean(blocked));
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
     * @see org.openuss.newsletter.SubscriberDao#findByUser(org.openuss.security.User)
     */
    public java.util.List findByUser(org.openuss.security.User user)
    {
        return this.findByUser(TRANSFORM_NONE, user);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByUser(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findByUser(final java.lang.String queryString, final org.openuss.security.User user)
    {
        return this.findByUser(TRANSFORM_NONE, queryString, user);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByUser(int, org.openuss.security.User)
     */
    public java.util.List findByUser(final int transform, final org.openuss.security.User user)
    {
        return this.findByUser(transform, "from org.openuss.newsletter.Subscriber as subscriber where subscriber.user = ?", user);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByUser(int, java.lang.String, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByUser(final int transform, final java.lang.String queryString, final org.openuss.security.User user)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, user);
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
     * @see org.openuss.newsletter.SubscriberDao#findByUserAndNewsletter(org.openuss.security.User, org.openuss.newsletter.Newsletter)
     */
    public org.openuss.newsletter.Subscriber findByUserAndNewsletter(org.openuss.security.User user, org.openuss.newsletter.Newsletter newsletter)
    {
        return (org.openuss.newsletter.Subscriber)this.findByUserAndNewsletter(TRANSFORM_NONE, user, newsletter);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByUserAndNewsletter(java.lang.String, org.openuss.security.User, org.openuss.newsletter.Newsletter)
     */
    public org.openuss.newsletter.Subscriber findByUserAndNewsletter(final java.lang.String queryString, final org.openuss.security.User user, final org.openuss.newsletter.Newsletter newsletter)
    {
        return (org.openuss.newsletter.Subscriber)this.findByUserAndNewsletter(TRANSFORM_NONE, queryString, user, newsletter);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByUserAndNewsletter(int, org.openuss.security.User, org.openuss.newsletter.Newsletter)
     */
    public java.lang.Object findByUserAndNewsletter(final int transform, final org.openuss.security.User user, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findByUserAndNewsletter(transform, "from org.openuss.newsletter.Subscriber as subscriber where subscriber.user = ? and subscriber.newsletter = ?", user, newsletter);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByUserAndNewsletter(int, java.lang.String, org.openuss.security.User, org.openuss.newsletter.Newsletter)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByUserAndNewsletter(final int transform, final java.lang.String queryString, final org.openuss.security.User user, final org.openuss.newsletter.Newsletter newsletter)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, user);
            queryObject.setParameter(1, newsletter);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.newsletter.Subscriber"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.newsletter.Subscriber)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByNewsletter(org.openuss.newsletter.Newsletter)
     */
    public java.util.List findByNewsletter(org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findByNewsletter(TRANSFORM_NONE, newsletter);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByNewsletter(java.lang.String, org.openuss.newsletter.Newsletter)
     */
    public java.util.List findByNewsletter(final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findByNewsletter(TRANSFORM_NONE, queryString, newsletter);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByNewsletter(int, org.openuss.newsletter.Newsletter)
     */
    public java.util.List findByNewsletter(final int transform, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findByNewsletter(transform, "from org.openuss.newsletter.Subscriber as subscriber where subscriber.newsletter = ?", newsletter);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByNewsletter(int, java.lang.String, org.openuss.newsletter.Newsletter)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByNewsletter(final int transform, final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, newsletter);
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
     * flag is set to one of the constants defined in <code>org.openuss.newsletter.SubscriberDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.newsletter.Subscriber} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.newsletter.SubscriberInfo} - {@link TRANSFORM_SUBSCRIBERINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.newsletter.SubscriberDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.newsletter.Subscriber entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_SUBSCRIBERINFO :
                    target = toSubscriberInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.newsletter.Subscriber)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.newsletter.SubscriberDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.newsletter.Subscriber)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_SUBSCRIBERINFO :
                toSubscriberInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#toSubscriberInfoCollection(java.util.Collection)
     */
    public final void toSubscriberInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, SUBSCRIBERINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link SubscriberDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.newsletter.SubscriberDao#toSubscriberInfo(org.openuss.newsletter.Subscriber)
     */
    protected org.openuss.newsletter.SubscriberInfo toSubscriberInfo(java.lang.Object[] row)
    {
        org.openuss.newsletter.SubscriberInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.newsletter.Subscriber)
                {
                    target = this.toSubscriberInfo((org.openuss.newsletter.Subscriber)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.newsletter.SubscriberInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer SUBSCRIBERINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.newsletter.Subscriber)
                {
                    result = toSubscriberInfo((org.openuss.newsletter.Subscriber)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toSubscriberInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.newsletter.SubscriberDao#subscriberInfoToEntityCollection(java.util.Collection)
     */
    public final void subscriberInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.newsletter.SubscriberInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, SubscriberInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer SubscriberInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return subscriberInfoToEntity((org.openuss.newsletter.SubscriberInfo)input);
            }
        };

    /**
     * @see org.openuss.newsletter.SubscriberDao#toSubscriberInfo(org.openuss.newsletter.Subscriber, org.openuss.newsletter.SubscriberInfo)
     */
    public void toSubscriberInfo(
        org.openuss.newsletter.Subscriber source,
        org.openuss.newsletter.SubscriberInfo target)
    {
        //TODO: if any VO attribute maps with identifier association ends, map it in the impl class
        target.setBlocked(source.isBlocked());
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#toSubscriberInfo(org.openuss.newsletter.Subscriber)
     */
    public org.openuss.newsletter.SubscriberInfo toSubscriberInfo(final org.openuss.newsletter.Subscriber entity)
    {
        final org.openuss.newsletter.SubscriberInfo target = new org.openuss.newsletter.SubscriberInfo();
        this.toSubscriberInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.newsletter.SubscriberDao#subscriberInfoToEntity(org.openuss.newsletter.SubscriberInfo, org.openuss.newsletter.Subscriber)
     */
    public void subscriberInfoToEntity(
        org.openuss.newsletter.SubscriberInfo source,
        org.openuss.newsletter.Subscriber target,
        boolean copyIfNull)
    {
        //TODO: if any VO attribute maps with identifier association ends, map it in the impl class
        if(target.getSubscriberPk() == null)
        {
            target.setSubscriberPk(new SubscriberPK());
        }
	    target.setBlocked(source.isBlocked());
    }
    
}