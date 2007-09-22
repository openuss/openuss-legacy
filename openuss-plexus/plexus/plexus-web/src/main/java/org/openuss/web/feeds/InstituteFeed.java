package org.openuss.web.feeds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDetails;
import org.openuss.lecture.LectureService;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

import com.sun.syndication.feed.synd.SyndEntry;

public class InstituteFeed extends AbstractFeed {

	private transient LectureService lectureService;

	private transient SystemService systemService;

	private transient NewsService newsService;

	public static final Logger logger = Logger.getLogger(CourseFeed.class);

	private FeedWrapper buildFeedArray(InstituteDetails institute) {
		final List<SyndEntry> entries = new ArrayList<SyndEntry>();

		List<NewsItemInfo> newsEntries = getNewsService().getNewsItems(institute);

		FeedWrapper feedWrapper = new FeedWrapper();
		
		final String serverUrl = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue(); 
		final String linkNewsItem = serverUrl + "views/public/news/newsDetail.faces?news=" ;

		if (newsEntries != null && newsEntries.size() != 0) {
			Collections.reverse(newsEntries);
			for (NewsItemInfo item : newsEntries) {
				this.addEntry(entries, 
						item.getTitle(), 
						linkNewsItem + item.getId(), 
						item.getPublishDate(), 
						item.getText(),
						institute.getName(), 
						item.getPublisherName());
			}

			feedWrapper.setLastModified(newsEntries.get(0).getPublishDate());
		}

		String link = serverUrl + "views/secured/lecture/institute.faces?institute=" + institute.getId();
		
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
		Institute f = Institute.Factory.newInstance();
		f.setId(instituteId);
		InstituteDetails institute = lectureService.getInstitute(f);
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

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

}