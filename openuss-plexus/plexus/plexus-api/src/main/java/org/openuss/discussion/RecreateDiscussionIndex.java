package org.openuss.discussion;

/**
 * Recreate Discussion Lucene Index
 * 
 * @author Thomas Jansing
 * @author Juergen de Braaf
 * @author Peter Schuh
 * @author Tobias Brockmann
 */
public interface RecreateDiscussionIndex {

	/**
	 * Force recreation of lecture Index
	 * 
	 * @throws Exception
	 */
	public void recreate() throws Exception;

}
