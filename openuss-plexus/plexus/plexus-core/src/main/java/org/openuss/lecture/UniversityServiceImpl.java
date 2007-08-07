// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;

/**
 * @see org.openuss.lecture.UniversityService
 * @author Ron Haus, Florian Dondorf
 */
public class UniversityServiceImpl extends org.openuss.lecture.UniversityServiceBase {

	/**
	 * @see org.openuss.lecture.UniversityService#create(org.openuss.lecture.UniversityInfo, java.lang.Long)
	 */
	protected java.lang.Long handleCreate(org.openuss.lecture.UniversityInfo university, java.lang.Long userId) {

		Validate.notNull(university, "UniversityService.handleCreate - the University cannot be null");
		Validate.notNull(userId, "UniversityService.handleCreate - the User must have a valid ID");
		Validate.isTrue(university.getId() == null, "UniversityService.handleCreate - the University shouldn't have an ID yet");

		// Transform ValueObject into Entity
		University universityEntity = this.getUniversityDao().universityInfoToEntity(university);
		
		// Create a default Membership for the University
		Membership membership = Membership.Factory.newInstance();
		universityEntity.setMembership(membership);
		
		//Create the University
		this.getUniversityDao().create(universityEntity);
		Validate.notNull(universityEntity.getId(), "UniversityService.handleCreate - Couldn't create University");
		
		//Create default Groups for the University
		GroupItem groupItem = new GroupItem();
		groupItem.setName("UNIVERSITY_"+universityEntity.getId()+"_ADMINS");
		groupItem.setLabel("autogroup_administrator_label");
		groupItem.setGroupType(GroupType.ADMINISTRATOR);
		Long groupId = this.getOrganisationService().createGroup(universityEntity.getId(), groupItem);
		
		//Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(universityEntity.getId(), userId);
		this.getOrganisationService().addUserToGroup(userId, groupId);
		
		return universityEntity.getId();
	}

	/**
	 * @see org.openuss.lecture.UniversityService#create(org.openuss.lecture.PeriodInfo)
	 */
	protected java.lang.Long handleCreate(org.openuss.lecture.PeriodInfo period) throws java.lang.Exception {
		
		Validate.notNull(period, "UniversityService.handleCreate - the period cannot be null");
		Period periodEntity = this.getPeriodDao().create(this.getPeriodDao().periodInfoToEntity(period));
		periodEntity.getUniversity().getPeriods().add(periodEntity);
		
		return periodEntity.getId();
	}

	/**
	 * @see org.openuss.lecture.UniversityService#update(org.openuss.lecture.UniversityInfo)
	 */
	protected void handleUpdate(org.openuss.lecture.UniversityInfo university) throws java.lang.Exception {
		
		Validate.notNull(university, "UniversityService.handleUpdate - the University cannot be null");
		Validate.notNull(university.getId(), "UniversityService.handleUpdate - the University must have a valid ID");
		
		// Transform ValueObject into Entity
		University universityEntity = this.getUniversityDao().universityInfoToEntity(university);
		
		// Update Entity
		this.getUniversityDao().update(universityEntity);
		
	}

	/**
	 * @see org.openuss.lecture.UniversityService#update(org.openuss.lecture.PeriodInfo)
	 */
	protected void handleUpdate(org.openuss.lecture.PeriodInfo period) throws java.lang.Exception {
		
		Validate.notNull(period, "UniversityService.handleUpdate - the Period cannot be null");
		
		//Transform ValueObject into Entity
		Period periodEntity = this.getPeriodDao().periodInfoToEntity(period);
		
		//Update Entity
		this.getPeriodDao().update(periodEntity);
	}

	/**
	 * @see org.openuss.lecture.UniversityService#removeUniversity(java.lang.Long)
	 */
	protected void handleRemoveUniversity(java.lang.Long universityId) throws java.lang.Exception {
		
		Validate.notNull(universityId, "UniversityService.handleRemoveUniversity - the UniversityID cannot be null");
		
		//TODO All Bookmarks need to be removed before
		
		this.getUniversityDao().remove(universityId);

	}

	/**
	 * @see org.openuss.lecture.UniversityService#removePeriod(java.lang.Long)
	 */
	protected void handleRemovePeriod(java.lang.Long periodId) throws java.lang.Exception {

		Validate.notNull(periodId, "UniversityService.handleRemovePeriod - the PeriodID cannot be null");
		
		this.getPeriodDao().remove(periodId);
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findUniversity(java.lang.Long)
	 */
	protected org.openuss.lecture.UniversityInfo handleFindUniversity(java.lang.Long universityId)
			throws java.lang.Exception {
		
		Validate.notNull(universityId, "UniversityService.handleFindUniversity - the UniversityID cannot be null");
		
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "UniversityService.handleFindUniversity - no University found corresponding to the ID "+universityId);
		
		return this.getUniversityDao().toUniversityInfo(university);
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findAllUniversities()
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindAllUniversities() throws java.lang.Exception {
		
		Collection<University> universities = this.getUniversityDao().loadAll();
		
		List universityInfos = new ArrayList();
		for(University university:universities) {
			universityInfos.add(this.getUniversityDao().toUniversityInfo(university));
		}
		
		return universityInfos;
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findPeriod(java.lang.Long)
	 */
	protected org.openuss.lecture.PeriodInfo handleFindPeriod(java.lang.Long periodId) throws java.lang.Exception {

		Validate.notNull(periodId, "UniversityService.handleFindPeriod - the PeriodID cannot be null");
		
		Period periodEntity = this.getPeriodDao().load(periodId);
		Validate.notNull(periodEntity, "UniversityService.handleFindPeriod - no Period found corresponding to the ID "+periodId);
		
		return this.getPeriodDao().toPeriodInfo(periodEntity);
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findPeriodsByUniversity(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindPeriodsByUniversity(java.lang.Long universityId) throws java.lang.Exception {

		Validate.notNull(universityId, "UniversityService.handleFindPeriodsByUniversity - the universityID cannot be null");
		
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "UniversityService.handleFindPeriodsByUniversity - no University found corresponding to the ID "+universityId);

		List periodInfos = new ArrayList();
		for (Period period : university.getPeriods()) {
			periodInfos.add(this.getPeriodDao().toPeriodInfo(period));
		}

		return periodInfos;
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findActivePeriodByUniversity(java.lang.Long)
	 */
	protected org.openuss.lecture.PeriodInfo handleFindActivePeriodByUniversity(java.lang.Long universityId)
			throws java.lang.Exception {
		
		Validate.notNull(universityId, "UniversityService.handleFindActivePeriodByUniversity - the universityID cannot be null");
		
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "UniversityService.handleFindActivePeriodsByUniversity - no University found corresponding to the ID "+universityId);
		
		Period activePeriod = university.getActivePeriod();
		if (activePeriod == null) {
			return null;
		} else {
			return this.getPeriodDao().toPeriodInfo(activePeriod);
		}
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findAllUniversitiesByEnabled(java.lang.Boolean)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindUniversitiesByEnabled(Boolean enabled) throws Exception {
		
		return this.getUniversityDao().findByEnabled(UniversityDao.TRANSFORM_UNIVERSITYINFO, enabled);
	}
	
	
}