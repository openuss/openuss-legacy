package org.openuss.security.ldap;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.security.ldap.RoleAttributeKey</code>.
 * </p>
 *
 * @see org.openuss.security.ldap.RoleAttributeKey
 */
public abstract class RoleAttributeKeyDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.security.ldap.RoleAttributeKeyDao
{


    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "RoleAttributeKey.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.security.ldap.RoleAttributeKeyImpl.class, id);
        return transformEntity(transform, (org.openuss.security.ldap.RoleAttributeKey)entity);
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#load(java.lang.Long)
     */
    public org.openuss.security.ldap.RoleAttributeKey load(java.lang.Long id)
    {
        return (org.openuss.security.ldap.RoleAttributeKey)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.ldap.RoleAttributeKey> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.security.ldap.RoleAttributeKeyImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#create(org.openuss.security.ldap.RoleAttributeKey)
     */
    public org.openuss.security.ldap.RoleAttributeKey create(org.openuss.security.ldap.RoleAttributeKey roleAttributeKey)
    {
        return (org.openuss.security.ldap.RoleAttributeKey)this.create(TRANSFORM_NONE, roleAttributeKey);
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#create(int transform, org.openuss.security.ldap.RoleAttributeKey)
     */
    public java.lang.Object create(final int transform, final org.openuss.security.ldap.RoleAttributeKey roleAttributeKey)
    {
        if (roleAttributeKey == null)
        {
            throw new IllegalArgumentException(
                "RoleAttributeKey.create - 'roleAttributeKey' can not be null");
        }
        this.getHibernateTemplate().save(roleAttributeKey);
        return this.transformEntity(transform, roleAttributeKey);
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#create(java.util.Collection<org.openuss.security.ldap.RoleAttributeKey>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.ldap.RoleAttributeKey> create(final java.util.Collection<org.openuss.security.ldap.RoleAttributeKey> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#create(int, java.util.Collection<org.openuss.security.ldap.RoleAttributeKey>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.security.ldap.RoleAttributeKey> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "RoleAttributeKey.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.security.ldap.RoleAttributeKey)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#update(org.openuss.security.ldap.RoleAttributeKey)
     */
    public void update(org.openuss.security.ldap.RoleAttributeKey roleAttributeKey)
    {
        if (roleAttributeKey == null)
        {
            throw new IllegalArgumentException(
                "RoleAttributeKey.update - 'roleAttributeKey' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(roleAttributeKey);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(roleAttributeKey);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#update(java.util.Collection<org.openuss.security.ldap.RoleAttributeKey>)
     */
    public void update(final java.util.Collection<org.openuss.security.ldap.RoleAttributeKey> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "RoleAttributeKey.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.security.ldap.RoleAttributeKey)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#remove(org.openuss.security.ldap.RoleAttributeKey)
     */
    public void remove(org.openuss.security.ldap.RoleAttributeKey roleAttributeKey)
    {
        if (roleAttributeKey == null)
        {
            throw new IllegalArgumentException(
                "RoleAttributeKey.remove - 'roleAttributeKey' can not be null");
        }
        this.getHibernateTemplate().delete(roleAttributeKey);
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "RoleAttributeKey.remove - 'id can not be null");
        }
        org.openuss.security.ldap.RoleAttributeKey entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#remove(java.util.Collection<org.openuss.security.ldap.RoleAttributeKey>)
     */
    public void remove(java.util.Collection<org.openuss.security.ldap.RoleAttributeKey> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "RoleAttributeKey.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#findByName(java.lang.String)
     */
    public org.openuss.security.ldap.RoleAttributeKey findByName(java.lang.String name)
    {
        return (org.openuss.security.ldap.RoleAttributeKey)this.findByName(TRANSFORM_NONE, name);
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#findByName(java.lang.String, java.lang.String)
     */
    public org.openuss.security.ldap.RoleAttributeKey findByName(final java.lang.String queryString, final java.lang.String name)
    {
        return (org.openuss.security.ldap.RoleAttributeKey)this.findByName(TRANSFORM_NONE, queryString, name);
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#findByName(int, java.lang.String)
     */
    public java.lang.Object findByName(final int transform, final java.lang.String name)
    {
        return this.findByName(transform, "from org.openuss.security.ldap.RoleAttributeKey as roleAttributeKey where lower(roleAttributeKey.name) = lower(?)", name);
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#findByName(int, java.lang.String, java.lang.String)
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
                        "More than one instance of 'org.openuss.security.ldap.RoleAttributeKey"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.security.ldap.RoleAttributeKey)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.security.ldap.RoleAttributeKeyDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.security.ldap.RoleAttributeKey} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.security.ldap.RoleAttributeKeyInfo} - {@link TRANSFORM_ROLEATTRIBUTEKEYINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.security.ldap.RoleAttributeKeyDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.security.ldap.RoleAttributeKey entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_ROLEATTRIBUTEKEYINFO :
                    target = toRoleAttributeKeyInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.security.ldap.RoleAttributeKey)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.security.ldap.RoleAttributeKeyDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.security.ldap.RoleAttributeKey)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_ROLEATTRIBUTEKEYINFO :
                toRoleAttributeKeyInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#toRoleAttributeKeyInfoCollection(java.util.Collection)
     */
    public final void toRoleAttributeKeyInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, ROLEATTRIBUTEKEYINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link RoleAttributeKeyDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#toRoleAttributeKeyInfo(org.openuss.security.ldap.RoleAttributeKey)
     */
    protected org.openuss.security.ldap.RoleAttributeKeyInfo toRoleAttributeKeyInfo(java.lang.Object[] row)
    {
        org.openuss.security.ldap.RoleAttributeKeyInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.security.ldap.RoleAttributeKey)
                {
                    target = this.toRoleAttributeKeyInfo((org.openuss.security.ldap.RoleAttributeKey)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.security.ldap.RoleAttributeKeyInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer ROLEATTRIBUTEKEYINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.security.ldap.RoleAttributeKey)
                {
                    result = toRoleAttributeKeyInfo((org.openuss.security.ldap.RoleAttributeKey)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toRoleAttributeKeyInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#roleAttributeKeyInfoToEntityCollection(java.util.Collection)
     */
    public final void roleAttributeKeyInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.security.ldap.RoleAttributeKeyInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, RoleAttributeKeyInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer RoleAttributeKeyInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return roleAttributeKeyInfoToEntity((org.openuss.security.ldap.RoleAttributeKeyInfo)input);
            }
        };

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#toRoleAttributeKeyInfo(org.openuss.security.ldap.RoleAttributeKey, org.openuss.security.ldap.RoleAttributeKeyInfo)
     */
    public void toRoleAttributeKeyInfo(
        org.openuss.security.ldap.RoleAttributeKey source,
        org.openuss.security.ldap.RoleAttributeKeyInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#toRoleAttributeKeyInfo(org.openuss.security.ldap.RoleAttributeKey)
     */
    public org.openuss.security.ldap.RoleAttributeKeyInfo toRoleAttributeKeyInfo(final org.openuss.security.ldap.RoleAttributeKey entity)
    {
        final org.openuss.security.ldap.RoleAttributeKeyInfo target = new org.openuss.security.ldap.RoleAttributeKeyInfo();
        this.toRoleAttributeKeyInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#roleAttributeKeyInfoToEntity(org.openuss.security.ldap.RoleAttributeKeyInfo, org.openuss.security.ldap.RoleAttributeKey)
     */
    public void roleAttributeKeyInfoToEntity(
        org.openuss.security.ldap.RoleAttributeKeyInfo source,
        org.openuss.security.ldap.RoleAttributeKey target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
    }
    
}