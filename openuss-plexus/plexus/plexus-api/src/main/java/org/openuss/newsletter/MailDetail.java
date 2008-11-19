package org.openuss.newsletter;

import java.util.Date;

/**
 * @author Ingo Dueppe 
 */
public class MailDetail implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 4205306803323887535L;

	public MailDetail() {
		this.id = null;
		this.status = null;
		this.subject = null;
		this.text = null;
		this.sms = false;
		this.toSendCount = 0;
		this.sendCount = 0;
		this.errorCount = 0;
		this.sendDate = null;
		this.mailCount = 0;
		this.commandId = null;
		this.messageId = null;
	}

	public MailDetail(Long id, org.openuss.newsletter.MailingStatus status, String subject, String text, boolean sms,
			int toSendCount, int sendCount, int errorCount, Date sendDate, int mailCount, Long commandId, Long messageId) {
		this.id = id;
		this.status = status;
		this.subject = subject;
		this.text = text;
		this.sms = sms;
		this.toSendCount = toSendCount;
		this.sendCount = sendCount;
		this.errorCount = errorCount;
		this.sendDate = sendDate;
		this.mailCount = mailCount;
		this.commandId = commandId;
		this.messageId = messageId;
	}

	/**
	 * Copies constructor from other MailDetail
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public MailDetail(MailDetail otherBean) {
		this(otherBean.getId(), otherBean.getStatus(), otherBean.getSubject(), otherBean.getText(), otherBean.isSms(),
				otherBean.getToSendCount(), otherBean.getSendCount(), otherBean.getErrorCount(), otherBean
						.getSendDate(), otherBean.getMailCount(), otherBean.getCommandId(), otherBean.getMessageId());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(MailDetail otherBean) {
		this.setId(otherBean.getId());
		this.setStatus(otherBean.getStatus());
		this.setSubject(otherBean.getSubject());
		this.setText(otherBean.getText());
		this.setSms(otherBean.isSms());
		this.setToSendCount(otherBean.getToSendCount());
		this.setSendCount(otherBean.getSendCount());
		this.setErrorCount(otherBean.getErrorCount());
		this.setSendDate(otherBean.getSendDate());
		this.setMailCount(otherBean.getMailCount());
		this.setCommandId(otherBean.getCommandId());
		this.setMessageId(otherBean.getMessageId());
	}

	private Long id;

	/**
     * 
     */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private org.openuss.newsletter.MailingStatus status;

	/**
     * 
     */
	public org.openuss.newsletter.MailingStatus getStatus() {
		return this.status;
	}

	public void setStatus(org.openuss.newsletter.MailingStatus status) {
		this.status = status;
	}

	private String subject;

	/**
     * 
     */
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	private String text;

	/**
     * 
     */
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private boolean sms = false;

	/**
     * 
     */
	public boolean isSms() {
		return this.sms;
	}

	public void setSms(boolean sms) {
		this.sms = sms;
	}

	private int toSendCount;

	/**
     * 
     */
	public int getToSendCount() {
		return this.toSendCount;
	}

	public void setToSendCount(int toSendCount) {
		this.toSendCount = toSendCount;
	}

	private int sendCount;

	/**
     * 
     */
	public int getSendCount() {
		return this.sendCount;
	}

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	private int errorCount;

	/**
     * 
     */
	public int getErrorCount() {
		return this.errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	private Date sendDate;

	/**
     * 
     */
	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	private int mailCount;

	/**
     * 
     */
	public int getMailCount() {
		return this.mailCount;
	}

	public void setMailCount(int mailCount) {
		this.mailCount = mailCount;
	}

	private Long commandId;

	/**
     * 
     */
	public Long getCommandId() {
		return this.commandId;
	}

	public void setCommandId(Long commandId) {
		this.commandId = commandId;
	}

	private Long messageId;

	/**
     * 
     */
	public Long getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 * Returns <code>true</code> if the argument is an MailDetail instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof MailDetail)) {
			return false;
		}
		final MailDetail that = (MailDetail) object;
		if (this.id == null || that.getId() == null || !this.id.equals(that.getId())) {
			return false;
		}

		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

		return hashCode;
	}

}