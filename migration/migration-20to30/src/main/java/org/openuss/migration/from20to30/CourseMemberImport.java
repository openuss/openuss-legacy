package org.openuss.migration.from20to30;

import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseMember;
import org.openuss.lecture.CourseMemberDao;
import org.openuss.lecture.CourseMemberPK;
import org.openuss.lecture.CourseMemberType;
import org.openuss.migration.legacy.dao.LegacyDao;
import org.openuss.migration.legacy.domain.Enrollment2;
import org.openuss.migration.legacy.domain.Enrollmentaccesslist2;
import org.openuss.migration.legacy.domain.Student2;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.security.acl.ObjectIdentity;
import org.openuss.security.acl.ObjectIdentityDao;
import org.openuss.security.acl.Permission;
import org.openuss.security.acl.PermissionDao;
import org.openuss.security.acl.PermissionPK;

/**
 * Import Course Members from Enrollment Access List
 * 
 * @author Ingo Dueppe
 *
 */
public class CourseMemberImport extends DefaultImport {

	private static final Logger logger = Logger.getLogger(CourseMemberImport.class);
	
	/** CourseMemberDao */
	private CourseMemberDao courseMemberDao;
	
	/** PermissionDao */
	private PermissionDao permissionDao;
	
	/** UserImport */
	private UserImport userImport;
	
	/** LectureImport */
	private LectureImport lectureImport;
	
	/** SecurityService */
	private SecurityService securityService;
	
	/** ObjectIdentityDao */
	private ObjectIdentityDao objectIdentityDao;
	
	public void perform() {
		logger.debug("loading course member data");
		ScrollableResults results = legacyDao.loadAllEnrollmentAccessList();
		Enrollmentaccesslist2 access = null;
		Enrollment2 enrollment = null;
		while (results.next()) {
			evict(enrollment);
			evict(access);
			access = (Enrollmentaccesslist2) results.get()[0];
			enrollment = access.getEnrollment();
			Student2 student = access.getStudent();
			if (enrollment != null && student != null) {
				Course course = lectureImport.getCourseByLegacyId(enrollment.getId());
				User user = userImport.loadUserByLegacyId(student.getId());
				if (course != null && user != null) {
					createMembership(access, course, user);
				} else {
					logger.debug("skip access, because user or course not found!");
				}
			}
		}
		results.close();
	}

	private void createMembership(Enrollmentaccesslist2 access, Course course, User user) {
		CourseMember member = CourseMember.Factory.newInstance();
		member.setCourseMemberPk(new CourseMemberPK());
		member.getCourseMemberPk().setCourse(course);
		member.getCourseMemberPk().setUser(user);
		if (ImportUtil.toBoolean(access.getAccepted())) {
			storePermission(course, user);
			member.setMemberType(CourseMemberType.PARTICIPANT);
		} else {
			member.setMemberType(CourseMemberType.ASPIRANT);
		}
		courseMemberDao.create(member);
	}

	private void storePermission(Course course, User user) {
		ObjectIdentity objectIdentity = objectIdentityDao.load(course.getId());
		Permission permission = Permission.Factory.newInstance();
		permission.setPermissionPk(new PermissionPK());
		permission.getPermissionPk().setAclObjectIdentity(objectIdentity);
		permission.getPermissionPk().setRecipient(user);
		permission.setMask(LectureAclEntry.COURSE_PARTICIPANT);				
		
		permissionDao.create(permission);
	}

	public LegacyDao getLegacyDao() {
		return legacyDao;
	}

	public UserImport getUserImport() {
		return userImport;
	}

	public void setUserImport(UserImport userImport) {
		this.userImport = userImport;
	}

	public LectureImport getLectureImport() {
		return lectureImport;
	}

	public void setLectureImport(LectureImport lectureImport) {
		this.lectureImport = lectureImport;
	}

	public CourseMemberDao getCourseMemberDao() {
		return courseMemberDao;
	}

	public void setCourseMemberDao(CourseMemberDao courseMemberDao) {
		this.courseMemberDao = courseMemberDao;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public static Logger getLogger() {
		return logger;
	}

	public PermissionDao getPermissionDao() {
		return permissionDao;
	}

	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

	public ObjectIdentityDao getObjectIdentityDao() {
		return objectIdentityDao;
	}

	public void setObjectIdentityDao(ObjectIdentityDao objectIdentityDao) {
		this.objectIdentityDao = objectIdentityDao;
	}
	
	

}
