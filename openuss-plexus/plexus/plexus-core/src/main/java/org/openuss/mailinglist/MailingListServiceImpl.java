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

import org.openuss.foundation.DomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.mailinglist.MailingListService
 */
public class MailingListServiceImpl
    extends org.openuss.mailinglist.MailingListServiceBase
{

	private MailingList getMailingList(DomainObject domainObject){
		MailingList ml = getMailingListDao().load(domainObject.getId());
		if (ml != null) return ml;
		return MailingList.Factory.newInstance();
	}
	
    /**
     * @see org.openuss.mailinglist.MailingListService#subscribe(org.openuss.foundation.DomainObject, org.openuss.security.User)
     */
    protected void handleSubscribe(DomainObject domainObject, User user)
        throws java.lang.Exception
    {
    	MailingList ml = getMailingList(domainObject);
    	Subscriber subscriber = getSubscriberDao().findByUserAndMailingList(user, ml);
    	if (subscriber==null){
    		subscriber = Subscriber.Factory.newInstance();    		
    		subscriber.setMailingList(ml);
    		subscriber.setUser(user);
    	}
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#unsubscribe(org.openuss.foundation.DomainObject, org.openuss.security.User)
     */
    protected void handleUnsubscribe(DomainObject domainObject, User user)
        throws java.lang.Exception
    {
    	MailingList ml = getMailingList(domainObject);
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
    protected List handleGetSubscribers(DomainObject domainObject)
        throws java.lang.Exception
    {
    	MailingList ml = getMailingList(domainObject);
    	List<SubscriberInfo> subscribers = getSubscriberDao().findByMailingList(SubscriberDao.TRANSFORM_SUBSCRIBERINFO, ml);
    	return subscribers;
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#saveMail(org.openuss.foundation.DomainObject, org.openuss.mailinglist.MailDetail)
     */
    protected void handleSaveMail(DomainObject domainObject, MailDetail mail)
        throws java.lang.Exception
    {
    	MailingList ml = getMailingList(domainObject);
    	Mail m = getMailDao().mailDetailToEntity(mail);
    	m.setMailingList(ml);
    	getMailDao().update(m);
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#deleteMail(org.openuss.foundation.DomainObject, org.openuss.mailinglist.MailDetail)
     */
    protected void handleDeleteMail(DomainObject domainObject, MailDetail mail)
        throws java.lang.Exception
    {
    	MailingList ml = getMailingList(domainObject);
    	Mail m = getMailDao().mailDetailToEntity(mail);
    	if (m.getJob() != null) throw new MailingListApplicationException("Mail already send!");
    	getMailDao().remove(m);    	
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#sendPreview(org.openuss.mailinglist.MailDetail)
     */
    protected void handleSendPreview(MailDetail mail)
        throws java.lang.Exception
    {
    	List<User> recipients = new ArrayList<User>();
    	recipients.add(getSecurityService().getCurrentUser());
    	getMessageService().sendMessage(mail.getSubject(), mail.getText(), mail.isSms(), recipients);
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#getMails(org.openuss.foundation.DomainObject)
     */
    protected java.util.List handleGetMails(DomainObject domainObject)
        throws java.lang.Exception
    {
    	MailingList ml = getMailingList(domainObject);
    	if (!AcegiUtils.hasPermission(domainObject, new Integer[] { LectureAclEntry.ASSIST })) {
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
    	return getMailDao().toMailDetail(getMailDao().load(mail.getId()));
    }

    /**
     * @see org.openuss.mailinglist.MailingListService#sendMail(org.openuss.mailinglist.MailInfo)
     */
    protected void handleSendMail(org.openuss.mailinglist.MailInfo mail)
        throws java.lang.Exception
    {
    	Mail m = getMailDao().load(mail.getId());
    	Set<Subscriber> subscribers = m.getMailingList().getSubscribers();
    	List<User> recipients = new ArrayList<User>(); 
    	Iterator i = subscribers.iterator();
    	while (i.hasNext()){
    		recipients.add(((Subscriber)i.next()).getUser());
    	}
    	getMessageService().sendMessage(m.getSubject(), m.getText(), m.isSms(), recipients);
    }

}