package org.openuss.messaging;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.messaging.Message</code>.
 * </p>
 *
 * @see org.openuss.messaging.Message
 */
public abstract class MessageDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.messaging.MessageDao
{


    /**
     * @see org.openuss.messaging.MessageDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Message.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.messaging.MessageImpl.class, id);
        return transformEntity(transform, (org.openuss.messaging.Message)entity);
    }

    /**
     * @see org.openuss.messaging.MessageDao#load(java.lang.Long)
     */
    public org.openuss.messaging.Message load(java.lang.Long id)
    {
        return (org.openuss.messaging.Message)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.messaging.MessageDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.messaging.Message> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.messaging.MessageDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.messaging.MessageImpl.class);
        this.transformEntities(transform, results);
        return results;
    }

    /**
     * @see org.openuss.messaging.MessageDao#update(org.openuss.messaging.Message)
     */
    public void update(org.openuss.messaging.Message message)
    {
        if (message == null)
        {
            throw new IllegalArgumentException(
                "Message.update - 'message' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(message);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(message);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.messaging.MessageDao#update(java.util.Collection<org.openuss.messaging.Message>)
     */
    public void update(final java.util.Collection<org.openuss.messaging.Message> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Message.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.messaging.Message)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.messaging.MessageDao#remove(org.openuss.messaging.Message)
     */
    public void remove(org.openuss.messaging.Message message)
    {
        if (message == null)
        {
            throw new IllegalArgumentException(
                "Message.remove - 'message' can not be null");
        }
        this.getHibernateTemplate().delete(message);
    }

    /**
     * @see org.openuss.messaging.MessageDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Message.remove - 'id can not be null");
        }
        org.openuss.messaging.Message entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.messaging.MessageDao#remove(java.util.Collection<org.openuss.messaging.Message>)
     */
    public void remove(java.util.Collection<org.openuss.messaging.Message> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Message.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.messaging.MessageDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.messaging.MessageDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.messaging.Message entity)
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
     * {@link #transformEntity(int,org.openuss.messaging.Message)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.messaging.MessageDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.messaging.Message)
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