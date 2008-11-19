package org.openuss.news;

import java.util.Date;

/**
 * This is the criteria object used for hibernate criteria searches
 */
public class NewsCriteria implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 6898898603577699676L;

	public NewsCriteria() {
	}

	/**
	 * Constructor taking all properties.
	 */
	public NewsCriteria(Long publisherIdentifier, NewsCategory category, Date publishDate,
			Date expireDate)

	{
		this.publisherIdentifier = publisherIdentifier;
		this.category = category;
		this.publishDate = publishDate;
		this.expireDate = expireDate;
	}

	/**
	 * Copies constructor from other NewsCriteria
	 */
	public NewsCriteria(NewsCriteria otherBean) {
		if (otherBean != null) {
			this.publisherIdentifier = otherBean.getPublisherIdentifier();
			this.category = otherBean.getCategory();
			this.publishDate = otherBean.getPublishDate();
			this.expireDate = otherBean.getExpireDate();
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

	private Long publisherIdentifier;

	/**
     * 
     */
	public Long getPublisherIdentifier() {
		return this.publisherIdentifier;
	}

	public void setPublisherIdentifier(Long publisherIdentifier) {
		this.publisherIdentifier = publisherIdentifier;
	}

	private NewsCategory category;

	/**
     * 
     */
	public NewsCategory getCategory() {
		return this.category;
	}

	public void setCategory(NewsCategory category) {
		this.category = category;
	}

	private Date publishDate;

	/**
     * 
     */
	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	private Date expireDate;

	/**
     * 
     */
	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

}