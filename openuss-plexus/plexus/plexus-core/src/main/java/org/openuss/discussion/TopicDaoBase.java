package org.openuss.discussion;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.discussion.Topic</code>.
 * </p>
 *
 * @see org.openuss.discussion.Topic
 */
public abstract class TopicDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.discussion.TopicDao
{


    /**
     * @see org.openuss.discussion.TopicDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Topic.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.discussion.TopicImpl.class, id);
        return transformEntity(transform, (org.openuss.discussion.Topic)entity);
    }

    /**
     * @see org.openuss.discussion.TopicDao#load(java.lang.Long)
     */
    public org.openuss.discussion.Topic load(java.lang.Long id)
    {
        return (org.openuss.discussion.Topic)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.discussion.TopicDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.Topic> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.discussion.TopicDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.discussion.TopicImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.discussion.TopicDao#create(org.openuss.discussion.Topic)
     */
    public org.openuss.discussion.Topic create(org.openuss.discussion.Topic topic)
    {
        return (org.openuss.discussion.Topic)this.create(TRANSFORM_NONE, topic);
    }

    /**
     * @see org.openuss.discussion.TopicDao#create(int transform, org.openuss.discussion.Topic)
     */
    public java.lang.Object create(final int transform, final org.openuss.discussion.Topic topic)
    {
        if (topic == null)
        {
            throw new IllegalArgumentException(
                "Topic.create - 'topic' can not be null");
        }
        this.getHibernateTemplate().save(topic);
        return this.transformEntity(transform, topic);
    }

    /**
     * @see org.openuss.discussion.TopicDao#create(java.util.Collection<org.openuss.discussion.Topic>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.Topic> create(final java.util.Collection<org.openuss.discussion.Topic> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.discussion.TopicDao#create(int, java.util.Collection<org.openuss.discussion.Topic>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.discussion.Topic> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Topic.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.discussion.Topic)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.discussion.TopicDao#update(org.openuss.discussion.Topic)
     */
    public void update(org.openuss.discussion.Topic topic)
    {
        if (topic == null)
        {
            throw new IllegalArgumentException(
                "Topic.update - 'topic' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(topic);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(topic);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.discussion.TopicDao#update(java.util.Collection<org.openuss.discussion.Topic>)
     */
    public void update(final java.util.Collection<org.openuss.discussion.Topic> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Topic.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.discussion.Topic)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.discussion.TopicDao#remove(org.openuss.discussion.Topic)
     */
    public void remove(org.openuss.discussion.Topic topic)
    {
        if (topic == null)
        {
            throw new IllegalArgumentException(
                "Topic.remove - 'topic' can not be null");
        }
        this.getHibernateTemplate().delete(topic);
    }

    /**
     * @see org.openuss.discussion.TopicDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Topic.remove - 'id can not be null");
        }
        org.openuss.discussion.Topic entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.discussion.TopicDao#remove(java.util.Collection<org.openuss.discussion.Topic>)
     */
    public void remove(java.util.Collection<org.openuss.discussion.Topic> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Topic.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.discussion.TopicDao#findBySubmitter(org.openuss.security.User)
     */
    public java.util.List findBySubmitter(org.openuss.security.User submitter)
    {
        return this.findBySubmitter(TRANSFORM_NONE, submitter);
    }

    /**
     * @see org.openuss.discussion.TopicDao#findBySubmitter(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findBySubmitter(final java.lang.String queryString, final org.openuss.security.User submitter)
    {
        return this.findBySubmitter(TRANSFORM_NONE, queryString, submitter);
    }

    /**
     * @see org.openuss.discussion.TopicDao#findBySubmitter(int, org.openuss.security.User)
     */
    public java.util.List findBySubmitter(final int transform, final org.openuss.security.User submitter)
    {
        return this.findBySubmitter(transform, "from org.openuss.discussion.Topic as topic where topic.submitter = ?", submitter);
    }

    /**
     * @see org.openuss.discussion.TopicDao#findBySubmitter(int, java.lang.String, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findBySubmitter(final int transform, final java.lang.String queryString, final org.openuss.security.User submitter)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, submitter);
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
     * @see org.openuss.discussion.TopicDao#findByForum(org.openuss.discussion.Forum)
     */
    public java.util.List findByForum(org.openuss.discussion.Forum forum)
    {
        return this.findByForum(TRANSFORM_NONE, forum);
    }

    /**
     * @see org.openuss.discussion.TopicDao#findByForum(java.lang.String, org.openuss.discussion.Forum)
     */
    public java.util.List findByForum(final java.lang.String queryString, final org.openuss.discussion.Forum forum)
    {
        return this.findByForum(TRANSFORM_NONE, queryString, forum);
    }

    /**
     * @see org.openuss.discussion.TopicDao#findByForum(int, org.openuss.discussion.Forum)
     */
    public java.util.List findByForum(final int transform, final org.openuss.discussion.Forum forum)
    {
        return this.findByForum(transform, "from org.openuss.discussion.Topic as topic where topic.forum = ?", forum);
    }

    /**
     * @see org.openuss.discussion.TopicDao#findByForum(int, java.lang.String, org.openuss.discussion.Forum)
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
     * @see org.openuss.discussion.TopicDao#loadTopicsWithViewState(org.openuss.discussion.Forum, org.openuss.security.User)
     */
    public java.util.List loadTopicsWithViewState(final org.openuss.discussion.Forum forum, final org.openuss.security.User user)
    {
        try
        {
            return this.handleLoadTopicsWithViewState(forum, user);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.discussion.TopicDao.loadTopicsWithViewState(org.openuss.discussion.Forum forum, org.openuss.security.User user)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #loadTopicsWithViewState(org.openuss.discussion.Forum, org.openuss.security.User)}
      */
    protected abstract java.util.List handleLoadTopicsWithViewState(org.openuss.discussion.Forum forum, org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.TopicDao#findUsersToNotifyByTopic(org.openuss.discussion.Topic)
     */
    public java.util.List findUsersToNotifyByTopic(final org.openuss.discussion.Topic topic)
    {
        try
        {
            return this.handleFindUsersToNotifyByTopic(topic);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.discussion.TopicDao.findUsersToNotifyByTopic(org.openuss.discussion.Topic topic)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #findUsersToNotifyByTopic(org.openuss.discussion.Topic)}
      */
    protected abstract java.util.List handleFindUsersToNotifyByTopic(org.openuss.discussion.Topic topic)
        throws java.lang.Exception;

    /**
     * @see org.openuss.discussion.TopicDao#findUsersToNotifyByForum(org.openuss.discussion.Topic, org.openuss.discussion.Forum)
     */
    public java.util.List findUsersToNotifyByForum(final org.openuss.discussion.Topic topic, final org.openuss.discussion.Forum forum)
    {
        try
        {
            return this.handleFindUsersToNotifyByForum(topic, forum);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.discussion.TopicDao.findUsersToNotifyByForum(org.openuss.discussion.Topic topic, org.openuss.discussion.Forum forum)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #findUsersToNotifyByForum(org.openuss.discussion.Topic, org.openuss.discussion.Forum)}
      */
    protected abstract java.util.List handleFindUsersToNotifyByForum(org.openuss.discussion.Topic topic, org.openuss.discussion.Forum forum)
        throws java.lang.Exception;

    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.discussion.TopicDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.discussion.Topic} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.discussion.TopicInfo} - {@link TRANSFORM_TOPICINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.discussion.TopicDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.discussion.Topic entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_TOPICINFO :
                    target = toTopicInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.discussion.Topic)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.discussion.TopicDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.discussion.Topic)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_TOPICINFO :
                toTopicInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.discussion.TopicDao#toTopicInfoCollection(java.util.Collection)
     */
    public final void toTopicInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, TOPICINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link TopicDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.discussion.TopicDao#toTopicInfo(org.openuss.discussion.Topic)
     */
    protected org.openuss.discussion.TopicInfo toTopicInfo(java.lang.Object[] row)
    {
        org.openuss.discussion.TopicInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.discussion.Topic)
                {
                    target = this.toTopicInfo((org.openuss.discussion.Topic)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.discussion.TopicInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer TOPICINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.discussion.Topic)
                {
                    result = toTopicInfo((org.openuss.discussion.Topic)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toTopicInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.discussion.TopicDao#topicInfoToEntityCollection(java.util.Collection)
     */
    public final void topicInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.discussion.TopicInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, TopicInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer TopicInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return topicInfoToEntity((org.openuss.discussion.TopicInfo)input);
            }
        };

    /**
     * @see org.openuss.discussion.TopicDao#toTopicInfo(org.openuss.discussion.Topic, org.openuss.discussion.TopicInfo)
     */
    public void toTopicInfo(
        org.openuss.discussion.Topic source,
        org.openuss.discussion.TopicInfo target)
    {
        // No conversion for target.submitter (can't convert source.getSubmitter():org.openuss.security.User to java.lang.String)
        target.setHits(source.getHits());
        target.setReadOnly(source.isReadOnly());
        target.setId(source.getId());
    }

    /**
     * @see org.openuss.discussion.TopicDao#toTopicInfo(org.openuss.discussion.Topic)
     */
    public org.openuss.discussion.TopicInfo toTopicInfo(final org.openuss.discussion.Topic entity)
    {
        final org.openuss.discussion.TopicInfo target = new org.openuss.discussion.TopicInfo();
        this.toTopicInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.discussion.TopicDao#topicInfoToEntity(org.openuss.discussion.TopicInfo, org.openuss.discussion.Topic)
     */
    public void topicInfoToEntity(
        org.openuss.discussion.TopicInfo source,
        org.openuss.discussion.Topic target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getHits() != null)
        {
            target.setHits(source.getHits());
        }
	    target.setReadOnly(source.isReadOnly());
    }
    
}