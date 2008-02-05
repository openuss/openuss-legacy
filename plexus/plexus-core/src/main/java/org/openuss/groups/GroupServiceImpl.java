// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.groups.GroupService
 */
public class GroupServiceImpl extends org.openuss.groups.GroupServiceBase {

	private static final Logger logger = Logger
			.getLogger(GroupServiceImpl.class);

	/**
	 * @see org.openuss.groups.GroupService#removeMember(long)
	 */
	protected void handleRemoveMember(long memberId) throws java.lang.Exception {
		GroupMember member = getGroupMemberDao().load(memberId);
		if (member != null) {
			getSecurityService().removePermission(member.getUser(),
					member.getGroup());
			getGroupMemberDao().remove(member);
		}
	}

	/**
	 * @see org.openuss.groups.GroupService#acceptAspirant(java.lang.Long)
	 */
	protected void handleAcceptAspirant(Long memberId) throws Exception {
		GroupMember member = getGroupMemberDao().load(memberId);
		if (member.getMemberType() == GroupMemberType.ASPIRANT) {
			persistParticipantWithPermissions(member);
		}
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("groupname", "" + member.getGroup().getName() + "("
				+ member.getGroup().getShortcut() + ")");
		getMessageService().sendMessage(
				member.getGroup().getName() + "("
						+ member.getGroup().getShortcut() + ")",
				"course.application.subject", "courseapplicationapply",
				parameters, member.getUser());
	}

	/**
	 * @see org.openuss.groups.GroupService#rejectAspirant(java.lang.Long)
	 */
	protected void handleRejectAspirant(Long memberId) throws Exception {
		GroupMember member = getGroupMemberDao().load(memberId);
		removeMember(memberId);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("groupename", "" + member.getGroup().getName() + "("
				+ member.getGroup().getShortcut() + ")");
		getMessageService().sendMessage(
				member.getGroup().getName() + "("
						+ member.getGroup().getShortcut() + ")",
				"group.application.subject", "groupapplicationreject",
				parameters, member.getUser());
	}

	/**
	 * @see org.openuss.groups.GroupService#getMember(org.openuss.groups.GroupInfo)
	 */
	protected List<GroupMemberInfo> handleGetMember(
			org.openuss.groups.GroupInfo group) throws Exception {
		return getGroupMemberDao()
				.findByType(GroupMemberDao.TRANSFORM_GROUPMEMBERINFO,
						getGroupsDao().groupInfoToEntity(group),
						GroupMemberType.MEMBER);
	}

	/**
	 * @see org.openuss.groups.GroupService#getModerators(org.openuss.groups.GroupInfo)
	 */
	protected List<GroupMemberInfo> handleGetModerators(GroupInfo group)
			throws Exception {
		return getGroupMemberDao().findByType(
				GroupMemberDao.TRANSFORM_GROUPMEMBERINFO,
				getGroupsDao().groupInfoToEntity(group),
				GroupMemberType.MODERATOR);
	}

	/**
	 * @see org.openuss.groups.GroupService#getAspirants(org.openuss.groups.GroupInfo)
	 */
	protected List<GroupMemberInfo> handleGetAspirants(GroupInfo group)
			throws Exception {
		return getGroupMemberDao().findByType(
				GroupMemberDao.TRANSFORM_GROUPMEMBERINFO,
				getGroupsDao().groupInfoToEntity(group),
				GroupMemberType.ASPIRANT);
	}

	/**
	 * @see org.openuss.groups.GroupService#addModerator(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected void handleAddModerator(GroupInfo group, UserInfo user)
			throws Exception {
		GroupMember aspirant = retrieveCourseMember(getGroupsDao()
				.groupInfoToEntity(group), getUserDao().userInfoToEntity(user));
		aspirant.setMemberType(GroupMemberType.MODERATOR);
		persistGroupMember(aspirant);
	}

	/**
	 * @see org.openuss.groups.GroupService#addAspirant(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected void handleAddAspirant(GroupInfo group, UserInfo user)
			throws Exception {
		GroupMember aspirant = retrieveCourseMember(getGroupsDao()
				.groupInfoToEntity(group), getUserDao().userInfoToEntity(user));
		aspirant.setMemberType(GroupMemberType.ASPIRANT);
		persistGroupMember(aspirant);
	}

	/**
	 * @see org.openuss.groups.GroupService#addMember(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected void handleAddMember(GroupInfo group, UserInfo user)
			throws Exception {
		GroupMember aspirant = retrieveCourseMember(getGroupsDao()
				.groupInfoToEntity(group), getUserDao().userInfoToEntity(user));
		aspirant.setMemberType(GroupMemberType.MEMBER);
		persistGroupMember(aspirant);
	}

	/**
	 * @see org.openuss.groups.GroupService#addUserByPassword(java.lang.String,
	 *      org.openuss.groups.GroupInfo, org.openuss.security.UserInfo)
	 */
	protected void handleAddUserByPassword(String password, GroupInfo group,
			UserInfo user) throws Exception {
		Groups originalGroup = getGroupsDao().groupInfoToEntity(group);
		if (originalGroup.getAccessType() == org.openuss.groups.AccessType.PASSWORD
				&& originalGroup.isPasswordCorrect(password)) {
			addMember(group, user);
		} else {
			throw new GroupApplicationException(
					"message_error_password_is_not_correct");
		}
	}

