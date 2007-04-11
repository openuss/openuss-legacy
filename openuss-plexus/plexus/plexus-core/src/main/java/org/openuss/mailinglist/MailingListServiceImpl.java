// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.openuss.foundation.DomainObject;
import org.openuss.mail.MailInfo;
import org.openuss.mail.RecipientInfo;
import org.openuss.security.User;

/**
 * @see org.openuss.mail.MailingListService
 */
public class MailingListServiceImpl extends MailingListServiceBase {

	/**
	 * @see org.openuss.mail.MailingListService#getMailingList(java.lang.Long)
	 */
	protected MailingListInfo handleGetMailingList(DomainObject domainObject) throws Exception {
		Validate.notNull(domainObject.getId());
		MailingListInfo mli = new MailingListInfo();
		mli.setDomainIdentity(domainObject.getId());
		return getMailingListDao().getByDomainIdentifier(domainObject.getId());

	}

	/**
	 * @see org.openuss.mail.MailingListService#addUserToMailingList(org.openuss.security.UserInfo,
	 *      org.openuss.mail.MailingListInfo)
	 */
	protected void handleAddUserToMailingList(User user, MailingListInfo mailingList) throws Exception {
		Validate.notNull(user);
		Validate.notNull(mailingList);
		Validate.notNull(mailingList.getId());
		MailingList ml = getMailingListDao().load(mailingList.getId());
		RecipientInfo ri = new RecipientInfo();
		ri.setName(user.getUsername());
		ri.setId(user.getId());
		ri.setEmail(user.getEmail());
		Collection<RecipientInfo> recipients = mailingList.getRecipients();
		if (recipients==null) recipients = new ArrayList<RecipientInfo>();
		recipients.add(ri);
		mailingList.setRecipients(recipients);
		Set<User> mlRecipients = ml.getRecipients();
		mlRecipients.add(user);
		ml.setRecipients(mlRecipients);
		getMailingListDao().update(ml);
	}

	/**
	 * @see org.openuss.mail.MailingListService#removeUserFromMailingList(org.openuss.security.UserInfo,
	 *      org.openuss.mail.MailingListInfo)
	 */
	protected void handleRemoveUserFromMailingList(User user, MailingListInfo mailingList) throws Exception {
		Validate.notNull(user);
		Validate.notNull(mailingList);
		Validate.notNull(mailingList.getId());		
		MailingList ml = getMailingListDao().load(mailingList.getId());
		Set<User> mlRecipients = ml.getRecipients();
		mlRecipients.remove(user);
		getMailingListDao().update(ml);
		mailingList = getMailingListDao().toMailingListInfo(ml);
	}

	/**
	 * @see org.openuss.mail.MailingListService#sendMail(org.openuss.mail.MailInfo,
	 *      org.openuss.mail.MailingListInfo)
	 */
	protected void handleSendMail(MailInfo mail, MailingListInfo mailingList) throws Exception {
		// @todo implement protected void
		// handleSendMail(org.openuss.mail.MailInfo mail,
		// org.openuss.mail.MailingListInfo mailingList)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.mail.MailingListService.handleSendMail(org.openuss.mail.MailInfo mail, org.openuss.mail.MailingListInfo mailingList) Not implemented!");
	}

}