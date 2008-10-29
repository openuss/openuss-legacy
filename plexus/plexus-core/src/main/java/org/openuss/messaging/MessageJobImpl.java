// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.Validate;
import org.openuss.security.User;

/**
 * @author Ingo Dueppe
 * @see org.openuss.messaging.MessageJob
 */
public class MessageJobImpl extends MessageJobBase implements MessageJob {

	private static final long serialVersionUID = 7356518302537926968L;

	public MessageJobImpl() {
		setCreated(new Date());
	}

	public void addRecipient(User user) {
		Validate.notNull(user, "Parameter recipient must not be null.");
		Recipient recipient = new RecipientImpl();
		recipient.setEmail(user.getEmail());
		recipient.setSms(user.getSmsEmail());
		recipient.setLocale(user.getLocale());
		recipient.setJob(this);
		getRecipients().add(recipient);

	}

	@SuppressWarnings("unchecked")
	public void addRecipients(Collection users) {
		Validate.allElementsOfType(users, User.class, "Parameter users must contain user objects");
		for (User user : (Collection<User>) users) {
			addRecipient(user);
		}
	}

	/**
	 * @see org.openuss.messaging.MessageJob#getToSend()
	 */
	public int getToSend() {
		return CollectionUtils.countMatches(getRecipients(), new ToSendPredicate());
	}

	
	/**
	 * {@inheritDoc}
	 */
	public int getError() {
		return CollectionUtils.countMatches(getRecipients(), new ErrorPredicate());
	}

	/**
	 * {@inheritDoc}
	 */
	public int getSend() {
		return CollectionUtils.countMatches(getRecipients(), new SendPredicate());
	}


	@Override
	public void addRecipient(String email, String locale) {
		Validate.notNull(email, "Parameter email must not be null.");
		Recipient recipient = new RecipientImpl();
		recipient.setEmail(email);
		recipient.setLocale(locale);
		recipient.setJob(this);
		getRecipients().add(recipient);
	}


	private static final class ToSendPredicate implements Predicate {
		public boolean evaluate(Object object) {
			return SendState.TOSEND.equals(((Recipient) object).getState()); 
		}
	}


	private static final class ErrorPredicate implements Predicate {
		public boolean evaluate(Object object) {
			return SendState.ERROR.equals(((Recipient) object).getState()); 
		}
	}


	private static final class SendPredicate implements Predicate {
		public boolean evaluate(Object object) {
			return SendState.SEND.equals(((Recipient) object).getState()); 
		}
	}

}