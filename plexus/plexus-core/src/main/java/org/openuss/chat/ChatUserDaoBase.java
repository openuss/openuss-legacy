package org.openuss.chat;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.chat.ChatUser</code>.
 * </p>
 *
 * @see org.openuss.chat.ChatUser
 */
public abstract class ChatUserDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.chat.ChatUserDao
{


    /**
     * @see org.openuss.chat.ChatUserDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "ChatUser.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.chat.ChatUserImpl.class, id);
        return transformEntity(transform, (org.openuss.chat.ChatUser)entity);
    }

    /**
     * @see org.openuss.chat.ChatUserDao#load(java.lang.Long)
     */
    public org.openuss.chat.ChatUser load(java.lang.Long id)
    {
        return (org.openuss.chat.ChatUser)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.chat.ChatUserDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.chat.ChatUser> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.chat.ChatUserDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.chat.ChatUserImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.chat.ChatUserDao#create(org.openuss.chat.ChatUser)
     */
    public org.openuss.chat.ChatUser create(org.openuss.chat.ChatUser chatUser)
    {
        return (org.openuss.chat.ChatUser)this.create(TRANSFORM_NONE, chatUser);
    }

    /**
     * @see org.openuss.chat.ChatUserDao#create(int transform, org.openuss.chat.ChatUser)
     */
    public java.lang.Object create(final int transform, final org.openuss.chat.ChatUser chatUser)
    {
        if (chatUser == null)
        {
            throw new IllegalArgumentException(
                "ChatUser.create - 'chatUser' can not be null");
        }
        this.getHibernateTemplate().save(chatUser);
        return this.transformEntity(transform, chatUser);
    }

    /**
     * @see org.openuss.chat.ChatUserDao#create(java.util.Collection<org.openuss.chat.ChatUser>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.chat.ChatUser> create(final java.util.Collection<org.openuss.chat.ChatUser> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.chat.ChatUserDao#create(int, java.util.Collection<org.openuss.chat.ChatUser>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.chat.ChatUser> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ChatUser.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.chat.ChatUser)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.chat.ChatUserDao#update(org.openuss.chat.ChatUser)
     */
    public void update(org.openuss.chat.ChatUser chatUser)
    {
        if (chatUser == null)
        {
            throw new IllegalArgumentException(
                "ChatUser.update - 'chatUser' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(chatUser);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(chatUser);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.chat.ChatUserDao#update(java.util.Collection<org.openuss.chat.ChatUser>)
     */
    public void update(final java.util.Collection<org.openuss.chat.ChatUser> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ChatUser.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.chat.ChatUser)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.chat.ChatUserDao#remove(org.openuss.chat.ChatUser)
     */
    public void remove(org.openuss.chat.ChatUser chatUser)
    {
        if (chatUser == null)
        {
            throw new IllegalArgumentException(
                "ChatUser.remove - 'chatUser' can not be null");
        }
        this.getHibernateTemplate().delete(chatUser);
    }

    /**
     * @see org.openuss.chat.ChatUserDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "ChatUser.remove - 'id can not be null");
        }
        org.openuss.chat.ChatUser entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.chat.ChatUserDao#remove(java.util.Collection<org.openuss.chat.ChatUser>)
     */
    public void remove(java.util.Collection<org.openuss.chat.ChatUser> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ChatUser.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.chat.ChatUserDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.chat.ChatUser} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.chat.ChatUserInfo} - {@link TRANSFORM_CHATUSERINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.chat.ChatUserDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.chat.ChatUser entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_CHATUSERINFO :
                    target = toChatUserInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.chat.ChatUser)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.chat.ChatUserDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.chat.ChatUser)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_CHATUSERINFO :
                toChatUserInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.chat.ChatUserDao#toChatUserInfoCollection(java.util.Collection)
     */
    public final void toChatUserInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, CHATUSERINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link ChatUserDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.chat.ChatUserDao#toChatUserInfo(org.openuss.chat.ChatUser)
     */
    protected org.openuss.chat.ChatUserInfo toChatUserInfo(java.lang.Object[] row)
    {
        org.openuss.chat.ChatUserInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.chat.ChatUser)
                {
                    target = this.toChatUserInfo((org.openuss.chat.ChatUser)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.chat.ChatUserInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer CHATUSERINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.chat.ChatUser)
                {
                    result = toChatUserInfo((org.openuss.chat.ChatUser)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toChatUserInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.chat.ChatUserDao#chatUserInfoToEntityCollection(java.util.Collection)
     */
    public final void chatUserInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.chat.ChatUserInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, ChatUserInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer ChatUserInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return chatUserInfoToEntity((org.openuss.chat.ChatUserInfo)input);
            }
        };

    /**
     * @see org.openuss.chat.ChatUserDao#toChatUserInfo(org.openuss.chat.ChatUser, org.openuss.chat.ChatUserInfo)
     */
    public void toChatUserInfo(
        org.openuss.chat.ChatUser source,
        org.openuss.chat.ChatUserInfo target)
    {
        target.setId(source.getId());
        target.setDisplayName(source.getDisplayName());
    }

    /**
     * @see org.openuss.chat.ChatUserDao#toChatUserInfo(org.openuss.chat.ChatUser)
     */
    public org.openuss.chat.ChatUserInfo toChatUserInfo(final org.openuss.chat.ChatUser entity)
    {
        final org.openuss.chat.ChatUserInfo target = new org.openuss.chat.ChatUserInfo();
        this.toChatUserInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.chat.ChatUserDao#chatUserInfoToEntity(org.openuss.chat.ChatUserInfo, org.openuss.chat.ChatUser)
     */
    public void chatUserInfoToEntity(
        org.openuss.chat.ChatUserInfo source,
        org.openuss.chat.ChatUser target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getDisplayName() != null)
        {
            target.setDisplayName(source.getDisplayName());
        }
    }
    
}