package org.openuss.lecture;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.lecture.CourseMember</code>.
 * </p>
 *
 * @see org.openuss.lecture.CourseMember
 */
public abstract class CourseMemberDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.lecture.CourseMemberDao
{


    /**
     * @see org.openuss.lecture.CourseMemberDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final org.openuss.lecture.CourseMemberPK courseMemberPk)
    {
        if (courseMemberPk == null)
        {
            throw new IllegalArgumentException(
                "CourseMember.load - 'courseMemberPk' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.lecture.CourseMemberImpl.class, courseMemberPk);
        return transformEntity(transform, (org.openuss.lecture.CourseMember)entity);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#load(org.openuss.lecture.CourseMemberPK)
     */
    public org.openuss.lecture.CourseMember load(org.openuss.lecture.CourseMemberPK courseMemberPk)
    {
        return (org.openuss.lecture.CourseMember)this.load(TRANSFORM_NONE, courseMemberPk);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.CourseMember> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.lecture.CourseMemberImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.lecture.CourseMemberDao#create(org.openuss.lecture.CourseMember)
     */
    public org.openuss.lecture.CourseMember create(org.openuss.lecture.CourseMember courseMember)
    {
        return (org.openuss.lecture.CourseMember)this.create(TRANSFORM_NONE, courseMember);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#create(int transform, org.openuss.lecture.CourseMember)
     */
    public java.lang.Object create(final int transform, final org.openuss.lecture.CourseMember courseMember)
    {
        if (courseMember == null)
        {
            throw new IllegalArgumentException(
                "CourseMember.create - 'courseMember' can not be null");
        }
        this.getHibernateTemplate().save(courseMember);
        return this.transformEntity(transform, courseMember);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#create(java.util.Collection<org.openuss.lecture.CourseMember>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.CourseMember> create(final java.util.Collection<org.openuss.lecture.CourseMember> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#create(int, java.util.Collection<org.openuss.lecture.CourseMember>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.lecture.CourseMember> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "CourseMember.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.lecture.CourseMember)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#update(org.openuss.lecture.CourseMember)
     */
    public void update(org.openuss.lecture.CourseMember courseMember)
    {
        if (courseMember == null)
        {
            throw new IllegalArgumentException(
                "CourseMember.update - 'courseMember' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(courseMember);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(courseMember);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#update(java.util.Collection<org.openuss.lecture.CourseMember>)
     */
    public void update(final java.util.Collection<org.openuss.lecture.CourseMember> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "CourseMember.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.lecture.CourseMember)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#remove(org.openuss.lecture.CourseMember)
     */
    public void remove(org.openuss.lecture.CourseMember courseMember)
    {
        if (courseMember == null)
        {
            throw new IllegalArgumentException(
                "CourseMember.remove - 'courseMember' can not be null");
        }
        this.getHibernateTemplate().delete(courseMember);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#remove(java.lang.Long)
     */
    public void remove(org.openuss.lecture.CourseMemberPK courseMemberPk)
    {
        if (courseMemberPk == null)
        {
            throw new IllegalArgumentException(
                "CourseMember.remove - 'courseMemberPk can not be null");
        }
        org.openuss.lecture.CourseMember entity = this.load(courseMemberPk);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#remove(java.util.Collection<org.openuss.lecture.CourseMember>)
     */
    public void remove(java.util.Collection<org.openuss.lecture.CourseMember> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "CourseMember.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.lecture.CourseMemberDao#findByCourse(org.openuss.lecture.Course)
     */
    public java.util.List findByCourse(org.openuss.lecture.Course course)
    {
        return this.findByCourse(TRANSFORM_NONE, course);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#findByCourse(java.lang.String, org.openuss.lecture.Course)
     */
    public java.util.List findByCourse(final java.lang.String queryString, final org.openuss.lecture.Course course)
    {
        return this.findByCourse(TRANSFORM_NONE, queryString, course);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#findByCourse(int, org.openuss.lecture.Course)
     */
    public java.util.List findByCourse(final int transform, final org.openuss.lecture.Course course)
    {
        return this.findByCourse(transform, "from org.openuss.lecture.CourseMember as courseMember where courseMember.course = ?", course);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#findByCourse(int, java.lang.String, org.openuss.lecture.Course)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByCourse(final int transform, final java.lang.String queryString, final org.openuss.lecture.Course course)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, course);
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
     * @see org.openuss.lecture.CourseMemberDao#findByType(org.openuss.lecture.Course, org.openuss.lecture.CourseMemberType)
     */
    public java.util.List findByType(org.openuss.lecture.Course course, org.openuss.lecture.CourseMemberType memberType)
    {
        return this.findByType(TRANSFORM_NONE, course, memberType);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#findByType(java.lang.String, org.openuss.lecture.Course, org.openuss.lecture.CourseMemberType)
     */
    public java.util.List findByType(final java.lang.String queryString, final org.openuss.lecture.Course course, final org.openuss.lecture.CourseMemberType memberType)
    {
        return this.findByType(TRANSFORM_NONE, queryString, course, memberType);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#findByType(int, org.openuss.lecture.Course, org.openuss.lecture.CourseMemberType)
     */
    public java.util.List findByType(final int transform, final org.openuss.lecture.Course course, final org.openuss.lecture.CourseMemberType memberType)
    {
        return this.findByType(transform, "from org.openuss.lecture.CourseMember as courseMember where courseMember.course = ? and courseMember.memberType = ?", course, memberType);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#findByType(int, java.lang.String, org.openuss.lecture.Course, org.openuss.lecture.CourseMemberType)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByType(final int transform, final java.lang.String queryString, final org.openuss.lecture.Course course, final org.openuss.lecture.CourseMemberType memberType)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, course);
            queryObject.setParameter(1, memberType.getValue());
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
     * @see org.openuss.lecture.CourseMemberDao#findByUser(org.openuss.security.User)
     */
    public java.util.List findByUser(org.openuss.security.User user)
    {
        return this.findByUser(TRANSFORM_NONE, user);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#findByUser(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findByUser(final java.lang.String queryString, final org.openuss.security.User user)
    {
        return this.findByUser(TRANSFORM_NONE, queryString, user);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#findByUser(int, org.openuss.security.User)
     */
    public java.util.List findByUser(final int transform, final org.openuss.security.User user)
    {
        return this.findByUser(transform, "from org.openuss.lecture.CourseMember as courseMember where courseMember.user = ?", user);
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#findByUser(int, java.lang.String, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByUser(final int transform, final java.lang.String queryString, final org.openuss.security.User user)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, user);
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
     * flag is set to one of the constants defined in <code>org.openuss.lecture.CourseMemberDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.lecture.CourseMember} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.lecture.CourseMemberInfo} - {@link TRANSFORM_COURSEMEMBERINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.lecture.CourseMemberDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.lecture.CourseMember entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_COURSEMEMBERINFO :
                    target = toCourseMemberInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.lecture.CourseMember)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.lecture.CourseMemberDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.lecture.CourseMember)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_COURSEMEMBERINFO :
                toCourseMemberInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#toCourseMemberInfoCollection(java.util.Collection)
     */
    public final void toCourseMemberInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, COURSEMEMBERINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link CourseMemberDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.lecture.CourseMemberDao#toCourseMemberInfo(org.openuss.lecture.CourseMember)
     */
    protected org.openuss.lecture.CourseMemberInfo toCourseMemberInfo(java.lang.Object[] row)
    {
        org.openuss.lecture.CourseMemberInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.lecture.CourseMember)
                {
                    target = this.toCourseMemberInfo((org.openuss.lecture.CourseMember)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.lecture.CourseMemberInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer COURSEMEMBERINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.lecture.CourseMember)
                {
                    result = toCourseMemberInfo((org.openuss.lecture.CourseMember)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toCourseMemberInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.lecture.CourseMemberDao#courseMemberInfoToEntityCollection(java.util.Collection)
     */
    public final void courseMemberInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.lecture.CourseMemberInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, CourseMemberInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer CourseMemberInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return courseMemberInfoToEntity((org.openuss.lecture.CourseMemberInfo)input);
            }
        };

    /**
     * @see org.openuss.lecture.CourseMemberDao#toCourseMemberInfo(org.openuss.lecture.CourseMember, org.openuss.lecture.CourseMemberInfo)
     */
    public void toCourseMemberInfo(
        org.openuss.lecture.CourseMember source,
        org.openuss.lecture.CourseMemberInfo target)
    {
        //TODO: if any VO attribute maps with identifier association ends, map it in the impl class
        target.setMemberType(source.getMemberType());
    }

    /**
     * @see org.openuss.lecture.CourseMemberDao#toCourseMemberInfo(org.openuss.lecture.CourseMember)
     */
    public org.openuss.lecture.CourseMemberInfo toCourseMemberInfo(final org.openuss.lecture.CourseMember entity)
    {
        final org.openuss.lecture.CourseMemberInfo target = new org.openuss.lecture.CourseMemberInfo();
        this.toCourseMemberInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.lecture.CourseMemberDao#courseMemberInfoToEntity(org.openuss.lecture.CourseMemberInfo, org.openuss.lecture.CourseMember)
     */
    public void courseMemberInfoToEntity(
        org.openuss.lecture.CourseMemberInfo source,
        org.openuss.lecture.CourseMember target,
        boolean copyIfNull)
    {
        //TODO: if any VO attribute maps with identifier association ends, map it in the impl class
        if(target.getCourseMemberPk() == null)
        {
            target.setCourseMemberPk(new CourseMemberPK());
        }
        if (copyIfNull || source.getMemberType() != null)
        {
            target.setMemberType(source.getMemberType());
        }
    }
    
}