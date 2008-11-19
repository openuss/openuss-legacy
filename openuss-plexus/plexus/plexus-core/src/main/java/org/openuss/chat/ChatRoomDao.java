package org.openuss.chat;

/**
 * @see org.openuss.chat.ChatRoom
 */
public interface ChatRoomDao
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
     * {@link org.openuss.chat.ChatRoomInfo}.
     */
    public final static int TRANSFORM_CHATROOMINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toChatRoomInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toChatRoomInfo(
        org.openuss.chat.ChatRoom sourceEntity,
        org.openuss.chat.ChatRoomInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.chat.ChatRoomInfo}.
     */
    public org.openuss.chat.ChatRoomInfo toChatRoomInfo(org.openuss.chat.ChatRoom entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.chat.ChatRoomInfo}.
     */
    public void toChatRoomInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.chat.ChatRoomInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void chatRoomInfoToEntity(
        org.openuss.chat.ChatRoomInfo sourceVO,
        org.openuss.chat.ChatRoom targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.chat.ChatRoomInfo} to this DAO's entity.
     */
    public org.openuss.chat.ChatRoom chatRoomInfoToEntity(org.openuss.chat.ChatRoomInfo chatRoomInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.chat.ChatRoomInfo} to this
     * DAO's entity.
     */
    public void chatRoomInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.chat.ChatRoom from the persistent store.
     */
    public org.openuss.chat.ChatRoom load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.chat.ChatRoom}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.chat.ChatRoom> loadAll();

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
     * Creates an instance of org.openuss.chat.ChatRoom and adds it to the persistent store.
     */
    public org.openuss.chat.ChatRoom create(org.openuss.chat.ChatRoom chatRoom);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.chat.ChatRoom)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.chat.ChatRoom chatRoom);

    /**
     * Creates a new instance of org.openuss.chat.ChatRoom and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.chat.ChatRoom
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.chat.ChatRoom> create(java.util.Collection<org.openuss.chat.ChatRoom> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.chat.ChatRoom)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.chat.ChatRoom> entities);

    /**
     * Updates the <code>chatRoom</code> instance in the persistent store.
     */
    public void update(org.openuss.chat.ChatRoom chatRoom);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.chat.ChatRoom> entities);

    /**
     * Removes the instance of org.openuss.chat.ChatRoom from the persistent store.
     */
    public void remove(org.openuss.chat.ChatRoom chatRoom);

    /**
     * Removes the instance of org.openuss.chat.ChatRoom having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.chat.ChatRoom> entities);

    /**
 * 
     */
    public java.util.List findChatRoomByDomainId(java.lang.Long id);

    /**
     * <p>
     * Does the same thing as {@link #findChatRoomByDomainId(java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findChatRoomByDomainId(java.lang.Long)}.
     * </p>
     */
    public java.util.List findChatRoomByDomainId(String queryString, java.lang.Long id);

    /**
     * <p>
     * Does the same thing as {@link #findChatRoomByDomainId(java.lang.Long)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findChatRoomByDomainId(int transform, java.lang.Long id);

    /**
     * <p>
     * Does the same thing as {@link #findChatRoomByDomainId(boolean, java.lang.Long)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findChatRoomByDomainId(int, java.lang.Long id)}.
     * </p>
     */
    public java.util.List findChatRoomByDomainId(int transform, String queryString, java.lang.Long id);

}
