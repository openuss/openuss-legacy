package org.openuss.web.feeds;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
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

	public static final String ENCODING = "UTF-8";

	public static final String TEXT_HTML = "text/html";

	public static final Logger logger = Logger.getLogger(AbstractFeed.class);

	private MessageSource messageSource;

	protected SecurityService securityService;

	public String i18n(final String code, final Object[] args, final Locale locale) {
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
			entry.setAuthor(StringUtils.trimToEmpty(author));
			entry.setTitle(StringUtils.trimToEmpty(title));
			entry.setLink(StringUtils.trimToEmpty(link));
			entry.setPublishedDate(date);
			description = new SyndContentImpl();
			description.setType(TEXT_HTML);
			description.setValue(StringUtils.trimToEmpty(blogContent));
			entry.setDescription(description);
			category = new SyndCategoryImpl();
			category.setName(StringUtils.trimToEmpty(cat));
			categories.add(category);
			entry.setCategories(categories);
			categories.remove(category);
			entries.add(entry);
		}

		catch (Exception ex) {
			logger.error("Unknown error occured:", ex);
		}

	}

	public Writer convertToXml(String title, String link, String description, String copyright, List<SyndEntry> entries) {
		try {
			final SyndFeed feed = new SyndFeedImpl();
			feed.setEncoding(ENCODING);
			feed.setTitle(title);
			feed.setLink(link);
			if (description == null) {
				feed.setDescription("");
			} else if (description != null) {
				feed.setDescription(description);
			}
			feed.setCopyright(copyright);
			feed.setFeedType(RSS_2_0);
			feed.setEntries(entries);

			final Writer writer = new StringWriter();
			final SyndFeedOutput output = new SyndFeedOutput();
			output.output(feed, writer);
			return writer;
		} catch (Exception ex) {
			logger.error("Unknown error: ", ex);
			return null;
		}
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	protected Locale locale() {
		Locale result = Locale.getDefault();
		UserInfo user = getSecurityService().getCurrentUser();
		if (user != null) {
			result = new Locale(user.getLocale());
		}
		return result;
	}

}