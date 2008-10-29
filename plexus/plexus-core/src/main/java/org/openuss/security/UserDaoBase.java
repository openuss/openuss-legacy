
package org.openuss.security;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.security.User</code>.
 * </p>
 *
 * @see org.openuss.security.User
 */
public abstract class UserDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.security.UserDao
{


    /**
     * @see org.openuss.security.UserDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "User.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.security.UserImpl.class, id);
        return transformEntity(transform, (org.openuss.security.User)entity);
    }

    /**
     * @see org.openuss.security.UserDao#load(java.lang.Long)
     */
    public org.openuss.security.User load(java.lang.Long id)
    {
        return (org.openuss.security.User)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.security.UserDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.User> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.security.UserDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.security.UserImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.security.UserDao#create(org.openuss.security.User)
     */
    public org.openuss.security.User create(org.openuss.security.User user)
    {
        return (org.openuss.security.User)this.create(TRANSFORM_NONE, user);
    }

    /**
     * @see org.openuss.security.UserDao#create(int transform, org.openuss.security.User)
     */
    public java.lang.Object create(final int transform, final org.openuss.security.User user)
    {
        if (user == null)
        {
            throw new IllegalArgumentException(
                "User.create - 'user' can not be null");
        }
        this.getHibernateTemplate().save(user);
        return this.transformEntity(transform, user);
    }

    /**
     * @see org.openuss.security.UserDao#create(java.util.Collection<org.openuss.security.User>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.security.User> create(final java.util.Collection<org.openuss.security.User> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.security.UserDao#create(int, java.util.Collection<org.openuss.security.User>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.security.User> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "User.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.security.User)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.security.UserDao#update(org.openuss.security.User)
     */
    public void update(org.openuss.security.User user)
    {
        if (user == null)
        {
            throw new IllegalArgumentException(
                "User.update - 'user' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(user);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(user);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.security.UserDao#update(java.util.Collection<org.openuss.security.User>)
     */
    public void update(final java.util.Collection<org.openuss.security.User> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "User.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.security.User)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.security.UserDao#remove(org.openuss.security.User)
     */
    public void remove(org.openuss.security.User user)
    {
        if (user == null)
        {
            throw new IllegalArgumentException(
                "User.remove - 'user' can not be null");
        }
        this.getHibernateTemplate().delete(user);
    }

    /**
     * @see org.openuss.security.UserDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "User.remove - 'id can not be null");
        }
        org.openuss.security.User entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.security.UserDao#remove(java.util.Collection<org.openuss.security.User>)
     */
    public void remove(java.util.Collection<org.openuss.security.User> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "User.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.security.UserDao#findUserByUsername(java.lang.String)
     */
    public org.openuss.security.User findUserByUsername(java.lang.String username)
    {
        return (org.openuss.security.User)this.findUserByUsername(TRANSFORM_NONE, username);
    }

    /**
     * @see org.openuss.security.UserDao#findUserByUsername(java.lang.String, java.lang.String)
     */
    public org.openuss.security.User findUserByUsername(final java.lang.String queryString, final java.lang.String username)
    {
        return (org.openuss.security.User)this.findUserByUsername(TRANSFORM_NONE, queryString, username);
    }

    /**
     * @see org.openuss.security.UserDao#findUserByUsername(int, java.lang.String)
     */
    public java.lang.Object findUserByUsername(final int transform, final java.lang.String username)
    {
        return this.findUserByUsername(transform, "from org.openuss.security.User as user where user.name = lower(?)", username);
    }

    /**
     * @see org.openuss.security.UserDao#findUserByUsername(int, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findUserByUsername(final int transform, final java.lang.String queryString, final java.lang.String username)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, username);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.security.User"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.security.User)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.security.UserDao#findUserByEmail(java.lang.String)
     */
    public org.openuss.security.User findUserByEmail(java.lang.String email)
    {
        return (org.openuss.security.User)this.findUserByEmail(TRANSFORM_NONE, email);
    }

    /**
     * @see org.openuss.security.UserDao#findUserByEmail(java.lang.String, java.lang.String)
     */
    public org.openuss.security.User findUserByEmail(final java.lang.String queryString, final java.lang.String email)
    {
        return (org.openuss.security.User)this.findUserByEmail(TRANSFORM_NONE, queryString, email);
    }

    /**
     * @see org.openuss.security.UserDao#findUserByEmail(int, java.lang.String)
     */
    public java.lang.Object findUserByEmail(final int transform, final java.lang.String email)
    {
        return this.findUserByEmail(transform, "from org.openuss.security.User as u where u.email = lower(?)", email);
    }

    /**
     * @see org.openuss.security.UserDao#findUserByEmail(int, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findUserByEmail(final int transform, final java.lang.String queryString, final java.lang.String email)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, email);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'org.openuss.security.User"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.security.User)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.security.UserDao#getPassword(java.lang.Long)
     */
    public java.lang.String getPassword(java.lang.Long id)
    {
        return (java.lang.String)this.getPassword(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.security.UserDao#getPassword(java.lang.String, java.lang.Long)
     */
    public java.lang.String getPassword(final java.lang.String queryString, final java.lang.Long id)
    {
        return (java.lang.String)this.getPassword(TRANSFORM_NONE, queryString, id);
    }

    /**
     * @see org.openuss.security.UserDao#getPassword(int, java.lang.Long)
     */
    public java.lang.Object getPassword(final int transform, final java.lang.Long id)
    {
        return this.getPassword(transform, "from org.openuss.security.User as user where user.id = ?", id);
    }

    /**
     * @see org.openuss.security.UserDao#getPassword(int, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object getPassword(final int transform, final java.lang.String queryString, final java.lang.Long id)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, id);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
                if (results.size() > 1)
                {
                    throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
                        "More than one instance of 'java.lang.String"
                            + "' was found when executing query --> '" + queryString + "'");
                }
                else if (results.size() == 1)
                {
                    result = results.iterator().next();
                }
            }
            result = transformEntity(transform, (org.openuss.security.User)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

    /**
     * @see org.openuss.security.UserDao#findUsersByCriteria(org.openuss.security.UserCriteria)
     */
    public java.util.List findUsersByCriteria(org.openuss.security.UserCriteria criteria)
    {
        return this.findUsersByCriteria(TRANSFORM_NONE, criteria);
    }

    /**
     * @see org.openuss.security.UserDao#findUsersByCriteria(int, java.lang.String, org.openuss.security.UserCriteria)
     */
    public java.util.List findUsersByCriteria(final int transform, final org.openuss.security.UserCriteria criteria)
    {
        try
        {
            org.andromda.spring.CriteriaSearch criteriaSearch = new org.andromda.spring.CriteriaSearch(super.getSession(false), org.openuss.security.UserImpl.class);
            criteriaSearch.getConfiguration().setFirstResult(criteria.getFirstResult());
            criteriaSearch.getConfiguration().setFetchSize(criteria.getFetchSize());
            criteriaSearch.getConfiguration().setMaximumResultSize(criteria.getMaximumResultSize());
            org.andromda.spring.CriteriaSearchParameter parameter1 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getUsername(),
                    "name", org.andromda.spring.CriteriaSearchParameter.LIKE_COMPARATOR);
            parameter1.setOrderDirection(org.andromda.spring.CriteriaSearchParameter.ORDER_ASC);
            parameter1.setOrderRelevance(1);
            criteriaSearch.addParameter(parameter1);
            org.andromda.spring.CriteriaSearchParameter parameter2 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getEmail(),
                    "email", org.andromda.spring.CriteriaSearchParameter.LIKE_COMPARATOR);
            criteriaSearch.addParameter(parameter2);
            org.andromda.spring.CriteriaSearchParameter parameter3 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getEnabled(),
                    "enabled");
            criteriaSearch.addParameter(parameter3);
            org.andromda.spring.CriteriaSearchParameter parameter4 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getAccountExpired(),
                    "accountExpired");
            criteriaSearch.addParameter(parameter4);
            org.andromda.spring.CriteriaSearchParameter parameter5 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getAccountLocked(),
                    "accountLocked");
            criteriaSearch.addParameter(parameter5);
            org.andromda.spring.CriteriaSearchParameter parameter6 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getCredentialsExpired(),
                    "credentialsExpired");
            criteriaSearch.addParameter(parameter6);
            org.andromda.spring.CriteriaSearchParameter parameter7 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getFirstName(),
                    "firstName", org.andromda.spring.CriteriaSearchParameter.LIKE_COMPARATOR);
            criteriaSearch.addParameter(parameter7);
            org.andromda.spring.CriteriaSearchParameter parameter8 =
                new org.andromda.spring.CriteriaSearchParameter(
                    criteria.getLastName(),
                    "lastName", org.andromda.spring.CriteriaSearchParameter.LIKE_COMPARATOR);
            criteriaSearch.addParameter(parameter8);
            java.util.List results = criteriaSearch.executeAsList();
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
     * flag is set to one of the constants defined in <code>org.openuss.security.UserDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.security.User} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.security.UserInfo} - {@link TRANSFORM_USERINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.security.UserDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.security.User entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_USERINFO :
                    target = toUserInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.security.User)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.security.UserDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.security.User)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_USERINFO :
                toUserInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.security.UserDao#toUserInfoCollection(java.util.Collection)
     */
    public final void toUserInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, USERINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link UserDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.security.UserDao#toUserInfo(org.openuss.security.User)
     */
    protected org.openuss.security.UserInfo toUserInfo(java.lang.Object[] row)
    {
        org.openuss.security.UserInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.security.User)
                {
                    target = this.toUserInfo((org.openuss.security.User)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.security.UserInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer USERINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.security.User)
                {
                    result = toUserInfo((org.openuss.security.User)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toUserInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.security.UserDao#userInfoToEntityCollection(java.util.Collection)
     */
    public final void userInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.security.UserInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, UserInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer UserInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return userInfoToEntity((org.openuss.security.UserInfo)input);
            }
        };

    /**
     * @see org.openuss.security.UserDao#toUserInfo(org.openuss.security.User, org.openuss.security.UserInfo)
     */
    public void toUserInfo(
        org.openuss.security.User source,
        org.openuss.security.UserInfo target)
    {
        target.setId(source.getId());
        target.setPassword(source.getPassword());
        target.setEmail(source.getEmail());
        target.setEnabled(source.isEnabled());
        target.setAccountExpired(source.isAccountExpired());
        target.setAccountLocked(source.isAccountLocked());
        target.setLastLogin(source.getLastLogin());
        target.setCredentialsExpired(source.isCredentialsExpired());
        target.setImageId(source.getImageId());
        target.setTheme(source.getTheme());
        target.setLocale(source.getLocale());
        target.setTimezone(source.getTimezone());
        target.setEmailPublic(source.isEmailPublic());
        target.setAddressPublic(source.isAddressPublic());
        target.setTelephonePublic(source.isTelephonePublic());
        target.setPortraitPublic(source.isPortraitPublic());
        target.setImagePublic(source.isImagePublic());
        target.setProfilePublic(source.isProfilePublic());
        target.setPortrait(source.getPortrait());
        target.setMatriculation(source.getMatriculation());
        target.setStudies(source.getStudies());
        target.setSmsEmail(source.getSmsEmail());
        target.setProfession(source.getProfession());
        target.setCity(source.getCity());
        target.setCountry(source.getCountry());
        target.setTelephone(source.getTelephone());
        target.setPostcode(source.getPostcode());
        target.setAgeGroup(source.getAgeGroup());
        target.setAddress(source.getAddress());
        target.setTitle(source.getTitle());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setNewsletterSubscriptionEnabled(source.isNewsletterSubscriptionEnabled());
        target.setDiscussionSubscriptionEnabled(source.isDiscussionSubscriptionEnabled());
        target.setDeleted(source.isDeleted());
    }

    /**
     * @see org.openuss.security.UserDao#toUserInfo(org.openuss.security.User)
     */
    public org.openuss.security.UserInfo toUserInfo(final org.openuss.security.User entity)
    {
        final org.openuss.security.UserInfo target = new org.openuss.security.UserInfo();
        this.toUserInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.security.UserDao#userInfoToEntity(org.openuss.security.UserInfo, org.openuss.security.User)
     */
    public void userInfoToEntity(
        org.openuss.security.UserInfo source,
        org.openuss.security.User target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getEmail() != null)
        {
            target.setEmail(source.getEmail());
        }
        if (copyIfNull || source.getPassword() != null)
        {
            target.setPassword(source.getPassword());
        }
        if (copyIfNull || source.getLastName() != null)
        {
            target.setLastName(source.getLastName());
        }
        if (copyIfNull || source.getFirstName() != null)
        {
            target.setFirstName(source.getFirstName());
        }
        if (copyIfNull || source.getTitle() != null)
        {
            target.setTitle(source.getTitle());
        }
        if (copyIfNull || source.getImageId() != null)
        {
            target.setImageId(source.getImageId());
        }
        if (copyIfNull || source.getLocale() != null)
        {
            target.setLocale(source.getLocale());
        }
        if (copyIfNull || source.getTheme() != null)
        {
            target.setTheme(source.getTheme());
        }
        if (copyIfNull || source.getTimezone() != null)
        {
            target.setTimezone(source.getTimezone());
        }
        if (copyIfNull || source.getAddress() != null)
        {
            target.setAddress(source.getAddress());
        }
        if (copyIfNull || source.getAgeGroup() != null)
        {
            target.setAgeGroup(source.getAgeGroup());
        }
        if (copyIfNull || source.getPostcode() != null)
        {
            target.setPostcode(source.getPostcode());
        }
        if (copyIfNull || source.getTelephone() != null)
        {
            target.setTelephone(source.getTelephone());
        }
        if (copyIfNull || source.getCountry() != null)
        {
            target.setCountry(source.getCountry());
        }
        if (copyIfNull || source.getCity() != null)
        {
            target.setCity(source.getCity());
        }
        if (copyIfNull || source.getProfession() != null)
        {
            target.setProfession(source.getProfession());
        }
        if (copyIfNull || source.getSmsEmail() != null)
        {
            target.setSmsEmail(source.getSmsEmail());
        }
        if (copyIfNull || source.getLastLogin() != null)
        {
            target.setLastLogin(source.getLastLogin());
        }
	    target.setCredentialsExpired(source.isCredentialsExpired());
	    target.setAccountLocked(source.isAccountLocked());
	    target.setAccountExpired(source.isAccountExpired());
	    target.setEnabled(source.isEnabled());
        if (copyIfNull || source.getStudies() != null)
        {
            target.setStudies(source.getStudies());
        }
        if (copyIfNull || source.getMatriculation() != null)
        {
            target.setMatriculation(source.getMatriculation());
        }
        if (copyIfNull || source.getPortrait() != null)
        {
            target.setPortrait(source.getPortrait());
        }
	    target.setProfilePublic(source.isProfilePublic());
	    target.setImagePublic(source.isImagePublic());
	    target.setPortraitPublic(source.isPortraitPublic());
	    target.setTelephonePublic(source.isTelephonePublic());
	    target.setAddressPublic(source.isAddressPublic());
	    target.setEmailPublic(source.isEmailPublic());
	    target.setDiscussionSubscriptionEnabled(source.isDiscussionSubscriptionEnabled());
	    target.setNewsletterSubscriptionEnabled(source.isNewsletterSubscriptionEnabled());
	    target.setDeleted(source.isDeleted());
    }
    
}