	/**
	 * @see org.openuss.groups.GroupService#getGroupInfo(java.lang.Long)
	 */
	protected GroupInfo handleGetGroupInfo(Long groupId) throws Exception {
		Validate.notNull(groupId, "Parameter groupId must not be null!");
		return (GroupInfo) getGroupsDao().load(GroupsDao.TRANSFORM_GROUPINFO,
				groupId);
	}

	/**
	 * @see org.openuss.groups.GroupService#getMemberInfo(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected GroupMemberInfo handleGetMemberInfo(GroupInfo group, UserInfo user)
			throws Exception {
		return (GroupMemberInfo) getGroupMemberDao().findByUserAndGroup(
				GroupMemberDao.TRANSFORM_GROUPMEMBERINFO,
				getUserDao().userInfoToEntity(user),
				getGroupsDao().groupInfoToEntity(group));
	}

	/**
	 * @see org.openuss.groups.GroupService#removeAspirant(org.openuss.groups.GroupInfo)
	 */
	protected void handleRemoveAspirant(GroupInfo group) throws Exception {
		Groups groupDao = getGroupsDao().load(group.getId());
		List<GroupMember> members = getGroupMemberDao().findByGroup(groupDao);
		Iterator<GroupMember> i = members.iterator();
		GroupMember member;
		while (i.hasNext()) {
			member = i.next();
			if (member.getMemberType() == GroupMemberType.ASPIRANT) {
				getGroupMemberDao().remove(member.getId());
			}
		}
	}

	/**
	 * @see org.openuss.groups.GroupService#updateGroup(org.openuss.groups.GroupInfo)
	 */
	protected void handleUpdateGroup(GroupInfo group) throws Exception {
		logger.debug("Starting method handleUpdateCourse");
		Validate.notNull(group, "Parameter group must not be null.");
		Validate.notNull(group.getId(),
				"Parameter group must contain a valid course id.");
		// Transform VO to Entity
		Groups groupEntity = getGroupsDao().groupInfoToEntity(group);
		// Update Rights
		updateAccessTypePermission(groupEntity);
		// Update Course
		getGroupsDao().update(groupEntity);
	}

	/**
	 * @see org.openuss.groups.GroupService#create(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected java.lang.Long handleCreate(GroupInfo group, UserInfo user)
			throws Exception {
		Validate.notNull(group, "GroupInfo cannot be null.");

		// Transform VO to entity
		Groups groupEntity = this.getGroupsDao().groupInfoToEntity(group);
		Validate.notNull(groupEntity, "Cannot transform groupInfo to entity.");

		// Save Entity
		this.getGroupsDao().create(groupEntity);
		Validate.notNull(groupEntity, "Id of group cannot be null.");

		// Update input parameter for aspects to get the right domain objects.
		group.setId(groupEntity.getId());

		// Set Security
		this.getSecurityService().createObjectIdentity(groupEntity, null);

		updateAccessTypePermission(groupEntity);

		return groupEntity.getId();
	}

	/**
	 * @see org.openuss.groups.GroupService#deleteGroup(java.lang.Long)
	 */
	protected void handleDeleteGroup(Long groupId) throws Exception {
		Validate.notNull(groupId, "GroupId cannot be null.");
		Groups group = (Groups) this.getGroupsDao().load(groupId);
		Validate.notNull(group,
				"No group entity found with the corresponding groupId "
						+ groupId);

		// Remove Security
		this.getSecurityService().removeAllPermissions(group);
		this.getSecurityService().removeObjectIdentity(group);

		// Remove Course
		this.getGroupsDao().remove(groupId);
	}

