package org.openuss.documents;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.documents.FileEntry</code>.
 * </p>
 *
 * @see org.openuss.documents.FileEntry
 */
public abstract class FileEntryDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.documents.FileEntryDao
{


    /**
     * @see org.openuss.documents.FileEntryDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "FileEntry.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.documents.FileEntryImpl.class, id);
        return transformEntity(transform, (org.openuss.documents.FileEntry)entity);
    }

    /**
     * @see org.openuss.documents.FileEntryDao#load(java.lang.Long)
     */
    public org.openuss.documents.FileEntry load(java.lang.Long id)
    {
        return (org.openuss.documents.FileEntry)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.documents.FileEntryDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.documents.FileEntry> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.documents.FileEntryDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.documents.FileEntryImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.documents.FileEntryDao#create(org.openuss.documents.FileEntry)
     */
    public org.openuss.documents.FileEntry create(org.openuss.documents.FileEntry fileEntry)
    {
        return (org.openuss.documents.FileEntry)this.create(TRANSFORM_NONE, fileEntry);
    }

    /**
     * @see org.openuss.documents.FileEntryDao#create(int transform, org.openuss.documents.FileEntry)
     */
    public java.lang.Object create(final int transform, final org.openuss.documents.FileEntry fileEntry)
    {
        if (fileEntry == null)
        {
            throw new IllegalArgumentException(
                "FileEntry.create - 'fileEntry' can not be null");
        }
        this.getHibernateTemplate().save(fileEntry);
        return this.transformEntity(transform, fileEntry);
    }

    /**
     * @see org.openuss.documents.FileEntryDao#create(java.util.Collection<org.openuss.documents.FileEntry>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.documents.FileEntry> create(final java.util.Collection<org.openuss.documents.FileEntry> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.documents.FileEntryDao#create(int, java.util.Collection<org.openuss.documents.FileEntry>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.documents.FileEntry> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "FileEntry.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.documents.FileEntry)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.documents.FileEntryDao#update(org.openuss.documents.FileEntry)
     */
    public void update(org.openuss.documents.FileEntry fileEntry)
    {
        if (fileEntry == null)
        {
            throw new IllegalArgumentException(
                "FileEntry.update - 'fileEntry' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(fileEntry);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(fileEntry);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.documents.FileEntryDao#update(java.util.Collection<org.openuss.documents.FileEntry>)
     */
    public void update(final java.util.Collection<org.openuss.documents.FileEntry> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "FileEntry.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.documents.FileEntry)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.documents.FileEntryDao#remove(org.openuss.documents.FileEntry)
     */
    public void remove(org.openuss.documents.FileEntry fileEntry)
    {
        if (fileEntry == null)
        {
            throw new IllegalArgumentException(
                "FileEntry.remove - 'fileEntry' can not be null");
        }
        this.getHibernateTemplate().delete(fileEntry);
    }

    /**
     * @see org.openuss.documents.FileEntryDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "FileEntry.remove - 'id can not be null");
        }
        org.openuss.documents.FileEntry entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.documents.FileEntryDao#remove(java.util.Collection<org.openuss.documents.FileEntry>)
     */
    public void remove(java.util.Collection<org.openuss.documents.FileEntry> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "FileEntry.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.documents.FileEntryDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.documents.FileEntry} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.documents.FileInfo} - {@link TRANSFORM_FILEINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.documents.FileEntryDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.documents.FileEntry entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_FILEINFO :
                    target = toFileInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.documents.FileEntry)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.documents.FileEntryDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.documents.FileEntry)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_FILEINFO :
                toFileInfoCollection(entities);
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
     * @see org.openuss.documents.FileEntryDao#toFileInfoCollection(java.util.Collection)
     */
    public final void toFileInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, FILEINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link FileEntryDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.documents.FileEntryDao#toFileInfo(org.openuss.documents.FileEntry)
     */
    protected org.openuss.documents.FileInfo toFileInfo(java.lang.Object[] row)
    {
        org.openuss.documents.FileInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.documents.FileEntry)
                {
                    target = this.toFileInfo((org.openuss.documents.FileEntry)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.documents.FileInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer FILEINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.documents.FileEntry)
                {
                    result = toFileInfo((org.openuss.documents.FileEntry)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toFileInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.documents.FileEntryDao#fileInfoToEntityCollection(java.util.Collection)
     */
    public final void fileInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.documents.FileInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, FileInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer FileInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return fileInfoToEntity((org.openuss.documents.FileInfo)input);
            }
        };

    /**
     * @see org.openuss.documents.FileEntryDao#toFileInfo(org.openuss.documents.FileEntry, org.openuss.documents.FileInfo)
     */
    public void toFileInfo(
        org.openuss.documents.FileEntry source,
        org.openuss.documents.FileInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setFileName(source.getFileName());
        target.setDescription(source.getDescription());
        target.setFileSize(source.getFileSize());
        target.setContentType(source.getContentType());
        target.setCreated(source.getCreated());
        target.setModified(source.getModified());
    }

    /**
     * @see org.openuss.documents.FileEntryDao#toFileInfo(org.openuss.documents.FileEntry)
     */
    public org.openuss.documents.FileInfo toFileInfo(final org.openuss.documents.FileEntry entity)
    {
        final org.openuss.documents.FileInfo target = new org.openuss.documents.FileInfo();
        this.toFileInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.documents.FileEntryDao#fileInfoToEntity(org.openuss.documents.FileInfo, org.openuss.documents.FileEntry)
     */
    public void fileInfoToEntity(
        org.openuss.documents.FileInfo source,
        org.openuss.documents.FileEntry target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getContentType() != null)
        {
            target.setContentType(source.getContentType());
        }
        if (copyIfNull || source.getFileName() != null)
        {
            target.setFileName(source.getFileName());
        }
        if (copyIfNull || source.getFileSize() != null)
        {
            target.setFileSize(source.getFileSize());
        }
        if (copyIfNull || source.getModified() != null)
        {
            target.setModified(source.getModified());
        }
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
     * @see org.openuss.documents.FileEntryDao#toFolderEntryInfoCollection(java.util.Collection)
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
     * {@link FileEntryDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.documents.FileEntryDao#toFolderEntryInfo(org.openuss.documents.FileEntry)
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
                if (object instanceof org.openuss.documents.FileEntry)
                {
                    target = this.toFolderEntryInfo((org.openuss.documents.FileEntry)object);
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
                if (input instanceof org.openuss.documents.FileEntry)
                {
                    result = toFolderEntryInfo((org.openuss.documents.FileEntry)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toFolderEntryInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.documents.FileEntryDao#folderEntryInfoToEntityCollection(java.util.Collection)
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
     * @see org.openuss.documents.FileEntryDao#toFolderEntryInfo(org.openuss.documents.FileEntry, org.openuss.documents.FolderEntryInfo)
     */
    public void toFolderEntryInfo(
        org.openuss.documents.FileEntry source,
        org.openuss.documents.FolderEntryInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setFileName(source.getFileName());
        target.setDescription(source.getDescription());
        target.setCreated(source.getCreated());
        target.setModified(source.getModified());
        target.setFileSize(source.getFileSize());
    }

    /**
     * @see org.openuss.documents.FileEntryDao#toFolderEntryInfo(org.openuss.documents.FileEntry)
     */
    public org.openuss.documents.FolderEntryInfo toFolderEntryInfo(final org.openuss.documents.FileEntry entity)
    {
        final org.openuss.documents.FolderEntryInfo target = new org.openuss.documents.FolderEntryInfo();
        this.toFolderEntryInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.documents.FileEntryDao#folderEntryInfoToEntity(org.openuss.documents.FolderEntryInfo, org.openuss.documents.FileEntry)
     */
    public void folderEntryInfoToEntity(
        org.openuss.documents.FolderEntryInfo source,
        org.openuss.documents.FileEntry target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getFileName() != null)
        {
            target.setFileName(source.getFileName());
        }
        if (copyIfNull || source.getFileSize() != null)
        {
            target.setFileSize(source.getFileSize());
        }
        if (copyIfNull || source.getModified() != null)
        {
            target.setModified(source.getModified());
        }
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