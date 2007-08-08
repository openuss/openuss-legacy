// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;

/**
 * @see org.openuss.lecture.InstituteService
 */
public class InstituteServiceImpl extends org.openuss.lecture.InstituteServiceBase {
	
	private static final Logger logger = Logger.getLogger(InstituteServiceImpl.class);

	/**
	 * @see org.openuss.lecture.InstituteService#create(org.openuss.lecture.InstituteInfo)
	 */
	protected java.lang.Long handleCreate(InstituteInfo instituteInfo, Long userId) throws java.lang.Exception {

		logger.debug("Starting method handleCreate");
		
		Validate.notNull(instituteInfo, "InstituteService.handleCreate - the Institute cannot be null");
		Validate.notNull(userId, "InstituteService.handleCreate - the User must have a valid ID");
		Validate.isTrue(instituteInfo.getId() == null,
				"InstituteService.handleCreate - the Institute shouldn't have an ID yet");

		// Transform ValueObject into Entity
		Institute institute = this.getInstituteDao().instituteInfoToEntity(instituteInfo);

		// Create a default Membership for the University
		Membership membership = Membership.Factory.newInstance();
		institute.setMembership(membership);

		// Create the University
		this.getInstituteDao().create(institute);
		Validate.notNull(institute.getId(), "InstituteService.handleCreate - Couldn't create Institute");

		// Create default Groups for Institute
		GroupItem admins = new GroupItem();
		admins.setName("INSTITUTE_" + institute.getId() + "_ADMINS");
		admins.setLabel("autogroup_administrator_label");
		admins.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsId = this.getOrganisationService().createGroup(institute.getId(), admins);

		GroupItem assistants = new GroupItem();
		assistants.setName("INSTITUTE_" + institute.getId() + "_ASSISTANTS");
		assistants.setLabel("autogroup_assistant_label");
		assistants.setGroupType(GroupType.ASSISTANT);
		this.getOrganisationService().createGroup(institute.getId(), assistants);

		GroupItem tutors = new GroupItem();
		tutors.setName("INSTITUTE_" + institute.getId() + "_TUTORS");
		tutors.setLabel("autogroup_tutor_label");
		tutors.setGroupType(GroupType.TUTOR);
		this.getOrganisationService().createGroup(institute.getId(), tutors);

		// TODO Set Security for Groups

		// Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(institute.getId(), userId);
		this.getOrganisationService().addUserToGroup(userId, adminsId);

		return institute.getId();
	}

	/**
	 * @see org.openuss.lecture.InstituteService#update(org.openuss.lecture.InstituteInfo)
	 */
	protected void handleUpdate(org.openuss.lecture.InstituteInfo instituteInfo) throws java.lang.Exception {
		
		logger.debug("Starting method handleUpdate");

		Validate.notNull(instituteInfo, "InstituteService.handleUpdate - the Institute cannot be null");
		Validate.notNull(instituteInfo.getId(), "InstituteService.handleUpdate - the Institute must have a valid ID");

		// Transform ValueObject into Entity
		Institute institute = this.getInstituteDao().instituteInfoToEntity(instituteInfo);

		// Update Entity
		this.getInstituteDao().update(institute);
	
	}

	/**
	 * @see org.openuss.lecture.InstituteService#removeInstitute(java.lang.Long)
	 */
	protected void handleRemoveInstitute(java.lang.Long instituteId) throws java.lang.Exception {
		
		logger.debug("Starting method handleRemoveInstitute");

		Validate.notNull(instituteId, "InstituteService.handleRemoveInstitute - the InstituteID cannot be null");

		// Get Institute entity
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"InstituteService.handleRemoveInstitute - no Institute found to the corresponding ID " + instituteId);

		// TODO All Bookmarks need to be removed before

		this.getInstituteDao().remove(instituteId);

		// fire events
		/*
		 * for (Course course : institute.getCourses()) { fireRemovingCourse(course); }
		 * 
		 * for (CourseType courseType : institute.getCourseTypes()) { fireRemovingCourseType(courseType); }
		 * 
		 * fireRemovingInstitute(institute);
		 */
		// TODO InstituteCodes need to be removed before
		// getRegistrationService().removeInstituteCodes(institute);
	}

	/**
	 * @see org.openuss.lecture.InstituteService#findInstitute(java.lang.Long)
	 */
	protected org.openuss.lecture.InstituteInfo handleFindInstitute(java.lang.Long instituteId)
			throws java.lang.Exception {

		Validate.notNull(instituteId, "InstituteService.handleFindInstitute - the DepartmentId cannot be null");

		Institute institute = (Institute) this.getInstituteDao().load(instituteId);
		Validate.notNull(institute,
				"InstituteService.handleFindInstitute - no Institute found corresponding to the ID " + instituteId);

		return this.getInstituteDao().toInstituteInfo(institute);
	}

	/**
	 * @see org.openuss.lecture.InstituteService#findInstitutesByDepartment(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindInstitutesByDepartment(java.lang.Long departmentId) throws java.lang.Exception {

		Validate.notNull(departmentId,
				"InstituteService.handleFindInstitutesByDepartment - the departmentId cannot be null");

		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department,
				"InstituteService.handleFindInstitutesByDepartment - no Department found corresponding to the ID "
						+ departmentId);

		List instituteInfos = new ArrayList();
		for (Institute institute : department.getInstitutes()) {
			instituteInfos.add(this.getInstituteDao().toInstituteInfo(institute));
		}

		return instituteInfos;
	}

	/**
	 * @see org.openuss.lecture.InstituteService#findInstitutesByDepartmentAndEnabled(java.lang.Long, java.lang.Boolean)
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindInstitutesByDepartmentAndEnabled(java.lang.Long departmentId,
			java.lang.Boolean enabled) throws java.lang.Exception {

		Validate.notNull(departmentId,
				"InstituteService.handleFindInstitutesByDepartmentAndEnabled - the departmentId cannot be null");

		Department department = this.getDepartmentDao().load(departmentId);
		Validate.notNull(department,
				"InstituteService.handleFindDepartmentsByUniversityAndEnabled - no University found corresponding to the ID "
						+ departmentId);

		return this.getInstituteDao().findByDepartmentAndEnabled(InstituteDao.TRANSFORM_INSTITUTEINFO, department,
				enabled);
	}

	@Override
	protected Long handleApplyAtDepartment(Long instituteId, Long departmentId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleRemoveUnconfirmedApplication(Long applicationId) throws Exception {
		// TODO Auto-generated method stub
		
	}

}