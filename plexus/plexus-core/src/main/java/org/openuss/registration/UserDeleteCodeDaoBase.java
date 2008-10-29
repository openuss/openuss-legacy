package org.openuss.registration;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.registration.UserDeleteCode</code>.
 * </p>
 *
 * @see org.openuss.registration.UserDeleteCode
 */
public abstract class UserDeleteCodeDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.registration.UserDeleteCodeDao
{


    /**
     * @see org.openuss.registration.UserDeleteCodeDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "UserDeleteCode.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.registration.UserDeleteCodeImpl.class, id);
        return transformEntity(transform, (org.openuss.registration.UserDeleteCode)entity);
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#load(java.lang.Long)
     */
    public org.openuss.registration.UserDeleteCode load(java.lang.Long id)
    {
        return (org.openuss.registration.UserDeleteCode)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.registration.UserDeleteCode> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.registration.UserDeleteCodeImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.registration.UserDeleteCodeDao#create(org.openuss.registration.UserDeleteCode)
     */
    public org.openuss.registration.UserDeleteCode create(org.openuss.registration.UserDeleteCode userDeleteCode)
    {
        return (org.openuss.registration.UserDeleteCode)this.create(TRANSFORM_NONE, userDeleteCode);
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#create(int transform, org.openuss.registration.UserDeleteCode)
     */
    public java.lang.Object create(final int transform, final org.openuss.registration.UserDeleteCode userDeleteCode)
    {
        if (userDeleteCode == null)
        {
            throw new IllegalArgumentException(
                "UserDeleteCode.create - 'userDeleteCode' can not be null");
        }
        this.getHibernateTemplate().save(userDeleteCode);
        return this.transformEntity(transform, userDeleteCode);
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#create(java.util.Collection<org.openuss.registration.UserDeleteCode>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.registration.UserDeleteCode> create(final java.util.Collection<org.openuss.registration.UserDeleteCode> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#create(int, java.util.Collection<org.openuss.registration.UserDeleteCode>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.registration.UserDeleteCode> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "UserDeleteCode.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.registration.UserDeleteCode)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#update(org.openuss.registration.UserDeleteCode)
     */
    public void update(org.openuss.registration.UserDeleteCode userDeleteCode)
    {
        if (userDeleteCode == null)
        {
            throw new IllegalArgumentException(
                "UserDeleteCode.update - 'userDeleteCode' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(userDeleteCode);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(userDeleteCode);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#update(java.util.Collection<org.openuss.registration.UserDeleteCode>)
     */
    public void update(final java.util.Collection<org.openuss.registration.UserDeleteCode> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "UserDeleteCode.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.registration.UserDeleteCode)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#remove(org.openuss.registration.UserDeleteCode)
     */
    public void remove(org.openuss.registration.UserDeleteCode userDeleteCode)
    {
        if (userDeleteCode == null)
        {
            throw new IllegalArgumentException(
                "UserDeleteCode.remove - 'userDeleteCode' can not be null");
        }
        this.getHibernateTemplate().delete(userDeleteCode);
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "UserDeleteCode.remove - 'id can not be null");
        }
        org.openuss.registration.UserDeleteCode entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#remove(java.util.Collection<org.openuss.registration.UserDeleteCode>)
     */
    public void remove(java.util.Collection<org.openuss.registration.UserDeleteCode> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "UserDeleteCode.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.registration.UserDeleteCodeDao#findByCode(java.lang.String)
     */
    public org.openuss.registration.UserDeleteCode findByCode(java.lang.String code)
    {
        return (org.openuss.registration.UserDeleteCode)this.findByCode(TRANSFORM_NONE, code);
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#findByCode(java.lang.String, java.lang.String)
     */
    public org.openuss.registration.UserDeleteCode findByCode(final java.lang.String queryString, final java.lang.String code)
    {
        return (org.openuss.registration.UserDeleteCode)this.findByCode(TRANSFORM_NONE, queryString, code);
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#findByCode(int, java.lang.String)
     */
    public java.lang.Object findByCode(final int transform, final java.lang.String code)
    {
        return this.findByCode(transform, "from org.openuss.registration.UserDeleteCode as userDeleteCode where userDeleteCode.code = ?", code);
    }

    /**
     * @see org.openuss.registration.UserDeleteCodeDao#findByCode(int, java.lang.String, java.lang.String)
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
                        "More than one instance of 'org.openuss.registration.UserDeleteCode"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.registration.UserDeleteCode)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.registration.UserDeleteCodeDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.registration.UserDeleteCodeDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.registration.UserDeleteCode entity)
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
     * {@link #transformEntity(int,org.openuss.registration.UserDeleteCode)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.registration.UserDeleteCodeDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.registration.UserDeleteCode)
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