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
 * @author Malte Stockmann
 */
public class UniversityServiceImpl extends org.openuss.lecture.UniversityServiceBase {

	private static final Logger logger = Logger.getLogger(UniversityServiceImpl.class);

	/**
	 * @see org.openuss.lecture.UniversityService#createUniversity(org.openuss.lecture.UniversityInfo, java.lang.Long)
	 */
	protected java.lang.Long handleCreateUniversity(org.openuss.lecture.UniversityInfo university, java.lang.Long userId) {

		logger.debug("Starting method handleCreateUniversity(UniversityInfo, Long)");

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
		period.setName("Standard Zeitraum");
		period.setDefaultPeriod(true);
		period
				.setDescription("Dies ist der Standard-Zeitraum der "
						+ university.getName()
						+ ". Er ist als Auffangbehälter für Veranstaltungen gedacht, die keinem anderen Zeitraum zugeordnet werden können.");
		period.setStartdate(new Date(0)); // 1. January 1970, 00:00:00 GMT
		Calendar cal = new GregorianCalendar();
		cal.set(2050, 11, 31);// 31. December 2050, 00:00:00 GMT
		period.setEnddate(new Date(cal.getTimeInMillis()));
		universityEntity.add(period);

		// Create a default Department
		Department department = Department.Factory.newInstance();
		department.setName("Standard Fachbereich");
		department.setDefaultDepartment(true);
		department.setDepartmentType(DepartmentType.NONOFFICIAL);
		department.setShortcut("StdDep_" + university.getShortcut());
		department
				.setDescription("Dies ist das (inoffizielle) Standard-Fachbereich der "
						+ university.getName()
						+ ". Es ist als Auffangbehälter für Institutionen gedacht, die noch keinem anderen Fachbereich zugeordnet werden können.");
		department.setOwnerName(university.getOwnerName());
		department.setEnabled(false);
		department.setMembership(Membership.Factory.newInstance());
		department.setAddress(university.getAddress());
		department.setCity(university.getCity());
		department.setCountry(university.getCountry());
		department.setEmail(university.getEmail());
		department.setLocale(university.getLocale());
		department.setPostcode(university.getPostcode());
		department.setTelefax(university.getTelefax());
		department.setTelephone(university.getTelephone());
		department.setTheme(university.getTheme());
		department.setWebsite(university.getWebsite());
		universityEntity.add(department);

		// Create the University
		this.getUniversityDao().create(universityEntity);
		this.getDepartmentDao().create(department);
		this.getPeriodDao().create(period);
		Validate.notNull(universityEntity.getId(), "UniversityService.handleCreate - Couldn't create University");

		// FIXME - Kai, Indexing should not base on VOs!
		// KAI: Do not delete this!!! Set id of university VO for indexing
		university.setId(universityEntity.getId());

		// Create default Groups for the University and it's Department
		GroupItem groupItemUni = new GroupItem();
		groupItemUni.setName("UNIVERSITY_" + universityEntity.getId() + "_ADMINS");
		groupItemUni.setLabel("autogroup_administrator_label");
		groupItemUni.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsUniId = this.getOrganisationService().createGroup(universityEntity.getId(), groupItemUni);
		Group adminsUni = this.getGroupDao().load(adminsUniId);

		GroupItem groupItemDepart = new GroupItem();
		groupItemDepart.setName("DEPARTMENT_" + department.getId() + "_ADMINS");
		groupItemDepart.setLabel("autogroup_administrator_label");
		groupItemDepart.setGroupType(GroupType.ADMINISTRATOR);
		Long adminsDepartId = this.getOrganisationService().createGroup(department.getId(), groupItemDepart);
		Group adminsDepart = this.getGroupDao().load(adminsDepartId);

		// Set ObjectIdentity for Security
		this.getSecurityService().createObjectIdentity(universityEntity, null);
		this.getSecurityService().createObjectIdentity(department, universityEntity);
		this.getSecurityService().createObjectIdentity(period, universityEntity);

		// Set ACL permissions
		this.getSecurityService()
				.setPermissions(adminsUni, universityEntity, LectureAclEntry.UNIVERSITY_ADMINISTRATION);
		this.getSecurityService().setPermissions(adminsDepart, department, LectureAclEntry.DEPARTMENT_ADMINISTRATION);

		// Add Owner to Members and Group of Administrators
		this.getOrganisationService().addMember(universityEntity.getId(), userId);
		this.getOrganisationService().addMember(department.getId(), userId);
		this.getOrganisationService().addUserToGroup(userId, adminsUni.getId());
		this.getOrganisationService().addUserToGroup(userId, adminsDepart.getId());

		// TODO: Fire createdUniversity and createdDepartment to bookmark University and Department to User who created
		// it

		return universityEntity.getId();
	}

