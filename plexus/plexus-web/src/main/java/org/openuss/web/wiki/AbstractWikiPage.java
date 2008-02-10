package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;
import org.openuss.wiki.WikiService;
import org.openuss.wiki.WikiSiteInfo;
import org.openuss.wiki.WikiSiteVersionInfo;

public class AbstractWikiPage extends AbstractCoursePage {
	
	public static final Logger logger = Logger.getLogger(AbstractWikiPage.class);
		
	@Property(value = "#{wikiService}")
	protected WikiService wikiService;
	
	@Property(value = "#{" + Constants.WIKI_CURRENT_SITE_VERSION + "}")
	protected WikiSiteVersionInfo siteVersionInfo;
	
	@Property(value = "#{" + Constants.WIKI_CURRENT_SITE+ "}")
	protected WikiSiteInfo siteInfo;

	public WikiService getWikiService() {
		return wikiService;
	}
	public void setWikiService(WikiService wikiService) {
		this.wikiService = wikiService;
	}
	
	public WikiSiteVersionInfo getSiteVersionInfo() {
		return siteVersionInfo;
	}
	public void setSiteVersionInfo(WikiSiteVersionInfo siteVersionInfo) {
		this.siteVersionInfo = siteVersionInfo;
	}
	public WikiSiteInfo getSiteInfo() {
		return siteInfo;
	}
	public void setSiteInfo(WikiSiteInfo siteInfo) {
		this.siteInfo = siteInfo;
	}
	
}