// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.newsletter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.messaging.JobInfo;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.newsletter.NewsletterService
 */
public class NewsletterServiceImpl extends org.openuss.newsletter.NewsletterServiceBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NewsletterServiceImpl.class);

	private static final String MAIL_SENDING_COMMAND = "mailSendingCommand";

	private Newsletter loadNewsletter(NewsletterInfo newsletter) {
		return getNewsletterDao().load(newsletter.getId());
	}

	/**
	 * @see org.openuss.newsletter.NewsletterService#subscribe(org.openuss.foundation.DomainObject,
	 *      org.openuss.security.User)
	 */
	protected void handleSubscribe(NewsletterInfo newsletter, UserInfo user) throws java.lang.Exception {
		Validate.notNull(user.getId(), "UserInfo id must not be empty!");
		Newsletter ml = loadNewsletter(newsletter);
		User userObject = getSecurityService().getUserObject(user);
		Subscriber subscriber = getSubscriberDao().findByUserAndNewsletter(userObject, ml);
		if (subscriber == null) {
			subscriber = Subscriber.Factory.newInstance();
			subscriber.setSubscriberPk(new SubscriberPK());
			subscriber.getSubscriberPk().setNewsletter(ml);
			subscriber.getSubscriberPk().setUser(userObject);
		}
		getSubscriberDao().create(subscriber);
	}

	/**
	 * @see org.openuss.newsletter.NewsletterService#unsubscribe(org.openuss.foundation.DomainObject,
	 *      org.openuss.security.User)
	 */
	protected void handleUnsubscribe(NewsletterInfo newsletter, UserInfo user) throws java.lang.Exception {
		Newsletter ml = loadNewsletter(newsletter);
		Subscriber subscriber = getSubscriberDao().findByUserAndNewsletter(getSecurityService().getUserObject(user), ml);
		if (subscriber != null) {
			getSubscriberDao().remove(subscriber);
		}
	}

	/**
	 * @see org.openuss.newsletter.NewsletterService#unsubscribe(org.openuss.newsletter.SubscriberInfo)
	 */
	protected void handleUnsubscribe(SubscriberInfo subscriber) throws java.lang.Exception {
		getSubscriberDao().remove(getSubscriberDao().subscriberInfoToEntity(subscriber));
	}

	/**
	 * @see org.openuss.newsletter.NewsletterService#setBlockingState(org.openuss.newsletter.SubscriberInfo)
	 */
	protected void handleSetBlockingState(SubscriberInfo subscriber) throws java.lang.Exception {
		Subscriber s = getSubscriberDao().subscriberInfoToEntity(subscriber);
		getSubscriberDao().update(s);
	}

	/**
	 * @see org.openuss.newsletter.NewsletterService#getSubscribers(org.openuss.foundation.DomainObject)
	 */
	@SuppressWarnings("unchecked")
	protected List handleGetSubscribers(NewsletterInfo newsletter) throws java.lang.Exception {
		Newsletter ml = loadNewsletter(newsletter);
		List<SubscriberInfo> subscribers = getSubscriberDao().findByNewsletter(SubscriberDao.TRANSFORM_SUBSCRIBERINFO,
				ml);
		return subscribers;
	}

	/**
	 * @see org.openuss.newsletter.NewsletterService#saveMail(org.openuss.foundation.DomainObject,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleSaveMail(NewsletterInfo newsletter, MailDetail mail) throws java.lang.Exception {
		Newsletter ml = loadNewsletter(newsletter);
		Mail m = getMailDao().mailDetailToEntity(mail);
		m.setNewsletter(ml);
		m.setStatus(MailingStatus.DRAFT);
		getMailDao().create(m);
		mail.setId(m.getId());
		getSecurityService().createObjectIdentity(m, newsletter.getDomainIdentifier());
	}

	/**
	 * @see org.openuss.newsletter.NewsletterService#deleteMail(org.openuss.foundation.DomainObject,
	 *      org.openuss.newsletter.MailDetail)
	 */
	protected void handleDeleteMail(NewsletterInfo newsletter, MailDetail mail) throws java.lang.Exception {
		Mail m = getMailDao().mailDetailToEntity(mail);
		if (getMessageService().getJobState(m.getId()) != null)
			throw new NewsletterApplicationException("Mail already send!");
		if (m.getStatus() == MailingStatus.DRAFT)
			getMailDao().remove(m);
		else if (m.getStatus() != MailingStatus.DRAFT) {
			m.setStatus(MailingStatus.DELETED);
			getMailDao().update(m);
		}
	}

	/**
	 * @see org.openuss.newsletter.NewsletterService#sendPreview(org.openuss.newsletter.MailDetail)
	 */
	protected void handleSendPreview(NewsletterInfo newsLetterInfo, MailDetail mail) throws java.lang.Exception {
		Validate.notNull(newsLetterInfo,"Parameter NewsLetterInfo must not be null.");
		Validate.notNull(newsLetterInfo.getId(), "Parameter NewsLetterInfo must contain a valid id.");
		List<User> recipients = new ArrayList<User>();
		recipients.add(getSecurityService().getUserObject(getSecurityService().getCurrentUser()));
		newsLetterInfo = getNewsletter(new DefaultDomainObject(newsLetterInfo.getDomainIdentifier()));
		if (newsLetterInfo != null) {
			getMessageService().sendMessage(newsLetterInfo.getName(),				
					"[" + newsLetterInfo.getName() + "]" + mail.getSubject(), mail.getText(), mail.isSms(),	recipients);
		} else {
			logger.error("Newsletter not found !");
		}
	}

		
	/**
	 * @see org.openuss.newsletter.NewsletterService#getMails(org.openuss.foundation.DomainObject)
	 */
	protected List<MailInfo> handleGetMails(NewsletterInfo newsletter, boolean withDeleted) throws Exception {
		Newsletter ml = loadNewsletter(newsletter);
		if (withDeleted) {
			if (getSecurityService().hasPermission(newsletter.getDomainIdentifier(), new Integer[] { LectureAclEntry.ASSIST })) {
				return getMailDao().findMailByNewsletterAndStatus(MailDao.TRANSFORM_MAILINFO, ml);
			}
			return getMailDao().findMailByNewsletter(MailDao.TRANSFORM_MAILINFO, ml);
		} else if (!withDeleted) {
			if (getSecurityService().hasPermission(newsletter.getDomainIdentifier(), new Integer[] { LectureAclEntry.ASSIST })) {
				return getMailDao().findNotDeletedByStatus(MailDao.TRANSFORM_MAILINFO, ml);
			}
		}
		return getMailDao().findByNewsletterWithoutDeleted(MailDao.TRANSFORM_MAILINFO, ml);
	}

	/**
	 * @see org.openuss.newsletter.NewsletterService#getMail(org.openuss.newsletter.MailInfo)
	 */
	protected MailDetail handleGetMail(MailInfo mailInfo) throws Exception {
		Validate.notNull(mailInfo, "Parameter mailInfo must not be null!");
		
		MailDetail mailDetails = (MailDetail) getMailDao().load(MailDao.TRANSFORM_MAILDETAIL, mailInfo.getId());
		
		if (mailDetails != null && mailDetails.getMessageId() != null) {
			JobInfo jobInfo = getMessageService().getJobState(mailDetails.getMessageId());
			if (jobInfo != null) {
				mailDetails.setErrorCount(jobInfo.getError());
				mailDetails.setSendCount(jobInfo.getSend());
				mailDetails.setToSendCount(jobInfo.getTosend());
				mailDetails.setMailCount(jobInfo.getTotal());
			}
		}
		
		return mailDetails;
	}

	/**
	 * @see org.openuss.newsletter.NewsletterService#sendMail(org.openuss.newsletter.MailInfo)
	 */
	protected void handleSendMail(NewsletterInfo newsletter, MailDetail mailDetail) throws Exception {
		Mail mail = getMailDao().mailDetailToEntity(mailDetail);
		mail.setStatus(MailingStatus.PLANNED);
		if (mailDetail.getId() != null) {
			getMailDao().update(mail);
		} else if (mailDetail.getId() == null) {
			Newsletter ml = loadNewsletter(newsletter);
			mail.setNewsletter(ml);
			getMailDao().create(mail);
			getSecurityService().createObjectIdentity(mail, newsletter.getDomainIdentifier());

		}
		mail.setCommandId(getCommandService().createOnceCommand(mail, MAIL_SENDING_COMMAND, mail.getSendDate(), null));
		mailDetail.setId(mail.getId());
	}

	@Override
	protected void handleAddNewsletter(DomainObject domainObject, String name) throws Exception {
		Newsletter newsletter = Newsletter.Factory.newInstance();
		newsletter.setDomainIdentifier(domainObject.getId());
		newsletter.setName(name);
		getNewsletterDao().create(newsletter);
	}

	@Override
	protected NewsletterInfo handleGetNewsletter(DomainObject domainObject) throws Exception {
		List<Newsletter> newsletters = getNewsletterDao().findByDomainIdentifier(domainObject.getId());
		if (newsletters == null||newsletters.size()==0){
			return null;
		}
		//return first newsletter of all, at the moment it is 
		//supposed that there is just 1 newsletter per domain object 
		Newsletter newsletter = (Newsletter)newsletters.get(0);
		NewsletterInfo info = getNewsletterDao().toNewsletterInfo(newsletter);
		info.setSubscribed(getSubscriberDao().findByUserAndNewsletter(getSecurityService().getUserObject(getSecurityService().getCurrentUser()), newsletter)!=null);
		return info;
	}

	@Override
	protected void handleUpdateNewsletter(NewsletterInfo newsletter) throws Exception {
		Newsletter ml = getNewsletterDao().newsletterInfoToEntity(newsletter);
		getNewsletterDao().update(ml);
	}

	@Override
	protected void handleUpdateMail(DomainObject domainObject, MailDetail mailDetail) throws Exception {
		Validate.notNull(domainObject, "Parameter domainObject must not be null!");
		Validate.notNull(mailDetail, "Parameter mailInfo must not be null!");
		
		Mail mail = getMailDao().mailDetailToEntity(mailDetail);
		
		if (mail.getStatus() == MailingStatus.PLANNED) {
			mail.setCommandId(getCommandService().createOnceCommand(mail, MAIL_SENDING_COMMAND, mail.getSendDate(), null));
		}
		if (mail.getStatus() == null) {
			mail.setStatus(MailingStatus.DRAFT);
		}
		mail.setNewsletter(loadNewsletter(getNewsletter(domainObject)));
		if (mailDetail.getId() == null) {
			handleSaveMail(getNewsletterDao().toNewsletterInfo(mail.getNewsletter()), getMailDao().toMailDetail(mail));
		} else if (mailDetail.getId() != null) {
			getMailDao().update(mail);
		}
		getMailDao().toMailDetail(mail, mailDetail);
	}

	@Override
	protected NewsletterInfo handleGetNewsletter(MailDetail mail) throws Exception {
		Mail m = getMailDao().mailDetailToEntity(mail);
		return getNewsletterDao().toNewsletterInfo(m.getNewsletter());
	}

	@Override
	protected String handleExportSubscribers(NewsletterInfo newsletterInfo) throws Exception {
		Newsletter newsletter = getNewsletterDao().load(newsletterInfo.getId());
		Set<Subscriber> subscribers = newsletter.getSubscribers();
		Iterator<Subscriber> i = subscribers.iterator();
		String subscriberList = "";
		while (i.hasNext()) {
			Subscriber s = i.next();
			subscriberList = subscriberList + s.getSubscriberPk().getUser().getEmail();
			if (i.hasNext())
				subscriberList = subscriberList + "; ";
		}
		return subscriberList;
	}

	@Override
	protected void handleCancelSending(MailInfo mailInfo) throws Exception {
		Validate.notNull(mailInfo, "Parameter mailInfo must not be null!");
		Mail mail = getMailDao().load(mailInfo.getId());
		if (mail.getStatus() == MailingStatus.PLANNED) {
			getCommandService().deleteCommand(mail.getCommandId());
			mail.setCommandId(null);
			mail.setStatus(MailingStatus.DRAFT);
			getMailDao().update(mail);
		} else {
			throw new NewsletterApplicationException("mailing_cannot_be_canceled");
		}
	}

	@Override
	protected void handleMarkAsSend(MailInfo mail) throws Exception {
		Validate.notNull(mail);
		Validate.notNull(mail.getId());
		MailDetail mailDetail =	(MailDetail) getMailDao().load(MailDao.TRANSFORM_MAILDETAIL, mail.getId());
		mailDetail.setStatus(MailingStatus.SEND);
		getMailDao().update(getMailDao().mailDetailToEntity(mailDetail));
	}

	@Override
	protected void handleRemoveAllSubscriptions(User user) throws Exception {
		List<Subscriber> subscribers = getSubscriberDao().findByUser(user);
		getSubscriberDao().remove(subscribers);
	}

}