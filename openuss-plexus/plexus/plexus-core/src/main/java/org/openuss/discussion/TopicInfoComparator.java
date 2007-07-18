package org.openuss.discussion;

import java.util.Comparator;

/**
 * @author Sebastian Roekens
 *
 */
public class TopicInfoComparator implements Comparator{

	/**
	 * compares last name of user
	 */
	public int compare(Object o1, Object o2) {		
		if (o1 instanceof TopicInfo){
			if (o2 instanceof TopicInfo){
				TopicInfo topicInfo1 = (TopicInfo) o1;
				TopicInfo topicInfo2 = (TopicInfo) o2;
				//reverse sorting -> newest topics on top of list
				return (topicInfo2.getCreated().compareTo(topicInfo1.getCreated()));
			}
		}
		return 0;
	}
	
}