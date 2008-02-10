// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

/**
 * @see org.openuss.groups.UserGroup
 */
public class UserGroupDaoImpl extends org.openuss.groups.UserGroupDaoBase {
	/**
	 * @see org.openuss.groups.GroupsDao#toGroupInfo(org.openuss.groups.UserGroup,
	 *      org.openuss.groups.GroupInfo)
	 */
	public void toGroupInfo(UserGroup sourceEntity, GroupInfo targetVO) {
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
	 * @see org.openuss.groups.GroupsDao#toGroupInfo(org.openuss.groups.UserGroup)
	 */
	public GroupInfo toGroupInfo(final UserGroup entity) {
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
	private UserGroup loadGroupsFromGroupInfo(GroupInfo groupInfo) {
		UserGroup groups = null;
		if (groupInfo != null && groupInfo.getId() != null) {
			groups = this.load(groupInfo.getId());
		}
		if (groups == null) {
			groups = UserGroup.Factory.newInstance();
		}
		return groups;
	}

	/**
	 * @see org.openuss.groups.GroupsDao#groupInfoToEntity(org.openuss.groups.GroupInfo)
	 */
	public UserGroup groupInfoToEntity(GroupInfo groupInfo) {
		UserGroup entity = this.loadGroupsFromGroupInfo(groupInfo);
		this.groupInfoToEntity(groupInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.groups.GroupsDao#groupInfoToEntity(org.openuss.groups.GroupInfo,
	 *      org.openuss.groups.UserGroup)
	 */
	public void groupInfoToEntity(GroupInfo sourceVO, UserGroup targetEntity,
			boolean copyIfNull) {
		super.groupInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}
}