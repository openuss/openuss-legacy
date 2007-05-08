// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

import java.util.List;
import java.util.Map;

/**
 * @see org.openuss.messaging.MessageService
 */
public class MessageServiceImpl extends MessageServiceBase {

	/**
	 * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String,
	 *      java.lang.String, boolean, java.util.List)
	 */
	protected MessageJob handleSendMessage(String subject, String text, boolean sms, List recipients) throws Exception {
		// @todo implement protected org.openuss.messaging.MessageJob
		// handleSendMessage(java.lang.String subject, java.lang.String text,
		// boolean sms, java.util.List recipients)
		return null;
	}

	/**
	 * @see org.openuss.messaging.MessageService#sendMessage(java.lang.String,
	 *      java.lang.String, java.util.Map, java.util.List)
	 */
	protected MessageJob handleSendMessage(String subject, String templateName, Map parameters, List recipients)
			throws Exception {
		// @todo implement protected org.openuss.messaging.MessageJob
		// handleSendMessage(java.lang.String subject, java.lang.String
		// templateName, java.util.Map parameters, java.util.List recipients)
		return null;
	}

	@Override
	protected JobState handleGetJobState(Long mailId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}