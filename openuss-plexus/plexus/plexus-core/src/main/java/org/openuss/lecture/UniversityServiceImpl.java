// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.apache.commons.lang.Validate;
import org.openuss.security.GroupItem;
import org.openuss.security.User;

/**
 * @see org.openuss.lecture.UniversityService
 * @author Ron Haus
 */
public class UniversityServiceImpl extends org.openuss.lecture.UniversityServiceBase {

	/**
	 * @see org.openuss.lecture.UniversityService#create(org.openuss.lecture.UniversityInfo, java.lang.Long)
	 */
	protected java.lang.Long handleCreate(org.openuss.lecture.UniversityInfo university, java.lang.Long ownerId) {

		Validate.notNull(university, "UniversityService.handleCreate - the University cannot be null");
		Validate.notNull(ownerId, "UniversityService.handleCreate - the Owner must have a valid ID");
		
		Validate.isTrue(university.getId() == null, "UniversityService.handleCreate - the University shouldn't have an ID yet");

		//Create University
		University universityEntity = this.getUniversityDao().universityInfoToEntity(university);
		this.getUniversityDao().create(universityEntity);
		Validate.notNull(universityEntity.getId(), "UniversityService.handleCreate - Couldn't create University");
		
		//Create Groups for University
		GroupItem groupItem = new GroupItem();
		groupItem.setName("Administrators");
		groupItem.setLabel("Admins");
		Long groupId = this.getOrganisationService().createGroup(universityEntity.getId(), groupItem);
		
		//Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(universityEntity.getId(), ownerId);
		this.getOrganisationService().addUserToGroup(ownerId, groupId);
		
		return universityEntity.getId();
	}

	/**
	 * @see org.openuss.lecture.UniversityService#create(org.openuss.lecture.PeriodInfo)
	 */
	protected java.lang.Long handleCreate(org.openuss.lecture.PeriodInfo period) throws java.lang.Exception {
		// @todo implement protected java.lang.Long handleCreate(org.openuss.lecture.PeriodInfo period)
		return null;
	}

	/**
	 * @see org.openuss.lecture.UniversityService#update(org.openuss.lecture.UniversityInfo)
	 */
	protected void handleUpdate(org.openuss.lecture.UniversityInfo university) throws java.lang.Exception {
		
		throw new java.lang.UnsupportedOperationException(
		"org.openuss.lecture.UniversityService.handleUpdate(org.openuss.lecture.PeriodInfo period) Not implemented!");
		
	}

	/**
	 * @see org.openuss.lecture.UniversityService#update(org.openuss.lecture.PeriodInfo)
	 */
	protected void handleUpdate(org.openuss.lecture.PeriodInfo period) throws java.lang.Exception {
		// @todo implement protected void handleUpdate(org.openuss.lecture.PeriodInfo period)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.lecture.UniversityService.handleUpdate(org.openuss.lecture.PeriodInfo period) Not implemented!");
	}

	/**
	 * @see org.openuss.lecture.UniversityService#removeUniversity(java.lang.Long)
	 */
	protected void handleRemoveUniversity(java.lang.Long universityId) throws java.lang.Exception {
		
		throw new java.lang.UnsupportedOperationException(
		"org.openuss.lecture.UniversityService.handleRemoveUniversity(java.lang.Long universityId) Not implemented!");

	}

	/**
	 * @see org.openuss.lecture.UniversityService#removePeriod(java.lang.Long)
	 */
	protected void handleRemovePeriod(java.lang.Long periodId) throws java.lang.Exception {
		// @todo implement protected void handleRemovePeriod(java.lang.Long periodId)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.lecture.UniversityService.handleRemovePeriod(java.lang.Long periodId) Not implemented!");
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findUniversity(java.lang.Long)
	 */
	protected org.openuss.lecture.UniversityInfo handleFindUniversity(java.lang.Long universityId)
			throws java.lang.Exception {
		// @todo implement protected org.openuss.lecture.UniversityInfo handleFindUniversity(java.lang.Long
		// universityId)
		return null;
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findAllUniversities()
	 */
	protected java.util.List handleFindAllUniversities() throws java.lang.Exception {
		// @todo implement protected java.util.List handleFindAllUniversities()
		return null;
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findPeriod(java.lang.Long)
	 */
	protected org.openuss.lecture.PeriodInfo handleFindPeriod(java.lang.Long periodId) throws java.lang.Exception {
		// @todo implement protected org.openuss.lecture.PeriodInfo handleFindPeriod(java.lang.Long periodId)
		return null;
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findPeriodsByUniversity(java.lang.Long)
	 */
	protected java.util.List handleFindPeriodsByUniversity(java.lang.Long universityId) throws java.lang.Exception {
		// @todo implement protected java.util.List handleFindAllPeriods(java.lang.Long universityId)
		return null;
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findctivePeriodByUniversity(java.lang.Long)
	 */
	protected org.openuss.lecture.PeriodInfo handleFindActivePeriodByUniversity(java.lang.Long universityId)
			throws java.lang.Exception {
		// @todo implement protected org.openuss.lecture.PeriodInfo handleFindActivePeriod(java.lang.Long universityId)
		return null;
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findUniversitiesByUser(java.lang.Long)
	 */
	protected java.util.List handleFindUniversitiesByUser(java.lang.Long userId) throws java.lang.Exception {
		// @todo implement protected java.util.List handleFindOrganisationsByUser(java.lang.Long userId)
		return null;
	}

}