// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.course.mailinglist;

import java.util.List;

import org.openuss.lecture.CourseInfo;
import org.openuss.mailinglist.MailDetail;
import org.openuss.mailinglist.MailInfo;
import org.openuss.mailinglist.MailingListInfo;
import org.openuss.mailinglist.SubscriberInfo;
import org.openuss.security.User;

/**
 * @see org.openuss.course.mailinglist.CourseMailingListService
 */
public class CourseMailingListServiceImpl extends CourseMailingListServiceBase {

	protected MailingListInfo handleGetMailingList(CourseInfo course) {
		MailingListInfo mailingList = getMailingListService().getMailingList(course);
		if (mailingList == null) {
			addMailingList(course);
			mailingList = getMailingListService().getMailingList(course);
		}
		if (mailingList.getName().equals(course.getName())) {
			mailingList.setName(course.getName());
			getMailingListService().updateMailingList(mailingList);
		}
		return mailingList;
	}

	/**
	 * @see org.openuss.course.mailinglist.CourseMailingListService#sendPreview(org.openuss.lecture.CourseInfo,
	 *      org.openuss.mailinglist.MailDetail)
	 */
	protected void handleSendPreview(CourseInfo course, MailDetail mail) throws Exception {
		MailingListInfo mailingList = getMailingList(course);
		getMailingListService().sendPreview(mailingList, mail);
	}

	/**
	 * @see org.openuss.course.mailinglist.CourseMailingListService#getMails(org.openuss.lecture.CourseInfo)
	 */
	protected List handleGetMails(CourseInfo course) throws Exception {
		MailingListInfo mailingList = getMailingList(course);
		return getMailingListService().getMails(mailingList, false);
	}

	/**
	 * @see org.openuss.course.mailinglist.CourseMailingListService#getMail(org.openuss.mailinglist.MailInfo)
	 */
	protected org.openuss.mailinglist.MailDetail handleGetMail(MailInfo mail) throws Exception {
		return getMailingListService().getMail(mail);
	}

	/**
	 * @see org.openuss.course.mailinglist.CourseMailingListService#sendMail(org.openuss.lecture.CourseInfo,
	 *      org.openuss.mailinglist.MailDetail)
	 */
	protected void handleSendMail(CourseInfo course, MailDetail mail) throws Exception {
		MailingListInfo mailingList = getMailingList(course);
		getMailingListService().sendMail(mailingList, mail);
	}

	/**
	 * @see org.openuss.course.mailinglist.CourseMailingListService#subscribe(org.openuss.lecture.CourseInfo,
	 *      org.openuss.security.User)
	 */
	protected void handleSubscribe(CourseInfo course, User user) throws Exception {
		MailingListInfo mailingList = getMailingList(course);
		getMailingListService().subscribe(mailingList, user);
	}

	/**
	 * @see org.openuss.course.mailinglist.CourseMailingListService#unsubscribe1(org.openuss.lecture.CourseInfo,
	 *      org.openuss.security.User)
	 */
	protected void handleUnsubscribe(CourseInfo course, User user) throws Exception {
		MailingListInfo mailingList = getMailingList(course);
		getMailingListService().unsubscribe(mailingList, user);
	}

	/**
	 * @see org.openuss.course.mailinglist.CourseMailingListService#unsubscribe(org.openuss.mailinglist.SubscriberInfo)
	 */
	protected void handleUnsubscribe(SubscriberInfo subscriber) throws Exception {
		getMailingListService().unsubscribe(subscriber);
	}

	/**
	 * @see org.openuss.course.mailinglist.CourseMailingListService#setBlockingState(org.openuss.mailinglist.SubscriberInfo)
	 */
	protected void handleSetBlockingState(SubscriberInfo subscriber) throws Exception {
		getMailingListService().setBlockingState(subscriber);
	}

	/**
	 * @see org.openuss.course.mailinglist.CourseMailingListService#getSubscribers(org.openuss.lecture.CourseInfo)
	 */
	protected java.util.List handleGetSubscribers(org.openuss.lecture.CourseInfo course)
			throws java.lang.Exception {
		MailingListInfo mailingList = getMailingList(course);
		return getMailingListService().getSubscribers(mailingList);
	}

	/**
	 * @see org.openuss.course.mailinglist.CourseMailingListService#saveMail(org.openuss.lecture.CourseInfo,
	 *      org.openuss.mailinglist.MailDetail)
	 */
	protected void handleSaveMail(CourseInfo course, MailDetail mail) throws Exception {
		MailingListInfo mailingList = getMailingList(course);
		getMailingListService().saveMail(mailingList, mail);
	}

	/**
	 * @see org.openuss.course.mailinglist.CourseMailingListService#deleteMail(org.openuss.lecture.CourseInfo,
	 *      org.openuss.mailinglist.MailDetail)
	 */
	protected void handleDeleteMail(CourseInfo course, MailDetail mail) throws Exception {
		MailingListInfo mailingList = getMailingList(course);
		getMailingListService().deleteMail(mailingList, mail);
	}

	@Override
	protected void handleAddMailingList(CourseInfo course) throws Exception {
		getMailingListService().addMailingList(course, course.getName());
	}

	@Override
	protected void handleUpdateMailingList(MailingListInfo mailingList) throws Exception {
		getMailingListService().updateMailingList(mailingList);
	}

	@Override
	protected void handleUpdateMail(CourseInfo course, MailDetail mail) throws Exception {
		getMailingListService().updateMail(course, mail);
	}

	@Override
	protected String handleExportSubscribers(CourseInfo course) throws Exception {
		return getMailingListService().exportSubscribers(getMailingList(course));
	}

	@Override
	protected void handleCancelSending(MailInfo mailInfo) throws Exception {
		getMailingListService().cancelSending(mailInfo);
	}

}