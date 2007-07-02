package org.openuss.lecture;

/**
 * Lecture index 
 * 
 * @author Ingo Dueppe
 */
public interface LectureIndex {

	/**
	 * Recreates all entries within the lecture index.
	 * @throws Exception
	 */
	public void recreate() throws Exception;
}
