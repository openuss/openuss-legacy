package org.openuss.desktop;

import java.io.Serializable;

/**
 * @author Ingo Dueppe
 */
public class MyUniCourseInfo implements Serializable {

	private static final long serialVersionUID = -1006119354109551619L;

	public MyUniCourseInfo() {
		this.id = null;
		this.name = null;
		this.period = null;
		this.periodId = null;
		this.forumSubscribed = null;
		this.newsletterSubscribed = null;
		this.pastUniInfo = null;
		this.currentUniInfo = null;
	}

	public MyUniCourseInfo(Long id, String name, String period, Long periodId, Boolean forumSubscribed,
			Boolean newsletterSubscribed, org.openuss.desktop.MyUniInfo pastUniInfo,
			org.openuss.desktop.MyUniInfo currentUniInfo) {
		this.id = id;
		this.name = name;
		this.period = period;
		this.periodId = periodId;
		this.forumSubscribed = forumSubscribed;
		this.newsletterSubscribed = newsletterSubscribed;
		this.pastUniInfo = pastUniInfo;
		this.currentUniInfo = currentUniInfo;
	}

	/**
	 * Copies constructor from other MyUniCourseInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public MyUniCourseInfo(MyUniCourseInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getPeriod(), otherBean.getPeriodId(), otherBean
				.getForumSubscribed(), otherBean.getNewsletterSubscribed(), otherBean.getPastUniInfo(), otherBean
				.getCurrentUniInfo());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(MyUniCourseInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setPeriod(otherBean.getPeriod());
		this.setPeriodId(otherBean.getPeriodId());
		this.setForumSubscribed(otherBean.getForumSubscribed());
		this.setNewsletterSubscribed(otherBean.getNewsletterSubscribed());
		this.setPastUniInfo(otherBean.getPastUniInfo());
		this.setCurrentUniInfo(otherBean.getCurrentUniInfo());
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

	private String name;

	/**
     * 
     */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String period;

	/**
     * 
     */
	public String getPeriod() {
		return this.period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	private Long periodId;

	/**
     * 
     */
	public Long getPeriodId() {
		return this.periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	private Boolean forumSubscribed;

	/**
     * 
     */
	public Boolean getForumSubscribed() {
		return this.forumSubscribed;
	}

	public void setForumSubscribed(Boolean forumSubscribed) {
		this.forumSubscribed = forumSubscribed;
	}

	private Boolean newsletterSubscribed;

	/**
     * 
     */
	public Boolean getNewsletterSubscribed() {
		return this.newsletterSubscribed;
	}

	public void setNewsletterSubscribed(Boolean newsletterSubscribed) {
		this.newsletterSubscribed = newsletterSubscribed;
	}

	private org.openuss.desktop.MyUniInfo pastUniInfo;

	/**
	 * Get the pastUniInfo
	 * 
	 */
	public org.openuss.desktop.MyUniInfo getPastUniInfo() {
		return this.pastUniInfo;
	}

	/**
	 * Sets the pastUniInfo
	 */
	public void setPastUniInfo(org.openuss.desktop.MyUniInfo pastUniInfo) {
		this.pastUniInfo = pastUniInfo;
	}

	private org.openuss.desktop.MyUniInfo currentUniInfo;

	/**
	 * Get the currentUniInfo
	 * 
	 */
	public org.openuss.desktop.MyUniInfo getCurrentUniInfo() {
		return this.currentUniInfo;
	}

	/**
	 * Sets the currentUniInfo
	 */
	public void setCurrentUniInfo(org.openuss.desktop.MyUniInfo currentUniInfo) {
		this.currentUniInfo = currentUniInfo;
	}

	/**
	 * Returns <code>true</code> if the argument is an MyUniCourseInfo instance
	 * and all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof MyUniCourseInfo)) {
			return false;
		}
		final MyUniCourseInfo that = (MyUniCourseInfo) object;
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