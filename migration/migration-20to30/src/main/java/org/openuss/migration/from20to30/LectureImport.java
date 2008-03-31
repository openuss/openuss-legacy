package org.openuss.migration.from20to30;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.CourseTypeDao;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.Period;
import org.openuss.migration.legacy.domain.Assistantfaculty2;
import org.openuss.migration.legacy.domain.Enrollment2;
import org.openuss.migration.legacy.domain.Faculty2;
import org.openuss.migration.legacy.domain.Semester2;
import org.openuss.migration.legacy.domain.Subject2;
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.GroupType;
import org.openuss.security.Membership;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.security.acl.ObjectIdentity;
import org.openuss.security.acl.ObjectIdentityDao;
import org.openuss.security.acl.Permission;
import org.openuss.security.acl.PermissionPK;

/**
 * This Service migrate data from openuss 2.0 to openuss-plexus 3.0
 * 
 * @author Ingo Dueppe
 * 
 */
public class LectureImport extends DefaultImport {

	private static final long DEFAULT_DEPARTMENT_ID = 12L;

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(LectureImport.class);

	/** InstituteDao */
	private InstituteDao instituteDao;

	/** GroupDao */
	private GroupDao groupDao;

	/** CourseDao */
	private CourseDao courseDao;

	/** CourseTypeDao */
	private CourseTypeDao courseTypeDao;

	/** ObjectIdentityDao */
	private ObjectIdentityDao objectIdentityDao;

	/** UserImport */
	private UserImport userImport;

	/** List of institutes */
	private Collection<Institute> institutes = new ArrayList<Institute>(1500);

	/** Map faculty to institute */
	private Map<Faculty2, Institute> faculty2Institute = new HashMap<Faculty2, Institute>(1500);

	/** Map legacy id of enrollment to course object */
	private Map<String, Course> id2Course = new HashMap<String, Course>(1500);

	/** Map legacy id of subject to CourseType object */
	private Map<String, CourseType> id2CourseType = new HashMap<String, CourseType>(1500);

	/** Map Insitute to ObjectIdentity */
	private Map<Institute, ObjectIdentity> institute2ObjectIdentity = new HashMap<Institute, ObjectIdentity>();
	
	/** Default Department */
	private Department department;
	
	private ObjectIdentity departmentOI;
	
	/** DepartmentDao **/
	private DepartmentDao departmentDao;
	
	private Map<Long,Period> periods = new HashMap<Long, Period>();
	
	private PeriodIdentifier periodIdentifier = new PeriodIdentifier();

	/**
	 * Imports legacy data of faculties, subjects, semesters and enrollments.
	 * These objects will be transformed to institute, period, coursetype and
	 * course objects.
	 */
	public void perform() {
		logger.debug("initializing...");
		initDepartment();
		
		logger.info("loading institutes structures...");
		loadFaculties();
		loadSubjectsSemesterEnrollments();

		saveInstitutes();

		logger.info("loading and saving permissiosn of institutes...");
		createInstitutePermissions();
		createGroupsGrantPermissionsOfFaculties();
		
		logger.info("clear everything");
		clearCollections();
		
	}

	private void initDepartment() {
		department = departmentDao.load(DEFAULT_DEPARTMENT_ID);
		for (Period period: department.getUniversity().getPeriods()) {
			periods.put(period.getId(), period);
		}
		departmentOI = objectIdentityDao.load(department.getId());
	}

	private void clearCollections() {
		id2Course = null;
		id2CourseType = null;
		institute2ObjectIdentity = null;
		institutes = null;
		faculty2Institute = null;
		periods = null;
	}

	private void saveInstitutes() {
		logger.info("saving institutes ...");
		instituteDao.create(institutes);
		departmentDao.update(department);
		ImportUtil.refresh(institute2ObjectIdentity);
		logger.info("saving legacy id mapping");

		logger.info("saving legacy ids of institues");
		for (Map.Entry<Faculty2, Institute> entry : faculty2Institute.entrySet()) {
			identifierDao.insertLegacyId(entry.getKey().getId(), entry.getValue().getId());
		}

		logger.info("saving legacy ids of courses");
		for (Map.Entry<String, Course> entry : id2Course.entrySet()) {
			identifierDao.insertLegacyId(entry.getKey(), entry.getValue().getId());
		}

		logger.info("saving legacy ids of course types");
		for (Map.Entry<String, CourseType> entry : id2CourseType.entrySet()) {
			identifierDao.insertLegacyId(entry.getKey(), entry.getValue().getId());
		}

	}

