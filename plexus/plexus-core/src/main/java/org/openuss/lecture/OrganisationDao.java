package org.openuss.lecture;

/**
 * @see org.openuss.lecture.Organisation
 */
public interface OrganisationDao
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
     * Loads an instance of org.openuss.lecture.Organisation from the persistent store.
     */
    public org.openuss.lecture.Organisation load(java.lang.Long id);

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
     * Loads all entities of type {@link org.openuss.lecture.Organisation}.
     *
     * @return the loaded entities.
     */
    public java.util.Collection<org.openuss.lecture.Organisation> loadAll();

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
     * Updates the <code>organisation</code> instance in the persistent store.
     */
    public void update(org.openuss.lecture.Organisation organisation);

    /**
     * Updates all instances in the <code>entities</code> collection in the persistent store.
     */
    public void update(java.util.Collection<org.openuss.lecture.Organisation> entities);

    /**
     * Removes the instance of org.openuss.lecture.Organisation from the persistent store.
     */
    public void remove(org.openuss.lecture.Organisation organisation);

    /**
     * Removes the instance of org.openuss.lecture.Organisation having the given
     * <code>identifier</code> from the persistent store.
     */
  
  public void remove(java.lang.Long id);

    /**
     * Removes all entities in the given <code>entities<code> collection.
     */
    public void remove(java.util.Collection<org.openuss.lecture.Organisation> entities);

    /**
 * <p>
 * Searches for an Organisation corresponding to a given shortcut.
 * </p>
 * <p>
 * @param shortcut - The shorcut that identifies the Organisation.
 * </p>
 * <p>
 * @return - The corresponding Orgnisation, null if nothing was
 * found.
 * </p>
     */
    public org.openuss.lecture.Organisation findByShortcut(java.lang.String shortcut);

    /**
     * <p>
     * Does the same thing as {@link #findByShortcut(java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByShortcut(java.lang.String)}.
     * </p>
     */
    public org.openuss.lecture.Organisation findByShortcut(String queryString, java.lang.String shortcut);

    /**
     * <p>
     * Does the same thing as {@link #findByShortcut(java.lang.String)} with an
     * additional flag called <code>transform</code>. If this flag is set to <code>TRANSFORM_NONE</code> then
     * finder results will <strong>NOT</strong> be transformed during retrieval.
     * If this flag is any of the other constants defined here
     * then finder results <strong>WILL BE</strong> passed through an operation which can optionally
     * transform the entities (into value objects for example). By default, transformation does
     * not occur.
     * </p>
     */
    public Object findByShortcut(int transform, java.lang.String shortcut);

    /**
     * <p>
     * Does the same thing as {@link #findByShortcut(boolean, java.lang.String)} with an
     * additional argument called <code>queryString</code>. This <code>queryString</code>
     * argument allows you to override the query string defined in {@link #findByShortcut(int, java.lang.String shortcut)}.
     * </p>
     */
    public Object findByShortcut(int transform, String queryString, java.lang.String shortcut);

}
