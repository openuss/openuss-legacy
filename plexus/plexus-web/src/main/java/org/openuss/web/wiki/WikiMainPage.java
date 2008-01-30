package org.openuss.web.wiki;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteInfo;
import org.openuss.wiki.WikiSiteVersionInfo;


@Bean(name = "views$secured$wiki$wikimain", scope = Scope.REQUEST)
@View
public class WikiMainPage extends AbstractWikiPage{
	private static final Logger logger = Logger.getLogger(WikiMainPage.class);
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		
		if (this.siteVersionInfo == null || this.siteVersionInfo.getId() == null) {
			this.siteInfo = this.wikiService.findWikiSiteByCourseAndName(this.courseInfo.getId(), Constants.WIKI_STARTSITE_NAME);
			if (this.siteInfo == null) {
				System.out.println("WikiSite '" + Constants.WIKI_STARTSITE_NAME + "' for course not found. Creating WikiSite for courseId: " + this.courseInfo.getId());
				logger.debug("WikiSite '" + Constants.WIKI_STARTSITE_NAME + "' for course not found. Creating WikiSite for courseId: " + this.courseInfo.getId());
				this.siteInfo = new WikiSiteInfo();
				this.siteInfo.setCourseId(courseInfo.getId());
				this.siteInfo.setName(Constants.WIKI_STARTSITE_NAME);
				this.wikiService.createWikiSite(this.siteInfo);
			}
			
			this.siteVersionInfo = this.wikiService.getNewestWikiSiteVersion(this.siteInfo.getId());
			if (this.siteVersionInfo == null) {
				System.out.println("No version found for wiki with id:" + this.siteInfo.getId() + " ... creating one.");
				logger.debug("No version found for wiki with id:" + this.siteInfo.getId() + " ... creating one.");
				this.siteVersionInfo = new WikiSiteVersionInfo();
				this.siteVersionInfo.setWikiSiteId(this.siteInfo.getId());
				this.siteVersionInfo.setCreationDate(new Date());
				this.siteVersionInfo.setUserId(user.getId());
			}
			
			setSessionBean(Constants.WIKI_CURRENT_SITE, this.siteInfo);
			setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
		}
		
		this.siteVersionInfo.setNote("");
		
		wikiActive.setValue(true);
		
	}
	
	public String save() {
		this.siteVersionInfo.setId(null);
		this.siteVersionInfo.setWikiSiteId(this.siteInfo.getId());
		this.siteVersionInfo.setCreationDate(new Date());
		this.siteVersionInfo.setUserId(user.getId());
		getWikiService().createWikiSiteVersion(this.siteVersionInfo);
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	public String overview() {
		return Constants.WIKI_OVERVIEW;
	}
	
}