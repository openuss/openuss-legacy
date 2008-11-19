package org.openuss.security.ldap;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.security.ldap.AttributeMapping</code>.
 * </p>
 *
 * @see org.openuss.security.ldap.AttributeMapping
 */
public abstract class AttributeMappingDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.security.ldap.AttributeMappingDao
{


    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "AttributeMapping.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.security.ldap.AttributeMappingImpl.class, id);
        return transformEntity(transform, (org.openuss.security.ldap.AttributeMapping)entity);
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#load(java.lang.Long)
     */
    public org.openuss.security.ldap.AttributeMapping load(java.lang.Long id)
    {
        return (org.openuss.security.ldap.AttributeMapping)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.ldap.AttributeMapping> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.security.ldap.AttributeMappingImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#create(org.openuss.security.ldap.AttributeMapping)
     */
    public org.openuss.security.ldap.AttributeMapping create(org.openuss.security.ldap.AttributeMapping attributeMapping)
    {
        return (org.openuss.security.ldap.AttributeMapping)this.create(TRANSFORM_NONE, attributeMapping);
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#create(int transform, org.openuss.security.ldap.AttributeMapping)
     */
    public java.lang.Object create(final int transform, final org.openuss.security.ldap.AttributeMapping attributeMapping)
    {
        if (attributeMapping == null)
        {
            throw new IllegalArgumentException(
                "AttributeMapping.create - 'attributeMapping' can not be null");
        }
        this.getHibernateTemplate().save(attributeMapping);
        return this.transformEntity(transform, attributeMapping);
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#create(java.util.Collection<org.openuss.security.ldap.AttributeMapping>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.ldap.AttributeMapping> create(final java.util.Collection<org.openuss.security.ldap.AttributeMapping> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#create(int, java.util.Collection<org.openuss.security.ldap.AttributeMapping>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.security.ldap.AttributeMapping> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "AttributeMapping.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.security.ldap.AttributeMapping)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#update(org.openuss.security.ldap.AttributeMapping)
     */
    public void update(org.openuss.security.ldap.AttributeMapping attributeMapping)
    {
        if (attributeMapping == null)
        {
            throw new IllegalArgumentException(
                "AttributeMapping.update - 'attributeMapping' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(attributeMapping);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(attributeMapping);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#update(java.util.Collection<org.openuss.security.ldap.AttributeMapping>)
     */
    public void update(final java.util.Collection<org.openuss.security.ldap.AttributeMapping> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "AttributeMapping.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.security.ldap.AttributeMapping)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#remove(org.openuss.security.ldap.AttributeMapping)
     */
    public void remove(org.openuss.security.ldap.AttributeMapping attributeMapping)
    {
        if (attributeMapping == null)
        {
            throw new IllegalArgumentException(
                "AttributeMapping.remove - 'attributeMapping' can not be null");
        }
        this.getHibernateTemplate().delete(attributeMapping);
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "AttributeMapping.remove - 'id can not be null");
        }
        org.openuss.security.ldap.AttributeMapping entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#remove(java.util.Collection<org.openuss.security.ldap.AttributeMapping>)
     */
    public void remove(java.util.Collection<org.openuss.security.ldap.AttributeMapping> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "AttributeMapping.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#findByName(java.lang.String)
     */
    public org.openuss.security.ldap.AttributeMapping findByName(java.lang.String name)
    {
        return (org.openuss.security.ldap.AttributeMapping)this.findByName(TRANSFORM_NONE, name);
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#findByName(java.lang.String, java.lang.String)
     */
    public org.openuss.security.ldap.AttributeMapping findByName(final java.lang.String queryString, final java.lang.String name)
    {
        return (org.openuss.security.ldap.AttributeMapping)this.findByName(TRANSFORM_NONE, queryString, name);
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#findByName(int, java.lang.String)
     */
    public java.lang.Object findByName(final int transform, final java.lang.String name)
    {
        return this.findByName(transform, "from org.openuss.security.ldap.AttributeMapping as mapping where lower(mapping.mappingName) = lower(?)", name);
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#findByName(int, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByName(final int transform, final java.lang.String queryString, final java.lang.String name)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, name);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.security.ldap.AttributeMapping"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.security.ldap.AttributeMapping)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.security.ldap.AttributeMappingDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.security.ldap.AttributeMapping} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.security.ldap.AttributeMappingInfo} - {@link TRANSFORM_ATTRIBUTEMAPPINGINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.security.ldap.AttributeMappingDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.security.ldap.AttributeMapping entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_ATTRIBUTEMAPPINGINFO :
                    target = toAttributeMappingInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.security.ldap.AttributeMapping)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.security.ldap.AttributeMappingDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.security.ldap.AttributeMapping)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_ATTRIBUTEMAPPINGINFO :
                toAttributeMappingInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#toAttributeMappingInfoCollection(java.util.Collection)
     */
    public final void toAttributeMappingInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, ATTRIBUTEMAPPINGINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link AttributeMappingDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.security.ldap.AttributeMappingDao#toAttributeMappingInfo(org.openuss.security.ldap.AttributeMapping)
     */
    protected org.openuss.security.ldap.AttributeMappingInfo toAttributeMappingInfo(java.lang.Object[] row)
    {
        org.openuss.security.ldap.AttributeMappingInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.security.ldap.AttributeMapping)
                {
                    target = this.toAttributeMappingInfo((org.openuss.security.ldap.AttributeMapping)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.security.ldap.AttributeMappingInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer ATTRIBUTEMAPPINGINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.security.ldap.AttributeMapping)
                {
                    result = toAttributeMappingInfo((org.openuss.security.ldap.AttributeMapping)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toAttributeMappingInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#attributeMappingInfoToEntityCollection(java.util.Collection)
     */
    public final void attributeMappingInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.security.ldap.AttributeMappingInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, AttributeMappingInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer AttributeMappingInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return attributeMappingInfoToEntity((org.openuss.security.ldap.AttributeMappingInfo)input);
            }
        };

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#toAttributeMappingInfo(org.openuss.security.ldap.AttributeMapping, org.openuss.security.ldap.AttributeMappingInfo)
     */
    public void toAttributeMappingInfo(
        org.openuss.security.ldap.AttributeMapping source,
        org.openuss.security.ldap.AttributeMappingInfo target)
    {
        target.setId(source.getId());
        target.setMappingName(source.getMappingName());
        target.setUsernameKey(source.getUsernameKey());
        target.setFirstNameKey(source.getFirstNameKey());
        target.setEmailKey(source.getEmailKey());
        target.setLastNameKey(source.getLastNameKey());
        target.setGroupRoleAttributeKey(source.getGroupRoleAttributeKey());
    }

    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#toAttributeMappingInfo(org.openuss.security.ldap.AttributeMapping)
     */
    public org.openuss.security.ldap.AttributeMappingInfo toAttributeMappingInfo(final org.openuss.security.ldap.AttributeMapping entity)
    {
        final org.openuss.security.ldap.AttributeMappingInfo target = new org.openuss.security.ldap.AttributeMappingInfo();
        this.toAttributeMappingInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#attributeMappingInfoToEntity(org.openuss.security.ldap.AttributeMappingInfo, org.openuss.security.ldap.AttributeMapping)
     */
    public void attributeMappingInfoToEntity(
        org.openuss.security.ldap.AttributeMappingInfo source,
        org.openuss.security.ldap.AttributeMapping target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getMappingName() != null)
        {
            target.setMappingName(source.getMappingName());
        }
        if (copyIfNull || source.getUsernameKey() != null)
        {
            target.setUsernameKey(source.getUsernameKey());
        }
        if (copyIfNull || source.getFirstNameKey() != null)
        {
            target.setFirstNameKey(source.getFirstNameKey());
        }
        if (copyIfNull || source.getLastNameKey() != null)
        {
            target.setLastNameKey(source.getLastNameKey());
        }
        if (copyIfNull || source.getEmailKey() != null)
        {
            target.setEmailKey(source.getEmailKey());
        }
        if (copyIfNull || source.getGroupRoleAttributeKey() != null)
        {
            target.setGroupRoleAttributeKey(source.getGroupRoleAttributeKey());
        }
    }
    
}