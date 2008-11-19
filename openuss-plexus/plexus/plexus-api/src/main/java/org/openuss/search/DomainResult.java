package org.openuss.search;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * Domain result object
 * 
 * @author Ingo Dueppe
 */
public interface DomainResult extends DomainObject {

	/**
	 * Name of the result, for instance the title of the institute.
	 * 
	 * @return string
	 */
	public String getName();

	/**
	 * the date when the index entry was modified at last.
	 * 
	 * @return date
	 */
	public Date getModified();

	/**
	 * Details of the result like the description of a institute or course.
	 * 
	 * @return string
	 */
	public String getDetails();

	/**
	 * Type of the domain object like institute or course.
	 * 
	 * @return string
	 */
	public String getDomainType();

	/**
	 * the search score of the result object
	 * 
	 * @return
	 */
	public float getScore();

	public String getUniversityId();

	public String getDepartmentId();

	public String getInstituteId();

	public String getCourseTypeId();

	public String getCourseId();

	public String getPeriodId();

	public String getDebugInfo();

}
