package org.openuss.news;

import java.util.Date;

/**
 * @author Ingo Dueppe 
 */
public interface NewsItem extends org.openuss.foundation.DomainObject {

	/**
	 * <p>
	 * The primary key for this entiy.
	 * </p>
	 */
	public Long getId();

	public void setId(Long id);

	/**
     * 
     */
	public org.openuss.news.NewsCategory getCategory();

	public void setCategory(org.openuss.news.NewsCategory category);

	/**
     * 
     */
	public Long getPublisherIdentifier();

	public void setPublisherIdentifier(Long publisherIdentifier);

	/**
	 * <p>
	 * The content of the news. The text will be saved as a html input. So the
	 * user can use <code>HTML</code> tags.
	 * </p>
	 * <p>
	 * This field is mandatory.
	 * </p>
	 */
	public String getText();

	public void setText(String text);

	/**
	 * <p>
	 * The display name of the Publisher. This name will be presented to the
	 * user.
	 * </p>
	 */
	public String getPublisherName();

	public void setPublisherName(String publisherName);

	/**
	 * <p>
	 * the title of the news
	 * </p>
	 */
	public String getTitle();

	public void setTitle(String title);

	/**
	 * <p>
	 * The point of time until this news should be published. If specified this
	 * property should be greater than <code>dateStart</code>.
	 * </p>
	 */
	public Date getPublishDate();

	public void setPublishDate(Date publishDate);

	/**
	 * <p>
	 * The point of time when this news should be published.
	 * </p>
	 * <p>
	 * This field is mandatory.
	 * </p>
	 */
	public Date getExpireDate();

	public void setExpireDate(Date expireDate);

	/**
     * 
     */
	public String getAuthor();

	public void setAuthor(String author);

	/**
     * 
     */
	public org.openuss.news.PublisherType getPublisherType();

	public void setPublisherType(org.openuss.news.PublisherType publisherType);

	/**
     * 
     */
	public abstract boolean isValidExpireDate();

	/**
     * 
     */
	public abstract boolean isReleased();

	/**
     * 
     */
	public abstract boolean isExpired();

	/**
	 * Constructs new instances of {@link org.openuss.news.NewsItem}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static NewsItem.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static NewsItem.Factory getFactory() {
			if (factory == null) {
				factory = (NewsItem.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.news.NewsItem.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract NewsItem createNewsItem();

		/**
		 * Constructs a new instance of {@link org.openuss.news.NewsItem}.
		 */
		public static org.openuss.news.NewsItem newInstance() {
			return getFactory().createNewsItem();
		}

		/**
		 * Constructs a new instance of {@link org.openuss.news.NewsItem},
		 * taking all required and/or read-only properties as arguments.
		 */
		public static org.openuss.news.NewsItem newInstanceByIdentifier(Long id) {
			final org.openuss.news.NewsItem entity = getFactory().createNewsItem();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of {@link org.openuss.news.NewsItem},
		 * taking all required and/or read-only properties as arguments.
		 */
		public static org.openuss.news.NewsItem newInstance(org.openuss.news.NewsCategory category,
				Long publisherIdentifier, String text, Date publishDate, org.openuss.news.PublisherType publisherType) {
			final org.openuss.news.NewsItem entity = getFactory().createNewsItem();
			entity.setCategory(category);
			entity.setPublisherIdentifier(publisherIdentifier);
			entity.setText(text);
			entity.setPublishDate(publishDate);
			entity.setPublisherType(publisherType);
			return entity;
		}

		/**
		 * Constructs a new instance of {@link org.openuss.news.NewsItem},
		 * taking all possible properties (except the identifier(s))as
		 * arguments.
		 */
		public static org.openuss.news.NewsItem newInstance(org.openuss.news.NewsCategory category,
				Long publisherIdentifier, String text, String publisherName, String title, Date publishDate,
				Date expireDate, String author, org.openuss.news.PublisherType publisherType) {
			final org.openuss.news.NewsItem entity = getFactory().createNewsItem();
			entity.setCategory(category);
			entity.setPublisherIdentifier(publisherIdentifier);
			entity.setText(text);
			entity.setPublisherName(publisherName);
			entity.setTitle(title);
			entity.setPublishDate(publishDate);
			entity.setExpireDate(expireDate);
			entity.setAuthor(author);
			entity.setPublisherType(publisherType);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}