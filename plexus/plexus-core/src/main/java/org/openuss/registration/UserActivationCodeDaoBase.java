package org.openuss.registration;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.registration.UserActivationCode</code>.
 * </p>
 *
 * @see org.openuss.registration.UserActivationCode
 */
public abstract class UserActivationCodeDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.registration.UserActivationCodeDao
{


    /**
     * @see org.openuss.registration.UserActivationCodeDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "UserActivationCode.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.registration.UserActivationCodeImpl.class, id);
        return transformEntity(transform, (org.openuss.registration.UserActivationCode)entity);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#load(java.lang.Long)
     */
    public org.openuss.registration.UserActivationCode load(java.lang.Long id)
    {
        return (org.openuss.registration.UserActivationCode)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.registration.UserActivationCode> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.registration.UserActivationCodeImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.registration.UserActivationCodeDao#create(org.openuss.registration.UserActivationCode)
     */
    public org.openuss.registration.UserActivationCode create(org.openuss.registration.UserActivationCode userActivationCode)
    {
        return (org.openuss.registration.UserActivationCode)this.create(TRANSFORM_NONE, userActivationCode);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#create(int transform, org.openuss.registration.UserActivationCode)
     */
    public java.lang.Object create(final int transform, final org.openuss.registration.UserActivationCode userActivationCode)
    {
        if (userActivationCode == null)
        {
            throw new IllegalArgumentException(
                "UserActivationCode.create - 'userActivationCode' can not be null");
        }
        this.getHibernateTemplate().save(userActivationCode);
        return this.transformEntity(transform, userActivationCode);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#create(java.util.Collection<org.openuss.registration.UserActivationCode>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.registration.UserActivationCode> create(final java.util.Collection<org.openuss.registration.UserActivationCode> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#create(int, java.util.Collection<org.openuss.registration.UserActivationCode>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.registration.UserActivationCode> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "UserActivationCode.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.registration.UserActivationCode)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#update(org.openuss.registration.UserActivationCode)
     */
    public void update(org.openuss.registration.UserActivationCode userActivationCode)
    {
        if (userActivationCode == null)
        {
            throw new IllegalArgumentException(
                "UserActivationCode.update - 'userActivationCode' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(userActivationCode);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(userActivationCode);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#update(java.util.Collection<org.openuss.registration.UserActivationCode>)
     */
    public void update(final java.util.Collection<org.openuss.registration.UserActivationCode> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "UserActivationCode.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.registration.UserActivationCode)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#remove(org.openuss.registration.UserActivationCode)
     */
    public void remove(org.openuss.registration.UserActivationCode userActivationCode)
    {
        if (userActivationCode == null)
        {
            throw new IllegalArgumentException(
                "UserActivationCode.remove - 'userActivationCode' can not be null");
        }
        this.getHibernateTemplate().delete(userActivationCode);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "UserActivationCode.remove - 'id can not be null");
        }
        org.openuss.registration.UserActivationCode entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#remove(java.util.Collection<org.openuss.registration.UserActivationCode>)
     */
    public void remove(java.util.Collection<org.openuss.registration.UserActivationCode> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "UserActivationCode.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.registration.UserActivationCodeDao#findByCode(java.lang.String)
     */
    public org.openuss.registration.UserActivationCode findByCode(java.lang.String code)
    {
        return (org.openuss.registration.UserActivationCode)this.findByCode(TRANSFORM_NONE, code);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#findByCode(java.lang.String, java.lang.String)
     */
    public org.openuss.registration.UserActivationCode findByCode(final java.lang.String queryString, final java.lang.String code)
    {
        return (org.openuss.registration.UserActivationCode)this.findByCode(TRANSFORM_NONE, queryString, code);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#findByCode(int, java.lang.String)
     */
    public java.lang.Object findByCode(final int transform, final java.lang.String code)
    {
        return this.findByCode(transform, "from org.openuss.registration.UserActivationCode as userActivationCode where userActivationCode.code = ?", code);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#findByCode(int, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByCode(final int transform, final java.lang.String queryString, final java.lang.String code)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, code);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.registration.UserActivationCode"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.registration.UserActivationCode)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#findByUser(org.openuss.security.User)
     */
    public java.util.List findByUser(org.openuss.security.User user)
    {
        return this.findByUser(TRANSFORM_NONE, user);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#findByUser(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findByUser(final java.lang.String queryString, final org.openuss.security.User user)
    {
        return this.findByUser(TRANSFORM_NONE, queryString, user);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#findByUser(int, org.openuss.security.User)
     */
    public java.util.List findByUser(final int transform, final org.openuss.security.User user)
    {
        return this.findByUser(transform, "from org.openuss.registration.UserActivationCode as userActivationCode where userActivationCode.user = ?", user);
    }

    /**
     * @see org.openuss.registration.UserActivationCodeDao#findByUser(int, java.lang.String, org.openuss.security.User)
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
     * flag is set to one of the constants defined in <code>org.openuss.registration.UserActivationCodeDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.registration.UserActivationCodeDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.registration.UserActivationCode entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_NONE : // fall-through
                default:
                    target = entity;
            }
        }
        return target;
    }

    /**
     * Transforms a collection of entities using the
     * {@link #transformEntity(int,org.openuss.registration.UserActivationCode)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.registration.UserActivationCodeDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.registration.UserActivationCode)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

}