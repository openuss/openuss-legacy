// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.wiki;

import java.util.List;

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;


/**
 * JUnit Test for Spring Hibernate WikiSiteDao class.
 * @see org.openuss.wiki.WikiSiteDao
 */
public class WikiSiteDaoTest extends WikiSiteDaoTestBase {
	
	@SuppressWarnings("unchecked")
	public void testWikiSiteDaoCreate() {
		final String siteName = "test";
		WikiSite wikiSite = WikiSite.Factory.newInstance();
		DefaultDomainObject domain = new DefaultDomainObject(TestUtility.unique());
		wikiSite.setName(siteName);
		wikiSite.setDeleted(true);
		wikiSite.setDomainId(domain.getId());
		assertNull(wikiSite.getId());
		wikiSiteDao.create(wikiSite);
		assertNotNull(wikiSite.getId());
		List<WikiSite> wikiSites = wikiSiteDao.findByDomainId(domain.getId());
		assertNotNull(wikiSites);
		WikiSite wikiSite2 = wikiSiteDao.findByDomainIdAndName(domain.getId(), siteName);
		assertNotNull(wikiSite2);
		
		
	}
}
