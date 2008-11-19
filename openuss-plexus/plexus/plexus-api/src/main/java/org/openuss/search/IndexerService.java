package org.openuss.search;

/**
 * 
 */
public interface IndexerService {

	/**
	 * <p>
	 * Creates a new index entry of the domain object.
	 * </p>
	 * <p>
	 * 
	 * @Param DomainObject - unique identifier of the domainObject
	 *        </p>
	 */
	public void createIndex(org.openuss.foundation.DomainObject domainObject)
			throws org.openuss.search.IndexerApplicationException;

	/**
	 * <p>
	 * Updates the index entry of the domain object.
	 * </p>
	 * <p>
	 * 
	 * @Param DomainObject - unique identifier of the domainObject
	 *        </p>
	 */
	public void updateIndex(org.openuss.foundation.DomainObject domainObject)
			throws org.openuss.search.IndexerApplicationException;

	/**
	 * <p>
	 * Deltes the index entry of the domain object.
	 * </p>
	 * <p>
	 * 
	 * @Param DomainObject - unique identifier of the domainObject
	 *        </p>
	 */
	public void deleteIndex(org.openuss.foundation.DomainObject domainObject)
			throws org.openuss.search.IndexerApplicationException;

	/**
	 * <p>
	 * Recreate the full search index.
	 * </p>
	 */
	public void recreate() throws org.openuss.search.IndexerApplicationException;

}
