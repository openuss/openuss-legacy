package org.openuss.discussion;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author Sebastian Roekens
 *
 */
public class TopicInfoComparator implements Comparator<TopicInfo>, Serializable{

	private static final long serialVersionUID = 2590681090108184395L;

	/**
	 * compares last name of user
	 */
	public int compare(TopicInfo topicInfo1, TopicInfo topicInfo2) {
		return (topicInfo2.getCreated().compareTo(topicInfo1.getCreated()));
	}
	
}