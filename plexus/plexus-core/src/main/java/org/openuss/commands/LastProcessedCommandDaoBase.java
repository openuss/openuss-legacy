package org.openuss.commands;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.commands.LastProcessedCommand</code>.
 * </p>
 *
 * @see org.openuss.commands.LastProcessedCommand
 */
public abstract class LastProcessedCommandDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.commands.LastProcessedCommandDao
{


    /**
     * @see org.openuss.commands.LastProcessedCommandDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "LastProcessedCommand.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.commands.LastProcessedCommandImpl.class, id);
        return transformEntity(transform, (org.openuss.commands.LastProcessedCommand)entity);
    }

    /**
     * @see org.openuss.commands.LastProcessedCommandDao#load(java.lang.Long)
     */
    public org.openuss.commands.LastProcessedCommand load(java.lang.Long id)
    {
        return (org.openuss.commands.LastProcessedCommand)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.commands.LastProcessedCommandDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.commands.LastProcessedCommand> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.commands.LastProcessedCommandDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.commands.LastProcessedCommandImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.commands.LastProcessedCommandDao#create(org.openuss.commands.LastProcessedCommand)
     */
    public org.openuss.commands.LastProcessedCommand create(org.openuss.commands.LastProcessedCommand lastProcessedCommand)
    {
        return (org.openuss.commands.LastProcessedCommand)this.create(TRANSFORM_NONE, lastProcessedCommand);
    }

    /**
     * @see org.openuss.commands.LastProcessedCommandDao#create(int transform, org.openuss.commands.LastProcessedCommand)
     */
    public java.lang.Object create(final int transform, final org.openuss.commands.LastProcessedCommand lastProcessedCommand)
    {
        if (lastProcessedCommand == null)
        {
            throw new IllegalArgumentException(
                "LastProcessedCommand.create - 'lastProcessedCommand' can not be null");
        }
        this.getHibernateTemplate().save(lastProcessedCommand);
        return this.transformEntity(transform, lastProcessedCommand);
    }

    /**
     * @see org.openuss.commands.LastProcessedCommandDao#create(java.util.Collection<org.openuss.commands.LastProcessedCommand>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.commands.LastProcessedCommand> create(final java.util.Collection<org.openuss.commands.LastProcessedCommand> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.commands.LastProcessedCommandDao#create(int, java.util.Collection<org.openuss.commands.LastProcessedCommand>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.commands.LastProcessedCommand> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "LastProcessedCommand.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.commands.LastProcessedCommand)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.commands.LastProcessedCommandDao#update(org.openuss.commands.LastProcessedCommand)
     */
    public void update(org.openuss.commands.LastProcessedCommand lastProcessedCommand)
    {
        if (lastProcessedCommand == null)
        {
            throw new IllegalArgumentException(
                "LastProcessedCommand.update - 'lastProcessedCommand' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(lastProcessedCommand);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(lastProcessedCommand);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.commands.LastProcessedCommandDao#update(java.util.Collection<org.openuss.commands.LastProcessedCommand>)
     */
    public void update(final java.util.Collection<org.openuss.commands.LastProcessedCommand> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "LastProcessedCommand.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.commands.LastProcessedCommand)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.commands.LastProcessedCommandDao#remove(org.openuss.commands.LastProcessedCommand)
     */
    public void remove(org.openuss.commands.LastProcessedCommand lastProcessedCommand)
    {
        if (lastProcessedCommand == null)
        {
            throw new IllegalArgumentException(
                "LastProcessedCommand.remove - 'lastProcessedCommand' can not be null");
        }
        this.getHibernateTemplate().delete(lastProcessedCommand);
    }

    /**
     * @see org.openuss.commands.LastProcessedCommandDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "LastProcessedCommand.remove - 'id can not be null");
        }
        org.openuss.commands.LastProcessedCommand entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.commands.LastProcessedCommandDao#remove(java.util.Collection<org.openuss.commands.LastProcessedCommand>)
     */
    public void remove(java.util.Collection<org.openuss.commands.LastProcessedCommand> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "LastProcessedCommand.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.commands.LastProcessedCommandDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.commands.LastProcessedCommandDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.commands.LastProcessedCommand entity)
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
     * {@link #transformEntity(int,org.openuss.commands.LastProcessedCommand)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.commands.LastProcessedCommandDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.commands.LastProcessedCommand)
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