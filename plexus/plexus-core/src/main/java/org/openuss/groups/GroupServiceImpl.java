// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.Organisation;
import org.openuss.security.Group;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.groups.GroupService
 * @author Lutz D. Kramer
 * 
 */
public class GroupServiceImpl extends org.openuss.groups.GroupServiceBase {

	private static final Logger logger = Logger.getLogger(GroupServiceImpl.class);

	/**
	 * @see org.openuss.groups.GroupService#createUserGroup(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected Long handleCreateUserGroup(UserGroupInfo groupInfo, Long userId)
			throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.isTrue(groupInfo.getId() == null,
				"The Group shouldn't have an ID yet");

		// Transform VO to entity
		UserGroup groupEntity = this.getUserGroupDao().userGroupInfoToEntity(
				groupInfo);
		Validate.notNull(groupEntity, "Cannot transform groupInfo to entity.");

		// Create a default Membership for the Institute
		Membership membership = Membership.Factory.newInstance();
		getMembershipDao().create(membership);
		groupEntity.setMembership(membership);
		
		// Create default SecurityGroups for UserGroup
		GroupItem moderators = new GroupItem();
		moderators.setName("GROUP_" + groupEntity.getId() + "_MODERATORS");
		// TODO - Lutz: Properties anpassen
		moderators.setLabel("autogroup_moderator_label");
		moderators.setGroupType(GroupType.MODERATOR);
		Group moderatorGroup = this.getGroupDao().groupItemToEntity(moderators);
		moderatorGroup = this.getMembershipService().createGroup(groupEntity.getMembership(), moderatorGroup);
		Validate.notNull(moderatorGroup.getId(), "MembershipService.handleCreateGroup - Group couldn't be created");
		moderatorGroup = this.getGroupDao().load(moderatorGroup.getId());
		groupEntity.setModeratorsGroup(moderatorGroup);

		GroupItem members = new GroupItem();
		members.setName("GROUP_" + groupEntity.getId() + "_MEMBERS");
		// TODO - Lutz: Properties anpassen
		members.setLabel("autogroup_member_label");
		members.setGroupType(GroupType.MEMBER);
		Group memberGroup = this.getGroupDao().groupItemToEntity(moderators);
		memberGroup = this.getMembershipService().createGroup(groupEntity.getMembership(), memberGroup);
		Validate.notNull(memberGroup.getId(), "MembershipService.handleCreateGroup - Group couldn't be created");
		moderatorGroup = this.getGroupDao().load(moderatorGroup.getId());
		groupEntity.setMembersGroup(memberGroup);
		
		// Add Creator to Group
		User creator = getUserDao().load(userId);
		groupEntity.setCreator(creator);

		// Save Entity
		this.getUserGroupDao().create(groupEntity);
		Validate.notNull(groupEntity, "Id of course cannot be null.");

		// FIXME - Kai, Indexing should not base on VOs!
		// Kai: Do not delete this!!! Set id of institute VO for indexing
		// Update input parameter for aspects to get the right domain objects.
		groupInfo.setId(groupEntity.getId());
		logger.debug("----> Indexing <---- ");
		
		// Add Creator to Group of Moderators
//		this.addModerator(groupInfo, creator.getId());
		logger.debug("----> Add Creator to Group of Moderators <---- ");

		// Set Security
//		this.getSecurityService().createObjectIdentity(groupEntity, null);
//		this.getSecurityService().setPermissions(moderatorGroup, groupEntity, LectureAclEntry.GROUP_MODERATOR);
//		this.getSecurityService().setPermissions(memberGroup, groupEntity, LectureAclEntry.GROUP_MEMBER);
		logger.debug("----> Set Security <---- ");

//		updateAccessTypePermission(groupEntity);
		logger.debug("----> updateAccessTypePermission <---- ");

		return groupEntity.getId();
	}

	/**
	 * @see org.openuss.groups.GroupService#updateUserGroup(org.openuss.groups.UserGroupInfo)
	 */
	protected void handleUpdateUserGroup(UserGroupInfo groupInfo)
			throws Exception {
		logger.debug("Starting method handleUpdateUserGroup");
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");

		// Transform VO to Entity
		UserGroup groupEntity = getUserGroupDao().userGroupInfoToEntity(
				groupInfo);
		// Update Rights
		updateAccessTypePermission(groupEntity);
		// Update Course
		getUserGroupDao().update(groupEntity);
	}

	/**
	 * @see org.openuss.groups.GroupService#deleteUserGroup(org.openuss.groups.UserGroupInfo)
	 */
	protected void handleDeleteUserGroup(UserGroupInfo groupInfo)
			throws Exception {
		logger.debug("Starting method handleDeleteUserGroup for UserGroupID "
				+ groupInfo.getId());

		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		UserGroup group = this.getUserGroupDao().load(groupInfo.getId());
		Validate.notNull(group, "No Group found to the corresponding ID "
				+ groupInfo.getId());

		// Remove Security
		this.getSecurityService().removeAllPermissions(group);
		this.getSecurityService().removeObjectIdentity(group);

		// Clear Membership
		this.getMembershipService().clearMembership(group.getMembership());

		// Remove Group
		this.getUserGroupDao().remove(group.getId());
	}

