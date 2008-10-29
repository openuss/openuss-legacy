package org.openuss.collaboration;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.collaboration.Workspace</code>.
 * </p>
 *
 * @see org.openuss.collaboration.Workspace
 */
public abstract class WorkspaceDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.collaboration.WorkspaceDao
{


    /**
     * @see org.openuss.collaboration.WorkspaceDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Workspace.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.collaboration.WorkspaceImpl.class, id);
        return transformEntity(transform, (org.openuss.collaboration.Workspace)entity);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#load(java.lang.Long)
     */
    public org.openuss.collaboration.Workspace load(java.lang.Long id)
    {
        return (org.openuss.collaboration.Workspace)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.collaboration.Workspace> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.collaboration.WorkspaceImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.collaboration.WorkspaceDao#create(org.openuss.collaboration.Workspace)
     */
    public org.openuss.collaboration.Workspace create(org.openuss.collaboration.Workspace workspace)
    {
        return (org.openuss.collaboration.Workspace)this.create(TRANSFORM_NONE, workspace);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#create(int transform, org.openuss.collaboration.Workspace)
     */
    public java.lang.Object create(final int transform, final org.openuss.collaboration.Workspace workspace)
    {
        if (workspace == null)
        {
            throw new IllegalArgumentException(
                "Workspace.create - 'workspace' can not be null");
        }
        this.getHibernateTemplate().save(workspace);
        return this.transformEntity(transform, workspace);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#create(java.util.Collection<org.openuss.collaboration.Workspace>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.collaboration.Workspace> create(final java.util.Collection<org.openuss.collaboration.Workspace> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#create(int, java.util.Collection<org.openuss.collaboration.Workspace>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.collaboration.Workspace> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Workspace.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.collaboration.Workspace)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#update(org.openuss.collaboration.Workspace)
     */
    public void update(org.openuss.collaboration.Workspace workspace)
    {
        if (workspace == null)
        {
            throw new IllegalArgumentException(
                "Workspace.update - 'workspace' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(workspace);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(workspace);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#update(java.util.Collection<org.openuss.collaboration.Workspace>)
     */
    public void update(final java.util.Collection<org.openuss.collaboration.Workspace> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Workspace.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.collaboration.Workspace)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#remove(org.openuss.collaboration.Workspace)
     */
    public void remove(org.openuss.collaboration.Workspace workspace)
    {
        if (workspace == null)
        {
            throw new IllegalArgumentException(
                "Workspace.remove - 'workspace' can not be null");
        }
        this.getHibernateTemplate().delete(workspace);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Workspace.remove - 'id can not be null");
        }
        org.openuss.collaboration.Workspace entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#remove(java.util.Collection<org.openuss.collaboration.Workspace>)
     */
    public void remove(java.util.Collection<org.openuss.collaboration.Workspace> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Workspace.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.collaboration.WorkspaceDao#findByDomainId(java.lang.Long)
     */
    public java.util.List findByDomainId(java.lang.Long domainId)
    {
        return this.findByDomainId(TRANSFORM_NONE, domainId);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#findByDomainId(java.lang.String, java.lang.Long)
     */
    public java.util.List findByDomainId(final java.lang.String queryString, final java.lang.Long domainId)
    {
        return this.findByDomainId(TRANSFORM_NONE, queryString, domainId);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#findByDomainId(int, java.lang.Long)
     */
    public java.util.List findByDomainId(final int transform, final java.lang.Long domainId)
    {
        return this.findByDomainId(transform, "from org.openuss.collaboration.Workspace as workspace where workspace.domainId = ?", domainId);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#findByDomainId(int, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByDomainId(final int transform, final java.lang.String queryString, final java.lang.Long domainId)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, domainId);
            java.util.List results = queryObject.list();
            transformEntities(transform, results);
            return results;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#findByDomainIdAndUser(java.lang.Long, org.openuss.security.User)
     */
    public java.util.List findByDomainIdAndUser(java.lang.Long domainId, org.openuss.security.User user)
    {
        return this.findByDomainIdAndUser(TRANSFORM_NONE, domainId, user);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#findByDomainIdAndUser(java.lang.String, java.lang.Long, org.openuss.security.User)
     */
    public java.util.List findByDomainIdAndUser(final java.lang.String queryString, final java.lang.Long domainId, final org.openuss.security.User user)
    {
        return this.findByDomainIdAndUser(TRANSFORM_NONE, queryString, domainId, user);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#findByDomainIdAndUser(int, java.lang.Long, org.openuss.security.User)
     */
    public java.util.List findByDomainIdAndUser(final int transform, final java.lang.Long domainId, final org.openuss.security.User user)
    {
        return this.findByDomainIdAndUser(transform, "from org.openuss.collaboration.Workspace as workspace where workspace.domainId = ? and workspace.user = ?", domainId, user);
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#findByDomainIdAndUser(int, java.lang.String, java.lang.Long, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByDomainIdAndUser(final int transform, final java.lang.String queryString, final java.lang.Long domainId, final org.openuss.security.User user)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, domainId);
            queryObject.setParameter(1, user);
            java.util.List results = queryObject.list();
            transformEntities(transform, results);
            return results;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#findByUser(org.openuss.security.User)
     */
    public java.util.List findByUser(final org.openuss.security.User user)
    {
        try
        {
            return this.handleFindByUser(user);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.collaboration.WorkspaceDao.findByUser(org.openuss.security.User user)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #findByUser(org.openuss.security.User)}
      */
    protected abstract java.util.List handleFindByUser(org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.collaboration.WorkspaceDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.collaboration.Workspace} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.collaboration.WorkspaceInfo} - {@link TRANSFORM_WORKSPACEINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.collaboration.WorkspaceDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.collaboration.Workspace entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_WORKSPACEINFO :
                    target = toWorkspaceInfo(entity);
                    break;
                case TRANSFORM_NONE : // fall-through
                default:
                    target = entity;
            }
        }
        return target;
    }

    /**
     * Transforms a collection of entities using the
     * {@link #transformEntity(int,org.openuss.collaboration.Workspace)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.collaboration.WorkspaceDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.collaboration.Workspace)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_WORKSPACEINFO :
                toWorkspaceInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#toWorkspaceInfoCollection(java.util.Collection)
     */
    public final void toWorkspaceInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, WORKSPACEINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link WorkspaceDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.collaboration.WorkspaceDao#toWorkspaceInfo(org.openuss.collaboration.Workspace)
     */
    protected org.openuss.collaboration.WorkspaceInfo toWorkspaceInfo(java.lang.Object[] row)
    {
        org.openuss.collaboration.WorkspaceInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.collaboration.Workspace)
                {
                    target = this.toWorkspaceInfo((org.openuss.collaboration.Workspace)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.collaboration.WorkspaceInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer WORKSPACEINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.collaboration.Workspace)
                {
                    result = toWorkspaceInfo((org.openuss.collaboration.Workspace)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toWorkspaceInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.collaboration.WorkspaceDao#workspaceInfoToEntityCollection(java.util.Collection)
     */
    public final void workspaceInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.collaboration.WorkspaceInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, WorkspaceInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer WorkspaceInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return workspaceInfoToEntity((org.openuss.collaboration.WorkspaceInfo)input);
            }
        };

    /**
     * @see org.openuss.collaboration.WorkspaceDao#toWorkspaceInfo(org.openuss.collaboration.Workspace, org.openuss.collaboration.WorkspaceInfo)
     */
    public void toWorkspaceInfo(
        org.openuss.collaboration.Workspace source,
        org.openuss.collaboration.WorkspaceInfo target)
    {
        target.setId(source.getId());
        target.setDomainId(source.getDomainId());
        target.setName(source.getName());
    }

    /**
     * @see org.openuss.collaboration.WorkspaceDao#toWorkspaceInfo(org.openuss.collaboration.Workspace)
     */
    public org.openuss.collaboration.WorkspaceInfo toWorkspaceInfo(final org.openuss.collaboration.Workspace entity)
    {
        final org.openuss.collaboration.WorkspaceInfo target = new org.openuss.collaboration.WorkspaceInfo();
        this.toWorkspaceInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.collaboration.WorkspaceDao#workspaceInfoToEntity(org.openuss.collaboration.WorkspaceInfo, org.openuss.collaboration.Workspace)
     */
    public void workspaceInfoToEntity(
        org.openuss.collaboration.WorkspaceInfo source,
        org.openuss.collaboration.Workspace target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getDomainId() != null)
        {
            target.setDomainId(source.getDomainId());
        }
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
    }
    
}