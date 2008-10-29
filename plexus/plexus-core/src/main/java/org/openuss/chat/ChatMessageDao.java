package org.openuss.chat;

/**
 * @see org.openuss.chat.ChatMessage
 */
public interface ChatMessageDao
{
    /**
     * This constant is used as a transformation flag; entities can be converted automatically into value objects
     * or other types, different methods in a class implementing this interface support this feature: look for
     * an <code>int</code> parameter called <code>transform</code>.
     * <p/>
     * This specific flag denotes no transformation will occur.
     */
    public final static int TRANSFORM_NONE = 0;

    /**
     * This constant is used as a transformation flag; entities can be converted automatically into value objects
     * or other types, different methods in a class implementing this interface support this feature: look for
     * an <code>int</code> parameter called <code>transform</code>.
     * <p/>
     * This specific flag denotes entities must be transformed into objects of type
     * {@link org.openuss.chat.ChatMessageInfo}.
     */
    public final static int TRANSFORM_CHATMESSAGEINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toChatMessageInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toChatMessageInfo(
        org.openuss.chat.ChatMessage sourceEntity,
        org.openuss.chat.ChatMessageInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.chat.ChatMessageInfo}.
     */
    public org.openuss.chat.ChatMessageInfo toChatMessageInfo(org.openuss.chat.ChatMessage entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.chat.ChatMessageInfo}.
     */
    public void toChatMessageInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.chat.ChatMessageInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void chatMessageInfoToEntity(
        org.openuss.chat.ChatMessageInfo sourceVO,
        org.openuss.chat.ChatMessage targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.chat.ChatMessageInfo} to this DAO's entity.
     */
    public org.openuss.chat.ChatMessage chatMessageInfoToEntity(org.openuss.chat.ChatMessageInfo chatMessageInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.chat.ChatMessageInfo} to this
     * DAO's entity.
     */
    public void chatMessageInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.chat.ChatMessage from the persistent store.
     */
    public org.openuss.chat.ChatMessage load(java.lang.Long id);

    /**
     * <p>
     * Does the same thing as {@link #load(java.lang.Long)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined in this class then the result <strong>WILL BE</strong> passed through an operation which can
     * optionally transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     *
     * @param id the identifier of the entity to load.
     * @return either the entity or the object transformed from the entity.
     */
    public Object load(int transform, java.lang.Long id);

    /**
     * Loads all entities of type {@link org.openuss.chat.ChatMessage}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.chat.ChatMessage> loadAll();

    /**
     * <p>
     * Does the same thing as {@link #loadAll()} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     *
     * @param transform the flag indicating what transformation to use.
     * @return the loaded entities.
     */
    public java.util.Collection loadAll(final int transform);

    /**
     * Creates an instance of org.openuss.chat.ChatMessage and adds it to the persistent store.
     */
    public org.openuss.chat.ChatMessage create(org.openuss.chat.ChatMessage chatMessage);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.chat.ChatMessage)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.chat.ChatMessage chatMessage);

    /**
     * Creates a new instance of org.openuss.chat.ChatMessage and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.chat.ChatMessage
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.chat.ChatMessage> create(java.util.Collection<org.openuss.chat.ChatMessage> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.chat.ChatMessage)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.chat.ChatMessage> entities);

    /**
     * Updates the <code>chatMessage</code> instance in the persistent store.
     */
    public void update(org.openuss.chat.ChatMessage chatMessage);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.chat.ChatMessage> entities);

    /**
     * Removes the instance of org.openuss.chat.ChatMessage from the persistent store.
     */
    public void remove(org.openuss.chat.ChatMessage chatMessage);

    /**
     * Removes the instance of org.openuss.chat.ChatMessage having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.chat.ChatMessage> entities);

    /**
 * 
     */
    public java.util.List findByRoom(java.lang.Long roomId);

    /**
     * <p>
     * Does the same thing as {@link #findByRoom(java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByRoom(java.lang.Long)}.
     * </p>
     */
    public java.util.List findByRoom(String queryString, java.lang.Long roomId);

    /**
     * <p>
     * Does the same thing as {@link #findByRoom(java.lang.Long)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByRoom(int transform, java.lang.Long roomId);

    /**
     * <p>
     * Does the same thing as {@link #findByRoom(boolean, java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByRoom(int, java.lang.Long roomId)}.
     * </p>
     */
    public java.util.List findByRoom(int transform, String queryString, java.lang.Long roomId);

    /**
 * 
     */
    public java.util.List findByRoomAfter(java.lang.Long roomId, java.lang.Long messageId);

    /**
     * <p>
     * Does the same thing as {@link #findByRoomAfter(java.lang.Long, java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByRoomAfter(java.lang.Long, java.lang.Long)}.
     * </p>
     */
    public java.util.List findByRoomAfter(String queryString, java.lang.Long roomId, java.lang.Long messageId);

    /**
     * <p>
     * Does the same thing as {@link #findByRoomAfter(java.lang.Long, java.lang.Long)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByRoomAfter(int transform, java.lang.Long roomId, java.lang.Long messageId);

    /**
     * <p>
     * Does the same thing as {@link #findByRoomAfter(boolean, java.lang.Long, java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByRoomAfter(int, java.lang.Long roomId, java.lang.Long messageId)}.
     * </p>
     */
    public java.util.List findByRoomAfter(int transform, String queryString, java.lang.Long roomId, java.lang.Long messageId);

    /**
 * 
     */
    public java.util.List findByRoomSince(java.lang.Long roomId, java.util.Date since);

    /**
     * <p>
     * Does the same thing as {@link #findByRoomSince(java.lang.Long, java.util.Date)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByRoomSince(java.lang.Long, java.util.Date)}.
     * </p>
     */
    public java.util.List findByRoomSince(String queryString, java.lang.Long roomId, java.util.Date since);

    /**
     * <p>
     * Does the same thing as {@link #findByRoomSince(java.lang.Long, java.util.Date)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByRoomSince(int transform, java.lang.Long roomId, java.util.Date since);

    /**
     * <p>
     * Does the same thing as {@link #findByRoomSince(boolean, java.lang.Long, java.util.Date)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByRoomSince(int, java.lang.Long roomId, java.util.Date since)}.
     * </p>
     */
    public java.util.List findByRoomSince(int transform, String queryString, java.lang.Long roomId, java.util.Date since);

    /**
 * 
     */
    public java.lang.Long lastMessageId(java.lang.Long roomId);

}
