// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

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

		if (university == null) {
			throw new IllegalArgumentException("UniversityService.handleCreate - the University cannot be null");
		}
		
		if (ownerId == null) {
			throw new IllegalArgumentException("UniversityService.handleCreate - the Owner must have a valid ID");
		}
		
		User owner = this.getUserDao().load(ownerId);
		if (owner == null) {
			throw new IllegalArgumentException("UniversityService.handleCreate - no User found corresponding to the id " + ownerId);
		}
		
		if (university.getId() != null) {
			if (university.getId() != 0L) {
				throw new IllegalArgumentException("UniversityService.handleCreate - the University shouldn't have an ID yet");
			}
		}
		
		University universityEntity = University.Factory.newInstance();
		universityEntity.setName(university.getName());
		universityEntity.setShortcut(university.getShortcut());
		universityEntity.setDescription(university.getDescription());
		universityEntity.setUniversityType(UniversityType.fromInteger(university.getUniversityType()));
		
		universityEntity.setOwner(owner);
		
		getUniversityDao().create(universityEntity);
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
		
		if (university == null) {
			throw new IllegalArgumentException("UniversityService.handleCreate - the University cannot be null");
		}
		
		if ((university.getId() == null) || (university.getId() == 0L)) {
			throw new IllegalArgumentException("UniversityService.handleCreate - the University must have an valid ID");
		}
		
		University universityEntity = getUniversityDao().universityInfoToEntity(university);
		
		getUniversityDao().update(universityEntity);
		
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
		
		University university = getUniversityDao().load(universityId);
		if (university == null) {
			throw new IllegalArgumentException("UniversityService.handleRemoveUniversity - no University found corresponding to the ID " + universityId);
		}
		
		if (university.getDepartments().size() != 0) {
			throw new IllegalStateException("UniversityService.handleRemoveUniversity - University cannot be removed as long as it still owns departments");
		}
		
		getUniversityDao().remove(universityId);
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