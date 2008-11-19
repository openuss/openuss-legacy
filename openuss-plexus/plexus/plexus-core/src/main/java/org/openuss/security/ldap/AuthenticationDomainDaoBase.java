package org.openuss.security.ldap;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.security.ldap.AuthenticationDomain</code>.
 * </p>
 *
 * @see org.openuss.security.ldap.AuthenticationDomain
 */
public abstract class AuthenticationDomainDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.security.ldap.AuthenticationDomainDao
{


    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "AuthenticationDomain.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.security.ldap.AuthenticationDomainImpl.class, id);
        return transformEntity(transform, (org.openuss.security.ldap.AuthenticationDomain)entity);
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#load(java.lang.Long)
     */
    public org.openuss.security.ldap.AuthenticationDomain load(java.lang.Long id)
    {
        return (org.openuss.security.ldap.AuthenticationDomain)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.ldap.AuthenticationDomain> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.security.ldap.AuthenticationDomainImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#create(org.openuss.security.ldap.AuthenticationDomain)
     */
    public org.openuss.security.ldap.AuthenticationDomain create(org.openuss.security.ldap.AuthenticationDomain authenticationDomain)
    {
        return (org.openuss.security.ldap.AuthenticationDomain)this.create(TRANSFORM_NONE, authenticationDomain);
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#create(int transform, org.openuss.security.ldap.AuthenticationDomain)
     */
    public java.lang.Object create(final int transform, final org.openuss.security.ldap.AuthenticationDomain authenticationDomain)
    {
        if (authenticationDomain == null)
        {
            throw new IllegalArgumentException(
                "AuthenticationDomain.create - 'authenticationDomain' can not be null");
        }
        this.getHibernateTemplate().save(authenticationDomain);
        return this.transformEntity(transform, authenticationDomain);
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#create(java.util.Collection<org.openuss.security.ldap.AuthenticationDomain>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.ldap.AuthenticationDomain> create(final java.util.Collection<org.openuss.security.ldap.AuthenticationDomain> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#create(int, java.util.Collection<org.openuss.security.ldap.AuthenticationDomain>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.security.ldap.AuthenticationDomain> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "AuthenticationDomain.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.security.ldap.AuthenticationDomain)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#update(org.openuss.security.ldap.AuthenticationDomain)
     */
    public void update(org.openuss.security.ldap.AuthenticationDomain authenticationDomain)
    {
        if (authenticationDomain == null)
        {
            throw new IllegalArgumentException(
                "AuthenticationDomain.update - 'authenticationDomain' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(authenticationDomain);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(authenticationDomain);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#update(java.util.Collection<org.openuss.security.ldap.AuthenticationDomain>)
     */
    public void update(final java.util.Collection<org.openuss.security.ldap.AuthenticationDomain> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "AuthenticationDomain.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.security.ldap.AuthenticationDomain)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#remove(org.openuss.security.ldap.AuthenticationDomain)
     */
    public void remove(org.openuss.security.ldap.AuthenticationDomain authenticationDomain)
    {
        if (authenticationDomain == null)
        {
            throw new IllegalArgumentException(
                "AuthenticationDomain.remove - 'authenticationDomain' can not be null");
        }
        this.getHibernateTemplate().delete(authenticationDomain);
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "AuthenticationDomain.remove - 'id can not be null");
        }
        org.openuss.security.ldap.AuthenticationDomain entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#remove(java.util.Collection<org.openuss.security.ldap.AuthenticationDomain>)
     */
    public void remove(java.util.Collection<org.openuss.security.ldap.AuthenticationDomain> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "AuthenticationDomain.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#findByName(java.lang.String)
     */
    public org.openuss.security.ldap.AuthenticationDomain findByName(java.lang.String name)
    {
        return (org.openuss.security.ldap.AuthenticationDomain)this.findByName(TRANSFORM_NONE, name);
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#findByName(java.lang.String, java.lang.String)
     */
    public org.openuss.security.ldap.AuthenticationDomain findByName(final java.lang.String queryString, final java.lang.String name)
    {
        return (org.openuss.security.ldap.AuthenticationDomain)this.findByName(TRANSFORM_NONE, queryString, name);
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#findByName(int, java.lang.String)
     */
    public java.lang.Object findByName(final int transform, final java.lang.String name)
    {
        return this.findByName(transform, "from org.openuss.security.ldap.AuthenticationDomain as authdomain where lower(authdomain.name) = lower(?)", name);
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#findByName(int, java.lang.String, java.lang.String)
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
                        "More than one instance of 'org.openuss.security.ldap.AuthenticationDomain"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.security.ldap.AuthenticationDomain)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.security.ldap.AuthenticationDomainDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.security.ldap.AuthenticationDomain} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.security.ldap.AuthenticationDomainInfo} - {@link TRANSFORM_AUTHENTICATIONDOMAININFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.security.ldap.AuthenticationDomainDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.security.ldap.AuthenticationDomain entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_AUTHENTICATIONDOMAININFO :
                    target = toAuthenticationDomainInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.security.ldap.AuthenticationDomain)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.security.ldap.AuthenticationDomainDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.security.ldap.AuthenticationDomain)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_AUTHENTICATIONDOMAININFO :
                toAuthenticationDomainInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#toAuthenticationDomainInfoCollection(java.util.Collection)
     */
    public final void toAuthenticationDomainInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, AUTHENTICATIONDOMAININFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link AuthenticationDomainDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.security.ldap.AuthenticationDomainDao#toAuthenticationDomainInfo(org.openuss.security.ldap.AuthenticationDomain)
     */
    protected org.openuss.security.ldap.AuthenticationDomainInfo toAuthenticationDomainInfo(java.lang.Object[] row)
    {
        org.openuss.security.ldap.AuthenticationDomainInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.security.ldap.AuthenticationDomain)
                {
                    target = this.toAuthenticationDomainInfo((org.openuss.security.ldap.AuthenticationDomain)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.security.ldap.AuthenticationDomainInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer AUTHENTICATIONDOMAININFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.security.ldap.AuthenticationDomain)
                {
                    result = toAuthenticationDomainInfo((org.openuss.security.ldap.AuthenticationDomain)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toAuthenticationDomainInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#authenticationDomainInfoToEntityCollection(java.util.Collection)
     */
    public final void authenticationDomainInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.security.ldap.AuthenticationDomainInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, AuthenticationDomainInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer AuthenticationDomainInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return authenticationDomainInfoToEntity((org.openuss.security.ldap.AuthenticationDomainInfo)input);
            }
        };

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#toAuthenticationDomainInfo(org.openuss.security.ldap.AuthenticationDomain, org.openuss.security.ldap.AuthenticationDomainInfo)
     */
    public void toAuthenticationDomainInfo(
        org.openuss.security.ldap.AuthenticationDomain source,
        org.openuss.security.ldap.AuthenticationDomainInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setChangePasswordUrl(source.getChangePasswordUrl());
    }

    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#toAuthenticationDomainInfo(org.openuss.security.ldap.AuthenticationDomain)
     */
    public org.openuss.security.ldap.AuthenticationDomainInfo toAuthenticationDomainInfo(final org.openuss.security.ldap.AuthenticationDomain entity)
    {
        final org.openuss.security.ldap.AuthenticationDomainInfo target = new org.openuss.security.ldap.AuthenticationDomainInfo();
        this.toAuthenticationDomainInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#authenticationDomainInfoToEntity(org.openuss.security.ldap.AuthenticationDomainInfo, org.openuss.security.ldap.AuthenticationDomain)
     */
    public void authenticationDomainInfoToEntity(
        org.openuss.security.ldap.AuthenticationDomainInfo source,
        org.openuss.security.ldap.AuthenticationDomain target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
        if (copyIfNull || source.getDescription() != null)
        {
            target.setDescription(source.getDescription());
        }
        if (copyIfNull || source.getChangePasswordUrl() != null)
        {
            target.setChangePasswordUrl(source.getChangePasswordUrl());
        }
    }
    
}