	/**
	 * @see org.openuss.groups.GroupService#addModerator(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleAddModerator(UserGroupInfo groupInfo, Long userId)
			throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User must not be null.");

		// Add user to Membership
		Membership membership = group.getMembership();
		getMembershipService().addMember(membership, user);

		// Add user to SecurityGroup - Moderators
		Group secGroup = getSecurityService().getGroupByName(
				"GROUP_" + group.getId() + "_MODERATORS");
		getSecurityService().addAuthorityToGroup(user, secGroup);

	}

	/**
	 * @see org.openuss.groups.GroupService#removeModerator(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleRemoveModerator(UserGroupInfo groupInfo, Long userId)
			throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User must not be null.");

		// Remove user from Security Group - Moderators
		Group secGroupMod = getSecurityService().getGroupByName(
				"GROUP_" + group.getId() + "_MODERATORS");
		getSecurityService().removeAuthorityFromGroup(user, secGroupMod);

		// Add user to SecurityGroup - Members
		Group secGroupMem = getSecurityService().getGroupByName(
				"GROUP_" + group.getId() + "_MEMBERS");
		getSecurityService().addAuthorityToGroup(user, secGroupMem);
	}

	/**
	 * @see org.openuss.groups.GroupService#addMember(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleAddMember(UserGroupInfo groupInfo, Long userId)
			throws Exception {
		Validate.notNull(groupInfo, "GroupInfo cannot be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User must not be null.");

		// Add user to Membership
		Membership membership = group.getMembership();
		getMembershipService().addMember(membership, user);

		// Add user to SecurityGroup - Members
		Group secGroup = getSecurityService().getGroupByName(
				"GROUP_" + group.getId() + "_MEMBERS");
		getSecurityService().addAuthorityToGroup(user, secGroup);

	}

	/**
	 * @see org.openuss.groups.GroupService#addUserByPassword(org.openuss.groups.UserGroupInfo,
	 *      java.lang.String, java.lang.Long)
	 */
	protected void handleAddUserByPassword(UserGroupInfo groupInfo,
			String password, Long userId) throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(password, "Password cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Check password
		if (group.isPasswordCorrect(password)) {
			// add User
			addMember(groupInfo, userId);
		}
	}

	/**
	 * @see org.openuss.groups.GroupService#removeMember(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleRemoveMember(UserGroupInfo groupInfo, Long userId)
			throws Exception {

		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User cannot be null.");

		// Remove User as Aspriant from Membership
		Membership membership = group.getMembership();
		getMembershipService().removeMember(membership, user);
	}

	/**
	 * @see org.openuss.groups.GroupService#addAspirant(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleAddAspirant(UserGroupInfo groupInfo, Long userId)
			throws Exception {
		Validate.notNull(groupInfo, "GroupInfo cannot be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User cannot be null.");

		// Add user to Membership as Aspirant
		Membership membership = group.getMembership();
		getMembershipService().addAspirant(membership, user);
	}

	/**
	 * @see org.openuss.groups.GroupService#acceptAspirant(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleAcceptAspirant(UserGroupInfo groupInfo, Long userId)
			throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User cannot be null.");

		// Remove User as Aspriant from Membership
		Membership membership = group.getMembership();
		getMembershipService().acceptAspirant(membership, user);

		// Add user to SecurityGroup - Members
		Group secGroup = getSecurityService().getGroupByName(
				"GROUP_" + group.getId() + "_MEMBERS");
		getSecurityService().addAuthorityToGroup(user, secGroup);

	}

	/**
	 * @see org.openuss.groups.GroupService#rejectAspirant(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleRejectAspirant(UserGroupInfo groupInfo, Long userId)
			throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User cannot be null.");

		// Remove User as Aspriant from Membership
		Membership membership = group.getMembership();
		getMembershipService().rejectAspirant(membership, user);
	}

	/**
	 * @see org.openuss.groups.GroupService#getMembers(org.openuss.groups.UserGroupInfo)
	 */
	protected List<UserGroupMemberInfo> handleGetMembers(UserGroupInfo groupInfo)
			throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load Membership
		Membership membership = group.getMembership();

