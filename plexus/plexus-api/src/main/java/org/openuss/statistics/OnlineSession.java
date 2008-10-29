package org.openuss.statistics;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 */
public interface OnlineSession extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public Date getStartTime();

	public void setStartTime(Date startTime);

	public Date getEndTime();

	public void setEndTime(Date endTime);

	public org.openuss.security.User getUser();

	public void setUser(org.openuss.security.User user);

	/**
	 * If the sessionStart is older then 120 minutes the user session is defined
	 * as expired.
	 */
	public abstract boolean isExpired();

	/**
	 * Wether or not this user session is still active, so the user is not loged
	 * out.
	 * 
	 * @return true - if session is active
	 */
	public abstract boolean isActive();

}