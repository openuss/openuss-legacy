package org.openuss.messaging;

import java.util.Date;

/**
 * @author Ingo Dueppe 
 */
public class JobInfo implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 8845369155460084494L;

	public JobInfo() {
		this.send = 0;
		this.tosend = 0;
		this.error = 0;
		this.total = 0;
		this.state = null;
		this.created = null;
	}

	public JobInfo(int send, int tosend, int error, int total, org.openuss.messaging.JobState state, Date created) {
		this.send = send;
		this.tosend = tosend;
		this.error = error;
		this.total = total;
		this.state = state;
		this.created = created;
	}

	/**
	 * Copies constructor from other JobInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public JobInfo(JobInfo otherBean) {
		this(otherBean.getSend(), otherBean.getTosend(), otherBean.getError(), otherBean.getTotal(), otherBean
				.getState(), otherBean.getCreated());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(JobInfo otherBean) {
		this.setSend(otherBean.getSend());
		this.setTosend(otherBean.getTosend());
		this.setError(otherBean.getError());
		this.setTotal(otherBean.getTotal());
		this.setState(otherBean.getState());
		this.setCreated(otherBean.getCreated());
	}

	private int send;

	/**
	 * <p>
	 * Number of mails that are already send
	 * </p>
	 */
	public int getSend() {
		return this.send;
	}

	public void setSend(int send) {
		this.send = send;
	}

	private int tosend;

	/**
	 * <p>
	 * Number of mails that stil need to be send
	 * </p>
	 */
	public int getTosend() {
		return this.tosend;
	}

	public void setTosend(int tosend) {
		this.tosend = tosend;
	}

	private int error;

	/**
	 * <p>
	 * Number of mails that produces an error during sending
	 * </p>
	 */
	public int getError() {
		return this.error;
	}

	public void setError(int error) {
		this.error = error;
	}

	private int total;

	/**
	 * <p>
	 * Number of the recipients of this message job
	 * </p>
	 */
	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private org.openuss.messaging.JobState state;

	/**
     * 
     */
	public org.openuss.messaging.JobState getState() {
		return this.state;
	}

	public void setState(org.openuss.messaging.JobState state) {
		this.state = state;
	}

	private Date created;

	/**
     * 
     */
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}