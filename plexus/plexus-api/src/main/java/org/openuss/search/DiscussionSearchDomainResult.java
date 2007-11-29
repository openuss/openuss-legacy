package org.openuss.search;

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
	 * @return date
	 */
	public Date getModified();
	
	/**
	 * the search score of the result object 
	 * @return
	 */
	public float getScore();
	
	public String getPostId();

	public String getCourseId();
	
	public String getSubmitter();
	
	public String getTopicId();
}
