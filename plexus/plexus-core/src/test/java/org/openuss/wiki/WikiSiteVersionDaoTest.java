// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.wiki;

import java.util.Date;


/**
 * JUnit Test for Spring Hibernate WikiSiteVersionDao class.
 * @see org.openuss.wiki.WikiSiteVersionDao
 */
public class WikiSiteVersionDaoTest extends WikiSiteVersionDaoTestBase {
	
	public void testWikiSiteVersionDaoCreate() {
		WikiSiteVersion wikiSiteVersion = WikiSiteVersion.Factory.newInstance();
		wikiSiteVersion.setText(" ");
		wikiSiteVersion.setCreationDate(new Date());
		assertNull(wikiSiteVersion.getId());
		wikiSiteVersionDao.create(wikiSiteVersion);
		assertNotNull(wikiSiteVersion.getId());
	}
}
