package org.openuss.lecture;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.lecture.Organisation</code>.
 * </p>
 *
 * @see org.openuss.lecture.Organisation
 */
public abstract class OrganisationDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.lecture.OrganisationDao
{


    /**
     * @see org.openuss.lecture.OrganisationDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Organisation.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.lecture.OrganisationImpl.class, id);
        return transformEntity(transform, (org.openuss.lecture.Organisation)entity);
    }

    /**
     * @see org.openuss.lecture.OrganisationDao#load(java.lang.Long)
     */
    public org.openuss.lecture.Organisation load(java.lang.Long id)
    {
        return (org.openuss.lecture.Organisation)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.lecture.OrganisationDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.Organisation> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.lecture.OrganisationDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.lecture.OrganisationImpl.class);
        this.transformEntities(transform, results);
        return results;
    }

    /**
     * @see org.openuss.lecture.OrganisationDao#update(org.openuss.lecture.Organisation)
     */
    public void update(org.openuss.lecture.Organisation organisation)
    {
        if (organisation == null)
        {
            throw new IllegalArgumentException(
                "Organisation.update - 'organisation' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(organisation);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(organisation);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.lecture.OrganisationDao#update(java.util.Collection<org.openuss.lecture.Organisation>)
     */
    public void update(final java.util.Collection<org.openuss.lecture.Organisation> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Organisation.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.lecture.Organisation)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.lecture.OrganisationDao#remove(org.openuss.lecture.Organisation)
     */
    public void remove(org.openuss.lecture.Organisation organisation)
    {
        if (organisation == null)
        {
            throw new IllegalArgumentException(
                "Organisation.remove - 'organisation' can not be null");
        }
        this.getHibernateTemplate().delete(organisation);
    }

    /**
     * @see org.openuss.lecture.OrganisationDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Organisation.remove - 'id can not be null");
        }
        org.openuss.lecture.Organisation entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.lecture.OrganisationDao#remove(java.util.Collection<org.openuss.lecture.Organisation>)
     */
    public void remove(java.util.Collection<org.openuss.lecture.Organisation> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Organisation.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.lecture.OrganisationDao#findByShortcut(java.lang.String)
     */
    public org.openuss.lecture.Organisation findByShortcut(java.lang.String shortcut)
    {
        return (org.openuss.lecture.Organisation)this.findByShortcut(TRANSFORM_NONE, shortcut);
    }

    /**
     * @see org.openuss.lecture.OrganisationDao#findByShortcut(java.lang.String, java.lang.String)
     */
    public org.openuss.lecture.Organisation findByShortcut(final java.lang.String queryString, final java.lang.String shortcut)
    {
        return (org.openuss.lecture.Organisation)this.findByShortcut(TRANSFORM_NONE, queryString, shortcut);
    }

    /**
     * @see org.openuss.lecture.OrganisationDao#findByShortcut(int, java.lang.String)
     */
    public java.lang.Object findByShortcut(final int transform, final java.lang.String shortcut)
    {
        return this.findByShortcut(transform, "from org.openuss.lecture.Organisation as f where f.shortcut = :shortcut", shortcut);
    }

    /**
     * @see org.openuss.lecture.OrganisationDao#findByShortcut(int, java.lang.String, java.lang.String)
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
                        "More than one instance of 'org.openuss.lecture.Organisation"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.lecture.Organisation)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.lecture.OrganisationDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.lecture.OrganisationDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.lecture.Organisation entity)
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
     * {@link #transformEntity(int,org.openuss.lecture.Organisation)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.lecture.OrganisationDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.lecture.Organisation)
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