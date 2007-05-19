package org.openuss.messaging;

import java.util.Date;

import org.openuss.security.User;

/**
 * MockUp of Recipient
 *
 * @author Ingo Dueppe
 */
public class RecipientMock implements Recipient {

	private Long id;
	private MessageJob job;
	private String email;
	private String smsEmail;
	private String locale;
	private SendState state;
	private Date send;

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

	public String getSmsEmail() {
		return smsEmail;
	}

	public void setSmsEmail(String smsEmail) {
		this.smsEmail = smsEmail;
	}

	public MessageJob getJob() {
		return job;
	}

	public void setJob(MessageJob job) {
		this.job = job;
	}

	public Date getSend() {
		return send;
	}

	public void setSend(Date send) {
		this.send = send;
	}

	public SendState getState() {
		return state;
	}

	public void setState(SendState state) {
		this.state = state;
	}

	public User getUser() {
		return null;
	}

	public void setUser(User user) {
	}

}
