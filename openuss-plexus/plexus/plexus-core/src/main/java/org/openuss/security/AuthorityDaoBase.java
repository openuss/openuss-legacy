package org.openuss.security;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.security.Authority</code>.
 * </p>
 *
 * @see org.openuss.security.Authority
 */
public abstract class AuthorityDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.security.AuthorityDao
{


    /**
     * @see org.openuss.security.AuthorityDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Authority.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.security.AuthorityImpl.class, id);
        return transformEntity(transform, (org.openuss.security.Authority)entity);
    }

    /**
     * @see org.openuss.security.AuthorityDao#load(java.lang.Long)
     */
    public org.openuss.security.Authority load(java.lang.Long id)
    {
        return (org.openuss.security.Authority)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.security.AuthorityDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.Authority> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.AuthorityDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.security.AuthorityImpl.class);
        this.transformEntities(transform, results);
        return results;
    }

    /**
     * @see org.openuss.security.AuthorityDao#update(org.openuss.security.Authority)
     */
    public void update(org.openuss.security.Authority authority)
    {
        if (authority == null)
        {
            throw new IllegalArgumentException(
                "Authority.update - 'authority' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(authority);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(authority);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.security.AuthorityDao#update(java.util.Collection<org.openuss.security.Authority>)
     */
    public void update(final java.util.Collection<org.openuss.security.Authority> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Authority.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.security.Authority)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.security.AuthorityDao#remove(org.openuss.security.Authority)
     */
    public void remove(org.openuss.security.Authority authority)
    {
        if (authority == null)
        {
            throw new IllegalArgumentException(
                "Authority.remove - 'authority' can not be null");
        }
        this.getHibernateTemplate().delete(authority);
    }

    /**
     * @see org.openuss.security.AuthorityDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Authority.remove - 'id can not be null");
        }
        org.openuss.security.Authority entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.security.AuthorityDao#remove(java.util.Collection<org.openuss.security.Authority>)
     */
    public void remove(java.util.Collection<org.openuss.security.Authority> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Authority.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.security.AuthorityDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.security.AuthorityDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.security.Authority entity)
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
     * {@link #transformEntity(int,org.openuss.security.Authority)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.security.AuthorityDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.security.Authority)
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