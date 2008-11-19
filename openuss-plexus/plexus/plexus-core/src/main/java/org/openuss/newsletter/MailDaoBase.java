package org.openuss.newsletter;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.newsletter.Mail</code>.
 * </p>
 *
 * @see org.openuss.newsletter.Mail
 */
public abstract class MailDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.newsletter.MailDao
{


    /**
     * @see org.openuss.newsletter.MailDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Mail.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.newsletter.MailImpl.class, id);
        return transformEntity(transform, (org.openuss.newsletter.Mail)entity);
    }

    /**
     * @see org.openuss.newsletter.MailDao#load(java.lang.Long)
     */
    public org.openuss.newsletter.Mail load(java.lang.Long id)
    {
        return (org.openuss.newsletter.Mail)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.newsletter.MailDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.newsletter.Mail> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.newsletter.MailDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.newsletter.MailImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.newsletter.MailDao#create(org.openuss.newsletter.Mail)
     */
    public org.openuss.newsletter.Mail create(org.openuss.newsletter.Mail mail)
    {
        return (org.openuss.newsletter.Mail)this.create(TRANSFORM_NONE, mail);
    }

    /**
     * @see org.openuss.newsletter.MailDao#create(int transform, org.openuss.newsletter.Mail)
     */
    public java.lang.Object create(final int transform, final org.openuss.newsletter.Mail mail)
    {
        if (mail == null)
        {
            throw new IllegalArgumentException(
                "Mail.create - 'mail' can not be null");
        }
        this.getHibernateTemplate().save(mail);
        return this.transformEntity(transform, mail);
    }

    /**
     * @see org.openuss.newsletter.MailDao#create(java.util.Collection<org.openuss.newsletter.Mail>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.newsletter.Mail> create(final java.util.Collection<org.openuss.newsletter.Mail> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.newsletter.MailDao#create(int, java.util.Collection<org.openuss.newsletter.Mail>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.newsletter.Mail> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Mail.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.newsletter.Mail)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.newsletter.MailDao#update(org.openuss.newsletter.Mail)
     */
    public void update(org.openuss.newsletter.Mail mail)
    {
        if (mail == null)
        {
            throw new IllegalArgumentException(
                "Mail.update - 'mail' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(mail);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(mail);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.newsletter.MailDao#update(java.util.Collection<org.openuss.newsletter.Mail>)
     */
    public void update(final java.util.Collection<org.openuss.newsletter.Mail> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Mail.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.newsletter.Mail)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.newsletter.MailDao#remove(org.openuss.newsletter.Mail)
     */
    public void remove(org.openuss.newsletter.Mail mail)
    {
        if (mail == null)
        {
            throw new IllegalArgumentException(
                "Mail.remove - 'mail' can not be null");
        }
        this.getHibernateTemplate().delete(mail);
    }

    /**
     * @see org.openuss.newsletter.MailDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "Mail.remove - 'id can not be null");
        }
        org.openuss.newsletter.Mail entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.newsletter.MailDao#remove(java.util.Collection<org.openuss.newsletter.Mail>)
     */
    public void remove(java.util.Collection<org.openuss.newsletter.Mail> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "Mail.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.newsletter.MailDao#findMailByState(org.openuss.newsletter.MailingStatus)
     */
    public java.util.List findMailByState(org.openuss.newsletter.MailingStatus status)
    {
        return this.findMailByState(TRANSFORM_NONE, status);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByState(java.lang.String, org.openuss.newsletter.MailingStatus)
     */
    public java.util.List findMailByState(final java.lang.String queryString, final org.openuss.newsletter.MailingStatus status)
    {
        return this.findMailByState(TRANSFORM_NONE, queryString, status);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByState(int, org.openuss.newsletter.MailingStatus)
     */
    public java.util.List findMailByState(final int transform, final org.openuss.newsletter.MailingStatus status)
    {
        return this.findMailByState(transform, "from org.openuss.newsletter.Mail as mail where mail.status = ?", status);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByState(int, java.lang.String, org.openuss.newsletter.MailingStatus)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findMailByState(final int transform, final java.lang.String queryString, final org.openuss.newsletter.MailingStatus status)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, status.getValue());
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
     * @see org.openuss.newsletter.MailDao#findMailByNewsletter(org.openuss.newsletter.Newsletter)
     */
    public java.util.List findMailByNewsletter(org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findMailByNewsletter(TRANSFORM_NONE, newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByNewsletter(java.lang.String, org.openuss.newsletter.Newsletter)
     */
    public java.util.List findMailByNewsletter(final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findMailByNewsletter(TRANSFORM_NONE, queryString, newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByNewsletter(int, org.openuss.newsletter.Newsletter)
     */
    public java.util.List findMailByNewsletter(final int transform, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findMailByNewsletter(transform, "from org.openuss.newsletter.Mail as mail where mail.newsletter = ?", newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByNewsletter(int, java.lang.String, org.openuss.newsletter.Newsletter)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findMailByNewsletter(final int transform, final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, newsletter);
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
     * @see org.openuss.newsletter.MailDao#findMailByNewsletterAndStatus(org.openuss.newsletter.Newsletter)
     */
    public java.util.List findMailByNewsletterAndStatus(org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findMailByNewsletterAndStatus(TRANSFORM_NONE, newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByNewsletterAndStatus(java.lang.String, org.openuss.newsletter.Newsletter)
     */
    public java.util.List findMailByNewsletterAndStatus(final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findMailByNewsletterAndStatus(TRANSFORM_NONE, queryString, newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByNewsletterAndStatus(int, org.openuss.newsletter.Newsletter)
     */
    public java.util.List findMailByNewsletterAndStatus(final int transform, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findMailByNewsletterAndStatus(transform, "from org.openuss.newsletter.Mail as m where m.newsletter = :newsletter", newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByNewsletterAndStatus(int, java.lang.String, org.openuss.newsletter.Newsletter)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findMailByNewsletterAndStatus(final int transform, final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("newsletter", newsletter);
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
     * @see org.openuss.newsletter.MailDao#findByNewsletterWithoutDeleted(org.openuss.newsletter.Newsletter)
     */
    public java.util.List findByNewsletterWithoutDeleted(org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findByNewsletterWithoutDeleted(TRANSFORM_NONE, newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findByNewsletterWithoutDeleted(java.lang.String, org.openuss.newsletter.Newsletter)
     */
    public java.util.List findByNewsletterWithoutDeleted(final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findByNewsletterWithoutDeleted(TRANSFORM_NONE, queryString, newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findByNewsletterWithoutDeleted(int, org.openuss.newsletter.Newsletter)
     */
    public java.util.List findByNewsletterWithoutDeleted(final int transform, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findByNewsletterWithoutDeleted(transform, "from org.openuss.newsletter.Mail as m where m.newsletter = :newsletter and m.status = org.openuss.newsletter.MailingStatus.SEND", newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findByNewsletterWithoutDeleted(int, java.lang.String, org.openuss.newsletter.Newsletter)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByNewsletterWithoutDeleted(final int transform, final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("newsletter", newsletter);
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
     * @see org.openuss.newsletter.MailDao#findNotDeletedByStatus(org.openuss.newsletter.Newsletter)
     */
    public java.util.List findNotDeletedByStatus(org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findNotDeletedByStatus(TRANSFORM_NONE, newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findNotDeletedByStatus(java.lang.String, org.openuss.newsletter.Newsletter)
     */
    public java.util.List findNotDeletedByStatus(final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findNotDeletedByStatus(TRANSFORM_NONE, queryString, newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findNotDeletedByStatus(int, org.openuss.newsletter.Newsletter)
     */
    public java.util.List findNotDeletedByStatus(final int transform, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findNotDeletedByStatus(transform, "from org.openuss.newsletter.Mail as m where m.newsletter = :newsletter and m.status not org.openuss.newsletter.MailingStatus.DELETED", newsletter);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findNotDeletedByStatus(int, java.lang.String, org.openuss.newsletter.Newsletter)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findNotDeletedByStatus(final int transform, final java.lang.String queryString, final org.openuss.newsletter.Newsletter newsletter)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("newsletter", newsletter);
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
     * @see org.openuss.newsletter.MailDao#findMailByMessageId(java.lang.Long)
     */
    public org.openuss.newsletter.Mail findMailByMessageId(java.lang.Long messageId)
    {
        return (org.openuss.newsletter.Mail)this.findMailByMessageId(TRANSFORM_NONE, messageId);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByMessageId(java.lang.String, java.lang.Long)
     */
    public org.openuss.newsletter.Mail findMailByMessageId(final java.lang.String queryString, final java.lang.Long messageId)
    {
        return (org.openuss.newsletter.Mail)this.findMailByMessageId(TRANSFORM_NONE, queryString, messageId);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByMessageId(int, java.lang.Long)
     */
    public java.lang.Object findMailByMessageId(final int transform, final java.lang.Long messageId)
    {
        return this.findMailByMessageId(transform, "from org.openuss.newsletter.Mail as mail where mail.messageId = ?", messageId);
    }

    /**
     * @see org.openuss.newsletter.MailDao#findMailByMessageId(int, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findMailByMessageId(final int transform, final java.lang.String queryString, final java.lang.Long messageId)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, messageId);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.newsletter.Mail"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.newsletter.Mail)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.newsletter.MailDao#loadAllMailInfos(org.openuss.newsletter.Newsletter, org.openuss.security.User)
     */
    public java.util.List loadAllMailInfos(final org.openuss.newsletter.Newsletter newsletter, final org.openuss.security.User user)
    {
        try
        {
            return this.handleLoadAllMailInfos(newsletter, user);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.newsletter.MailDao.loadAllMailInfos(org.openuss.newsletter.Newsletter newsletter, org.openuss.security.User user)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #loadAllMailInfos(org.openuss.newsletter.Newsletter, org.openuss.security.User)}
      */
    protected abstract java.util.List handleLoadAllMailInfos(org.openuss.newsletter.Newsletter newsletter, org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * @see org.openuss.newsletter.MailDao#loadSendMailInfos(org.openuss.newsletter.Newsletter, org.openuss.security.User)
     */
    public java.util.List loadSendMailInfos(final org.openuss.newsletter.Newsletter newsletter, final org.openuss.security.User user)
    {
        try
        {
            return this.handleLoadSendMailInfos(newsletter, user);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.newsletter.MailDao.loadSendMailInfos(org.openuss.newsletter.Newsletter newsletter, org.openuss.security.User user)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #loadSendMailInfos(org.openuss.newsletter.Newsletter, org.openuss.security.User)}
      */
    protected abstract java.util.List handleLoadSendMailInfos(org.openuss.newsletter.Newsletter newsletter, org.openuss.security.User user)
        throws java.lang.Exception;

    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.newsletter.MailDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.newsletter.Mail} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.newsletter.MailInfo} - {@link TRANSFORM_MAILINFO}</li>
     *   <li>{@link org.openuss.newsletter.MailDetail} - {@link TRANSFORM_MAILDETAIL}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.newsletter.MailDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.newsletter.Mail entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_MAILINFO :
                    target = toMailInfo(entity);
                    break;
                case TRANSFORM_MAILDETAIL :
                    target = toMailDetail(entity);
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
     * {@link #transformEntity(int,org.openuss.newsletter.Mail)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.newsletter.MailDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.newsletter.Mail)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_MAILINFO :
                toMailInfoCollection(entities);
                break;
            case TRANSFORM_MAILDETAIL :
                toMailDetailCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.newsletter.MailDao#toMailInfoCollection(java.util.Collection)
     */
    public final void toMailInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, MAILINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link MailDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.newsletter.MailDao#toMailInfo(org.openuss.newsletter.Mail)
     */
    protected org.openuss.newsletter.MailInfo toMailInfo(java.lang.Object[] row)
    {
        org.openuss.newsletter.MailInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.newsletter.Mail)
                {
                    target = this.toMailInfo((org.openuss.newsletter.Mail)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.newsletter.MailInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer MAILINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.newsletter.Mail)
                {
                    result = toMailInfo((org.openuss.newsletter.Mail)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toMailInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.newsletter.MailDao#mailInfoToEntityCollection(java.util.Collection)
     */
    public final void mailInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.newsletter.MailInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, MailInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer MailInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return mailInfoToEntity((org.openuss.newsletter.MailInfo)input);
            }
        };

    /**
     * @see org.openuss.newsletter.MailDao#toMailInfo(org.openuss.newsletter.Mail, org.openuss.newsletter.MailInfo)
     */
    public void toMailInfo(
        org.openuss.newsletter.Mail source,
        org.openuss.newsletter.MailInfo target)
    {
        target.setId(source.getId());
        target.setStatus(source.getStatus());
        target.setSubject(source.getSubject());
        target.setSendDate(source.getSendDate());
    }

    /**
     * @see org.openuss.newsletter.MailDao#toMailInfo(org.openuss.newsletter.Mail)
     */
    public org.openuss.newsletter.MailInfo toMailInfo(final org.openuss.newsletter.Mail entity)
    {
        final org.openuss.newsletter.MailInfo target = new org.openuss.newsletter.MailInfo();
        this.toMailInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.newsletter.MailDao#mailInfoToEntity(org.openuss.newsletter.MailInfo, org.openuss.newsletter.Mail)
     */
    public void mailInfoToEntity(
        org.openuss.newsletter.MailInfo source,
        org.openuss.newsletter.Mail target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getStatus() != null)
        {
            target.setStatus(source.getStatus());
        }
        if (copyIfNull || source.getSubject() != null)
        {
            target.setSubject(source.getSubject());
        }
        if (copyIfNull || source.getSendDate() != null)
        {
            target.setSendDate(source.getSendDate());
        }
    }
    
    /**
     * @see org.openuss.newsletter.MailDao#toMailDetailCollection(java.util.Collection)
     */
    public final void toMailDetailCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, MAILDETAIL_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link MailDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.newsletter.MailDao#toMailDetail(org.openuss.newsletter.Mail)
     */
    protected org.openuss.newsletter.MailDetail toMailDetail(java.lang.Object[] row)
    {
        org.openuss.newsletter.MailDetail target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.newsletter.Mail)
                {
                    target = this.toMailDetail((org.openuss.newsletter.Mail)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.newsletter.MailDetail}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer MAILDETAIL_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.newsletter.Mail)
                {
                    result = toMailDetail((org.openuss.newsletter.Mail)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toMailDetail((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.newsletter.MailDao#mailDetailToEntityCollection(java.util.Collection)
     */
    public final void mailDetailToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.newsletter.MailDetail))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, MailDetailToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer MailDetailToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return mailDetailToEntity((org.openuss.newsletter.MailDetail)input);
            }
        };

    /**
     * @see org.openuss.newsletter.MailDao#toMailDetail(org.openuss.newsletter.Mail, org.openuss.newsletter.MailDetail)
     */
    public void toMailDetail(
        org.openuss.newsletter.Mail source,
        org.openuss.newsletter.MailDetail target)
    {
        target.setId(source.getId());
        target.setStatus(source.getStatus());
        target.setSubject(source.getSubject());
        target.setText(source.getText());
        target.setSms(source.isSms());
        target.setSendDate(source.getSendDate());
        target.setCommandId(source.getCommandId());
        target.setMessageId(source.getMessageId());
    }

    /**
     * @see org.openuss.newsletter.MailDao#toMailDetail(org.openuss.newsletter.Mail)
     */
    public org.openuss.newsletter.MailDetail toMailDetail(final org.openuss.newsletter.Mail entity)
    {
        final org.openuss.newsletter.MailDetail target = new org.openuss.newsletter.MailDetail();
        this.toMailDetail(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.newsletter.MailDao#mailDetailToEntity(org.openuss.newsletter.MailDetail, org.openuss.newsletter.Mail)
     */
    public void mailDetailToEntity(
        org.openuss.newsletter.MailDetail source,
        org.openuss.newsletter.Mail target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getStatus() != null)
        {
            target.setStatus(source.getStatus());
        }
        if (copyIfNull || source.getSubject() != null)
        {
            target.setSubject(source.getSubject());
        }
        if (copyIfNull || source.getText() != null)
        {
            target.setText(source.getText());
        }
	    target.setSms(source.isSms());
        if (copyIfNull || source.getSendDate() != null)
        {
            target.setSendDate(source.getSendDate());
        }
        if (copyIfNull || source.getCommandId() != null)
        {
            target.setCommandId(source.getCommandId());
        }
        if (copyIfNull || source.getMessageId() != null)
        {
            target.setMessageId(source.getMessageId());
        }
    }
    
}