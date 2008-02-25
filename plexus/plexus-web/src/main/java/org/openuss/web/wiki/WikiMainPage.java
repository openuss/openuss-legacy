package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteInfo;


@Bean(name = "views$secured$wiki$wikimain", scope = Scope.REQUEST)
@View
public class WikiMainPage extends AbstractWikiPage{
	private static final Logger logger = Logger.getLogger(WikiMainPage.class);
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		if (siteVersionId != null) {
			this.siteVersionInfo = this.wikiService.getWikiSiteContent(siteVersionId);
		} else {
			String pageName = Constants.WIKI_STARTSITE_NAME;
			if (this.siteName != null) {
				pageName = this.siteName;
			}
			
			
			this.siteVersionInfo = this.wikiService.findWikiSiteContentByDomainObjectAndName(this.courseInfo.getId(), pageName);
			
			if (this.siteVersionInfo == null) {
				setSessionBean(Constants.WIKI_NEW_SITE_NAME, pageName);
			}
		}
		
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);

		// prerender at the end because of breadcrumbs!
		super.prerender();
	}
	
	public String overview() {
		return Constants.WIKI_OVERVIEW;
	}
	
	public String recoverSite() {
		final WikiSiteInfo wikiSiteInfo = this.wikiService.getWikiSite(this.siteVersionInfo.getWikiSiteId());
		wikiSiteInfo.setDeleted(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_CURRENT_SITE_VERSION;
	}
	
	public String lockSite() {
		final WikiSiteInfo wikiSiteInfo = this.wikiService.getWikiSite(this.siteVersionInfo.getWikiSiteId());
		wikiSiteInfo.setReadOnly(true);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_CURRENT_SITE_VERSION;
	}
	
	public String unlockSite() {
		final WikiSiteInfo wikiSiteInfo = this.wikiService.getWikiSite(this.siteVersionInfo.getWikiSiteId());
		wikiSiteInfo.setReadOnly(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_CURRENT_SITE_VERSION;
	}
	
	public String showStable() {
		final WikiSiteInfo wikiSiteInfo = this.wikiService.getWikiSite(this.siteVersionInfo.getWikiSiteId());
		wikiSiteInfo.setReadOnly(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_CURRENT_SITE_VERSION;
	}
	public String markStable() {
		final WikiSiteInfo wikiSiteInfo = this.wikiService.getWikiSite(this.siteVersionInfo.getWikiSiteId());
		wikiSiteInfo.setReadOnly(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_CURRENT_SITE_VERSION;
	}
	public String unmarkStable() {
		final WikiSiteInfo wikiSiteInfo = this.wikiService.getWikiSite(this.siteVersionInfo.getWikiSiteId());
		wikiSiteInfo.setReadOnly(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_CURRENT_SITE_VERSION;
	}
	
}