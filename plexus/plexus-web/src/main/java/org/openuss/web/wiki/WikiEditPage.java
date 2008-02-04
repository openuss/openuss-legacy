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


@Bean(name = "views$secured$wiki$wikiedit", scope = Scope.REQUEST)
@View
public class WikiEditPage extends AbstractWikiPage{
	private static final Logger logger = Logger.getLogger(WikiEditPage.class);
	
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
			
			if (this.siteVersionInfo != null) {
				this.siteVersionInfo.setNote("");
			}
		}
		
		setSessionBean(Constants.WIKI_CURRENT_SITE, this.siteInfo);
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
	}
	
	public String save() {
		this.siteVersionInfo.setId(null);
		this.siteVersionInfo.setWikiSiteId(this.siteInfo.getId());
		this.siteVersionInfo.setCreationDate(new Date());
		this.siteVersionInfo.setUserId(user.getId());
		getWikiService().createWikiSiteVersion(this.siteVersionInfo);
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	public String create() {
		String pageName = (String) getSessionBean(Constants.WIKI_NEW_SITE_NAME);
		System.out.println(">>>>> " + pageName);
		
		this.siteInfo.setId(null);
		this.siteInfo.setName(pageName);
		this.siteInfo.setCourseId(courseInfo.getId());
		this.wikiService.createWikiSite(this.siteInfo);
		
		this.siteVersionInfo.setId(null);
		this.siteVersionInfo.setWikiSiteId(this.siteInfo.getId());
		this.siteVersionInfo.setCreationDate(new Date());
		this.siteVersionInfo.setUserId(user.getId());
		
		setSessionBean(Constants.WIKI_NEW_SITE_NAME, null);
		
		return Constants.WIKI_EDIT_PAGE;
	}
	
	public String overview() {
		return Constants.WIKI_OVERVIEW;
	}
	
}