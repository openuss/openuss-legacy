package org.openuss.documents;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.documents.Folder</code>.
 * </p>
 *
 * @see org.openuss.documents.Folder
 */
public abstract class FolderDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.documents.FolderDao
{


    /**
     * @see org.openuss.documents.FolderDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Folder.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.documents.FolderImpl.class, id);
        return transformEntity(transform, (org.openuss.documents.Folder)entity);
    }

    /**
     * @see org.openuss.documents.FolderDao#load(java.lang.Long)
     */
    public org.openuss.documents.Folder load(java.lang.Long id)
    {
        return (org.openuss.documents.Folder)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.documents.FolderDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.documents.Folder> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.documents.FolderDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.documents.FolderImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.documents.FolderDao#create(org.openuss.documents.Folder)
     */
    public org.openuss.documents.Folder create(org.openuss.documents.Folder folder)
    {
        return (org.openuss.documents.Folder)this.create(TRANSFORM_NONE, folder);
    }

    /**
     * @see org.openuss.documents.FolderDao#create(int transform, org.openuss.documents.Folder)
     */
    public java.lang.Object create(final int transform, final org.openuss.documents.Folder folder)
    {
        if (folder == null)
        {
            throw new IllegalArgumentException(
                "Folder.create - 'folder' can not be null");
        }
        this.getHibernateTemplate().save(folder);
        return this.transformEntity(transform, folder);
    }

    /**
     * @see org.openuss.documents.FolderDao#create(java.util.Collection<org.openuss.documents.Folder>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.documents.Folder> create(final java.util.Collection<org.openuss.documents.Folder> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.documents.FolderDao#create(int, java.util.Collection<org.openuss.documents.Folder>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.documents.Folder> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Folder.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.documents.Folder)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.documents.FolderDao#update(org.openuss.documents.Folder)
     */
    public void update(org.openuss.documents.Folder folder)
    {
        if (folder == null)
        {
            throw new IllegalArgumentException(
                "Folder.update - 'folder' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(folder);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(folder);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.documents.FolderDao#update(java.util.Collection<org.openuss.documents.Folder>)
     */
    public void update(final java.util.Collection<org.openuss.documents.Folder> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Folder.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.documents.Folder)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.documents.FolderDao#remove(org.openuss.documents.Folder)
     */
    public void remove(org.openuss.documents.Folder folder)
    {
        if (folder == null)
        {
            throw new IllegalArgumentException(
                "Folder.remove - 'folder' can not be null");
        }
        this.getHibernateTemplate().delete(folder);
    }

    /**
     * @see org.openuss.documents.FolderDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Folder.remove - 'id can not be null");
        }
        org.openuss.documents.Folder entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.documents.FolderDao#remove(java.util.Collection<org.openuss.documents.Folder>)
     */
    public void remove(java.util.Collection<org.openuss.documents.Folder> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Folder.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.documents.FolderDao#findByDomainIdentifier(java.lang.Long)
     */
    public org.openuss.documents.Folder findByDomainIdentifier(java.lang.Long domainIdentifier)
    {
        return (org.openuss.documents.Folder)this.findByDomainIdentifier(TRANSFORM_NONE, domainIdentifier);
    }

    /**
     * @see org.openuss.documents.FolderDao#findByDomainIdentifier(java.lang.String, java.lang.Long)
     */
    public org.openuss.documents.Folder findByDomainIdentifier(final java.lang.String queryString, final java.lang.Long domainIdentifier)
    {
        return (org.openuss.documents.Folder)this.findByDomainIdentifier(TRANSFORM_NONE, queryString, domainIdentifier);
    }

    /**
     * @see org.openuss.documents.FolderDao#findByDomainIdentifier(int, java.lang.Long)
     */
    public java.lang.Object findByDomainIdentifier(final int transform, final java.lang.Long domainIdentifier)
    {
        return this.findByDomainIdentifier(transform, "from org.openuss.documents.Folder as folder where folder.domainIdentifier = ?", domainIdentifier);
    }

    /**
     * @see org.openuss.documents.FolderDao#findByDomainIdentifier(int, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByDomainIdentifier(final int transform, final java.lang.String queryString, final java.lang.Long domainIdentifier)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, domainIdentifier);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.documents.Folder"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.documents.Folder)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.documents.FolderDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.documents.Folder} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.documents.FolderInfo} - {@link TRANSFORM_FOLDERINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.documents.FolderDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.documents.Folder entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_FOLDERINFO :
                    target = toFolderInfo(entity);
                    break;
                case TRANSFORM_FOLDERENTRYINFO :
                    target = toFolderEntryInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.documents.Folder)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.documents.FolderDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.documents.Folder)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_FOLDERINFO :
                toFolderInfoCollection(entities);
                break;
            case TRANSFORM_FOLDERENTRYINFO :
                toFolderEntryInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.documents.FolderDao#toFolderInfoCollection(java.util.Collection)
     */
    public final void toFolderInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, FOLDERINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link FolderDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.documents.FolderDao#toFolderInfo(org.openuss.documents.Folder)
     */
    protected org.openuss.documents.FolderInfo toFolderInfo(java.lang.Object[] row)
    {
        org.openuss.documents.FolderInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.documents.Folder)
                {
                    target = this.toFolderInfo((org.openuss.documents.Folder)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.documents.FolderInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer FOLDERINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.documents.Folder)
                {
                    result = toFolderInfo((org.openuss.documents.Folder)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toFolderInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.documents.FolderDao#folderInfoToEntityCollection(java.util.Collection)
     */
    public final void folderInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.documents.FolderInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, FolderInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer FolderInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return folderInfoToEntity((org.openuss.documents.FolderInfo)input);
            }
        };

    /**
     * @see org.openuss.documents.FolderDao#toFolderInfo(org.openuss.documents.Folder, org.openuss.documents.FolderInfo)
     */
    public void toFolderInfo(
        org.openuss.documents.Folder source,
        org.openuss.documents.FolderInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setCreated(source.getCreated());
    }

    /**
     * @see org.openuss.documents.FolderDao#toFolderInfo(org.openuss.documents.Folder)
     */
    public org.openuss.documents.FolderInfo toFolderInfo(final org.openuss.documents.Folder entity)
    {
        final org.openuss.documents.FolderInfo target = new org.openuss.documents.FolderInfo();
        this.toFolderInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.documents.FolderDao#folderInfoToEntity(org.openuss.documents.FolderInfo, org.openuss.documents.Folder)
     */
    public void folderInfoToEntity(
        org.openuss.documents.FolderInfo source,
        org.openuss.documents.Folder target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
        if (copyIfNull || source.getCreated() != null)
        {
            target.setCreated(source.getCreated());
        }
        if (copyIfNull || source.getDescription() != null)
        {
            target.setDescription(source.getDescription());
        }
    }
    
    /**
     * @see org.openuss.documents.FolderDao#toFolderEntryInfoCollection(java.util.Collection)
     */
    public final void toFolderEntryInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, FOLDERENTRYINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link FolderDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.documents.FolderDao#toFolderEntryInfo(org.openuss.documents.Folder)
     */
    protected org.openuss.documents.FolderEntryInfo toFolderEntryInfo(java.lang.Object[] row)
    {
        org.openuss.documents.FolderEntryInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.documents.Folder)
                {
                    target = this.toFolderEntryInfo((org.openuss.documents.Folder)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.documents.FolderEntryInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer FOLDERENTRYINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.documents.Folder)
                {
                    result = toFolderEntryInfo((org.openuss.documents.Folder)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toFolderEntryInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.documents.FolderDao#folderEntryInfoToEntityCollection(java.util.Collection)
     */
    public final void folderEntryInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.documents.FolderEntryInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, FolderEntryInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer FolderEntryInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return folderEntryInfoToEntity((org.openuss.documents.FolderEntryInfo)input);
            }
        };

    /**
     * @see org.openuss.documents.FolderDao#toFolderEntryInfo(org.openuss.documents.Folder, org.openuss.documents.FolderEntryInfo)
     */
    public void toFolderEntryInfo(
        org.openuss.documents.Folder source,
        org.openuss.documents.FolderEntryInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setCreated(source.getCreated());
    }

    /**
     * @see org.openuss.documents.FolderDao#toFolderEntryInfo(org.openuss.documents.Folder)
     */
    public org.openuss.documents.FolderEntryInfo toFolderEntryInfo(final org.openuss.documents.Folder entity)
    {
        final org.openuss.documents.FolderEntryInfo target = new org.openuss.documents.FolderEntryInfo();
        this.toFolderEntryInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.documents.FolderDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo, org.openuss.documents.Folder)
     */
    public void folderEntryInfoToEntity(
        org.openuss.documents.FolderEntryInfo source,
        org.openuss.documents.Folder target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
        if (copyIfNull || source.getCreated() != null)
        {
            target.setCreated(source.getCreated());
        }
        if (copyIfNull || source.getDescription() != null)
        {
            target.setDescription(source.getDescription());
        }
    }
    
}