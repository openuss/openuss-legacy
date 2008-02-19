package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;


@Bean(name = "views$secured$wiki$wikimain", scope = Scope.REQUEST)
@View
public class WikiMainPage extends AbstractWikiPage{
	private static final Logger logger = Logger.getLogger(WikiMainPage.class);
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		
		String pageName = Constants.WIKI_STARTSITE_NAME;
		if (this.siteName != null) {
			pageName = this.siteName;
		} 
		this.siteVersionInfo = this.wikiService.findWikiSiteContentByDomainObjectAndName(this.courseInfo.getId(), pageName);
		
		System.out.println(">>>>>>>>>> " + siteVersionInfo + " : " + (siteVersionInfo!=null?siteVersionInfo.getText():"NULL"));
		
		if (this.siteVersionInfo == null) {
			setSessionBean(Constants.WIKI_NEW_SITE_NAME, pageName);
		}
		
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
	}
	
	public String overview() {
		return Constants.WIKI_OVERVIEW;
	}
	
}