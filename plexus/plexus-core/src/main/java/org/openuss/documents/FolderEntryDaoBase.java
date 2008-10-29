package org.openuss.documents;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.documents.FolderEntry</code>.
 * </p>
 *
 * @see org.openuss.documents.FolderEntry
 */
public abstract class FolderEntryDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.documents.FolderEntryDao
{


    /**
     * @see org.openuss.documents.FolderEntryDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "FolderEntry.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.documents.FolderEntryImpl.class, id);
        return transformEntity(transform, (org.openuss.documents.FolderEntry)entity);
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#load(java.lang.Long)
     */
    public org.openuss.documents.FolderEntry load(java.lang.Long id)
    {
        return (org.openuss.documents.FolderEntry)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.documents.FolderEntry> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.documents.FolderEntryImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.documents.FolderEntryDao#create(org.openuss.documents.FolderEntry)
     */
    public org.openuss.documents.FolderEntry create(org.openuss.documents.FolderEntry folderEntry)
    {
        return (org.openuss.documents.FolderEntry)this.create(TRANSFORM_NONE, folderEntry);
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#create(int transform, org.openuss.documents.FolderEntry)
     */
    public java.lang.Object create(final int transform, final org.openuss.documents.FolderEntry folderEntry)
    {
        if (folderEntry == null)
        {
            throw new IllegalArgumentException(
                "FolderEntry.create - 'folderEntry' can not be null");
        }
        this.getHibernateTemplate().save(folderEntry);
        return this.transformEntity(transform, folderEntry);
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#create(java.util.Collection<org.openuss.documents.FolderEntry>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.documents.FolderEntry> create(final java.util.Collection<org.openuss.documents.FolderEntry> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#create(int, java.util.Collection<org.openuss.documents.FolderEntry>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.documents.FolderEntry> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "FolderEntry.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.documents.FolderEntry)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#update(org.openuss.documents.FolderEntry)
     */
    public void update(org.openuss.documents.FolderEntry folderEntry)
    {
        if (folderEntry == null)
        {
            throw new IllegalArgumentException(
                "FolderEntry.update - 'folderEntry' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(folderEntry);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(folderEntry);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#update(java.util.Collection<org.openuss.documents.FolderEntry>)
     */
    public void update(final java.util.Collection<org.openuss.documents.FolderEntry> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "FolderEntry.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.documents.FolderEntry)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#remove(org.openuss.documents.FolderEntry)
     */
    public void remove(org.openuss.documents.FolderEntry folderEntry)
    {
        if (folderEntry == null)
        {
            throw new IllegalArgumentException(
                "FolderEntry.remove - 'folderEntry' can not be null");
        }
        this.getHibernateTemplate().delete(folderEntry);
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "FolderEntry.remove - 'id can not be null");
        }
        org.openuss.documents.FolderEntry entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#remove(java.util.Collection<org.openuss.documents.FolderEntry>)
     */
    public void remove(java.util.Collection<org.openuss.documents.FolderEntry> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "FolderEntry.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.documents.FolderEntryDao#findByParent(org.openuss.documents.Folder)
     */
    public java.util.List findByParent(org.openuss.documents.Folder parent)
    {
        return this.findByParent(TRANSFORM_NONE, parent);
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#findByParent(java.lang.String, org.openuss.documents.Folder)
     */
    public java.util.List findByParent(final java.lang.String queryString, final org.openuss.documents.Folder parent)
    {
        return this.findByParent(TRANSFORM_NONE, queryString, parent);
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#findByParent(int, org.openuss.documents.Folder)
     */
    public java.util.List findByParent(final int transform, final org.openuss.documents.Folder parent)
    {
        return this.findByParent(transform, "from org.openuss.documents.FolderEntry as folderEntry where folderEntry.parent = ?", parent);
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#findByParent(int, java.lang.String, org.openuss.documents.Folder)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByParent(final int transform, final java.lang.String queryString, final org.openuss.documents.Folder parent)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, parent);
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
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.documents.FolderEntryDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.documents.FolderEntry} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.documents.FolderEntryInfo} - {@link TRANSFORM_FOLDERENTRYINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.documents.FolderEntryDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.documents.FolderEntry entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
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
     * {@link #transformEntity(int,org.openuss.documents.FolderEntry)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.documents.FolderEntryDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.documents.FolderEntry)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_FOLDERENTRYINFO :
                toFolderEntryInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#toFolderEntryInfoCollection(java.util.Collection)
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
     * {@link FolderEntryDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.documents.FolderEntryDao#toFolderEntryInfo(org.openuss.documents.FolderEntry)
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
                if (object instanceof org.openuss.documents.FolderEntry)
                {
                    target = this.toFolderEntryInfo((org.openuss.documents.FolderEntry)object);
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
                if (input instanceof org.openuss.documents.FolderEntry)
                {
                    result = toFolderEntryInfo((org.openuss.documents.FolderEntry)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toFolderEntryInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.documents.FolderEntryDao#folderEntryInfoToEntityCollection(java.util.Collection)
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
     * @see org.openuss.documents.FolderEntryDao#toFolderEntryInfo(org.openuss.documents.FolderEntry, org.openuss.documents.FolderEntryInfo)
     */
    public void toFolderEntryInfo(
        org.openuss.documents.FolderEntry source,
        org.openuss.documents.FolderEntryInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setCreated(source.getCreated());
    }

    /**
     * @see org.openuss.documents.FolderEntryDao#toFolderEntryInfo(org.openuss.documents.FolderEntry)
     */
    public org.openuss.documents.FolderEntryInfo toFolderEntryInfo(final org.openuss.documents.FolderEntry entity)
    {
        final org.openuss.documents.FolderEntryInfo target = new org.openuss.documents.FolderEntryInfo();
        this.toFolderEntryInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.documents.FolderEntryDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo, org.openuss.documents.FolderEntry)
     */
    public void folderEntryInfoToEntity(
        org.openuss.documents.FolderEntryInfo source,
        org.openuss.documents.FolderEntry target,
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