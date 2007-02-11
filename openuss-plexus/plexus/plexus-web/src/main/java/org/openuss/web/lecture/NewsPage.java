package org.openuss.web.lecture;

import java.util.Date;
import java.util.List;

import javax.faces.component.UIData;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.news.NewsItem;
import org.openuss.news.NewsPublisher;
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

	private static final long serialVersionUID = 792199034646593736L;

	@Property(value = "#{newsService}")
	private NewsService newsService;

	private UIData newsItemTable;
	 
	/**
	 * Starting to add a new newsitem. 
	 * @return outcome
	 */
	public String addNewsItem() {
		NewsItem newsItem = NewsItem.Factory.newInstance();
		// define initial values
		newsItem.setPublishDate(new Date());
		newsItem.setExpireDate(new Date(System.currentTimeMillis()+1000L*60L*60L*24L*28L));
		if (newsItem.getPublisher() == null) {
			NewsPublisher publisher = newsService.getPublisher(faculty);
			if (publisher == null) {
				publisher = NewsPublisher.Factory.newInstance();
				publisher.setDisplayName(faculty.getName());
				publisher.setForeignId(faculty.getId());
				publisher.setForeignClass("org.openuss.lecture.Faculty");
			}
			newsItem.setPublisher(publisher);
		}
		setSessionBean(Constants.NEWSITEM, newsItem);
		return Constants.FACULTY_NEWS_EDIT_PAGE;
	}
	
	/**
	 * Editing a news item
	 * @return outcome
	 */
	public String editNewsItem() {
		logger.debug("edit news item");
		NewsItem newsItem = (NewsItem) newsItemTable.getRowData();
		setSessionBean(Constants.NEWSITEM, newsItem);
		return Constants.FACULTY_NEWS_EDIT_PAGE;
	}
	
	/**
	 * Remove news item
	 * @return outcome
	 */
	public String removeNewsItem() {
		logger.debug("edit removing newsitem");
		NewsItem newsItem = (NewsItem) newsItemTable.getRowData();
		newsService.deleteNewsItem(newsItem.getId());
		addMessage(i18n("message_newsitem_removed_succeed"));
		return Constants.FACULTY_NEWS_PAGE;
	}

	public List<NewsItem> getNewsItems() {
		logger.debug("getting newsitems for faculty " + faculty);
		return newsService.getNewsItems(faculty);
	}

	/* --------------- properties -------------- */

	public UIData getNewsItemTable() {
		return newsItemTable;
	}

	public void setNewsItemTable(UIData newsItemTable) {
		this.newsItemTable = newsItemTable;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
}
