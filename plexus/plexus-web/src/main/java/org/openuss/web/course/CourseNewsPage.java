package org.openuss.web.course;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.news.NewsItem;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 * News Page Controller
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$course$coursenews", scope = Scope.REQUEST)
@View
public class CourseNewsPage extends AbstractCoursePage {

	private static final Logger logger = Logger.getLogger(CourseNewsPage.class);

	private static final long serialVersionUID = 792199034646593736L;

	@Property(value = "#{newsService}")
	private NewsService newsService;

	private NewsDataProvider data = new NewsDataProvider();

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}		
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("course_command_options_news"));
		crumb.setHint(i18n("course_command_options_news"));
		breadcrumbs.addCrumb(crumb);
	}	
	
	private class NewsDataProvider extends AbstractPagedTable<NewsItemInfo> {

		private static final long serialVersionUID = 8155867089090710357L;
		
		private DataPage<NewsItemInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<NewsItemInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<NewsItemInfo> news = getNewsService().getNewsItems(getCourseInfo());
				page = new DataPage<NewsItemInfo>(news.size(),0,news);
				sort(news);
			}
			return page;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<NewsItemInfo> getCurrentNews() {
		return newsService.getCurrentNewsItems(courseInfo,null);
	}
	
	/**
	 * Starting to add a new news item. 
	 * @return outcome
	 */
	public String addNewsItem() {
		setBean(Constants.NEWS_SELECTED_NEWSITEM, new NewsItemInfo());
		return Constants.COURSE_NEWS_EDIT_PAGE;
	}
	
	/**
	 * Editing a news item
	 * @return outcome
	 */
	public String editNewsItem() {
		logger.debug("edit news item");
		NewsItemInfo newsItem = data.getRowData();
		setBean(Constants.NEWS_SELECTED_NEWSITEM, newsItem);
		return Constants.COURSE_NEWS_EDIT_PAGE;
	}
	
	/**
	 * Remove news item
	 * @return outcome
	 */
	public String removeNewsItem() {
		logger.debug("edit removing newsitem");
		NewsItemInfo newsItem = data.getRowData();
		newsService.deleteNewsItem(newsItem);
		addMessage(i18n("message_newsitem_removed_succeed"));
		return Constants.COURSE_NEWS_PAGE;
	}

	@SuppressWarnings("unchecked")
	public List<NewsItem> getNewsItems() {
		logger.debug("getting newsitems for course " + courseInfo);
		return newsService.getNewsItems(courseInfo);
	}

	/* --------------- properties -------------- */

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public NewsDataProvider getData() {
		return data;
	}

	public void setData(NewsDataProvider data) {
		this.data = data;
	}
}
