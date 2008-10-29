package org.openuss.statistics;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.statistics.SystemStatistic</code>.
 * </p>
 *
 * @see org.openuss.statistics.SystemStatistic
 */
public abstract class SystemStatisticDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.statistics.SystemStatisticDao
{


    /**
     * @see org.openuss.statistics.SystemStatisticDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "SystemStatistic.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.statistics.SystemStatisticImpl.class, id);
        return transformEntity(transform, (org.openuss.statistics.SystemStatistic)entity);
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#load(java.lang.Long)
     */
    public org.openuss.statistics.SystemStatistic load(java.lang.Long id)
    {
        return (org.openuss.statistics.SystemStatistic)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.statistics.SystemStatistic> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.statistics.SystemStatisticImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.statistics.SystemStatisticDao#create(org.openuss.statistics.SystemStatistic)
     */
    public org.openuss.statistics.SystemStatistic create(org.openuss.statistics.SystemStatistic systemStatistic)
    {
        return (org.openuss.statistics.SystemStatistic)this.create(TRANSFORM_NONE, systemStatistic);
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#create(int transform, org.openuss.statistics.SystemStatistic)
     */
    public java.lang.Object create(final int transform, final org.openuss.statistics.SystemStatistic systemStatistic)
    {
        if (systemStatistic == null)
        {
            throw new IllegalArgumentException(
                "SystemStatistic.create - 'systemStatistic' can not be null");
        }
        this.getHibernateTemplate().save(systemStatistic);
        return this.transformEntity(transform, systemStatistic);
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#create(java.util.Collection<org.openuss.statistics.SystemStatistic>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.statistics.SystemStatistic> create(final java.util.Collection<org.openuss.statistics.SystemStatistic> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#create(int, java.util.Collection<org.openuss.statistics.SystemStatistic>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.statistics.SystemStatistic> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "SystemStatistic.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.statistics.SystemStatistic)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#update(org.openuss.statistics.SystemStatistic)
     */
    public void update(org.openuss.statistics.SystemStatistic systemStatistic)
    {
        if (systemStatistic == null)
        {
            throw new IllegalArgumentException(
                "SystemStatistic.update - 'systemStatistic' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(systemStatistic);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(systemStatistic);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#update(java.util.Collection<org.openuss.statistics.SystemStatistic>)
     */
    public void update(final java.util.Collection<org.openuss.statistics.SystemStatistic> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "SystemStatistic.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.statistics.SystemStatistic)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#remove(org.openuss.statistics.SystemStatistic)
     */
    public void remove(org.openuss.statistics.SystemStatistic systemStatistic)
    {
        if (systemStatistic == null)
        {
            throw new IllegalArgumentException(
                "SystemStatistic.remove - 'systemStatistic' can not be null");
        }
        this.getHibernateTemplate().delete(systemStatistic);
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "SystemStatistic.remove - 'id can not be null");
        }
        org.openuss.statistics.SystemStatistic entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#remove(java.util.Collection<org.openuss.statistics.SystemStatistic>)
     */
    public void remove(java.util.Collection<org.openuss.statistics.SystemStatistic> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "SystemStatistic.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.statistics.SystemStatisticDao#findNewest()
     */
    public org.openuss.statistics.SystemStatistic findNewest()
    {
        try
        {
            return this.handleFindNewest();
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.statistics.SystemStatisticDao.findNewest()' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #findNewest()}
      */
    protected abstract org.openuss.statistics.SystemStatistic handleFindNewest()
        throws java.lang.Exception;

    /**
     * @see org.openuss.statistics.SystemStatisticDao#current()
     */
    public org.openuss.statistics.SystemStatistic current()
    {
        try
        {
            return this.handleCurrent();
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.statistics.SystemStatisticDao.current()' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #current()}
      */
    protected abstract org.openuss.statistics.SystemStatistic handleCurrent()
        throws java.lang.Exception;

    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.statistics.SystemStatisticDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.statistics.SystemStatistic} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.statistics.SystemStatisticInfo} - {@link TRANSFORM_SYSTEMSTATISTICINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.statistics.SystemStatisticDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.statistics.SystemStatistic entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_SYSTEMSTATISTICINFO :
                    target = toSystemStatisticInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.statistics.SystemStatistic)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.statistics.SystemStatisticDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.statistics.SystemStatistic)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_SYSTEMSTATISTICINFO :
                toSystemStatisticInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#toSystemStatisticInfoCollection(java.util.Collection)
     */
    public final void toSystemStatisticInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, SYSTEMSTATISTICINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link SystemStatisticDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.statistics.SystemStatisticDao#toSystemStatisticInfo(org.openuss.statistics.SystemStatistic)
     */
    protected org.openuss.statistics.SystemStatisticInfo toSystemStatisticInfo(java.lang.Object[] row)
    {
        org.openuss.statistics.SystemStatisticInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.statistics.SystemStatistic)
                {
                    target = this.toSystemStatisticInfo((org.openuss.statistics.SystemStatistic)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.statistics.SystemStatisticInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer SYSTEMSTATISTICINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.statistics.SystemStatistic)
                {
                    result = toSystemStatisticInfo((org.openuss.statistics.SystemStatistic)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toSystemStatisticInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.statistics.SystemStatisticDao#systemStatisticInfoToEntityCollection(java.util.Collection)
     */
    public final void systemStatisticInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.statistics.SystemStatisticInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, SystemStatisticInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer SystemStatisticInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return systemStatisticInfoToEntity((org.openuss.statistics.SystemStatisticInfo)input);
            }
        };

    /**
     * @see org.openuss.statistics.SystemStatisticDao#toSystemStatisticInfo(org.openuss.statistics.SystemStatistic, org.openuss.statistics.SystemStatisticInfo)
     */
    public void toSystemStatisticInfo(
        org.openuss.statistics.SystemStatistic source,
        org.openuss.statistics.SystemStatisticInfo target)
    {
        target.setId(source.getId());
        target.setPosts(source.getPosts());
        target.setUniversities(source.getUniversities());
        target.setDepartments(source.getDepartments());
        target.setDocuments(source.getDocuments());
        target.setCourses(source.getCourses());
        target.setInstitutes(source.getInstitutes());
        target.setUsers(source.getUsers());
        target.setCreateTime(source.getCreateTime());
    }

    /**
     * @see org.openuss.statistics.SystemStatisticDao#toSystemStatisticInfo(org.openuss.statistics.SystemStatistic)
     */
    public org.openuss.statistics.SystemStatisticInfo toSystemStatisticInfo(final org.openuss.statistics.SystemStatistic entity)
    {
        final org.openuss.statistics.SystemStatisticInfo target = new org.openuss.statistics.SystemStatisticInfo();
        this.toSystemStatisticInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.statistics.SystemStatisticDao#systemStatisticInfoToEntity(org.openuss.statistics.SystemStatisticInfo, org.openuss.statistics.SystemStatistic)
     */
    public void systemStatisticInfoToEntity(
        org.openuss.statistics.SystemStatisticInfo source,
        org.openuss.statistics.SystemStatistic target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getUsers() != null)
        {
            target.setUsers(source.getUsers());
        }
        if (copyIfNull || source.getUniversities() != null)
        {
            target.setUniversities(source.getUniversities());
        }
        if (copyIfNull || source.getDepartments() != null)
        {
            target.setDepartments(source.getDepartments());
        }
        if (copyIfNull || source.getInstitutes() != null)
        {
            target.setInstitutes(source.getInstitutes());
        }
        if (copyIfNull || source.getCourses() != null)
        {
            target.setCourses(source.getCourses());
        }
        if (copyIfNull || source.getDocuments() != null)
        {
            target.setDocuments(source.getDocuments());
        }
        if (copyIfNull || source.getPosts() != null)
        {
            target.setPosts(source.getPosts());
        }
        if (copyIfNull || source.getCreateTime() != null)
        {
            target.setCreateTime(source.getCreateTime());
        }
    }
    
}