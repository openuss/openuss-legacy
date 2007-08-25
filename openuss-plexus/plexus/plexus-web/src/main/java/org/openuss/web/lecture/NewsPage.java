package org.openuss.web.lecture;

import java.util.Date;
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
import org.openuss.lecture.LectureException;
import org.openuss.news.NewsCategory;
import org.openuss.news.NewsItem;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 * News Page Controller
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$news", scope = Scope.REQUEST)
@View
public class NewsPage extends AbstractLecturePage {

	private static final Logger logger = Logger.getLogger(NewsPage.class);

	@Property(value = "#{newsService}")
	private NewsService newsService;

	private NewsDataProvider data = new NewsDataProvider();
	
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}	
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("institute_command_news"));
		crumb.setHint(i18n("institute_command_news"));
		
		breadcrumbs.loadInstituteCrumbs(instituteInfo);
		breadcrumbs.addCrumb(crumb);
		
		// TODO Remove old crumb code
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}
	
	private class NewsDataProvider extends AbstractPagedTable<NewsItemInfo> {

		private static final long serialVersionUID = -8735576547984958020L;
		
		private DataPage<NewsItemInfo> page; 
		
		@Override 
		public DataPage<NewsItemInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<NewsItemInfo> news = newsService.getNewsItems(institute);
				page = new DataPage<NewsItemInfo>(news.size(),0,news);
				sort(news);
			}
			return page;
		}
	}
		
	/**
	 * Starting to add a new newsitem. 
	 * @return outcome
	 */
	public String addNewsItem() {
		NewsItemInfo newsItem = new NewsItemInfo();
		// define initial values
		newsItem.setCategory(NewsCategory.GLOBAL);
		newsItem.setPublishDate(new Date());
		newsItem.setExpireDate(new Date(System.currentTimeMillis()+1000L*60L*60L*24L*28L));
		
		newsItem.setPublisherIdentifier(institute.getId());
		newsItem.setPublisherName(institute.getName());
		
		setSessionBean(Constants.NEWS_SELECTED_NEWSITEM, newsItem);
		return Constants.INSTITUTE_NEWS_EDIT_PAGE;
	}
	
	/**
	 * Editing a news item
	 * @return outcome
	 */
	public String editNewsItem() {
		logger.debug("edit news item");
		NewsItemInfo newsItem = data.getRowData();
		setSessionBean(Constants.NEWS_SELECTED_NEWSITEM, newsItem);
		return Constants.INSTITUTE_NEWS_EDIT_PAGE;
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
		return Constants.INSTITUTE_NEWS_PAGE;
	}

	public List<NewsItem> getNewsItems() {
		logger.debug("getting newsitems for institute " + institute);
		return newsService.getNewsItems(institute);
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
