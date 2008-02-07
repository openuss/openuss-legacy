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
	protected void handleRemoveMember(long memberId) throws Exception {
		GroupMember member = getGroupMemberDao().load(memberId);
		if (member != null) {
			getSecurityService().removePermission(member.getUser(),
					member.getGroup());
			getGroupMemberDao().remove(member);
		}
	}

	/**
	 * @see org.openuss.groups.GroupService#acceptAspirant(Long)
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
	 * @see org.openuss.groups.GroupService#rejectAspirant(Long)
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
	protected void handleAddModerator(GroupInfo group, User user)
			throws Exception {
		GroupMember aspirant = retrieveCourseMember(getGroupsDao()
				.groupInfoToEntity(group), user);
		aspirant.setMemberType(GroupMemberType.MODERATOR);
		persistGroupMember(aspirant);
	}

	/**
	 * @see org.openuss.groups.GroupService#addAspirant(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected void handleAddAspirant(GroupInfo group, User user)
			throws Exception {
		GroupMember aspirant = retrieveCourseMember(getGroupsDao()
				.groupInfoToEntity(group), user);
		aspirant.setMemberType(GroupMemberType.ASPIRANT);
		persistGroupMember(aspirant);
	}

	/**
	 * @see org.openuss.groups.GroupService#addMember(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.UserInfo)
	 */
	protected void handleAddMember(GroupInfo group, User user) throws Exception {
		GroupMember aspirant = retrieveCourseMember(getGroupsDao()
				.groupInfoToEntity(group), user);
		aspirant.setMemberType(GroupMemberType.MEMBER);
		persistGroupMember(aspirant);
	}

	/**
	 * @see org.openuss.groups.GroupService#addUserByPassword(java.lang.String,
	 *      org.openuss.groups.GroupInfo, org.openuss.security.User)
	 */
	protected void handleAddUserByPassword(String password, GroupInfo group,
			User user) throws Exception {
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
	 * @see org.openuss.groups.GroupService#getGroupInfo(Long)
	 */
	protected GroupInfo handleGetGroupInfo(Long groupId) throws Exception {
		Validate.notNull(groupId, "Parameter groupId must not be null!");
		return (GroupInfo) getGroupsDao().load(GroupsDao.TRANSFORM_GROUPINFO,
				groupId);
	}

	/**
	 * @see org.openuss.groups.GroupService#getMemberInfo(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.User)
	 */
	protected GroupMemberInfo handleGetMemberInfo(GroupInfo group, User user)
			throws Exception {
		return (GroupMemberInfo) getGroupMemberDao().findByUserAndGroup(
				GroupMemberDao.TRANSFORM_GROUPMEMBERINFO,
				user,
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
	 *      org.openuss.security.User)
	 */
	protected Long handleCreate(GroupInfo group, User user)
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
		
		// ADD User as Creator
		GroupMember creater = retrieveCourseMember(getGroupsDao().groupInfoToEntity(group), user);
		creater.setMemberType(GroupMemberType.CREATOR);
		persistGroupMember(creater);

		updateAccessTypePermission(groupEntity);

		return groupEntity.getId();
	}

	/**
	 * @see org.openuss.groups.GroupService#deleteGroup(Long)
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
	 * @see org.openuss.groups.GroupService#findGroups(org.openuss.security.User)
	 */
	protected List<GroupInfo> handleFindGroups(User user) throws Exception {
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
	 *      org.openuss.security.User)
	 */
	protected boolean handleIsModerator(GroupInfo groupInfo, User user)
			throws Exception {
		GroupMember member = getGroupMemberDao().findByUserAndGroup(user,
				getGroupsDao().groupInfoToEntity(groupInfo));
		return (member.getMemberType() == org.openuss.groups.GroupMemberType.MODERATOR);
	}

	/**
	 * @see org.openuss.groups.GroupService#isMember(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.User)
	 */
	protected boolean handleIsMember(GroupInfo groupInfo, User user)
			throws Exception {
		GroupMember member = getGroupMemberDao().findByUserAndGroup(user,
				getGroupsDao().groupInfoToEntity(groupInfo));
		return (member.getMemberType() == org.openuss.groups.GroupMemberType.MEMBER);
	}

	/**
	 * @see org.openuss.groups.GroupService#isCreator(org.openuss.groups.GroupInfo,
	 *      org.openuss.security.User)
	 */
	protected boolean handleIsCreator(GroupInfo groupInfo, User user)
			throws Exception {
		GroupMember member = getGroupMemberDao().findByUserAndGroup(user,
				getGroupsDao().groupInfoToEntity(groupInfo));
		return (member.getMemberType() == org.openuss.groups.GroupMemberType.CREATOR);
	}

	/*------------------- private methods -------------------- */
	private void updateAccessTypePermission(Groups group) {
		logger.debug("changing group " + group.getName() + " (" + group.getId() + ") to " + group.getAccessType());
		getSecurityService().setPermissions(Roles.ANONYMOUS, group, LectureAclEntry.NOTHING);
		getSecurityService().setPermissions(Roles.USER, group, LectureAclEntry.NOTHING);
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
		getSecurityService().setPermissions(participant.getUser(),participant.getGroup(), LectureAclEntry.COURSE_PARTICIPANT);
		persistGroupMember(participant);
	}

}