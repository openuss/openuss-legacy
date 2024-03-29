package org.openuss.security;

/**
 * @see org.openuss.security.User
 */
public interface UserDao {
	/**
	 * This constant is used as a transformation flag; entities can be converted
	 * automatically into value objects or other types, different methods in a
	 * class implementing this interface support this feature: look for an
	 * <code>int</code> parameter called <code>transform</code>. <p/> This
	 * specific flag denotes no transformation will occur.
	 */
	public final static int TRANSFORM_NONE = 0;

	/**
	 * This constant is used as a transformation flag; entities can be converted
	 * automatically into value objects or other types, different methods in a
	 * class implementing this interface support this feature: look for an
	 * <code>int</code> parameter called <code>transform</code>. <p/> This
	 * specific flag denotes entities must be transformed into objects of type
	 * {@link org.openuss.security.UserInfo}.
	 */
	public final static int TRANSFORM_USERINFO = 1;

	/**
	 * Copies the fields of the specified entity to the target value object.
	 * This method is similar to toUserInfo(), but it does not handle any
	 * attributes in the target value object that are "read-only" (as those do
	 * not have setter methods exposed).
	 */
	public void toUserInfo(org.openuss.security.User sourceEntity, org.openuss.security.UserInfo targetVO);

	/**
	 * Converts this DAO's entity to an object of type
	 * {@link org.openuss.security.UserInfo}.
	 */
	public org.openuss.security.UserInfo toUserInfo(org.openuss.security.User entity);

	/**
	 * Converts this DAO's entity to a Collection of instances of type
	 * {@link org.openuss.security.UserInfo}.
	 */
	public void toUserInfoCollection(java.util.Collection entities);

	/**
	 * Copies the fields of {@link org.openuss.security.UserInfo} to the
	 * specified entity.
	 * 
	 * @param copyIfNull
	 *            If FALSE, the value object's field will not be copied to the
	 *            entity if the value is NULL. If TRUE, it will be copied
	 *            regardless of its value.
	 */
	public void userInfoToEntity(org.openuss.security.UserInfo sourceVO, org.openuss.security.User targetEntity,
			boolean copyIfNull);

	/**
	 * Converts an instance of type {@link org.openuss.security.UserInfo} to
	 * this DAO's entity.
	 */
	public org.openuss.security.User userInfoToEntity(org.openuss.security.UserInfo userInfo);

	/**
	 * Converts a Collection of instances of type
	 * {@link org.openuss.security.UserInfo} to this DAO's entity.
	 */
	public void userInfoToEntityCollection(java.util.Collection instances);

	/**
	 * Loads an instance of org.openuss.security.User from the persistent store.
	 */
	public org.openuss.security.User load(java.lang.Long id);

	/**
	 * <p>
	 * Does the same thing as {@link #load(java.lang.Long)} with an additional
	 * flag called <code>transform</code>. If this flag is set to
	 * <code>TRANSFORM_NONE</code> then the returned entity will
	 * <strong>NOT</strong> be transformed. If this flag is any of the other
	 * constants defined in this class then the result <strong>WILL BE</strong>
	 * passed through an operation which can optionally transform the entity
	 * (into a value object for example). By default, transformation does not
	 * occur.
	 * </p>
	 * 
	 * @param id
	 *            the identifier of the entity to load.
	 * @return either the entity or the object transformed from the entity.
	 */
	public Object load(int transform, java.lang.Long id);

	/**
	 * Loads all entities of type {@link org.openuss.security.User}.
	 * 
	 * @return the loaded entities.
	 */
	public java.util.Collection<org.openuss.security.User> loadAll();

	/**
	 * <p>
	 * Does the same thing as {@link #loadAll()} with an additional flag called
	 * <code>transform</code>. If this flag is set to
	 * <code>TRANSFORM_NONE</code> then the returned entity will
	 * <strong>NOT</strong> be transformed. If this flag is any of the other
	 * constants defined here then the result <strong>WILL BE</strong> passed
	 * through an operation which can optionally transform the entity (into a
	 * value object for example). By default, transformation does not occur.
	 * </p>
	 * 
	 * @param transform
	 *            the flag indicating what transformation to use.
	 * @return the loaded entities.
	 */
	public java.util.Collection loadAll(final int transform);

