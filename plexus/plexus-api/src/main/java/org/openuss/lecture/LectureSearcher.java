package org.openuss.lecture;

import java.util.List;

import org.openuss.search.DomainResult;

/**
 * Lecture Search Interface
 * 
 * @author Ingo Dueppe
 */
public interface LectureSearcher {

	public List<DomainResult> search(String textToSearch, boolean fuzzy);

}