	private void loadFaculties() {
		Collection<Faculty2> faculties = legacyDao.loadAllInstitutes();
		logger.debug("load " + faculties.size() + " legacy faculties entries.");
		for (Faculty2 faculty : faculties) {
			if (faculty.getEnrollments().isEmpty()) {
				identifierDao.log("Skip empty faculty "+faculty.getName());
				logger.debug("Skip empty faculty "+faculty.getName());
			} else {
				if (ImportUtil.toBoolean(faculty.getAactive())) {
					Institute institute = createInstitute(faculty);
					// FIXME OWNER IS NOT ACTIVE
					User owner = userImport.loadUserByLegacyId(faculty.getAssistant().getId());
					if (owner != null) {
						// institute.setOwner(owner);
						institute.getMembership().getMembers().add(owner);
	
						institutes.add(institute);
						faculty2Institute.put(faculty, institute);
						department.add(institute);
					} else {
						logger.debug("skip faculty because owner doesn't exist " + faculty.getName());
					}
					
				} else {
					logger.debug("skip faculty is not active " + faculty.getName());
					identifierDao.log("skip faculty is not active " + faculty.getName());
				}
			}
		}
	}

	private void loadSubjectsSemesterEnrollments() {
		logger.info("importing subjects...");
		for (Map.Entry<Faculty2, Institute> entry : faculty2Institute.entrySet()) {
			Faculty2 faculty = entry.getKey();
			Institute institute = entry.getValue();

			parseSubjectsOfFaculty(faculty, institute);
		}
	}

	private void createInstitutePermissions() {
		logger.info("create insitute permission settings...");

		Collection<ObjectIdentity> objIds = new ArrayList<ObjectIdentity>();
		Collection<Group> groups = new ArrayList<Group>();

		// Swicht mapping - need to have persistent faculty object to work
		// within a hashmap
		Map<Institute, Faculty2> institute2Faculty = new HashMap<Institute, Faculty2>(2000);
		for (Map.Entry<Faculty2, Institute> entry : faculty2Institute.entrySet()) {
			institute2Faculty.put(entry.getValue(), entry.getKey());
		}

		for (Institute institute : institutes) {
			Faculty2 faculty = institute2Faculty.get(institute);
			if (faculty != null) {
				Group groupAdmins = createGroup(institute.getId(), GroupType.ADMINISTRATOR);
				Group groupAssistants = createGroup(institute.getId(), GroupType.ASSISTANT);
				Group groupTutors = createGroup(institute.getId(), GroupType.TUTOR);

				buildInstituteSecurity(objIds, groups, institute, groupAdmins, groupAssistants, groupTutors);
				buildInstituteMembers(faculty, institute, groupAdmins, groupAssistants);
			} else {
				logger.debug("faculty to institute not found " + institute.getName());
			}
		}

		logger.debug("starting to persist institutes, groups, objectidenties and permissions");

		groupDao.create(groups);
		instituteDao.update(institutes);
		objectIdentityDao.create(objIds);
	}

	/**
	 * The faculty groups must inherit the grant privileges from institute.
	 * Therefore each group must be defined as a subelement of the faculty.
	 */
	private void createGroupsGrantPermissionsOfFaculties() {
		logger.debug("creating institute group grant permissions.");
		List<ObjectIdentity> groupObjIds = new ArrayList<ObjectIdentity>();
		for (Institute institute : institutes) {
			ObjectIdentity instituteObjId = institute2ObjectIdentity.get(institute);
			for (Group group : institute.getMembership().getGroups()) {
				groupObjIds.add(ImportUtil.createObjectIdentity(group.getId(), instituteObjId));
			}
		}
		logger.debug("persisting institute group grant permissions.");
		objectIdentityDao.create(groupObjIds);
	}

