package org.openuss.lecture;

import java.io.Serializable;
import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 */
public class PeriodInfo implements Serializable, DomainObject {

	private static final long serialVersionUID = -7142889996146424248L;

	public PeriodInfo() {
		this.id = null;
		this.name = null;
		this.description = null;
		this.startdate = null;
		this.enddate = null;
		this.active = false;
		this.universityId = null;
		this.defaultPeriod = false;
	}

	public PeriodInfo(Long id, String name, String description, Date startdate, Date enddate, boolean active,
			Long universityId, boolean defaultPeriod) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.startdate = startdate;
		this.enddate = enddate;
		this.active = active;
		this.universityId = universityId;
		this.defaultPeriod = defaultPeriod;
	}

	/**
	 * Copies constructor from other PeriodInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public PeriodInfo(PeriodInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getDescription(), otherBean.getStartdate(), otherBean
				.getEnddate(), otherBean.isActive(), otherBean.getUniversityId(), otherBean.isDefaultPeriod());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(PeriodInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setDescription(otherBean.getDescription());
		this.setStartdate(otherBean.getStartdate());
		this.setEnddate(otherBean.getEnddate());
		this.setActive(otherBean.isActive());
		this.setUniversityId(otherBean.getUniversityId());
		this.setDefaultPeriod(otherBean.isDefaultPeriod());
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

	private String description;

	/**
     * 
     */
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Date startdate;

	/**
     * 
     */
	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	private Date enddate;

	/**
     * 
     */
	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	private boolean active;

	/**
     * 
     */
	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	private Long universityId;

	/**
     * 
     */
	public Long getUniversityId() {
		return this.universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	private boolean defaultPeriod;

	/**
     * 
     */
	public boolean isDefaultPeriod() {
		return this.defaultPeriod;
	}

	public void setDefaultPeriod(boolean defaultPeriod) {
		this.defaultPeriod = defaultPeriod;
	}

	/**
	 * Returns <code>true</code> if the argument is an PeriodInfo instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof PeriodInfo)) {
			return false;
		}
		final PeriodInfo that = (PeriodInfo) object;
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