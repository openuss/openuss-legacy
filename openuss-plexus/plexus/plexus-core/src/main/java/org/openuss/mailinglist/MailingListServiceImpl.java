// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;

import java.util.ArrayList;
import java.util.List;

import org.openuss.foundation.DomainObject;
import org.openuss.messaging.JobState;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.mailinglist.MailingListService
 */
public class MailingListServiceImpl
    extends org.openuss.mailinglist.MailingListServiceBase
{

	private MailingList getMailingListDao(MailingListInfo mailingList){
		return getMailingListDao().load(mailingList.getId());
	}
	
    /**
     * @see org.openuss.mailinglist.MailingListService#subscribe(org.openuss.foundation.DomainObject, org.openuss.security.User)
     */
    protected void handleSubscribe(MailingListInfo mailingList, User user)
        throws java.lang.Exception
    {
    	MailingList ml = getMailingListDao(mailingList);
    	Subscriber subscriber = getSubscriberDao().findByUserAndMailingList(user, ml);
    	if (subscriber==null){
    		subscriber = Subscriber.Factory.newInstance();    		
    		subscriber.setMailingList(ml);
    		subscriber.setUser(user);
    	}
    	getSubscriberDao().create(subscriber);
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#unsubscribe(org.openuss.foundation.DomainObject, org.openuss.security.User)
     */
    protected void handleUnsubscribe(MailingListInfo mailingList, User user)
        throws java.lang.Exception
    {
    	MailingList ml = getMailingListDao(mailingList);
    	Subscriber subscriber = getSubscriberDao().findByUserAndMailingList(user, ml);
    	if (subscriber!=null){
    		getSubscriberDao().remove(subscriber);    		
    	}    	
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#unsubscribe(org.openuss.mailinglist.SubscriberInfo)
     */
    protected void handleUnsubscribe(SubscriberInfo subscriber)
        throws java.lang.Exception
    {
    	getSubscriberDao().remove(getSubscriberDao().subscriberInfoToEntity(subscriber));
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#setBlockingState(org.openuss.mailinglist.SubscriberInfo)
     */
    protected void handleSetBlockingState(SubscriberInfo subscriber)
        throws java.lang.Exception
    {
    	Subscriber s = getSubscriberDao().subscriberInfoToEntity(subscriber);
    	getSubscriberDao().update(s);
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#getSubscribers(org.openuss.foundation.DomainObject)
     */
    protected List handleGetSubscribers(MailingListInfo mailingList)
        throws java.lang.Exception
    {
    	MailingList ml = getMailingListDao(mailingList);
    	List<SubscriberInfo> subscribers = getSubscriberDao().findByMailingList(SubscriberDao.TRANSFORM_SUBSCRIBERINFO, ml);
    	return subscribers;
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#saveMail(org.openuss.foundation.DomainObject, org.openuss.mailinglist.MailDetail)
     */
    protected void handleSaveMail(MailingListInfo mailingList, MailDetail mail)
        throws java.lang.Exception
    {
    	MailingList ml = getMailingListDao(mailingList);
    	Mail m = getMailDao().mailDetailToEntity(mail);
    	m.setMailingList(ml);
    	m.setStatus(MailingStatus.DRAFT);
    	getMailDao().create(m);
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#deleteMail(org.openuss.foundation.DomainObject, org.openuss.mailinglist.MailDetail)
     */
    protected void handleDeleteMail(MailingListInfo mailingList, MailDetail mail)
        throws java.lang.Exception
    {
    	Mail m = getMailDao().mailDetailToEntity(mail);
    	if (getMessageService().getJobState(m.getId())!=null) throw new MailingListApplicationException("Mail already send!");
    	if (m.getStatus()==MailingStatus.DRAFT)	getMailDao().remove(m);
    	else if (m.getStatus()!=MailingStatus.DRAFT){
    		m.setStatus(MailingStatus.DELETED);
    		getMailDao().update(m);
    	}
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#sendPreview(org.openuss.mailinglist.MailDetail)
     */
    protected void handleSendPreview(MailingListInfo mailingList, MailDetail mail)
        throws java.lang.Exception
    {
    	List<User> recipients = new ArrayList<User>();
    	recipients.add(getSecurityService().getCurrentUser());    	
    	getMessageService().sendMessage(getMailingList(mailingList).getName(), mail.getSubject(), mail.getText(), mail.isSms(), recipients);
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#getMails(org.openuss.foundation.DomainObject)
     */
    protected java.util.List handleGetMails(MailingListInfo mailingList)
        throws java.lang.Exception
    {
    	MailingList ml = getMailingListDao(mailingList);
    	if (!getSecurityService().hasPermission(mailingList, new Integer[] { LectureAclEntry.ASSIST })) {
    		return getMailDao().findMailByMailingListAndStatus(MailDao.TRANSFORM_MAILINFO, ml);
    	} 
    	return getMailDao().findMailByMailingList(MailDao.TRANSFORM_MAILINFO, ml);
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#getMail(org.openuss.mailinglist.MailInfo)
     */
    protected org.openuss.mailinglist.MailDetail handleGetMail(MailInfo mail)
        throws java.lang.Exception
    {
    	MailDetail md = getMailDao().toMailDetail(getMailDao().load(mail.getId()));
    	JobState js = getMessageService().getJobState(mail.getId());
    	if (js!=null){
	    	md.setErrorCount(js.getError());
	    	md.setSendCount(js.getSend());
	    	md.setToSendCount(js.getTosend());
    	}
    	return md;
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#sendMail(org.openuss.mailinglist.MailInfo)
     */
    protected void handleSendMail(MailingListInfo mailingList, org.openuss.mailinglist.MailDetail mail)
        throws java.lang.Exception
    {
    	Mail m = getMailDao().mailDetailToEntity(mail);
    	m.setStatus(MailingStatus.PLANNED);
    	if (mail.getId()!=null){
    		getMailDao().update(m);
    	} else if (mail.getId()==null){
    		MailingList ml = getMailingListDao(mailingList);
    		m.setMailingList(ml);
    		getMailDao().create(m);  
    		
    	}
    	//TODO trigger command
    	/*
    	 * move to MailSendingCommand 
    	Set<Subscriber> subscribers = m.getMailingList().getSubscribers();
    	List<User> recipients = new ArrayList<User>(); 
    	Iterator i = subscribers.iterator();
    	while (i.hasNext()){
    		recipients.add(((Subscriber)i.next()).getUser());
    	}
    	getMessageService().sendMessage(m.getSubject(), m.getText(), m.isSms(), recipients);*/
    }

	@Override
	protected void handleAddMailingList(DomainObject domainObject, String name) throws Exception {
		MailingList ml = getMailingListDao().load(domainObject.getId());
		if (ml == null) {
			ml = MailingList.Factory.newInstance();
			ml.setId(domainObject.getId());
			ml.setName(name);
			getMailingListDao().create(ml);
		}
	}

	@Override
	protected MailingListInfo handleGetMailingList(DomainObject domainObject) throws Exception {
		return getMailingListDao().toMailingListInfo(getMailingListDao().load(domainObject.getId()));

	}

	@Override
	protected void handleUpdateMailingList(MailingListInfo mailingList) throws Exception {
		MailingList ml = getMailingListDao().mailingListInfoToEntity(mailingList);
		getMailingListDao().update(ml);
	}

}