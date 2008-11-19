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
import org.openuss.course.newsletter.CourseNewsletterService;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.MailInfo;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

import com.sun.syndication.feed.synd.SyndEntry;

public class NewsletterFeed extends AbstractFeed {

	private transient CourseService courseService;

	private transient SystemService systemService;

	private transient CourseNewsletterService courseNewsletterService;

	public static final Logger logger = Logger.getLogger(NewsletterFeed.class);

	@SuppressWarnings("unchecked")
	private FeedWrapper buildFeedArray(CourseInfo course) {
		final List<SyndEntry> entries = new ArrayList<SyndEntry>();
		MailInfo mailInfo;
		MailDetail mailDetail;
		String link;
		FeedWrapper feedWrapper = new FeedWrapper();
		List<MailInfo> mails = getCourseNewsletterService().getMails(course);

		if (mails != null && mails.size() != 0) {
			Collections.reverse(mails);
			Iterator<MailInfo> i = mails.iterator();
			while (i.hasNext()) {
				mailInfo = (MailInfo) i.next();
				link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()
						+ "/views/secured/newsletter/showmail.faces?mail=" + mailInfo.getId();
				mailDetail = getCourseNewsletterService().getMail(mailInfo);
				this.addEntry(entries, mailDetail.getSubject(), link, mailDetail.getSendDate(), mailDetail.getText(),
						course.getName(), course.getName());
			}
			mailDetail = getCourseNewsletterService().getMail((MailInfo) mails.get(0));
			feedWrapper.setLastModified(mailDetail.getSendDate());
		}

		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()
				+ "/views/secured/newsletter/newsletter.faces?course=" + course.getId();

		feedWrapper.setWriter(this.convertToXml("["+i18n("rss_newsletter", null, locale())+"] "+course.getName(),
				link, course.getDescription(), systemService
				.getProperty(SystemProperties.COPYRIGHT).getValue(), entries));

		return feedWrapper;
	}

	/**
	 * @see org.openuss.feed.FeedService#getRssFeedForCourse(org.openuss.lecture.CourseInfo)
	 */
	public FeedWrapper getFeed(Long courseId) {
		if (courseId == null || courseId == 0) {
			return null;
		}
		CourseInfo course = getCourseService().findCourse(courseId);
		if (course == null) {
			return null;
		}

		return buildFeedArray(course);
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

	public CourseNewsletterService getCourseNewsletterService() {
		return courseNewsletterService;
	}

	public void setCourseNewsletterService(CourseNewsletterService courseNewsletterService) {
		this.courseNewsletterService = courseNewsletterService;
	}

}