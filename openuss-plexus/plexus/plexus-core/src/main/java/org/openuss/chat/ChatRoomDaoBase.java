package org.openuss.chat;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.chat.ChatRoom</code>.
 * </p>
 *
 * @see org.openuss.chat.ChatRoom
 */
public abstract class ChatRoomDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.chat.ChatRoomDao
{


    /**
     * @see org.openuss.chat.ChatRoomDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "ChatRoom.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.chat.ChatRoomImpl.class, id);
        return transformEntity(transform, (org.openuss.chat.ChatRoom)entity);
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#load(java.lang.Long)
     */
    public org.openuss.chat.ChatRoom load(java.lang.Long id)
    {
        return (org.openuss.chat.ChatRoom)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.chat.ChatRoom> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.chat.ChatRoomImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.chat.ChatRoomDao#create(org.openuss.chat.ChatRoom)
     */
    public org.openuss.chat.ChatRoom create(org.openuss.chat.ChatRoom chatRoom)
    {
        return (org.openuss.chat.ChatRoom)this.create(TRANSFORM_NONE, chatRoom);
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#create(int transform, org.openuss.chat.ChatRoom)
     */
    public java.lang.Object create(final int transform, final org.openuss.chat.ChatRoom chatRoom)
    {
        if (chatRoom == null)
        {
            throw new IllegalArgumentException(
                "ChatRoom.create - 'chatRoom' can not be null");
        }
        this.getHibernateTemplate().save(chatRoom);
        return this.transformEntity(transform, chatRoom);
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#create(java.util.Collection<org.openuss.chat.ChatRoom>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.chat.ChatRoom> create(final java.util.Collection<org.openuss.chat.ChatRoom> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#create(int, java.util.Collection<org.openuss.chat.ChatRoom>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.chat.ChatRoom> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ChatRoom.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.chat.ChatRoom)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#update(org.openuss.chat.ChatRoom)
     */
    public void update(org.openuss.chat.ChatRoom chatRoom)
    {
        if (chatRoom == null)
        {
            throw new IllegalArgumentException(
                "ChatRoom.update - 'chatRoom' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(chatRoom);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(chatRoom);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#update(java.util.Collection<org.openuss.chat.ChatRoom>)
     */
    public void update(final java.util.Collection<org.openuss.chat.ChatRoom> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ChatRoom.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.chat.ChatRoom)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#remove(org.openuss.chat.ChatRoom)
     */
    public void remove(org.openuss.chat.ChatRoom chatRoom)
    {
        if (chatRoom == null)
        {
            throw new IllegalArgumentException(
                "ChatRoom.remove - 'chatRoom' can not be null");
        }
        this.getHibernateTemplate().delete(chatRoom);
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "ChatRoom.remove - 'id can not be null");
        }
        org.openuss.chat.ChatRoom entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#remove(java.util.Collection<org.openuss.chat.ChatRoom>)
     */
    public void remove(java.util.Collection<org.openuss.chat.ChatRoom> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ChatRoom.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.chat.ChatRoomDao#findChatRoomByDomainId(java.lang.Long)
     */
    public java.util.List findChatRoomByDomainId(java.lang.Long id)
    {
        return this.findChatRoomByDomainId(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#findChatRoomByDomainId(java.lang.String, java.lang.Long)
     */
    public java.util.List findChatRoomByDomainId(final java.lang.String queryString, final java.lang.Long id)
    {
        return this.findChatRoomByDomainId(TRANSFORM_NONE, queryString, id);
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#findChatRoomByDomainId(int, java.lang.Long)
     */
    public java.util.List findChatRoomByDomainId(final int transform, final java.lang.Long id)
    {
        return this.findChatRoomByDomainId(transform, "from org.openuss.chat.ChatRoom as c where c.domainId = :id", id);
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#findChatRoomByDomainId(int, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findChatRoomByDomainId(final int transform, final java.lang.String queryString, final java.lang.Long id)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter("id", id);
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
     * flag is set to one of the constants defined in <code>org.openuss.chat.ChatRoomDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.chat.ChatRoom} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.chat.ChatRoomInfo} - {@link TRANSFORM_CHATROOMINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.chat.ChatRoomDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.chat.ChatRoom entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_CHATROOMINFO :
                    target = toChatRoomInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.chat.ChatRoom)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.chat.ChatRoomDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.chat.ChatRoom)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_CHATROOMINFO :
                toChatRoomInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#toChatRoomInfoCollection(java.util.Collection)
     */
    public final void toChatRoomInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, CHATROOMINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link ChatRoomDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.chat.ChatRoomDao#toChatRoomInfo(org.openuss.chat.ChatRoom)
     */
    protected org.openuss.chat.ChatRoomInfo toChatRoomInfo(java.lang.Object[] row)
    {
        org.openuss.chat.ChatRoomInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.chat.ChatRoom)
                {
                    target = this.toChatRoomInfo((org.openuss.chat.ChatRoom)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.chat.ChatRoomInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer CHATROOMINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.chat.ChatRoom)
                {
                    result = toChatRoomInfo((org.openuss.chat.ChatRoom)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toChatRoomInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.chat.ChatRoomDao#chatRoomInfoToEntityCollection(java.util.Collection)
     */
    public final void chatRoomInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.chat.ChatRoomInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, ChatRoomInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer ChatRoomInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return chatRoomInfoToEntity((org.openuss.chat.ChatRoomInfo)input);
            }
        };

    /**
     * @see org.openuss.chat.ChatRoomDao#toChatRoomInfo(org.openuss.chat.ChatRoom, org.openuss.chat.ChatRoomInfo)
     */
    public void toChatRoomInfo(
        org.openuss.chat.ChatRoom source,
        org.openuss.chat.ChatRoomInfo target)
    {
        target.setId(source.getId());
        target.setDomainId(source.getDomainId());
        target.setName(source.getName());
        target.setTopic(source.getTopic());
        // No conversion for target.messages (can't convert source.getMessages():org.openuss.chat.ChatMessage to int)
        target.setCreated(source.getCreated());
    }

    /**
     * @see org.openuss.chat.ChatRoomDao#toChatRoomInfo(org.openuss.chat.ChatRoom)
     */
    public org.openuss.chat.ChatRoomInfo toChatRoomInfo(final org.openuss.chat.ChatRoom entity)
    {
        final org.openuss.chat.ChatRoomInfo target = new org.openuss.chat.ChatRoomInfo();
        this.toChatRoomInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.chat.ChatRoomDao#chatRoomInfoToEntity(org.openuss.chat.ChatRoomInfo, org.openuss.chat.ChatRoom)
     */
    public void chatRoomInfoToEntity(
        org.openuss.chat.ChatRoomInfo source,
        org.openuss.chat.ChatRoom target,
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
        if (copyIfNull || source.getTopic() != null)
        {
            target.setTopic(source.getTopic());
        }
        if (copyIfNull || source.getCreated() != null)
        {
            target.setCreated(source.getCreated());
        }
    }
    
}