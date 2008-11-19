package org.openuss.lecture;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.lecture.CourseType</code>.
 * </p>
 *
 * @see org.openuss.lecture.CourseType
 */
public abstract class CourseTypeDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.lecture.CourseTypeDao
{

    private org.openuss.lecture.InstituteDao instituteDao;

    /**
     * Sets the reference to <code>instituteDao</code>.
     */
    public void setInstituteDao(org.openuss.lecture.InstituteDao instituteDao)
    {
        this.instituteDao = instituteDao;
    }

    /**
     * Gets the reference to <code>instituteDao</code>.
     */
    protected org.openuss.lecture.InstituteDao getInstituteDao()
    {
        return this.instituteDao;
    }


    /**
     * @see org.openuss.lecture.CourseTypeDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "CourseType.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.lecture.CourseTypeImpl.class, id);
        return transformEntity(transform, (org.openuss.lecture.CourseType)entity);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#load(java.lang.Long)
     */
    public org.openuss.lecture.CourseType load(java.lang.Long id)
    {
        return (org.openuss.lecture.CourseType)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.CourseType> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.lecture.CourseTypeImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.lecture.CourseTypeDao#create(org.openuss.lecture.CourseType)
     */
    public org.openuss.lecture.CourseType create(org.openuss.lecture.CourseType courseType)
    {
        return (org.openuss.lecture.CourseType)this.create(TRANSFORM_NONE, courseType);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#create(int transform, org.openuss.lecture.CourseType)
     */
    public java.lang.Object create(final int transform, final org.openuss.lecture.CourseType courseType)
    {
        if (courseType == null)
        {
            throw new IllegalArgumentException(
                "CourseType.create - 'courseType' can not be null");
        }
        this.getHibernateTemplate().save(courseType);
        return this.transformEntity(transform, courseType);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#create(java.util.Collection<org.openuss.lecture.CourseType>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.CourseType> create(final java.util.Collection<org.openuss.lecture.CourseType> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#create(int, java.util.Collection<org.openuss.lecture.CourseType>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.lecture.CourseType> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "CourseType.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.lecture.CourseType)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#update(org.openuss.lecture.CourseType)
     */
    public void update(org.openuss.lecture.CourseType courseType)
    {
        if (courseType == null)
        {
            throw new IllegalArgumentException(
                "CourseType.update - 'courseType' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(courseType);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(courseType);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#update(java.util.Collection<org.openuss.lecture.CourseType>)
     */
    public void update(final java.util.Collection<org.openuss.lecture.CourseType> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "CourseType.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.lecture.CourseType)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#remove(org.openuss.lecture.CourseType)
     */
    public void remove(org.openuss.lecture.CourseType courseType)
    {
        if (courseType == null)
        {
            throw new IllegalArgumentException(
                "CourseType.remove - 'courseType' can not be null");
        }
        this.getHibernateTemplate().delete(courseType);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "CourseType.remove - 'id can not be null");
        }
        org.openuss.lecture.CourseType entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#remove(java.util.Collection<org.openuss.lecture.CourseType>)
     */
    public void remove(java.util.Collection<org.openuss.lecture.CourseType> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "CourseType.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.lecture.CourseTypeDao#findByName(java.lang.String)
     */
    public org.openuss.lecture.CourseType findByName(java.lang.String name)
    {
        return (org.openuss.lecture.CourseType)this.findByName(TRANSFORM_NONE, name);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#findByName(java.lang.String, java.lang.String)
     */
    public org.openuss.lecture.CourseType findByName(final java.lang.String queryString, final java.lang.String name)
    {
        return (org.openuss.lecture.CourseType)this.findByName(TRANSFORM_NONE, queryString, name);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#findByName(int, java.lang.String)
     */
    public java.lang.Object findByName(final int transform, final java.lang.String name)
    {
        return this.findByName(transform, "from org.openuss.lecture.CourseType as s where s.name = :name", name);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#findByName(int, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByName(final int transform, final java.lang.String queryString, final java.lang.String name)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("name", name);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.lecture.CourseType"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.lecture.CourseType)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#findByShortcut(java.lang.String)
     */
    public org.openuss.lecture.CourseType findByShortcut(java.lang.String shortcut)
    {
        return (org.openuss.lecture.CourseType)this.findByShortcut(TRANSFORM_NONE, shortcut);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#findByShortcut(java.lang.String, java.lang.String)
     */
    public org.openuss.lecture.CourseType findByShortcut(final java.lang.String queryString, final java.lang.String shortcut)
    {
        return (org.openuss.lecture.CourseType)this.findByShortcut(TRANSFORM_NONE, queryString, shortcut);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#findByShortcut(int, java.lang.String)
     */
    public java.lang.Object findByShortcut(final int transform, final java.lang.String shortcut)
    {
        return this.findByShortcut(transform, "from org.openuss.lecture.CourseType as s where s.shortcut = :shortcut", shortcut);
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#findByShortcut(int, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByShortcut(final int transform, final java.lang.String queryString, final java.lang.String shortcut)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("shortcut", shortcut);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.lecture.CourseType"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.lecture.CourseType)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.lecture.CourseTypeDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.lecture.CourseType} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.lecture.CourseTypeInfo} - {@link TRANSFORM_COURSETYPEINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.lecture.CourseTypeDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.lecture.CourseType entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_COURSETYPEINFO :
                    target = toCourseTypeInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.lecture.CourseType)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.lecture.CourseTypeDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.lecture.CourseType)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_COURSETYPEINFO :
                toCourseTypeInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#toCourseTypeInfoCollection(java.util.Collection)
     */
    public final void toCourseTypeInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, COURSETYPEINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link CourseTypeDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.lecture.CourseTypeDao#toCourseTypeInfo(org.openuss.lecture.CourseType)
     */
    protected org.openuss.lecture.CourseTypeInfo toCourseTypeInfo(java.lang.Object[] row)
    {
        org.openuss.lecture.CourseTypeInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.lecture.CourseType)
                {
                    target = this.toCourseTypeInfo((org.openuss.lecture.CourseType)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.lecture.CourseTypeInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer COURSETYPEINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.lecture.CourseType)
                {
                    result = toCourseTypeInfo((org.openuss.lecture.CourseType)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toCourseTypeInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.lecture.CourseTypeDao#courseTypeInfoToEntityCollection(java.util.Collection)
     */
    public final void courseTypeInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.lecture.CourseTypeInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, CourseTypeInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer CourseTypeInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return courseTypeInfoToEntity((org.openuss.lecture.CourseTypeInfo)input);
            }
        };

    /**
     * @see org.openuss.lecture.CourseTypeDao#toCourseTypeInfo(org.openuss.lecture.CourseType, org.openuss.lecture.CourseTypeInfo)
     */
    public void toCourseTypeInfo(
        org.openuss.lecture.CourseType source,
        org.openuss.lecture.CourseTypeInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setShortcut(source.getShortcut());
        target.setDescription(source.getDescription());
    }

    /**
     * @see org.openuss.lecture.CourseTypeDao#toCourseTypeInfo(org.openuss.lecture.CourseType)
     */
    public org.openuss.lecture.CourseTypeInfo toCourseTypeInfo(final org.openuss.lecture.CourseType entity)
    {
        final org.openuss.lecture.CourseTypeInfo target = new org.openuss.lecture.CourseTypeInfo();
        this.toCourseTypeInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.lecture.CourseTypeDao#courseTypeInfoToEntity(org.openuss.lecture.CourseTypeInfo, org.openuss.lecture.CourseType)
     */
    public void courseTypeInfoToEntity(
        org.openuss.lecture.CourseTypeInfo source,
        org.openuss.lecture.CourseType target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
        if (copyIfNull || source.getShortcut() != null)
        {
            target.setShortcut(source.getShortcut());
        }
        if (copyIfNull || source.getDescription() != null)
        {
            target.setDescription(source.getDescription());
        }
    }
    
}