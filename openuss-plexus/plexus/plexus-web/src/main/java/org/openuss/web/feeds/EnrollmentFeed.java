// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.web.feeds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.EnrollmentInfo;
import org.openuss.lecture.EnrollmentService;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

public class EnrollmentFeed extends AbstractFeed{
	

	private transient EnrollmentService enrollmentService;
	
	private transient SystemService systemService;
	
	private transient NewsService newsService;
	
	public static final Logger logger = Logger.getLogger(EnrollmentFeed.class);

	private FeedWrapper buildFeedArray(EnrollmentInfo enrollment) {
		final List entries = new ArrayList();
		List newsEntries = getNewsService().getNewsItems(enrollment);
		if (newsEntries==null||newsEntries.size()==0) return null;
		FeedWrapper feedWrapper = new FeedWrapper();
	    
		Iterator i = newsEntries.iterator();
		NewsItemInfo newsItem;
		String link;
		while (i.hasNext()) {  
			newsItem = (NewsItemInfo) i.next();
			link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/public/news/newsDetail.faces?news="+newsItem.getId();
			this.addEntry(entries, newsItem.getTitle(), link, newsItem.getPublishDate(), newsItem.getText(), enrollment.getName(), newsItem.getPublisherName());
		}
		
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/secured/enrollment/main.faces?"+enrollment.getId();
		
		feedWrapper.setWriter(this.convertToXml(enrollment.getName(), link, enrollment.getDescription(), systemService.getProperty(SystemProperties.COPYRIGHT).getValue(), entries));
		newsItem = (NewsItemInfo) newsEntries.get(newsEntries.size()-1);
		feedWrapper.setLastModified(newsItem.getPublishDate());
		return feedWrapper;
	}	
	
	
    /**
     * @see org.openuss.feed.FeedService#getRssFeedForEnrollment(org.openuss.lecture.EnrollmentInfo)
     */
    public FeedWrapper getFeed(Long enrollmentId)        
    {
    	if (enrollmentId==null||enrollmentId==0) return null;
    	Enrollment e = Enrollment.Factory.newInstance();
    	e.setId(enrollmentId);    	
    	EnrollmentInfo enrollment = getEnrollmentService().getEnrollmentInfo(getEnrollmentService().getEnrollment(e));
    	if (enrollment==null) return null;

        return buildFeedArray(enrollment);
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

	public EnrollmentService getEnrollmentService() {
		return enrollmentService;
	}

	public void setEnrollmentService(EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}

}