	/**
	 * Creates an instance of org.openuss.security.User and adds it to the
	 * persistent store.
	 */
	public org.openuss.security.User create(org.openuss.security.User user);

	/**
	 * <p>
	 * Does the same thing as {@link #create(org.openuss.security.User)} with an
	 * additional flag called <code>transform</code>. If this flag is set to
	 * <code>TRANSFORM_NONE</code> then the returned entity will
	 * <strong>NOT</strong> be transformed. If this flag is any of the other
	 * constants defined here then the result <strong>WILL BE</strong> passed
	 * through an operation which can optionally transform the entity (into a
	 * value object for example). By default, transformation does not occur.
	 * </p>
	 */
	public Object create(int transform, org.openuss.security.User user);

	/**
	 * Creates a new instance of org.openuss.security.User and adds from the
	 * passed in <code>entities</code> collection
	 * 
	 * @param entities
	 *            the collection of org.openuss.security.User instances to
	 *            create.
	 * 
	 * @return the created instances.
	 */
	public java.util.Collection<org.openuss.security.User> create(
			java.util.Collection<org.openuss.security.User> entities);

	/**
	 * <p>
	 * Does the same thing as {@link #create(org.openuss.security.User)} with an
	 * additional flag called <code>transform</code>. If this flag is set to
	 * <code>TRANSFORM_NONE</code> then the returned entity will
	 * <strong>NOT</strong> be transformed. If this flag is any of the other
	 * constants defined here then the result <strong>WILL BE</strong> passed
	 * through an operation which can optionally transform the entities (into
	 * value objects for example). By default, transformation does not occur.
	 * </p>
	 */
	public java.util.Collection create(int transform, java.util.Collection<org.openuss.security.User> entities);

	/**
	 * Updates the <code>user</code> instance in the persistent store.
	 */
	public void update(org.openuss.security.User user);

	/**
	 * Updates all instances in the <code>entities</code> collection in the
	 * persistent store.
	 */
	public void update(java.util.Collection<org.openuss.security.User> entities);

	/**
	 * Removes the instance of org.openuss.security.User from the persistent
	 * store.
	 */
	public void remove(org.openuss.security.User user);

	/**
	 * Removes the instance of org.openuss.security.User having the given
	 * <code>identifier</code> from the persistent store.
	 */

	public void remove(java.lang.Long id);

	/**
	 * Removes all entities in the given <code>entities<code> collection.
	 */
	public void remove(java.util.Collection<org.openuss.security.User> entities);

	/**
 * 
     */
	public org.openuss.security.User findUserByUsername(java.lang.String username);

	/**
	 * <p> Does the same thing as {@link #findUserByUsername(java.lang.String)}
	 * with an additional argument called <code>queryString</code>. This
	 * <code>queryString</code> argument allows you to override the query string
	 * defined in {@link #findUserByUsername(java.lang.String)}. </p>
	 */
	public org.openuss.security.User findUserByUsername(String queryString, java.lang.String username);

	/**
	 * <p>
	 * Does the same thing as {@link #findUserByUsername(java.lang.String)} with
	 * an additional flag called <code>transform</code>. If this flag is set to
	 * <code>TRANSFORM_NONE</code> then finder results will <strong>NOT</strong>
	 * be transformed during retrieval. If this flag is any of the other
	 * constants defined here then finder results <strong>WILL BE</strong>
	 * passed through an operation which can optionally transform the entities
	 * (into value objects for example). By default, transformation does not
	 * occur.
	 * </p>
	 */
	public Object findUserByUsername(int transform, java.lang.String username);

