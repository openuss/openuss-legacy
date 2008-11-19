package org.openuss.paperSubmission;

/**
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.paperSubmission.Exam</code>.
 *
 * @see org.openuss.paperSubmission.Exam
 */
public abstract class ExamDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.paperSubmission.ExamDao
{


    /**
     * @see org.openuss.paperSubmission.ExamDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Exam.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.paperSubmission.ExamImpl.class, id);
        return transformEntity(transform, (org.openuss.paperSubmission.Exam)entity);
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#load(java.lang.Long)
     */
    public org.openuss.paperSubmission.Exam load(java.lang.Long id)
    {
        return (org.openuss.paperSubmission.Exam)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.paperSubmission.Exam> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.paperSubmission.ExamImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.paperSubmission.ExamDao#create(org.openuss.paperSubmission.Exam)
     */
    public org.openuss.paperSubmission.Exam create(org.openuss.paperSubmission.Exam exam)
    {
        return (org.openuss.paperSubmission.Exam)this.create(TRANSFORM_NONE, exam);
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#create(int transform, org.openuss.paperSubmission.Exam)
     */
    public java.lang.Object create(final int transform, final org.openuss.paperSubmission.Exam exam)
    {
        if (exam == null)
        {
            throw new IllegalArgumentException(
                "Exam.create - 'exam' can not be null");
        }
        this.getHibernateTemplate().save(exam);
        return this.transformEntity(transform, exam);
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#create(java.util.Collection<org.openuss.paperSubmission.Exam>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.paperSubmission.Exam> create(final java.util.Collection<org.openuss.paperSubmission.Exam> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#create(int, java.util.Collection<org.openuss.paperSubmission.Exam>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.paperSubmission.Exam> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Exam.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.paperSubmission.Exam)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#update(org.openuss.paperSubmission.Exam)
     */
    public void update(org.openuss.paperSubmission.Exam exam)
    {
        if (exam == null)
        {
            throw new IllegalArgumentException(
                "Exam.update - 'exam' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(exam);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(exam);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#update(java.util.Collection<org.openuss.paperSubmission.Exam>)
     */
    public void update(final java.util.Collection<org.openuss.paperSubmission.Exam> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Exam.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.paperSubmission.Exam)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#remove(org.openuss.paperSubmission.Exam)
     */
    public void remove(org.openuss.paperSubmission.Exam exam)
    {
        if (exam == null)
        {
            throw new IllegalArgumentException(
                "Exam.remove - 'exam' can not be null");
        }
        this.getHibernateTemplate().delete(exam);
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Exam.remove - 'id can not be null");
        }
        org.openuss.paperSubmission.Exam entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#remove(java.util.Collection<org.openuss.paperSubmission.Exam>)
     */
    public void remove(java.util.Collection<org.openuss.paperSubmission.Exam> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Exam.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.paperSubmission.ExamDao#findByDomainId(java.lang.Long)
     */
    public java.util.List findByDomainId(java.lang.Long domainId)
    {
        return this.findByDomainId(TRANSFORM_NONE, domainId);
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#findByDomainId(java.lang.String, java.lang.Long)
     */
    public java.util.List findByDomainId(final java.lang.String queryString, final java.lang.Long domainId)
    {
        return this.findByDomainId(TRANSFORM_NONE, queryString, domainId);
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#findByDomainId(int, java.lang.Long)
     */
    public java.util.List findByDomainId(final int transform, final java.lang.Long domainId)
    {
        return this.findByDomainId(transform, "from org.openuss.paperSubmission.Exam as exam where exam.domainId = ?", domainId);
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#findByDomainId(int, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByDomainId(final int transform, final java.lang.String queryString, final java.lang.Long domainId)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, domainId);
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
     * flag is set to one of the constants defined in <code>org.openuss.paperSubmission.ExamDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.paperSubmission.Exam} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.paperSubmission.ExamInfo} - {@link TRANSFORM_EXAMINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.paperSubmission.ExamDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.paperSubmission.Exam entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_EXAMINFO :
                    target = toExamInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.paperSubmission.Exam)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.paperSubmission.ExamDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.paperSubmission.Exam)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_EXAMINFO :
                toExamInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#toExamInfoCollection(java.util.Collection)
     */
    public final void toExamInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, EXAMINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link ExamDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.paperSubmission.ExamDao#toExamInfo(org.openuss.paperSubmission.Exam)
     */
    protected org.openuss.paperSubmission.ExamInfo toExamInfo(java.lang.Object[] row)
    {
        org.openuss.paperSubmission.ExamInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.paperSubmission.Exam)
                {
                    target = this.toExamInfo((org.openuss.paperSubmission.Exam)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.paperSubmission.ExamInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer EXAMINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.paperSubmission.Exam)
                {
                    result = toExamInfo((org.openuss.paperSubmission.Exam)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toExamInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.paperSubmission.ExamDao#examInfoToEntityCollection(java.util.Collection)
     */
    public final void examInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.paperSubmission.ExamInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, ExamInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer ExamInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return examInfoToEntity((org.openuss.paperSubmission.ExamInfo)input);
            }
        };

    /**
     * @see org.openuss.paperSubmission.ExamDao#toExamInfo(org.openuss.paperSubmission.Exam, org.openuss.paperSubmission.ExamInfo)
     */
    public void toExamInfo(
        org.openuss.paperSubmission.Exam source,
        org.openuss.paperSubmission.ExamInfo target)
    {
        target.setId(source.getId());
        target.setDomainId(source.getDomainId());
        target.setName(source.getName());
        target.setDeadline(source.getDeadline());
        target.setDescription(source.getDescription());
    }

    /**
     * @see org.openuss.paperSubmission.ExamDao#toExamInfo(org.openuss.paperSubmission.Exam)
     */
    public org.openuss.paperSubmission.ExamInfo toExamInfo(final org.openuss.paperSubmission.Exam entity)
    {
        final org.openuss.paperSubmission.ExamInfo target = new org.openuss.paperSubmission.ExamInfo();
        this.toExamInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.paperSubmission.ExamDao#examInfoToEntity(org.openuss.paperSubmission.ExamInfo, org.openuss.paperSubmission.Exam)
     */
    public void examInfoToEntity(
        org.openuss.paperSubmission.ExamInfo source,
        org.openuss.paperSubmission.Exam target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getDomainId() != null)
        {
            target.setDomainId(source.getDomainId());
        }
        if (copyIfNull || source.getName() != null)
        {
            target.setName(source.getName());
        }
        if (copyIfNull || source.getDeadline() != null)
        {
            target.setDeadline(source.getDeadline());
        }
        if (copyIfNull || source.getDescription() != null)
        {
            target.setDescription(source.getDescription());
        }
    }
    
}