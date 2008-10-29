package org.openuss.discussion;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.discussion.Post</code>.
 * </p>
 *
 * @see org.openuss.discussion.Post
 */
public abstract class PostDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.discussion.PostDao
{


    /**
     * @see org.openuss.discussion.PostDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Post.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.discussion.PostImpl.class, id);
        return transformEntity(transform, (org.openuss.discussion.Post)entity);
    }

    /**
     * @see org.openuss.discussion.PostDao#load(java.lang.Long)
     */
    public org.openuss.discussion.Post load(java.lang.Long id)
    {
        return (org.openuss.discussion.Post)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.discussion.PostDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.Post> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.discussion.PostDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.discussion.PostImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.discussion.PostDao#create(org.openuss.discussion.Post)
     */
    public org.openuss.discussion.Post create(org.openuss.discussion.Post post)
    {
        return (org.openuss.discussion.Post)this.create(TRANSFORM_NONE, post);
    }

    /**
     * @see org.openuss.discussion.PostDao#create(int transform, org.openuss.discussion.Post)
     */
    public java.lang.Object create(final int transform, final org.openuss.discussion.Post post)
    {
        if (post == null)
        {
            throw new IllegalArgumentException(
                "Post.create - 'post' can not be null");
        }
        this.getHibernateTemplate().save(post);
        return this.transformEntity(transform, post);
    }

    /**
     * @see org.openuss.discussion.PostDao#create(java.util.Collection<org.openuss.discussion.Post>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.Post> create(final java.util.Collection<org.openuss.discussion.Post> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.discussion.PostDao#create(int, java.util.Collection<org.openuss.discussion.Post>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.discussion.Post> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Post.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.discussion.Post)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.discussion.PostDao#update(org.openuss.discussion.Post)
     */
    public void update(org.openuss.discussion.Post post)
    {
        if (post == null)
        {
            throw new IllegalArgumentException(
                "Post.update - 'post' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(post);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(post);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.discussion.PostDao#update(java.util.Collection<org.openuss.discussion.Post>)
     */
    public void update(final java.util.Collection<org.openuss.discussion.Post> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Post.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.discussion.Post)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.discussion.PostDao#remove(org.openuss.discussion.Post)
     */
    public void remove(org.openuss.discussion.Post post)
    {
        if (post == null)
        {
            throw new IllegalArgumentException(
                "Post.remove - 'post' can not be null");
        }
        this.getHibernateTemplate().delete(post);
    }

    /**
     * @see org.openuss.discussion.PostDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Post.remove - 'id can not be null");
        }
        org.openuss.discussion.Post entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.discussion.PostDao#remove(java.util.Collection<org.openuss.discussion.Post>)
     */
    public void remove(java.util.Collection<org.openuss.discussion.Post> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Post.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.discussion.PostDao#findByTopic(org.openuss.discussion.Topic)
     */
    public java.util.List findByTopic(org.openuss.discussion.Topic topic)
    {
        return this.findByTopic(TRANSFORM_NONE, topic);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByTopic(java.lang.String, org.openuss.discussion.Topic)
     */
    public java.util.List findByTopic(final java.lang.String queryString, final org.openuss.discussion.Topic topic)
    {
        return this.findByTopic(TRANSFORM_NONE, queryString, topic);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByTopic(int, org.openuss.discussion.Topic)
     */
    public java.util.List findByTopic(final int transform, final org.openuss.discussion.Topic topic)
    {
        return this.findByTopic(transform, "from org.openuss.discussion.Post as post where post.topic = ?", topic);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByTopic(int, java.lang.String, org.openuss.discussion.Topic)
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
     * @see org.openuss.discussion.PostDao#findByUser(org.openuss.security.User)
     */
    public java.util.List findByUser(org.openuss.security.User submitter)
    {
        return this.findByUser(TRANSFORM_NONE, submitter);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByUser(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findByUser(final java.lang.String queryString, final org.openuss.security.User submitter)
    {
        return this.findByUser(TRANSFORM_NONE, queryString, submitter);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByUser(int, org.openuss.security.User)
     */
    public java.util.List findByUser(final int transform, final org.openuss.security.User submitter)
    {
        return this.findByUser(transform, "from org.openuss.discussion.Post as post where post.submitter = ?", submitter);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByUser(int, java.lang.String, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByUser(final int transform, final java.lang.String queryString, final org.openuss.security.User submitter)
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
     * @see org.openuss.discussion.PostDao#findByTopicAndUser(org.openuss.discussion.Topic, org.openuss.security.User)
     */
    public java.util.List findByTopicAndUser(org.openuss.discussion.Topic topic, org.openuss.security.User submitter)
    {
        return this.findByTopicAndUser(TRANSFORM_NONE, topic, submitter);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByTopicAndUser(java.lang.String, org.openuss.discussion.Topic, org.openuss.security.User)
     */
    public java.util.List findByTopicAndUser(final java.lang.String queryString, final org.openuss.discussion.Topic topic, final org.openuss.security.User submitter)
    {
        return this.findByTopicAndUser(TRANSFORM_NONE, queryString, topic, submitter);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByTopicAndUser(int, org.openuss.discussion.Topic, org.openuss.security.User)
     */
    public java.util.List findByTopicAndUser(final int transform, final org.openuss.discussion.Topic topic, final org.openuss.security.User submitter)
    {
        return this.findByTopicAndUser(transform, "from org.openuss.discussion.Post as post where post.topic = ? and post.submitter = ?", topic, submitter);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByTopicAndUser(int, java.lang.String, org.openuss.discussion.Topic, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByTopicAndUser(final int transform, final java.lang.String queryString, final org.openuss.discussion.Topic topic, final org.openuss.security.User submitter)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, topic);
            queryObject.setParameter(1, submitter);
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
     * @see org.openuss.discussion.PostDao#findByEditor(org.openuss.security.User)
     */
    public java.util.List findByEditor(org.openuss.security.User editor)
    {
        return this.findByEditor(TRANSFORM_NONE, editor);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByEditor(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findByEditor(final java.lang.String queryString, final org.openuss.security.User editor)
    {
        return this.findByEditor(TRANSFORM_NONE, queryString, editor);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByEditor(int, org.openuss.security.User)
     */
    public java.util.List findByEditor(final int transform, final org.openuss.security.User editor)
    {
        return this.findByEditor(transform, "from org.openuss.discussion.Post as post where post.editor = ?", editor);
    }

    /**
     * @see org.openuss.discussion.PostDao#findByEditor(int, java.lang.String, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByEditor(final int transform, final java.lang.String queryString, final org.openuss.security.User editor)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, editor);
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
     * flag is set to one of the constants defined in <code>org.openuss.discussion.PostDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.discussion.Post} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.discussion.PostInfo} - {@link TRANSFORM_POSTINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.discussion.PostDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.discussion.Post entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_POSTINFO :
                    target = toPostInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.discussion.Post)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.discussion.PostDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.discussion.Post)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_POSTINFO :
                toPostInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.discussion.PostDao#toPostInfoCollection(java.util.Collection)
     */
    public final void toPostInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, POSTINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link PostDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.discussion.PostDao#toPostInfo(org.openuss.discussion.Post)
     */
    protected org.openuss.discussion.PostInfo toPostInfo(java.lang.Object[] row)
    {
        org.openuss.discussion.PostInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.discussion.Post)
                {
                    target = this.toPostInfo((org.openuss.discussion.Post)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.discussion.PostInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer POSTINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.discussion.Post)
                {
                    result = toPostInfo((org.openuss.discussion.Post)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toPostInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.discussion.PostDao#postInfoToEntityCollection(java.util.Collection)
     */
    public final void postInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.discussion.PostInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, PostInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer PostInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return postInfoToEntity((org.openuss.discussion.PostInfo)input);
            }
        };

    /**
     * @see org.openuss.discussion.PostDao#toPostInfo(org.openuss.discussion.Post, org.openuss.discussion.PostInfo)
     */
    public void toPostInfo(
        org.openuss.discussion.Post source,
        org.openuss.discussion.PostInfo target)
    {
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setText(source.getText());
        target.setCreated(source.getCreated());
        target.setLastModification(source.getLastModification());
        // No conversion for target.formula (can't convert source.getFormula():org.openuss.discussion.Formula to java.lang.String)
        // No conversion for target.editor (can't convert source.getEditor():org.openuss.security.User to java.lang.String)
        // No conversion for target.submitter (can't convert source.getSubmitter():org.openuss.security.User to java.lang.String)
        target.setIp(source.getIp());
    }

    /**
     * @see org.openuss.discussion.PostDao#toPostInfo(org.openuss.discussion.Post)
     */
    public org.openuss.discussion.PostInfo toPostInfo(final org.openuss.discussion.Post entity)
    {
        final org.openuss.discussion.PostInfo target = new org.openuss.discussion.PostInfo();
        this.toPostInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.discussion.PostDao#postInfoToEntity(org.openuss.discussion.PostInfo, org.openuss.discussion.Post)
     */
    public void postInfoToEntity(
        org.openuss.discussion.PostInfo source,
        org.openuss.discussion.Post target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getTitle() != null)
        {
            target.setTitle(source.getTitle());
        }
        if (copyIfNull || source.getText() != null)
        {
            target.setText(source.getText());
        }
        if (copyIfNull || source.getCreated() != null)
        {
            target.setCreated(source.getCreated());
        }
        if (copyIfNull || source.getLastModification() != null)
        {
            target.setLastModification(source.getLastModification());
        }
        if (copyIfNull || source.getIp() != null)
        {
            target.setIp(source.getIp());
        }
    }
    
}