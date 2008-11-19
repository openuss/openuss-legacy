package org.openuss.messaging;

/**
 * 
 */
public interface Message extends org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public Long getId();

	public void setId(Long id);

	/**
	 * <p>
	 * name of the sender
	 * </p>
	 */
	public String getSenderName();

	public void setSenderName(String senderName);

	/**
	 * <p>
	 * subject of the email or content of the sms
	 * </p>
	 */
	public String getSubject();

	public void setSubject(String subject);

	// Interface HibernateEntity.vsl merge-point
}