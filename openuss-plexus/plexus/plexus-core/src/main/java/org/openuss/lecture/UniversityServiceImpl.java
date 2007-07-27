// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.desktop.Desktop;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;
import org.openuss.security.User;

/**
 * @see org.openuss.lecture.UniversityService
 * @author Ron Haus, Florian Dondorf
 */
public class UniversityServiceImpl extends org.openuss.lecture.UniversityServiceBase {

	/**
	 * @see org.openuss.lecture.UniversityService#create(org.openuss.lecture.UniversityInfo, java.lang.Long)
	 */
	protected java.lang.Long handleCreate(org.openuss.lecture.UniversityInfo university, java.lang.Long ownerId) {

		Validate.notNull(university, "UniversityService.handleCreate - the University cannot be null");
		Validate.notNull(ownerId, "UniversityService.handleCreate - the Owner must have a valid ID");
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
		// @todo implement protected void handleUpdate(org.openuss.lecture.PeriodInfo period)
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.lecture.UniversityService.handleUpdate(org.openuss.lecture.PeriodInfo period) Not implemented!");
	}

	/**
	 * @see org.openuss.lecture.UniversityService#removeUniversity(java.lang.Long)
	 */
	protected void handleRemoveUniversity(java.lang.Long universityId) throws java.lang.Exception {
		
		Validate.notNull(universityId, "UniversityService.handleRemoveUniversity - the UniversityID cannot be null");
		
		this.getUniversityDao().remove(universityId);

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
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindUniversitiesByUser(java.lang.Long userId) throws java.lang.Exception {
		
		Validate.notNull(userId, "UniversityService.handleFindUniversitiesByUser - the User must have a valid ID");
		
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "UniversityService.handleFindUniversitiesByUser - no User found corresponding to the ID "+userId);
		
		List universities = new ArrayList();
		
		//Check whether a User is Member of a University
		List allUniversities = this.findAllUniversities();
		Iterator iter1 = allUniversities.iterator();
		University university = null;
		while (iter1.hasNext()) {
			university = (University) iter1.next();
			if ((university.getMembership().getMembers().contains(user)) && (!universities.contains(university))) {
				universities.add(university);
			}
		}
		
		//Check whether a User has a Desktop Link to the University
		Desktop desktop = this.getDesktopService().getDesktopByUser(user);
		List<University> desktopUnis = desktop.getUniversities();
		for(University desktopUni: desktopUnis) {
			if (!universities.contains(desktopUni)) {
				universities.add(desktopUni);
			}
		}

		//Transformation
		List universityInfos = new ArrayList(universities.size());
		for (int i = 0; i < universities.size(); i++) {
			universityInfos.add(this.getUniversityDao().toUniversityInfo((University) universities.get(i)));
		}
		
		return universityInfos;
	}

}