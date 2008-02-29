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
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.GroupItem;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

/**
 * @see org.openuss.lecture.OrganisationService
 * @author Ron Haus, Florian Dondorf
 */
public class OrganisationServiceImpl extends OrganisationServiceBase {

	/**
	 * @see org.openuss.lecture.OrganisationService#addMember(java.lang.Long, java.lang.Long)
	 */
	protected void handleAddMember(java.lang.Long organisationId, java.lang.Long userId) throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate.notNull(organisation, "No Organisation found corresponding to the ID " + organisationId);

		User user = this.getUserDao().load(userId);
		Validate.notNull(organisation, "No User found corresponding to the ID "	+ userId);

		this.getMembershipService().addMember(organisation.getMembership(), user);
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#removeMember(java.lang.Long, java.lang.Long)
	 */
	protected void handleRemoveMember(Long organisationId, Long userId) throws Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate.notNull(organisation, "No Organisation found corresponding to the ID "	+ organisationId);

		Validate.isTrue(organisation.getMembership().getMembers().size() > 1, "You cannot remove the last Member!");

		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "No User found corresponding to the ID "	+ userId);

		this.getMembershipService().removeMember(organisation.getMembership(), user);

	}

	/**
	 * @see org.openuss.lecture.OrganisationService#addAspirant(java.lang.Long, java.lang.Long)
	 */
	protected void handleAddAspirant(Long organisationId, Long userId) throws Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate
				.notNull(organisation,
						"MembershipService.handleAddAspirant - no Organisation found corresponding to the ID "
								+ organisationId);

		User user = this.getUserDao().load(userId);
		Validate.notNull(organisation, "MembershipService.handleAddAspirant - no User found corresponding to the ID "
				+ userId);

		this.getMembershipService().addAspirant(organisation.getMembership(), user);
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

		List<UserInfo> memberInfos = new ArrayList();
		for (User user : members) {
			memberInfos.add(getSecurityService().getUserByEmail(user.getEmail()));
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

		List<UserInfo> aspirantInfos = new ArrayList();
		for (User user : aspirants) {
			aspirantInfos.add(getSecurityService().getUserByEmail(user.getEmail()));
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

		this.getMembershipService().acceptAspirant(organisation.getMembership(), user);
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

		this.getMembershipService().rejectAspirant(organisation.getMembership(), user);
	}

	/**
	 * @see org.openuss.lecture.OrganisationService#createGroup(java.lang.Long, org.openuss.security.GroupItem)
	 */
	protected Long handleCreateGroup(java.lang.Long organisationId, org.openuss.security.GroupItem groupItem)
			throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		Validate
				.notNull(organisation,
						"MembershipService.handleCreateGroup - no Organisation found corresponding to the ID "
								+ organisationId);

		Group group = this.getGroupDao().groupItemToEntity(groupItem);
		group = this.getMembershipService().createGroup(organisation.getMembership(), group);
		Validate.notNull(group.getId(), "MembershipService.handleCreateGroup - Group couldn't be created");

		return group.getId();
	}

	@Override
	protected GroupItem handleFindGroup(Long groupId) throws Exception {
		Validate.notNull(groupId, "OrganisationService.handleFindGroup - groupId cannot be null.");

		// Find Group
		return (GroupItem) this.getGroupDao().load(GroupDao.TRANSFORM_GROUPITEM, groupId);
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

		// Delete Group
		this.getMembershipService().removeGroup(organisation.getMembership(), group);
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
	protected void handleSetOrganisationEnabled(Long organisationId, boolean enabled) throws Exception {
		Validate.notNull(organisationId,
				"OrganisationService.handleSetOrganisationEnabled - organisationId cannot be null.");
		Validate.notNull(enabled, "OrganisationService.handleSetOrganisationEnabled - enabled cannot be null.");

		// Load Organisation
		Organisation organisation = this.getOrganisationDao().load(organisationId);

		// Set Organisation status
		organisation.setEnabled(enabled);
		this.getOrganisationDao().update(organisation);
	}

	@Override
	protected OrganisationHierarchy handleFindCourseHierarchy(Long courseId) throws Exception {
		Validate.notNull(courseId, "OrganisationService.handleFindCourseHierarchy - courseId cannot be null.");

		Course course = this.getCourseDao().load(courseId);
		Validate.notNull(course,
				"OrganisationService.handleFindCourseHierarchy - no Institute found corresponding to the ID "
						+ courseId);

		OrganisationHierarchy hierarchy = new OrganisationHierarchy();
		hierarchy.setInstituteInfo(this.getInstituteDao().toInstituteInfo(course.getCourseType().getInstitute()));
		hierarchy.setDepartmentInfo(this.getDepartmentDao().toDepartmentInfo(
				course.getCourseType().getInstitute().getDepartment()));
		hierarchy.setUniversityInfo(this.getUniversityDao().toUniversityInfo(
				course.getCourseType().getInstitute().getDepartment().getUniversity()));
		return hierarchy;
	}

	@Override
	protected OrganisationHierarchy handleFindDepartmentHierarchy(Long departmentId) throws Exception {
		Validate.notNull(departmentId,
				"OrganisationService.handleFindDepartmentHierarchy - departmentId cannot be null.");

		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department,
				"OrganisationService.handleFindDepartmentHierarchy - no Department found corresponding to the ID "
						+ departmentId);

		OrganisationHierarchy hierarchy = new OrganisationHierarchy();
		// hierarchy.setDepartmentInfo(this.getDepartmentDao().toDepartmentInfo(department));
		hierarchy.setUniversityInfo(this.getUniversityDao().toUniversityInfo(department.getUniversity()));
		return hierarchy;
	}

	@Override
	protected OrganisationHierarchy handleFindInstituteHierarchy(Long instituteId) throws Exception {
		Validate.notNull(instituteId, "OrganisationService.handleFindInstituteHierarchy - instituteId cannot be null.");

		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"OrganisationService.handleFindInstituteHierarchy - no Institute found corresponding to the ID "
						+ instituteId);

		OrganisationHierarchy hierarchy = new OrganisationHierarchy();
		// hierarchy.setInstituteInfo(this.getInstituteDao().toInstituteInfo(institute));
		hierarchy.setDepartmentInfo(this.getDepartmentDao().toDepartmentInfo(institute.getDepartment()));
		hierarchy
				.setUniversityInfo(this.getUniversityDao().toUniversityInfo(institute.getDepartment().getUniversity()));
		return hierarchy;
	}
	
}