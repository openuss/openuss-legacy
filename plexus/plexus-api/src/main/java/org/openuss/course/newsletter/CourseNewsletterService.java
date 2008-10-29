package org.openuss.course.newsletter;

import java.util.List;

import org.openuss.lecture.CourseInfo;
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.MailInfo;
import org.openuss.newsletter.NewsletterInfo;
import org.openuss.newsletter.SubscriberInfo;

/**
 * @author Ingo Dueppe
 */
public interface CourseNewsletterService {

	public void sendPreview(CourseInfo course, MailDetail mail);

	public List getMails(CourseInfo course);

	public MailDetail getMail(MailInfo mail);

	/**
	 * Sets mail into state INQUEUE
	 */
	public void sendMail(CourseInfo course, MailDetail mail);

	public void subscribe(CourseInfo course, org.openuss.security.UserInfo user);

	public void unsubscribe(CourseInfo course, org.openuss.security.UserInfo user);

	public void unsubscribe(SubscriberInfo subscriber);

	public void setBlockingState(SubscriberInfo subscriber);

	public List getSubscribers(CourseInfo course);

	public void saveMail(CourseInfo course, MailDetail mail);

	public void deleteMail(CourseInfo course, MailDetail mail);

	public void addNewsletter(CourseInfo course);

	public NewsletterInfo getNewsletter(CourseInfo course);

	public void updateNewsletter(NewsletterInfo newsletter);

	public void updateMail(CourseInfo course, MailDetail mail);

	public String exportSubscribers(CourseInfo course);

	public void cancelSending(MailInfo mail);

}