	/**
	 * @see org.openuss.lecture.UniversityService#create(org.openuss.lecture.PeriodInfo)
	 */
	protected java.lang.Long handleCreatePeriod(org.openuss.lecture.PeriodInfo period) throws java.lang.Exception {

		Validate.notNull(period, "UniversityService.handleCreatePeriod - the period cannot be null");
		Validate.isTrue(!period.isDefaultPeriod(),
				"UniversityService.handleCreatePeriod - You cannot create a default Period!");
		Validate.notNull(period.getUniversityId(),
				"UniversityService.handleCreatePeriod - the UniversityID cannot be null");
		University university = this.getUniversityDao().load(period.getUniversityId());
		Validate.notNull(university,
				"UniversityService.handleCreatePeriod - no University found corresponding to the UniversityID "
						+ period.getUniversityId());

		//Create Period
		Period periodEntity = this.getPeriodDao().create(this.getPeriodDao().periodInfoToEntity(period));
		periodEntity.getUniversity().getPeriods().add(periodEntity);
		
		// Set ObjectIdentity for Security
		this.getSecurityService().createObjectIdentity(periodEntity, university);		

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

		// It is not intended to use this method to change the status of enabled!
	}

	/**
	 * @see org.openuss.lecture.UniversityService#update(org.openuss.lecture.PeriodInfo)
	 */
	protected void handleUpdate(org.openuss.lecture.PeriodInfo periodInfo) throws java.lang.Exception {

		Validate.notNull(periodInfo, "UniversityService.handleUpdate - the Period cannot be null");

		// Check changes of default Period
		PeriodInfo periodOld = (PeriodInfo) getPeriodDao().load(getPeriodDao().TRANSFORM_PERIODINFO, periodInfo.getId());
		if (!periodOld.isDefaultPeriod() == periodInfo.isDefaultPeriod()) {
			throw new UniversityServiceException("UniversityService.handleUpdate - The default attribute cannot be changed.");
		}

		// Transform VO to Entity
		Period periodEntity = this.getPeriodDao().periodInfoToEntity(periodInfo);
		
		// Update Entity
		this.getPeriodDao().update(periodEntity);

	}

	@Override
	protected void handleRemoveUniversity(Long universityId) throws Exception {
		logger.debug("Starting method handleRemoveUniversity for UniversityID " + universityId);

		Validate.notNull(universityId, "UniversityService.handleRemove - the UniversityId cannot be null");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"UniversityService.handleRemoveUniversity - no University found corresponding to the ID "
						+ universityId);
		Validate.isTrue(university.getDepartments().size() == 0,
				"UniversityService.handleRemoveUniversity - the University still contains Departments");

		// Remove Periods
		List<Period> periodsNew = new ArrayList<Period>();
		for (Period period : university.getPeriods()) {
			periodsNew.add(period);
		}
		for (Period period : periodsNew) {
			this.removePeriod(period.getId());
		}

		// Remove Security
		this.getSecurityService().removeAllPermissions(university);
		this.getSecurityService().removeObjectIdentity(university);

		// Clear Membership
		this.getMembershipService().clearMembership(university.getMembership());

