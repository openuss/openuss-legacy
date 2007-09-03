package org.openuss.migration.from20to30;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.migration.legacy.domain.Mailinglist2;
import org.openuss.migration.legacy.domain.Mailinglistmessage2;
import org.openuss.newsletter.Mail;
import org.openuss.newsletter.MailDao;
import org.openuss.newsletter.MailingStatus;
import org.openuss.newsletter.Newsletter;
import org.openuss.newsletter.NewsletterDao;
import org.openuss.newsletter.NewsletterInfo;
import org.openuss.newsletter.NewsletterService;

/**
 * Course NewsLetter Import
 * 
 * @author Ingo Dueppe
 */
public class NewsLetterImport extends DefaultImport {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(NewsLetterImport.class);

	/** MailDao */
	private MailDao mailDao;

	/** NewsletterService */
	private NewsletterService newsletterService;

	/** NewsletterDao */
	private NewsletterDao newsletterDao;

	/** CourseDao */
	private CourseDao courseDao;

	public void perform() {
		importSubscribers();
		importMails();
	}

	private void importSubscribers() {
		ScrollableResults results = legacyDao.loadAllMailingListSubscribers();

		Mailinglist2 subscription = null;
		String enrollmentPk = null;
		NewsletterInfo newsLetter = null;
		Long courseId = null;

		while (results.next()) {
			evict(subscription);
			subscription = (Mailinglist2) results.get()[0];

			if (!StringUtils.equals(enrollmentPk, subscription.getEnrollmentpk())) {
				enrollmentPk = subscription.getEnrollmentpk();
				courseId = identifierDao.getId(enrollmentPk);
				newsLetter = loadNewsLetter(courseId);
			}

			Long userId = identifierDao.getUserId(subscription.getMemberpk());
			if (userId != null && newsLetter != null) {
				identifierDao.insertNewsletterSubscriber(newsLetter.getId(), userId);
			}
		}
	}

	private NewsletterInfo loadNewsLetter(Long courseId) {
		if (courseId != null) {
			NewsletterInfo newsLetter;
			newsLetter = newsletterService.getNewsletter(new DefaultDomainObject(courseId));
			if (newsLetter == null) {
				Course course = courseDao.load(courseId);
				if (course != null) {
					logger.debug("create new newsletter for course " + course.getName());
					newsletterService.addNewsletter(course, StringUtils.abbreviate(course.getName(), 64));
					newsLetter = newsletterService.getNewsletter(course);
				} else {
					logger.debug("course " + courseId + " not found!");
				}
			}
			return newsLetter;
		} else {
			return null;
		}
	}

	private void importMails() {
		Session legacySession = legacySessionFactory.getCurrentSession();
		ScrollableResults results = legacyDao.loadAllMailingListMessages();
		Mailinglistmessage2 message = null;
		while (results.next()) {
			if (message != null) {
				legacySession.evict(message);
			}
			message = (Mailinglistmessage2) results.get()[0];
			Long courseId = identifierDao.getId(message.getEnrollmentPk());
			List<Newsletter> list = newsletterDao.findByDomainIdentifier(courseId);
			if (list != null && list.size() > 0) {
				Newsletter newsletter = (Newsletter) newsletterDao.findByDomainIdentifier(courseId).get(0);
				Mail mail = createMail(message);
				mail.setNewsletter(newsletter);
				mailDao.create(mail);
			}
		}
	}

	private Mail createMail(Mailinglistmessage2 message) {
		Mail mail = Mail.Factory.newInstance();
		mail.setStatus(MailingStatus.SEND);
		mail.setSendDate(message.getDdate());
		mail.setSubject(message.getTitle());
		mail.setText(message.getBody());
		return mail;
	}

	public void setNewsletterService(NewsletterService newsletterService) {
		this.newsletterService = newsletterService;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public void setMailDao(MailDao mailDao) {
		this.mailDao = mailDao;
	}

	public void setNewsletterDao(NewsletterDao newsletterDao) {
		this.newsletterDao = newsletterDao;
	}

}
