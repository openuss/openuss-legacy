package org.openuss.search;

import java.util.List;

/**
 * Lecture Search Interface
 * 
 * @author Malte Stockmann
 */
public interface ExtendedSearcher {

	public List<DomainResult> search(String textToSearch, String domainType, Long universityId, Long departmentId,
			Long instituteId, Long courseTypeId, Long periodId, boolean onlyOfficial, boolean onlyInTitle);

}