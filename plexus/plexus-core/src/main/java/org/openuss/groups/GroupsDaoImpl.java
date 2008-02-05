// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

import org.openuss.lecture.Course;
import org.openuss.lecture.CourseInfo;

/**
 * @see org.openuss.groups.Groups
 */
public class GroupsDaoImpl extends org.openuss.groups.GroupsDaoBase {
	/**
	 * @see org.openuss.groups.GroupsDao#toGroupInfo(org.openuss.groups.Groups,
	 *      org.openuss.groups.GroupInfo)
	 */
	public void toGroupInfo(Groups sourceEntity, GroupInfo targetVO) {
		super.toGroupInfo(sourceEntity, targetVO);
		targetVO.setId(sourceEntity.getId());
		targetVO.setAccessType(sourceEntity.getAccessType());
		targetVO.setCalendar(sourceEntity.getCalendar());
		targetVO.setChat(sourceEntity.getChat());
		targetVO.setDescription(sourceEntity.getDescription());
		targetVO.setDocuments(sourceEntity.getDocuments());
		targetVO.setForum(sourceEntity.getForum());
		targetVO.setName(sourceEntity.getName());
		targetVO.setNewsletter(sourceEntity.getNewsletter());
		targetVO.setPassword(sourceEntity.getPassword());
		targetVO.setShortcut(sourceEntity.getShortcut());
	}

	/**
	 * @see org.openuss.groups.GroupsDao#toGroupInfo(org.openuss.groups.Groups)
	 */
	public GroupInfo toGroupInfo(final Groups entity) {
		if (entity != null) { 
			GroupInfo targetVO = new GroupInfo();
			toGroupInfo(entity, targetVO);
			return targetVO;
		} else {
			return null;
		}
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Groups loadGroupsFromGroupInfo(GroupInfo groupInfo) {
		Groups groups = null;
		if (groupInfo != null && groupInfo.getId() != null) {
			groups = this.load(groupInfo.getId());
		}
		if (groups == null) {
			groups = Groups.Factory.newInstance();
		}
		return groups;
	}

	/**
	 * @see org.openuss.groups.GroupsDao#groupInfoToEntity(org.openuss.groups.GroupInfo)
	 */
	public Groups groupInfoToEntity(GroupInfo groupInfo) {
		Groups entity = this.loadGroupsFromGroupInfo(groupInfo);
		this.groupInfoToEntity(groupInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.groups.GroupsDao#groupInfoToEntity(org.openuss.groups.GroupInfo,
	 *      org.openuss.groups.Groups)
	 */
	public void groupInfoToEntity(GroupInfo sourceVO, Groups targetEntity, boolean copyIfNull) {
		super.groupInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}