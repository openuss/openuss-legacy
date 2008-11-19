package org.openuss.chat;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.chat.ChatMessage</code>.
 * </p>
 *
 * @see org.openuss.chat.ChatMessage
 */
public abstract class ChatMessageDaoBase
    extends org.springframework.orm.hibernate3.support.HibernateDaoSupport
    implements org.openuss.chat.ChatMessageDao
{


    /**
     * @see org.openuss.chat.ChatMessageDao#load(int, java.lang.Long)
     */
    public java.lang.Object load(final int transform, final java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "ChatMessage.load - 'id' can not be null");
        }

        final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.chat.ChatMessageImpl.class, id);
        return transformEntity(transform, (org.openuss.chat.ChatMessage)entity);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#load(java.lang.Long)
     */
    public org.openuss.chat.ChatMessage load(java.lang.Long id)
    {
        return (org.openuss.chat.ChatMessage)this.load(TRANSFORM_NONE, id);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#loadAll()
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.chat.ChatMessage> loadAll()
    {
        return this.loadAll(TRANSFORM_NONE);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#loadAll(int)
     */
    public java.util.Collection loadAll(final int transform)
    {
        final java.util.Collection results = this.getHibernateTemplate().loadAll(org.openuss.chat.ChatMessageImpl.class);
        this.transformEntities(transform, results);
        return results;
    }


    /**
     * @see org.openuss.chat.ChatMessageDao#create(org.openuss.chat.ChatMessage)
     */
    public org.openuss.chat.ChatMessage create(org.openuss.chat.ChatMessage chatMessage)
    {
        return (org.openuss.chat.ChatMessage)this.create(TRANSFORM_NONE, chatMessage);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#create(int transform, org.openuss.chat.ChatMessage)
     */
    public java.lang.Object create(final int transform, final org.openuss.chat.ChatMessage chatMessage)
    {
        if (chatMessage == null)
        {
            throw new IllegalArgumentException(
                "ChatMessage.create - 'chatMessage' can not be null");
        }
        this.getHibernateTemplate().save(chatMessage);
        return this.transformEntity(transform, chatMessage);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#create(java.util.Collection<org.openuss.chat.ChatMessage>)
     */
    @SuppressWarnings({"unchecked"})
    public java.util.Collection<org.openuss.chat.ChatMessage> create(final java.util.Collection<org.openuss.chat.ChatMessage> entities)
    {
        return create(TRANSFORM_NONE, entities);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#create(int, java.util.Collection<org.openuss.chat.ChatMessage>)
     */
    public java.util.Collection create(final int transform, final java.util.Collection<org.openuss.chat.ChatMessage> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ChatMessage.create - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        create(transform, (org.openuss.chat.ChatMessage)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
        return entities;
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#update(org.openuss.chat.ChatMessage)
     */
    public void update(org.openuss.chat.ChatMessage chatMessage)
    {
        if (chatMessage == null)
        {
            throw new IllegalArgumentException(
                "ChatMessage.update - 'chatMessage' can not be null");
        }
        try {
	        this.getHibernateTemplate().update(chatMessage);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException "+ex.getCause().getMessage());
				getSession().merge(chatMessage);
			} else {
				throw ex;
			}
		}
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#update(java.util.Collection<org.openuss.chat.ChatMessage>)
     */
    public void update(final java.util.Collection<org.openuss.chat.ChatMessage> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ChatMessage.update - 'entities' can not be null");
        }
        this.getHibernateTemplate().execute(
            new org.springframework.orm.hibernate3.HibernateCallback()
            {
                public java.lang.Object doInHibernate(org.hibernate.Session session)
                    throws org.hibernate.HibernateException
                {
                    for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();)
                    {
                        update((org.openuss.chat.ChatMessage)entityIterator.next());
                    }
                    return null;
                }
            },
            true);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#remove(org.openuss.chat.ChatMessage)
     */
    public void remove(org.openuss.chat.ChatMessage chatMessage)
    {
        if (chatMessage == null)
        {
            throw new IllegalArgumentException(
                "ChatMessage.remove - 'chatMessage' can not be null");
        }
        this.getHibernateTemplate().delete(chatMessage);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#remove(java.lang.Long)
     */
    public void remove(java.lang.Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException(
                "ChatMessage.remove - 'id can not be null");
        }
        org.openuss.chat.ChatMessage entity = this.load(id);
        if (entity != null)
        {
            this.remove(entity);
        }
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#remove(java.util.Collection<org.openuss.chat.ChatMessage>)
     */
    public void remove(java.util.Collection<org.openuss.chat.ChatMessage> entities)
    {
        if (entities == null)
        {
            throw new IllegalArgumentException(
                "ChatMessage.remove - 'entities' can not be null");
        }
        this.getHibernateTemplate().deleteAll(entities);
    }
    /**
     * @see org.openuss.chat.ChatMessageDao#findByRoom(java.lang.Long)
     */
    public java.util.List findByRoom(java.lang.Long roomId)
    {
        return this.findByRoom(TRANSFORM_NONE, roomId);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#findByRoom(java.lang.String, java.lang.Long)
     */
    public java.util.List findByRoom(final java.lang.String queryString, final java.lang.Long roomId)
    {
        return this.findByRoom(TRANSFORM_NONE, queryString, roomId);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#findByRoom(int, java.lang.Long)
     */
    public java.util.List findByRoom(final int transform, final java.lang.Long roomId)
    {
        return this.findByRoom(transform, "from org.openuss.chat.ChatMessage as chatMessage where chatMessage.roomId = ?", roomId);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#findByRoom(int, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByRoom(final int transform, final java.lang.String queryString, final java.lang.Long roomId)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, roomId);
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
     * @see org.openuss.chat.ChatMessageDao#findByRoomAfter(java.lang.Long, java.lang.Long)
     */
    public java.util.List findByRoomAfter(java.lang.Long roomId, java.lang.Long messageId)
    {
        return this.findByRoomAfter(TRANSFORM_NONE, roomId, messageId);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#findByRoomAfter(java.lang.String, java.lang.Long, java.lang.Long)
     */
    public java.util.List findByRoomAfter(final java.lang.String queryString, final java.lang.Long roomId, final java.lang.Long messageId)
    {
        return this.findByRoomAfter(TRANSFORM_NONE, queryString, roomId, messageId);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#findByRoomAfter(int, java.lang.Long, java.lang.Long)
     */
    public java.util.List findByRoomAfter(final int transform, final java.lang.Long roomId, final java.lang.Long messageId)
    {
        return this.findByRoomAfter(transform, "from org.openuss.chat.ChatMessage as chatMessage where chatMessage.roomId = ? and chatMessage.messageId = ?", roomId, messageId);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#findByRoomAfter(int, java.lang.String, java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByRoomAfter(final int transform, final java.lang.String queryString, final java.lang.Long roomId, final java.lang.Long messageId)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, roomId);
            queryObject.setParameter(1, messageId);
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
     * @see org.openuss.chat.ChatMessageDao#findByRoomSince(java.lang.Long, java.util.Date)
     */
    public java.util.List findByRoomSince(java.lang.Long roomId, java.util.Date since)
    {
        return this.findByRoomSince(TRANSFORM_NONE, roomId, since);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#findByRoomSince(java.lang.String, java.lang.Long, java.util.Date)
     */
    public java.util.List findByRoomSince(final java.lang.String queryString, final java.lang.Long roomId, final java.util.Date since)
    {
        return this.findByRoomSince(TRANSFORM_NONE, queryString, roomId, since);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#findByRoomSince(int, java.lang.Long, java.util.Date)
     */
    public java.util.List findByRoomSince(final int transform, final java.lang.Long roomId, final java.util.Date since)
    {
        return this.findByRoomSince(transform, "from org.openuss.chat.ChatMessage as chatMessage where chatMessage.roomId = ? and chatMessage.since = ?", roomId, since);
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#findByRoomSince(int, java.lang.String, java.lang.Long, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    public java.util.List findByRoomSince(final int transform, final java.lang.String queryString, final java.lang.Long roomId, final java.util.Date since)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
            queryObject.setParameter(0, roomId);
            queryObject.setParameter(1, since);
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
     * @see org.openuss.chat.ChatMessageDao#lastMessageId(java.lang.Long)
     */
    public java.lang.Long lastMessageId(final java.lang.Long roomId)
    {
        try
        {
            return this.handleLastMessageId(roomId);
        }
        catch (Throwable th)
        {
            throw new java.lang.RuntimeException(
            "Error performing 'org.openuss.chat.ChatMessageDao.lastMessageId(java.lang.Long roomId)' --> " + th,
            th);
        }
    }

     /**
      * Performs the core logic for {@link #lastMessageId(java.lang.Long)}
      */
    protected abstract java.lang.Long handleLastMessageId(java.lang.Long roomId)
        throws java.lang.Exception;

    /**
     * Allows transformation of entities into value objects
     * (or something else for that matter), when the <code>transform</code>
     * flag is set to one of the constants defined in <code>org.openuss.chat.ChatMessageDao</code>, please note
     * that the {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity itself
     * will be returned.
     * <p/>
     * This method will return instances of these types:
     * <ul>
     *   <li>{@link org.openuss.chat.ChatMessage} - {@link #TRANSFORM_NONE}</li>
     *   <li>{@link org.openuss.chat.ChatMessageInfo} - {@link TRANSFORM_CHATMESSAGEINFO}</li>
     * </ul>
     *
     * If the integer argument value is unknown {@link #TRANSFORM_NONE} is assumed.
     *
     * @param transform one of the constants declared in {@link org.openuss.chat.ChatMessageDao}
     * @param entity an entity that was found
     * @return the transformed entity (i.e. new value object, etc)
     * @see #transformEntities(int,java.util.Collection)
     */
    protected java.lang.Object transformEntity(final int transform, final org.openuss.chat.ChatMessage entity)
    {
        java.lang.Object target = null;
        if (entity != null)
        {
            switch (transform)
            {
                case TRANSFORM_CHATMESSAGEINFO :
                    target = toChatMessageInfo(entity);
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
     * {@link #transformEntity(int,org.openuss.chat.ChatMessage)}
     * method. This method does not instantiate a new collection.
     * <p/>
     * This method is to be used internally only.
     *
     * @param transform one of the constants declared in <code>org.openuss.chat.ChatMessageDao</code>
     * @param entities the collection of entities to transform
     * @see #transformEntity(int,org.openuss.chat.ChatMessage)
     */
    protected void transformEntities(final int transform, final java.util.Collection entities)
    {
        switch (transform)
        {
            case TRANSFORM_CHATMESSAGEINFO :
                toChatMessageInfoCollection(entities);
                break;
            case TRANSFORM_NONE : // fall-through
                default:
                // do nothing;
        }
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#toChatMessageInfoCollection(java.util.Collection)
     */
    public final void toChatMessageInfoCollection(java.util.Collection entities)
    {
        if (entities != null)
        {
            org.apache.commons.collections.CollectionUtils.transform(entities, CHATMESSAGEINFO_TRANSFORMER);
        }
    }

    /**
     * Default implementation for transforming the results of a report query into a value object. This
     * implementation exists for convenience reasons only. It needs only be overridden in the
     * {@link ChatMessageDaoImpl} class if you intend to use reporting queries.
     * @see org.openuss.chat.ChatMessageDao#toChatMessageInfo(org.openuss.chat.ChatMessage)
     */
    protected org.openuss.chat.ChatMessageInfo toChatMessageInfo(java.lang.Object[] row)
    {
        org.openuss.chat.ChatMessageInfo target = null;
        if (row != null)
        {
            final int numberOfObjects = row.length;
            for (int ctr = 0; ctr < numberOfObjects; ctr++)
            {
                final java.lang.Object object = row[ctr];
                if (object instanceof org.openuss.chat.ChatMessage)
                {
                    target = this.toChatMessageInfo((org.openuss.chat.ChatMessage)object);
                    break;
                }
            }
        }
        return target;
    }

    /**
     * This anonymous transformer is designed to transform entities or report query results
     * (which result in an array of objects) to {@link org.openuss.chat.ChatMessageInfo}
     * using the Jakarta Commons-Collections Transformation API.
     */
    private org.apache.commons.collections.Transformer CHATMESSAGEINFO_TRANSFORMER =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                java.lang.Object result = null;
                if (input instanceof org.openuss.chat.ChatMessage)
                {
                    result = toChatMessageInfo((org.openuss.chat.ChatMessage)input);
                }
                else if (input instanceof java.lang.Object[])
                {
                    result = toChatMessageInfo((java.lang.Object[])input);
                }
                return result;
            }
        };

    /**
     * @see org.openuss.chat.ChatMessageDao#chatMessageInfoToEntityCollection(java.util.Collection)
     */
    public final void chatMessageInfoToEntityCollection(java.util.Collection instances)
    {
        if (instances != null)
        {
            for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();)
            {
                // - remove an objects that are null or not of the correct instance
                if (!(iterator.next() instanceof org.openuss.chat.ChatMessageInfo))
                {
                    iterator.remove();
                }
            }
            org.apache.commons.collections.CollectionUtils.transform(instances, ChatMessageInfoToEntityTransformer);
        }
    }

    private final org.apache.commons.collections.Transformer ChatMessageInfoToEntityTransformer =
        new org.apache.commons.collections.Transformer()
        {
            public java.lang.Object transform(java.lang.Object input)
            {
                return chatMessageInfoToEntity((org.openuss.chat.ChatMessageInfo)input);
            }
        };

    /**
     * @see org.openuss.chat.ChatMessageDao#toChatMessageInfo(org.openuss.chat.ChatMessage, org.openuss.chat.ChatMessageInfo)
     */
    public void toChatMessageInfo(
        org.openuss.chat.ChatMessage source,
        org.openuss.chat.ChatMessageInfo target)
    {
        target.setId(source.getId());
        target.setText(source.getText());
        target.setTime(source.getTime());
        target.setI18n(source.isI18n());
    }

    /**
     * @see org.openuss.chat.ChatMessageDao#toChatMessageInfo(org.openuss.chat.ChatMessage)
     */
    public org.openuss.chat.ChatMessageInfo toChatMessageInfo(final org.openuss.chat.ChatMessage entity)
    {
        final org.openuss.chat.ChatMessageInfo target = new org.openuss.chat.ChatMessageInfo();
        this.toChatMessageInfo(entity, target);
        return target;
    }
    
    /**
     * @see org.openuss.chat.ChatMessageDao#chatMessageInfoToEntity(org.openuss.chat.ChatMessageInfo, org.openuss.chat.ChatMessage)
     */
    public void chatMessageInfoToEntity(
        org.openuss.chat.ChatMessageInfo source,
        org.openuss.chat.ChatMessage target,
        boolean copyIfNull)
    {
        if (copyIfNull || source.getText() != null)
        {
            target.setText(source.getText());
        }
        if (copyIfNull || source.getTime() != null)
        {
            target.setTime(source.getTime());
        }
	    target.setI18n(source.isI18n());
    }
    
}