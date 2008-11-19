package org.openuss.security.acl;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.security.acl.ObjectIdentity</code>.
 * </p>
 *
 * @see org.openuss.security.acl.ObjectIdentity
 */
public abstract class ObjectIdentityDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.security.acl.ObjectIdentityDao
{


    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "ObjectIdentity.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.security.acl.ObjectIdentityImpl.class, id);
        return transformEntity(transform, (org.openuss.security.acl.ObjectIdentity)entity);
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#load(java.lang.Long)
     */
    public org.openuss.security.acl.ObjectIdentity load(java.lang.Long id)
    {
        return (org.openuss.security.acl.ObjectIdentity)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.acl.ObjectIdentity> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.security.acl.ObjectIdentityImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#create(org.openuss.security.acl.ObjectIdentity)
     */
    public org.openuss.security.acl.ObjectIdentity create(org.openuss.security.acl.ObjectIdentity objectIdentity)
    {
        return (org.openuss.security.acl.ObjectIdentity)this.create(TRANSFORM_NONE, objectIdentity);
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#create(int transform, org.openuss.security.acl.ObjectIdentity)
     */
    public java.lang.Object create(final int transform, final org.openuss.security.acl.ObjectIdentity objectIdentity)
    {
        if (objectIdentity == null)
        {
            throw new IllegalArgumentException(
                "ObjectIdentity.create - 'objectIdentity' can not be null");
        }
        this.getHibernateTemplate().save(objectIdentity);
        return this.transformEntity(transform, objectIdentity);
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#create(java.util.Collection<org.openuss.security.acl.ObjectIdentity>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.acl.ObjectIdentity> create(final java.util.Collection<org.openuss.security.acl.ObjectIdentity> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#create(int, java.util.Collection<org.openuss.security.acl.ObjectIdentity>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.security.acl.ObjectIdentity> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ObjectIdentity.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.security.acl.ObjectIdentity)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#update(org.openuss.security.acl.ObjectIdentity)
     */
    public void update(org.openuss.security.acl.ObjectIdentity objectIdentity)
    {
        if (objectIdentity == null)
        {
            throw new IllegalArgumentException(
                "ObjectIdentity.update - 'objectIdentity' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(objectIdentity);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(objectIdentity);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#update(java.util.Collection<org.openuss.security.acl.ObjectIdentity>)
     */
    public void update(final java.util.Collection<org.openuss.security.acl.ObjectIdentity> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ObjectIdentity.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.security.acl.ObjectIdentity)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#remove(org.openuss.security.acl.ObjectIdentity)
     */
    public void remove(org.openuss.security.acl.ObjectIdentity objectIdentity)
    {
        if (objectIdentity == null)
        {
            throw new IllegalArgumentException(
                "ObjectIdentity.remove - 'objectIdentity' can not be null");
        }
        this.getHibernateTemplate().delete(objectIdentity);
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "ObjectIdentity.remove - 'id can not be null");
        }
        org.openuss.security.acl.ObjectIdentity entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.security.acl.ObjectIdentityDao#remove(java.util.Collection<org.openuss.security.acl.ObjectIdentity>)
     */
    public void remove(java.util.Collection<org.openuss.security.acl.ObjectIdentity> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ObjectIdentity.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.security.acl.ObjectIdentityDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.security.acl.ObjectIdentityDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.security.acl.ObjectIdentity entity)
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
     * {@link #transformEntity(int,org.openuss.security.acl.ObjectIdentity)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.security.acl.ObjectIdentityDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.security.acl.ObjectIdentity)
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