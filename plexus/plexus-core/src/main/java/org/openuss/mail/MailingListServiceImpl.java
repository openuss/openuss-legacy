// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mail;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.mail.MailingListService
 */
public class MailingListServiceImpl
    extends org.openuss.mail.MailingListServiceBase
{

    /**
     * @see org.openuss.mail.MailingListService#getMailingList(java.lang.Long)
     */
    protected org.openuss.mail.MailingListInfo handleGetMailingList(java.lang.Long domainIdentitfier)
        throws java.lang.Exception
    {
        Validate.notNull(domainIdentitfier);
    	MailingListInfo ml = getMailingListDao().toMailingListInfo(getMailingListDao().load(domainIdentitfier));
    	return ml;
    	
    }

    /**
     * @see org.openuss.mail.MailingListService#addUserToMailingList(org.openuss.security.UserInfo, org.openuss.mail.MailingListInfo)
     */
    protected void handleAddUserToMailingList(org.openuss.security.User user, org.openuss.mail.MailingListInfo mailingList)
        throws java.lang.Exception
    {
        Validate.notNull(user);
        Validate.notNull(mailingList);        
    	RecipientInfo ri = new RecipientInfo();
        ri.setName(user.getUsername());
        ri.setEmail(user.getEmail());
        Collection<RecipientInfo> recipients = mailingList.getRecipients();
        recipients.add(ri); 
        mailingList.setRecipients(recipients);
        getMailingListDao().update(getMailingListDao().mailingListInfoToEntity(mailingList));        
    }

    /**
     * @see org.openuss.mail.MailingListService#removeUserFromMailingList(org.openuss.security.UserInfo, org.openuss.mail.MailingListInfo)
     */
    protected void handleRemoveUserFromMailingList(org.openuss.security.User user, org.openuss.mail.MailingListInfo mailingList)
        throws java.lang.Exception
    {
        Validate.notNull(user);
        Validate.notNull(mailingList);        
        Collection<RecipientInfo> recipients = mailingList.getRecipients();
        Iterator i = recipients.iterator();
        while (i.hasNext()){
        	RecipientInfo ri = (RecipientInfo)i.next();
        	if (ri.getName().equals(user.getUsername())&&ri.getEmail().equals(user.getEmail()))recipients.remove(ri);
        }
    }

    /**
     * @see org.openuss.mail.MailingListService#sendMail(org.openuss.mail.MailInfo, org.openuss.mail.MailingListInfo)
     */
    protected void handleSendMail(org.openuss.mail.MailInfo mail, org.openuss.mail.MailingListInfo mailingList)
        throws java.lang.Exception
    {
        // @todo implement protected void handleSendMail(org.openuss.mail.MailInfo mail, org.openuss.mail.MailingListInfo mailingList)
        throw new java.lang.UnsupportedOperationException("org.openuss.mail.MailingListService.handleSendMail(org.openuss.mail.MailInfo mail, org.openuss.mail.MailingListInfo mailingList) Not implemented!");
    }

}