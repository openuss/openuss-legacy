
package org.openuss.lecture;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.lecture.Course</code>.
 * </p>
 *
 * @see org.openuss.lecture.Course
 */
public abstract class CourseDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.lecture.CourseDao
{

    private org.openuss.lecture.PeriodDao periodDao;

    /**
     * Sets the reference to <code>periodDao</code>.
     */
    public void setPeriodDao(org.openuss.lecture.PeriodDao periodDao)
    {
        this.periodDao = periodDao;
    }

    /**
     * Gets the reference to <code>periodDao</code>.
     */
    protected org.openuss.lecture.PeriodDao getPeriodDao()
    {
        return this.periodDao;
    }

    private org.openuss.lecture.CourseTypeDao courseTypeDao;

    /**
     * Sets the reference to <code>courseTypeDao</code>.
     */
    public void setCourseTypeDao(org.openuss.lecture.CourseTypeDao courseTypeDao)
    {
        this.courseTypeDao = courseTypeDao;
    }

    /**
     * Gets the reference to <code>courseTypeDao</code>.
     */
    protected org.openuss.lecture.CourseTypeDao getCourseTypeDao()
    {
        return this.courseTypeDao;
    }


    /**
     * @see org.openuss.lecture.CourseDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Course.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.lecture.CourseImpl.class, id);
        return transformEntity(transform, (org.openuss.lecture.Course)entity);
    }

    /**
     * @see org.openuss.lecture.CourseDao#load(java.lang.Long)
     */
    public org.openuss.lecture.Course load(java.lang.Long id)
    {
        return (org.openuss.lecture.Course)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.lecture.CourseDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.Course> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.lecture.CourseDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.lecture.CourseImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.lecture.CourseDao#create(org.openuss.lecture.Course)
     */
    public org.openuss.lecture.Course create(org.openuss.lecture.Course course)
    {
        return (org.openuss.lecture.Course)this.create(TRANSFORM_NONE, course);
    }

    /**
     * @see org.openuss.lecture.CourseDao#create(int transform, org.openuss.lecture.Course)
     */
    public java.lang.Object create(final int transform, final org.openuss.lecture.Course course)
    {
        if (course == null)
        {
            throw new IllegalArgumentException(
                "Course.create - 'course' can not be null");
        }
        this.getHibernateTemplate().save(course);
        return this.transformEntity(transform, course);
    }

    /**
     * @see org.openuss.lecture.CourseDao#create(java.util.Collection<org.openuss.lecture.Course>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.Course> create(final java.util.Collection<org.openuss.lecture.Course> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.lecture.CourseDao#create(int, java.util.Collection<org.openuss.lecture.Course>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.lecture.Course> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Course.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.lecture.Course)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.lecture.CourseDao#update(org.openuss.lecture.Course)
     */
    public void update(org.openuss.lecture.Course course)
    {
        if (course == null)
        {
            throw new IllegalArgumentException(
                "Course.update - 'course' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(course);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(course);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.lecture.CourseDao#update(java.util.Collection<org.openuss.lecture.Course>)
     */
    public void update(final java.util.Collection<org.openuss.lecture.Course> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Course.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.lecture.Course)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.lecture.CourseDao#remove(org.openuss.lecture.Course)
     */
    public void remove(org.openuss.lecture.Course course)
    {
        if (course == null)
        {
            throw new IllegalArgumentException(
                "Course.remove - 'course' can not be null");
        }
        this.getHibernateTemplate().delete(course);
    }

    /**
     * @see org.openuss.lecture.CourseDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Course.remove - 'id can not be null");
        }
        org.openuss.lecture.Course entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.lecture.CourseDao#remove(java.util.Collection<org.openuss.lecture.Course>)
     */
    public void remove(java.util.Collection<org.openuss.lecture.Course> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Course.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.lecture.CourseDao#findByShortcut(java.lang.String)
     */
    public org.openuss.lecture.Course findByShortcut(java.lang.String shortcut)
    {
        return (org.openuss.lecture.Course)this.findByShortcut(TRANSFORM_NONE, shortcut);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByShortcut(java.lang.String, java.lang.String)
     */
    public org.openuss.lecture.Course findByShortcut(final java.lang.String queryString, final java.lang.String shortcut)
    {
        return (org.openuss.lecture.Course)this.findByShortcut(TRANSFORM_NONE, queryString, shortcut);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByShortcut(int, java.lang.String)
     */
    public java.lang.Object findByShortcut(final int transform, final java.lang.String shortcut)
    {
        return this.findByShortcut(transform, "from org.openuss.lecture.Course as e where e.shortcut = :shortcut", shortcut);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByShortcut(int, java.lang.String, java.lang.String)
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
                        "More than one instance of 'org.openuss.lecture.Course"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.lecture.Course)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByCourseType(org.openuss.lecture.CourseType)
     */
    public java.util.List findByCourseType(org.openuss.lecture.CourseType courseType)
    {
        return this.findByCourseType(TRANSFORM_NONE, courseType);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByCourseType(java.lang.String, org.openuss.lecture.CourseType)
     */
    public java.util.List findByCourseType(final java.lang.String queryString, final org.openuss.lecture.CourseType courseType)
    {
        return this.findByCourseType(TRANSFORM_NONE, queryString, courseType);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByCourseType(int, org.openuss.lecture.CourseType)
     */
    public java.util.List findByCourseType(final int transform, final org.openuss.lecture.CourseType courseType)
    {
        return this.findByCourseType(transform, "from org.openuss.lecture.Course as f where f.courseType = :courseType", courseType);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByCourseType(int, java.lang.String, org.openuss.lecture.CourseType)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByCourseType(final int transform, final java.lang.String queryString, final org.openuss.lecture.CourseType courseType)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("courseType", courseType);
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
     * @see org.openuss.lecture.CourseDao#findByPeriod(org.openuss.lecture.Period)
     */
    public java.util.List findByPeriod(org.openuss.lecture.Period period)
    {
        return this.findByPeriod(TRANSFORM_NONE, period);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByPeriod(java.lang.String, org.openuss.lecture.Period)
     */
    public java.util.List findByPeriod(final java.lang.String queryString, final org.openuss.lecture.Period period)
    {
        return this.findByPeriod(TRANSFORM_NONE, queryString, period);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByPeriod(int, org.openuss.lecture.Period)
     */
    public java.util.List findByPeriod(final int transform, final org.openuss.lecture.Period period)
    {
        return this.findByPeriod(transform, "from org.openuss.lecture.Course as f where f.period = :period", period);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByPeriod(int, java.lang.String, org.openuss.lecture.Period)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByPeriod(final int transform, final java.lang.String queryString, final org.openuss.lecture.Period period)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("period", period);
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
     * @see org.openuss.lecture.CourseDao#findByPeriodAndCourseType(org.openuss.lecture.Period, org.openuss.lecture.CourseType)
     */
    public java.util.List findByPeriodAndCourseType(org.openuss.lecture.Period period, org.openuss.lecture.CourseType courseType)
    {
        return this.findByPeriodAndCourseType(TRANSFORM_NONE, period, courseType);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByPeriodAndCourseType(java.lang.String, org.openuss.lecture.Period, org.openuss.lecture.CourseType)
     */
    public java.util.List findByPeriodAndCourseType(final java.lang.String queryString, final org.openuss.lecture.Period period, final org.openuss.lecture.CourseType courseType)
    {
        return this.findByPeriodAndCourseType(TRANSFORM_NONE, queryString, period, courseType);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByPeriodAndCourseType(int, org.openuss.lecture.Period, org.openuss.lecture.CourseType)
     */
    public java.util.List findByPeriodAndCourseType(final int transform, final org.openuss.lecture.Period period, final org.openuss.lecture.CourseType courseType)
    {
        return this.findByPeriodAndCourseType(transform, "from org.openuss.lecture.Course as f where f.period = :period and f.institute = :institute", period, courseType);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByPeriodAndCourseType(int, java.lang.String, org.openuss.lecture.Period, org.openuss.lecture.CourseType)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByPeriodAndCourseType(final int transform, final java.lang.String queryString, final org.openuss.lecture.Period period, final org.openuss.lecture.CourseType courseType)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("period", period);
            queryObject.setParameter("courseType", courseType);
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
     * @see org.openuss.lecture.CourseDao#findByPeriodAndEnabled(org.openuss.lecture.Period, boolean)
     */
    public java.util.List findByPeriodAndEnabled(org.openuss.lecture.Period period, boolean enabled)
    {
        return this.findByPeriodAndEnabled(TRANSFORM_NONE, period, enabled);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByPeriodAndEnabled(java.lang.String, org.openuss.lecture.Period, boolean)
     */
    public java.util.List findByPeriodAndEnabled(final java.lang.String queryString, final org.openuss.lecture.Period period, final boolean enabled)
    {
        return this.findByPeriodAndEnabled(TRANSFORM_NONE, queryString, period, enabled);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByPeriodAndEnabled(int, org.openuss.lecture.Period, boolean)
     */
    public java.util.List findByPeriodAndEnabled(final int transform, final org.openuss.lecture.Period period, final boolean enabled)
    {
        return this.findByPeriodAndEnabled(transform, "from org.openuss.lecture.Course as f where f.period = :period and f.enabled = :enabled", period, enabled);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByPeriodAndEnabled(int, java.lang.String, org.openuss.lecture.Period, boolean)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByPeriodAndEnabled(final int transform, final java.lang.String queryString, final org.openuss.lecture.Period period, final boolean enabled)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("period", period);
            queryObject.setParameter("enabled", new java.lang.Boolean(enabled));
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
     * @see org.openuss.lecture.CourseDao#findByEnabled(boolean)
     */
    public java.util.List findByEnabled(boolean enabled)
    {
        return this.findByEnabled(TRANSFORM_NONE, enabled);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByEnabled(java.lang.String, boolean)
     */
    public java.util.List findByEnabled(final java.lang.String queryString, final boolean enabled)
    {
        return this.findByEnabled(TRANSFORM_NONE, queryString, enabled);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByEnabled(int, boolean)
     */
    public java.util.List findByEnabled(final int transform, final boolean enabled)
    {
        return this.findByEnabled(transform, "from org.openuss.lecture.Course as f where f.enabled = :enabled", enabled);
    }

    /**
     * @see org.openuss.lecture.CourseDao#findByEnabled(int, java.lang.String, boolean)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByEnabled(final int transform, final java.lang.String queryString, final boolean enabled)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("enabled", new java.lang.Boolean(enabled));
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
     * flag is set to one of the constants defined in <code>org.openuss.lecture.CourseDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.lecture.Course} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.lecture.CourseInfo} - {@link TRANSFORM_COURSEINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.lecture.CourseDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.lecture.Course entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_COURSEINFO :
                    target = toCourseInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.lecture.Course)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.lecture.CourseDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.lecture.Course)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_COURSEINFO :
                toCourseInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.lecture.CourseDao#toCourseInfoCollection(java.util.Collection)
     */
    public final void toCourseInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, COURSEINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link CourseDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.lecture.CourseDao#toCourseInfo(org.openuss.lecture.Course)
     */
    protected org.openuss.lecture.CourseInfo toCourseInfo(java.lang.Object[] row)
    {
        org.openuss.lecture.CourseInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.lecture.Course)
                {
                    target = this.toCourseInfo((org.openuss.lecture.Course)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.lecture.CourseInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer COURSEINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.lecture.Course)
                {
                    result = toCourseInfo((org.openuss.lecture.Course)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toCourseInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.lecture.CourseDao#courseInfoToEntityCollection(java.util.Collection)
     */
    public final void courseInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.lecture.CourseInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, CourseInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer CourseInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return courseInfoToEntity((org.openuss.lecture.CourseInfo)input);
            }
        };

    /**
     * @see org.openuss.lecture.CourseDao#toCourseInfo(org.openuss.lecture.Course, org.openuss.lecture.CourseInfo)
     */
    public void toCourseInfo(
        org.openuss.lecture.Course source,
        org.openuss.lecture.CourseInfo target)
    {
        target.setId(source.getId());
        target.setShortcut(source.getShortcut());
        target.setDescription(source.getDescription());
        target.setPassword(source.getPassword());
        target.setAccessType(source.getAccessType());
        target.setBraincontest((source.getBraincontest() == null ? false : source.getBraincontest().booleanValue()));
        target.setFreestylelearning((source.getFreestylelearning() == null ? false : source.getFreestylelearning().booleanValue()));
        target.setChat((source.getChat() == null ? false : source.getChat().booleanValue()));
        target.setNewsletter((source.getNewsletter() == null ? false : source.getNewsletter().booleanValue()));
        target.setDiscussion((source.getDiscussion() == null ? false : source.getDiscussion().booleanValue()));
        target.setCollaboration((source.getCollaboration() == null ? false : source.getCollaboration().booleanValue()));
        target.setPapersubmission((source.getPapersubmission() == null ? false : source.getPapersubmission().booleanValue()));
        target.setWiki((source.getWiki() == null ? false : source.getWiki().booleanValue()));
        target.setDocuments((source.getDocuments() == null ? false : source.getDocuments().booleanValue()));
        target.setEnabled(source.isEnabled());
    }

    /**
     * @see org.openuss.lecture.CourseDao#toCourseInfo(org.openuss.lecture.Course)
     */
    public org.openuss.lecture.CourseInfo toCourseInfo(final org.openuss.lecture.Course entity)
    {
        final org.openuss.lecture.CourseInfo target = new org.openuss.lecture.CourseInfo();
        this.toCourseInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.lecture.CourseDao#courseInfoToEntity(org.openuss.lecture.CourseInfo, org.openuss.lecture.Course)
     */
    public void courseInfoToEntity(
        org.openuss.lecture.CourseInfo source,
        org.openuss.lecture.Course target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getShortcut() != null)
        {
            target.setShortcut(source.getShortcut());
        }
        if (copyIfNull || source.getAccessType() != null)
        {
            target.setAccessType(source.getAccessType());
        }
        if (copyIfNull || source.getPassword() != null)
        {
            target.setPassword(source.getPassword());
        }
	    target.setDocuments(new java.lang.Boolean(source.isDocuments()));
	    target.setDiscussion(new java.lang.Boolean(source.isDiscussion()));
	    target.setNewsletter(new java.lang.Boolean(source.isNewsletter()));
	    target.setChat(new java.lang.Boolean(source.isChat()));
	    target.setFreestylelearning(new java.lang.Boolean(source.isFreestylelearning()));
	    target.setBraincontest(new java.lang.Boolean(source.isBraincontest()));
	    target.setCollaboration(new java.lang.Boolean(source.isCollaboration()));
	    target.setPapersubmission(new java.lang.Boolean(source.isPapersubmission()));
	    target.setWiki(new java.lang.Boolean(source.isWiki()));
        if (copyIfNull || source.getDescription() != null)
        {
            target.setDescription(source.getDescription());
        }
	    target.setEnabled(source.isEnabled());
    }
    
}