// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

import java.util.List;

import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.MailInfo;
import org.openuss.newsletter.NewsletterInfo;
import org.openuss.newsletter.SubscriberInfo;
import org.openuss.security.User;

/**
 * @see org.openuss.groups.GroupNewsletterService
 * @author Lutz D. Kramer
 */
public class GroupNewsletterServiceImpl extends GroupNewsletterServiceBase {

	/**
	 * @see org.openuss.groups.GroupNewsletterService#sendPreview(org.openuss.groups.UserGroupInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSendPreview(UserGroupInfo group, MailDetail mail)
			throws java.lang.Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		getNewsletterService().sendPreview(newsletter, mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#getMails(org.openuss.groups.UserGroupInfo)
	 */
	protected List<MailInfo> handleGetMails(UserGroupInfo group) {
		NewsletterInfo newsletter = getNewsletter(group);
		return getNewsletterService().getMails(newsletter, false);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#getMail(org.openuss.newsletter.MailInfo)
	 */
	protected MailDetail handleGetMail(MailInfo mail)
			throws java.lang.Exception {
		return getNewsletterService().getMail(mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#sendMail(org.openuss.groups.UserGroupInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSendMail(UserGroupInfo group, MailDetail mail) {
		NewsletterInfo newsletter = getNewsletter(group);
		getNewsletterService().sendMail(newsletter, mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#subscribe(org.openuss.groups.UserGroupInfo,
	 *      org.openuss.security.User)
	 */
	protected void handleSubscribe(UserGroupInfo group, User user) {
		NewsletterInfo newsletter = getNewsletter(group);
		getNewsletterService().subscribe(newsletter, user);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#unsubscribe(org.openuss.groups.UserGroupInfo,
	 *      org.openuss.security.User)
	 */
	protected void handleUnsubscribe(UserGroupInfo group, User user)
			throws java.lang.Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		if (newsletter.isSubscribed()) {
			getNewsletterService().unsubscribe(newsletter, user);
		}
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#unsubscribe(org.openuss.newsletter.Subscriber)
	 */
	protected void handleUnsubscribe(SubscriberInfo subscriber)
			throws java.lang.Exception {
		getNewsletterService().unsubscribe(subscriber);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#setBlockingState(org.openuss.newsletter.Subscriber)
	 */
	protected void handleSetBlockingState(SubscriberInfo subscriber)
			throws java.lang.Exception {
		getNewsletterService().setBlockingState(subscriber);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#getSubscribers(org.openuss.groups.UserGroupInfo)
	 */
	protected List<SubscriberInfo> handleGetSubscribers(UserGroupInfo group)
			throws java.lang.Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		return getNewsletterService().getSubscribers(newsletter);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#saveMail(org.openuss.groups.UserGroupInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSaveMail(UserGroupInfo group, MailDetail mail)
			throws java.lang.Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		getNewsletterService().saveMail(newsletter, mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#deleteMail(org.openuss.groups.UserGroupInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleDeleteMail(UserGroupInfo group, MailDetail mail)
			throws java.lang.Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		getNewsletterService().deleteMail(newsletter, mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#addNewsletter(org.openuss.groups.UserGroupInfo)
	 */
	protected void handleAddNewsletter(UserGroupInfo group)
			throws java.lang.Exception {
		getNewsletterService().addNewsletter(group, group.getName());
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#getNewsletter(org.openuss.groups.UserGroupInfo)
	 */
	protected NewsletterInfo handleGetNewsletter(UserGroupInfo group)
			throws java.lang.Exception {
		NewsletterInfo newsletter = getNewsletterService().getNewsletter(group);
		if (newsletter == null) {
			addNewsletter(group);
			newsletter = getNewsletterService().getNewsletter(group);
		}
		if (!newsletter.getName().equals(group.getName())) {
			newsletter.setName(group.getName());
			getNewsletterService().updateNewsletter(newsletter);
		}
		return newsletter;
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#updateNewsletter(org.openuss.newsletter.NewsletterInfo)
	 */
	protected void handleUpdateNewsletter(NewsletterInfo newsletter)
			throws java.lang.Exception {
		getNewsletterService().updateNewsletter(newsletter);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#updateMail(org.openuss.groups.UserGroupInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleUpdateMail(UserGroupInfo group, MailDetail mail)
			throws java.lang.Exception {
		getNewsletterService().updateMail(group, mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#exportSubscribers(org.openuss.groups.UserGroupInfo)
	 */
	protected String handleExportSubscribers(UserGroupInfo group)
			throws java.lang.Exception {
		return getNewsletterService().exportSubscribers(getNewsletter(group));
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#cancelSending(org.openuss.newsletter.MailInfo)
	 */
	protected void handleCancelSending(MailInfo mail)
			throws java.lang.Exception {
		getNewsletterService().cancelSending(mail);
	}

}