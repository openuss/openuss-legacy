package org.openuss.discussion;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.discussion.Formula</code>.
 * </p>
 *
 * @see org.openuss.discussion.Formula
 */
public abstract class FormulaDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.discussion.FormulaDao
{


    /**
     * @see org.openuss.discussion.FormulaDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Formula.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.discussion.FormulaImpl.class, id);
        return transformEntity(transform, (org.openuss.discussion.Formula)entity);
    }

    /**
     * @see org.openuss.discussion.FormulaDao#load(java.lang.Long)
     */
    public org.openuss.discussion.Formula load(java.lang.Long id)
    {
        return (org.openuss.discussion.Formula)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.discussion.FormulaDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.Formula> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.discussion.FormulaDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.discussion.FormulaImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.discussion.FormulaDao#create(org.openuss.discussion.Formula)
     */
    public org.openuss.discussion.Formula create(org.openuss.discussion.Formula formula)
    {
        return (org.openuss.discussion.Formula)this.create(TRANSFORM_NONE, formula);
    }

    /**
     * @see org.openuss.discussion.FormulaDao#create(int transform, org.openuss.discussion.Formula)
     */
    public java.lang.Object create(final int transform, final org.openuss.discussion.Formula formula)
    {
        if (formula == null)
        {
            throw new IllegalArgumentException(
                "Formula.create - 'formula' can not be null");
        }
        this.getHibernateTemplate().save(formula);
        return this.transformEntity(transform, formula);
    }

    /**
     * @see org.openuss.discussion.FormulaDao#create(java.util.Collection<org.openuss.discussion.Formula>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.discussion.Formula> create(final java.util.Collection<org.openuss.discussion.Formula> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.discussion.FormulaDao#create(int, java.util.Collection<org.openuss.discussion.Formula>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.discussion.Formula> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Formula.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.discussion.Formula)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.discussion.FormulaDao#update(org.openuss.discussion.Formula)
     */
    public void update(org.openuss.discussion.Formula formula)
    {
        if (formula == null)
        {
            throw new IllegalArgumentException(
                "Formula.update - 'formula' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(formula);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(formula);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.discussion.FormulaDao#update(java.util.Collection<org.openuss.discussion.Formula>)
     */
    public void update(final java.util.Collection<org.openuss.discussion.Formula> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Formula.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.discussion.Formula)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.discussion.FormulaDao#remove(org.openuss.discussion.Formula)
     */
    public void remove(org.openuss.discussion.Formula formula)
    {
        if (formula == null)
        {
            throw new IllegalArgumentException(
                "Formula.remove - 'formula' can not be null");
        }
        this.getHibernateTemplate().delete(formula);
    }

    /**
     * @see org.openuss.discussion.FormulaDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Formula.remove - 'id can not be null");
        }
        org.openuss.discussion.Formula entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.discussion.FormulaDao#remove(java.util.Collection<org.openuss.discussion.Formula>)
     */
    public void remove(java.util.Collection<org.openuss.discussion.Formula> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Formula.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.discussion.FormulaDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.discussion.FormulaDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.discussion.Formula entity)
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
     * {@link #transformEntity(int,org.openuss.discussion.Formula)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.discussion.FormulaDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.discussion.Formula)
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