// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.registration.RegistrationException;
import org.openuss.security.Authority;
import org.openuss.security.Group;
import org.openuss.security.GroupItem;
import org.openuss.security.User;

/**
 * @see org.openuss.lecture.OrganisationService
 * @author Ron Haus, Florian Dondorf
 */
public class OrganisationServiceImpl extends org.openuss.lecture.OrganisationServiceBase {

	/**
	 * @see org.openuss.lecture.OrganisationService#addMember(java.lang.Long, java.lang.Long)
	 */
	protected void handleAddMember(java.lang.Long organisationId, java.lang.Long userId) throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate.notNull(organisation,
				"MembershipService.handleAddMember - no Organisation found corresponding to the ID " + organisationId);

		User user = this.getUserDao().load(userId);
		Validate.notNull(organisation, "MembershipService.handleAddMember - no User found corresponding to the ID "
				+ userId);

		this.getMembershipService().addMember(organisation.getMembership(), user, null);
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#addAspirant(java.lang.Long, java.lang.Long)
	 */
	protected void handleAddAspirant(java.lang.Long organisationId, java.lang.Long userId) throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate
				.notNull(organisation,
						"MembershipService.handleAddAspirant - no Organisation found corresponding to the ID "
								+ organisationId);

		User user = this.getUserDao().load(userId);
		Validate.notNull(organisation, "MembershipService.handleAddAspirant - no User found corresponding to the ID "
				+ userId);

