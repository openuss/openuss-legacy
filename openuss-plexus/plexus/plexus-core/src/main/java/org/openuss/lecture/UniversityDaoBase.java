package org.openuss.lecture;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.lecture.University</code>.
 * </p>
 *
 * @see org.openuss.lecture.University
 */
public abstract class UniversityDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.lecture.UniversityDao
{


    /**
     * @see org.openuss.lecture.UniversityDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "University.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.lecture.UniversityImpl.class, id);
        return transformEntity(transform, (org.openuss.lecture.University)entity);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#load(java.lang.Long)
     */
    public org.openuss.lecture.University load(java.lang.Long id)
    {
        return (org.openuss.lecture.University)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.University> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.lecture.UniversityImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.lecture.UniversityDao#create(org.openuss.lecture.University)
     */
    public org.openuss.lecture.University create(org.openuss.lecture.University university)
    {
        return (org.openuss.lecture.University)this.create(TRANSFORM_NONE, university);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#create(int transform, org.openuss.lecture.University)
     */
    public java.lang.Object create(final int transform, final org.openuss.lecture.University university)
    {
        if (university == null)
        {
            throw new IllegalArgumentException(
                "University.create - 'university' can not be null");
        }
        this.getHibernateTemplate().save(university);
        return this.transformEntity(transform, university);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#create(java.util.Collection<org.openuss.lecture.University>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.University> create(final java.util.Collection<org.openuss.lecture.University> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#create(int, java.util.Collection<org.openuss.lecture.University>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.lecture.University> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "University.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.lecture.University)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.lecture.UniversityDao#update(org.openuss.lecture.University)
     */
    public void update(org.openuss.lecture.University university)
    {
        if (university == null)
        {
            throw new IllegalArgumentException(
                "University.update - 'university' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(university);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(university);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.lecture.UniversityDao#update(java.util.Collection<org.openuss.lecture.University>)
     */
    public void update(final java.util.Collection<org.openuss.lecture.University> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "University.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.lecture.University)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#remove(org.openuss.lecture.University)
     */
    public void remove(org.openuss.lecture.University university)
    {
        if (university == null)
        {
            throw new IllegalArgumentException(
                "University.remove - 'university' can not be null");
        }
        this.getHibernateTemplate().delete(university);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "University.remove - 'id can not be null");
        }
        org.openuss.lecture.University entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.lecture.UniversityDao#remove(java.util.Collection<org.openuss.lecture.University>)
     */
    public void remove(java.util.Collection<org.openuss.lecture.University> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "University.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.lecture.UniversityDao#findByEnabled(boolean)
     */
    public java.util.List findByEnabled(boolean enabled)
    {
        return this.findByEnabled(TRANSFORM_NONE, enabled);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#findByEnabled(java.lang.String, boolean)
     */
    public java.util.List findByEnabled(final java.lang.String queryString, final boolean enabled)
    {
        return this.findByEnabled(TRANSFORM_NONE, queryString, enabled);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#findByEnabled(int, boolean)
     */
    public java.util.List findByEnabled(final int transform, final boolean enabled)
    {
        return this.findByEnabled(transform, "from org.openuss.lecture.University as f where f.enabled = :enabled order by name", enabled);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#findByEnabled(int, java.lang.String, boolean)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByEnabled(final int transform, final java.lang.String queryString, final boolean enabled)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("enabled", new java.lang.Boolean(enabled));
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
     * @see org.openuss.lecture.UniversityDao#findByTypeAndEnabled(org.openuss.lecture.UniversityType, boolean)
     */
    public java.util.List findByTypeAndEnabled(org.openuss.lecture.UniversityType universityType, boolean enabled)
    {
        return this.findByTypeAndEnabled(TRANSFORM_NONE, universityType, enabled);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#findByTypeAndEnabled(java.lang.String, org.openuss.lecture.UniversityType, boolean)
     */
    public java.util.List findByTypeAndEnabled(final java.lang.String queryString, final org.openuss.lecture.UniversityType universityType, final boolean enabled)
    {
        return this.findByTypeAndEnabled(TRANSFORM_NONE, queryString, universityType, enabled);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#findByTypeAndEnabled(int, org.openuss.lecture.UniversityType, boolean)
     */
    public java.util.List findByTypeAndEnabled(final int transform, final org.openuss.lecture.UniversityType universityType, final boolean enabled)
    {
        return this.findByTypeAndEnabled(transform, "from org.openuss.lecture.University as f where f.enabled = :enabled and (f.universityType = :universityType) order by name", universityType, enabled);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#findByTypeAndEnabled(int, java.lang.String, org.openuss.lecture.UniversityType, boolean)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByTypeAndEnabled(final int transform, final java.lang.String queryString, final org.openuss.lecture.UniversityType universityType, final boolean enabled)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("universityType", universityType.getValue());
            queryObject.setParameter("enabled", new java.lang.Boolean(enabled));
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
     * @see org.openuss.lecture.UniversityDao#findByShortcut(java.lang.String)
     */
    public org.openuss.lecture.University findByShortcut(java.lang.String shortcut)
    {
        return (org.openuss.lecture.University)this.findByShortcut(TRANSFORM_NONE, shortcut);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#findByShortcut(java.lang.String, java.lang.String)
     */
    public org.openuss.lecture.University findByShortcut(final java.lang.String queryString, final java.lang.String shortcut)
    {
        return (org.openuss.lecture.University)this.findByShortcut(TRANSFORM_NONE, queryString, shortcut);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#findByShortcut(int, java.lang.String)
     */
    public java.lang.Object findByShortcut(final int transform, final java.lang.String shortcut)
    {
        return this.findByShortcut(transform, "from org.openuss.lecture.University as f where f.shortcut = :shortcut", shortcut);
    }

    /**
     * @see org.openuss.lecture.UniversityDao#findByShortcut(int, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByShortcut(final int transform, final java.lang.String queryString, final java.lang.String shortcut)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("shortcut", shortcut);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.lecture.University"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.lecture.University)result);
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
     * flag is set to one of the constants defined in <code>org.openuss.lecture.UniversityDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.lecture.University} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.lecture.UniversityInfo} - {@link TRANSFORM_UNIVERSITYINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.lecture.UniversityDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.lecture.University entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_UNIVERSITYINFO :
                    target = toUniversityInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.lecture.University)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.lecture.UniversityDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.lecture.University)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_UNIVERSITYINFO :
                toUniversityInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.lecture.UniversityDao#toUniversityInfoCollection(java.util.Collection)
     */
    public final void toUniversityInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, UNIVERSITYINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link UniversityDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.lecture.UniversityDao#toUniversityInfo(org.openuss.lecture.University)
     */
    protected org.openuss.lecture.UniversityInfo toUniversityInfo(java.lang.Object[] row)
    {
        org.openuss.lecture.UniversityInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.lecture.University)
                {
                    target = this.toUniversityInfo((org.openuss.lecture.University)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.lecture.UniversityInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer UNIVERSITYINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.lecture.University)
                {
                    result = toUniversityInfo((org.openuss.lecture.University)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toUniversityInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.lecture.UniversityDao#universityInfoToEntityCollection(java.util.Collection)
     */
    public final void universityInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.lecture.UniversityInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, UniversityInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer UniversityInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return universityInfoToEntity((org.openuss.lecture.UniversityInfo)input);
            }
        };

    /**
     * @see org.openuss.lecture.UniversityDao#toUniversityInfo(org.openuss.lecture.University, org.openuss.lecture.UniversityInfo)
     */
    public void toUniversityInfo(
        org.openuss.lecture.University source,
        org.openuss.lecture.UniversityInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setShortName(source.getShortName());
        target.setDescription(source.getDescription());
        target.setShortcut(source.getShortcut());
        target.setUniversityType(source.getUniversityType());
        target.setOwnerName(source.getOwnerName());
        target.setAddress(source.getAddress());
        target.setPostcode(source.getPostcode());
        target.setCity(source.getCity());
        target.setCountry(source.getCountry());
        target.setTelephone(source.getTelephone());
        target.setTelefax(source.getTelefax());
        target.setWebsite(source.getWebsite());
        target.setEmail(source.getEmail());
        target.setLocale(source.getLocale());
        target.setTheme(source.getTheme());
        target.setImageId(source.getImageId());
        target.setEnabled(source.isEnabled());
    }

    /**
     * @see org.openuss.lecture.UniversityDao#toUniversityInfo(org.openuss.lecture.University)
     */
    public org.openuss.lecture.UniversityInfo toUniversityInfo(final org.openuss.lecture.University entity)
    {
        final org.openuss.lecture.UniversityInfo target = new org.openuss.lecture.UniversityInfo();
        this.toUniversityInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.lecture.UniversityDao#universityInfoToEntity(org.openuss.lecture.UniversityInfo, org.openuss.lecture.University)
     */
    public void universityInfoToEntity(
        org.openuss.lecture.UniversityInfo source,
        org.openuss.lecture.University target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getUniversityType() != null)
        {
            target.setUniversityType(source.getUniversityType());
        }
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
        if (copyIfNull || source.getShortName() != null)
        {
            target.setShortName(source.getShortName());
        }
        if (copyIfNull || source.getShortcut() != null)
        {
            target.setShortcut(source.getShortcut());
        }
        if (copyIfNull || source.getDescription() != null)
        {
            target.setDescription(source.getDescription());
        }
        if (copyIfNull || source.getOwnerName() != null)
        {
            target.setOwnerName(source.getOwnerName());
        }
        if (copyIfNull || source.getAddress() != null)
        {
            target.setAddress(source.getAddress());
        }
        if (copyIfNull || source.getPostcode() != null)
        {
            target.setPostcode(source.getPostcode());
        }
        if (copyIfNull || source.getCity() != null)
        {
            target.setCity(source.getCity());
        }
        if (copyIfNull || source.getCountry() != null)
        {
            target.setCountry(source.getCountry());
        }
        if (copyIfNull || source.getTelephone() != null)
        {
            target.setTelephone(source.getTelephone());
        }
        if (copyIfNull || source.getTelefax() != null)
        {
            target.setTelefax(source.getTelefax());
        }
        if (copyIfNull || source.getWebsite() != null)
        {
            target.setWebsite(source.getWebsite());
        }
        if (copyIfNull || source.getEmail() != null)
        {
            target.setEmail(source.getEmail());
        }
        if (copyIfNull || source.getLocale() != null)
        {
            target.setLocale(source.getLocale());
        }
        if (copyIfNull || source.getTheme() != null)
        {
            target.setTheme(source.getTheme());
        }
        if (copyIfNull || source.getImageId() != null)
        {
            target.setImageId(source.getImageId());
        }
	    target.setEnabled(source.isEnabled());
    }
    
}