	private Group createGroup(Long id, GroupType groupType) {
		String name = null;
		String label = null;
		if (groupType == GroupType.ADMINISTRATOR) {
			name = "GROUP_FACULTY_" + id + "_ADMINS";
			label = "Administrators";
		} else if (groupType == GroupType.ASSISTANT) {
			name = "GROUP_FACULTY_" + id + "_ASSISTANTS";
			label = "Assistants";
		} else if (groupType == GroupType.TUTOR) {
			name = "GROUP_FACULTY_" + id + "_TUTORS";
			label = "Tutors";
		}
		Group group = Group.Factory.newInstance();
		group.setName(name);
		group.setLabel(label);
		group.setPassword(null);
		group.setGroupType(groupType);
		return group;
	}

	private void buildInstituteSecurity(Collection<ObjectIdentity> objIds, Collection<Group> groups,
		Institute institute, Group groupAdmins, Group groupAssistants, Group groupTutors) {
		institute.getMembership().getGroups().add(groupAdmins);
		institute.getMembership().getGroups().add(groupAssistants);
		institute.getMembership().getGroups().add(groupTutors);

		groups.addAll(institute.getMembership().getGroups());

		// create object identity structure institute <-(parent)---- course
		ObjectIdentity instituteObjId = ImportUtil.createObjectIdentity(institute.getId(), departmentOI);
		objIds.add(instituteObjId);

		// cache institute <--> objId dependency
		institute2ObjectIdentity.put(institute, instituteObjId);

		for(CourseType courseType : institute.getCourseTypes() ) {
			ObjectIdentity courseTypeObjectIdentity = ImportUtil.createObjectIdentity(courseType.getId(), instituteObjId);
			objIds.add(courseTypeObjectIdentity);
			for (Course course : courseType.getCourses()) {
				objIds.add(ImportUtil.createObjectIdentity(course.getId(), courseTypeObjectIdentity));
			}
		}
		
		// create group permissions
		Permission instituteAdministration = Permission.Factory.newInstance();
		instituteAdministration.setPermissionPk(new PermissionPK());
		instituteAdministration.getPermissionPk().setAclObjectIdentity(instituteObjId);
		instituteAdministration.getPermissionPk().setRecipient(groupAdmins);
		instituteAdministration.setMask(LectureAclEntry.INSTITUTE_ADMINISTRATION);

		Permission instituteAssist = Permission.Factory.newInstance();
		instituteAssist.setPermissionPk(new PermissionPK());
		instituteAssist.getPermissionPk().setAclObjectIdentity(instituteObjId);
		instituteAssist.getPermissionPk().setRecipient(groupAssistants);
		instituteAssist.setMask(LectureAclEntry.INSTITUTE_ASSIST);
		
		Permission instituteTutor = Permission.Factory.newInstance();
		instituteTutor.setPermissionPk(new PermissionPK());
		instituteTutor.getPermissionPk().setAclObjectIdentity(instituteObjId);
		instituteTutor.getPermissionPk().setRecipient(groupTutors);
		instituteTutor.setMask(LectureAclEntry.INSTITUTE_TUTOR);
		
		instituteObjId.addPermission(instituteAdministration);
		instituteObjId.addPermission(instituteAssist);
		instituteObjId.addPermission(instituteTutor);
	}

	private void buildInstituteMembers(Faculty2 faculty2, Institute institute, Group groupAdmins, Group groupAssistants) {
		// analyse institute members and aspirants
		if (faculty2.getAssistantinstitutes() != null && !faculty2.getAssistantinstitutes().isEmpty()) {
			for (Assistantfaculty2 assistantFaculty : faculty2.getAssistantinstitutes()) {
				User user = userImport.loadUserByLegacyId(assistantFaculty.getAssistant().getId());
				if (user != null) {
					// aspirant or member?
					if (ImportUtil.toBoolean(assistantFaculty.getAactive())) {
						// if (!institute.getOwner().equals(user)) {
						// FIXME check for duplicated members
						institute.getMembership().getMembers().add(user);
						// }
						// is administrator or owner?
						if (ImportUtil.toBoolean(assistantFaculty.getIsadmin())
								|| faculty2.getAssistant().getId().equals(assistantFaculty.getAssistant().getId())) {
							groupAdmins.addMember(user);
							user.addGroup(groupAdmins);
						}
						groupAssistants.addMember(user);
						user.addGroup(groupAssistants);
					} else {
						logger.debug("skip faculty assistant aspirant " + user.getDisplayName());
					}
				} else {
					logger.error("user not found " + assistantFaculty.getAssistant().getId());
				}
			}
		}
	}

