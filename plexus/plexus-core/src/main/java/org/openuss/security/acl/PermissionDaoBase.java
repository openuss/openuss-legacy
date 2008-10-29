package org.openuss.security.acl;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.security.acl.Permission</code>.
 * </p>
 *
 * @see org.openuss.security.acl.Permission
 */
public abstract class PermissionDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.security.acl.PermissionDao
{


    /**
     * @see org.openuss.security.acl.PermissionDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final org.openuss.security.acl.PermissionPK permissionPk)
    {
        if (permissionPk == null)
        {
            throw new IllegalArgumentException(
                "Permission.load - 'permissionPk' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.security.acl.PermissionImpl.class, permissionPk);
        return transformEntity(transform, (org.openuss.security.acl.Permission)entity);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#load(org.openuss.security.acl.PermissionPK)
     */
    public org.openuss.security.acl.Permission load(org.openuss.security.acl.PermissionPK permissionPk)
    {
        return (org.openuss.security.acl.Permission)this.load(TRANSFORM_NONE, permissionPk);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.acl.Permission> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.security.acl.PermissionImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.security.acl.PermissionDao#create(org.openuss.security.acl.Permission)
     */
    public org.openuss.security.acl.Permission create(org.openuss.security.acl.Permission permission)
    {
        return (org.openuss.security.acl.Permission)this.create(TRANSFORM_NONE, permission);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#create(int transform, org.openuss.security.acl.Permission)
     */
    public java.lang.Object create(final int transform, final org.openuss.security.acl.Permission permission)
    {
        if (permission == null)
        {
            throw new IllegalArgumentException(
                "Permission.create - 'permission' can not be null");
        }
        this.getHibernateTemplate().save(permission);
        return this.transformEntity(transform, permission);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#create(java.util.Collection<org.openuss.security.acl.Permission>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.acl.Permission> create(final java.util.Collection<org.openuss.security.acl.Permission> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#create(int, java.util.Collection<org.openuss.security.acl.Permission>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.security.acl.Permission> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Permission.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.security.acl.Permission)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#update(org.openuss.security.acl.Permission)
     */
    public void update(org.openuss.security.acl.Permission permission)
    {
        if (permission == null)
        {
            throw new IllegalArgumentException(
                "Permission.update - 'permission' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(permission);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(permission);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#update(java.util.Collection<org.openuss.security.acl.Permission>)
     */
    public void update(final java.util.Collection<org.openuss.security.acl.Permission> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Permission.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.security.acl.Permission)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#remove(org.openuss.security.acl.Permission)
     */
    public void remove(org.openuss.security.acl.Permission permission)
    {
        if (permission == null)
        {
            throw new IllegalArgumentException(
                "Permission.remove - 'permission' can not be null");
        }
        this.getHibernateTemplate().delete(permission);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#remove(java.lang.Long)
     */
    public void remove(org.openuss.security.acl.PermissionPK permissionPk)
    {
        if (permissionPk == null)
        {
            throw new IllegalArgumentException(
                "Permission.remove - 'permissionPk can not be null");
        }
        org.openuss.security.acl.Permission entity = this.load(permissionPk);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#remove(java.util.Collection<org.openuss.security.acl.Permission>)
     */
    public void remove(java.util.Collection<org.openuss.security.acl.Permission> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Permission.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.security.acl.PermissionDao#findPermission(org.openuss.security.acl.ObjectIdentity, org.openuss.security.Authority)
     */
    public org.openuss.security.acl.Permission findPermission(org.openuss.security.acl.ObjectIdentity aclObjectIdentity, org.openuss.security.Authority recipient)
    {
        return (org.openuss.security.acl.Permission)this.findPermission(TRANSFORM_NONE, aclObjectIdentity, recipient);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermission(java.lang.String, org.openuss.security.acl.ObjectIdentity, org.openuss.security.Authority)
     */
    public org.openuss.security.acl.Permission findPermission(final java.lang.String queryString, final org.openuss.security.acl.ObjectIdentity aclObjectIdentity, final org.openuss.security.Authority recipient)
    {
        return (org.openuss.security.acl.Permission)this.findPermission(TRANSFORM_NONE, queryString, aclObjectIdentity, recipient);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermission(int, org.openuss.security.acl.ObjectIdentity, org.openuss.security.Authority)
     */
    public java.lang.Object findPermission(final int transform, final org.openuss.security.acl.ObjectIdentity aclObjectIdentity, final org.openuss.security.Authority recipient)
    {
        return this.findPermission(transform, "from org.openuss.security.acl.Permission as permission where permission.aclObjectIdentity = ? and permission.recipient = ?", aclObjectIdentity, recipient);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermission(int, java.lang.String, org.openuss.security.acl.ObjectIdentity, org.openuss.security.Authority)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findPermission(final int transform, final java.lang.String queryString, final org.openuss.security.acl.ObjectIdentity aclObjectIdentity, final org.openuss.security.Authority recipient)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, aclObjectIdentity);
            queryObject.setParameter(1, recipient);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.security.acl.Permission"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.security.acl.Permission)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermissions(org.openuss.security.acl.ObjectIdentity)
     */
    public java.util.List findPermissions(org.openuss.security.acl.ObjectIdentity aclObjectIdentity)
    {
        return this.findPermissions(TRANSFORM_NONE, aclObjectIdentity);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermissions(java.lang.String, org.openuss.security.acl.ObjectIdentity)
     */
    public java.util.List findPermissions(final java.lang.String queryString, final org.openuss.security.acl.ObjectIdentity aclObjectIdentity)
    {
        return this.findPermissions(TRANSFORM_NONE, queryString, aclObjectIdentity);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermissions(int, org.openuss.security.acl.ObjectIdentity)
     */
    public java.util.List findPermissions(final int transform, final org.openuss.security.acl.ObjectIdentity aclObjectIdentity)
    {
        return this.findPermissions(transform, "from org.openuss.security.acl.Permission as permission where permission.aclObjectIdentity = ?", aclObjectIdentity);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermissions(int, java.lang.String, org.openuss.security.acl.ObjectIdentity)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findPermissions(final int transform, final java.lang.String queryString, final org.openuss.security.acl.ObjectIdentity aclObjectIdentity)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, aclObjectIdentity);
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
     * @see org.openuss.security.acl.PermissionDao#findPermissionsByAuthority(org.openuss.security.Authority)
     */
    public java.util.List findPermissionsByAuthority(org.openuss.security.Authority recipient)
    {
        return this.findPermissionsByAuthority(TRANSFORM_NONE, recipient);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermissionsByAuthority(java.lang.String, org.openuss.security.Authority)
     */
    public java.util.List findPermissionsByAuthority(final java.lang.String queryString, final org.openuss.security.Authority recipient)
    {
        return this.findPermissionsByAuthority(TRANSFORM_NONE, queryString, recipient);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermissionsByAuthority(int, org.openuss.security.Authority)
     */
    public java.util.List findPermissionsByAuthority(final int transform, final org.openuss.security.Authority recipient)
    {
        return this.findPermissionsByAuthority(transform, "from org.openuss.security.acl.Permission as permission where permission.recipient = ?", recipient);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermissionsByAuthority(int, java.lang.String, org.openuss.security.Authority)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findPermissionsByAuthority(final int transform, final java.lang.String queryString, final org.openuss.security.Authority recipient)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, recipient);
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
     * @see org.openuss.security.acl.PermissionDao#findPermissionsWithRecipient(org.openuss.security.acl.ObjectIdentity)
     */
    public java.util.List findPermissionsWithRecipient(org.openuss.security.acl.ObjectIdentity objectIdentity)
    {
        return this.findPermissionsWithRecipient(TRANSFORM_NONE, objectIdentity);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermissionsWithRecipient(java.lang.String, org.openuss.security.acl.ObjectIdentity)
     */
    public java.util.List findPermissionsWithRecipient(final java.lang.String queryString, final org.openuss.security.acl.ObjectIdentity objectIdentity)
    {
        return this.findPermissionsWithRecipient(TRANSFORM_NONE, queryString, objectIdentity);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermissionsWithRecipient(int, org.openuss.security.acl.ObjectIdentity)
     */
    public java.util.List findPermissionsWithRecipient(final int transform, final org.openuss.security.acl.ObjectIdentity objectIdentity)
    {
        return this.findPermissionsWithRecipient(transform, "from org.openuss.security.acl.Permission as permission where permission.objectIdentity = ?", objectIdentity);
    }

    /**
     * @see org.openuss.security.acl.PermissionDao#findPermissionsWithRecipient(int, java.lang.String, org.openuss.security.acl.ObjectIdentity)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findPermissionsWithRecipient(final int transform, final java.lang.String queryString, final org.openuss.security.acl.ObjectIdentity objectIdentity)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, objectIdentity);
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
     * flag is set to one of the constants defined in <code>org.openuss.security.acl.PermissionDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.security.acl.PermissionDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.security.acl.Permission entity)
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
     * {@link #transformEntity(int,org.openuss.security.acl.Permission)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.security.acl.PermissionDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.security.acl.Permission)
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