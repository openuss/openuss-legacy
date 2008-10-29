package org.openuss.security;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.security.Group</code>.
 * </p>
 *
 * @see org.openuss.security.Group
 */
public abstract class GroupDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.security.GroupDao
{


    /**
     * @see org.openuss.security.GroupDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Group.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.security.GroupImpl.class, id);
        return transformEntity(transform, (org.openuss.security.Group)entity);
    }

    /**
     * @see org.openuss.security.GroupDao#load(java.lang.Long)
     */
    public org.openuss.security.Group load(java.lang.Long id)
    {
        return (org.openuss.security.Group)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.security.GroupDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.Group> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.GroupDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.security.GroupImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.security.GroupDao#create(org.openuss.security.Group)
     */
    public org.openuss.security.Group create(org.openuss.security.Group group)
    {
        return (org.openuss.security.Group)this.create(TRANSFORM_NONE, group);
    }

    /**
     * @see org.openuss.security.GroupDao#create(int transform, org.openuss.security.Group)
     */
    public java.lang.Object create(final int transform, final org.openuss.security.Group group)
    {
        if (group == null)
        {
            throw new IllegalArgumentException(
                "Group.create - 'group' can not be null");
        }
        this.getHibernateTemplate().save(group);
        return this.transformEntity(transform, group);
    }

    /**
     * @see org.openuss.security.GroupDao#create(java.util.Collection<org.openuss.security.Group>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.Group> create(final java.util.Collection<org.openuss.security.Group> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.security.GroupDao#create(int, java.util.Collection<org.openuss.security.Group>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.security.Group> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Group.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.security.Group)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.security.GroupDao#update(org.openuss.security.Group)
     */
    public void update(org.openuss.security.Group group)
    {
        if (group == null)
        {
            throw new IllegalArgumentException(
                "Group.update - 'group' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(group);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(group);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.security.GroupDao#update(java.util.Collection<org.openuss.security.Group>)
     */
    public void update(final java.util.Collection<org.openuss.security.Group> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Group.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.security.Group)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.security.GroupDao#remove(org.openuss.security.Group)
     */
    public void remove(org.openuss.security.Group group)
    {
        if (group == null)
        {
            throw new IllegalArgumentException(
                "Group.remove - 'group' can not be null");
        }
        this.getHibernateTemplate().delete(group);
    }

    /**
     * @see org.openuss.security.GroupDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Group.remove - 'id can not be null");
        }
        org.openuss.security.Group entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.security.GroupDao#remove(java.util.Collection<org.openuss.security.Group>)
     */
    public void remove(java.util.Collection<org.openuss.security.Group> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Group.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.security.GroupDao#findGroupByName(java.lang.String)
     */
    public org.openuss.security.Group findGroupByName(java.lang.String name)
    {
        return (org.openuss.security.Group)this.findGroupByName(TRANSFORM_NONE, name);
    }

    /**
     * @see org.openuss.security.GroupDao#findGroupByName(java.lang.String, java.lang.String)
     */
    public org.openuss.security.Group findGroupByName(final java.lang.String queryString, final java.lang.String name)
    {
        return (org.openuss.security.Group)this.findGroupByName(TRANSFORM_NONE, queryString, name);
    }

    /**
     * @see org.openuss.security.GroupDao#findGroupByName(int, java.lang.String)
     */
    public java.lang.Object findGroupByName(final int transform, final java.lang.String name)
    {
        return this.findGroupByName(transform, "from org.openuss.security.Group as g where g.name = :name", name);
    }

    /**
     * @see org.openuss.security.GroupDao#findGroupByName(int, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findGroupByName(final int transform, final java.lang.String queryString, final java.lang.String name)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("name", name);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.security.Group"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.security.Group)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.security.GroupDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.security.Group} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.security.GroupItem} - {@link TRANSFORM_GROUPITEM}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.security.GroupDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.security.Group entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_GROUPITEM :
                    target = toGroupItem(entity);
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
     * {@link #transformEntity(int,org.openuss.security.Group)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.security.GroupDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.security.Group)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_GROUPITEM :
                toGroupItemCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.security.GroupDao#toGroupItemCollection(java.util.Collection)
     */
    public final void toGroupItemCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, GROUPITEM_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link GroupDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.security.GroupDao#toGroupItem(org.openuss.security.Group)
     */
    protected org.openuss.security.GroupItem toGroupItem(java.lang.Object[] row)
    {
        org.openuss.security.GroupItem target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.security.Group)
                {
                    target = this.toGroupItem((org.openuss.security.Group)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.security.GroupItem}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer GROUPITEM_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.security.Group)
                {
                    result = toGroupItem((org.openuss.security.Group)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toGroupItem((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.security.GroupDao#groupItemToEntityCollection(java.util.Collection)
     */
    public final void groupItemToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.security.GroupItem))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, GroupItemToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer GroupItemToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return groupItemToEntity((org.openuss.security.GroupItem)input);
            }
        };

    /**
     * @see org.openuss.security.GroupDao#toGroupItem(org.openuss.security.Group, org.openuss.security.GroupItem)
     */
    public void toGroupItem(
        org.openuss.security.Group source,
        org.openuss.security.GroupItem target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setLabel(source.getLabel());
        target.setGroupType(source.getGroupType());
        target.setPassword(source.getPassword());
    }

    /**
     * @see org.openuss.security.GroupDao#toGroupItem(org.openuss.security.Group)
     */
    public org.openuss.security.GroupItem toGroupItem(final org.openuss.security.Group entity)
    {
        final org.openuss.security.GroupItem target = new org.openuss.security.GroupItem();
        this.toGroupItem(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.security.GroupDao#groupItemToEntity(org.openuss.security.GroupItem, org.openuss.security.Group)
     */
    public void groupItemToEntity(
        org.openuss.security.GroupItem source,
        org.openuss.security.Group target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getLabel() != null)
        {
            target.setLabel(source.getLabel());
        }
        if (copyIfNull || source.getGroupType() != null)
        {
            target.setGroupType(source.getGroupType());
        }
        if (copyIfNull || source.getPassword() != null)
        {
            target.setPassword(source.getPassword());
        }
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
    }
    
}