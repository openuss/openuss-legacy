package org.openuss.messaging;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.messaging.MessageJob</code>.
 * </p>
 *
 * @see org.openuss.messaging.MessageJob
 */
public abstract class MessageJobDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.messaging.MessageJobDao
{


    /**
     * @see org.openuss.messaging.MessageJobDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "MessageJob.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.messaging.MessageJobImpl.class, id);
        return transformEntity(transform, (org.openuss.messaging.MessageJob)entity);
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#load(java.lang.Long)
     */
    public org.openuss.messaging.MessageJob load(java.lang.Long id)
    {
        return (org.openuss.messaging.MessageJob)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.messaging.MessageJob> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.messaging.MessageJobImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.messaging.MessageJobDao#create(org.openuss.messaging.MessageJob)
     */
    public org.openuss.messaging.MessageJob create(org.openuss.messaging.MessageJob messageJob)
    {
        return (org.openuss.messaging.MessageJob)this.create(TRANSFORM_NONE, messageJob);
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#create(int transform, org.openuss.messaging.MessageJob)
     */
    public java.lang.Object create(final int transform, final org.openuss.messaging.MessageJob messageJob)
    {
        if (messageJob == null)
        {
            throw new IllegalArgumentException(
                "MessageJob.create - 'messageJob' can not be null");
        }
        this.getHibernateTemplate().save(messageJob);
        return this.transformEntity(transform, messageJob);
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#create(java.util.Collection<org.openuss.messaging.MessageJob>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.messaging.MessageJob> create(final java.util.Collection<org.openuss.messaging.MessageJob> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#create(int, java.util.Collection<org.openuss.messaging.MessageJob>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.messaging.MessageJob> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "MessageJob.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.messaging.MessageJob)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#update(org.openuss.messaging.MessageJob)
     */
    public void update(org.openuss.messaging.MessageJob messageJob)
    {
        if (messageJob == null)
        {
            throw new IllegalArgumentException(
                "MessageJob.update - 'messageJob' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(messageJob);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(messageJob);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#update(java.util.Collection<org.openuss.messaging.MessageJob>)
     */
    public void update(final java.util.Collection<org.openuss.messaging.MessageJob> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "MessageJob.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.messaging.MessageJob)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#remove(org.openuss.messaging.MessageJob)
     */
    public void remove(org.openuss.messaging.MessageJob messageJob)
    {
        if (messageJob == null)
        {
            throw new IllegalArgumentException(
                "MessageJob.remove - 'messageJob' can not be null");
        }
        this.getHibernateTemplate().delete(messageJob);
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "MessageJob.remove - 'id can not be null");
        }
        org.openuss.messaging.MessageJob entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#remove(java.util.Collection<org.openuss.messaging.MessageJob>)
     */
    public void remove(java.util.Collection<org.openuss.messaging.MessageJob> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "MessageJob.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.messaging.MessageJobDao#findByState(org.openuss.messaging.JobState)
     */
    public java.util.List findByState(org.openuss.messaging.JobState state)
    {
        return this.findByState(TRANSFORM_NONE, state);
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#findByState(java.lang.String, org.openuss.messaging.JobState)
     */
    public java.util.List findByState(final java.lang.String queryString, final org.openuss.messaging.JobState state)
    {
        return this.findByState(TRANSFORM_NONE, queryString, state);
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#findByState(int, org.openuss.messaging.JobState)
     */
    public java.util.List findByState(final int transform, final org.openuss.messaging.JobState state)
    {
        return this.findByState(transform, "from org.openuss.messaging.MessageJob as messageJob where messageJob.state = ?", state);
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#findByState(int, java.lang.String, org.openuss.messaging.JobState)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByState(final int transform, final java.lang.String queryString, final org.openuss.messaging.JobState state)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, state.getValue());
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
     * flag is set to one of the constants defined in <code>org.openuss.messaging.MessageJobDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.messaging.MessageJob} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.messaging.JobInfo} - {@link TRANSFORM_JOBINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.messaging.MessageJobDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.messaging.MessageJob entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_JOBINFO :
                    target = toJobInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.messaging.MessageJob)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.messaging.MessageJobDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.messaging.MessageJob)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_JOBINFO :
                toJobInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#toJobInfoCollection(java.util.Collection)
     */
    public final void toJobInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, JOBINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link MessageJobDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.messaging.MessageJobDao#toJobInfo(org.openuss.messaging.MessageJob)
     */
    protected org.openuss.messaging.JobInfo toJobInfo(java.lang.Object[] row)
    {
        org.openuss.messaging.JobInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.messaging.MessageJob)
                {
                    target = this.toJobInfo((org.openuss.messaging.MessageJob)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.messaging.JobInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer JOBINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.messaging.MessageJob)
                {
                    result = toJobInfo((org.openuss.messaging.MessageJob)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toJobInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.messaging.MessageJobDao#jobInfoToEntityCollection(java.util.Collection)
     */
    public final void jobInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.messaging.JobInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, JobInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer JobInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return jobInfoToEntity((org.openuss.messaging.JobInfo)input);
            }
        };

    /**
     * @see org.openuss.messaging.MessageJobDao#toJobInfo(org.openuss.messaging.MessageJob, org.openuss.messaging.JobInfo)
     */
    public void toJobInfo(
        org.openuss.messaging.MessageJob source,
        org.openuss.messaging.JobInfo target)
    {
        target.setState(source.getState());
        target.setCreated(source.getCreated());
    }

    /**
     * @see org.openuss.messaging.MessageJobDao#toJobInfo(org.openuss.messaging.MessageJob)
     */
    public org.openuss.messaging.JobInfo toJobInfo(final org.openuss.messaging.MessageJob entity)
    {
        final org.openuss.messaging.JobInfo target = new org.openuss.messaging.JobInfo();
        this.toJobInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.messaging.MessageJobDao#jobInfoToEntity(org.openuss.messaging.JobInfo, org.openuss.messaging.MessageJob)
     */
    public void jobInfoToEntity(
        org.openuss.messaging.JobInfo source,
        org.openuss.messaging.MessageJob target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getCreated() != null)
        {
            target.setCreated(source.getCreated());
        }
        if (copyIfNull || source.getState() != null)
        {
            target.setState(source.getState());
        }
    }
    
}