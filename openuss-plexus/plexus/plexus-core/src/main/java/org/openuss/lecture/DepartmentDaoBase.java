package org.openuss.lecture;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.lecture.Department</code>.
 * </p>
 *
 * @see org.openuss.lecture.Department
 */
public abstract class DepartmentDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.lecture.DepartmentDao
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
     * @see org.openuss.lecture.DepartmentDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Department.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.lecture.DepartmentImpl.class, id);
        return transformEntity(transform, (org.openuss.lecture.Department)entity);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#load(java.lang.Long)
     */
    public org.openuss.lecture.Department load(java.lang.Long id)
    {
        return (org.openuss.lecture.Department)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.Department> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.lecture.DepartmentImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.lecture.DepartmentDao#create(org.openuss.lecture.Department)
     */
    public org.openuss.lecture.Department create(org.openuss.lecture.Department department)
    {
        return (org.openuss.lecture.Department)this.create(TRANSFORM_NONE, department);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#create(int transform, org.openuss.lecture.Department)
     */
    public java.lang.Object create(final int transform, final org.openuss.lecture.Department department)
    {
        if (department == null)
        {
            throw new IllegalArgumentException(
                "Department.create - 'department' can not be null");
        }
        this.getHibernateTemplate().save(department);
        return this.transformEntity(transform, department);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#create(java.util.Collection<org.openuss.lecture.Department>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.Department> create(final java.util.Collection<org.openuss.lecture.Department> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#create(int, java.util.Collection<org.openuss.lecture.Department>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.lecture.Department> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Department.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.lecture.Department)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#update(org.openuss.lecture.Department)
     */
    public void update(org.openuss.lecture.Department department)
    {
        if (department == null)
        {
            throw new IllegalArgumentException(
                "Department.update - 'department' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(department);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(department);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#update(java.util.Collection<org.openuss.lecture.Department>)
     */
    public void update(final java.util.Collection<org.openuss.lecture.Department> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Department.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.lecture.Department)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#remove(org.openuss.lecture.Department)
     */
    public void remove(org.openuss.lecture.Department department)
    {
        if (department == null)
        {
            throw new IllegalArgumentException(
                "Department.remove - 'department' can not be null");
        }
        this.getHibernateTemplate().delete(department);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Department.remove - 'id can not be null");
        }
        org.openuss.lecture.Department entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#remove(java.util.Collection<org.openuss.lecture.Department>)
     */
    public void remove(java.util.Collection<org.openuss.lecture.Department> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Department.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.lecture.DepartmentDao#findByEnabled(boolean)
     */
    public java.util.List findByEnabled(boolean enabled)
    {
        return this.findByEnabled(TRANSFORM_NONE, enabled);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByEnabled(java.lang.String, boolean)
     */
    public java.util.List findByEnabled(final java.lang.String queryString, final boolean enabled)
    {
        return this.findByEnabled(TRANSFORM_NONE, queryString, enabled);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByEnabled(int, boolean)
     */
    public java.util.List findByEnabled(final int transform, final boolean enabled)
    {
        return this.findByEnabled(transform, "from org.openuss.lecture.Department as f where f.enabled = :enabled order by name", enabled);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByEnabled(int, java.lang.String, boolean)
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
     * @see org.openuss.lecture.DepartmentDao#findByUniversity(org.openuss.lecture.University)
     */
    public java.util.List findByUniversity(org.openuss.lecture.University university)
    {
        return this.findByUniversity(TRANSFORM_NONE, university);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversity(java.lang.String, org.openuss.lecture.University)
     */
    public java.util.List findByUniversity(final java.lang.String queryString, final org.openuss.lecture.University university)
    {
        return this.findByUniversity(TRANSFORM_NONE, queryString, university);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversity(int, org.openuss.lecture.University)
     */
    public java.util.List findByUniversity(final int transform, final org.openuss.lecture.University university)
    {
        return this.findByUniversity(transform, "from org.openuss.lecture.Department as f where f.university = :university", university);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversity(int, java.lang.String, org.openuss.lecture.University)
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
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndEnabled(org.openuss.lecture.University, boolean)
     */
    public java.util.List findByUniversityAndEnabled(org.openuss.lecture.University university, boolean enabled)
    {
        return this.findByUniversityAndEnabled(TRANSFORM_NONE, university, enabled);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndEnabled(java.lang.String, org.openuss.lecture.University, boolean)
     */
    public java.util.List findByUniversityAndEnabled(final java.lang.String queryString, final org.openuss.lecture.University university, final boolean enabled)
    {
        return this.findByUniversityAndEnabled(TRANSFORM_NONE, queryString, university, enabled);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndEnabled(int, org.openuss.lecture.University, boolean)
     */
    public java.util.List findByUniversityAndEnabled(final int transform, final org.openuss.lecture.University university, final boolean enabled)
    {
        return this.findByUniversityAndEnabled(transform, "from org.openuss.lecture.Department as f where f.enabled = :enabled and (f.university = :university) order by name", university, enabled);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndEnabled(int, java.lang.String, org.openuss.lecture.University, boolean)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByUniversityAndEnabled(final int transform, final java.lang.String queryString, final org.openuss.lecture.University university, final boolean enabled)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("university", university);
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
     * @see org.openuss.lecture.DepartmentDao#findByType(org.openuss.lecture.DepartmentType)
     */
    public java.util.List findByType(org.openuss.lecture.DepartmentType type)
    {
        return this.findByType(TRANSFORM_NONE, type);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByType(java.lang.String, org.openuss.lecture.DepartmentType)
     */
    public java.util.List findByType(final java.lang.String queryString, final org.openuss.lecture.DepartmentType type)
    {
        return this.findByType(TRANSFORM_NONE, queryString, type);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByType(int, org.openuss.lecture.DepartmentType)
     */
    public java.util.List findByType(final int transform, final org.openuss.lecture.DepartmentType type)
    {
        return this.findByType(transform, "from org.openuss.lecture.Department as f where f.departmentType = :type order by name", type);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByType(int, java.lang.String, org.openuss.lecture.DepartmentType)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByType(final int transform, final java.lang.String queryString, final org.openuss.lecture.DepartmentType type)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("type", type.getValue());
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
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndType(org.openuss.lecture.University, org.openuss.lecture.DepartmentType)
     */
    public java.util.List findByUniversityAndType(org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType)
    {
        return this.findByUniversityAndType(TRANSFORM_NONE, university, departmentType);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndType(java.lang.String, org.openuss.lecture.University, org.openuss.lecture.DepartmentType)
     */
    public java.util.List findByUniversityAndType(final java.lang.String queryString, final org.openuss.lecture.University university, final org.openuss.lecture.DepartmentType departmentType)
    {
        return this.findByUniversityAndType(TRANSFORM_NONE, queryString, university, departmentType);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndType(int, org.openuss.lecture.University, org.openuss.lecture.DepartmentType)
     */
    public java.util.List findByUniversityAndType(final int transform, final org.openuss.lecture.University university, final org.openuss.lecture.DepartmentType departmentType)
    {
        return this.findByUniversityAndType(transform, "from org.openuss.lecture.Department as f where f.university = :university and (f.departmentType = :departmentType)", university, departmentType);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndType(int, java.lang.String, org.openuss.lecture.University, org.openuss.lecture.DepartmentType)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByUniversityAndType(final int transform, final java.lang.String queryString, final org.openuss.lecture.University university, final org.openuss.lecture.DepartmentType departmentType)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("university", university);
            queryObject.setParameter("departmentType", departmentType.getValue());
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
     * @see org.openuss.lecture.DepartmentDao#findByShortcut(java.lang.String)
     */
    public org.openuss.lecture.Department findByShortcut(java.lang.String shortcut)
    {
        return (org.openuss.lecture.Department)this.findByShortcut(TRANSFORM_NONE, shortcut);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByShortcut(java.lang.String, java.lang.String)
     */
    public org.openuss.lecture.Department findByShortcut(final java.lang.String queryString, final java.lang.String shortcut)
    {
        return (org.openuss.lecture.Department)this.findByShortcut(TRANSFORM_NONE, queryString, shortcut);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByShortcut(int, java.lang.String)
     */
    public java.lang.Object findByShortcut(final int transform, final java.lang.String shortcut)
    {
        return this.findByShortcut(transform, "from org.openuss.lecture.Department as f where f.shortcut = :shortcut", shortcut);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByShortcut(int, java.lang.String, java.lang.String)
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
                        "More than one instance of 'org.openuss.lecture.Department"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.lecture.Department)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndTypeAndEnabled(org.openuss.lecture.University, org.openuss.lecture.DepartmentType, boolean)
     */
    public java.util.List findByUniversityAndTypeAndEnabled(org.openuss.lecture.University university, org.openuss.lecture.DepartmentType departmentType, boolean enabled)
    {
        return this.findByUniversityAndTypeAndEnabled(TRANSFORM_NONE, university, departmentType, enabled);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndTypeAndEnabled(java.lang.String, org.openuss.lecture.University, org.openuss.lecture.DepartmentType, boolean)
     */
    public java.util.List findByUniversityAndTypeAndEnabled(final java.lang.String queryString, final org.openuss.lecture.University university, final org.openuss.lecture.DepartmentType departmentType, final boolean enabled)
    {
        return this.findByUniversityAndTypeAndEnabled(TRANSFORM_NONE, queryString, university, departmentType, enabled);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndTypeAndEnabled(int, org.openuss.lecture.University, org.openuss.lecture.DepartmentType, boolean)
     */
    public java.util.List findByUniversityAndTypeAndEnabled(final int transform, final org.openuss.lecture.University university, final org.openuss.lecture.DepartmentType departmentType, final boolean enabled)
    {
        return this.findByUniversityAndTypeAndEnabled(transform, "from org.openuss.lecture.Department as f where f.university = :university and (f.departmentType = :departmentType) and (f.enabled = :enabled)", university, departmentType, enabled);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndTypeAndEnabled(int, java.lang.String, org.openuss.lecture.University, org.openuss.lecture.DepartmentType, boolean)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByUniversityAndTypeAndEnabled(final int transform, final java.lang.String queryString, final org.openuss.lecture.University university, final org.openuss.lecture.DepartmentType departmentType, final boolean enabled)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("university", university);
            queryObject.setParameter("departmentType", departmentType.getValue());
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
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndDefault(org.openuss.lecture.University, boolean)
     */
    public org.openuss.lecture.Department findByUniversityAndDefault(org.openuss.lecture.University university, boolean defaultDepartment)
    {
        return (org.openuss.lecture.Department)this.findByUniversityAndDefault(TRANSFORM_NONE, university, defaultDepartment);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndDefault(java.lang.String, org.openuss.lecture.University, boolean)
     */
    public org.openuss.lecture.Department findByUniversityAndDefault(final java.lang.String queryString, final org.openuss.lecture.University university, final boolean defaultDepartment)
    {
        return (org.openuss.lecture.Department)this.findByUniversityAndDefault(TRANSFORM_NONE, queryString, university, defaultDepartment);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndDefault(int, org.openuss.lecture.University, boolean)
     */
    public java.lang.Object findByUniversityAndDefault(final int transform, final org.openuss.lecture.University university, final boolean defaultDepartment)
    {
        return this.findByUniversityAndDefault(transform, "from org.openuss.lecture.Department as f where f.university = :university and (f.defaultDepartment = :defaultDepartment)", university, defaultDepartment);
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#findByUniversityAndDefault(int, java.lang.String, org.openuss.lecture.University, boolean)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByUniversityAndDefault(final int transform, final java.lang.String queryString, final org.openuss.lecture.University university, final boolean defaultDepartment)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("university", university);
            queryObject.setParameter("defaultDepartment", new java.lang.Boolean(defaultDepartment));
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.lecture.Department"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.lecture.Department)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.lecture.DepartmentDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.lecture.Department} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.lecture.DepartmentInfo} - {@link TRANSFORM_DEPARTMENTINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.lecture.DepartmentDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.lecture.Department entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_DEPARTMENTINFO :
                    target = toDepartmentInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.lecture.Department)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.lecture.DepartmentDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.lecture.Department)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_DEPARTMENTINFO :
                toDepartmentInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#toDepartmentInfoCollection(java.util.Collection)
     */
    public final void toDepartmentInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, DEPARTMENTINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link DepartmentDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.lecture.DepartmentDao#toDepartmentInfo(org.openuss.lecture.Department)
     */
    protected org.openuss.lecture.DepartmentInfo toDepartmentInfo(java.lang.Object[] row)
    {
        org.openuss.lecture.DepartmentInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.lecture.Department)
                {
                    target = this.toDepartmentInfo((org.openuss.lecture.Department)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.lecture.DepartmentInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer DEPARTMENTINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.lecture.Department)
                {
                    result = toDepartmentInfo((org.openuss.lecture.Department)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toDepartmentInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.lecture.DepartmentDao#departmentInfoToEntityCollection(java.util.Collection)
     */
    public final void departmentInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.lecture.DepartmentInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, DepartmentInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer DepartmentInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return departmentInfoToEntity((org.openuss.lecture.DepartmentInfo)input);
            }
        };

    /**
     * @see org.openuss.lecture.DepartmentDao#toDepartmentInfo(org.openuss.lecture.Department, org.openuss.lecture.DepartmentInfo)
     */
    public void toDepartmentInfo(
        org.openuss.lecture.Department source,
        org.openuss.lecture.DepartmentInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setShortName(source.getShortName());
        target.setDescription(source.getDescription());
        target.setShortcut(source.getShortcut());
        target.setDepartmentType(source.getDepartmentType());
        target.setOwnerName(source.getOwnerName());
        target.setAddress(source.getAddress());
        target.setPostcode(source.getPostcode());
        target.setCity(source.getCity());
        target.setCountry(source.getCountry());
        target.setTelephone(source.getTelephone());
        target.setTelefax(source.getTelefax());
        target.setWebsite(source.getWebsite());
        target.setEmail(source.getEmail());
        target.setLocale(source.getLocale());
        target.setTheme(source.getTheme());
        target.setImageId(source.getImageId());
        target.setEnabled(source.isEnabled());
        target.setDefaultDepartment(source.isDefaultDepartment());
    }

    /**
     * @see org.openuss.lecture.DepartmentDao#toDepartmentInfo(org.openuss.lecture.Department)
     */
    public org.openuss.lecture.DepartmentInfo toDepartmentInfo(final org.openuss.lecture.Department entity)
    {
        final org.openuss.lecture.DepartmentInfo target = new org.openuss.lecture.DepartmentInfo();
        this.toDepartmentInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.lecture.DepartmentDao#departmentInfoToEntity(org.openuss.lecture.DepartmentInfo, org.openuss.lecture.Department)
     */
    public void departmentInfoToEntity(
        org.openuss.lecture.DepartmentInfo source,
        org.openuss.lecture.Department target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getDepartmentType() != null)
        {
            target.setDepartmentType(source.getDepartmentType());
        }
	    target.setDefaultDepartment(source.isDefaultDepartment());
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
        if (copyIfNull || source.getShortName() != null)
        {
            target.setShortName(source.getShortName());
        }
        if (copyIfNull || source.getShortcut() != null)
        {
            target.setShortcut(source.getShortcut());
        }
        if (copyIfNull || source.getDescription() != null)
        {
            target.setDescription(source.getDescription());
        }
        if (copyIfNull || source.getOwnerName() != null)
        {
            target.setOwnerName(source.getOwnerName());
        }
        if (copyIfNull || source.getAddress() != null)
        {
            target.setAddress(source.getAddress());
        }
        if (copyIfNull || source.getPostcode() != null)
        {
            target.setPostcode(source.getPostcode());
        }
        if (copyIfNull || source.getCity() != null)
        {
            target.setCity(source.getCity());
        }
        if (copyIfNull || source.getCountry() != null)
        {
            target.setCountry(source.getCountry());
        }
        if (copyIfNull || source.getTelephone() != null)
        {
            target.setTelephone(source.getTelephone());
        }
        if (copyIfNull || source.getTelefax() != null)
        {
            target.setTelefax(source.getTelefax());
        }
        if (copyIfNull || source.getWebsite() != null)
        {
            target.setWebsite(source.getWebsite());
        }
        if (copyIfNull || source.getEmail() != null)
        {
            target.setEmail(source.getEmail());
        }
        if (copyIfNull || source.getLocale() != null)
        {
            target.setLocale(source.getLocale());
        }
        if (copyIfNull || source.getTheme() != null)
        {
            target.setTheme(source.getTheme());
        }
        if (copyIfNull || source.getImageId() != null)
        {
            target.setImageId(source.getImageId());
        }
	    target.setEnabled(source.isEnabled());
    }
    
}