package org.openuss.web.wiki;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteContentInfo;

/**
 * Backing Bean for wikicreatesite.xhtml.
 * @author Ingo Dueppe
 *
 */
@Bean(name = "views$secured$wiki$wikiprintpreview", scope = Scope.REQUEST)
@View
public class WikiPrintPreviewPage extends AbstractWikiPage {
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		if (!checkCourseInfo()) {
			return;
		}
		
		siteVersionInfo = (WikiSiteContentInfo) getBean(Constants.WIKI_CURRENT_SITE_VERSION);
		
		if (siteVersionInfo != null && siteVersionInfo.getId() != null) {
			siteVersionInfo = wikiService.getWikiSiteContent(this.siteVersionInfo.getId());
		} else {
			String siteName = null;
			if (siteVersionInfo != null && siteVersionInfo.getName() != null) {
				siteName = this.siteVersionInfo.getName().trim();
				
				if (!isValidWikiSiteName(siteName)) {
					addError(i18n("wiki_error_illegal_site_name"));
					return;
				}
	
				siteName = formatPageName(siteName);
			} else {
				siteName = Constants.WIKI_STARTSITE_NAME;
			}

			WikiSiteContentInfo version = this.wikiService.findWikiSiteContentByDomainObjectAndName(this.courseInfo.getId(), siteName);
			
			if (version == null) {
				this.siteVersionInfo = new WikiSiteContentInfo();
				siteVersionInfo.setName(siteName);
				setBean(Constants.WIKI_CURRENT_SITE_VERSION, siteVersionInfo);
			} else {
				this.siteVersionInfo = version;
			}
		}

		setBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
				
		super.prerender();

	}
	
}