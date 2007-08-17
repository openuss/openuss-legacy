// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.security.Group;
import org.openuss.security.GroupItem;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.lecture.UniversityService
 * @author Ron Haus
 * @author Florian Dondorf
 */
public class UniversityServiceImpl extends org.openuss.lecture.UniversityServiceBase {

	private static final Logger logger = Logger.getLogger(UniversityServiceImpl.class);

	/**
	 * @see org.openuss.lecture.UniversityService#create(org.openuss.lecture.UniversityInfo, java.lang.Long)
	 */
	protected java.lang.Long handleCreateUniversity(org.openuss.lecture.UniversityInfo university, java.lang.Long userId) {

		logger.debug("Starting method handleCreate");

		Validate.notNull(university, "UniversityService.handleCreate - the University cannot be null");
		Validate.notNull(userId, "UniversityService.handleCreate - the User must have a valid ID");
		Validate.isTrue(university.getId() == null,
				"UniversityService.handleCreate - the University shouldn't have an ID yet");

		// Transform ValueObject into Entity
		University universityEntity = this.getUniversityDao().universityInfoToEntity(university);

		// Create a default Membership for the University
		Membership membership = Membership.Factory.newInstance();
		universityEntity.setMembership(membership);
		
		// Create a default Period
		Period period = Period.Factory.newInstance();
		period.setName("Standard Period");
		period.setDescription("Dies ist die Standard Period. Sie ist als Auffangbehälter für Veranstaltungen gedacht, die keiner anderen Periode zugeordnet werden können.");
		period.setStartdate(new Date(0)); //1. January 1970, 00:00:00 GMT
		Calendar cal = new GregorianCalendar();
		cal.set(2050, 11, 31);//31. December 2050, 00:00:00 GMT
		period.setEnddate(new Date(cal.getTimeInMillis()));
		universityEntity.add(period);

		// Create a default Department
		Department department = Department.Factory.newInstance();
		department.setDepartmentType(DepartmentType.NONOFFICIAL);
		department.setName("Standard Department");
		department.setShortcut("StandDepart");
		department.setDescription("Dies ist das Standard Department. Es ist als Auffangbehälter für Institutionen gedacht, die noch keinem anderen Department zugeordnet werden können.");
		department.setAddress(universityEntity.getAddress());
		department.setPostcode(universityEntity.getPostcode());
		department.setCity(universityEntity.getCity());
		department.setCountry(universityEntity.getCountry());
		department.setEmail(universityEntity.getEmail());
		department.setEnabled(true);
		department.setLocale(universityEntity.getLocale());
		department.setOwnerName(universityEntity.getOwnerName());
		department.setTelefax(universityEntity.getTelefax());
		department.setTelephone(universityEntity.getTelephone());
		department.setWebsite(universityEntity.getWebsite());
		universityEntity.add(department);

		// Create the University
		this.getUniversityDao().create(universityEntity);
		Validate.notNull(universityEntity.getId(), "UniversityService.handleCreate - Couldn't create University");

		// FIXME - Kai, Indexing should not base on VOs!
		// KAI: Do not delete this!!! Set id of university VO for indexing
		university.setId(universityEntity.getId());

		// Create default Groups for the University
		GroupItem groupItem = new GroupItem();
		groupItem.setName("UNIVERSITY_" + universityEntity.getId() + "_ADMINS");
		groupItem.setLabel("autogroup_administrator_label");
		groupItem.setGroupType(GroupType.ADMINISTRATOR);
		Group admins = this.getOrganisationService().createGroup(universityEntity.getId(), groupItem);

		// Security
		this.getSecurityService().createObjectIdentity(universityEntity, null);
		this.getSecurityService().setPermissions(admins, universityEntity, LectureAclEntry.UNIVERSITY_ADMINISTRATION);

		// Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(universityEntity.getId(), userId);
		this.getOrganisationService().addUserToGroup(userId, admins.getId());

		// TODO: Fire createdUniversity event to bookmark University to User who created it

		return universityEntity.getId();
	}

	/**
	 * @see org.openuss.lecture.UniversityService#create(org.openuss.lecture.PeriodInfo)
	 */
	protected java.lang.Long handleCreatePeriod(org.openuss.lecture.PeriodInfo period) throws java.lang.Exception {

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

		// Transform ValueObject into Entity
		Period periodEntity = this.getPeriodDao().periodInfoToEntity(period);

		// Update Entity
		this.getPeriodDao().update(periodEntity);
	}

	/**
	 * @see org.openuss.lecture.UniversityService#removeUniversity(java.lang.Long)
	 */
	protected void handleRemoveUniversity(java.lang.Long universityId) throws java.lang.Exception {

		Validate.notNull(universityId, "UniversityService.handleRemoveUniversity - the UniversityID cannot be null");

		// TODO: Fire removedUniversity event to delete all bookmarks

		this.getUniversityDao().remove(universityId);

	}

	/**
	 * @see org.openuss.lecture.UniversityService#removePeriod(java.lang.Long)
	 */
	protected void handleRemovePeriod(java.lang.Long periodId) throws java.lang.Exception {

		Validate.notNull(periodId, "UniversityService.handleRemovePeriod - the PeriodID cannot be null");

		Period period = this.getPeriodDao().load(periodId);
		
		if (period.getCourses().size()==0) {
			this.getPeriodDao().remove(periodId);
		} else {
			throw new IllegalArgumentException("The Period "+periodId+" contains at least one Courses. Remove Courses before!");
		}
	}

