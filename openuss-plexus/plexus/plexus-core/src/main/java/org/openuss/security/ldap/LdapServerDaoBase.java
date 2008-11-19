
package org.openuss.security.ldap;

/**
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.security.ldap.LdapServer</code>.
 *
 * @see org.openuss.security.ldap.LdapServer
 */
public abstract class LdapServerDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.security.ldap.LdapServerDao
{


    /**
     * @see org.openuss.security.ldap.LdapServerDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "LdapServer.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.security.ldap.LdapServerImpl.class, id);
        return transformEntity(transform, (org.openuss.security.ldap.LdapServer)entity);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#load(java.lang.Long)
     */
    public org.openuss.security.ldap.LdapServer load(java.lang.Long id)
    {
        return (org.openuss.security.ldap.LdapServer)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.ldap.LdapServer> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.security.ldap.LdapServerImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.security.ldap.LdapServerDao#create(org.openuss.security.ldap.LdapServer)
     */
    public org.openuss.security.ldap.LdapServer create(org.openuss.security.ldap.LdapServer ldapServer)
    {
        return (org.openuss.security.ldap.LdapServer)this.create(TRANSFORM_NONE, ldapServer);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#create(int transform, org.openuss.security.ldap.LdapServer)
     */
    public java.lang.Object create(final int transform, final org.openuss.security.ldap.LdapServer ldapServer)
    {
        if (ldapServer == null)
        {
            throw new IllegalArgumentException(
                "LdapServer.create - 'ldapServer' can not be null");
        }
        this.getHibernateTemplate().save(ldapServer);
        return this.transformEntity(transform, ldapServer);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#create(java.util.Collection<org.openuss.security.ldap.LdapServer>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.ldap.LdapServer> create(final java.util.Collection<org.openuss.security.ldap.LdapServer> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#create(int, java.util.Collection<org.openuss.security.ldap.LdapServer>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.security.ldap.LdapServer> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "LdapServer.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.security.ldap.LdapServer)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#update(org.openuss.security.ldap.LdapServer)
     */
    public void update(org.openuss.security.ldap.LdapServer ldapServer)
    {
        if (ldapServer == null)
        {
            throw new IllegalArgumentException(
                "LdapServer.update - 'ldapServer' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(ldapServer);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(ldapServer);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#update(java.util.Collection<org.openuss.security.ldap.LdapServer>)
     */
    public void update(final java.util.Collection<org.openuss.security.ldap.LdapServer> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "LdapServer.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.security.ldap.LdapServer)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#remove(org.openuss.security.ldap.LdapServer)
     */
    public void remove(org.openuss.security.ldap.LdapServer ldapServer)
    {
        if (ldapServer == null)
        {
            throw new IllegalArgumentException(
                "LdapServer.remove - 'ldapServer' can not be null");
        }
        this.getHibernateTemplate().delete(ldapServer);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "LdapServer.remove - 'id can not be null");
        }
        org.openuss.security.ldap.LdapServer entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#remove(java.util.Collection<org.openuss.security.ldap.LdapServer>)
     */
    public void remove(java.util.Collection<org.openuss.security.ldap.LdapServer> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "LdapServer.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.security.ldap.LdapServerDao#findByAuthenticationDomain(org.openuss.security.ldap.AuthenticationDomain)
     */
    public java.util.List findByAuthenticationDomain(org.openuss.security.ldap.AuthenticationDomain authenticationDomain)
    {
        return this.findByAuthenticationDomain(TRANSFORM_NONE, authenticationDomain);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#findByAuthenticationDomain(java.lang.String, org.openuss.security.ldap.AuthenticationDomain)
     */
    public java.util.List findByAuthenticationDomain(final java.lang.String queryString, final org.openuss.security.ldap.AuthenticationDomain authenticationDomain)
    {
        return this.findByAuthenticationDomain(TRANSFORM_NONE, queryString, authenticationDomain);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#findByAuthenticationDomain(int, org.openuss.security.ldap.AuthenticationDomain)
     */
    public java.util.List findByAuthenticationDomain(final int transform, final org.openuss.security.ldap.AuthenticationDomain authenticationDomain)
    {
        return this.findByAuthenticationDomain(transform, "from org.openuss.security.ldap.LdapServer as ldapServer where ldapServer.authenticationDomain = ?", authenticationDomain);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#findByAuthenticationDomain(int, java.lang.String, org.openuss.security.ldap.AuthenticationDomain)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByAuthenticationDomain(final int transform, final java.lang.String queryString, final org.openuss.security.ldap.AuthenticationDomain authenticationDomain)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, authenticationDomain);
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
     * @see org.openuss.security.ldap.LdapServerDao#findAllEnabledServers()
     */
    public java.util.List findAllEnabledServers()
    {
        return this.findAllEnabledServers(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#findAllEnabledServers(java.lang.String)
     */
    public java.util.List findAllEnabledServers(final java.lang.String queryString)
    {
        return this.findAllEnabledServers(TRANSFORM_NONE, queryString);
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#findAllEnabledServers(int)
     */
    public java.util.List findAllEnabledServers(final int transform)
    {
        return this.findAllEnabledServers(transform, "from org.openuss.security.ldap.LdapServer where enabled=true");
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#findAllEnabledServers(int, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findAllEnabledServers(final int transform, final java.lang.String queryString)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
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
     * flag is set to one of the constants defined in <code>org.openuss.security.ldap.LdapServerDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.security.ldap.LdapServer} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.security.ldap.LdapServerInfo} - {@link TRANSFORM_LDAPSERVERINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.security.ldap.LdapServerDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.security.ldap.LdapServer entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_LDAPSERVERINFO :
                    target = toLdapServerInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.security.ldap.LdapServer)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.security.ldap.LdapServerDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.security.ldap.LdapServer)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_LDAPSERVERINFO :
                toLdapServerInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#toLdapServerInfoCollection(java.util.Collection)
     */
    public final void toLdapServerInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, LDAPSERVERINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link LdapServerDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.security.ldap.LdapServerDao#toLdapServerInfo(org.openuss.security.ldap.LdapServer)
     */
    protected org.openuss.security.ldap.LdapServerInfo toLdapServerInfo(java.lang.Object[] row)
    {
        org.openuss.security.ldap.LdapServerInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.security.ldap.LdapServer)
                {
                    target = this.toLdapServerInfo((org.openuss.security.ldap.LdapServer)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.security.ldap.LdapServerInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer LDAPSERVERINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.security.ldap.LdapServer)
                {
                    result = toLdapServerInfo((org.openuss.security.ldap.LdapServer)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toLdapServerInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.security.ldap.LdapServerDao#ldapServerInfoToEntityCollection(java.util.Collection)
     */
    public final void ldapServerInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.security.ldap.LdapServerInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, LdapServerInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer LdapServerInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return ldapServerInfoToEntity((org.openuss.security.ldap.LdapServerInfo)input);
            }
        };

    /**
     * @see org.openuss.security.ldap.LdapServerDao#toLdapServerInfo(org.openuss.security.ldap.LdapServer, org.openuss.security.ldap.LdapServerInfo)
     */
    public void toLdapServerInfo(
        org.openuss.security.ldap.LdapServer source,
        org.openuss.security.ldap.LdapServerInfo target)
    {
        target.setId(source.getId());
        target.setProviderUrl(source.getProviderUrl());
        target.setPort(source.getPort());
        target.setRootDn(source.getRootDn());
        target.setAuthenticationType(source.getAuthenticationType());
        target.setManagerDn(source.getManagerDn());
        target.setManagerPassword(source.getManagerPassword());
        target.setUseConnectionPool(source.getUseConnectionPool());
        target.setUseLdapContext(source.getUseLdapContext());
        target.setDescription(source.getDescription());
        target.setLdapServerType(source.getLdapServerType());
        target.setEnabled(source.isEnabled());
    }

    /**
     * @see org.openuss.security.ldap.LdapServerDao#toLdapServerInfo(org.openuss.security.ldap.LdapServer)
     */
    public org.openuss.security.ldap.LdapServerInfo toLdapServerInfo(final org.openuss.security.ldap.LdapServer entity)
    {
        final org.openuss.security.ldap.LdapServerInfo target = new org.openuss.security.ldap.LdapServerInfo();
        this.toLdapServerInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.security.ldap.LdapServerDao#ldapServerInfoToEntity(org.openuss.security.ldap.LdapServerInfo, org.openuss.security.ldap.LdapServer)
     */
    public void ldapServerInfoToEntity(
        org.openuss.security.ldap.LdapServerInfo source,
        org.openuss.security.ldap.LdapServer target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getProviderUrl() != null)
        {
            target.setProviderUrl(source.getProviderUrl());
        }
        if (copyIfNull || source.getPort() != null)
        {
            target.setPort(source.getPort());
        }
        if (copyIfNull || source.getRootDn() != null)
        {
            target.setRootDn(source.getRootDn());
        }
        if (copyIfNull || source.getAuthenticationType() != null)
        {
            target.setAuthenticationType(source.getAuthenticationType());
        }
        if (copyIfNull || source.getManagerDn() != null)
        {
            target.setManagerDn(source.getManagerDn());
        }
        if (copyIfNull || source.getManagerPassword() != null)
        {
            target.setManagerPassword(source.getManagerPassword());
        }
        if (copyIfNull || source.getUseConnectionPool() != null)
        {
            target.setUseConnectionPool(source.getUseConnectionPool());
        }
        if (copyIfNull || source.getUseLdapContext() != null)
        {
            target.setUseLdapContext(source.getUseLdapContext());
        }
        if (copyIfNull || source.getDescription() != null)
        {
            target.setDescription(source.getDescription());
        }
        if (copyIfNull || source.getLdapServerType() != null)
        {
            target.setLdapServerType(source.getLdapServerType());
        }
	    target.setEnabled(source.isEnabled());
    }
    
}