package org.openuss.discussion;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.discussion.Forum</code>.
 * </p>
 *
 * @see org.openuss.discussion.Forum
 */
public abstract class ForumDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.discussion.ForumDao
{


    /**
     * @see org.openuss.discussion.ForumDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Forum.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.discussion.ForumImpl.class, id);
        return transformEntity(transform, (org.openuss.discussion.Forum)entity);
    }

    /**
     * @see org.openuss.discussion.ForumDao#load(java.lang.Long)
     */
    public org.openuss.discussion.Forum load(java.lang.Long id)
    {
        return (org.openuss.discussion.Forum)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.discussion.ForumDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.Forum> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.discussion.ForumDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.discussion.ForumImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.discussion.ForumDao#create(org.openuss.discussion.Forum)
     */
    public org.openuss.discussion.Forum create(org.openuss.discussion.Forum forum)
    {
        return (org.openuss.discussion.Forum)this.create(TRANSFORM_NONE, forum);
    }

    /**
     * @see org.openuss.discussion.ForumDao#create(int transform, org.openuss.discussion.Forum)
     */
    public java.lang.Object create(final int transform, final org.openuss.discussion.Forum forum)
    {
        if (forum == null)
        {
            throw new IllegalArgumentException(
                "Forum.create - 'forum' can not be null");
        }
        this.getHibernateTemplate().save(forum);
        return this.transformEntity(transform, forum);
    }

    /**
     * @see org.openuss.discussion.ForumDao#create(java.util.Collection<org.openuss.discussion.Forum>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.Forum> create(final java.util.Collection<org.openuss.discussion.Forum> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.discussion.ForumDao#create(int, java.util.Collection<org.openuss.discussion.Forum>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.discussion.Forum> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Forum.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.discussion.Forum)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.discussion.ForumDao#update(org.openuss.discussion.Forum)
     */
    public void update(org.openuss.discussion.Forum forum)
    {
        if (forum == null)
        {
            throw new IllegalArgumentException(
                "Forum.update - 'forum' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(forum);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(forum);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.discussion.ForumDao#update(java.util.Collection<org.openuss.discussion.Forum>)
     */
    public void update(final java.util.Collection<org.openuss.discussion.Forum> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Forum.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.discussion.Forum)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.discussion.ForumDao#remove(org.openuss.discussion.Forum)
     */
    public void remove(org.openuss.discussion.Forum forum)
    {
        if (forum == null)
        {
            throw new IllegalArgumentException(
                "Forum.remove - 'forum' can not be null");
        }
        this.getHibernateTemplate().delete(forum);
    }

    /**
     * @see org.openuss.discussion.ForumDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Forum.remove - 'id can not be null");
        }
        org.openuss.discussion.Forum entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.discussion.ForumDao#remove(java.util.Collection<org.openuss.discussion.Forum>)
     */
    public void remove(java.util.Collection<org.openuss.discussion.Forum> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Forum.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.discussion.ForumDao#findByDomainIdentifier(java.lang.Long)
     */
    public org.openuss.discussion.Forum findByDomainIdentifier(java.lang.Long domainIdentifier)
    {
        return (org.openuss.discussion.Forum)this.findByDomainIdentifier(TRANSFORM_NONE, domainIdentifier);
    }

    /**
     * @see org.openuss.discussion.ForumDao#findByDomainIdentifier(java.lang.String, java.lang.Long)
     */
    public org.openuss.discussion.Forum findByDomainIdentifier(final java.lang.String queryString, final java.lang.Long domainIdentifier)
    {
        return (org.openuss.discussion.Forum)this.findByDomainIdentifier(TRANSFORM_NONE, queryString, domainIdentifier);
    }

    /**
     * @see org.openuss.discussion.ForumDao#findByDomainIdentifier(int, java.lang.Long)
     */
    public java.lang.Object findByDomainIdentifier(final int transform, final java.lang.Long domainIdentifier)
    {
        return this.findByDomainIdentifier(transform, "from org.openuss.discussion.Forum as forum where forum.domainIdentifier = ?", domainIdentifier);
    }

    /**
     * @see org.openuss.discussion.ForumDao#findByDomainIdentifier(int, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByDomainIdentifier(final int transform, final java.lang.String queryString, final java.lang.Long domainIdentifier)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, domainIdentifier);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.discussion.Forum"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.discussion.Forum)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.discussion.ForumDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.discussion.Forum} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.discussion.ForumInfo} - {@link TRANSFORM_FORUMINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.discussion.ForumDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.discussion.Forum entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_FORUMINFO :
                    target = toForumInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.discussion.Forum)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.discussion.ForumDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.discussion.Forum)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_FORUMINFO :
                toForumInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.discussion.ForumDao#toForumInfoCollection(java.util.Collection)
     */
    public final void toForumInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, FORUMINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link ForumDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.discussion.ForumDao#toForumInfo(org.openuss.discussion.Forum)
     */
    protected org.openuss.discussion.ForumInfo toForumInfo(java.lang.Object[] row)
    {
        org.openuss.discussion.ForumInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.discussion.Forum)
                {
                    target = this.toForumInfo((org.openuss.discussion.Forum)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.discussion.ForumInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer FORUMINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.discussion.Forum)
                {
                    result = toForumInfo((org.openuss.discussion.Forum)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toForumInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.discussion.ForumDao#forumInfoToEntityCollection(java.util.Collection)
     */
    public final void forumInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.discussion.ForumInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, ForumInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer ForumInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return forumInfoToEntity((org.openuss.discussion.ForumInfo)input);
            }
        };

    /**
     * @see org.openuss.discussion.ForumDao#toForumInfo(org.openuss.discussion.Forum, org.openuss.discussion.ForumInfo)
     */
    public void toForumInfo(
        org.openuss.discussion.Forum source,
        org.openuss.discussion.ForumInfo target)
    {
        target.setDomainIdentifier(source.getDomainIdentifier());
        target.setReadOnly(source.isReadOnly());
        target.setId(source.getId());
        target.setName(source.getName());
    }

    /**
     * @see org.openuss.discussion.ForumDao#toForumInfo(org.openuss.discussion.Forum)
     */
    public org.openuss.discussion.ForumInfo toForumInfo(final org.openuss.discussion.Forum entity)
    {
        final org.openuss.discussion.ForumInfo target = new org.openuss.discussion.ForumInfo();
        this.toForumInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.discussion.ForumDao#forumInfoToEntity(org.openuss.discussion.ForumInfo, org.openuss.discussion.Forum)
     */
    public void forumInfoToEntity(
        org.openuss.discussion.ForumInfo source,
        org.openuss.discussion.Forum target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getDomainIdentifier() != null)
        {
            target.setDomainIdentifier(source.getDomainIdentifier());
        }
        if (copyIfNull || source.getId() != null)
        {
        	target.setId(source.getId());
        }
        if (copyIfNull || source.getName() != null)
        {
        	target.setName(source.getName());
        }
	    target.setReadOnly(source.isReadOnly());
    }
    
}