// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;

import java.util.Date;

/**
 * @author ingo dueppe
 * @see org.openuss.news.NewsItem
 */
public class NewsItemImpl extends NewsItemBase implements NewsItem {

	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -6534578341590643230L;

	/**
	 * @see org.openuss.news.NewsItem#isValidExpireDate()
	 */
	public boolean isValidExpireDate() {
		if (getExpireDate() != null) {
			return !getExpireDate().before(getPublishDate());
		}
		return true;
	}

	/**
	 * @see org.openuss.news.NewsItem#isReleased()
	 */
	public boolean isReleased() {
		if (getPublishDate() != null) {
			return new Date().after(getPublishDate());
		} else {
			return true;
		}
	}

	/**
	 * @see org.openuss.news.NewsItem#isExpired()
	 */
	public boolean isExpired() {
		if (getExpireDate() != null) {
			return new Date().after(getExpireDate());
		} else {
			return false;
		}
	}

	@Override
	public String getText() {
		String text = super.getText();
		return text;
	}
}