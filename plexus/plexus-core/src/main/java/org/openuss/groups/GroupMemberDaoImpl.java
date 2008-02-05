// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

/**
 * @see org.openuss.groups.GroupMember
 */
public class GroupMemberDaoImpl extends org.openuss.groups.GroupMemberDaoBase {
	/**
	 * @see org.openuss.groups.GroupMemberDao#toGroupMemberInfo(org.openuss.groups.GroupMember,
	 *      org.openuss.groups.GroupMemberInfo)
	 */
	public void toGroupMemberInfo(GroupMember sourceEntity, GroupMemberInfo targetVO) {
		targetVO.setId(sourceEntity.getId());
		targetVO.setMemberType(sourceEntity.getMemberType());
		targetVO.setGroupId(sourceEntity.getGroup().getId());
		targetVO.setUserId(sourceEntity.getUser().getId());
		targetVO.setUserName(sourceEntity.getUser().getUsername());
		targetVO.setFirstName(sourceEntity.getUser().getFirstName());
		targetVO.setLastName(sourceEntity.getUser().getLastName());
		targetVO.setEmail(sourceEntity.getUser().getEmail());
	}

	/**
	 * @see org.openuss.groups.GroupMemberDao#toGroupMemberInfo(org.openuss.groups.GroupMember)
	 */
	public GroupMemberInfo toGroupMemberInfo(final GroupMember entity) {
		return super.toGroupMemberInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private GroupMember loadGroupMemberFromGroupMemberInfo(GroupMemberInfo groupMemberInfo) {
		GroupMember groupMember = this.load(groupMemberInfo.getId());
		if (groupMember == null) {
			groupMember = GroupMember.Factory.newInstance();
		}
		return groupMember;
	}

	/**
	 * @see org.openuss.groups.GroupMemberDao#groupMemberInfoToEntity(org.openuss.groups.GroupMemberInfo)
	 */
	public org.openuss.groups.GroupMember groupMemberInfoToEntity(
			org.openuss.groups.GroupMemberInfo groupMemberInfo) {
		org.openuss.groups.GroupMember entity = this
				.loadGroupMemberFromGroupMemberInfo(groupMemberInfo);
		this.groupMemberInfoToEntity(groupMemberInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.groups.GroupMemberDao#groupMemberInfoToEntity(org.openuss.groups.GroupMemberInfo,
	 *      org.openuss.groups.GroupMember)
	 */
	public void groupMemberInfoToEntity(
			org.openuss.groups.GroupMemberInfo sourceVO,
			org.openuss.groups.GroupMember targetEntity, boolean copyIfNull) {
		super.groupMemberInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}