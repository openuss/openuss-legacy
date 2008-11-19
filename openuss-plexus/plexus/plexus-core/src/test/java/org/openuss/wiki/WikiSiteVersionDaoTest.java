package org.openuss.wiki;

import java.util.Date;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;

/**
 * JUnit Test for Spring Hibernate WikiSiteVersionDao class.
 * @see org.openuss.wiki.WikiSiteVersionDao
 */
public class WikiSiteVersionDaoTest extends WikiSiteVersionDaoTestBase {
	
	protected WikiSiteDao wikiSiteDao;
	
	public WikiSiteDao getWikiSiteDao() {
		return wikiSiteDao;
	}

	public void setWikiSiteDao(WikiSiteDao wikiSiteDao) {
		this.wikiSiteDao = wikiSiteDao;
	}
	
	@SuppressWarnings("unchecked")
	public void testWikiSiteVersionDaoCreate() {
		WikiSite wikiSite = new WikiSiteImpl();
		DefaultDomainObject domain = new DefaultDomainObject(TestUtility.unique());
		wikiSite.setDomainId(domain.getId());
		wikiSite.setName("test");
		wikiSite.setDeleted(true);
		wikiSiteDao.create(wikiSite);
		
		WikiSiteVersion wikiSiteVersion = new WikiSiteVersionImpl();
		wikiSiteVersion.setText(" ");
		wikiSiteVersion.setCreationDate(new Date());
		wikiSiteVersion.setWikiSite(wikiSite);
		assertNull(wikiSiteVersion.getId());
		wikiSiteVersionDao.create(wikiSiteVersion);
		assertNotNull(wikiSiteVersion.getId());
		List<WikiSite> wikiSites = wikiSiteVersionDao.findByWikiSite(wikiSite);
		assertNotNull(wikiSites);
		WikiSiteVersion wikiSite2 = wikiSiteVersionDao.load(wikiSiteVersion.getId());
		assertNotNull(wikiSite2);
//		WikiSiteContentInfo wikiSiteContent = wikiSiteVersionDao.toWikiSiteContentInfo(wikiSiteVersion);
//		assertNotNull(wikiSiteContent.getId());
		
		
		wikiSiteVersionDao.remove(wikiSiteVersion);
		
	}
	
}
