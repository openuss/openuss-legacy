package org.openuss.discussion;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.discussion.ForumWatch</code>.
 * </p>
 *
 * @see org.openuss.discussion.ForumWatch
 */
public abstract class ForumWatchDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.discussion.ForumWatchDao
{


    /**
     * @see org.openuss.discussion.ForumWatchDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final org.openuss.discussion.ForumWatchPK forumWatchPk)
    {
        if (forumWatchPk == null)
        {
            throw new IllegalArgumentException(
                "ForumWatch.load - 'forumWatchPk' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.discussion.ForumWatchImpl.class, forumWatchPk);
        return transformEntity(transform, (org.openuss.discussion.ForumWatch)entity);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#load(org.openuss.discussion.ForumWatchPK)
     */
    public org.openuss.discussion.ForumWatch load(org.openuss.discussion.ForumWatchPK forumWatchPk)
    {
        return (org.openuss.discussion.ForumWatch)this.load(TRANSFORM_NONE, forumWatchPk);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.ForumWatch> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.discussion.ForumWatchImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.discussion.ForumWatchDao#create(org.openuss.discussion.ForumWatch)
     */
    public org.openuss.discussion.ForumWatch create(org.openuss.discussion.ForumWatch forumWatch)
    {
        return (org.openuss.discussion.ForumWatch)this.create(TRANSFORM_NONE, forumWatch);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#create(int transform, org.openuss.discussion.ForumWatch)
     */
    public java.lang.Object create(final int transform, final org.openuss.discussion.ForumWatch forumWatch)
    {
        if (forumWatch == null)
        {
            throw new IllegalArgumentException(
                "ForumWatch.create - 'forumWatch' can not be null");
        }
        this.getHibernateTemplate().save(forumWatch);
        return this.transformEntity(transform, forumWatch);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#create(java.util.Collection<org.openuss.discussion.ForumWatch>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.ForumWatch> create(final java.util.Collection<org.openuss.discussion.ForumWatch> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#create(int, java.util.Collection<org.openuss.discussion.ForumWatch>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.discussion.ForumWatch> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ForumWatch.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.discussion.ForumWatch)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#update(org.openuss.discussion.ForumWatch)
     */
    public void update(org.openuss.discussion.ForumWatch forumWatch)
    {
        if (forumWatch == null)
        {
            throw new IllegalArgumentException(
                "ForumWatch.update - 'forumWatch' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(forumWatch);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(forumWatch);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#update(java.util.Collection<org.openuss.discussion.ForumWatch>)
     */
    public void update(final java.util.Collection<org.openuss.discussion.ForumWatch> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ForumWatch.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.discussion.ForumWatch)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#remove(org.openuss.discussion.ForumWatch)
     */
    public void remove(org.openuss.discussion.ForumWatch forumWatch)
    {
        if (forumWatch == null)
        {
            throw new IllegalArgumentException(
                "ForumWatch.remove - 'forumWatch' can not be null");
        }
        this.getHibernateTemplate().delete(forumWatch);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#remove(java.lang.Long)
     */
    public void remove(org.openuss.discussion.ForumWatchPK forumWatchPk)
    {
        if (forumWatchPk == null)
        {
            throw new IllegalArgumentException(
                "ForumWatch.remove - 'forumWatchPk can not be null");
        }
        org.openuss.discussion.ForumWatch entity = this.load(forumWatchPk);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#remove(java.util.Collection<org.openuss.discussion.ForumWatch>)
     */
    public void remove(java.util.Collection<org.openuss.discussion.ForumWatch> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ForumWatch.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.discussion.ForumWatchDao#findByUser(org.openuss.security.User)
     */
    public java.util.List findByUser(org.openuss.security.User user)
    {
        return this.findByUser(TRANSFORM_NONE, user);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#findByUser(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findByUser(final java.lang.String queryString, final org.openuss.security.User user)
    {
        return this.findByUser(TRANSFORM_NONE, queryString, user);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#findByUser(int, org.openuss.security.User)
     */
    public java.util.List findByUser(final int transform, final org.openuss.security.User user)
    {
        return this.findByUser(transform, "from org.openuss.discussion.ForumWatch as forumWatch where forumWatch.user = ?", user);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#findByUser(int, java.lang.String, org.openuss.security.User)
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
     * @see org.openuss.discussion.ForumWatchDao#findByForum(org.openuss.discussion.Forum)
     */
    public java.util.List findByForum(org.openuss.discussion.Forum forum)
    {
        return this.findByForum(TRANSFORM_NONE, forum);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#findByForum(java.lang.String, org.openuss.discussion.Forum)
     */
    public java.util.List findByForum(final java.lang.String queryString, final org.openuss.discussion.Forum forum)
    {
        return this.findByForum(TRANSFORM_NONE, queryString, forum);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#findByForum(int, org.openuss.discussion.Forum)
     */
    public java.util.List findByForum(final int transform, final org.openuss.discussion.Forum forum)
    {
        return this.findByForum(transform, "from org.openuss.discussion.ForumWatch as forumWatch where forumWatch.forum = ?", forum);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#findByForum(int, java.lang.String, org.openuss.discussion.Forum)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByForum(final int transform, final java.lang.String queryString, final org.openuss.discussion.Forum forum)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, forum);
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
     * @see org.openuss.discussion.ForumWatchDao#findByUserAndForum(org.openuss.security.User, org.openuss.discussion.Forum)
     */
    public org.openuss.discussion.ForumWatch findByUserAndForum(org.openuss.security.User user, org.openuss.discussion.Forum forum)
    {
        return (org.openuss.discussion.ForumWatch)this.findByUserAndForum(TRANSFORM_NONE, user, forum);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#findByUserAndForum(java.lang.String, org.openuss.security.User, org.openuss.discussion.Forum)
     */
    public org.openuss.discussion.ForumWatch findByUserAndForum(final java.lang.String queryString, final org.openuss.security.User user, final org.openuss.discussion.Forum forum)
    {
        return (org.openuss.discussion.ForumWatch)this.findByUserAndForum(TRANSFORM_NONE, queryString, user, forum);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#findByUserAndForum(int, org.openuss.security.User, org.openuss.discussion.Forum)
     */
    public java.lang.Object findByUserAndForum(final int transform, final org.openuss.security.User user, final org.openuss.discussion.Forum forum)
    {
        return this.findByUserAndForum(transform, "from org.openuss.discussion.ForumWatch as forumWatch where forumWatch.user = ? and forumWatch.forum = ?", user, forum);
    }

    /**
     * @see org.openuss.discussion.ForumWatchDao#findByUserAndForum(int, java.lang.String, org.openuss.security.User, org.openuss.discussion.Forum)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByUserAndForum(final int transform, final java.lang.String queryString, final org.openuss.security.User user, final org.openuss.discussion.Forum forum)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, user);
            queryObject.setParameter(1, forum);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.discussion.ForumWatch"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.discussion.ForumWatch)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.discussion.ForumWatchDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.discussion.ForumWatchDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.discussion.ForumWatch entity)
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
     * {@link #transformEntity(int,org.openuss.discussion.ForumWatch)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.discussion.ForumWatchDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.discussion.ForumWatch)
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