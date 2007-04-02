package org.openuss.news;

import java.util.Date;

import junit.framework.TestCase;

public class NewsItemTest extends TestCase {
	
	private Date past = new Date(System.currentTimeMillis()-1000000);
	private Date now = new Date(System.currentTimeMillis());
	private Date future = new Date(System.currentTimeMillis()+1000000);

	public final void testIsValidExpireDate() {
		NewsItem item = NewsItem.Factory.newInstance();
		
		item.setPublishDate(now);
		item.setExpireDate(future);
		assertTrue(item.isValidExpireDate());
		
		item.setExpireDate(past);
		assertFalse(item.isValidExpireDate());
	}

	public final void testIsReleased() {
		NewsItem item = NewsItem.Factory.newInstance();
		
		item.setPublishDate(past);
		assertTrue(item.isReleased());
		
		item.setPublishDate(future);
		assertFalse(item.isReleased());
	}

	public final void testIsExpired() {
		NewsItem item = NewsItem.Factory.newInstance();
		
		item.setExpireDate(past);
		assertTrue(item.isExpired());
		
		item.setExpireDate(future);
		assertFalse(item.isExpired());
	}

}
