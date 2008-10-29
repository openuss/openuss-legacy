package org.openuss.statistics;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * @see org.openuss.statistics.OnlineSession
 * @author Ingo Dueppe
 */
public class OnlineSessionImpl extends OnlineSessionBase {
	
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 6218392658866924282L;

	@Override
	public boolean isActive() {
		return getEndTime() == null;
	}

	@Override
	public boolean isExpired() {
		return getStartTime().before(DateUtils.addHours(new Date(), -2));
	}

}