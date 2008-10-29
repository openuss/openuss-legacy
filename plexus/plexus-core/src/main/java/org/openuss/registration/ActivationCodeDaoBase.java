package org.openuss.registration;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.registration.ActivationCode</code>.
 * </p>
 *
 * @see org.openuss.registration.ActivationCode
 */
public abstract class ActivationCodeDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.registration.ActivationCodeDao
{


    /**
     * @see org.openuss.registration.ActivationCodeDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "ActivationCode.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.registration.ActivationCodeImpl.class, id);
        return transformEntity(transform, (org.openuss.registration.ActivationCode)entity);
    }

    /**
     * @see org.openuss.registration.ActivationCodeDao#load(java.lang.Long)
     */
    public org.openuss.registration.ActivationCode load(java.lang.Long id)
    {
        return (org.openuss.registration.ActivationCode)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.registration.ActivationCodeDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.registration.ActivationCode> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.registration.ActivationCodeDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.registration.ActivationCodeImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.registration.ActivationCodeDao#create(org.openuss.registration.ActivationCode)
     */
    public org.openuss.registration.ActivationCode create(org.openuss.registration.ActivationCode activationCode)
    {
        return (org.openuss.registration.ActivationCode)this.create(TRANSFORM_NONE, activationCode);
    }

    /**
     * @see org.openuss.registration.ActivationCodeDao#create(int transform, org.openuss.registration.ActivationCode)
     */
    public java.lang.Object create(final int transform, final org.openuss.registration.ActivationCode activationCode)
    {
        if (activationCode == null)
        {
            throw new IllegalArgumentException(
                "ActivationCode.create - 'activationCode' can not be null");
        }
        this.getHibernateTemplate().save(activationCode);
        return this.transformEntity(transform, activationCode);
    }

    /**
     * @see org.openuss.registration.ActivationCodeDao#create(java.util.Collection<org.openuss.registration.ActivationCode>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.registration.ActivationCode> create(final java.util.Collection<org.openuss.registration.ActivationCode> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.registration.ActivationCodeDao#create(int, java.util.Collection<org.openuss.registration.ActivationCode>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.registration.ActivationCode> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ActivationCode.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.registration.ActivationCode)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.registration.ActivationCodeDao#update(org.openuss.registration.ActivationCode)
     */
    public void update(org.openuss.registration.ActivationCode activationCode)
    {
        if (activationCode == null)
        {
            throw new IllegalArgumentException(
                "ActivationCode.update - 'activationCode' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(activationCode);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(activationCode);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.registration.ActivationCodeDao#update(java.util.Collection<org.openuss.registration.ActivationCode>)
     */
    public void update(final java.util.Collection<org.openuss.registration.ActivationCode> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ActivationCode.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.registration.ActivationCode)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.registration.ActivationCodeDao#remove(org.openuss.registration.ActivationCode)
     */
    public void remove(org.openuss.registration.ActivationCode activationCode)
    {
        if (activationCode == null)
        {
            throw new IllegalArgumentException(
                "ActivationCode.remove - 'activationCode' can not be null");
        }
        this.getHibernateTemplate().delete(activationCode);
    }

    /**
     * @see org.openuss.registration.ActivationCodeDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "ActivationCode.remove - 'id can not be null");
        }
        org.openuss.registration.ActivationCode entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.registration.ActivationCodeDao#remove(java.util.Collection<org.openuss.registration.ActivationCode>)
     */
    public void remove(java.util.Collection<org.openuss.registration.ActivationCode> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ActivationCode.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.registration.ActivationCodeDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.registration.ActivationCodeDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.registration.ActivationCode entity)
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
     * {@link #transformEntity(int,org.openuss.registration.ActivationCode)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.registration.ActivationCodeDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.registration.ActivationCode)
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