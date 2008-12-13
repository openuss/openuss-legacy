package org.openuss.lecture;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.lecture.Period</code>.
 * </p>
 *
 * @see org.openuss.lecture.Period
 */
public abstract class PeriodDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.lecture.PeriodDao
{

    private org.openuss.lecture.UniversityDao universityDao;

    /**
     * Sets the reference to <code>universityDao</code>.
     */
    public void setUniversityDao(org.openuss.lecture.UniversityDao universityDao)
    {
        this.universityDao = universityDao;
    }

    /**
     * Gets the reference to <code>universityDao</code>.
     */
    protected org.openuss.lecture.UniversityDao getUniversityDao()
    {
        return this.universityDao;
    }


    /**
     * @see org.openuss.lecture.PeriodDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Period.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.lecture.PeriodImpl.class, id);
        return transformEntity(transform, (org.openuss.lecture.Period)entity);
    }

    /**
     * @see org.openuss.lecture.PeriodDao#load(java.lang.Long)
     */
    public org.openuss.lecture.Period load(java.lang.Long id)
    {
        return (org.openuss.lecture.Period)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.lecture.PeriodDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.Period> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.lecture.PeriodDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.lecture.PeriodImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.lecture.PeriodDao#create(org.openuss.lecture.Period)
     */
    public org.openuss.lecture.Period create(org.openuss.lecture.Period period)
    {
        return (org.openuss.lecture.Period)this.create(TRANSFORM_NONE, period);
    }

    /**
     * @see org.openuss.lecture.PeriodDao#create(int transform, org.openuss.lecture.Period)
     */
    public java.lang.Object create(final int transform, final org.openuss.lecture.Period period)
    {
        if (period == null)
        {
            throw new IllegalArgumentException(
                "Period.create - 'period' can not be null");
        }
        this.getHibernateTemplate().save(period);
        return this.transformEntity(transform, period);
    }

    /**
     * @see org.openuss.lecture.PeriodDao#create(java.util.Collection<org.openuss.lecture.Period>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.Period> create(final java.util.Collection<org.openuss.lecture.Period> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.lecture.PeriodDao#create(int, java.util.Collection<org.openuss.lecture.Period>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.lecture.Period> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Period.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.lecture.Period)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.lecture.PeriodDao#update(org.openuss.lecture.Period)
     */
    public void update(org.openuss.lecture.Period period)
    {
        if (period == null)
        {
            throw new IllegalArgumentException(
                "Period.update - 'period' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(period);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(period);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.lecture.PeriodDao#update(java.util.Collection<org.openuss.lecture.Period>)
     */
    public void update(final java.util.Collection<org.openuss.lecture.Period> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Period.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.lecture.Period)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.lecture.PeriodDao#remove(org.openuss.lecture.Period)
     */
    public void remove(org.openuss.lecture.Period period)
    {
        if (period == null)
        {
            throw new IllegalArgumentException(
                "Period.remove - 'period' can not be null");
        }
        this.getHibernateTemplate().delete(period);
    }

    /**
     * @see org.openuss.lecture.PeriodDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Period.remove - 'id can not be null");
        }
        org.openuss.lecture.Period entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.lecture.PeriodDao#remove(java.util.Collection<org.openuss.lecture.Period>)
     */
    public void remove(java.util.Collection<org.openuss.lecture.Period> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Period.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.lecture.PeriodDao#findByUniversity(org.openuss.lecture.University)
     */
    public java.util.List findByUniversity(org.openuss.lecture.University university)
    {
        return this.findByUniversity(TRANSFORM_NONE, university);
    }

    /**
     * @see org.openuss.lecture.PeriodDao#findByUniversity(java.lang.String, org.openuss.lecture.University)
     */
    public java.util.List findByUniversity(final java.lang.String queryString, final org.openuss.lecture.University university)
    {
        return this.findByUniversity(TRANSFORM_NONE, queryString, university);
    }

    /**
     * @see org.openuss.lecture.PeriodDao#findByUniversity(int, org.openuss.lecture.University)
     */
    public java.util.List findByUniversity(final int transform, final org.openuss.lecture.University university)
    {
        return this.findByUniversity(transform, "from org.openuss.lecture.Period as f where f.university = :university", university);
    }

    /**
     * @see org.openuss.lecture.PeriodDao#findByUniversity(int, java.lang.String, org.openuss.lecture.University)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByUniversity(final int transform, final java.lang.String queryString, final org.openuss.lecture.University university)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("university", university);
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
     * flag is set to one of the constants defined in <code>org.openuss.lecture.PeriodDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.lecture.Period} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.lecture.PeriodInfo} - {@link TRANSFORM_PERIODINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.lecture.PeriodDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.lecture.Period entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_PERIODINFO :
                    target = toPeriodInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.lecture.Period)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.lecture.PeriodDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.lecture.Period)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_PERIODINFO :
                toPeriodInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.lecture.PeriodDao#toPeriodInfoCollection(java.util.Collection)
     */
    public final void toPeriodInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, PERIODINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link PeriodDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.lecture.PeriodDao#toPeriodInfo(org.openuss.lecture.Period)
     */
    protected org.openuss.lecture.PeriodInfo toPeriodInfo(java.lang.Object[] row)
    {
        org.openuss.lecture.PeriodInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.lecture.Period)
                {
                    target = this.toPeriodInfo((org.openuss.lecture.Period)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.lecture.PeriodInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer PERIODINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.lecture.Period)
                {
                    result = toPeriodInfo((org.openuss.lecture.Period)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toPeriodInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.lecture.PeriodDao#periodInfoToEntityCollection(java.util.Collection)
     */
    public final void periodInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.lecture.PeriodInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, PeriodInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer PeriodInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return periodInfoToEntity((org.openuss.lecture.PeriodInfo)input);
            }
        };

    /**
     * @see org.openuss.lecture.PeriodDao#toPeriodInfo(org.openuss.lecture.Period, org.openuss.lecture.PeriodInfo)
     */
    public void toPeriodInfo(
        org.openuss.lecture.Period source,
        org.openuss.lecture.PeriodInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setStartdate(source.getStartdate());
        target.setEnddate(source.getEnddate());
        target.setDefaultPeriod(source.isDefaultPeriod());
    }

    /**
     * @see org.openuss.lecture.PeriodDao#toPeriodInfo(org.openuss.lecture.Period)
     */
    public org.openuss.lecture.PeriodInfo toPeriodInfo(final org.openuss.lecture.Period entity)
    {
        final org.openuss.lecture.PeriodInfo target = new org.openuss.lecture.PeriodInfo();
        this.toPeriodInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.lecture.PeriodDao#periodInfoToEntity(org.openuss.lecture.PeriodInfo, org.openuss.lecture.Period)
     */
    public void periodInfoToEntity(
        org.openuss.lecture.PeriodInfo source,
        org.openuss.lecture.Period target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
        if (copyIfNull || source.getDescription() != null)
        {
            target.setDescription(source.getDescription());
        }
        if (copyIfNull || source.getStartdate() != null)
        {
            target.setStartdate(source.getStartdate());
        }
        if (copyIfNull || source.getEnddate() != null)
        {
            target.setEnddate(source.getEnddate());
        }
	    target.setDefaultPeriod(source.isDefaultPeriod());
    }
    
}