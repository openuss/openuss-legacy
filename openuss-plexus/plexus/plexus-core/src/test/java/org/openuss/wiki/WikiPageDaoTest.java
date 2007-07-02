// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.wiki;


/**
 * JUnit Test for Spring Hibernate WikiPageDao class.
 * @see org.openuss.wiki.WikiPageDao
 */
public class WikiPageDaoTest extends WikiPageDaoTestBase {
	
	public void testWikiPageDaoCreate() {
		WikiPage wikiPage = new WikiPageImpl();
		wikiPage.setText(" ");
		assertNull(wikiPage.getId());
		wikiPageDao.create(wikiPage);
		assertNotNull(wikiPage.getId());
	}
}