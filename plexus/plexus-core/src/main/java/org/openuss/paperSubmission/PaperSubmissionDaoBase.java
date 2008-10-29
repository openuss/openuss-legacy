package org.openuss.paperSubmission;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.paperSubmission.PaperSubmission</code>.
 * </p>
 *
 * @see org.openuss.paperSubmission.PaperSubmission
 */
public abstract class PaperSubmissionDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.paperSubmission.PaperSubmissionDao
{


    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "PaperSubmission.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.paperSubmission.PaperSubmissionImpl.class, id);
        return transformEntity(transform, (org.openuss.paperSubmission.PaperSubmission)entity);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#load(java.lang.Long)
     */
    public org.openuss.paperSubmission.PaperSubmission load(java.lang.Long id)
    {
        return (org.openuss.paperSubmission.PaperSubmission)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.paperSubmission.PaperSubmission> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.paperSubmission.PaperSubmissionImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#create(org.openuss.paperSubmission.PaperSubmission)
     */
    public org.openuss.paperSubmission.PaperSubmission create(org.openuss.paperSubmission.PaperSubmission paperSubmission)
    {
        return (org.openuss.paperSubmission.PaperSubmission)this.create(TRANSFORM_NONE, paperSubmission);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#create(int transform, org.openuss.paperSubmission.PaperSubmission)
     */
    public java.lang.Object create(final int transform, final org.openuss.paperSubmission.PaperSubmission paperSubmission)
    {
        if (paperSubmission == null)
        {
            throw new IllegalArgumentException(
                "PaperSubmission.create - 'paperSubmission' can not be null");
        }
        this.getHibernateTemplate().save(paperSubmission);
        return this.transformEntity(transform, paperSubmission);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#create(java.util.Collection<org.openuss.paperSubmission.PaperSubmission>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.paperSubmission.PaperSubmission> create(final java.util.Collection<org.openuss.paperSubmission.PaperSubmission> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#create(int, java.util.Collection<org.openuss.paperSubmission.PaperSubmission>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.paperSubmission.PaperSubmission> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "PaperSubmission.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.paperSubmission.PaperSubmission)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#update(org.openuss.paperSubmission.PaperSubmission)
     */
    public void update(org.openuss.paperSubmission.PaperSubmission paperSubmission)
    {
        if (paperSubmission == null)
        {
            throw new IllegalArgumentException(
                "PaperSubmission.update - 'paperSubmission' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(paperSubmission);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(paperSubmission);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#update(java.util.Collection<org.openuss.paperSubmission.PaperSubmission>)
     */
    public void update(final java.util.Collection<org.openuss.paperSubmission.PaperSubmission> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "PaperSubmission.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.paperSubmission.PaperSubmission)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#remove(org.openuss.paperSubmission.PaperSubmission)
     */
    public void remove(org.openuss.paperSubmission.PaperSubmission paperSubmission)
    {
        if (paperSubmission == null)
        {
            throw new IllegalArgumentException(
                "PaperSubmission.remove - 'paperSubmission' can not be null");
        }
        this.getHibernateTemplate().delete(paperSubmission);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "PaperSubmission.remove - 'id can not be null");
        }
        org.openuss.paperSubmission.PaperSubmission entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#remove(java.util.Collection<org.openuss.paperSubmission.PaperSubmission>)
     */
    public void remove(java.util.Collection<org.openuss.paperSubmission.PaperSubmission> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "PaperSubmission.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByExam(org.openuss.paperSubmission.Exam)
     */
    public java.util.List findByExam(org.openuss.paperSubmission.Exam exam)
    {
        return this.findByExam(TRANSFORM_NONE, exam);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByExam(java.lang.String, org.openuss.paperSubmission.Exam)
     */
    public java.util.List findByExam(final java.lang.String queryString, final org.openuss.paperSubmission.Exam exam)
    {
        return this.findByExam(TRANSFORM_NONE, queryString, exam);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByExam(int, org.openuss.paperSubmission.Exam)
     */
    public java.util.List findByExam(final int transform, final org.openuss.paperSubmission.Exam exam)
    {
        return this.findByExam(transform, "from org.openuss.paperSubmission.PaperSubmission as paperSubmission where paperSubmission.exam = ?", exam);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByExam(int, java.lang.String, org.openuss.paperSubmission.Exam)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByExam(final int transform, final java.lang.String queryString, final org.openuss.paperSubmission.Exam exam)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, exam);
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
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByExamAndUser(org.openuss.paperSubmission.Exam, org.openuss.security.User)
     */
    public java.util.List findByExamAndUser(org.openuss.paperSubmission.Exam exam, org.openuss.security.User sender)
    {
        return this.findByExamAndUser(TRANSFORM_NONE, exam, sender);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByExamAndUser(java.lang.String, org.openuss.paperSubmission.Exam, org.openuss.security.User)
     */
    public java.util.List findByExamAndUser(final java.lang.String queryString, final org.openuss.paperSubmission.Exam exam, final org.openuss.security.User sender)
    {
        return this.findByExamAndUser(TRANSFORM_NONE, queryString, exam, sender);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByExamAndUser(int, org.openuss.paperSubmission.Exam, org.openuss.security.User)
     */
    public java.util.List findByExamAndUser(final int transform, final org.openuss.paperSubmission.Exam exam, final org.openuss.security.User sender)
    {
        return this.findByExamAndUser(transform, "from org.openuss.paperSubmission.PaperSubmission as paperSubmission where paperSubmission.exam = ? and paperSubmission.sender = ?", exam, sender);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByExamAndUser(int, java.lang.String, org.openuss.paperSubmission.Exam, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByExamAndUser(final int transform, final java.lang.String queryString, final org.openuss.paperSubmission.Exam exam, final org.openuss.security.User sender)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, exam);
            queryObject.setParameter(1, sender);
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
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByUser(org.openuss.security.User)
     */
    public java.util.List findByUser(org.openuss.security.User sender)
    {
        return this.findByUser(TRANSFORM_NONE, sender);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByUser(java.lang.String, org.openuss.security.User)
     */
    public java.util.List findByUser(final java.lang.String queryString, final org.openuss.security.User sender)
    {
        return this.findByUser(TRANSFORM_NONE, queryString, sender);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByUser(int, org.openuss.security.User)
     */
    public java.util.List findByUser(final int transform, final org.openuss.security.User sender)
    {
        return this.findByUser(transform, "from org.openuss.paperSubmission.PaperSubmission as paperSubmission where paperSubmission.sender = ?", sender);
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#findByUser(int, java.lang.String, org.openuss.security.User)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByUser(final int transform, final java.lang.String queryString, final org.openuss.security.User sender)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, sender);
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
     * flag is set to one of the constants defined in <code>org.openuss.paperSubmission.PaperSubmissionDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.paperSubmission.PaperSubmission} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.paperSubmission.PaperSubmissionInfo} - {@link TRANSFORM_PAPERSUBMISSIONINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.paperSubmission.PaperSubmissionDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.paperSubmission.PaperSubmission entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_PAPERSUBMISSIONINFO :
                    target = toPaperSubmissionInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.paperSubmission.PaperSubmission)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.paperSubmission.PaperSubmissionDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.paperSubmission.PaperSubmission)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_PAPERSUBMISSIONINFO :
                toPaperSubmissionInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#toPaperSubmissionInfoCollection(java.util.Collection)
     */
    public final void toPaperSubmissionInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, PAPERSUBMISSIONINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link PaperSubmissionDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.paperSubmission.PaperSubmissionDao#toPaperSubmissionInfo(org.openuss.paperSubmission.PaperSubmission)
     */
    protected org.openuss.paperSubmission.PaperSubmissionInfo toPaperSubmissionInfo(java.lang.Object[] row)
    {
        org.openuss.paperSubmission.PaperSubmissionInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.paperSubmission.PaperSubmission)
                {
                    target = this.toPaperSubmissionInfo((org.openuss.paperSubmission.PaperSubmission)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.paperSubmission.PaperSubmissionInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer PAPERSUBMISSIONINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.paperSubmission.PaperSubmission)
                {
                    result = toPaperSubmissionInfo((org.openuss.paperSubmission.PaperSubmission)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toPaperSubmissionInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#paperSubmissionInfoToEntityCollection(java.util.Collection)
     */
    public final void paperSubmissionInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.paperSubmission.PaperSubmissionInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, PaperSubmissionInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer PaperSubmissionInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return paperSubmissionInfoToEntity((org.openuss.paperSubmission.PaperSubmissionInfo)input);
            }
        };

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#toPaperSubmissionInfo(org.openuss.paperSubmission.PaperSubmission, org.openuss.paperSubmission.PaperSubmissionInfo)
     */
    public void toPaperSubmissionInfo(
        org.openuss.paperSubmission.PaperSubmission source,
        org.openuss.paperSubmission.PaperSubmissionInfo target)
    {
        target.setId(source.getId());
        target.setDeliverDate(source.getDeliverDate());
        target.setComment(source.getComment());
    }

    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#toPaperSubmissionInfo(org.openuss.paperSubmission.PaperSubmission)
     */
    public org.openuss.paperSubmission.PaperSubmissionInfo toPaperSubmissionInfo(final org.openuss.paperSubmission.PaperSubmission entity)
    {
        final org.openuss.paperSubmission.PaperSubmissionInfo target = new org.openuss.paperSubmission.PaperSubmissionInfo();
        this.toPaperSubmissionInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#paperSubmissionInfoToEntity(org.openuss.paperSubmission.PaperSubmissionInfo, org.openuss.paperSubmission.PaperSubmission)
     */
    public void paperSubmissionInfoToEntity(
        org.openuss.paperSubmission.PaperSubmissionInfo source,
        org.openuss.paperSubmission.PaperSubmission target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getDeliverDate() != null)
        {
            target.setDeliverDate(source.getDeliverDate());
        }
        if (copyIfNull || source.getComment() != null)
        {
            target.setComment(source.getComment());
        }
    }
    
}