		this.getMembershipService().addAspirant(organisation.getMembership(), user, null);
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#findAllMembers(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindAllMembers(java.lang.Long organisationId) throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate.notNull(organisation,
				"MembershipService.handleFindAllMembers - no Organisation found corresponding to the ID "
						+ organisationId);

		List<User> members = organisation.getMembership().getMembers();

		List memberInfos = new ArrayList();
		for (User user : members) {
			memberInfos.add(this.getUserDao().toUserInfo(user));
		}

		return memberInfos;
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#findAllAspirants(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindAllAspirants(java.lang.Long organisationId) throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate.notNull(organisation,
				"MembershipService.handleFindAllAspirants - no Organisation found corresponding to the ID "
						+ organisationId);

		List<User> aspirants = organisation.getMembership().getAspirants();

		List aspirantInfos = new ArrayList();
		for (User user : aspirants) {
			aspirantInfos.add(this.getUserDao().toUserInfo(user));
		}

		return aspirantInfos;
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#acceptAspirant(java.lang.Long, java.lang.Long)
	 */
	protected void handleAcceptAspirant(java.lang.Long organisationId, java.lang.Long userId)
			throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate.notNull(organisation,
				"MembershipService.handleAcceptAspirant - no Organisation found corresponding to the ID "
						+ organisationId);

		User user = this.getUserDao().load(userId);
		Validate.notNull(organisation,
				"MembershipService.handleAcceptAspirant - no User found corresponding to the ID " + userId);

		this.getMembershipService().acceptAspirant(organisation.getMembership(), user, null);
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#rejectAspirant(java.lang.Long, java.lang.Long)
	 */
	protected void handleRejectAspirant(java.lang.Long organisationId, java.lang.Long userId)
			throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate.notNull(organisation,
				"MembershipService.handleRejectAspirant - no Organisation found corresponding to the ID "
						+ organisationId);

		User user = this.getUserDao().load(userId);
		Validate.notNull(organisation,
				"MembershipService.handleRejectAspirant - no User found corresponding to the ID " + userId);

		this.getMembershipService().rejectAspirant(organisation.getMembership(), user, null);
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#createGroup(java.lang.Long, org.openuss.security.GroupItem)
	 */
	protected Group handleCreateGroup(java.lang.Long organisationId, org.openuss.security.GroupItem groupItem)
			throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate
				.notNull(organisation,
						"MembershipService.handleCreateGroup - no Organisation found corresponding to the ID "
								+ organisationId);

		Group group = this.getGroupDao().groupItemToEntity(groupItem);
		group = this.getMembershipService().createGroup(organisation.getMembership(), group);
		Validate.notNull(group.getId(), "MembershipService.handleCreateGroup - Group couldn't be created");

		return group;
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#removeGroup(java.lang.Long)
	 */
	protected void handleRemoveGroup(java.lang.Long organisationId, java.lang.Long groupId) throws java.lang.Exception {

		Group group = this.getGroupDao().load(groupId);
		Validate.notNull(group, "MembershipService.handleRemoveGroup - no Group found corresponding to the ID "
				+ groupId);

		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate
				.notNull(organisation,
						"MembershipService.handleRemoveGroup - no Organisation found corresponding to the ID "
								+ organisationId);

		// Remove all Authorities from Group
		List<Authority> authorities = group.getMembers();
		for (Authority authority : authorities) {
			authority.removeGroup(group);
		}

		// Remove the Group from its Organisation
		boolean removed = organisation.getMembership().getGroups().remove(group);
		Validate.isTrue(removed, "MembershipService.handleRemoveGroup - Group " + groupId + " couldn't be removed");

		// Delete Group
		this.getSecurityService().removeGroup(group);
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#addUserToGroup(java.lang.Long, java.lang.Long)
	 */
	protected void handleAddUserToGroup(java.lang.Long userId, java.lang.Long groupId) throws java.lang.Exception {
		Group group = this.getGroupDao().load(groupId);
		Validate.notNull(group, "MembershipService.handleAddUserToGroup - no Group found corresponding to the ID "
				+ groupId);

		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "MembershipService.handleAddUserToGroup - no User found corresponding to the ID "
				+ groupId);

		this.getSecurityService().addAuthorityToGroup(user, group);
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#removeUserFromGroup(java.lang.Long, java.lang.Long)
	 */
	protected void handleRemoveUserFromGroup(java.lang.Long userId, java.lang.Long groupId) throws java.lang.Exception {
		Group group = this.getGroupDao().load(groupId);
		Validate.notNull(group, "MembershipService.handleRemoveUserFromGroup - no Group found corresponding to the ID "
				+ groupId);

		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "MembershipService.handleRemoveUserFromGroup - no User found corresponding to the ID "
				+ groupId);

		this.getSecurityService().removeAuthorityFromGroup(user, group);
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#sendActivationCode(org.openuss.lecture.Organisation)
	 */
	protected void handleSendActivationCode(Organisation organisation) throws RegistrationException {
		/*
		 * String activationCode = getRegistrationService().generateInstituteActivationCode(institute); Map<String,
		 * String> parameters = new HashMap<String, String>(); parameters.put("institutename", institute.getName() +
		 * "(" + institute.getShortcut() + ")"); parameters.put("institutelink",
		 * getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() +
		 * "actions/public/lecture/instituteactivation.faces?code=" + activationCode);
		 * getMessageService().sendMessage(institute.getShortcut(), "institute.activation.subject",
		 * "instituteactivation", parameters, institute.getEmail(), institute.getLocale());
		 */
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#findGroupsByOrganisation(Long)
	 */
	protected List handleFindGroupsByOrganisation(Long organisationId) throws Exception {

		Validate.notNull(organisationId,
				"OrganisationService.handleFindGroupsByOrganisation - organisationId cannot be null.");

		// Load Organisation
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate.notNull(organisation,
				"OrganisationService.handleFindGroupsByOrganisation - no Organisation found corresponding to the ID "
						+ organisationId);

		// Find Groups
		List<GroupItem> groupItems = new ArrayList<GroupItem>(organisation.getMembership().getGroups().size());
		for (Group group : organisation.getMembership().getGroups()) {
			groupItems.add(this.getGroupDao().toGroupItem(group));
		}
		return groupItems;
	}

	@Override
	protected void handleSetOrganisationEnabled(Long organisationId, Boolean enabled) throws Exception {
		Validate.notNull(organisationId,
				"OrganisationService.handleSetOrganisationEnabled - organisationId cannot be null.");
		Validate.notNull(enabled, "OrganisationService.handleSetOrganisationEnabled - enabled cannot be null.");

		// Load Organisation
		Organisation organisation = this.getOrganisationDao().load(organisationId);

		// Set Organisation status
		organisation.setEnabled(enabled);
		this.getOrganisationDao().update(organisation);
	}

}