package org.openuss.news;

import java.util.List;

/**
 * @author Ingo Dueppe 
 */
public interface NewsService {

	/**
	 * Finds a news item by its primary key.
	 * 
	 * @return a NewsItemInfo object containing possible attachments
	 */
	public org.openuss.news.NewsItemInfo getNewsItem(org.openuss.news.NewsItemInfo item);

	/**
	 * <p>
	 * Save the given news item to the database.
	 * </p>
	 */
	public void saveNewsItem(org.openuss.news.NewsItemInfo item);

	/**
     * 
     */
	public void deleteNewsItem(org.openuss.news.NewsItemInfo item);

	/**
	 * <p>
	 * Returns a list of all available NewsItems specified by the the publisher.
	 * The publisher can be any object that has a public getId() method.
	 * </p>
	 */
	public List getNewsItems(org.openuss.foundation.DomainObject publisher);

	/**
	 * <p>
	 * Returns a list of all available current NewsItems specified by the the
	 * publisher. The publisher can be any object that has a public getId()
	 * method. Current NewsItems is defined as NewsItems that dateStart < now <
	 * dateEnd.
	 * </p>
	 */
	public List getCurrentNewsItems(org.openuss.foundation.DomainObject publisher, Integer count);

	/**
	 * <p>
	 * Returns a list of all available current NewsItems specified by the
	 * category.
	 * </p>
	 */
	public List getCurrentNewsItems(org.openuss.news.NewsCategory category, Integer count);

	/**
     * 
     */
	public List getPublishedNewsItems(org.openuss.foundation.DomainObject publisher, Integer firstResult, Integer count);

	/**
     * 
     */
	public List getPublishedNewsItems(org.openuss.news.NewsCategory category, Integer firstResult, Integer count);

	/**
     * 
     */
	public long getPublishedNewsItemsCount(org.openuss.foundation.DomainObject publisher);

	/**
     * 
     */
	public long getPublishedNewsItemsCount(org.openuss.news.NewsCategory category);

}
