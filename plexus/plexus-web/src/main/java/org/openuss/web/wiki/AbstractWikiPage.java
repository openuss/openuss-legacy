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
	
	private WikiOverviewDataProvider data = new WikiOverviewDataProvider();

	protected String siteName;
	protected Long siteVersionId;
	
	@Override
	public void prerender() throws Exception {
		super.prerender();
		
		addBreadCrumbs();
	}
	
	/** Adds an additional breadcrumb to the course-crumbs.
	 * 
	 */
	private void addBreadCrumbs() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink(PageLinks.WIKI_MAIN);
		crumb.setName(i18n("wiki_main_header"));
		crumb.setHint(i18n("wiki_main_header"));

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
		
		if (this.siteInfo.getName() != null && 
				!Constants.WIKI_STARTSITE_NAME.equals(this.siteInfo.getName())) {
			crumb = new BreadCrumb();
			crumb.setLink("");
			crumb.setName(readablePageName(this.siteInfo.getName()));
			crumb.setHint(this.siteInfo.getName());
			breadcrumbs.addCrumb(crumb);
		}
	}
	
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

	protected String readablePageName(String name) {
		if (Constants.WIKI_STARTSITE_NAME.equals(name)) {
			return i18n(Constants.WIKI_STARTSITE_NAME_I18N);
		} else {
			return StringUtils.capitalize(name);
		}
	}
	
	private class WikiOverviewDataProvider extends AbstractPagedTable<WikiSiteInfo> {
		private DataPage<WikiSiteInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override
		public DataPage<WikiSiteInfo> getDataPage(int startRow, int pageSize) {		
			List<WikiSiteInfo> wikiSiteInfoList = wikiService.findWikiSitesByCourse(courseInfo.getId());		
			page = new DataPage<WikiSiteInfo>(wikiSiteInfoList.size(), 0, wikiSiteInfoList);
			sort(wikiSiteInfoList);
			return page;
		}
	}
	
}
