package org.openuss.newsletter;

import java.util.Date;

import org.openuss.foundation.DomainObject;
import org.openuss.viewtracking.ViewState;

/**
 * @author Ingo Dueppe
 */
public class MailInfo implements java.io.Serializable, DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -7640616563350869785L;

	public MailInfo() {
		this.id = null;
		this.status = null;
		this.subject = null;
		this.sendDate = null;
		this.viewState = null;
	}

	public MailInfo(Long id, MailingStatus status, String subject, Date sendDate, ViewState viewState) {
		this.id = id;
		this.status = status;
		this.subject = subject;
		this.sendDate = sendDate;
		this.viewState = viewState;
	}

	/**
	 * Copies constructor from other MailInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public MailInfo(MailInfo otherBean) {
		this(otherBean.getId(), otherBean.getStatus(), otherBean.getSubject(), otherBean.getSendDate(), otherBean
				.getViewState());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(MailInfo otherBean) {
		this.setId(otherBean.getId());
		this.setStatus(otherBean.getStatus());
		this.setSubject(otherBean.getSubject());
		this.setSendDate(otherBean.getSendDate());
		this.setViewState(otherBean.getViewState());
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

	private org.openuss.viewtracking.ViewState viewState;

	/**
     * 
     */
	public org.openuss.viewtracking.ViewState getViewState() {
		return this.viewState;
	}

	public void setViewState(org.openuss.viewtracking.ViewState viewState) {
		this.viewState = viewState;
	}

	/**
	 * Returns <code>true</code> if the argument is an MailInfo instance and all
	 * identifiers for this object equal the identifiers of the argument object.
	 * Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof MailInfo)) {
			return false;
		}
		final MailInfo that = (MailInfo) object;
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