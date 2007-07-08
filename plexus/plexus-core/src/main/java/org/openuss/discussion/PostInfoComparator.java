package org.openuss.discussion;

import java.util.Comparator;

/**
 * @author Sebastian Roekens
 *
 */
public class PostInfoComparator implements Comparator{

	/**
	 * compares last name of user
	 */
	public int compare(Object o1, Object o2) {		
		if (o1 instanceof PostInfo){
			if (o2 instanceof PostInfo){
				PostInfo postInfo1 = (PostInfo) o1;
				PostInfo postInfo2 = (PostInfo) o2;
				return (postInfo1.getCreated().compareTo(postInfo2.getCreated()));
			}
		}
		return 0;
	}
	
}