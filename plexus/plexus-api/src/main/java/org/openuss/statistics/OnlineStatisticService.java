package org.openuss.statistics;

import java.util.List;

/**
 * 
 */
public interface OnlineStatisticService {

	/**
	 * <p>
	 * Log session start.
	 * </p>
	 * <p>
	 * 
	 * @param sessionId
	 *            - id of the http session
	 *            </p>
	 */
	public Long logSessionStart(Long sessionId);

	/**
	 * <p>
	 * Log session end
	 * </p>
	 * <p>
	 * 
	 * @param optional
	 *            id of the <code>HttpSession</code>
	 *            </p>
	 */
	public void logSessionEnd(Long sessionId);

	/**
	 * <p>
	 * Number of users and guests that are currently online.
	 * </p>
	 * <p>
	 * 
	 * @return OnlineInfo
	 *         </p>
	 */
	public org.openuss.statistics.OnlineInfo getOnlineInfo();

	/**
	 * <p>
	 * Retrieve a list of users that are currently online.
	 * </p>
	 * <p>
	 * 
	 * @return List<OnlineUserInfo>
	 *         </p>
	 */
	public List getActiveUsers();

	/**
     * 
     */
	public org.openuss.statistics.SystemStatisticInfo getSystemStatistics();

}
