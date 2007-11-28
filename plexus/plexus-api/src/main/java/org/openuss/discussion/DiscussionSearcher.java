package org.openuss.discussion;

import java.util.List;

import org.openuss.search.DiscussionSearchDomainResult;

/**
 * Discussion Search Interface
 *  
 * @author Thomas Jansing
 * @author Juergen de Braaf
 * @author Peter Schuh
 * @author Tobias Brockmann 
 */
public interface DiscussionSearcher {
	
	public List<DiscussionSearchDomainResult> search(String textToSearch, Long courseId, boolean onlyInTitle, String submitter);

}