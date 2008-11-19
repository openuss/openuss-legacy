package org.openuss.security;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.security.Membership</code>.
 * </p>
 *
 * @see org.openuss.security.Membership
 */
public abstract class MembershipDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.security.MembershipDao
{


    /**
     * @see org.openuss.security.MembershipDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Membership.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.security.MembershipImpl.class, id);
        return transformEntity(transform, (org.openuss.security.Membership)entity);
    }

    /**
     * @see org.openuss.security.MembershipDao#load(java.lang.Long)
     */
    public org.openuss.security.Membership load(java.lang.Long id)
    {
        return (org.openuss.security.Membership)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.security.MembershipDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.Membership> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.MembershipDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.security.MembershipImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.security.MembershipDao#create(org.openuss.security.Membership)
     */
    public org.openuss.security.Membership create(org.openuss.security.Membership membership)
    {
        return (org.openuss.security.Membership)this.create(TRANSFORM_NONE, membership);
    }

    /**
     * @see org.openuss.security.MembershipDao#create(int transform, org.openuss.security.Membership)
     */
    public java.lang.Object create(final int transform, final org.openuss.security.Membership membership)
    {
        if (membership == null)
        {
            throw new IllegalArgumentException(
                "Membership.create - 'membership' can not be null");
        }
        this.getHibernateTemplate().save(membership);
        return this.transformEntity(transform, membership);
    }

    /**
     * @see org.openuss.security.MembershipDao#create(java.util.Collection<org.openuss.security.Membership>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.Membership> create(final java.util.Collection<org.openuss.security.Membership> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.security.MembershipDao#create(int, java.util.Collection<org.openuss.security.Membership>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.security.Membership> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Membership.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.security.Membership)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.security.MembershipDao#update(org.openuss.security.Membership)
     */
    public void update(org.openuss.security.Membership membership)
    {
        if (membership == null)
        {
            throw new IllegalArgumentException(
                "Membership.update - 'membership' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(membership);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(membership);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.security.MembershipDao#update(java.util.Collection<org.openuss.security.Membership>)
     */
    public void update(final java.util.Collection<org.openuss.security.Membership> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Membership.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.security.Membership)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.security.MembershipDao#remove(org.openuss.security.Membership)
     */
    public void remove(org.openuss.security.Membership membership)
    {
        if (membership == null)
        {
            throw new IllegalArgumentException(
                "Membership.remove - 'membership' can not be null");
        }
        this.getHibernateTemplate().delete(membership);
    }

    /**
     * @see org.openuss.security.MembershipDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Membership.remove - 'id can not be null");
        }
        org.openuss.security.Membership entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.security.MembershipDao#remove(java.util.Collection<org.openuss.security.Membership>)
     */
    public void remove(java.util.Collection<org.openuss.security.Membership> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Membership.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.security.MembershipDao#findByMember(org.openuss.security.User)
     */
    public java.util.List findByMember(final org.openuss.security.User user)
    {
        try
        {
            return this.handleFindByMember(user);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.security.MembershipDao.findByMember(org.openuss.security.User user)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #findByMember(org.openuss.security.User)}
      */
    protected abstract java.util.List handleFindByMember(org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.security.MembershipDao#findByAspirant(org.openuss.security.User)
     */
    public java.util.List findByAspirant(final org.openuss.security.User aspirant)
    {
        try
        {
            return this.handleFindByAspirant(aspirant);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.security.MembershipDao.findByAspirant(org.openuss.security.User aspirant)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #findByAspirant(org.openuss.security.User)}
      */
    protected abstract java.util.List handleFindByAspirant(org.openuss.security.User aspirant)
        throws java.lang.Exception;

    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.security.MembershipDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.security.MembershipDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.security.Membership entity)
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
     * {@link #transformEntity(int,org.openuss.security.Membership)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.security.MembershipDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.security.Membership)
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