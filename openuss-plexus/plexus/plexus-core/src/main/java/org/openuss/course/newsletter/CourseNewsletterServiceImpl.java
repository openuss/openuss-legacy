// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.course.newsletter;

import java.util.List;

import org.openuss.lecture.CourseInfo;
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.MailInfo;
import org.openuss.newsletter.NewsletterInfo;
import org.openuss.newsletter.SubscriberInfo;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

/**
 * @see org.openuss.course.newsletter.CourseNewsletterService
 */
public class CourseNewsletterServiceImpl extends CourseNewsletterServiceBase {

	protected NewsletterInfo handleGetNewsletter(CourseInfo course) {
		NewsletterInfo newsletter = getNewsletterService().getNewsletter(course);
		if (newsletter == null) {
			addNewsletter(course);
			newsletter = getNewsletterService().getNewsletter(course);
		}
		if (!newsletter.getName().equals(course.getName())) {
			newsletter.setName(course.getName());
			getNewsletterService().updateNewsletter(newsletter);
		}
		return newsletter;
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#sendPreview(org.openuss.lecture.CourseInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSendPreview(CourseInfo course, MailDetail mail) throws Exception {
		NewsletterInfo newsletter = getNewsletter(course);
		getNewsletterService().sendPreview(newsletter, mail);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#getMails(org.openuss.lecture.CourseInfo)
	 */
	protected List<MailInfo> handleGetMails(CourseInfo course) throws Exception {
		NewsletterInfo newsletter = getNewsletter(course);
		return getNewsletterService().getMails(newsletter, false);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#getMail(org.openuss.newsletter.MailInfo)
	 */
	protected org.openuss.newsletter.MailDetail handleGetMail(MailInfo mail) throws Exception {
		return getNewsletterService().getMail(mail);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#sendMail(org.openuss.lecture.CourseInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSendMail(CourseInfo course, MailDetail mail) throws Exception {
		NewsletterInfo newsletter = getNewsletter(course);
		getNewsletterService().sendMail(newsletter, mail);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#subscribe(org.openuss.lecture.CourseInfo,
	 *      org.openuss.security.User)
	 */
	protected void handleSubscribe(CourseInfo course, UserInfo user) throws Exception {
		NewsletterInfo newsletter = getNewsletter(course);
		getNewsletterService().subscribe(newsletter, user);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#unsubscribe1(org.openuss.lecture.CourseInfo,
	 *      org.openuss.security.User)
	 */
	protected void handleUnsubscribe(CourseInfo course, UserInfo user) throws Exception {
		NewsletterInfo newsletter = getNewsletter(course);
		getNewsletterService().unsubscribe(newsletter, user);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#unsubscribe(org.openuss.newsletter.SubscriberInfo)
	 */
	protected void handleUnsubscribe(SubscriberInfo subscriber) throws Exception {
		getNewsletterService().unsubscribe(subscriber);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#setBlockingState(org.openuss.newsletter.SubscriberInfo)
	 */
	protected void handleSetBlockingState(SubscriberInfo subscriber) throws Exception {
		getNewsletterService().setBlockingState(subscriber);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#getSubscribers(org.openuss.lecture.CourseInfo)
	 */
	protected List<SubscriberInfo> handleGetSubscribers(CourseInfo course) throws Exception {
		NewsletterInfo newsletter = getNewsletter(course);
		return getNewsletterService().getSubscribers(newsletter);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#saveMail(org.openuss.lecture.CourseInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSaveMail(CourseInfo course, MailDetail mail) throws Exception {
		NewsletterInfo newsletter = getNewsletter(course);
		getNewsletterService().saveMail(newsletter, mail);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#deleteMail(org.openuss.lecture.CourseInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleDeleteMail(CourseInfo course, MailDetail mail) throws Exception {
		NewsletterInfo newsletter = getNewsletter(course);
		getNewsletterService().deleteMail(newsletter, mail);
	}

	@Override
	protected void handleAddNewsletter(CourseInfo course) throws Exception {
		getNewsletterService().addNewsletter(course, course.getName());
	}

	@Override
	protected void handleUpdateNewsletter(NewsletterInfo newsletter) throws Exception {
		getNewsletterService().updateNewsletter(newsletter);
	}

	@Override
	protected void handleUpdateMail(CourseInfo course, MailDetail mail) throws Exception {
		getNewsletterService().updateMail(course, mail);
	}

	@Override
	protected String handleExportSubscribers(CourseInfo course) throws Exception {
		return getNewsletterService().exportSubscribers(getNewsletter(course));
	}

	@Override
	protected void handleCancelSending(MailInfo mailInfo) throws Exception {
		getNewsletterService().cancelSending(mailInfo);
	}

}