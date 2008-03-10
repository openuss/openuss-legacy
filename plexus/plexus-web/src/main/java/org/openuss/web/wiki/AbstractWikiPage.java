package org.openuss.web.wiki;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
		
	@Property(value = "#{wikiService}")
	protected WikiService wikiService;
	
	@Property(value = "#{" + Constants.WIKI_CURRENT_SITE_VERSION + "}")
	protected WikiSiteContentInfo siteVersionInfo;
	
	private WikiOverviewDataProvider data = new WikiOverviewDataProvider();

	protected String siteName;
	
	protected Long siteVersionId;
	
	private final DateFormat dateFormat = new SimpleDateFormat();
	
	@Override
	public void prerender() throws Exception {
		if (!checkSession()) {
			return;
		}
		
		super.prerender();
		
		addBreadCrumbs();
	}
	
	protected boolean checkSession() {
		if (courseInfo == null || courseInfo.getId() == null) {
			addError(i18n("message_error_course_page"));
			redirect(Constants.OUTCOME_BACKWARD);
			return false;
		} else {
			return true;
		}
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
	 * Gets the page name with Creation Date if shown page is not the newest.
	 * @return string of title with optional creation date
	 */
	public String getSiteTitle() {
		if (siteVersionInfo.getName() == null) {
			return this.siteName;
		}
		
		final StringBuilder siteTitle = new StringBuilder(readablePageName(siteVersionInfo.getName()));
		
		final WikiSiteInfo wikiSiteInfo = wikiService.getNewestWikiSite(siteVersionInfo.getWikiSiteId());
		if (!siteVersionInfo.getId().equals(wikiSiteInfo.getId())) {
			siteTitle.append(" ").append(i18n("wiki_main_version", dateFormat.format(siteVersionInfo.getCreationDate())));
		}
		
		return siteTitle.toString();
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