	/**
	 * Transform institute subject structure to new subjects
	 * 
	 * @param faculty2
	 * @param institute
	 */
	private void parseSubjectsOfFaculty(Faculty2 faculty2, Institute institute) {
		for (Subject2 subject2 : faculty2.getSubjects()) {
			CourseType courseType = createCourseType(subject2);
			id2CourseType.put(subject2.getId(), courseType);
			institute.add(courseType);
			courseType.setInstitute(institute);
			
			for (Enrollment2 enrollment:subject2.getEnrollments()) {
				Course course = createCourse(enrollment);
				id2Course.put(enrollment.getId(), course);
				courseType.add(course);
			}
		}
	}

	private CourseType createCourseType(Subject2 subject2) {
		CourseType courseType = CourseType.Factory.newInstance();
		courseType.setName(subject2.getName());
		courseType.setShortcut(subject2.getId());
		courseType.setDescription(subject2.getRemark());
		return courseType;
	}

	private Institute createInstitute(Faculty2 faculty) {
		Institute institute = Institute.Factory.newInstance();
		institute.setMembership(Membership.Factory.newInstance());
		institute.setName(faculty.getName());
		institute.setShortcut(faculty.getId());
		institute.setDescription(faculty.getRemark());
		institute.setWebsite(faculty.getWebsite());
		institute.setLocale(faculty.getLocale());
		institute.setOwnerName(faculty.getOwner());
		institute.setEnabled(ImportUtil.toBoolean(faculty.getAactive()));
		return institute;
	}

	private Course createCourse(Enrollment2 enrollment2) {
		Course course = Course.Factory.newInstance();
		// subject name instead of the previous guid
		Semester2 semester = enrollment2.getSemester();
		course.setShortcut(StringUtils.abbreviate(semester.getName() +"-"+ enrollment2.getId(),30));
		course.setDescription(enrollment2.getSubject().getRemark());
		
		course.setPeriod(periods.get(periodIdentifier.find(semester.getName())));

		if (ImportUtil.toBoolean(enrollment2.getWithPassword())) {
			course.setPassword(enrollment2.getPassword());
			course.setAccessType(AccessType.PASSWORD);
		} else if (ImportUtil.toBoolean(enrollment2.getForpublic())) {
			course.setAccessType(AccessType.OPEN);
		} else {
			course.setAccessType(AccessType.CLOSED);
		}

		course.setBraincontest(ImportUtil.toBoolean(enrollment2.getQuiz()));
		course.setChat(ImportUtil.toBoolean(enrollment2.getChat()));
		course.setDiscussion(ImportUtil.toBoolean(enrollment2.getDiscussion()));
		course.setDocuments(ImportUtil.toBoolean(enrollment2.getLecturematerials()));
		course.setNewsletter(ImportUtil.toBoolean(enrollment2.getMailinglist()));
		course.setFreestylelearning(ImportUtil.toBoolean(enrollment2.getFslinstall()));
		course.setWiki(false);
		return course;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public void setUserImport(UserImport userImport) {
		this.userImport = userImport;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setObjectIdentityDao(ObjectIdentityDao objectIdentityDao) {
		this.objectIdentityDao = objectIdentityDao;
	}

	public Course getCourseByLegacyId(String legacyId) {
		Long id = identifierDao.getId(legacyId);
		if (id != null) {
			return courseDao.load(id);
		} else {
			return null;
		}
	}

	public CourseType getCourseTypeByLegacyId(String legacyId) {
		Long id = identifierDao.getId(legacyId);
		if (id != null) {
			return courseTypeDao.load(id);
		} else {
			return null;
		}
	}

	public Institute getInstituteByLegacyId(String legacyId) {
		Long id = identifierDao.getId(legacyId);
		if (id != null) {
			return instituteDao.load(id);
		} else {
			return null;
		}
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public void setCourseTypeDao(CourseTypeDao courseTypeDao) {
		this.courseTypeDao = courseTypeDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

}
