package org.openuss.messaging;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.messaging.TemplateParameter</code>.
 * </p>
 *
 * @see org.openuss.messaging.TemplateParameter
 */
public abstract class TemplateParameterDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.messaging.TemplateParameterDao
{


    /**
     * @see org.openuss.messaging.TemplateParameterDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "TemplateParameter.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.messaging.TemplateParameterImpl.class, id);
        return transformEntity(transform, (org.openuss.messaging.TemplateParameter)entity);
    }

    /**
     * @see org.openuss.messaging.TemplateParameterDao#load(java.lang.Long)
     */
    public org.openuss.messaging.TemplateParameter load(java.lang.Long id)
    {
        return (org.openuss.messaging.TemplateParameter)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.messaging.TemplateParameterDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.messaging.TemplateParameter> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.messaging.TemplateParameterDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.messaging.TemplateParameterImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.messaging.TemplateParameterDao#create(org.openuss.messaging.TemplateParameter)
     */
    public org.openuss.messaging.TemplateParameter create(org.openuss.messaging.TemplateParameter templateParameter)
    {
        return (org.openuss.messaging.TemplateParameter)this.create(TRANSFORM_NONE, templateParameter);
    }

    /**
     * @see org.openuss.messaging.TemplateParameterDao#create(int transform, org.openuss.messaging.TemplateParameter)
     */
    public java.lang.Object create(final int transform, final org.openuss.messaging.TemplateParameter templateParameter)
    {
        if (templateParameter == null)
        {
            throw new IllegalArgumentException(
                "TemplateParameter.create - 'templateParameter' can not be null");
        }
        this.getHibernateTemplate().save(templateParameter);
        return this.transformEntity(transform, templateParameter);
    }

    /**
     * @see org.openuss.messaging.TemplateParameterDao#create(java.util.Collection<org.openuss.messaging.TemplateParameter>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.messaging.TemplateParameter> create(final java.util.Collection<org.openuss.messaging.TemplateParameter> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.messaging.TemplateParameterDao#create(int, java.util.Collection<org.openuss.messaging.TemplateParameter>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.messaging.TemplateParameter> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "TemplateParameter.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.messaging.TemplateParameter)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.messaging.TemplateParameterDao#update(org.openuss.messaging.TemplateParameter)
     */
    public void update(org.openuss.messaging.TemplateParameter templateParameter)
    {
        if (templateParameter == null)
        {
            throw new IllegalArgumentException(
                "TemplateParameter.update - 'templateParameter' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(templateParameter);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(templateParameter);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.messaging.TemplateParameterDao#update(java.util.Collection<org.openuss.messaging.TemplateParameter>)
     */
    public void update(final java.util.Collection<org.openuss.messaging.TemplateParameter> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "TemplateParameter.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.messaging.TemplateParameter)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.messaging.TemplateParameterDao#remove(org.openuss.messaging.TemplateParameter)
     */
    public void remove(org.openuss.messaging.TemplateParameter templateParameter)
    {
        if (templateParameter == null)
        {
            throw new IllegalArgumentException(
                "TemplateParameter.remove - 'templateParameter' can not be null");
        }
        this.getHibernateTemplate().delete(templateParameter);
    }

    /**
     * @see org.openuss.messaging.TemplateParameterDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "TemplateParameter.remove - 'id can not be null");
        }
        org.openuss.messaging.TemplateParameter entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.messaging.TemplateParameterDao#remove(java.util.Collection<org.openuss.messaging.TemplateParameter>)
     */
    public void remove(java.util.Collection<org.openuss.messaging.TemplateParameter> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "TemplateParameter.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.messaging.TemplateParameterDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.messaging.TemplateParameterDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.messaging.TemplateParameter entity)
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
     * {@link #transformEntity(int,org.openuss.messaging.TemplateParameter)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.messaging.TemplateParameterDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.messaging.TemplateParameter)
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