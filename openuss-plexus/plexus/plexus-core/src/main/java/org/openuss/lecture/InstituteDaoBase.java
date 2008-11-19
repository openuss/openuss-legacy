package org.openuss.lecture;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.lecture.Institute</code>.
 * </p>
 *
 * @see org.openuss.lecture.Institute
 */
public abstract class InstituteDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.lecture.InstituteDao
{

    private org.openuss.lecture.DepartmentDao departmentDao;

    /**
     * Sets the reference to <code>departmentDao</code>.
     */
    public void setDepartmentDao(org.openuss.lecture.DepartmentDao departmentDao)
    {
        this.departmentDao = departmentDao;
    }

    /**
     * Gets the reference to <code>departmentDao</code>.
     */
    protected org.openuss.lecture.DepartmentDao getDepartmentDao()
    {
        return this.departmentDao;
    }


    /**
     * @see org.openuss.lecture.InstituteDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Institute.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.lecture.InstituteImpl.class, id);
        return transformEntity(transform, (org.openuss.lecture.Institute)entity);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#load(java.lang.Long)
     */
    public org.openuss.lecture.Institute load(java.lang.Long id)
    {
        return (org.openuss.lecture.Institute)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.Institute> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.lecture.InstituteImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.lecture.InstituteDao#create(org.openuss.lecture.Institute)
     */
    public org.openuss.lecture.Institute create(org.openuss.lecture.Institute institute)
    {
        return (org.openuss.lecture.Institute)this.create(TRANSFORM_NONE, institute);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#create(int transform, org.openuss.lecture.Institute)
     */
    public java.lang.Object create(final int transform, final org.openuss.lecture.Institute institute)
    {
        if (institute == null)
        {
            throw new IllegalArgumentException(
                "Institute.create - 'institute' can not be null");
        }
        this.getHibernateTemplate().save(institute);
        return this.transformEntity(transform, institute);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#create(java.util.Collection<org.openuss.lecture.Institute>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.lecture.Institute> create(final java.util.Collection<org.openuss.lecture.Institute> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#create(int, java.util.Collection<org.openuss.lecture.Institute>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.lecture.Institute> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Institute.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.lecture.Institute)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.lecture.InstituteDao#update(org.openuss.lecture.Institute)
     */
    public void update(org.openuss.lecture.Institute institute)
    {
        if (institute == null)
        {
            throw new IllegalArgumentException(
                "Institute.update - 'institute' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(institute);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(institute);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.lecture.InstituteDao#update(java.util.Collection<org.openuss.lecture.Institute>)
     */
    public void update(final java.util.Collection<org.openuss.lecture.Institute> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Institute.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.lecture.Institute)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#remove(org.openuss.lecture.Institute)
     */
    public void remove(org.openuss.lecture.Institute institute)
    {
        if (institute == null)
        {
            throw new IllegalArgumentException(
                "Institute.remove - 'institute' can not be null");
        }
        this.getHibernateTemplate().delete(institute);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Institute.remove - 'id can not be null");
        }
        org.openuss.lecture.Institute entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.lecture.InstituteDao#remove(java.util.Collection<org.openuss.lecture.Institute>)
     */
    public void remove(java.util.Collection<org.openuss.lecture.Institute> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Institute.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.lecture.InstituteDao#findByShortcut(java.lang.String)
     */
    public java.util.List findByShortcut(java.lang.String shortcut)
    {
        return this.findByShortcut(TRANSFORM_NONE, shortcut);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#findByShortcut(java.lang.String, java.lang.String)
     */
    public java.util.List findByShortcut(final java.lang.String queryString, final java.lang.String shortcut)
    {
        return this.findByShortcut(TRANSFORM_NONE, queryString, shortcut);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#findByShortcut(int, java.lang.String)
     */
    public java.util.List findByShortcut(final int transform, final java.lang.String shortcut)
    {
        return this.findByShortcut(transform, "from org.openuss.lecture.Institute as f where f.shortcut = :shortcut", shortcut);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#findByShortcut(int, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByShortcut(final int transform, final java.lang.String queryString, final java.lang.String shortcut)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("shortcut", shortcut);
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
     * @see org.openuss.lecture.InstituteDao#findByEnabled(java.lang.Boolean)
     */
    public java.util.List findByEnabled(java.lang.Boolean enabled)
    {
        return this.findByEnabled(TRANSFORM_NONE, enabled);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#findByEnabled(java.lang.String, java.lang.Boolean)
     */
    public java.util.List findByEnabled(final java.lang.String queryString, final java.lang.Boolean enabled)
    {
        return this.findByEnabled(TRANSFORM_NONE, queryString, enabled);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#findByEnabled(int, java.lang.Boolean)
     */
    public java.util.List findByEnabled(final int transform, final java.lang.Boolean enabled)
    {
        return this.findByEnabled(transform, "from org.openuss.lecture.Institute as f where f.enabled = :enabled order by name", enabled);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#findByEnabled(int, java.lang.String, java.lang.Boolean)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByEnabled(final int transform, final java.lang.String queryString, final java.lang.Boolean enabled)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("enabled", enabled);
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
     * @see org.openuss.lecture.InstituteDao#findByDepartmentAndEnabled(org.openuss.lecture.Department, java.lang.Boolean)
     */
    public java.util.List findByDepartmentAndEnabled(org.openuss.lecture.Department department, java.lang.Boolean enabled)
    {
        return this.findByDepartmentAndEnabled(TRANSFORM_NONE, department, enabled);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#findByDepartmentAndEnabled(java.lang.String, org.openuss.lecture.Department, java.lang.Boolean)
     */
    public java.util.List findByDepartmentAndEnabled(final java.lang.String queryString, final org.openuss.lecture.Department department, final java.lang.Boolean enabled)
    {
        return this.findByDepartmentAndEnabled(TRANSFORM_NONE, queryString, department, enabled);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#findByDepartmentAndEnabled(int, org.openuss.lecture.Department, java.lang.Boolean)
     */
    public java.util.List findByDepartmentAndEnabled(final int transform, final org.openuss.lecture.Department department, final java.lang.Boolean enabled)
    {
        return this.findByDepartmentAndEnabled(transform, "from org.openuss.lecture.Institute as institute where institute.department = ? and institute.enabled = ?", department, enabled);
    }

    /**
     * @see org.openuss.lecture.InstituteDao#findByDepartmentAndEnabled(int, java.lang.String, org.openuss.lecture.Department, java.lang.Boolean)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByDepartmentAndEnabled(final int transform, final java.lang.String queryString, final org.openuss.lecture.Department department, final java.lang.Boolean enabled)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, department);
            queryObject.setParameter(1, enabled);
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
     * flag is set to one of the constants defined in <code>org.openuss.lecture.InstituteDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.lecture.Institute} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.lecture.InstituteSecurity} - {@link TRANSFORM_INSTITUTESECURITY}</li>
     *   <li>{@link org.openuss.lecture.InstituteInfo} - {@link TRANSFORM_INSTITUTEINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.lecture.InstituteDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.lecture.Institute entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_INSTITUTESECURITY :
                    target = toInstituteSecurity(entity);
                    break;
                case TRANSFORM_INSTITUTEINFO :
                    target = toInstituteInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.lecture.Institute)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.lecture.InstituteDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.lecture.Institute)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_INSTITUTESECURITY :
                toInstituteSecurityCollection(entities);
                break;
            case TRANSFORM_INSTITUTEINFO :
                toInstituteInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.lecture.InstituteDao#toInstituteSecurityCollection(java.util.Collection)
     */
    public final void toInstituteSecurityCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, INSTITUTESECURITY_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link InstituteDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.lecture.InstituteDao#toInstituteSecurity(org.openuss.lecture.Institute)
     */
    protected org.openuss.lecture.InstituteSecurity toInstituteSecurity(java.lang.Object[] row)
    {
        org.openuss.lecture.InstituteSecurity target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.lecture.Institute)
                {
                    target = this.toInstituteSecurity((org.openuss.lecture.Institute)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.lecture.InstituteSecurity}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer INSTITUTESECURITY_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.lecture.Institute)
                {
                    result = toInstituteSecurity((org.openuss.lecture.Institute)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toInstituteSecurity((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.lecture.InstituteDao#instituteSecurityToEntityCollection(java.util.Collection)
     */
    public final void instituteSecurityToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.lecture.InstituteSecurity))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, InstituteSecurityToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer InstituteSecurityToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return instituteSecurityToEntity((org.openuss.lecture.InstituteSecurity)input);
            }
        };

    /**
     * @see org.openuss.lecture.InstituteDao#toInstituteSecurity(org.openuss.lecture.Institute, org.openuss.lecture.InstituteSecurity)
     */
    public void toInstituteSecurity(
        org.openuss.lecture.Institute source,
        org.openuss.lecture.InstituteSecurity target)
    {
    }

    /**
     * @see org.openuss.lecture.InstituteDao#toInstituteSecurity(org.openuss.lecture.Institute)
     */
    public org.openuss.lecture.InstituteSecurity toInstituteSecurity(final org.openuss.lecture.Institute entity)
    {
        final org.openuss.lecture.InstituteSecurity target = new org.openuss.lecture.InstituteSecurity();
        this.toInstituteSecurity(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.lecture.InstituteDao#instituteSecurityToEntity(org.openuss.lecture.InstituteSecurity, org.openuss.lecture.Institute)
     */
    public void instituteSecurityToEntity(
        org.openuss.lecture.InstituteSecurity source,
        org.openuss.lecture.Institute target,
        boolean copyIfNull)
    {
    }
    
    /**
     * @see org.openuss.lecture.InstituteDao#toInstituteInfoCollection(java.util.Collection)
     */
    public final void toInstituteInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, INSTITUTEINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link InstituteDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.lecture.InstituteDao#toInstituteInfo(org.openuss.lecture.Institute)
     */
    protected org.openuss.lecture.InstituteInfo toInstituteInfo(java.lang.Object[] row)
    {
        org.openuss.lecture.InstituteInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.lecture.Institute)
                {
                    target = this.toInstituteInfo((org.openuss.lecture.Institute)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.lecture.InstituteInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer INSTITUTEINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.lecture.Institute)
                {
                    result = toInstituteInfo((org.openuss.lecture.Institute)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toInstituteInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.lecture.InstituteDao#instituteInfoToEntityCollection(java.util.Collection)
     */
    public final void instituteInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.lecture.InstituteInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, InstituteInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer InstituteInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return instituteInfoToEntity((org.openuss.lecture.InstituteInfo)input);
            }
        };

    /**
     * @see org.openuss.lecture.InstituteDao#toInstituteInfo(org.openuss.lecture.Institute, org.openuss.lecture.InstituteInfo)
     */
    public void toInstituteInfo(
        org.openuss.lecture.Institute source,
        org.openuss.lecture.InstituteInfo target)
    {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setShortName(source.getShortName());
        target.setDescription(source.getDescription());
        target.setShortcut(source.getShortcut());
        target.setOwnerName(source.getOwnerName());
        target.setAddress(source.getAddress());
        target.setPostcode(source.getPostcode());
        target.setCity(source.getCity());
        target.setCountry(source.getCountry());
        target.setTelephone(source.getTelephone());
        target.setTelefax(source.getTelefax());
        target.setWebsite(source.getWebsite());
        target.setLocale(source.getLocale());
        target.setEmail(source.getEmail());
        target.setTheme(source.getTheme());
        target.setImageId(source.getImageId());
        target.setEnabled(source.isEnabled());
    }

    /**
     * @see org.openuss.lecture.InstituteDao#toInstituteInfo(org.openuss.lecture.Institute)
     */
    public org.openuss.lecture.InstituteInfo toInstituteInfo(final org.openuss.lecture.Institute entity)
    {
        final org.openuss.lecture.InstituteInfo target = new org.openuss.lecture.InstituteInfo();
        this.toInstituteInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.lecture.InstituteDao#instituteInfoToEntity(org.openuss.lecture.InstituteInfo, org.openuss.lecture.Institute)
     */
    public void instituteInfoToEntity(
        org.openuss.lecture.InstituteInfo source,
        org.openuss.lecture.Institute target,
        boolean copyIfNull)
    {
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