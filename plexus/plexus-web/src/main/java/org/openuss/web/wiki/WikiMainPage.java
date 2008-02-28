package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.utilities.URLUTF8Encoder;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteInfo;
import org.openuss.wiki.WikiSiteContentInfo;


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
				pageName = URLUTF8Encoder.decode(this.siteName);
			}

			WikiSiteContentInfo backup = this.siteVersionInfo;
			this.siteVersionInfo = this.wikiService.findWikiSiteContentByDomainObjectAndName(this.courseInfo.getId(), pageName);
			
			if (this.siteVersionInfo == null) {
				setSessionBean(Constants.WIKI_NEW_SITE_BACKUP, backup);
				setSessionBean(Constants.WIKI_NEW_SITE_NAME, pageName);
				this.siteName = null;
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
	
	public String showImportPage() {
		return Constants.WIKI_IMPORT_PAGE;
	}
	
	public String showStable() { //Test
		WikiSiteContentInfo wikiSiteContentInfo = wikiService.getNewestStableWikiSiteContent(siteVersionInfo.getWikiSiteId());
		if(wikiSiteContentInfo == null)
			wikiSiteContentInfo = wikiService.getNewestWikiSiteContent(siteVersionInfo.getWikiSiteId());
		
		siteVersionInfo = wikiSiteContentInfo;
		siteVersionId=siteVersionInfo.getId();
		//setSessionBean(Constants.WIKI_MAIN_PAGE, wikiSiteContentInfo);
		return Constants.WIKI_MAIN_PAGE;
		
	}
	
	public String markStable() {
		siteVersionInfo.setStable(true);
		wikiService.saveWikiSite(siteVersionInfo);		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	public String unmarkStable() { //FIXME
		siteVersionInfo.setStable(false);
		wikiService.saveWikiSite(siteVersionInfo);
		return Constants.WIKI_MAIN_PAGE;
	}
	
	public Boolean getHasStableVersion() {
		return wikiService.getNewestStableWikiSiteContent(siteVersionInfo.getWikiSiteId()) != null;
	}
	
	public String getSiteTitle() {
		if (siteVersionInfo.getName() == null) {
			return siteName;
		}
		
		StringBuilder siteTitle;
		if(Constants.WIKI_STARTSITE_NAME.equals(siteVersionInfo.getName())) {
			siteTitle = new StringBuilder(i18n("wiki_index_page_readable"));
		} else {
			siteTitle = new StringBuilder(siteVersionInfo.getName());
		}
		
		//attach Version if not newest
		WikiSiteInfo wikiSiteInfo = wikiService.getNewestWikiSite(siteVersionInfo.getWikiSiteId());
		if(siteVersionInfo.getId() != wikiSiteInfo.getId()){
			siteTitle.append(" (Version "+ siteVersionInfo.getId() +")");
		}
		
		return siteTitle.toString();
	}
}