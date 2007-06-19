// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.web.feeds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

public class CourseFeed extends AbstractFeed{
	

	private transient CourseService courseService;
	
	private transient SystemService systemService;
	
	private transient NewsService newsService;
	
	public static final Logger logger = Logger.getLogger(CourseFeed.class);

	private FeedWrapper buildFeedArray(CourseInfo course) {
		final List entries = new ArrayList();
		List newsEntries = getNewsService().getNewsItems(course);
		FeedWrapper feedWrapper = new FeedWrapper();
		NewsItemInfo newsItem;
		String link;
	    
		if (newsEntries!=null&&newsEntries.size()!=0) {
			Iterator i = newsEntries.iterator();
			Collections.reverse(newsEntries);
			while (i.hasNext()) {  
				newsItem = (NewsItemInfo) i.next();
				link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/public/news/newsDetail.faces?news="+newsItem.getId();
				this.addEntry(entries, newsItem.getTitle(), link, newsItem.getPublishDate(), newsItem.getText(), course.getName(), newsItem.getPublisherName());
			}
			
			
			newsItem = (NewsItemInfo) newsEntries.get(0);
			feedWrapper.setLastModified(newsItem.getPublishDate());
		}

		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/secured/course/main.faces?"+course.getId();
		feedWrapper.setWriter(this.convertToXml(course.getName(), link, course.getDescription(), systemService.getProperty(SystemProperties.COPYRIGHT).getValue(), entries));
		
		return feedWrapper;
	}	
	
	
    /**
     * @see org.openuss.feed.FeedService#getRssFeedForCourse(org.openuss.lecture.CourseInfo)
     */
    public FeedWrapper getFeed(Long courseId)        
    {
    	if (courseId==null||courseId==0) return null;
    	Course e = Course.Factory.newInstance();
    	e.setId(courseId);    	
    	CourseInfo course = getCourseService().getCourseInfo(getCourseService().getCourse(e));
    	if (course==null) return null;

        return buildFeedArray(course);
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

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

}