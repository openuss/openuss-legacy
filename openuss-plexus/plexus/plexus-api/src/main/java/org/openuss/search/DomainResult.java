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
	 * Name of the result, for instance the title of the faculty.
	 * @return string 
	 */
	public String getName();

	/**
	 * the date when the index entry was modified at last.
	 * @return date
	 */
	public Date getModified();
	
	/**
	 * Details of the result like the description of a faculty or course.
	 * @return string
	 */
	public String getDetails();

	/**
	 * Type of the domain object like faculty or course.
	 * @return string
	 */
	public String getDomainType();

	/**
	 * the search score of the result object 
	 * @return
	 */
	public float getScore();
	
}
