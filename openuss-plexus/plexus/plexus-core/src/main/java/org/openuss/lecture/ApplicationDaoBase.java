package org.openuss.lecture;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.lecture.Application</code>.
 * </p>
 *
 * @see org.openuss.lecture.Application
 */
public abstract class ApplicationDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.lecture.ApplicationDao
{

    private org.openuss.lecture.DepartmentDao departmentDao;

    /**
     * Sets the reference to <code>departmentDao</code>.
     */
    public void setDepartmentDao(org.openuss.lecture.DepartmentDao departmentDao)
    {
        this.departmentDao = departmentDao;
    }

    /**
     * Gets the reference to <code>departmentDao</code>.
     */
    protected org.openuss.lecture.DepartmentDao getDepartmentDao()
    {
        return this.departmentDao;
    }

    private org.openuss.security.UserDao userDao;

    /**
     * Sets the reference to <code>userDao</code>.
     */
    public void setUserDao(org.openuss.security.UserDao userDao)
    {
        this.userDao = userDao;
    }

    /**
     * Gets the reference to <code>userDao</code>.
     */
    protected org.openuss.security.UserDao getUserDao()
    {
        return this.userDao;
    }

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
     * @see org.openuss.lecture.ApplicationDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Application.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.lecture.ApplicationImpl.class, id);
        return transformEntity(transform, (org.openuss.lecture.Application)entity);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#load(java.lang.Long)
     */
    public org.openuss.lecture.Application load(java.lang.Long id)
    {
        return (org.openuss.lecture.Application)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.Application> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.lecture.ApplicationImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.lecture.ApplicationDao#create(org.openuss.lecture.Application)
     */
    public org.openuss.lecture.Application create(org.openuss.lecture.Application application)
    {
        return (org.openuss.lecture.Application)this.create(TRANSFORM_NONE, application);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#create(int transform, org.openuss.lecture.Application)
     */
    public java.lang.Object create(final int transform, final org.openuss.lecture.Application application)
    {
        if (application == null)
        {
            throw new IllegalArgumentException(
                "Application.create - 'application' can not be null");
        }
        this.getHibernateTemplate().save(application);
        return this.transformEntity(transform, application);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#create(java.util.Collection<org.openuss.lecture.Application>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.Application> create(final java.util.Collection<org.openuss.lecture.Application> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#create(int, java.util.Collection<org.openuss.lecture.Application>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.lecture.Application> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Application.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.lecture.Application)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#update(org.openuss.lecture.Application)
     */
    public void update(org.openuss.lecture.Application application)
    {
        if (application == null)
        {
            throw new IllegalArgumentException(
                "Application.update - 'application' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(application);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(application);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#update(java.util.Collection<org.openuss.lecture.Application>)
     */
    public void update(final java.util.Collection<org.openuss.lecture.Application> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Application.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.lecture.Application)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#remove(org.openuss.lecture.Application)
     */
    public void remove(org.openuss.lecture.Application application)
    {
        if (application == null)
        {
            throw new IllegalArgumentException(
                "Application.remove - 'application' can not be null");
        }
        this.getHibernateTemplate().delete(application);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Application.remove - 'id can not be null");
        }
        org.openuss.lecture.Application entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#remove(java.util.Collection<org.openuss.lecture.Application>)
     */
    public void remove(java.util.Collection<org.openuss.lecture.Application> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Application.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.lecture.ApplicationDao#findByInstituteAndDepartment(org.openuss.lecture.Institute, org.openuss.lecture.Department)
     */
    public org.openuss.lecture.Application findByInstituteAndDepartment(org.openuss.lecture.Institute institute, org.openuss.lecture.Department department)
    {
        return (org.openuss.lecture.Application)this.findByInstituteAndDepartment(TRANSFORM_NONE, institute, department);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByInstituteAndDepartment(java.lang.String, org.openuss.lecture.Institute, org.openuss.lecture.Department)
     */
    public org.openuss.lecture.Application findByInstituteAndDepartment(final java.lang.String queryString, final org.openuss.lecture.Institute institute, final org.openuss.lecture.Department department)
    {
        return (org.openuss.lecture.Application)this.findByInstituteAndDepartment(TRANSFORM_NONE, queryString, institute, department);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByInstituteAndDepartment(int, org.openuss.lecture.Institute, org.openuss.lecture.Department)
     */
    public java.lang.Object findByInstituteAndDepartment(final int transform, final org.openuss.lecture.Institute institute, final org.openuss.lecture.Department department)
    {
        return this.findByInstituteAndDepartment(transform, "from org.openuss.lecture.Application as application where application.institute = ? and application.department = ?", institute, department);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByInstituteAndDepartment(int, java.lang.String, org.openuss.lecture.Institute, org.openuss.lecture.Department)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByInstituteAndDepartment(final int transform, final java.lang.String queryString, final org.openuss.lecture.Institute institute, final org.openuss.lecture.Department department)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, institute);
            queryObject.setParameter(1, department);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.lecture.Application"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.lecture.Application)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByDepartment(org.openuss.lecture.Department)
     */
    public java.util.List findByDepartment(org.openuss.lecture.Department department)
    {
        return this.findByDepartment(TRANSFORM_NONE, department);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByDepartment(java.lang.String, org.openuss.lecture.Department)
     */
    public java.util.List findByDepartment(final java.lang.String queryString, final org.openuss.lecture.Department department)
    {
        return this.findByDepartment(TRANSFORM_NONE, queryString, department);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByDepartment(int, org.openuss.lecture.Department)
     */
    public java.util.List findByDepartment(final int transform, final org.openuss.lecture.Department department)
    {
        return this.findByDepartment(transform, "from org.openuss.lecture.Application as f where f.department = :department", department);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByDepartment(int, java.lang.String, org.openuss.lecture.Department)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByDepartment(final int transform, final java.lang.String queryString, final org.openuss.lecture.Department department)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("department", department);
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
     * @see org.openuss.lecture.ApplicationDao#findByDepartmentAndConfirmed(org.openuss.lecture.Department, boolean)
     */
    public java.util.List findByDepartmentAndConfirmed(org.openuss.lecture.Department department, boolean confirmed)
    {
        return this.findByDepartmentAndConfirmed(TRANSFORM_NONE, department, confirmed);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByDepartmentAndConfirmed(java.lang.String, org.openuss.lecture.Department, boolean)
     */
    public java.util.List findByDepartmentAndConfirmed(final java.lang.String queryString, final org.openuss.lecture.Department department, final boolean confirmed)
    {
        return this.findByDepartmentAndConfirmed(TRANSFORM_NONE, queryString, department, confirmed);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByDepartmentAndConfirmed(int, org.openuss.lecture.Department, boolean)
     */
    public java.util.List findByDepartmentAndConfirmed(final int transform, final org.openuss.lecture.Department department, final boolean confirmed)
    {
        return this.findByDepartmentAndConfirmed(transform, "from org.openuss.lecture.Application as f where f.confirmed = :confirmed and (f.department = :department) order by applicationDate", department, confirmed);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByDepartmentAndConfirmed(int, java.lang.String, org.openuss.lecture.Department, boolean)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByDepartmentAndConfirmed(final int transform, final java.lang.String queryString, final org.openuss.lecture.Department department, final boolean confirmed)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("department", department);
            queryObject.setParameter("confirmed", new java.lang.Boolean(confirmed));
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
     * @see org.openuss.lecture.ApplicationDao#findByInstitute(org.openuss.lecture.Institute)
     */
    public java.util.List findByInstitute(org.openuss.lecture.Institute institute)
    {
        return this.findByInstitute(TRANSFORM_NONE, institute);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByInstitute(java.lang.String, org.openuss.lecture.Institute)
     */
    public java.util.List findByInstitute(final java.lang.String queryString, final org.openuss.lecture.Institute institute)
    {
        return this.findByInstitute(TRANSFORM_NONE, queryString, institute);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByInstitute(int, org.openuss.lecture.Institute)
     */
    public java.util.List findByInstitute(final int transform, final org.openuss.lecture.Institute institute)
    {
        return this.findByInstitute(transform, "from org.openuss.lecture.Application as application where application.institute = ?", institute);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByInstitute(int, java.lang.String, org.openuss.lecture.Institute)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByInstitute(final int transform, final java.lang.String queryString, final org.openuss.lecture.Institute institute)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, institute);
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
     * @see org.openuss.lecture.ApplicationDao#findByApplyingUser(org.openuss.security.User)
     */
    public java.util.List findByApplyingUser(org.openuss.security.User applyingUser)
    {
        return this.findByApplyingUser(TRANSFORM_NONE, applyingUser);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByApplyingUser(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findByApplyingUser(final java.lang.String queryString, final org.openuss.security.User applyingUser)
    {
        return this.findByApplyingUser(TRANSFORM_NONE, queryString, applyingUser);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByApplyingUser(int, org.openuss.security.User)
     */
    public java.util.List findByApplyingUser(final int transform, final org.openuss.security.User applyingUser)
    {
        return this.findByApplyingUser(transform, "from org.openuss.lecture.Application as application where application.applyingUser = ?", applyingUser);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByApplyingUser(int, java.lang.String, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByApplyingUser(final int transform, final java.lang.String queryString, final org.openuss.security.User applyingUser)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, applyingUser);
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
     * @see org.openuss.lecture.ApplicationDao#findByConfirmingUser(org.openuss.security.User)
     */
    public java.util.List findByConfirmingUser(org.openuss.security.User confirmingUser)
    {
        return this.findByConfirmingUser(TRANSFORM_NONE, confirmingUser);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByConfirmingUser(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findByConfirmingUser(final java.lang.String queryString, final org.openuss.security.User confirmingUser)
    {
        return this.findByConfirmingUser(TRANSFORM_NONE, queryString, confirmingUser);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByConfirmingUser(int, org.openuss.security.User)
     */
    public java.util.List findByConfirmingUser(final int transform, final org.openuss.security.User confirmingUser)
    {
        return this.findByConfirmingUser(transform, "from org.openuss.lecture.Application as application where application.confirmingUser = ?", confirmingUser);
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#findByConfirmingUser(int, java.lang.String, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByConfirmingUser(final int transform, final java.lang.String queryString, final org.openuss.security.User confirmingUser)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, confirmingUser);
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
     * flag is set to one of the constants defined in <code>org.openuss.lecture.ApplicationDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.lecture.Application} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.lecture.ApplicationInfo} - {@link TRANSFORM_APPLICATIONINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.lecture.ApplicationDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.lecture.Application entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_APPLICATIONINFO :
                    target = toApplicationInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.lecture.Application)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.lecture.ApplicationDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.lecture.Application)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_APPLICATIONINFO :
                toApplicationInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#toApplicationInfoCollection(java.util.Collection)
     */
    public final void toApplicationInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, APPLICATIONINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link ApplicationDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.lecture.ApplicationDao#toApplicationInfo(org.openuss.lecture.Application)
     */
    protected org.openuss.lecture.ApplicationInfo toApplicationInfo(java.lang.Object[] row)
    {
        org.openuss.lecture.ApplicationInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.lecture.Application)
                {
                    target = this.toApplicationInfo((org.openuss.lecture.Application)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.lecture.ApplicationInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer APPLICATIONINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.lecture.Application)
                {
                    result = toApplicationInfo((org.openuss.lecture.Application)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toApplicationInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.lecture.ApplicationDao#applicationInfoToEntityCollection(java.util.Collection)
     */
    public final void applicationInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.lecture.ApplicationInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, ApplicationInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer ApplicationInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return applicationInfoToEntity((org.openuss.lecture.ApplicationInfo)input);
            }
        };

    /**
     * @see org.openuss.lecture.ApplicationDao#toApplicationInfo(org.openuss.lecture.Application, org.openuss.lecture.ApplicationInfo)
     */
    public void toApplicationInfo(
        org.openuss.lecture.Application source,
        org.openuss.lecture.ApplicationInfo target)
    {
        target.setId(source.getId());
        target.setApplicationDate(source.getApplicationDate());
        target.setConfirmationDate(source.getConfirmationDate());
        target.setDescription(source.getDescription());
        target.setConfirmed(source.isConfirmed());
    }

    /**
     * @see org.openuss.lecture.ApplicationDao#toApplicationInfo(org.openuss.lecture.Application)
     */
    public org.openuss.lecture.ApplicationInfo toApplicationInfo(final org.openuss.lecture.Application entity)
    {
        final org.openuss.lecture.ApplicationInfo target = new org.openuss.lecture.ApplicationInfo();
        this.toApplicationInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.lecture.ApplicationDao#applicationInfoToEntity(org.openuss.lecture.ApplicationInfo, org.openuss.lecture.Application)
     */
    public void applicationInfoToEntity(
        org.openuss.lecture.ApplicationInfo source,
        org.openuss.lecture.Application target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getApplicationDate() != null)
        {
            target.setApplicationDate(source.getApplicationDate());
        }
        if (copyIfNull || source.getConfirmationDate() != null)
        {
            target.setConfirmationDate(source.getConfirmationDate());
        }
        if (copyIfNull || source.getDescription() != null)
        {
            target.setDescription(source.getDescription());
        }
	    target.setConfirmed(source.isConfirmed());
    }
    
}