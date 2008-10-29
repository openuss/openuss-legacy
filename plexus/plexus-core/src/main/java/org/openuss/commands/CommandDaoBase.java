package org.openuss.commands;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.commands.Command</code>.
 * </p>
 *
 * @see org.openuss.commands.Command
 */
public abstract class CommandDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.commands.CommandDao
{


    /**
     * @see org.openuss.commands.CommandDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Command.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.commands.CommandImpl.class, id);
        return transformEntity(transform, (org.openuss.commands.Command)entity);
    }

    /**
     * @see org.openuss.commands.CommandDao#load(java.lang.Long)
     */
    public org.openuss.commands.Command load(java.lang.Long id)
    {
        return (org.openuss.commands.Command)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.commands.CommandDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.commands.Command> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.commands.CommandDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.commands.CommandImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.commands.CommandDao#create(org.openuss.commands.Command)
     */
    public org.openuss.commands.Command create(org.openuss.commands.Command command)
    {
        return (org.openuss.commands.Command)this.create(TRANSFORM_NONE, command);
    }

    /**
     * @see org.openuss.commands.CommandDao#create(int transform, org.openuss.commands.Command)
     */
    public java.lang.Object create(final int transform, final org.openuss.commands.Command command)
    {
        if (command == null)
        {
            throw new IllegalArgumentException(
                "Command.create - 'command' can not be null");
        }
        this.getHibernateTemplate().save(command);
        return this.transformEntity(transform, command);
    }

    /**
     * @see org.openuss.commands.CommandDao#create(java.util.Collection<org.openuss.commands.Command>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.commands.Command> create(final java.util.Collection<org.openuss.commands.Command> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.commands.CommandDao#create(int, java.util.Collection<org.openuss.commands.Command>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.commands.Command> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Command.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.commands.Command)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.commands.CommandDao#update(org.openuss.commands.Command)
     */
    public void update(org.openuss.commands.Command command)
    {
        if (command == null)
        {
            throw new IllegalArgumentException(
                "Command.update - 'command' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(command);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(command);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.commands.CommandDao#update(java.util.Collection<org.openuss.commands.Command>)
     */
    public void update(final java.util.Collection<org.openuss.commands.Command> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Command.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.commands.Command)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.commands.CommandDao#remove(org.openuss.commands.Command)
     */
    public void remove(org.openuss.commands.Command command)
    {
        if (command == null)
        {
            throw new IllegalArgumentException(
                "Command.remove - 'command' can not be null");
        }
        this.getHibernateTemplate().delete(command);
    }

    /**
     * @see org.openuss.commands.CommandDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Command.remove - 'id can not be null");
        }
        org.openuss.commands.Command entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.commands.CommandDao#remove(java.util.Collection<org.openuss.commands.Command>)
     */
    public void remove(java.util.Collection<org.openuss.commands.Command> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Command.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.commands.CommandDao#findAllEachCommandsAfter(java.lang.Long)
     */
    public java.util.List findAllEachCommandsAfter(java.lang.Long commandId)
    {
        return this.findAllEachCommandsAfter(TRANSFORM_NONE, commandId);
    }

    /**
     * @see org.openuss.commands.CommandDao#findAllEachCommandsAfter(java.lang.String, java.lang.Long)
     */
    public java.util.List findAllEachCommandsAfter(final java.lang.String queryString, final java.lang.Long commandId)
    {
        return this.findAllEachCommandsAfter(TRANSFORM_NONE, queryString, commandId);
    }

    /**
     * @see org.openuss.commands.CommandDao#findAllEachCommandsAfter(int, java.lang.Long)
     */
    public java.util.List findAllEachCommandsAfter(final int transform, final java.lang.Long commandId)
    {
        return this.findAllEachCommandsAfter(transform, "from org.openuss.commands.Command as c where c.id > :commandId and state = org.openuss.commands.CommandState.EACH", commandId);
    }

    /**
     * @see org.openuss.commands.CommandDao#findAllEachCommandsAfter(int, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findAllEachCommandsAfter(final int transform, final java.lang.String queryString, final java.lang.Long commandId)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("commandId", commandId);
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
     * @see org.openuss.commands.CommandDao#findAllOnceCommands()
     */
    public java.util.List findAllOnceCommands()
    {
        return this.findAllOnceCommands(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.commands.CommandDao#findAllOnceCommands(java.lang.String)
     */
    public java.util.List findAllOnceCommands(final java.lang.String queryString)
    {
        return this.findAllOnceCommands(TRANSFORM_NONE, queryString);
    }

    /**
     * @see org.openuss.commands.CommandDao#findAllOnceCommands(int)
     */
    public java.util.List findAllOnceCommands(final int transform)
    {
        return this.findAllOnceCommands(transform, "from org.openuss.commands.Command as c where c.state = org.openuss.commands.CommandState.ONCE and c.startTime <= current_timestamp");
    }

    /**
     * @see org.openuss.commands.CommandDao#findAllOnceCommands(int, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findAllOnceCommands(final int transform, final java.lang.String queryString)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
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
     * flag is set to one of the constants defined in <code>org.openuss.commands.CommandDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.commands.CommandDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.commands.Command entity)
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
     * {@link #transformEntity(int,org.openuss.commands.Command)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.commands.CommandDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.commands.Command)
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