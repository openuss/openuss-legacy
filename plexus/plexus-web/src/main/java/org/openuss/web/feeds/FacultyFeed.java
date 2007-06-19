package org.openuss.web.feeds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.FacultyDetails;
import org.openuss.lecture.LectureService;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

public class FacultyFeed extends AbstractFeed{

		private transient LectureService lectureService;
		
		private transient SystemService systemService;
		
		private transient NewsService newsService;
		
		public static final Logger logger = Logger.getLogger(CourseFeed.class);

		private FeedWrapper buildFeedArray(FacultyDetails faculty) {
			final List entries = new ArrayList();
			List newsEntries = getNewsService().getNewsItems(faculty);
			FeedWrapper feedWrapper = new FeedWrapper();
			NewsItemInfo newsItem;
			String link;

			if (newsEntries!=null&&newsEntries.size()!=0) {
				Collections.reverse(newsEntries);
				Iterator i = newsEntries.iterator();
				while (i.hasNext()) {  
					newsItem = (NewsItemInfo) i.next();
					link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/public/news/newsDetail.faces?news="+newsItem.getId();
					this.addEntry(entries, newsItem.getTitle(), link, newsItem.getPublishDate(), newsItem.getText(), faculty.getName(), newsItem.getPublisherName());
				}
				
				newsItem = (NewsItemInfo) newsEntries.get(0);
				feedWrapper.setLastModified(newsItem.getPublishDate());
			}
		    
			link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/secured/lecture/faculty.faces?faculty="+faculty.getId();
			feedWrapper.setWriter(this.convertToXml(faculty.getName(), link, faculty.getDescription(), systemService.getProperty(SystemProperties.COPYRIGHT).getValue(), entries));
			return feedWrapper;
		}	
		
		
	    public FeedWrapper getFeed(Long facultyId)        
	    {
	    	if (facultyId==null||facultyId==0) return null;
	    	Faculty f = Faculty.Factory.newInstance();
	    	f.setId(facultyId);    	
	    	FacultyDetails faculty = lectureService.getFaculty(f);
	    	if (faculty==null) return null;
	        return buildFeedArray(faculty);
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