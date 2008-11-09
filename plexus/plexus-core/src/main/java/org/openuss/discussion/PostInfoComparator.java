package org.openuss.discussion;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * @author Sebastian Roekens
 *
 */
public class PostInfoComparator implements Comparator<PostInfo>, Serializable{

	private static final long serialVersionUID = 7539148325827130253L;

	/**
	 * compares dates of 2 PostInfo objects
	 */
	public int compare(PostInfo postInfo1, PostInfo postInfo2) {
		if (postInfo1 == null || postInfo1.getCreated()==null) {
			return 0;			
		}
		if (postInfo2 == null || postInfo2.getCreated()==null) {
			return 0;			
		}
		Date d1 = postInfo1.getCreated();
		Date d2 = postInfo2.getCreated();
		if (postInfo1.getLastModification() != null){
			d1 = postInfo1.getLastModification();
		}
		if (postInfo2.getLastModification() != null){
			d2 = postInfo2.getLastModification();
		}		
		return (d1.compareTo(d2));
	}

}