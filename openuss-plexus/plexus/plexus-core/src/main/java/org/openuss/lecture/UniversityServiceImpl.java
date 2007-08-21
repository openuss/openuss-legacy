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
import org.openuss.security.Authority;
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
						+ ". Er ist als Auffangbeh�lter f�r Veranstaltungen gedacht, die keinem anderen Zeitraum zugeordnet werden k�nnen.");
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
						+ ". Es ist als Auffangbeh�lter f�r Institutionen gedacht, die noch keinem anderen Fachbereich zugeordnet werden k�nnen.");
		department.setOwnerName(university.getOwnerName());
		department.setEnabled(true);
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
		Group adminsUni = this.getOrganisationService().createGroup(universityEntity.getId(), groupItemUni);

		GroupItem groupItemDepart = new GroupItem();
		groupItemDepart.setName("DEPARTMENT_" + department.getId() + "_ADMINS");
		groupItemDepart.setLabel("autogroup_administrator_label");
		groupItemDepart.setGroupType(GroupType.ADMINISTRATOR);
		Group adminsDepart = this.getOrganisationService().createGroup(department.getId(), groupItemDepart);

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

		Validate.notNull(period, "UniversityService.handleCreate - the period cannot be null");
		if (period.getDefaultPeriod() == null) {
			period.setDefaultPeriod(false);
		}
		Validate.isTrue(!period.getDefaultPeriod(),
				"UniversityService.handleCreate - You cannot create a default Period!");
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

		// It is not intended to use this method to change the status of enabled!
	}

	/**
	 * @see org.openuss.lecture.UniversityService#update(org.openuss.lecture.PeriodInfo)
	 */
	protected void handleUpdate(org.openuss.lecture.PeriodInfo period) throws java.lang.Exception {

		Validate.notNull(period, "UniversityService.handleUpdate - the Period cannot be null");

		// It is not intended to use this method to change the status of defaultPeriod!
		if (period.getDefaultPeriod() == null) {
			period.setDefaultPeriod(false);
		}

		// Transform ValueObject into Entity
		Period periodEntity = this.getPeriodDao().periodInfoToEntity(period);

		// Update Entity
		this.getPeriodDao().update(periodEntity);

	}

	/**
	 * @see org.openuss.lecture.UniversityService#removeUniversity(java.lang.Long)
	 */
	protected void handleRemoveUniversity(java.lang.Long universityId) throws java.lang.Exception {

		/*
		 * Validate.notNull(universityId, "UniversityService.handleRemoveUniversity - the UniversityID cannot be null"); //
		 * TODO: Fire removedUniversity event to delete all bookmarks
		 * 
		 * University university = this.getUniversityDao().load(universityId);
		 * 
		 * Validate .isTrue(university.getDepartments().isEmpty(), "UniversityService.handleRemoveUniversity - the
		 * University contains at least one Department. Delete before."); Validate
		 * .isTrue(university.getPeriods().isEmpty(), "UniversityService.handleRemoveUniversity - the University
		 * contains at least one Period. Delete before."); this.getUniversityDao().remove(university);
		 * 
		 * 
		 */

		Validate.notNull(universityId, "UniversityService.handleRemoveUniversity - universityId cannot be null.");

		// Find University
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"UniversityService.handleRemoveUniversity - cannot find a university with the corresponding ID "
						+ universityId);

		// TODO: Fire removedUniversity event to delete all bookmarks

		//university.getMembership().getGroups().clear(); // due to problems of cascade
		
		// Get Departments
		List<Department> departments = university.getDepartments();
		for (Department department : departments) {

			// TODO: Fire removedDepartment event to delete all bookmarks

			List<Institute> institutes = department.getInstitutes();
			for (Institute institute : institutes) {

				// TODO: Fire removedInstitute event to delete all bookmarks

				List<CourseType> courseTypes = institute.getCourseTypes();
				for (CourseType courseType : courseTypes) {

					// TODO: Fire removedCourseType event to delete all bookmarks

					List<Course> courses = courseType.getCourses();
					for (Course course : courses) {
						// TODO: Fire removedCourse event to delete all bookmarks
					}
					// remove Courses
					this.getCourseDao().remove(courseType.getCourses());
				}
				// remove CourseTypes
				this.getCourseTypeDao().remove(courseTypes);
				institute.setMembership(null);
			}
			// remove Institutes
			this.getInstituteDao().remove(institutes);
			department.setMembership(null);

		}
		// remove Departments
		this.getDepartmentDao().remove(departments);

		// remove Periods
		this.getPeriodDao().remove(university.getPeriods());

		// remove University (including its Groups)
		
		List<Group> groups = university.getMembership().getGroups();
		List<Group> groups2 = new ArrayList<Group>();
		for (Group group:groups) {
			groups2.add(group);
		}
		for (Group group:groups2) {
			this.getOrganisationService().removeGroup(university.getId(), group.getId());
		}
		
		this.getUniversityDao().remove(university);

	}

	/**
	 * @see org.openuss.lecture.UniversityService#removePeriod(java.lang.Long)
	 */
	protected void handleRemovePeriod(java.lang.Long periodId) throws java.lang.Exception {

		Validate.notNull(periodId, "UniversityService.handleRemovePeriod - the PeriodID cannot be null");

		Period period = this.getPeriodDao().load(periodId);

		if (period.getCourses().size() == 0) {
			this.getPeriodDao().remove(periodId);
			period.getUniversity().remove(period);
		} else {
			throw new IllegalArgumentException("The Period " + periodId
					+ " contains at least one Courses. Remove Courses before!");
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
	 * @see org.openuss.lecture.UniversityService#findActivePeriodsByUniversity(java.lang.Long)
	 */
	protected List handleFindActivePeriodsByUniversity(java.lang.Long universityId) throws java.lang.Exception {

		Validate.notNull(universityId,
				"UniversityService.handleFindActivePeriodByUniversity - the universityID cannot be null");

		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university,
				"UniversityService.handleFindActivePeriodsByUniversity - no University found corresponding to the ID "
						+ universityId);

		List activePeriods = university.getActivePeriods();
		if (activePeriods == null) {
			return null;
		} else {
			this.getPeriodDao().toPeriodInfoCollection(activePeriods);
			return activePeriods;
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
	
	@Override
	public void handleSetUniversityStatus (Long universityId, boolean status) {
		Validate.notNull(universityId, "UniversityService.setUniversityStatus - the universityId cannot be null.");
		Validate.notNull(status, "UniversityService.setUniversityStatus - status cannot be null.");
		
		// Load university
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "UniversityService.setUniversityStatus - university cannot be found with the corresponding universityId "+universityId);
		
		// Set status
		university.setEnabled(status);
		this.update(this.getUniversityDao().toUniversityInfo(university));
	}
	
	@Override
	public boolean handleIsNoneExistingUniversityShortcut(UniversityInfo self, String shortcut) throws Exception {
		University found = getUniversityDao().findByShortcut(shortcut);
		UniversityInfo foundInfo = null;
		if (found != null) {
			foundInfo = this.getUniversityDao().toUniversityInfo(found);
		}
		return isEqualOrNull(self, foundInfo);
	}
	
	
	/**
	 * @see org.openuss.lecture.UniversityService#findPeriodsByUniversityWithActiveCourses(java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	@Override
	public List handleFindPeriodsByUniversityWithCourses(Long universityId) throws Exception {
		Validate.notNull(universityId, "UniversityService.findPeriodsByUniversityWithActiveCourses -" +
				"the universityId cannot be null.");
		
		University university = this.getUniversityDao().load(universityId);
		Validate.notNull(university, "UniversityService.findPeriodsByUniversityWithActiveCourses -" +
				"cannot find a university with the given universityId "+universityId);
		
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

	/*------------------- private methods -------------------- */

	/**
	 * Convenience method for isNonExisting methods.<br/> Checks whether or not the found record is equal to self
	 * entry.
	 * <ul>
	 * <li>self == null AND found == null => <b>true</b></li>
	 * <li>self == null AND found <> null => <b>false</b></li>
	 * <li>self <> null AND found == null => <b>true</b></li>
	 * <li>self <> null AND found <> null AND self == found => <b>true</b></li>
	 * <li>self <> null AND found <> null AND self <> found => <b>false</b></li>
	 * </ul>
	 * 
	 * @param self
	 *            current record
	 * @param found
	 *            in database
	 * @return true or false
	 */
	private boolean isEqualOrNull(Object self, Object found) {
		if (self == null || found == null) {
			return found == null;
		} else {
			return self.equals(found);
		}
	}

	/*------------------- private methods -------------------- */

	// TODO: Add Set of listeners
	// TODO: Method unregisterListener
	// TODO: Method fireRemovingUniversity (University university)
	// TODO: Method fireCreatedUniversity (University university)
}