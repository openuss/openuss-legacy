package org.openuss.web.feeds;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.openuss.security.SecurityService;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

public abstract class AbstractFeed implements MessageSourceAware {

	public static final String RSS_2_0 = "rss_2.0";

	public static final String ISO_8859_1 = "ISO-8859-1";

	public static final String TEXT_HTML = "text/html";
	
	public static final Logger logger = Logger.getLogger(AbstractFeed.class);
	
	private MessageSource messageSource;
	
	private MessageSource i18nMessageSource;
	
	protected SecurityService securityService;
	
	public String i18n(String code, Object[] args, Locale locale){
		return messageSource.getMessage(code, args, locale);
	}

	@SuppressWarnings("unchecked")
	public void addEntry(List entries, String title, String link, Date date, String blogContent, String cat, String author) {
		
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
	        description.setType(TEXT_HTML);
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
	
	public Writer convertToXml(String title, String link, String description, String copyright, List entries) {
            try {
 
                final SyndFeed feed = new SyndFeedImpl();
                feed.setEncoding(ISO_8859_1);
                feed.setTitle(title);
                feed.setLink(link);
                if (description==null) feed.setDescription("");
                else if (description != null)feed.setDescription(description);
                feed.setCopyright(copyright);
                feed.setFeedType(RSS_2_0);
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

	public void setMessageSource(MessageSource messageSource) {
		//this.messageSource = messageSource;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public MessageSource getI18nMessageSource() {
		return i18nMessageSource;
	}

	public void setI18nMessageSource(MessageSource messageSource) {
		i18nMessageSource = messageSource;
		this.messageSource = messageSource;
	}

}