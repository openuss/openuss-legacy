// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntity.vsl in andromda-hibernate-cartridge.
//
package org.openuss.news;

/**
 * @see org.openuss.news.NewsItem
 */
public class NewsItemImpl extends org.openuss.news.NewsItemBase implements org.openuss.news.NewsItem {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 7237827307940513470L;

	public boolean isValidExpireDate() {
		return this.getPublishDate().before(this.getExpireDate());
	}
}