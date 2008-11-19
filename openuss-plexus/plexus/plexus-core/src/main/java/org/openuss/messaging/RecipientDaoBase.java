package org.openuss.messaging;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.messaging.Recipient</code>.
 * </p>
 *
 * @see org.openuss.messaging.Recipient
 */
public abstract class RecipientDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.messaging.RecipientDao
{


    /**
     * @see org.openuss.messaging.RecipientDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Recipient.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.messaging.RecipientImpl.class, id);
        return transformEntity(transform, (org.openuss.messaging.Recipient)entity);
    }

    /**
     * @see org.openuss.messaging.RecipientDao#load(java.lang.Long)
     */
    public org.openuss.messaging.Recipient load(java.lang.Long id)
    {
        return (org.openuss.messaging.Recipient)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.messaging.RecipientDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.messaging.Recipient> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.messaging.RecipientDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.messaging.RecipientImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.messaging.RecipientDao#create(org.openuss.messaging.Recipient)
     */
    public org.openuss.messaging.Recipient create(org.openuss.messaging.Recipient recipient)
    {
        return (org.openuss.messaging.Recipient)this.create(TRANSFORM_NONE, recipient);
    }

    /**
     * @see org.openuss.messaging.RecipientDao#create(int transform, org.openuss.messaging.Recipient)
     */
    public java.lang.Object create(final int transform, final org.openuss.messaging.Recipient recipient)
    {
        if (recipient == null)
        {
            throw new IllegalArgumentException(
                "Recipient.create - 'recipient' can not be null");
        }
        this.getHibernateTemplate().save(recipient);
        return this.transformEntity(transform, recipient);
    }

    /**
     * @see org.openuss.messaging.RecipientDao#create(java.util.Collection<org.openuss.messaging.Recipient>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.messaging.Recipient> create(final java.util.Collection<org.openuss.messaging.Recipient> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.messaging.RecipientDao#create(int, java.util.Collection<org.openuss.messaging.Recipient>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.messaging.Recipient> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Recipient.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.messaging.Recipient)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.messaging.RecipientDao#update(org.openuss.messaging.Recipient)
     */
    public void update(org.openuss.messaging.Recipient recipient)
    {
        if (recipient == null)
        {
            throw new IllegalArgumentException(
                "Recipient.update - 'recipient' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(recipient);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(recipient);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.messaging.RecipientDao#update(java.util.Collection<org.openuss.messaging.Recipient>)
     */
    public void update(final java.util.Collection<org.openuss.messaging.Recipient> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Recipient.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.messaging.Recipient)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.messaging.RecipientDao#remove(org.openuss.messaging.Recipient)
     */
    public void remove(org.openuss.messaging.Recipient recipient)
    {
        if (recipient == null)
        {
            throw new IllegalArgumentException(
                "Recipient.remove - 'recipient' can not be null");
        }
        this.getHibernateTemplate().delete(recipient);
    }

    /**
     * @see org.openuss.messaging.RecipientDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Recipient.remove - 'id can not be null");
        }
        org.openuss.messaging.Recipient entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.messaging.RecipientDao#remove(java.util.Collection<org.openuss.messaging.Recipient>)
     */
    public void remove(java.util.Collection<org.openuss.messaging.Recipient> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Recipient.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.messaging.RecipientDao#findByState(org.openuss.messaging.SendState)
     */
    public java.util.List findByState(org.openuss.messaging.SendState status)
    {
        return this.findByState(TRANSFORM_NONE, status);
    }

    /**
     * @see org.openuss.messaging.RecipientDao#findByState(java.lang.String, org.openuss.messaging.SendState)
     */
    public java.util.List findByState(final java.lang.String queryString, final org.openuss.messaging.SendState status)
    {
        return this.findByState(TRANSFORM_NONE, queryString, status);
    }

    /**
     * @see org.openuss.messaging.RecipientDao#findByState(int, org.openuss.messaging.SendState)
     */
    public java.util.List findByState(final int transform, final org.openuss.messaging.SendState status)
    {
        return this.findByState(transform, "from org.openuss.messaging.Recipient as recipient where recipient.status = ?", status);
    }

    /**
     * @see org.openuss.messaging.RecipientDao#findByState(int, java.lang.String, org.openuss.messaging.SendState)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByState(final int transform, final java.lang.String queryString, final org.openuss.messaging.SendState status)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, status.getValue());
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
     * flag is set to one of the constants defined in <code>org.openuss.messaging.RecipientDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.messaging.RecipientDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.messaging.Recipient entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_NONE : // fall-through
                default:
                    target = entity;
            }
        }
        return target;
    }

    /**
     * Transforms a collection of entities using the
     * {@link #transformEntity(int,org.openuss.messaging.Recipient)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.messaging.RecipientDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.messaging.Recipient)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

}