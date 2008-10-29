package org.openuss.system;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.system.SystemProperty</code>.
 * </p>
 *
 * @see org.openuss.system.SystemProperty
 */
public abstract class SystemPropertyDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.system.SystemPropertyDao
{


    /**
     * @see org.openuss.system.SystemPropertyDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "SystemProperty.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.system.SystemPropertyImpl.class, id);
        return transformEntity(transform, (org.openuss.system.SystemProperty)entity);
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#load(java.lang.Long)
     */
    public org.openuss.system.SystemProperty load(java.lang.Long id)
    {
        return (org.openuss.system.SystemProperty)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.system.SystemProperty> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.system.SystemPropertyImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.system.SystemPropertyDao#create(org.openuss.system.SystemProperty)
     */
    public org.openuss.system.SystemProperty create(org.openuss.system.SystemProperty systemProperty)
    {
        return (org.openuss.system.SystemProperty)this.create(TRANSFORM_NONE, systemProperty);
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#create(int transform, org.openuss.system.SystemProperty)
     */
    public java.lang.Object create(final int transform, final org.openuss.system.SystemProperty systemProperty)
    {
        if (systemProperty == null)
        {
            throw new IllegalArgumentException(
                "SystemProperty.create - 'systemProperty' can not be null");
        }
        this.getHibernateTemplate().save(systemProperty);
        return this.transformEntity(transform, systemProperty);
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#create(java.util.Collection<org.openuss.system.SystemProperty>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.system.SystemProperty> create(final java.util.Collection<org.openuss.system.SystemProperty> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#create(int, java.util.Collection<org.openuss.system.SystemProperty>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.system.SystemProperty> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "SystemProperty.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.system.SystemProperty)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#update(org.openuss.system.SystemProperty)
     */
    public void update(org.openuss.system.SystemProperty systemProperty)
    {
        if (systemProperty == null)
        {
            throw new IllegalArgumentException(
                "SystemProperty.update - 'systemProperty' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(systemProperty);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(systemProperty);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#update(java.util.Collection<org.openuss.system.SystemProperty>)
     */
    public void update(final java.util.Collection<org.openuss.system.SystemProperty> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "SystemProperty.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.system.SystemProperty)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#remove(org.openuss.system.SystemProperty)
     */
    public void remove(org.openuss.system.SystemProperty systemProperty)
    {
        if (systemProperty == null)
        {
            throw new IllegalArgumentException(
                "SystemProperty.remove - 'systemProperty' can not be null");
        }
        this.getHibernateTemplate().delete(systemProperty);
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "SystemProperty.remove - 'id can not be null");
        }
        org.openuss.system.SystemProperty entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#remove(java.util.Collection<org.openuss.system.SystemProperty>)
     */
    public void remove(java.util.Collection<org.openuss.system.SystemProperty> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "SystemProperty.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.system.SystemPropertyDao#findByName(java.lang.String)
     */
    public org.openuss.system.SystemProperty findByName(java.lang.String name)
    {
        return (org.openuss.system.SystemProperty)this.findByName(TRANSFORM_NONE, name);
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#findByName(java.lang.String, java.lang.String)
     */
    public org.openuss.system.SystemProperty findByName(final java.lang.String queryString, final java.lang.String name)
    {
        return (org.openuss.system.SystemProperty)this.findByName(TRANSFORM_NONE, queryString, name);
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#findByName(int, java.lang.String)
     */
    public java.lang.Object findByName(final int transform, final java.lang.String name)
    {
        return this.findByName(transform, "from org.openuss.system.SystemProperty as p where p.name = :name order by name", name);
    }

    /**
     * @see org.openuss.system.SystemPropertyDao#findByName(int, java.lang.String, java.lang.String)
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
                        "More than one instance of 'org.openuss.system.SystemProperty"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.system.SystemProperty)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.system.SystemPropertyDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.system.SystemPropertyDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.system.SystemProperty entity)
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
     * {@link #transformEntity(int,org.openuss.system.SystemProperty)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.system.SystemPropertyDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.system.SystemProperty)
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