// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.openuss.foundation.DomainObject;
import org.openuss.messaging.JobInfo;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.mailinglist.MailingListService
 */
public class MailingListServiceImpl extends org.openuss.mailinglist.MailingListServiceBase {

	private static final String MAIL_SENDING_COMMAND = "mailSendingCommand";

	private MailingList loadMailingList(MailingListInfo mailingList) {
		return getMailingListDao().load(mailingList.getId());
	}

	/**
	 * @see org.openuss.mailinglist.MailingListService#subscribe(org.openuss.foundation.DomainObject,
	 *      org.openuss.security.User)
	 */
	protected void handleSubscribe(MailingListInfo mailingList, User user) throws java.lang.Exception {
		MailingList ml = loadMailingList(mailingList);
		Subscriber subscriber = getSubscriberDao().findByUserAndMailingList(user, ml);
		if (subscriber == null) {
			subscriber = Subscriber.Factory.newInstance();
			subscriber.setMailingList(ml);
			subscriber.setUser(user);
		}
		getSubscriberDao().create(subscriber);
	}

	/**
	 * @see org.openuss.mailinglist.MailingListService#unsubscribe(org.openuss.foundation.DomainObject,
	 *      org.openuss.security.User)
	 */
	protected void handleUnsubscribe(MailingListInfo mailingList, User user) throws java.lang.Exception {
		MailingList ml = loadMailingList(mailingList);
		Subscriber subscriber = getSubscriberDao().findByUserAndMailingList(user, ml);
		if (subscriber != null) {
			getSubscriberDao().remove(subscriber);
		}
	}

	/**
	 * @see org.openuss.mailinglist.MailingListService#unsubscribe(org.openuss.mailinglist.SubscriberInfo)
	 */
	protected void handleUnsubscribe(SubscriberInfo subscriber) throws java.lang.Exception {
		getSubscriberDao().remove(getSubscriberDao().subscriberInfoToEntity(subscriber));
	}

	/**
	 * @see org.openuss.mailinglist.MailingListService#setBlockingState(org.openuss.mailinglist.SubscriberInfo)
	 */
	protected void handleSetBlockingState(SubscriberInfo subscriber) throws java.lang.Exception {
		Subscriber s = getSubscriberDao().subscriberInfoToEntity(subscriber);
		getSubscriberDao().update(s);
	}

	/**
	 * @see org.openuss.mailinglist.MailingListService#getSubscribers(org.openuss.foundation.DomainObject)
	 */
	@SuppressWarnings("unchecked")
	protected List handleGetSubscribers(MailingListInfo mailingList) throws java.lang.Exception {
		MailingList ml = loadMailingList(mailingList);
		List<SubscriberInfo> subscribers = getSubscriberDao().findByMailingList(SubscriberDao.TRANSFORM_SUBSCRIBERINFO,
				ml);
		return subscribers;
	}

	/**
	 * @see org.openuss.mailinglist.MailingListService#saveMail(org.openuss.foundation.DomainObject,
	 *      org.openuss.mailinglist.MailDetail)
	 */
	protected void handleSaveMail(MailingListInfo mailingList, MailDetail mail) throws java.lang.Exception {
		MailingList ml = loadMailingList(mailingList);
		Mail m = getMailDao().mailDetailToEntity(mail);
		m.setMailingList(ml);
		m.setStatus(MailingStatus.DRAFT);
		getMailDao().create(m);
		mail.setId(m.getId());
		getSecurityService().createObjectIdentity(m, mailingList.getDomainIdentifier());
	}

	/**
	 * @see org.openuss.mailinglist.MailingListService#deleteMail(org.openuss.foundation.DomainObject,
	 *      org.openuss.mailinglist.MailDetail)
	 */
	protected void handleDeleteMail(MailingListInfo mailingList, MailDetail mail) throws java.lang.Exception {
		Mail m = getMailDao().mailDetailToEntity(mail);
		if (getMessageService().getJobState(m.getId()) != null)
			throw new MailingListApplicationException("Mail already send!");
		if (m.getStatus() == MailingStatus.DRAFT)
			getMailDao().remove(m);
		else if (m.getStatus() != MailingStatus.DRAFT) {
			m.setStatus(MailingStatus.DELETED);
			getMailDao().update(m);
		}
	}

	/**
	 * @see org.openuss.mailinglist.MailingListService#sendPreview(org.openuss.mailinglist.MailDetail)
	 */
	protected void handleSendPreview(MailingListInfo mailingList, MailDetail mail) throws java.lang.Exception {
		List<User> recipients = new ArrayList<User>();
		recipients.add(getSecurityService().getCurrentUser());
		getMessageService().sendMessage(getMailingList(mailingList).getName(),
				"[" + getMailingList(mailingList).getName() + "]" + mail.getSubject(), mail.getText(), mail.isSms(),
				recipients);
	}

