package org.openuss.lecture;

import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.openuss.search.DomainResult;

/**
 * Value Object of DomainResult
 * 
 * @author Ingo Dueppe
 *
 */
public class DomainResultBean implements DomainResult {

	private static final long serialVersionUID = 1297801100348861030L;
	
	private Long id;
	private Date modified;
	private String name;
	private String details;
	private String domainType;
	private String universityId;
	private String departmentId;
	private String instituteId;
	private String courseTypeId;
	private String courseId;
	private String periodId;
	private String seminarpoolId;
	
	private float score;

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	public String getFormattedScore(){ 
		DecimalFormat df = new DecimalFormat("###.##"); 
		return df.format(this.score*100)+" %";
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDomainType() {
		return domainType;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	/**
	 * {@inheritDoc}
	 */
	public Long getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getModified() {
		return modified != null ? new Date(modified.getTime()) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModified(Date modified) {
		this.modified = modified != null ? new Date(modified.getTime()) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getUniversityId() {
		return universityId;
	}

	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getCourseTypeId() {
		return courseTypeId;
	}

	public void setCourseTypeId(String courseTypeId) {
		this.courseTypeId = courseTypeId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}
	
	public String getDebugInfo(){
		return "DEBUG: "
			+"DomainType: "+domainType+"; "
			+"Uni: "+universityId+"; "
			+"Dept: "+departmentId+"; "
			+"Inst: "+instituteId+"; "
			+"Seminarpool: "+seminarpoolId+"; "
			+"CourseType: "+courseTypeId;
	}

	public String getSeminarpoolId() {
	    return seminarpoolId;
	}

	public void setSeminarpoolId(String seminarpoolId) {
	    this.seminarpoolId = seminarpoolId;
	}

}
