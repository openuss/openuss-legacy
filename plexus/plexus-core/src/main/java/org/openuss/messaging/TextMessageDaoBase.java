package org.openuss.messaging;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.messaging.TextMessage</code>.
 * </p>
 *
 * @see org.openuss.messaging.TextMessage
 */
public abstract class TextMessageDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.messaging.TextMessageDao
{


    /**
     * @see org.openuss.messaging.TextMessageDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "TextMessage.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.messaging.TextMessageImpl.class, id);
        return transformEntity(transform, (org.openuss.messaging.TextMessage)entity);
    }

    /**
     * @see org.openuss.messaging.TextMessageDao#load(java.lang.Long)
     */
    public org.openuss.messaging.TextMessage load(java.lang.Long id)
    {
        return (org.openuss.messaging.TextMessage)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.messaging.TextMessageDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.messaging.TextMessage> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.messaging.TextMessageDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.messaging.TextMessageImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.messaging.TextMessageDao#create(org.openuss.messaging.TextMessage)
     */
    public org.openuss.messaging.TextMessage create(org.openuss.messaging.TextMessage textMessage)
    {
        return (org.openuss.messaging.TextMessage)this.create(TRANSFORM_NONE, textMessage);
    }

    /**
     * @see org.openuss.messaging.TextMessageDao#create(int transform, org.openuss.messaging.TextMessage)
     */
    public java.lang.Object create(final int transform, final org.openuss.messaging.TextMessage textMessage)
    {
        if (textMessage == null)
        {
            throw new IllegalArgumentException(
                "TextMessage.create - 'textMessage' can not be null");
        }
        this.getHibernateTemplate().save(textMessage);
        return this.transformEntity(transform, textMessage);
    }

    /**
     * @see org.openuss.messaging.TextMessageDao#create(java.util.Collection<org.openuss.messaging.TextMessage>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.messaging.TextMessage> create(final java.util.Collection<org.openuss.messaging.TextMessage> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.messaging.TextMessageDao#create(int, java.util.Collection<org.openuss.messaging.TextMessage>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.messaging.TextMessage> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "TextMessage.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.messaging.TextMessage)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.messaging.TextMessageDao#update(org.openuss.messaging.TextMessage)
     */
    public void update(org.openuss.messaging.TextMessage textMessage)
    {
        if (textMessage == null)
        {
            throw new IllegalArgumentException(
                "TextMessage.update - 'textMessage' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(textMessage);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(textMessage);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.messaging.TextMessageDao#update(java.util.Collection<org.openuss.messaging.TextMessage>)
     */
    public void update(final java.util.Collection<org.openuss.messaging.TextMessage> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "TextMessage.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.messaging.TextMessage)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.messaging.TextMessageDao#remove(org.openuss.messaging.TextMessage)
     */
    public void remove(org.openuss.messaging.TextMessage textMessage)
    {
        if (textMessage == null)
        {
            throw new IllegalArgumentException(
                "TextMessage.remove - 'textMessage' can not be null");
        }
        this.getHibernateTemplate().delete(textMessage);
    }

    /**
     * @see org.openuss.messaging.TextMessageDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "TextMessage.remove - 'id can not be null");
        }
        org.openuss.messaging.TextMessage entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.messaging.TextMessageDao#remove(java.util.Collection<org.openuss.messaging.TextMessage>)
     */
    public void remove(java.util.Collection<org.openuss.messaging.TextMessage> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "TextMessage.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.messaging.TextMessageDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.messaging.TextMessageDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.messaging.TextMessage entity)
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
     * {@link #transformEntity(int,org.openuss.messaging.TextMessage)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.messaging.TextMessageDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.messaging.TextMessage)
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