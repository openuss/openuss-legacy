package org.openuss.discussion;

import java.util.List;


/**
 * Discussion Search Interface
 *  
 * @author Thomas Jansing
 * @author Juergen de Braaf
 * @author Peter Schuh
 * @author Tobias Brockmann 
 */
public interface DiscussionSearcher {
	
	public List<DiscussionSearchDomainResult> search(String textToSearch, Long courseId, boolean onlyInTitle, boolean isFuzzy, String submitter);
	
	public List<DiscussionSearchDomainResult> groupSearch(String textToSearch, Long groupId, boolean onlyInTitle, boolean isFuzzy, String submitter);
}