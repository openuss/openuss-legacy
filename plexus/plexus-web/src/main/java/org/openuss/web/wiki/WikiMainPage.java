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
		
		String pageName = Constants.WIKI_STARTSITE_NAME;
		if (this.siteInfo != null && this.siteInfo.getName() != null) {
			pageName = this.siteInfo.getName();
		} 
		this.siteInfo = this.wikiService.findWikiSiteByCourseAndName(this.courseInfo.getId(), pageName);
		
		if (this.siteInfo != null) {
			this.siteVersionInfo = this.wikiService.getNewestWikiSiteVersion(this.siteInfo.getId());
		} else {
			this.siteVersionInfo = null;
			setSessionBean(Constants.WIKI_NEW_SITE_NAME, pageName);
		}
		
		setSessionBean(Constants.WIKI_CURRENT_SITE, this.siteInfo);
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
	}
	
	public String overview() {
		return Constants.WIKI_OVERVIEW;
	}
	
}