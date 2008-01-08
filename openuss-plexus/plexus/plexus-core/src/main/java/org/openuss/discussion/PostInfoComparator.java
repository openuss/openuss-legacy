package org.openuss.discussion;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author Sebastian Roekens
 *
 */
public class PostInfoComparator implements Comparator<PostInfo>, Serializable{

	private static final long serialVersionUID = 7539148325827130253L;

	/**
	 * compares last name of user
	 */
	public int compare(PostInfo postInfo1, PostInfo postInfo2) {
		return (postInfo1.getCreated().compareTo(postInfo2.getCreated()));
	}
	
}