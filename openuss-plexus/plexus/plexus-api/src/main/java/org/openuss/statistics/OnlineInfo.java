package org.openuss.statistics;

/**
 * 
 */
public class OnlineInfo implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -7028052568939905139L;

	public OnlineInfo() {
		this.total = null;
		this.users = null;
	}

	public OnlineInfo(Long total, Long users) {
		this.total = total;
		this.users = users;
	}

	/**
	 * Copies constructor from other OnlineInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public OnlineInfo(OnlineInfo otherBean) {
		this(otherBean.getTotal(), otherBean.getUsers());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(OnlineInfo otherBean) {
		this.setTotal(otherBean.getTotal());
		this.setUsers(otherBean.getUsers());
	}

	private Long total;

	/**
	 * <p>
	 * Total number of online session.
	 * </p>
	 */
	public Long getTotal() {
		return this.total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	private Long users;

	/**
	 * <p>
	 * Number of logined online users.
	 * </p>
	 */
	public Long getUsers() {
		return this.users;
	}

	public void setUsers(Long users) {
		this.users = users;
	}

}