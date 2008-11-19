package org.openuss.discussion;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.discussion.DiscussionWatch</code>.
 * </p>
 *
 * @see org.openuss.discussion.DiscussionWatch
 */
public abstract class DiscussionWatchDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.discussion.DiscussionWatchDao
{


    /**
     * @see org.openuss.discussion.DiscussionWatchDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final org.openuss.discussion.DiscussionWatchPK discussionWatchPk)
    {
        if (discussionWatchPk == null)
        {
            throw new IllegalArgumentException(
                "DiscussionWatch.load - 'discussionWatchPk' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.discussion.DiscussionWatchImpl.class, discussionWatchPk);
        return transformEntity(transform, (org.openuss.discussion.DiscussionWatch)entity);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#load(org.openuss.discussion.DiscussionWatchPK)
     */
    public org.openuss.discussion.DiscussionWatch load(org.openuss.discussion.DiscussionWatchPK discussionWatchPk)
    {
        return (org.openuss.discussion.DiscussionWatch)this.load(TRANSFORM_NONE, discussionWatchPk);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.DiscussionWatch> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.discussion.DiscussionWatchImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.discussion.DiscussionWatchDao#create(org.openuss.discussion.DiscussionWatch)
     */
    public org.openuss.discussion.DiscussionWatch create(org.openuss.discussion.DiscussionWatch discussionWatch)
    {
        return (org.openuss.discussion.DiscussionWatch)this.create(TRANSFORM_NONE, discussionWatch);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#create(int transform, org.openuss.discussion.DiscussionWatch)
     */
    public java.lang.Object create(final int transform, final org.openuss.discussion.DiscussionWatch discussionWatch)
    {
        if (discussionWatch == null)
        {
            throw new IllegalArgumentException(
                "DiscussionWatch.create - 'discussionWatch' can not be null");
        }
        this.getHibernateTemplate().save(discussionWatch);
        return this.transformEntity(transform, discussionWatch);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#create(java.util.Collection<org.openuss.discussion.DiscussionWatch>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.DiscussionWatch> create(final java.util.Collection<org.openuss.discussion.DiscussionWatch> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#create(int, java.util.Collection<org.openuss.discussion.DiscussionWatch>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.discussion.DiscussionWatch> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "DiscussionWatch.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.discussion.DiscussionWatch)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#update(org.openuss.discussion.DiscussionWatch)
     */
    public void update(org.openuss.discussion.DiscussionWatch discussionWatch)
    {
        if (discussionWatch == null)
        {
            throw new IllegalArgumentException(
                "DiscussionWatch.update - 'discussionWatch' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(discussionWatch);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(discussionWatch);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#update(java.util.Collection<org.openuss.discussion.DiscussionWatch>)
     */
    public void update(final java.util.Collection<org.openuss.discussion.DiscussionWatch> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "DiscussionWatch.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.discussion.DiscussionWatch)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#remove(org.openuss.discussion.DiscussionWatch)
     */
    public void remove(org.openuss.discussion.DiscussionWatch discussionWatch)
    {
        if (discussionWatch == null)
        {
            throw new IllegalArgumentException(
                "DiscussionWatch.remove - 'discussionWatch' can not be null");
        }
        this.getHibernateTemplate().delete(discussionWatch);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#remove(java.lang.Long)
     */
    public void remove(org.openuss.discussion.DiscussionWatchPK discussionWatchPk)
    {
        if (discussionWatchPk == null)
        {
            throw new IllegalArgumentException(
                "DiscussionWatch.remove - 'discussionWatchPk can not be null");
        }
        org.openuss.discussion.DiscussionWatch entity = this.load(discussionWatchPk);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#remove(java.util.Collection<org.openuss.discussion.DiscussionWatch>)
     */
    public void remove(java.util.Collection<org.openuss.discussion.DiscussionWatch> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "DiscussionWatch.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByTopic(org.openuss.discussion.Topic)
     */
    public java.util.List findByTopic(org.openuss.discussion.Topic topic)
    {
        return this.findByTopic(TRANSFORM_NONE, topic);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByTopic(java.lang.String, org.openuss.discussion.Topic)
     */
    public java.util.List findByTopic(final java.lang.String queryString, final org.openuss.discussion.Topic topic)
    {
        return this.findByTopic(TRANSFORM_NONE, queryString, topic);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByTopic(int, org.openuss.discussion.Topic)
     */
    public java.util.List findByTopic(final int transform, final org.openuss.discussion.Topic topic)
    {
        return this.findByTopic(transform, "from org.openuss.discussion.DiscussionWatch as discussionWatch where discussionWatch.topic = ?", topic);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByTopic(int, java.lang.String, org.openuss.discussion.Topic)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByTopic(final int transform, final java.lang.String queryString, final org.openuss.discussion.Topic topic)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, topic);
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
     * @see org.openuss.discussion.DiscussionWatchDao#findByUser(org.openuss.security.User)
     */
    public java.util.List findByUser(org.openuss.security.User user)
    {
        return this.findByUser(TRANSFORM_NONE, user);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByUser(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findByUser(final java.lang.String queryString, final org.openuss.security.User user)
    {
        return this.findByUser(TRANSFORM_NONE, queryString, user);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByUser(int, org.openuss.security.User)
     */
    public java.util.List findByUser(final int transform, final org.openuss.security.User user)
    {
        return this.findByUser(transform, "from org.openuss.discussion.DiscussionWatch as discussionWatch where discussionWatch.user = ?", user);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByUser(int, java.lang.String, org.openuss.security.User)
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
     * @see org.openuss.discussion.DiscussionWatchDao#findByTopicAndUser(org.openuss.discussion.Topic, org.openuss.security.User)
     */
    public org.openuss.discussion.DiscussionWatch findByTopicAndUser(org.openuss.discussion.Topic topic, org.openuss.security.User user)
    {
        return (org.openuss.discussion.DiscussionWatch)this.findByTopicAndUser(TRANSFORM_NONE, topic, user);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByTopicAndUser(java.lang.String, org.openuss.discussion.Topic, org.openuss.security.User)
     */
    public org.openuss.discussion.DiscussionWatch findByTopicAndUser(final java.lang.String queryString, final org.openuss.discussion.Topic topic, final org.openuss.security.User user)
    {
        return (org.openuss.discussion.DiscussionWatch)this.findByTopicAndUser(TRANSFORM_NONE, queryString, topic, user);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByTopicAndUser(int, org.openuss.discussion.Topic, org.openuss.security.User)
     */
    public java.lang.Object findByTopicAndUser(final int transform, final org.openuss.discussion.Topic topic, final org.openuss.security.User user)
    {
        return this.findByTopicAndUser(transform, "from org.openuss.discussion.DiscussionWatch as discussionWatch where discussionWatch.topic = ? and discussionWatch.user = ?", topic, user);
    }

    /**
     * @see org.openuss.discussion.DiscussionWatchDao#findByTopicAndUser(int, java.lang.String, org.openuss.discussion.Topic, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByTopicAndUser(final int transform, final java.lang.String queryString, final org.openuss.discussion.Topic topic, final org.openuss.security.User user)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, topic);
            queryObject.setParameter(1, user);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.discussion.DiscussionWatch"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.discussion.DiscussionWatch)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.discussion.DiscussionWatchDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.discussion.DiscussionWatchDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.discussion.DiscussionWatch entity)
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
     * {@link #transformEntity(int,org.openuss.discussion.DiscussionWatch)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.discussion.DiscussionWatchDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.discussion.DiscussionWatch)
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