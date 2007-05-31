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
    private final List entries = new ArrayList();
    private final List categories = new ArrayList();
    SyndEntry entry;
    SyndContent description;
	SyndCategory category;
	

	private String populateCategoryArray(EnrollmentInfo enrollment, String title, String link, String description, String copyright) {
		 
		List newsEntries = null;
		//List blogInfo = null;
		//List blogCategories = null;
		
	    newsEntries = getNewsService().getNewsItems(enrollment); 
	    	
		Iterator i = newsEntries.iterator();
		NewsItemInfo newsItem;
		while (!i.hasNext()) {  
			newsItem = (NewsItemInfo) i.next();
			this.addEntry(newsItem.getTitle(), link, newsItem.getPublishDate(), newsItem.getText(), enrollment.getName(), newsItem.getPublisherName());
		}

		return this.doSyndication(title, link, description, copyright);
	}	
	
	@SuppressWarnings("unchecked")
	private void addEntry(String title, String link, Date date, String blogContent, String cat, String author) {
		
		try {
 
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
            ex.printStackTrace();
            System.out.println("ERROR: "+ex.getMessage());
                             }
		
	}	
	
	/**
	 * This method is called last after you have added all your entries and have specified your
	 * feed type and filename. This actually does the work
	 * <p>
	 * NOTE: This has static content entered in to the fields! You must have access to the source
	 * code edit this method or else you will be publishing content as the Post Modern Banter Blog
	 * Yes, I should change this immediately. Ideally, it would take values from the web.xml file itself.
	 * <p>
	 * @throws Exception
	 */
	private String doSyndication(String title, String link, String description_loc, String copyright) {
            try {
 
                final SyndFeed feed = new SyndFeedImpl();
                feed.setFeedType(feedType);
 
                feed.setTitle(title);
                feed.setLink(link);
                feed.setDescription(description_loc);
			    feed.setCopyright(copyright);
				
                feed.setEntries(entries);
			   
                final Writer writer = new StringWriter();
                final SyndFeedOutput output = new SyndFeedOutput();
                output.output(feed,writer);
                return writer.toString();
                 
            }
			
            catch (Exception ex) {
            	logger.error("Unknown error: ", ex);
            }
            return null;
	}	
	
    /**
     * @see org.openuss.feed.FeedService#getRssFeedForEnrollment(org.openuss.lecture.EnrollmentInfo)
     */
    protected java.lang.String handleGetRssFeedForEnrollment(EnrollmentInfo enrollment)
        throws java.lang.Exception
    {    	
        return populateCategoryArray(enrollment, enrollment.getName(), "link"+enrollment.getId(), enrollment.getDescription(), "OpenUSS");
    }

}