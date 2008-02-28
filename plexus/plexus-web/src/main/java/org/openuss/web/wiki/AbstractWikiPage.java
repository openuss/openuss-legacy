package org.openuss.web.wiki;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.course.AbstractCoursePage;
import org.openuss.wiki.WikiService;
import org.openuss.wiki.WikiSiteContentInfo;
import org.openuss.wiki.WikiSiteInfo;

/**
 * Abstract superclass for all Wiki Backing Beans. Provides context and service infrastructure.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
public class AbstractWikiPage extends AbstractCoursePage {
	
	public static final Logger logger = Logger.getLogger(AbstractWikiPage.class);
		
	@Property(value = "#{wikiService}")
	protected WikiService wikiService;
	
	@Property(value = "#{" + Constants.WIKI_CURRENT_SITE_VERSION + "}")
	protected WikiSiteContentInfo siteVersionInfo;
	
	private WikiOverviewDataProvider data = new WikiOverviewDataProvider();

	protected String siteName;
	
	protected Long siteVersionId;
	
	@Override
	public void prerender() throws Exception {
		super.prerender();
		
		addBreadCrumbs();
	}
	
	/**
	 * Adds an additional BreadCrumb to the course crumbs.
	 */
	private void addBreadCrumbs() {
		breadcrumbs.loadCourseCrumbs(courseInfo);
		
		final BreadCrumb wikiBreadCrumb = new BreadCrumb();
		wikiBreadCrumb.setLink(PageLinks.WIKI_MAIN);
		wikiBreadCrumb.setName(i18n(Constants.WIKI_MAIN_HEADER));
		wikiBreadCrumb.setHint(i18n(Constants.WIKI_MAIN_HEADER));
		breadcrumbs.addCrumb(wikiBreadCrumb);
		
		if (siteVersionInfo != null && siteVersionInfo.getName() != null) {
			final BreadCrumb wikiSiteBreadCrumb = new BreadCrumb();
			wikiSiteBreadCrumb.setName(readablePageName(siteVersionInfo.getName()));
			wikiSiteBreadCrumb.setHint(readablePageName(siteVersionInfo.getName()));
			breadcrumbs.addCrumb(wikiSiteBreadCrumb);
		}
	}

	/**
	 * Capitalizes a page name.
	 * @param pageName Page name.
	 * @return Capitalized page name.
	 */
	protected String readablePageName(String pageName) {
		if (Constants.WIKI_STARTSITE_NAME.equals(pageName)) {
			return i18n(Constants.WIKI_STARTSITE_NAME_I18N);
		} else {
			return StringUtils.capitalize(pageName);
		}
	}
	
	/**
	 * DataProvider for WikiOverview table.
	 * @author Projektseminar WS 07/08, Team Collaboration
	 *
	 */
	protected class WikiOverviewDataProvider extends AbstractPagedTable<WikiSiteInfo> {
		private DataPage<WikiSiteInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override
		public DataPage<WikiSiteInfo> getDataPage(int startRow, int pageSize) {		
			final List<WikiSiteInfo> wikiSiteInfoList = wikiService.findWikiSitesByDomainObject(courseInfo.getId());		
			page = new DataPage<WikiSiteInfo>(wikiSiteInfoList.size(), 0, wikiSiteInfoList);
			setSortColumn("name");			
			sort(wikiSiteInfoList);
			return page;
		}
	}
	
	public WikiService getWikiService() {
		return wikiService;
	}
	
	public void setWikiService(WikiService wikiService) {
		this.wikiService = wikiService;
	}
	
	public WikiSiteContentInfo getSiteVersionInfo() {
		return siteVersionInfo;
	}
	
	public void setSiteVersionInfo(WikiSiteContentInfo siteVersionInfo) {
		this.siteVersionInfo = siteVersionInfo;
	}
	
	public WikiOverviewDataProvider getData() {
		return data;
	}
	
	public void setData(WikiOverviewDataProvider data) {
		this.data = data;
	}
	
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public String getSiteName() {
		return siteName;
	}
	
	public void setSiteVersionId(Long siteVersionId) {
		this.siteVersionId = siteVersionId;
	}
	
	public Long getSiteVersionId() {
		return siteVersionId;
	}
	
}