	/**
	 * @see org.openuss.lecture.UniversityService#removePeriodAndCourses(java.lang.Long)
	 */
	protected void handleRemovePeriodAndCourses(Long periodId) throws Exception {
		
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
		Validate.notNull(university,
				"UniversityService.handleFindUniversity - no University found corresponding to the ID " + universityId);

		return this.getUniversityDao().toUniversityInfo(university);
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findAllUniversities()
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindAllUniversities() throws java.lang.Exception {

		Collection<University> universities = this.getUniversityDao().loadAll();

		List universityInfos = new ArrayList();
		for (University university : universities) {
			universityInfos.add(this.getUniversityDao().toUniversityInfo(university));
		}

		return universityInfos;
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findUniversitiesByUser(Long, Boolean)
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindUniversitiesByMemberAndEnabled(Long userId, Boolean enabled)
			throws java.lang.Exception {

		Validate.notNull(userId, "UniversityServiceImpl.findUniversitiesByUser - userId cannot be null.");

		// Load user
		User user = this.getUserDao().load(userId);

		// Load all universities
		Collection<University> universities = this.getUniversityDao().findByEnabled(enabled);

		// Get universities of user
		List universityInfos = new ArrayList();
		for (University university : universities) {
			if (university.getMembership().getMembers().contains(user)) {
				universityInfos.add(this.getUniversityDao().toUniversityInfo(university));
			}
		}

		return universityInfos;
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findPeriod(java.lang.Long)
	 */
	protected org.openuss.lecture.PeriodInfo handleFindPeriod(java.lang.Long periodId) throws java.lang.Exception {

		Validate.notNull(periodId, "UniversityService.handleFindPeriod - the PeriodID cannot be null");

		Period periodEntity = this.getPeriodDao().load(periodId);
		Validate.notNull(periodEntity, "UniversityService.handleFindPeriod - no Period found corresponding to the ID "
				+ periodId);

		return this.getPeriodDao().toPeriodInfo(periodEntity);
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findPeriodsByUniversity(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected java.util.List handleFindPeriodsByUniversity(java.lang.Long universityId) throws java.lang.Exception {

		Validate.notNull(universityId,
				"UniversityService.handleFindPeriodsByUniversity - the universityID cannot be null");

		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"UniversityService.handleFindPeriodsByUniversity - no University found corresponding to the ID "
						+ universityId);

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

		Validate.notNull(universityId,
				"UniversityService.handleFindActivePeriodByUniversity - the universityID cannot be null");

		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"UniversityService.handleFindActivePeriodsByUniversity - no University found corresponding to the ID "
						+ universityId);

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

	/**
	 * @see org.openuss.lecture.UniversityService#findUniversitiesByType(UniversityType, Boolean)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindUniversitiesByTypeAndEnabled(UniversityType universityType, Boolean enabled)
			throws Exception {

		Validate.notNull(universityType,
				"UniversityService.handleFindUniversitiesByTypeAndEnabled - UniversityType cannot be null.");
		Validate.notNull(enabled, "UniversityService.handleFindUniversitiesByTypeAndEnabled - enabled cannot be null.");

		return this.getUniversityDao().findByTypeAndEnabled(UniversityDao.TRANSFORM_UNIVERSITYINFO, universityType,
				enabled);
	}

	/**
	 * @see org.openuss.lecture.UniversityService#removeCompleteUniversityTree(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleRemoveCompleteUniversityTree(Long universityId) throws Exception {

		Validate.notNull(universityId, "UniversityService.removeCompleteUniversityTree - universityId cannot be null.");

		// Find University
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"UniversityService.removeCompleteUniversityTree - cannot find a university with the corresponding universityId "
						+ universityId);

		// Get Departments
		List<Department> departmentsOfUni = university.getDepartments();

		Iterator iter = departmentsOfUni.iterator();
		while (iter.hasNext()) {
			Department department = (Department) iter.next();
			// TODO: Fire removing department event

			Iterator instIter = department.getInstitutes().iterator();
			while (instIter.hasNext()) {
				Institute institute = (Institute) instIter.next();
				// TODO: Fire removing institute event

				Iterator courseTypeIter = institute.getCourseTypes().iterator();
				while (courseTypeIter.hasNext()) {
					CourseType courseType = (CourseType) courseTypeIter.next();
					// TODO: Fire removing courseType event

					Iterator courseIter = courseType.getCourses().iterator();
					while (courseIter.hasNext()) {
						courseIter.next();
						// TODO: Fire removing course event
					}

					// remove courses
					this.getCourseDao().remove(courseType.getCourses());

					// remove courseType
					this.getCourseTypeDao().remove(courseType);
				}

				// remove institute
				this.getInstituteDao().remove(institute);
			}

			// remove department
			this.getDepartmentDao().remove(department);
		}

		// remove periods
		// this.getPeriodDao().remove(university.getPeriods());

		// remove university
		this.getUniversityDao().remove(university);
	}

	/*------------------- private methods -------------------- */

	// TODO: Add Set of listeners
	// TODO: Method unregisterListener
	// TODO: Method fireRemovingUniversity (University university)
	// TODO: Method fireCreatedUniversity (University university)
}