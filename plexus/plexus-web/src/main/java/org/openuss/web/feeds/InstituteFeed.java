package org.openuss.web.feeds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 */
public class InstituteFeed extends AbstractFeed {

	/** logger **/
	public static final Logger logger = Logger.getLogger(CourseFeed.class);

	/** institute service **/
	private transient InstituteService instituteService;

	/** system service **/
	private transient SystemService systemService;

	/** news service **/
	private transient NewsService newsService;

	@SuppressWarnings("unchecked")
	private FeedWrapper buildFeedArray(InstituteInfo institute) {
		final List<SyndEntry> entries = new ArrayList<SyndEntry>();

		List<NewsItemInfo> newsEntries = getNewsService().getNewsItems(institute);

		FeedWrapper feedWrapper = new FeedWrapper();
		
		final String serverUrl = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue(); 
		final String linkNewsItem = serverUrl + "/views/public/news/newsDetail.faces?news=" ;

		if (newsEntries != null && newsEntries.size() != 0) {
			Collections.reverse(newsEntries);
			for (NewsItemInfo item : newsEntries) {
				if (item.getPublishDate().before(new Date())){
					this.addEntry(entries, 
							item.getTitle(), 
							linkNewsItem + item.getId(), 
							item.getPublishDate(), 
							item.getText(),
							institute.getName(), 
							item.getPublisherName());
				}
			}

			feedWrapper.setLastModified(newsEntries.get(0).getPublishDate());
		}

		String link = serverUrl + "/views/secured/lecture/institute.faces?institute=" + institute.getId();
		
		feedWrapper.setWriter(this.convertToXml("["
				+ i18n("rss_institute", null, locale()) + "] "
				+ institute.getName(), link, institute.getDescription(), systemService.getProperty(
				SystemProperties.COPYRIGHT).getValue(), entries));
		return feedWrapper;
	}
	
	public FeedWrapper getFeed(Long instituteId) {
		if (instituteId == null || instituteId == 0) {
			return null;
		}
		InstituteInfo institute = instituteService.findInstitute(instituteId);
		if (institute == null) {
			return null;
		} else  {
			return buildFeedArray(institute);
		}
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

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

}