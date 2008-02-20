// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

/**
 * @see org.openuss.groups.UserGroup
 * @author Lutz D. Kramer
 */
public class UserGroupDaoImpl extends org.openuss.groups.UserGroupDaoBase {

	/**
	 * @see org.openuss.groups.UserGroupDao#toUserGroupInfo(org.openuss.groups.UserGroup,
	 *      org.openuss.groups.UserGroupInfo)
	 */
	public void toUserGroupInfo(UserGroup sourceEntity, UserGroupInfo targetVO) {
		super.toUserGroupInfo(sourceEntity, targetVO);
		targetVO.setAccessType(sourceEntity.getAccessType());
		targetVO.setCalendar(sourceEntity.getCalendar());
		targetVO.setChat(sourceEntity.getChat());
		targetVO.setCreator(sourceEntity.getCreator().getId());
		targetVO.setCreatorName(sourceEntity.getCreator().getUsername());
		targetVO.setDescription(sourceEntity.getDescription());
		targetVO.setDocuments(sourceEntity.getDocuments());
		targetVO.setForum(sourceEntity.getForum());
		targetVO.setId(sourceEntity.getId());
		targetVO.setName(sourceEntity.getName());
		targetVO.setNewsletter(sourceEntity.getNewsletter());
		targetVO.setPassword(sourceEntity.getPassword());
		targetVO.setShortcut(sourceEntity.getShortcut());
	}

	/**
	 * @see org.openuss.groups.UserGroupDao#toUserGroupInfo(org.openuss.groups.UserGroup)
	 */
	public UserGroupInfo toUserGroupInfo(final UserGroup entity) {
		if (entity != null) {
			UserGroupInfo targetVO = new UserGroupInfo();
			toUserGroupInfo(entity, targetVO);
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
	private UserGroup loadUserGroupFromUserGroupInfo(UserGroupInfo userGroupInfo) {
		UserGroup group = null;
		if (userGroupInfo != null && userGroupInfo.getId() != null) {
			group = this.load(userGroupInfo.getId());
		}
		if (group == null) {
			group = UserGroup.Factory.newInstance();
		}
		return group;
	}

	/**
	 * @see org.openuss.groups.UserGroupDao#userGroupInfoToEntity(org.openuss.groups.UserGroupInfo)
	 */
	public UserGroup userGroupInfoToEntity(UserGroupInfo userGroupInfo) {
		UserGroup entity = this.loadUserGroupFromUserGroupInfo(userGroupInfo);
		this.userGroupInfoToEntity(userGroupInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.groups.UserGroupDao#userGroupInfoToEntity(org.openuss.groups.UserGroupInfo,
	 *      org.openuss.groups.UserGroup)
	 */
	public void userGroupInfoToEntity(UserGroupInfo sourceVO,
			UserGroup targetEntity, boolean copyIfNull) {
		super.userGroupInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}