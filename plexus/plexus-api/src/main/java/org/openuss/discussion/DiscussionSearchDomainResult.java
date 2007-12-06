package org.openuss.discussion;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * Discussion Search Domain result object
 * 
 * @author Thomas Jansing
 * @author Juergen de Braaf
 * @author Peter Schuh
 * @author Tobias Brockmann
 */
public interface DiscussionSearchDomainResult extends DomainObject {

	/**
	 * 
	 * @return string 
	 */
	public String getTitle();

	/**
	 * the date when the index entry was modified at last.
	 * @return Date
	 */
	public Date getModified();
	
	/**
	 * the search score of the result object 
	 * @return String
	 */
	public float getScore();
	
	/**
	 * post id 
	 * @return String
	 */
	public String getPostId();

	/**
	 * course id 
	 * @return String
	 */
	public String getCourseId();
	
	/**
	 * submitter id 
	 * @return String
	 */
	public String getSubmitter();

	/**
	 * topic id 
	 * @return String
	 */
	public String getTopicId();
}