	/**
	 * <p>
	 * Does the same thing as
	 * {@link #findUserByUsername(boolean, java.lang.String)} with an additional
	 * argument called <code>queryString</code>. This <code>queryString</code>
	 * argument allows you to override the query string defined in {@link
	 * #findUserByUsername(int, java.lang.String username)}.
	 * </p>
	 */
	public Object findUserByUsername(int transform, String queryString, java.lang.String username);

	/**
 * 
     */
	public org.openuss.security.User findUserByEmail(java.lang.String email);

	/**
	 * <p>
	 * Does the same thing as {@link #findUserByEmail(java.lang.String)} with an
	 * additional argument called <code>queryString</code>. This
	 * <code>queryString</code> argument allows you to override the query string
	 * defined in {@link #findUserByEmail(java.lang.String)}.
	 * </p>
	 */
	public org.openuss.security.User findUserByEmail(String queryString, java.lang.String email);

	/**
	 * <p>
	 * Does the same thing as {@link #findUserByEmail(java.lang.String)} with an
	 * additional flag called <code>transform</code>. If this flag is set to
	 * <code>TRANSFORM_NONE</code> then finder results will <strong>NOT</strong>
	 * be transformed during retrieval. If this flag is any of the other
	 * constants defined here then finder results <strong>WILL BE</strong>
	 * passed through an operation which can optionally transform the entities
	 * (into value objects for example). By default, transformation does not
	 * occur.
	 * </p>
	 */
	public Object findUserByEmail(int transform, java.lang.String email);

	/**
	 * <p>
	 * Does the same thing as
	 * {@link #findUserByEmail(boolean, java.lang.String)} with an additional
	 * argument called <code>queryString</code>. This <code>queryString</code>
	 * argument allows you to override the query string defined in {@link
	 * #findUserByEmail(int, java.lang.String email)}.
	 * </p>
	 */
	public Object findUserByEmail(int transform, String queryString, java.lang.String email);

	/**
 * 
     */
	public java.lang.String getPassword(java.lang.Long id);

	/**
	 * <p>
	 * Does the same thing as {@link #getPassword(java.lang.Long)} with an
	 * additional argument called <code>queryString</code>. This
	 * <code>queryString</code> argument allows you to override the query string
	 * defined in {@link #getPassword(java.lang.Long)}.
	 * </p>
	 */
	public java.lang.String getPassword(String queryString, java.lang.Long id);

	/**
	 * <p>
	 * Does the same thing as {@link #getPassword(java.lang.Long)} with an
	 * additional flag called <code>transform</code>. If this flag is set to
	 * <code>TRANSFORM_NONE</code> then finder results will <strong>NOT</strong>
	 * be transformed during retrieval. If this flag is any of the other
	 * constants defined here then finder results <strong>WILL BE</strong>
	 * passed through an operation which can optionally transform the entities
	 * (into value objects for example). By default, transformation does not
	 * occur.
	 * </p>
	 */
	public Object getPassword(int transform, java.lang.Long id);

	/**
	 * <p>
	 * Does the same thing as {@link #getPassword(boolean, java.lang.Long)} with
	 * an additional argument called <code>queryString</code>. This
	 * <code>queryString</code> argument allows you to override the query string
	 * defined in {@link #getPassword(int, java.lang.Long id)}.
	 * </p>
	 */
	public Object getPassword(int transform, String queryString, java.lang.Long id);

	/**
 * 
     */
	public java.util.List findUsersByCriteria(org.openuss.security.UserCriteria criteria);

	/**
	 * <p>
	 * Does the same thing as
	 * {@link #findUsersByCriteria(org.openuss.security.UserCriteria)} with an
	 * additional flag called <code>transform</code>. If this flag is set to
	 * <code>TRANSFORM_NONE</code> then finder results will <strong>NOT</strong>
	 * be transformed during retrieval. If this flag is any of the other
	 * constants defined here then finder results <strong>WILL BE</strong>
	 * passed through an operation which can optionally transform the entities
	 * (into value objects for example). By default, transformation does not
	 * occur.
	 * </p>
	 */
	public java.util.List findUsersByCriteria(final int transform, final org.openuss.security.UserCriteria criteria);

}