		// Load Members
		List<User> users = membership.getMembers();
		return userListToUserGroupMemberInfoList(users, group, group
				.getMembersGroup());
	}

	/**
	 * @see org.openuss.groups.GroupService#getModerators(org.openuss.groups.UserGroupInfo)
	 */
	protected List<UserGroupMemberInfo> handleGetModerators(
			UserGroupInfo groupInfo) throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load Membership
		Membership membership = group.getMembership();

		List<User> users = membership.getMembers();
		return userListToUserGroupMemberInfoList(users, group, group
				.getModeratorsGroup());
	}

	/**
	 * @see org.openuss.groups.GroupService#getAspirants(org.openuss.groups.UserGroupInfo)
	 */
	protected List<UserGroupMemberInfo> handleGetAspirants(
			UserGroupInfo groupInfo) throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load Membership
		Membership membership = group.getMembership();

		List<User> users = membership.getAspirants();
		return userListToUserGroupMemberInfoList(users, group, null);
	}

	/**
	 * @see org.openuss.groups.GroupService#getAllGroups()
	 */
	protected List<UserGroupInfo> handleGetAllGroups() throws Exception {
		Collection<UserGroup> userGroups = getUserGroupDao().loadAll();
		List<UserGroupInfo> group = null;
		for (UserGroup userGroup : userGroups) {
			group.add(getUserGroupDao().toUserGroupInfo(userGroup));

		}
		return group;
	}

	/**
	 * @see org.openuss.groups.GroupService#getGroupsByUser(java.lang.Long)
	 */
	protected List<UserGroupInfo> handleGetGroupsByUser(Long userId)
			throws Exception {

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User cannot be null.");

		Collection<Membership> memberships = getMembershipDao().loadAll();
		List<UserGroupInfo> group = null;
		for (Membership membership : memberships) {
			List<User> members = membership.getMembers();
			for (User member : members) {
				if (member.getId() == userId) {
					group.add(getUserGroupDao().toUserGroupInfo(
							getUserGroupDao().findByMembership(membership)));
				}
			}
		}

		// for (GroupMember member : membership) {
		// group.add(getUserGroupDao().findbyMembership(member));
		// }
		return group;
	}

	/**
	 * @see org.openuss.groups.GroupService#getGroupInfo(java.lang.Long)
	 */
	protected UserGroupInfo handleGetGroupInfo(Long groupId) throws Exception {
		Validate.notNull(groupId, "UserId cannot be null.");
		return getUserGroupDao().toUserGroupInfo(
				getUserGroupDao().load(groupId));
	}

	/**
	 * @see org.openuss.groups.GroupService#isModerator(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected boolean handleIsModerator(UserGroupInfo groupInfo, Long userId)
			throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User cannot be null.");

		Membership membership = group.getMembership();
		// List<User> moderators = membership.getModerators();
		// for(User moderator:moderators){
		// if(moderator == user){
		// return true;
		// }
		// }
		return false;
	}

	/**
	 * @see org.openuss.groups.GroupService#isMember(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected boolean handleIsMember(UserGroupInfo groupInfo, Long userId)
			throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User cannot be null.");

		Membership membership = group.getMembership();
		List<User> members = membership.getMembers();
		for (User member : members) {
			if (member == user) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.openuss.groups.GroupService#isCreator(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected boolean handleIsCreator(UserGroupInfo groupInfo, Long userId)
			throws Exception {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		return !(group.getCreator().getId() == userId);

	}

	/*------------------- private methods -------------------- */

	private void updateAccessTypePermission(UserGroup group) {
		logger.debug("changing group " + group.getName() + " (" + group.getId()
				+ ") to " + group.getAccessType());

		getSecurityService().setPermissions(Roles.ANONYMOUS, group,
				LectureAclEntry.NOTHING);

		if (group.getAccessType() == GroupAccessType.OPEN) {
			getSecurityService().setPermissions(Roles.USER, group,
					LectureAclEntry.GROUP_MEMBER);
		} else {
			getSecurityService().setPermissions(Roles.USER, group,
					LectureAclEntry.NOTHING);
		}
	}

	private List<UserGroupMemberInfo> userListToUserGroupMemberInfoList(
			List<User> users, UserGroup group, Group secGroups) {
		List<User> users2 = null;
		if (secGroups != null) {
			for (User user : users) {
				for (Group secGroup : user.getGroups()) {
					if (secGroup == secGroups) {
						users2.add(user);
					}

				}
			}
			users = users2;
		}
		List<UserGroupMemberInfo> groupMembers = null;
		for (User user : users) {
			UserGroupMemberInfo groupMember = new UserGroupMemberInfo();
			groupMember.setUserId(user.getId());
			groupMember.setUsername(user.getUsername());
			groupMember.setFirstName(user.getFirstName());
			groupMember.setLastName(user.getLastName());
			groupMember.setEMail(user.getEmail());
			if (secGroups == group.getModeratorsGroup()) {
				groupMember.setModerator(true);
			} else {
				groupMember.setModerator(false);
			}
			groupMember.setGroupId(group.getId());
			groupMembers.add(groupMember);
		}
		return groupMembers;
	}
}