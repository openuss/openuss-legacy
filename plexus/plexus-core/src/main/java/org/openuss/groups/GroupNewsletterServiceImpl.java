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
 */
public class GroupNewsletterServiceImpl extends GroupNewsletterServiceBase {

	/**
	 * @see org.openuss.groups.GroupNewsletterService#sendPreview(org.openuss.groups.GroupInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSendPreview(GroupInfo group, MailDetail mail)
			throws java.lang.Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		getNewsletterService().sendPreview(newsletter, mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#getMails(org.openuss.groups.GroupInfo)
	 */
	protected List<MailInfo> handleGetMails(GroupInfo group) throws Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		return getNewsletterService().getMails(newsletter, false);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#getMail(org.openuss.newsletter.MailInfo)
	 */
	protected MailDetail handleGetMail(MailInfo mail) throws java.lang.Exception {
		return getNewsletterService().getMail(mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#sendMail(org.openuss.groups.GroupInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSendMail(GroupInfo group, MailDetail mail) throws Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		getNewsletterService().sendMail(newsletter, mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#subscribe(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.User)
	 */
	protected void handleSubscribe(GroupInfo group, User user) throws Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		getNewsletterService().subscribe(newsletter, user);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#unsubscribe(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.User)
	 */
	protected void handleUnsubscribe(GroupInfo group, User user) throws java.lang.Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		if(newsletter.isSubscribed()){
			getNewsletterService().unsubscribe(newsletter, user);
		}
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#unsubscribe(org.openuss.newsletter.Subscriber)
	 */
	protected void handleUnsubscribe(SubscriberInfo subscriber) throws java.lang.Exception {
		getNewsletterService().unsubscribe(subscriber);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#setBlockingState(org.openuss.newsletter.Subscriber)
	 */
	protected void handleSetBlockingState(SubscriberInfo subscriber) throws java.lang.Exception {
		getNewsletterService().setBlockingState(subscriber);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#getSubscribers(org.openuss.groups.GroupInfo)
	 */
	protected List<SubscriberInfo> handleGetSubscribers(GroupInfo group) throws java.lang.Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		return getNewsletterService().getSubscribers(newsletter);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#saveMail(org.openuss.groups.GroupInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSaveMail(GroupInfo group, MailDetail mail) throws java.lang.Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		getNewsletterService().saveMail(newsletter, mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#deleteMail(org.openuss.groups.GroupInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleDeleteMail(GroupInfo group,MailDetail mail) throws java.lang.Exception {
		NewsletterInfo newsletter = getNewsletter(group);
		getNewsletterService().deleteMail(newsletter, mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#addNewsletter(org.openuss.groups.GroupInfo)
	 */
	protected void handleAddNewsletter(GroupInfo group)	throws java.lang.Exception {
		getNewsletterService().addNewsletter(group, group.getName());
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#getNewsletter(org.openuss.groups.GroupInfo)
	 */
	protected NewsletterInfo handleGetNewsletter(GroupInfo group) throws java.lang.Exception {
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
	protected void handleUpdateNewsletter(NewsletterInfo newsletter) throws java.lang.Exception {
		getNewsletterService().updateNewsletter(newsletter);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#updateMail(org.openuss.groups.GroupInfo,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleUpdateMail(GroupInfo group, MailDetail mail) throws java.lang.Exception {
		getNewsletterService().updateMail(group, mail);
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#exportSubscribers(org.openuss.groups.GroupInfo)
	 */
	protected String handleExportSubscribers(GroupInfo group) throws java.lang.Exception {
		return getNewsletterService().exportSubscribers(getNewsletter(group));
	}

	/**
	 * @see org.openuss.groups.GroupNewsletterService#cancelSending(org.openuss.newsletter.MailInfo)
	 */
	protected void handleCancelSending(MailInfo mail)throws java.lang.Exception {
		getNewsletterService().cancelSending(mail);
	}

}