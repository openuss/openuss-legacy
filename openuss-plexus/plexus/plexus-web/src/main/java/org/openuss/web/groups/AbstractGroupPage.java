package org.openuss.web.groups;

import org.openuss.group.WorkingGroupInfo;
import org.openuss.web.course.AbstractCoursePage;

public abstract class AbstractGroupPage extends AbstractCoursePage{
	public String getNameWithSizeInfo(WorkingGroupInfo wgi){
		return wgi.getGroupName()+" ("+wgi.getMembersReal().toString()+"/"+wgi.getMemberCount().toString()+")";
	}
}