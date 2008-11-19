package org.openuss.messaging;

import java.util.List;
import java.util.Map;

import org.openuss.security.UserInfo;

/**
 * @author Ingo Dueppe 
 */
public interface MessageService {

	/**
	 * Sends a message with specified text and subject to the given recipients.
	 * The subject of the email will have the format <code>
     * [$sender] $subject</code>.
	 * 
	 * @param sender
	 *            - name of the sender
	 * @param sms
	 *            - send the subject also as sms if possible
	 * @param recipients
	 *            - a list of User objects
	 * @return Long - unique message id
	 */
	public Long sendMessage(String sender, String subject, String text, boolean sms, List recipients);

	/**
	 * Sends a message with specified text and subject to the given recipients.
	 * The subject of the email will have the format <code>
     * [$sender] $subject</code>.
	 * 
	 * @param sender
	 *            - name of the sender
	 * @param sms
	 *            - send the subject also as sms if possible
	 * @param recipients
	 *            - a list of User objects
	 * @return Long - unique message id
	 */
	public Long sendMessage(String sender, String subject, String text, boolean sms,
			UserInfo recipient);

	/**
	 * <p>
	 * Sends a message specified by given template to recipients
	 * </p>
	 * <p>
	 * The subject of the email will have the format <code> [$sender]
	 * $subject</code>.
	 * </p>
	 * <p>
	 * 
	 * @param sender
	 *            - name of the sender
	 *            </p>
	 *            <p>
	 * @param recipients
	 *            - a list of User objects
	 *            </p>
	 *            <p>
	 * @return Long - unique message id
	 *         </p>
	 */
	public Long sendMessage(String sender, String subject, String templateName, Map parameters, List recipients);

	/**
     * 
     */
	public Long sendMessage(String sender, String subject, String templateName, Map paramters,
			UserInfo recipient);

	/**
	 * <p>
	 * Sends a message specified by given template to recipients
	 * </p>
	 * <p>
	 * The subject of the email will have the format <code> [$sender]
	 * $subject</code>.
	 * </p>
	 * <p>
	 * 
	 * @param sender
	 *            - name of the sender
	 *            </p>
	 *            <p>
	 * @param email
	 *            - email of the recipient
	 *            </p>
	 *            <p>
	 * @return Long - unique message id
	 *         </p>
	 */
	public Long sendMessage(String sender, String subject, String templateName, Map parameters, String email,
			String locale);

	/**
	 * <p>
	 * Retrieve the job state to a given message id
	 * </p>
	 * <p>
	 * 
	 * @Param messageId - unique id of an existing message
	 *        </p>
	 *        <p>
	 * @Return JobState - instance of JobState
	 *         </p>
	 */
	public org.openuss.messaging.JobInfo getJobState(Long messageId);

}
