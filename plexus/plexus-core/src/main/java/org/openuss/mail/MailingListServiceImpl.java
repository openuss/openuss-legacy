// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mail;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.openuss.security.User;

/**
 * @see org.openuss.mail.MailingListService
 */
public class MailingListServiceImpl extends MailingListServiceBase {

	/**
	 * @see org.openuss.mail.MailingListService#getMailingList(java.lang.Long)
	 */
	protected MailingListInfo handleGetMailingList(Long domainIdentifier) throws Exception {
		// FIXME paramter should be object not long
		Validate.notNull(domainIdentifier);
		return (MailingListInfo) getMailingListDao().load(MailingListDao.TRANSFORM_MAILINGLISTINFO, domainIdentifier);

	}

	/**
	 * @see org.openuss.mail.MailingListService#addUserToMailingList(org.openuss.security.UserInfo,
	 *      org.openuss.mail.MailingListInfo)
	 */
	protected void handleAddUserToMailingList(User user, MailingListInfo mailingList) throws Exception {
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
	 * @see org.openuss.mail.MailingListService#removeUserFromMailingList(org.openuss.security.UserInfo,
	 *      org.openuss.mail.MailingListInfo)
	 */
	protected void handleRemoveUserFromMailingList(User user, MailingListInfo mailingList) throws Exception {
		Validate.notNull(user);
		Validate.notNull(mailingList);
		Collection<RecipientInfo> recipients = mailingList.getRecipients();
		Iterator i = recipients.iterator();
		while (i.hasNext()) {
			RecipientInfo ri = (RecipientInfo) i.next();
			if (ri.getName().equals(user.getUsername()) && ri.getEmail().equals(user.getEmail()))
				recipients.remove(ri);
		}
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