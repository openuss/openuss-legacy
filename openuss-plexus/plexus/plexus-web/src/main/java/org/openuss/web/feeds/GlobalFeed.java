package org.openuss.web.feeds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.news.NewsCategory;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.openuss.web.news.NewsController;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 */
public class GlobalFeed extends AbstractFeed {

	/** logger **/
	public static final Logger logger = Logger.getLogger(CourseFeed.class);

	/** system service **/
	private transient SystemService systemService;

	/** news service **/
	private transient NewsService newsService;
	
	private NewsController newsController;

	@SuppressWarnings("unchecked")
	private FeedWrapper buildFeedArray() {
		final List<SyndEntry> entries = new ArrayList<SyndEntry>();

		List<NewsItemInfo> newsEntries = getNewsService().getCurrentNewsItems(NewsCategory.GLOBAL, 20);
		

		FeedWrapper feedWrapper = new FeedWrapper();
		
		final String serverUrl = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue(); 
		final String linkNewsItem = serverUrl + "/views/public/news/newsDetail.faces?news=" ;

		if (newsEntries != null && newsEntries.size() != 0) {
			//Collections.reverse(newsEntries);
			
			for (NewsItemInfo item : newsEntries) {
				if (item.getPublishDate().before(new Date())) {
					this.addEntry(entries, item.getTitle() + " ("
							+ item.getPublisherName() + ")", linkNewsItem
							+ item.getId(), item.getPublishDate(), item
							.getText(), "Global", item.getPublisherName());
				}
			}

			feedWrapper.setLastModified(newsEntries.get(0).getPublishDate());
		}

		String link = serverUrl + "/views/welcome.faces";
		
		feedWrapper.setWriter(this.convertToXml("[OpenUSS] " + 
				i18n("rss_global", null, locale()), 
				link, i18n("rss_global_description", null, locale()), systemService.getProperty(
				SystemProperties.COPYRIGHT).getValue(), entries));
		return feedWrapper;
	}
	
	public FeedWrapper getFeed() {
			return buildFeedArray();
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	public NewsController getNewsController() {
		return newsController;
	}

	public void setNewsController(NewsController newsController) {
		this.newsController = newsController;
	}


}