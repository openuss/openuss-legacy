package org.openuss.viewtracking;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.viewtracking.DomainViewState</code>.
 * </p>
 *
 * @see org.openuss.viewtracking.DomainViewState
 */
public abstract class DomainViewStateDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.viewtracking.DomainViewStateDao
{


    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final org.openuss.viewtracking.DomainViewStatePK domainViewStatePk)
    {
        if (domainViewStatePk == null)
        {
            throw new IllegalArgumentException(
                "DomainViewState.load - 'domainViewStatePk' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.viewtracking.DomainViewStateImpl.class, domainViewStatePk);
        return transformEntity(transform, (org.openuss.viewtracking.DomainViewState)entity);
    }

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#load(org.openuss.viewtracking.DomainViewStatePK)
     */
    public org.openuss.viewtracking.DomainViewState load(org.openuss.viewtracking.DomainViewStatePK domainViewStatePk)
    {
        return (org.openuss.viewtracking.DomainViewState)this.load(TRANSFORM_NONE, domainViewStatePk);
    }

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.viewtracking.DomainViewState> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.viewtracking.DomainViewStateImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#create(org.openuss.viewtracking.DomainViewState)
     */
    public org.openuss.viewtracking.DomainViewState create(org.openuss.viewtracking.DomainViewState domainViewState)
    {
        return (org.openuss.viewtracking.DomainViewState)this.create(TRANSFORM_NONE, domainViewState);
    }

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#create(int transform, org.openuss.viewtracking.DomainViewState)
     */
    public java.lang.Object create(final int transform, final org.openuss.viewtracking.DomainViewState domainViewState)
    {
        if (domainViewState == null)
        {
            throw new IllegalArgumentException(
                "DomainViewState.create - 'domainViewState' can not be null");
        }
        this.getHibernateTemplate().save(domainViewState);
        return this.transformEntity(transform, domainViewState);
    }

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#create(java.util.Collection<org.openuss.viewtracking.DomainViewState>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.viewtracking.DomainViewState> create(final java.util.Collection<org.openuss.viewtracking.DomainViewState> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#create(int, java.util.Collection<org.openuss.viewtracking.DomainViewState>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.viewtracking.DomainViewState> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "DomainViewState.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.viewtracking.DomainViewState)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#update(org.openuss.viewtracking.DomainViewState)
     */
    public void update(org.openuss.viewtracking.DomainViewState domainViewState)
    {
        if (domainViewState == null)
        {
            throw new IllegalArgumentException(
                "DomainViewState.update - 'domainViewState' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(domainViewState);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(domainViewState);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#update(java.util.Collection<org.openuss.viewtracking.DomainViewState>)
     */
    public void update(final java.util.Collection<org.openuss.viewtracking.DomainViewState> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "DomainViewState.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.viewtracking.DomainViewState)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#remove(org.openuss.viewtracking.DomainViewState)
     */
    public void remove(org.openuss.viewtracking.DomainViewState domainViewState)
    {
        if (domainViewState == null)
        {
            throw new IllegalArgumentException(
                "DomainViewState.remove - 'domainViewState' can not be null");
        }
        this.getHibernateTemplate().delete(domainViewState);
    }

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#remove(java.lang.Long)
     */
    public void remove(org.openuss.viewtracking.DomainViewStatePK domainViewStatePk)
    {
        if (domainViewStatePk == null)
        {
            throw new IllegalArgumentException(
                "DomainViewState.remove - 'domainViewStatePk can not be null");
        }
        org.openuss.viewtracking.DomainViewState entity = this.load(domainViewStatePk);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#remove(java.util.Collection<org.openuss.viewtracking.DomainViewState>)
     */
    public void remove(java.util.Collection<org.openuss.viewtracking.DomainViewState> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "DomainViewState.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#updateAllToModified(java.lang.Long)
     */
    public void updateAllToModified(final java.lang.Long domainIdentifier)
    {
        try
        {
            this.handleUpdateAllToModified(domainIdentifier);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.viewtracking.DomainViewStateDao.updateAllToModified(java.lang.Long domainIdentifier)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #updateAllToModified(java.lang.Long)}
      */
    protected abstract void handleUpdateAllToModified(java.lang.Long domainIdentifier)
        throws java.lang.Exception;

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#removeAllByDomain(java.lang.Long)
     */
    public void removeAllByDomain(final java.lang.Long domainIdentifier)
    {
        try
        {
            this.handleRemoveAllByDomain(domainIdentifier);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.viewtracking.DomainViewStateDao.removeAllByDomain(java.lang.Long domainIdentifier)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #removeAllByDomain(java.lang.Long)}
      */
    protected abstract void handleRemoveAllByDomain(java.lang.Long domainIdentifier)
        throws java.lang.Exception;

    /**
     * @see org.openuss.viewtracking.DomainViewStateDao#getTopicViewStates(java.lang.Long, java.lang.Long)
     */
    public java.util.List getTopicViewStates(final java.lang.Long domainIdentifier, final java.lang.Long userId)
    {
        try
        {
            return this.handleGetTopicViewStates(domainIdentifier, userId);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.viewtracking.DomainViewStateDao.getTopicViewStates(java.lang.Long domainIdentifier, java.lang.Long userId)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #getTopicViewStates(java.lang.Long, java.lang.Long)}
      */
    protected abstract java.util.List handleGetTopicViewStates(java.lang.Long domainIdentifier, java.lang.Long userId)
        throws java.lang.Exception;

    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.viewtracking.DomainViewStateDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.viewtracking.DomainViewStateDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.viewtracking.DomainViewState entity)
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
     * {@link #transformEntity(int,org.openuss.viewtracking.DomainViewState)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.viewtracking.DomainViewStateDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.viewtracking.DomainViewState)
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