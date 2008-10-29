package org.openuss.lecture;

/**
 * Recreate Lucene Index
 * 
 * @author Ingo Dueppe
 * 
 */
public interface RecreateLectureIndex {

	/**
	 * Force recreation of lecture Index
	 * 
	 * @throws Exception
	 */
	public void recreate() throws Exception;

}
