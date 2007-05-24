package org.openuss.lecture;

import java.util.List;

/**
 * Lecture Search Interface 
 * @author Ingo Dueppe
 */
public interface LectureSearcher {
	
	public List search(String textToSearch);

}