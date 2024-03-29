
package org.openuss.security.ldap;

/**
 * @see org.openuss.security.ldap.LdapServer
 */
public interface LdapServerDao
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
     * {@link org.openuss.security.ldap.LdapServerInfo}.
     */
    public final static int TRANSFORM_LDAPSERVERINFO = 1;

    /**
     * Copies the fields of the specified entity to the target value object. This method is similar to
     * toLdapServerInfo(), but it does not handle any attributes in the target
     * value object that are "read-only" (as those do not have setter methods exposed).
     */
    public void toLdapServerInfo(
        org.openuss.security.ldap.LdapServer sourceEntity,
        org.openuss.security.ldap.LdapServerInfo targetVO);


    /**
     * Converts this DAO's entity to an object of type {@link org.openuss.security.ldap.LdapServerInfo}.
     */
    public org.openuss.security.ldap.LdapServerInfo toLdapServerInfo(org.openuss.security.ldap.LdapServer entity);

    /**
     * Converts this DAO's entity to a Collection of instances of type {@link org.openuss.security.ldap.LdapServerInfo}.
     */
    public void toLdapServerInfoCollection(java.util.Collection entities);
    
    /**
     * Copies the fields of {@link org.openuss.security.ldap.LdapServerInfo} to the specified entity.
     * @param copyIfNull If FALSE, the value object's field will not be copied to the entity if the value is NULL. If TRUE,
     * it will be copied regardless of its value.
     */
    public void ldapServerInfoToEntity(
        org.openuss.security.ldap.LdapServerInfo sourceVO,
        org.openuss.security.ldap.LdapServer targetEntity,
        boolean copyIfNull);

    /**
     * Converts an instance of type {@link org.openuss.security.ldap.LdapServerInfo} to this DAO's entity.
     */
    public org.openuss.security.ldap.LdapServer ldapServerInfoToEntity(org.openuss.security.ldap.LdapServerInfo ldapServerInfo);

    /**
     * Converts a Collection of instances of type {@link org.openuss.security.ldap.LdapServerInfo} to this
     * DAO's entity.
     */
    public void ldapServerInfoToEntityCollection(java.util.Collection instances);

    /**
     * Loads an instance of org.openuss.security.ldap.LdapServer from the persistent store.
     */
    public org.openuss.security.ldap.LdapServer load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.security.ldap.LdapServer}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.security.ldap.LdapServer> loadAll();

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
     * Creates an instance of org.openuss.security.ldap.LdapServer and adds it to the persistent store.
     */
    public org.openuss.security.ldap.LdapServer create(org.openuss.security.ldap.LdapServer ldapServer);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.security.ldap.LdapServer)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entity (into a value object for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object create(int transform, org.openuss.security.ldap.LdapServer ldapServer);

    /**
     * Creates a new instance of org.openuss.security.ldap.LdapServer and adds
     * from the passed in <code>entities</code> collection
     *
     * @param entities the collection of org.openuss.security.ldap.LdapServer
     * instances to create.
     *
     * @return the created instances.
     */
    public java.util.Collection<org.openuss.security.ldap.LdapServer> create(java.util.Collection<org.openuss.security.ldap.LdapServer> entities);

    /**
     * <p>
     * Does the same thing as {@link #create(org.openuss.security.ldap.LdapServer)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * the returned entity will <strong>NOT</strong> be transformed. If this flag is any of the other constants
     * defined here then the result <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.Collection create(int transform, java.util.Collection<org.openuss.security.ldap.LdapServer> entities);

    /**
     * Updates the <code>ldapServer</code> instance in the persistent store.
     */
    public void update(org.openuss.security.ldap.LdapServer ldapServer);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.security.ldap.LdapServer> entities);

    /**
     * Removes the instance of org.openuss.security.ldap.LdapServer from the persistent store.
     */
    public void remove(org.openuss.security.ldap.LdapServer ldapServer);

    /**
     * Removes the instance of org.openuss.security.ldap.LdapServer having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.security.ldap.LdapServer> entities);

    /**
 * 
     */
    public java.util.List findByAuthenticationDomain(org.openuss.security.ldap.AuthenticationDomain authenticationDomain);

    /**
     * <p>
     * Does the same thing as {@link #findByAuthenticationDomain(org.openuss.security.ldap.AuthenticationDomain)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByAuthenticationDomain(org.openuss.security.ldap.AuthenticationDomain)}.
     * </p>
     */
    public java.util.List findByAuthenticationDomain(String queryString, org.openuss.security.ldap.AuthenticationDomain authenticationDomain);

    /**
     * <p>
     * Does the same thing as {@link #findByAuthenticationDomain(org.openuss.security.ldap.AuthenticationDomain)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findByAuthenticationDomain(int transform, org.openuss.security.ldap.AuthenticationDomain authenticationDomain);

    /**
     * <p>
     * Does the same thing as {@link #findByAuthenticationDomain(boolean, org.openuss.security.ldap.AuthenticationDomain)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByAuthenticationDomain(int, org.openuss.security.ldap.AuthenticationDomain authenticationDomain)}.
     * </p>
     */
    public java.util.List findByAuthenticationDomain(int transform, String queryString, org.openuss.security.ldap.AuthenticationDomain authenticationDomain);

    /**
 * 
     */
    public java.util.List findAllEnabledServers();

    /**
     * <p>
     * Does the same thing as {@link #findAllEnabledServers()} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findAllEnabledServers()}.
     * </p>
     */
    public java.util.List findAllEnabledServers(String queryString);

    /**
     * <p>
     * Does the same thing as {@link #findAllEnabledServers()} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public java.util.List findAllEnabledServers(int transform);

    /**
     * <p>
     * Does the same thing as {@link #findAllEnabledServers(boolean)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findAllEnabledServers(int)}.
     * </p>
     */
    public java.util.List findAllEnabledServers(int transform, String queryString);

}