		// Remove University
		this.getUniversityDao().remove(university);

	}

	/**
	 * @see org.openuss.lecture.UniversityService#removeCompleteUniversityTree(java.lang.Long)
	 */
	protected void handleRemoveCompleteUniversityTree(Long universityId) throws Exception {
		logger.debug("Starting method handleRemoveUniversity for UniversityID " + universityId);

		Validate.notNull(universityId, "UniversityId cannot be null.");
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "Cannot find a university with the corresponding ID "	+ universityId);

		if (!university.getDepartments().isEmpty()) {
			// Remove Departments
			List<Department> departments = new ArrayList<Department>();
			for (Department department : university.getDepartments()) {
				departments.add(department);
			}
			for (Department department : departments) {
				this.getDepartmentService().removeCompleteDepartmentTree(department.getId());
			}
		}

		// Remove Periods
		List<Period> periodsNew = new ArrayList<Period>();
		for (Period period : university.getPeriods()) {
			periodsNew.add(period);
		}
		for (Period period : periodsNew) {
			this.removePeriod(period.getId());
		}

		// Remove Security
		this.getSecurityService().removeAllPermissions(university);
		this.getSecurityService().removeObjectIdentity(university);

		// Clear Membership
		this.getMembershipService().clearMembership(university.getMembership());

		// Remove University
		this.getUniversityDao().remove(university);
	}

	/**
	 * @see org.openuss.lecture.UniversityService#removePeriod(java.lang.Long)
	 */
	protected void handleRemovePeriod(java.lang.Long periodId) throws java.lang.Exception {

		Validate.notNull(periodId, "UniversityService.handleRemovePeriod - the PeriodID cannot be null");
		Period period = this.getPeriodDao().load(periodId);
		Validate.notNull(period,
				"UniversityService.handleRemovePeriod - no Period found corresponding to the PeriodID " + periodId);
		Validate.isTrue(period.getCourses().size() == 0,
				"UniversityService.handleRemovePeriod - the Period still contains Courses");

		// Remove Security
		this.getSecurityService().removeAllPermissions(period);
		this.getSecurityService().removeObjectIdentity(period);

		// Remove Period
		period.getUniversity().remove(period);
		this.getPeriodDao().remove(period);

	}

	/**
	 * @see org.openuss.lecture.UniversityService#findUniversity(java.lang.Long)
	 */
	protected org.openuss.lecture.UniversityInfo handleFindUniversity(java.lang.Long universityId)
			throws java.lang.Exception {

		Validate.notNull(universityId, "UniversityService.handleFindUniversity - the UniversityID cannot be null");

		return (UniversityInfo) this.getUniversityDao().load(getUniversityDao().TRANSFORM_UNIVERSITYINFO, universityId);
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
	protected java.util.List handleFindUniversitiesByMemberAndEnabled(Long userId, boolean enabled)
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
		return (PeriodInfo) getPeriodDao().load(getPeriodDao().TRANSFORM_PERIODINFO, periodId);
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
	 * @see org.openuss.lecture.UniversityService#findActivePeriodsByUniversity(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindPeriodsByUniversityAndActivation(java.lang.Long universityId, boolean active) throws java.lang.Exception {

		Validate.notNull(universityId,
				"UniversityService.handleFindActivePeriodByUniversity - the universityID cannot be null");

		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"UniversityService.handleFindActivePeriodsByUniversity - no University found corresponding to the ID "
						+ universityId);

		List<PeriodInfo> periodInfos = new ArrayList<PeriodInfo>();
		for (Period period : university.getPeriods()) {
			if (period.isActive() == active) {
				periodInfos.add(this.getPeriodDao().toPeriodInfo(period));
			}
		}

		return periodInfos;
	}

	@SuppressWarnings( { "unchecked" })
	public List handleFindPeriodsByInstituteWithCoursesOrActive(InstituteInfo instituteInfo) {

		Validate.notNull(instituteInfo, "UniversityService.handleFindPeriodsByInstituteWithCoursesOrActive - "
				+ "instituteInfo cannot be null.");
		Validate.notNull(instituteInfo.getId(), "UniversityService.handleFindPeriodsByInstituteWithCoursesOrActive - "
				+ "the id of instituteInfo cannot be null.");

		Institute instituteEntity = this.getInstituteDao().load(instituteInfo.getId());
		Validate.notNull(instituteEntity, "UniversityService.handleFindPeriodsByInstituteWithCoursesOrActive - "
				+ "no instituteEntity could be found with the corresponding instituteId " + instituteInfo.getId());

		Validate.notNull(instituteEntity.getDepartment(),
				"UniversityService.handleFindPeriodsByInstituteWithCoursesOrActive - "
						+ "no department is associated with the given institute.");
		Validate.notNull(instituteEntity.getDepartment().getUniversity(),
				"UniversityService.handleFindPeriodsByInstituteWithCoursesOrActive - "
						+ "no university is associated with the department of the given institute.");

		List<Period> periods = this.getPeriodDao().findByUniversity(instituteEntity.getDepartment().getUniversity());
		List<PeriodInfo> periodInfos = new ArrayList<PeriodInfo>();
		Iterator iter = periods.iterator();
		while (iter.hasNext()) {
			Period period = (Period) iter.next();
			if (period.isActive() || period.getCourses().size() > 0) {
				periodInfos.add(this.getPeriodDao().toPeriodInfo(period));
			}
		}
		return periodInfos;
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findAllUniversitiesByEnabled(java.lang.Boolean)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindUniversitiesByEnabled(boolean enabled) throws Exception {

		return this.getUniversityDao().findByEnabled(UniversityDao.TRANSFORM_UNIVERSITYINFO, enabled);
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findUniversitiesByType(UniversityType, Boolean)
	 */
	@SuppressWarnings( { "unchecked" })
	protected List handleFindUniversitiesByTypeAndEnabled(UniversityType universityType, boolean enabled)
			throws Exception {

		Validate.notNull(universityType,
				"UniversityService.handleFindUniversitiesByTypeAndEnabled - UniversityType cannot be null.");
		Validate.notNull(enabled, "UniversityService.handleFindUniversitiesByTypeAndEnabled - enabled cannot be null.");

		return this.getUniversityDao().findByTypeAndEnabled(UniversityDao.TRANSFORM_UNIVERSITYINFO, universityType,
				enabled);
	}

	@Override
	public void handleSetUniversityStatus(Long universityId, boolean status) {
		Validate.notNull(universityId, "UniversityService.setUniversityStatus - the universityId cannot be null.");
		Validate.notNull(status, "UniversityService.setUniversityStatus - status cannot be null.");

		// Load university
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"UniversityService.setUniversityStatus - university cannot be found with the corresponding universityId "
						+ universityId);

		// Set status
		university.setEnabled(status);
		UniversityInfo universityInfo = this.getUniversityDao().toUniversityInfo(university);
		this.update(universityInfo);
		
		// Set subordinate organisations to "disabled" if the university was just disabled
		if(!status){
			for (Department department : university.getDepartments()){
				department.setEnabled(false);
				this.getDepartmentDao().update(department);
				for(Institute institute : department.getInstitutes()){
					institute.setEnabled(false);
					this.getInstituteDao().update(institute);
					for(CourseType courseType : institute.getCourseTypes()){
						for(Course course : courseType.getCourses()){
							course.setEnabled(false);
							this.getCourseDao().update(course);
						}
					}
				}
			}
		}
		
	}

//	@Override
//	public boolean handleIsNoneExistingOrganisationShortcutByUniversity(UniversityInfo self, String shortcut) throws Exception {
//		Organisation organisationFound = getOrganisationDao().findByShortcut(shortcut);
//		if (organisationFound instanceof University) {
//			University found = (University) organisationFound;
//			UniversityInfo foundInfo = null;
//			if (found != null) {
//				foundInfo = this.getUniversityDao().toUniversityInfo(found);
//			}
//			return isEqualOrNull(self, foundInfo);
//			
//		} else if (organisationFound instanceof Department) {
//			
//			Department found = (Department) organisationFound;
//			DepartmentInfo foundInfo = null;
//			if (found != null) {
//				foundInfo = this.getDepartmentDao().toDepartmentInfo(found);
//			}
//			return isEqualOrNull(self, foundInfo);
//			
//		} else {
//			
//			Institute found = (Institute) organisationFound;
//			InstituteInfo foundInfo = null;
//			if (found != null) {
//				foundInfo = this.getInstituteDao().toInstituteInfo(found);
//			}
//			return isEqualOrNull(self, foundInfo);
//			
//		}
//	}

	@Override
	public boolean handleIsActivePeriod(Long periodId) throws Exception {
		Validate.notNull(periodId, "UniversityService.isActivePeriod - the periodId cannot be null.");

		// Load Period
		Period period = this.getPeriodDao().load(periodId);
		Validate.notNull(period, "UniversityService.isActivePeriod - cannot find period with the given periodId "
				+ periodId);

		return period.isActive();
	}

	/**
	 * @see org.openuss.lecture.UniversityService#findPeriodsByUniversityWithActiveCourses(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	@Override
	public List handleFindPeriodsByUniversityWithCourses(Long universityId) throws Exception {
		Validate.notNull(universityId, "UniversityService.findPeriodsByUniversityWithActiveCourses -"
				+ "the universityId cannot be null.");

		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "UniversityService.findPeriodsByUniversityWithActiveCourses -"
				+ "cannot find a university with the given universityId " + universityId);

		List<Period> allPeriods = this.getPeriodDao().findByUniversity(university);
		List<PeriodInfo> periodsWithActiveCourses = new ArrayList<PeriodInfo>();
		Iterator iter = allPeriods.iterator();
		while (iter.hasNext()) {
			Period period = (Period) iter.next();
			if (period.getCourses() != null) {
				if (period.getCourses().size() > 0) {
					periodsWithActiveCourses.add(this.getPeriodDao().toPeriodInfo(period));
				}
			}
		}

		return periodsWithActiveCourses;
	}
	
	@Override
	public void handleRemovePeriodAndCourses (Long periodId) {
		// FIXME Implement this method
		throw new UnsupportedOperationException("Operation is not implemented yet!");
	}

	/*------------------- private methods -------------------- */

	// TODO: Add Set of listeners
	// TODO: Method unregisterListener
	// TODO: Method fireRemovingUniversity (University university)
	// TODO: Method fireCreatedUniversity (University university)
}