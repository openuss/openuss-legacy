package org.openuss.search;

import java.util.List;

import org.openuss.search.DomainResult;

/**
 * Lecture Search Interface 
 * @author Malte Stockmann
 */
public interface ExtendedSearcher {
	
	public List<DomainResult> search(String textToSearch, Long resultTypeId, Long universityId, Long departmentId, Long instituteId, Long courseTypeId, boolean onlyOfficial, boolean onlyInTitle);

}