	/**
	 * @see org.openuss.groups.GroupService#findGroups(org.openuss.security.UserInfo)
	 */
	protected List<GroupInfo> handleFindGroups(UserInfo userInfo)
			throws Exception {
		Validate.notNull(userInfo, "UserInfo cannot be null.");

		// Load entity
		User user = this.getUserDao().userInfoToEntity(userInfo);
		Validate.notNull(user, "UserInfo cannot be transformed to user.");

		List<GroupMember> groupMembers = this.getGroupMemberDao().findByUser(
				user);
		List<GroupInfo> groups = new ArrayList<GroupInfo>();
		for (GroupMember member : groupMembers) {
			groups.add(getGroupsDao().toGroupInfo(member.getGroup()));
		}
		return groups;
	}

	/**
	 * @see org.openuss.groups.GroupService#getCreater(org.openuss.groups.GroupInfo)
	 */
	protected GroupMemberInfo handleGetCreater(GroupInfo groupInfo)
			throws Exception {
		List<GroupMemberInfo> creator = getGroupMemberDao().findByType(
				GroupMemberDao.TRANSFORM_GROUPMEMBERINFO,
				getGroupsDao().groupInfoToEntity(groupInfo),
				GroupMemberType.CREATOR);
		if (creator.isEmpty()) {
			throw new GroupApplicationException(
					"message_error_creator_is_deleted");
		} else {
			return creator.get(0);
		}
	}

	/**
	 * @see org.openuss.groups.GroupService#isModerator(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected boolean handleIsModerator(GroupInfo groupInfo, UserInfo userInfo)
			throws Exception {
		GroupMember member = getGroupMemberDao().findByUserAndGroup(
				getUserDao().userInfoToEntity(userInfo),
				getGroupsDao().groupInfoToEntity(groupInfo));
		return (member.getMemberType() == org.openuss.groups.GroupMemberType.MODERATOR);
	}

	/**
	 * @see org.openuss.groups.GroupService#isMember(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected boolean handleIsMember(GroupInfo groupInfo, UserInfo userInfo)
			throws Exception {
		GroupMember member = getGroupMemberDao().findByUserAndGroup(
				getUserDao().userInfoToEntity(userInfo),
				getGroupsDao().groupInfoToEntity(groupInfo));
		return (member.getMemberType() == org.openuss.groups.GroupMemberType.MEMBER);
	}

	/**
	 * @see org.openuss.groups.GroupService#isCreator(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected boolean handleIsCreator(GroupInfo groupInfo, UserInfo userInfo)
			throws Exception {
		GroupMember member = getGroupMemberDao().findByUserAndGroup(
				getUserDao().userInfoToEntity(userInfo),
				getGroupsDao().groupInfoToEntity(groupInfo));
		return (member.getMemberType() == org.openuss.groups.GroupMemberType.CREATOR);
	}

	/*------------------- private methods -------------------- */
	//TODO - Lutz: Check Permission-States with Ingo

	private void updateAccessTypePermission(Groups group) {
		logger.debug("changing group " + group.getName() + " (" + group.getId()
				+ ") to " + group.getAccessType());

// 		getSecurityService().setPermissions(Roles.ANONYMOUS, group,
// 		LectureAclEntry.NOTHING);
//		if (group.getAccessType() == org.openuss.groups.AccessType.OPEN
//				|| group.getAccessType() == org.openuss.groups.AccessType.ANONYMOUS) {
//			getSecurityService().setPermissions(Roles.USER, group,
//					LectureAclEntry.COURSE_PARTICIPANT);
//		} else {
//			getSecurityService().setPermissions(Roles.USER, group,
//					LectureAclEntry.NOTHING);
//		}
	}

	private GroupMember retrieveCourseMember(Groups group, User user) {
		GroupMember member = getGroupMemberDao()
				.findByUserAndGroup(user, group);
		if (member == null) {
			member = GroupMember.Factory.newInstance();
			group = getGroupsDao().load(group.getId());
			user = getSecurityService().getUser(user.getId());
			member.setGroup(group);
			member.setUser(user);
		}
		return member;
	}

	private void persistGroupMember(GroupMember member) {
		if (member.getId() == null) {
			getGroupMemberDao().create(member);
		} else {
			getGroupMemberDao().update(member);
		}
	}

	private void persistParticipantWithPermissions(GroupMember participant) {
		participant.setMemberType(GroupMemberType.MEMBER);
		// TODO - Lutz: LectureAclEntry.COURSE_PARTICIPANT -->
		// GroupMemberType.MEMBER ???
// 		getSecurityService().setPermissions(participant.getUser(),
// 		participant.getGroup(), GroupMemberType.MEMBER.getValue());
		persistGroupMember(participant);
	}

}