package org.openuss.repository;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.repository.RepositoryFile</code>.
 * </p>
 *
 * @see org.openuss.repository.RepositoryFile
 */
public abstract class RepositoryFileDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.repository.RepositoryFileDao
{


    /**
     * @see org.openuss.repository.RepositoryFileDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "RepositoryFile.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.repository.RepositoryFileImpl.class, id);
        return transformEntity(transform, (org.openuss.repository.RepositoryFile)entity);
    }

    /**
     * @see org.openuss.repository.RepositoryFileDao#load(java.lang.Long)
     */
    public org.openuss.repository.RepositoryFile load(java.lang.Long id)
    {
        return (org.openuss.repository.RepositoryFile)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.repository.RepositoryFileDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.repository.RepositoryFile> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.repository.RepositoryFileDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.repository.RepositoryFileImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.repository.RepositoryFileDao#create(org.openuss.repository.RepositoryFile)
     */
    public org.openuss.repository.RepositoryFile create(org.openuss.repository.RepositoryFile repositoryFile)
    {
        return (org.openuss.repository.RepositoryFile)this.create(TRANSFORM_NONE, repositoryFile);
    }

    /**
     * @see org.openuss.repository.RepositoryFileDao#create(int transform, org.openuss.repository.RepositoryFile)
     */
    public java.lang.Object create(final int transform, final org.openuss.repository.RepositoryFile repositoryFile)
    {
        if (repositoryFile == null)
        {
            throw new IllegalArgumentException(
                "RepositoryFile.create - 'repositoryFile' can not be null");
        }
        this.getHibernateTemplate().save(repositoryFile);
        return this.transformEntity(transform, repositoryFile);
    }

    /**
     * @see org.openuss.repository.RepositoryFileDao#create(java.util.Collection<org.openuss.repository.RepositoryFile>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.repository.RepositoryFile> create(final java.util.Collection<org.openuss.repository.RepositoryFile> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.repository.RepositoryFileDao#create(int, java.util.Collection<org.openuss.repository.RepositoryFile>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.repository.RepositoryFile> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "RepositoryFile.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.repository.RepositoryFile)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.repository.RepositoryFileDao#update(org.openuss.repository.RepositoryFile)
     */
    public void update(org.openuss.repository.RepositoryFile repositoryFile)
    {
        if (repositoryFile == null)
        {
            throw new IllegalArgumentException(
                "RepositoryFile.update - 'repositoryFile' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(repositoryFile);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(repositoryFile);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.repository.RepositoryFileDao#update(java.util.Collection<org.openuss.repository.RepositoryFile>)
     */
    public void update(final java.util.Collection<org.openuss.repository.RepositoryFile> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "RepositoryFile.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.repository.RepositoryFile)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.repository.RepositoryFileDao#remove(org.openuss.repository.RepositoryFile)
     */
    public void remove(org.openuss.repository.RepositoryFile repositoryFile)
    {
        if (repositoryFile == null)
        {
            throw new IllegalArgumentException(
                "RepositoryFile.remove - 'repositoryFile' can not be null");
        }
        this.getHibernateTemplate().delete(repositoryFile);
    }

    /**
     * @see org.openuss.repository.RepositoryFileDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "RepositoryFile.remove - 'id can not be null");
        }
        org.openuss.repository.RepositoryFile entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.repository.RepositoryFileDao#remove(java.util.Collection<org.openuss.repository.RepositoryFile>)
     */
    public void remove(java.util.Collection<org.openuss.repository.RepositoryFile> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "RepositoryFile.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.repository.RepositoryFileDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.repository.RepositoryFileDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.repository.RepositoryFile entity)
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
     * {@link #transformEntity(int,org.openuss.repository.RepositoryFile)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.repository.RepositoryFileDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.repository.RepositoryFile)
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