// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.Validate;
import org.openuss.security.User;

/**
 * @author Ingo Dueppe
 * @see org.openuss.messaging.MessageJob
 */
public class MessageJobImpl extends MessageJobBase implements MessageJob {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 7356518302537926968L;
	
	
	public MessageJobImpl() {
		setCreated(new Date());
	}

	/**
	 * @see org.openuss.messaging.MessageJob#getToSend()
	 */
	public int getToSend() {
		int send = 0;
		// TODO everything
		return send;
	}

	public void addRecipient(User user) {
		Validate.notNull(user, "Parameter recipient must not be null.");
		Recipient recipient = Recipient.Factory.newInstance();
		recipient.setUser(user);
		getRecipients().add(recipient);
		
	}

	public void addRecipients(Collection users) {
		// TODO Auto-generated method stub
		
	}
	
	

}