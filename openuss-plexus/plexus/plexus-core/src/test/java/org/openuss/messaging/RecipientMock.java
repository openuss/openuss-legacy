package org.openuss.messaging;

import java.util.Date;

/**
 * MockUp of Recipient
 *
 * @author Ingo Dueppe
 */
public class RecipientMock implements Recipient {

	private static final long serialVersionUID = 1626941589497304940L;
	
	private Long id;
	private MessageJob job;
	private String email;
	private String sms;
	private String locale;
	private SendState state;
	private Date send;
	private boolean smsNotification;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public MessageJob getJob() {
		return job;
	}

	public void setJob(MessageJob job) {
		this.job = job;
	}

	public Date getSend() {
		return send == null?null: new Date(send.getTime());
	}

	public void setSend(Date send) {
		this.send = send == null ? null : new Date(send.getTime());
	}

	public SendState getState() {
		return state;
	}

	public void setState(SendState state) {
		this.state = state;
	}


	public boolean hasSmsNotification() {
		return smsNotification;
	}

	public void setSmsNotification(boolean smsNotification) {
		this.smsNotification = smsNotification;
	}


}
