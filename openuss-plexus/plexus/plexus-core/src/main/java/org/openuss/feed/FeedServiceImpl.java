// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.feed;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.EnrollmentInfo;
import org.openuss.news.NewsItemInfo;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * @see org.openuss.feed.FeedService
 */
public class FeedServiceImpl
    extends org.openuss.feed.FeedServiceBase
{
	public static final Logger logger = Logger.getLogger(FeedServiceImpl.class);

	private static String feedType = null;
	

	private FeedWrapper buildFeedArray(EnrollmentInfo enrollment) {
		final List entries = new ArrayList();
		List newsEntries = getNewsService().getNewsItems(enrollment);
		FeedWrapper feedWrapper = new FeedWrapper();
	    
		Iterator i = newsEntries.iterator();
		NewsItemInfo newsItem;
		while (i.hasNext()) {  
			newsItem = (NewsItemInfo) i.next();
			this.addEntry(entries, newsItem.getTitle(), "link", newsItem.getPublishDate(), newsItem.getText(), enrollment.getName(), newsItem.getPublisherName());
		}

		feedWrapper.setWriter(this.convertToXml(enrollment.getName(), "link", enrollment.getDescription(), "Copyright OpenUSS", entries));
		newsItem = (NewsItemInfo) newsEntries.get(0);
		feedWrapper.setLastModified(newsItem.getPublishDate());
		return feedWrapper;
	}	
	
	@SuppressWarnings("unchecked")
	private void addEntry(List entries, String title, String link, Date date, String blogContent, String cat, String author) {
		
		try {			
			final List categories = new ArrayList();
			SyndEntry entry;
			SyndContent description;
			SyndCategory category;
 
	        entry = new SyndEntryImpl();
		    entry.setAuthor(author);
	        entry.setTitle(title);
	        entry.setLink(link);
	        entry.setPublishedDate(date);
	        description = new SyndContentImpl();
	        description.setType("text/plain");
	        description.setValue(blogContent);
	        entry.setDescription(description);
		    category = new SyndCategoryImpl();
			category.setName(cat);
			categories.add(category);
			entry.setCategories(categories);
			categories.remove(category);
			entries.add(entry);
		}
		
        catch (Exception ex) {
        	logger.error("Unknown error occured:", ex);
        }
		
	}	
	
	private Writer convertToXml(String title, String link, String description_loc, String copyright, List entries) {
            try {
 
                final SyndFeed feed = new SyndFeedImpl();
                feed.setEncoding("ISO-8859-1");
                feed.setTitle(title);
                feed.setLink(link);
                feed.setDescription("testDescription");
                feed.setCopyright(copyright);
                feed.setFeedType("rss_2.0");

                feed.setEntries(entries);
                final Writer writer = new StringWriter();
                final SyndFeedOutput output = new SyndFeedOutput();
                output.output(feed,writer);                
                return writer;
                 
            }
			
            catch (Exception ex) {
            	logger.error("Unknown error: ", ex);
            }
            return null;
	}	
	
    /**
     * @see org.openuss.feed.FeedService#getRssFeedForEnrollment(org.openuss.lecture.EnrollmentInfo)
     */
    protected FeedWrapper handleGetRssFeedForEnrollment(Long enrollmentId)
        throws java.lang.Exception
    {
    	if (enrollmentId==null||enrollmentId==0) return null;
    	Enrollment e = Enrollment.Factory.newInstance();
    	e.setId(enrollmentId);    	
    	EnrollmentInfo enrollment = getEnrollmentService().getEnrollmentInfo(getEnrollmentService().getEnrollment(e));
    	if (enrollment==null) return null;

        return buildFeedArray(enrollment);
    }

}