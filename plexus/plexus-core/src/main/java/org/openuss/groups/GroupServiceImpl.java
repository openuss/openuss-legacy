// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.security.Authority;
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
	
	private static final Logger logger = Logger
	.getLogger(GroupServiceImpl.class);

	/**
	 * @see org.openuss.groups.GroupService#createUserGroup(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected Long handleCreateUserGroup(UserGroupInfo groupInfo, Long userId) {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.isTrue(groupInfo.getId() == null,
				"The Group shouldn't have an ID yet, the aktual ID is: "
						+ groupInfo.getId());

		// Transform VO to entity
		UserGroup groupEntity = this.getUserGroupDao().userGroupInfoToEntity(
				groupInfo);
		Validate.notNull(groupEntity, "Cannot transform groupInfo to entity.");

		// Create a default Membership for the Group
		Membership membership = Membership.Factory.newInstance();
		getMembershipDao().create(membership);
		groupEntity.setMembership(membership);

		// Create default SecurityGroups for UserGroup
		GroupItem moderators = new GroupItem();
		moderators.setName("GROUP_MODERATORS");
		moderators.setLabel("autogroup_moderator_label");
		moderators.setGroupType(GroupType.MODERATOR);
		Group moderatorGroup = this.getGroupDao().groupItemToEntity(moderators);
		moderatorGroup = this.getMembershipService().createGroup(
				groupEntity.getMembership(), moderatorGroup);
		Validate
				.notNull(moderatorGroup.getId(),
						"MembershipService.handleCreateGroup - Group couldn't be created");
		moderatorGroup = this.getGroupDao().load(moderatorGroup.getId());
		groupEntity.setModeratorsGroup(moderatorGroup);

		GroupItem members = new GroupItem();
		members.setName("GROUP_MEMBERS");
		members.setLabel("autogroup_member_label");
		members.setGroupType(GroupType.MEMBER);
		Group memberGroup = this.getGroupDao().groupItemToEntity(members);
		memberGroup = this.getMembershipService().createGroup(
				groupEntity.getMembership(), memberGroup);
		Validate
				.notNull(memberGroup.getId(),
						"MembershipService.handleCreateGroup - Group couldn't be created");
		moderatorGroup = this.getGroupDao().load(moderatorGroup.getId());
		groupEntity.setMembersGroup(memberGroup);

		// Add Creator to Group
		User creator = getUserDao().load(userId);
		groupEntity.setCreator(creator);

		// Save Entity
		this.getUserGroupDao().create(groupEntity);
		Validate.notNull(groupEntity, "Id of course cannot be null.");

		// Update SecurityGroups and groupEntity
		moderatorGroup.setName("GROUP_" + groupEntity.getId() + "_MODERATORS");
		getGroupDao().update(moderatorGroup);
		groupEntity.setModeratorsGroup(moderatorGroup);
		memberGroup.setName("GROUP_" + groupEntity.getId() + "_MEMBERS");
		getGroupDao().update(memberGroup);
		groupEntity.setMembersGroup(memberGroup);
		this.getUserGroupDao().update(groupEntity);

		// Update input parameter for aspects to get the right domain objects.
		groupInfo.setId(groupEntity.getId());

		// Add Creator as Member
		this.addMember(groupInfo, creator.getId());

		// Add Creator to Group of Moderators
		this.addModerator(groupInfo, creator.getId());

		// Set Security
		this.getSecurityService().createObjectIdentity(groupEntity, null);
		this.getSecurityService().setPermissions(moderatorGroup, groupEntity,
				LectureAclEntry.GROUP_MODERATOR);
		this.getSecurityService().setPermissions(memberGroup, groupEntity,
				LectureAclEntry.GROUP_MEMBER);

		updateAccessTypePermission(groupEntity);

		return groupEntity.getId();
	}

	/**
	 * @see org.openuss.groups.GroupService#updateUserGroup(org.openuss.groups.UserGroupInfo)
	 */
	protected void handleUpdateUserGroup(UserGroupInfo groupInfo) {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");

		// Transform VO to Entity
		UserGroup groupEntity = getUserGroupDao().userGroupInfoToEntity(
				groupInfo);

		// Check Access-Type Open --> Add Aspirants
		if (groupInfo.getAccessType() == GroupAccessType.OPEN) {
			List<UserGroupMemberInfo> aspirants = this.getAspirants(groupInfo);
			for (UserGroupMemberInfo aspirant : aspirants) {
				this.addAspirant(groupInfo, aspirant.getUserId());
			}
		}

		// Check Access-Type Password --> Reject Aspirants
		if (groupInfo.getAccessType() == GroupAccessType.PASSWORD) {
			List<UserGroupMemberInfo> aspirants = this.getAspirants(groupInfo);
			for (UserGroupMemberInfo aspirant : aspirants) {
				this.rejectAspirant(groupInfo, aspirant.getUserId());
			}
		}

		// Update Rights
		updateAccessTypePermission(groupEntity);

		// Update Course
		getUserGroupDao().update(groupEntity);
	}

	/**
	 * @see org.openuss.groups.GroupService#deleteUserGroup(org.openuss.groups.UserGroupInfo)
	 */
	protected void handleDeleteUserGroup(UserGroupInfo groupInfo) {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");

		// Load Group
		UserGroup group = this.getUserGroupDao().load(groupInfo.getId());
		Validate.notNull(group, "No Group found to the corresponding ID "
				+ groupInfo.getId());

		// Remove all Members
		List<UserGroupMemberInfo> members = this.getAllMembers(groupInfo);
		if (members.size() > 0) {
			for (UserGroupMemberInfo member : members) {
				this.removeMember(groupInfo, member.getUserId());
			}
		}

		// Remove all Aspirants
		List<UserGroupMemberInfo> aspirants = this.getAspirants(groupInfo);
		if (aspirants.size() > 0) {
			for (UserGroupMemberInfo aspirant : aspirants) {
				this.rejectAspirant(groupInfo, aspirant.getUserId());
			}
		}

		// Remove Security
		this.getSecurityService().removeAllPermissions(group);
		this.getSecurityService().removeObjectIdentity(group);

		// Remove Group
		this.getUserGroupDao().remove(group.getId());
	}

	/**
	 * @see org.openuss.groups.GroupService#addModerator(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleAddModerator(UserGroupInfo groupInfo, Long userId) {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		if (!this.isModerator(groupInfo, userId)) {

			// Load Group Entitiy
			UserGroup group = getUserGroupDao()
					.userGroupInfoToEntity(groupInfo);
			Validate.notNull(group, "Cannot transform groupInfo to entity.");

			// Load User Entity
			User user = getUserDao().load(userId);
			Validate.notNull(user, "User must not be null.");

			// Add user to SecurityGroup - Moderators
			Group secGroup = group.getModeratorsGroup();
			getSecurityService().addAuthorityToGroup(user, secGroup);

			// Remove user from SecurityGroup - Members
			Group secGroupMem = group.getMembersGroup();
			getSecurityService().removeAuthorityFromGroup(user, secGroupMem);

		}
	}

	/**
	 * @see org.openuss.groups.GroupService#removeModerator(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleRemoveModerator(UserGroupInfo groupInfo, Long userId) {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		if (this.isModerator(groupInfo, userId)) {

			// Load Group Entity
			UserGroup group = getUserGroupDao()
					.userGroupInfoToEntity(groupInfo);
			Validate.notNull(group, "Cannot transform groupInfo to entity.");

			// Load User Entity
			User user = getUserDao().load(userId);
			Validate.notNull(user, "User must not be null.");

			// Add user to SecurityGroup - Members
			Group secGroupMem = group.getMembersGroup();
			getSecurityService().addAuthorityToGroup(user, secGroupMem);

			// Remove user from Security Group - Moderators
			Group secGroupMod = group.getModeratorsGroup();
			getSecurityService().removeAuthorityFromGroup(user, secGroupMod);

		}
	}

	/**
	 * @see org.openuss.groups.GroupService#addMember(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleAddMember(UserGroupInfo groupInfo, Long userId) {
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
		Group secGroup = group.getMembersGroup();
		getSecurityService().addAuthorityToGroup(user, secGroup);

	}

	/**
	 * @see org.openuss.groups.GroupService#addUserByPassword(org.openuss.groups.UserGroupInfo,
	 *      java.lang.String, java.lang.Long)
	 */
	protected void handleAddUserByPassword(UserGroupInfo groupInfo,
			String password, Long userId) {
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
	protected void handleRemoveMember(UserGroupInfo groupInfo, Long userId) {

		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Test If User is Moderator
		if (isModerator(groupInfo, userId)) {
			this.removeModerator(groupInfo, userId);
		}

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User cannot be null.");

		// Remove User from SecurityGroup - Members
		Group secGroupMem = group.getMembersGroup();
		getSecurityService().removeAuthorityFromGroup(user, secGroupMem);

		// Remove User as Member from Membership
		Membership membership = group.getMembership();
		getMembershipService().removeMember(membership, user);
	}

	/**
	 * @see org.openuss.groups.GroupService#addAspirant(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleAddAspirant(UserGroupInfo groupInfo, Long userId) {
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
	protected void handleAcceptAspirant(UserGroupInfo groupInfo, Long userId) {
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
		Group secGroup = group.getMembersGroup();
		getSecurityService().addAuthorityToGroup(user, secGroup);

	}

	/**
	 * @see org.openuss.groups.GroupService#rejectAspirant(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected void handleRejectAspirant(UserGroupInfo groupInfo, Long userId) {
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
	 * @see org.openuss.groups.GroupService#getAllMembers(org.openuss.groups.UserGroupInfo)
	 */
	protected List<UserGroupMemberInfo> handleGetAllMembers(
			UserGroupInfo groupInfo) {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		List<UserGroupMemberInfo> moderators = this.getModerators(groupInfo);
		List<UserGroupMemberInfo> members = this.getMembers(groupInfo);
		moderators.addAll(members);
		return moderators;
	}

	/**
	 * @see org.openuss.groups.GroupService#getMembers(org.openuss.groups.UserGroupInfo)
	 */
	protected List<UserGroupMemberInfo> handleGetMembers(UserGroupInfo groupInfo) {
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
			UserGroupInfo groupInfo) {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load Membership
		Membership membership = group.getMembership();

		List<User> users = membership.getMembers();
		for(User user:users){
			logger.debug("List<User>: " + user.getId() + " - " + user.getUsername());
		}
		return userListToUserGroupMemberInfoList(users, group, group
				.getModeratorsGroup());
	}

	/**
	 * @see org.openuss.groups.GroupService#getAspirants(org.openuss.groups.UserGroupInfo)
	 */
	protected List<UserGroupMemberInfo> handleGetAspirants(
			UserGroupInfo groupInfo) {
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
	protected List<UserGroupInfo> handleGetAllGroups() {
		Collection<UserGroup> userGroups = getUserGroupDao().loadAll();
		List<UserGroupInfo> group = new ArrayList<UserGroupInfo>();
		for (UserGroup userGroup : userGroups) {
			group.add(getUserGroupDao().toUserGroupInfo(userGroup));
		}
		return group;
	}

	/**
	 * @see org.openuss.groups.GroupService#getGroupsByUser(java.lang.Long)
	 */
	protected List<UserGroupInfo> handleGetGroupsByUser(Long userId) {
		Validate.notNull(userId, "UserId cannot be null.");

		// Load User Entity
		User user = getUserDao().load(userId);
		Validate.notNull(user, "User cannot be null.");

		// Load Membership
		Collection<Membership> memberships = getMembershipDao().loadAll();
		Collection<UserGroup> groups = getUserGroupDao().loadAll();
		List<UserGroupInfo> groupList = new ArrayList<UserGroupInfo>();
		for (Membership membership : memberships) {
			for (UserGroup group : groups) {
				if (group.getMembership().getId().compareTo(membership.getId()) == 0) {
					List<User> members = membership.getMembers();
					for (User member : members) {
						if (member.getId().compareTo(userId) == 0) {
							groupList.add(getUserGroupDao().toUserGroupInfo(
									getUserGroupDao().findByMembership(
											membership)));
						}
					}
				}
			}
		}
		return groupList;
	}

	/**
	 * @see org.openuss.groups.GroupService#getGroupByNameOrShortcut(java.long.String)
	 */
	protected List<UserGroupInfo> handleGetGroupByNameOrShortcut(String name) {
		Validate.notNull(name, "Name cannot be null.");
		Collection<UserGroup> userGroups = getUserGroupDao().loadAll();
		List<UserGroupInfo> group = new ArrayList<UserGroupInfo>();
		for (UserGroup userGroup : userGroups) {
			if ((userGroup.getShortcut().contentEquals(name))
					|| (userGroup.getName().contains(name))) {
				group.add(getUserGroupDao().toUserGroupInfo(userGroup));
			}
		}
		return group;
	}

	/**
	 * @see org.openuss.groups.GroupService#getGroupInfo(java.lang.Long)
	 */
	protected UserGroupInfo handleGetGroupInfo(Long groupId) {
		Validate.notNull(groupId, "GroupId cannot be null.");
		return getUserGroupDao().toUserGroupInfo(
				getUserGroupDao().load(groupId));
	}

	/**
	 * @see org.openuss.groups.GroupService#isModerator(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected boolean handleIsModerator(UserGroupInfo groupInfo, Long userId) {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		// Load Moderators
		List<Authority> authorities = group.getModeratorsGroup().getMembers();

		for (Authority authority : authorities) {
			if (authority.getId().compareTo(userId) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.openuss.groups.GroupService#isMember(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected boolean handleIsMember(UserGroupInfo groupInfo, Long userId) {
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
	 * @see org.openuss.groups.GroupService#isAspirant(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected boolean handleIsAspirant(UserGroupInfo groupInfo, Long userId) {
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
		List<User> aspirants = membership.getAspirants();
		for (User aspirant : aspirants) {
			if (aspirant == user) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.openuss.groups.GroupService#isCreator(org.openuss.groups.UserGroupInfo,
	 *      java.lang.Long)
	 */
	protected boolean handleIsCreator(UserGroupInfo groupInfo, Long userId) {
		Validate.notNull(groupInfo, "Parameter group must not be null.");
		Validate.notNull(groupInfo.getId(),
				"Parameter group must contain a valid group id.");
		Validate.notNull(userId, "UserId cannot be null.");

		// Load Group Entitiy
		UserGroup group = getUserGroupDao().userGroupInfoToEntity(groupInfo);
		Validate.notNull(group, "Cannot transform groupInfo to entity.");

		return (group.getCreator().getId().compareTo(userId) == 0);

	}

	/**
	 * @see org.openuss.groups.GroupService#isUniqueShortcut(java.lang.String)
	 */
	protected boolean handleIsUniqueShortcut(String shortcut) {
		Validate.notNull(shortcut, "Parameter shortcut must not be null.");
		UserGroup group = getUserGroupDao().findByShortcut(shortcut);
		return (group == null);
	}

	/*------------------- private methods -------------------- */

	private void updateAccessTypePermission(UserGroup group) {
		getSecurityService().setPermissions(Roles.ANONYMOUS, group,
				LectureAclEntry.NOTHING);
		getSecurityService().setPermissions(Roles.USER, group,
				LectureAclEntry.NOTHING);
	}

	private List<UserGroupMemberInfo> userListToUserGroupMemberInfoList(
			List<User> userList, UserGroup group, Group secGroups) {
		Validate.notNull(userList, "Parameter users must not be null.");
		Validate.notNull(group, "Parameter group must not be null.");

		List<User> users2 = new ArrayList<User>();	
		if ((userList.size() > 0) && (secGroups != null)) {
			List<Authority> authorities = secGroups.getMembers();
			for (User user : userList) {
				for (Authority authority : authorities) {
					if (user.getId().compareTo(authority.getId()) == 0) {
						users2.add(user);
					}
				}

			}
			userList = users2;
		}
		List<UserGroupMemberInfo> groupMembers = new ArrayList<UserGroupMemberInfo>();
		for (User user : userList) {
			UserGroupMemberInfo groupMember = new UserGroupMemberInfo();
			groupMember.setUserId(user.getId());
			groupMember.setUsername(user.getUsername());
			groupMember.setFirstName(user.getFirstName());
			groupMember.setLastName(user.getLastName());
			groupMember.setEMail(user.getEmail());
			groupMember.setCreator(this.isCreator(getUserGroupDao()
					.toUserGroupInfo(group), user.getId()));
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