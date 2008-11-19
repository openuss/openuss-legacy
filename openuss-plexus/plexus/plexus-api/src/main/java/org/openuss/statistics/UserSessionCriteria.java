package org.openuss.statistics;

import java.util.Date;

/**
 * 
 */
public class UserSessionCriteria implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 8001463070782008239L;

	public UserSessionCriteria() {
	}

	/**
	 * Constructor taking all properties.
	 */
	public UserSessionCriteria(Date startTime, Date endTime)

	{
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Copies constructor from other UserSessionCriteria
	 */
	public UserSessionCriteria(UserSessionCriteria otherBean) {
		if (otherBean != null) {
			this.startTime = otherBean.getStartTime();
			this.endTime = otherBean.getEndTime();
		}
	}

	/**
	 * The first result to retrieve.
	 */
	private Integer firstResult;

	/**
	 * Gets the first result to retrieve.
	 * 
	 * @return the first result to retrieve
	 */
	public Integer getFirstResult() {
		return this.firstResult;
	}

	/**
	 * Sets the first result to retrieve.
	 * 
	 * @param firstResult
	 *            the first result to retrieve
	 */
	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * The fetch size.
	 */
	private Integer fetchSize;

	/**
	 * Gets the fetch size.
	 * 
	 * @return the fetch size
	 */
	public Integer getFetchSize() {
		return this.fetchSize;
	}

	/**
	 * Sets the fetch size.
	 * 
	 * @param fetchSize
	 *            the fetch size
	 */
	public void setFetchSize(Integer fetchSize) {
		this.fetchSize = fetchSize;
	}

	/**
	 * The maximum size of the result set.
	 */
	private Integer maximumResultSize;

	/**
	 * Gets the maximum size of the search result.
	 * 
	 * @return the maximum size of the search result.
	 */
	public Integer getMaximumResultSize() {
		return this.maximumResultSize;
	}

	/**
	 * Sets the maxmimum size of the result.
	 * 
	 * @param maximumResultSize
	 *            A number indicating how many results will be returned.
	 */
	public void setMaximumResultSize(Integer maximumResultSize) {
		this.maximumResultSize = maximumResultSize;
	}

	private Date startTime;

	/**
     * 
     */
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	private Date endTime;

	/**
     * 
     */
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}