package org.openuss.wiki;

import java.util.Collection;
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
		WikiSite wikiSite = new WikiSiteImpl();
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
		WikiSite wikiSite3 = wikiSiteDao.load(wikiSite.getId());
		assertNotNull(wikiSite3);
		wikiSiteDao.remove(wikiSite);
		Collection wikiSites2 = wikiSiteDao.loadAll();
		assertEquals(wikiSites2.size(),0);
		
		
	}
}
