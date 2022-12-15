package org.openuss.security.ldap;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.security.ldap.UserDnPattern</code>.
 * </p>
 *
 * @see org.openuss.security.ldap.UserDnPattern
 */
public abstract class UserDnPatternDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.security.ldap.UserDnPatternDao
{


    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "UserDnPattern.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.security.ldap.UserDnPatternImpl.class, id);
        return transformEntity(transform, (org.openuss.security.ldap.UserDnPattern)entity);
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#load(java.lang.Long)
     */
    public org.openuss.security.ldap.UserDnPattern load(java.lang.Long id)
    {
        return (org.openuss.security.ldap.UserDnPattern)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.ldap.UserDnPattern> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.security.ldap.UserDnPatternImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#create(org.openuss.security.ldap.UserDnPattern)
     */
    public org.openuss.security.ldap.UserDnPattern create(org.openuss.security.ldap.UserDnPattern userDnPattern)
    {
        return (org.openuss.security.ldap.UserDnPattern)this.create(TRANSFORM_NONE, userDnPattern);
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#create(int transform, org.openuss.security.ldap.UserDnPattern)
     */
    public java.lang.Object create(final int transform, final org.openuss.security.ldap.UserDnPattern userDnPattern)
    {
        if (userDnPattern == null)
        {
            throw new IllegalArgumentException(
                "UserDnPattern.create - 'userDnPattern' can not be null");
        }
        this.getHibernateTemplate().save(userDnPattern);
        return this.transformEntity(transform, userDnPattern);
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#create(java.util.Collection<org.openuss.security.ldap.UserDnPattern>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.ldap.UserDnPattern> create(final java.util.Collection<org.openuss.security.ldap.UserDnPattern> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#create(int, java.util.Collection<org.openuss.security.ldap.UserDnPattern>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.security.ldap.UserDnPattern> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "UserDnPattern.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.security.ldap.UserDnPattern)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#update(org.openuss.security.ldap.UserDnPattern)
     */
    public void update(org.openuss.security.ldap.UserDnPattern userDnPattern)
    {
        if (userDnPattern == null)
        {
            throw new IllegalArgumentException(
                "UserDnPattern.update - 'userDnPattern' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(userDnPattern);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(userDnPattern);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#update(java.util.Collection<org.openuss.security.ldap.UserDnPattern>)
     */
    public void update(final java.util.Collection<org.openuss.security.ldap.UserDnPattern> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "UserDnPattern.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.security.ldap.UserDnPattern)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#remove(org.openuss.security.ldap.UserDnPattern)
     */
    public void remove(org.openuss.security.ldap.UserDnPattern userDnPattern)
    {
        if (userDnPattern == null)
        {
            throw new IllegalArgumentException(
                "UserDnPattern.remove - 'userDnPattern' can not be null");
        }
        this.getHibernateTemplate().delete(userDnPattern);
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "UserDnPattern.remove - 'id can not be null");
        }
        org.openuss.security.ldap.UserDnPattern entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#remove(java.util.Collection<org.openuss.security.ldap.UserDnPattern>)
     */
    public void remove(java.util.Collection<org.openuss.security.ldap.UserDnPattern> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "UserDnPattern.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#findByName(java.lang.String)
     */
    public org.openuss.security.ldap.UserDnPattern findByName(java.lang.String name)
    {
        return (org.openuss.security.ldap.UserDnPattern)this.findByName(TRANSFORM_NONE, name);
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#findByName(java.lang.String, java.lang.String)
     */
    public org.openuss.security.ldap.UserDnPattern findByName(final java.lang.String queryString, final java.lang.String name)
    {
        return (org.openuss.security.ldap.UserDnPattern)this.findByName(TRANSFORM_NONE, queryString, name);
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#findByName(int, java.lang.String)
     */
    public java.lang.Object findByName(final int transform, final java.lang.String name)
    {
        return this.findByName(transform, "from org.openuss.security.ldap.UserDnPattern as userDnPattern where lower(userDnPattern.name) = lower(?)", name);
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#findByName(int, java.lang.String, java.lang.String)
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
                        "More than one instance of 'org.openuss.security.ldap.UserDnPattern"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.security.ldap.UserDnPattern)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.security.ldap.UserDnPatternDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.security.ldap.UserDnPattern} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.security.ldap.UserDnPatternInfo} - {@link TRANSFORM_USERDNPATTERNINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.security.ldap.UserDnPatternDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.security.ldap.UserDnPattern entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_USERDNPATTERNINFO :
                    target = toUserDnPatternInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.security.ldap.UserDnPattern)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.security.ldap.UserDnPatternDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.security.ldap.UserDnPattern)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_USERDNPATTERNINFO :
                toUserDnPatternInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#toUserDnPatternInfoCollection(java.util.Collection)
     */
    public final void toUserDnPatternInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, USERDNPATTERNINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link UserDnPatternDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.security.ldap.UserDnPatternDao#toUserDnPatternInfo(org.openuss.security.ldap.UserDnPattern)
     */
    protected org.openuss.security.ldap.UserDnPatternInfo toUserDnPatternInfo(java.lang.Object[] row)
    {
        org.openuss.security.ldap.UserDnPatternInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.security.ldap.UserDnPattern)
                {
                    target = this.toUserDnPatternInfo((org.openuss.security.ldap.UserDnPattern)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.security.ldap.UserDnPatternInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer USERDNPATTERNINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.security.ldap.UserDnPattern)
                {
                    result = toUserDnPatternInfo((org.openuss.security.ldap.UserDnPattern)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toUserDnPatternInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#userDnPatternInfoToEntityCollection(java.util.Collection)
     */
    public final void userDnPatternInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.security.ldap.UserDnPatternInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, UserDnPatternInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer UserDnPatternInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return userDnPatternInfoToEntity((org.openuss.security.ldap.UserDnPatternInfo)input);
            }
        };

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#toUserDnPatternInfo(org.openuss.security.ldap.UserDnPattern, org.openuss.security.ldap.UserDnPatternInfo)
     */
    public void toUserDnPatternInfo(
        org.openuss.security.ldap.UserDnPattern source,
        org.openuss.security.ldap.UserDnPatternInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#toUserDnPatternInfo(org.openuss.security.ldap.UserDnPattern)
     */
    public org.openuss.security.ldap.UserDnPatternInfo toUserDnPatternInfo(final org.openuss.security.ldap.UserDnPattern entity)
    {
        final org.openuss.security.ldap.UserDnPatternInfo target = new org.openuss.security.ldap.UserDnPatternInfo();
        this.toUserDnPatternInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#userDnPatternInfoToEntity(org.openuss.security.ldap.UserDnPatternInfo, org.openuss.security.ldap.UserDnPattern)
     */
    public void userDnPatternInfoToEntity(
        org.openuss.security.ldap.UserDnPatternInfo source,
        org.openuss.security.ldap.UserDnPattern target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
    }
    
}