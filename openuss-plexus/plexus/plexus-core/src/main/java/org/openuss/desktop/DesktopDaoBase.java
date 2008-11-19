package org.openuss.desktop;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.desktop.Desktop</code>.
 * </p>
 *
 * @see org.openuss.desktop.Desktop
 */
public abstract class DesktopDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.desktop.DesktopDao
{

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

    private org.openuss.lecture.CourseDao courseDao;

    /**
     * Sets the reference to <code>courseDao</code>.
     */
    public void setCourseDao(org.openuss.lecture.CourseDao courseDao)
    {
        this.courseDao = courseDao;
    }

    /**
     * Gets the reference to <code>courseDao</code>.
     */
    protected org.openuss.lecture.CourseDao getCourseDao()
    {
        return this.courseDao;
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
     * @see org.openuss.desktop.DesktopDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Desktop.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.desktop.DesktopImpl.class, id);
        return transformEntity(transform, (org.openuss.desktop.Desktop)entity);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#load(java.lang.Long)
     */
    public org.openuss.desktop.Desktop load(java.lang.Long id)
    {
        return (org.openuss.desktop.Desktop)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.desktop.Desktop> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.desktop.DesktopImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.desktop.DesktopDao#create(org.openuss.desktop.Desktop)
     */
    public org.openuss.desktop.Desktop create(org.openuss.desktop.Desktop desktop)
    {
        return (org.openuss.desktop.Desktop)this.create(TRANSFORM_NONE, desktop);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#create(int transform, org.openuss.desktop.Desktop)
     */
    public java.lang.Object create(final int transform, final org.openuss.desktop.Desktop desktop)
    {
        if (desktop == null)
        {
            throw new IllegalArgumentException(
                "Desktop.create - 'desktop' can not be null");
        }
        this.getHibernateTemplate().save(desktop);
        return this.transformEntity(transform, desktop);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#create(java.util.Collection<org.openuss.desktop.Desktop>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.desktop.Desktop> create(final java.util.Collection<org.openuss.desktop.Desktop> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#create(int, java.util.Collection<org.openuss.desktop.Desktop>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.desktop.Desktop> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Desktop.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.desktop.Desktop)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.desktop.DesktopDao#update(org.openuss.desktop.Desktop)
     */
    public void update(org.openuss.desktop.Desktop desktop)
    {
        if (desktop == null)
        {
            throw new IllegalArgumentException(
                "Desktop.update - 'desktop' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(desktop);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(desktop);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.desktop.DesktopDao#update(java.util.Collection<org.openuss.desktop.Desktop>)
     */
    public void update(final java.util.Collection<org.openuss.desktop.Desktop> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Desktop.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.desktop.Desktop)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#remove(org.openuss.desktop.Desktop)
     */
    public void remove(org.openuss.desktop.Desktop desktop)
    {
        if (desktop == null)
        {
            throw new IllegalArgumentException(
                "Desktop.remove - 'desktop' can not be null");
        }
        this.getHibernateTemplate().delete(desktop);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Desktop.remove - 'id can not be null");
        }
        org.openuss.desktop.Desktop entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.desktop.DesktopDao#remove(java.util.Collection<org.openuss.desktop.Desktop>)
     */
    public void remove(java.util.Collection<org.openuss.desktop.Desktop> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Desktop.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.desktop.DesktopDao#findByUser(org.openuss.security.User)
     */
    public org.openuss.desktop.Desktop findByUser(org.openuss.security.User user)
    {
        return (org.openuss.desktop.Desktop)this.findByUser(TRANSFORM_NONE, user);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByUser(java.lang.String, org.openuss.security.User)
     */
    public org.openuss.desktop.Desktop findByUser(final java.lang.String queryString, final org.openuss.security.User user)
    {
        return (org.openuss.desktop.Desktop)this.findByUser(TRANSFORM_NONE, queryString, user);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByUser(int, org.openuss.security.User)
     */
    public java.lang.Object findByUser(final int transform, final org.openuss.security.User user)
    {
        return this.findByUser(transform, "from org.openuss.desktop.Desktop as d where d.user = :user", user);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByUser(int, java.lang.String, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByUser(final int transform, final java.lang.String queryString, final org.openuss.security.User user)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("user", user);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.desktop.Desktop"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.desktop.Desktop)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByUniversity(org.openuss.lecture.University)
     */
    public java.util.Collection findByUniversity(org.openuss.lecture.University university)
    {
        return this.findByUniversity(TRANSFORM_NONE, university);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByUniversity(java.lang.String, org.openuss.lecture.University)
     */
    public java.util.Collection findByUniversity(final java.lang.String queryString, final org.openuss.lecture.University university)
    {
        return this.findByUniversity(TRANSFORM_NONE, queryString, university);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByUniversity(int, org.openuss.lecture.University)
     */
    public java.util.Collection findByUniversity(final int transform, final org.openuss.lecture.University university)
    {
        return this.findByUniversity(transform, "from org.openuss.desktop.Desktop as desktop where :university in (universities)", university);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByUniversity(int, java.lang.String, org.openuss.lecture.University)
     */
    @SuppressWarnings("unchecked")
    public java.util.Collection findByUniversity(final int transform, final java.lang.String queryString, final org.openuss.lecture.University university)
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
     * @see org.openuss.desktop.DesktopDao#findByDepartment(org.openuss.lecture.Department)
     */
    public java.util.Collection findByDepartment(org.openuss.lecture.Department department)
    {
        return this.findByDepartment(TRANSFORM_NONE, department);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByDepartment(java.lang.String, org.openuss.lecture.Department)
     */
    public java.util.Collection findByDepartment(final java.lang.String queryString, final org.openuss.lecture.Department department)
    {
        return this.findByDepartment(TRANSFORM_NONE, queryString, department);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByDepartment(int, org.openuss.lecture.Department)
     */
    public java.util.Collection findByDepartment(final int transform, final org.openuss.lecture.Department department)
    {
        return this.findByDepartment(transform, "from org.openuss.desktop.Desktop as desktop where :department in (departments)", department);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByDepartment(int, java.lang.String, org.openuss.lecture.Department)
     */
    @SuppressWarnings("unchecked")
    public java.util.Collection findByDepartment(final int transform, final java.lang.String queryString, final org.openuss.lecture.Department department)
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
     * @see org.openuss.desktop.DesktopDao#findByInstitute(org.openuss.lecture.Institute)
     */
    public java.util.Collection findByInstitute(org.openuss.lecture.Institute institute)
    {
        return this.findByInstitute(TRANSFORM_NONE, institute);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByInstitute(java.lang.String, org.openuss.lecture.Institute)
     */
    public java.util.Collection findByInstitute(final java.lang.String queryString, final org.openuss.lecture.Institute institute)
    {
        return this.findByInstitute(TRANSFORM_NONE, queryString, institute);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByInstitute(int, org.openuss.lecture.Institute)
     */
    public java.util.Collection findByInstitute(final int transform, final org.openuss.lecture.Institute institute)
    {
        return this.findByInstitute(transform, "from org.openuss.desktop.Desktop as desktop where :institute in (institutes)", institute);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByInstitute(int, java.lang.String, org.openuss.lecture.Institute)
     */
    @SuppressWarnings("unchecked")
    public java.util.Collection findByInstitute(final int transform, final java.lang.String queryString, final org.openuss.lecture.Institute institute)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("institute", institute);
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
     * @see org.openuss.desktop.DesktopDao#findByCourseType(org.openuss.lecture.CourseType)
     */
    public java.util.Collection findByCourseType(org.openuss.lecture.CourseType courseType)
    {
        return this.findByCourseType(TRANSFORM_NONE, courseType);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByCourseType(java.lang.String, org.openuss.lecture.CourseType)
     */
    public java.util.Collection findByCourseType(final java.lang.String queryString, final org.openuss.lecture.CourseType courseType)
    {
        return this.findByCourseType(TRANSFORM_NONE, queryString, courseType);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByCourseType(int, org.openuss.lecture.CourseType)
     */
    public java.util.Collection findByCourseType(final int transform, final org.openuss.lecture.CourseType courseType)
    {
        return this.findByCourseType(transform, "from org.openuss.desktop.Desktop as desktop where :courseType in (courseTypes)", courseType);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByCourseType(int, java.lang.String, org.openuss.lecture.CourseType)
     */
    @SuppressWarnings("unchecked")
    public java.util.Collection findByCourseType(final int transform, final java.lang.String queryString, final org.openuss.lecture.CourseType courseType)
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
     * @see org.openuss.desktop.DesktopDao#findByCourse(org.openuss.lecture.Course)
     */
    public java.util.Collection findByCourse(org.openuss.lecture.Course course)
    {
        return this.findByCourse(TRANSFORM_NONE, course);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByCourse(java.lang.String, org.openuss.lecture.Course)
     */
    public java.util.Collection findByCourse(final java.lang.String queryString, final org.openuss.lecture.Course course)
    {
        return this.findByCourse(TRANSFORM_NONE, queryString, course);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByCourse(int, org.openuss.lecture.Course)
     */
    public java.util.Collection findByCourse(final int transform, final org.openuss.lecture.Course course)
    {
        return this.findByCourse(transform, "from org.openuss.desktop.Desktop as desktop where :course in (courses)", course);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByCourse(int, java.lang.String, org.openuss.lecture.Course)
     */
    @SuppressWarnings("unchecked")
    public java.util.Collection findByCourse(final int transform, final java.lang.String queryString, final org.openuss.lecture.Course course)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("course", course);
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
     * @see org.openuss.desktop.DesktopDao#findByUniversityAndUser(org.openuss.lecture.University, org.openuss.security.User)
     */
    public org.openuss.desktop.Desktop findByUniversityAndUser(org.openuss.lecture.University university, org.openuss.security.User user)
    {
        return (org.openuss.desktop.Desktop)this.findByUniversityAndUser(TRANSFORM_NONE, university, user);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByUniversityAndUser(java.lang.String, org.openuss.lecture.University, org.openuss.security.User)
     */
    public org.openuss.desktop.Desktop findByUniversityAndUser(final java.lang.String queryString, final org.openuss.lecture.University university, final org.openuss.security.User user)
    {
        return (org.openuss.desktop.Desktop)this.findByUniversityAndUser(TRANSFORM_NONE, queryString, university, user);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByUniversityAndUser(int, org.openuss.lecture.University, org.openuss.security.User)
     */
    public java.lang.Object findByUniversityAndUser(final int transform, final org.openuss.lecture.University university, final org.openuss.security.User user)
    {
        return this.findByUniversityAndUser(transform, "from org.openuss.desktop.Desktop as desktop where desktop.university = ? and desktop.user = ?", university, user);
    }

    /**
     * @see org.openuss.desktop.DesktopDao#findByUniversityAndUser(int, java.lang.String, org.openuss.lecture.University, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByUniversityAndUser(final int transform, final java.lang.String queryString, final org.openuss.lecture.University university, final org.openuss.security.User user)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, university);
            queryObject.setParameter(1, user);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.desktop.Desktop"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.desktop.Desktop)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.desktop.DesktopDao#isInstituteBookmarked(java.lang.Long, java.lang.Long)
     */
    public boolean isInstituteBookmarked(final java.lang.Long instituteId, final java.lang.Long userId)
    {
        try
        {
            return this.handleIsInstituteBookmarked(instituteId, userId);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.desktop.DesktopDao.isInstituteBookmarked(java.lang.Long instituteId, java.lang.Long userId)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #isInstituteBookmarked(java.lang.Long, java.lang.Long)}
      */
    protected abstract boolean handleIsInstituteBookmarked(java.lang.Long instituteId, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopDao#isDepartmentBookmarked(java.lang.Long, java.lang.Long)
     */
    public boolean isDepartmentBookmarked(final java.lang.Long departmentId, final java.lang.Long userId)
    {
        try
        {
            return this.handleIsDepartmentBookmarked(departmentId, userId);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.desktop.DesktopDao.isDepartmentBookmarked(java.lang.Long departmentId, java.lang.Long userId)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #isDepartmentBookmarked(java.lang.Long, java.lang.Long)}
      */
    protected abstract boolean handleIsDepartmentBookmarked(java.lang.Long departmentId, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * @see org.openuss.desktop.DesktopDao#isCourseBookmarked(java.lang.Long, java.lang.Long)
     */
    public boolean isCourseBookmarked(final java.lang.Long courseId, final java.lang.Long userId)
    {
        try
        {
            return this.handleIsCourseBookmarked(courseId, userId);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.desktop.DesktopDao.isCourseBookmarked(java.lang.Long courseId, java.lang.Long userId)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #isCourseBookmarked(java.lang.Long, java.lang.Long)}
      */
    protected abstract boolean handleIsCourseBookmarked(java.lang.Long courseId, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.desktop.DesktopDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.desktop.Desktop} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.desktop.DesktopInfo} - {@link TRANSFORM_DESKTOPINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.desktop.DesktopDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.desktop.Desktop entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_DESKTOPINFO :
                    target = toDesktopInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.desktop.Desktop)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.desktop.DesktopDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.desktop.Desktop)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_DESKTOPINFO :
                toDesktopInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.desktop.DesktopDao#toDesktopInfoCollection(java.util.Collection)
     */
    public final void toDesktopInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, DESKTOPINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link DesktopDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.desktop.DesktopDao#toDesktopInfo(org.openuss.desktop.Desktop)
     */
    protected org.openuss.desktop.DesktopInfo toDesktopInfo(java.lang.Object[] row)
    {
        org.openuss.desktop.DesktopInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.desktop.Desktop)
                {
                    target = this.toDesktopInfo((org.openuss.desktop.Desktop)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.desktop.DesktopInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer DESKTOPINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.desktop.Desktop)
                {
                    result = toDesktopInfo((org.openuss.desktop.Desktop)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toDesktopInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.desktop.DesktopDao#desktopInfoToEntityCollection(java.util.Collection)
     */
    public final void desktopInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.desktop.DesktopInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, DesktopInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer DesktopInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return desktopInfoToEntity((org.openuss.desktop.DesktopInfo)input);
            }
        };

    /**
     * @see org.openuss.desktop.DesktopDao#toDesktopInfo(org.openuss.desktop.Desktop, org.openuss.desktop.DesktopInfo)
     */
    public void toDesktopInfo(
        org.openuss.desktop.Desktop source,
        org.openuss.desktop.DesktopInfo target)
    {
        target.setId(source.getId());
    }

    /**
     * @see org.openuss.desktop.DesktopDao#toDesktopInfo(org.openuss.desktop.Desktop)
     */
    public org.openuss.desktop.DesktopInfo toDesktopInfo(final org.openuss.desktop.Desktop entity)
    {
        final org.openuss.desktop.DesktopInfo target = new org.openuss.desktop.DesktopInfo();
        this.toDesktopInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.desktop.DesktopDao#desktopInfoToEntity(org.openuss.desktop.DesktopInfo, org.openuss.desktop.Desktop)
     */
    public void desktopInfoToEntity(
        org.openuss.desktop.DesktopInfo source,
        org.openuss.desktop.Desktop target,
        boolean copyIfNull)
    {
    }
    
}