	/**
	 * @see org.openuss.mailinglist.MailingListService#getMails(org.openuss.foundation.DomainObject)
	 */
	protected List handleGetMails(MailingListInfo mailingList, boolean withDeleted) throws Exception {
		MailingList ml = loadMailingList(mailingList);
		if (withDeleted) {
			if (getSecurityService().hasPermission(mailingList.getDomainIdentifier(), new Integer[] { LectureAclEntry.ASSIST })) {
				return getMailDao().findMailByMailingListAndStatus(MailDao.TRANSFORM_MAILINFO, ml);
			}
			return getMailDao().findMailByMailingList(MailDao.TRANSFORM_MAILINFO, ml);
		} else if (!withDeleted) {
			if (getSecurityService().hasPermission(mailingList.getDomainIdentifier(), new Integer[] { LectureAclEntry.ASSIST })) {
				return getMailDao().findNotDeletedByStatus(MailDao.TRANSFORM_MAILINFO, ml);
			}
		}
		return getMailDao().findByMailingListWithoutDeleted(MailDao.TRANSFORM_MAILINFO, ml);
	}

	/**
	 * @see org.openuss.mailinglist.MailingListService#getMail(org.openuss.mailinglist.MailInfo)
	 */
	protected MailDetail handleGetMail(MailInfo mailInfo) throws Exception {
		Validate.notNull(mailInfo, "Parameter mailInfo must not be null!");
		
		MailDetail mailDetails = (MailDetail) getMailDao().load(MailDao.TRANSFORM_MAILDETAIL, mailInfo.getId());
		
		if (mailDetails.getMessageId() != null) {
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
	 * @see org.openuss.mailinglist.MailingListService#sendMail(org.openuss.mailinglist.MailInfo)
	 */
	protected void handleSendMail(MailingListInfo mailingList, MailDetail mailDetail) throws Exception {
		Mail mail = getMailDao().mailDetailToEntity(mailDetail);
		mail.setStatus(MailingStatus.PLANNED);
		if (mailDetail.getId() != null) {
			getMailDao().update(mail);
		} else if (mailDetail.getId() == null) {
			MailingList ml = loadMailingList(mailingList);
			mail.setMailingList(ml);
			getMailDao().create(mail);
			getSecurityService().createObjectIdentity(mail, mailingList.getDomainIdentifier());

		}
		mail.setCommandId(getCommandService().createOnceCommand(mail, MAIL_SENDING_COMMAND, mail.getSendDate(), null));
		mailDetail.setId(mail.getId());
	}

	@Override
	protected void handleAddMailingList(DomainObject domainObject, String name) throws Exception {
		MailingList mailingList = MailingList.Factory.newInstance();
		mailingList.setDomainIdentifier(domainObject.getId());
		mailingList.setName(name);
		getMailingListDao().create(mailingList);
	}

	@Override
	protected MailingListInfo handleGetMailingList(DomainObject domainObject) throws Exception {
		List mailingLists = getMailingListDao().findByDomainIdentifier(domainObject.getId());
		if (mailingLists == null||mailingLists.size()==0){
			return null;
		}
		//return first mailinglist of all, at the moment it is 
		//supposed that there is just 1 mailinglist per domain object 
		MailingList mailingList = (MailingList)mailingLists.get(0);
		MailingListInfo ml = getMailingListDao().toMailingListInfo(mailingList);
		ml.setSubscribed(getSubscriberDao().findByUserAndMailingList(getSecurityService().getCurrentUser(), mailingList)!=null);
		return ml;
	}

	@Override
	protected void handleUpdateMailingList(MailingListInfo mailingList) throws Exception {
		MailingList ml = getMailingListDao().mailingListInfoToEntity(mailingList);
		getMailingListDao().update(ml);
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
		mail.setMailingList(loadMailingList(getMailingList(domainObject)));
		if (mailDetail.getId() == null) {
			handleSaveMail(getMailingListDao().toMailingListInfo(mail.getMailingList()), getMailDao().toMailDetail(mail));
		} else if (mailDetail.getId() != null) {
			getMailDao().update(mail);
		}
		getMailDao().toMailDetail(mail, mailDetail);
	}

	@Override
	protected MailingListInfo handleGetMailingList(MailDetail mail) throws Exception {
		Mail m = getMailDao().mailDetailToEntity(mail);
		return getMailingListDao().toMailingListInfo(m.getMailingList());
	}

	@Override
	protected String handleExportSubscribers(MailingListInfo mailingListInfo) throws Exception {
		MailingList mailingList = getMailingListDao().load(mailingListInfo.getId());
		Set<Subscriber> subscribers = mailingList.getSubscribers();
		Iterator i = subscribers.iterator();
		Subscriber s;
		String subscriberList = "";
		while (i.hasNext()) {
			s = (Subscriber) i.next();
			subscriberList = subscriberList + s.getUser().getEmail();
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
			throw new MailingListApplicationException("mailing_cannot_be_canceled");
		}
	}

	@Override
	protected void handleMarkAsSend(MailInfo mail) throws Exception {
		Validate.notNull(mail);
		Validate.notNull(mail.getId());
		MailDetail mailDetail =	(MailDetail) getMailDao().load(getMailDao().TRANSFORM_MAILDETAIL, mail.getId());
		mailDetail.setStatus(MailingStatus.SEND);
		getMailDao().update(getMailDao().mailDetailToEntity(mailDetail));
	}

}