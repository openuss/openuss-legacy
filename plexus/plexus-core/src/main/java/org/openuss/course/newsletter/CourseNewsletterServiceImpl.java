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
import org.openuss.newsletter.NewsletterApplicationException;
import org.openuss.newsletter.NewsletterInfo;
import org.openuss.newsletter.SubscriberInfo;
import org.openuss.security.UserInfo;

/**
 * @see org.openuss.course.newsletter.CourseNewsletterService
 */
public class CourseNewsletterServiceImpl extends CourseNewsletterServiceBase {

	protected NewsletterInfo handleGetNewsletter(final CourseInfo course) {
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
	protected void handleSendPreview(final CourseInfo course, final MailDetail mail) {
		getNewsletterService().sendPreview(getNewsletter(course), mail);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#getMails(org.openuss.lecture.CourseInfo)
	 */
	protected List<MailInfo> handleGetMails(final CourseInfo course) {
		return getNewsletterService().getMails(getNewsletter(course), false);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#getMail(org.openuss.newsletter.MailInfo)
	 */
	protected MailDetail handleGetMail(final MailInfo mail) {
		return getNewsletterService().getMail(mail);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#sendMail(org.openuss.lecture.CourseInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSendMail(final CourseInfo course, final MailDetail mail) {
		getNewsletterService().sendMail(getNewsletter(course), mail);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#subscribe(org.openuss.lecture.CourseInfo,
	 *      org.openuss.security.User)
	 */
	protected void handleSubscribe(final CourseInfo course, final UserInfo user) {
		getNewsletterService().subscribe(getNewsletter(course), user);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#unsubscribe1(org.openuss.lecture.CourseInfo,
	 *      org.openuss.security.User)
	 */
	protected void handleUnsubscribe(final CourseInfo course, final UserInfo user) {
		NewsletterInfo newsletter = getNewsletter(course);
		if(newsletter.isSubscribed()){
			getNewsletterService().unsubscribe(newsletter, user);
		}
		
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#unsubscribe(org.openuss.newsletter.SubscriberInfo)
	 */
	protected void handleUnsubscribe(final SubscriberInfo subscriber) {
		getNewsletterService().unsubscribe(subscriber);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#setBlockingState(org.openuss.newsletter.SubscriberInfo)
	 */
	protected void handleSetBlockingState(final SubscriberInfo subscriber) {
		getNewsletterService().setBlockingState(subscriber);
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#getSubscribers(org.openuss.lecture.CourseInfo)
	 */
	protected List<SubscriberInfo> handleGetSubscribers(final CourseInfo course) {
		return getNewsletterService().getSubscribers(getNewsletter(course));
	}

	/**
	 * @see org.openuss.course.newsletter.CourseNewsletterService#saveMail(org.openuss.lecture.CourseInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSaveMail(final CourseInfo course, final MailDetail mail) {
		getNewsletterService().saveMail(getNewsletter(course), mail);
	}

	/**
	 * @throws NewsletterApplicationException 
	 * @see org.openuss.course.newsletter.CourseNewsletterService#deleteMail(org.openuss.lecture.CourseInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleDeleteMail(final CourseInfo course, final MailDetail mail) throws NewsletterApplicationException {
		getNewsletterService().deleteMail(getNewsletter(course), mail);
	}

	@Override
	protected void handleAddNewsletter(final CourseInfo course) {
		getNewsletterService().addNewsletter(course, course.getName());
	}

	@Override
	protected void handleUpdateNewsletter(final NewsletterInfo newsletter) {
		getNewsletterService().updateNewsletter(newsletter);
	}

	@Override
	protected void handleUpdateMail(final CourseInfo course, final MailDetail mail) {
		getNewsletterService().updateMail(course, mail);
	}

	@Override
	protected String handleExportSubscribers(final CourseInfo course) {
		return getNewsletterService().exportSubscribers(getNewsletter(course));
	}

	@Override
	protected void handleCancelSending(final MailInfo mailInfo) {
		getNewsletterService().cancelSending(mailInfo);
	}

}