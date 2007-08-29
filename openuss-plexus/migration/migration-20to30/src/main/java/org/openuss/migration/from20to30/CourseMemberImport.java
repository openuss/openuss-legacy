package org.openuss.migration.from20to30;

import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseMember;
import org.openuss.lecture.CourseMemberDao;
import org.openuss.lecture.CourseMemberType;
import org.openuss.migration.legacy.dao.LegacyDao;
import org.openuss.migration.legacy.domain.Enrollment2;
import org.openuss.migration.legacy.domain.Enrollmentaccesslist2;
import org.openuss.migration.legacy.domain.Student2;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * Import Course Members from Enrollment Access List
 * 
 * @author Ingo Dueppe
 *
 */
public class CourseMemberImport {

	private static final Logger logger = Logger.getLogger(CourseMemberImport.class);
	
	/** LegacyDao */
	private LegacyDao legacyDao;
	
	/** CourseMemberDao */
	private CourseMemberDao courseMemberDao;
	
	/** UserImport */
	private UserImport userImport;
	
	/** LectureImport */
	private LectureImport lectureImport;
	
	/** SecurityService */
	private SecurityService securityService;
	
	public void importMembers() {
		logger.debug("loading course member data");
		List<Enrollmentaccesslist2> accesslist = legacyDao.loadAllEnrollmentAccessList();
		for (Enrollmentaccesslist2 access : accesslist) {
			Enrollment2 enrollment = access.getEnrollment();
			Student2 student = access.getStudent();
			if (enrollment != null && student != null) {
				Course course = lectureImport.getCourseById(enrollment.getId());
				User user = userImport.getUserByLegacyId(student.getId());
				if (course != null && user != null) {
					createMembership(access, course, user);
				} else {
					logger.debug("skip access, because user or course not found!");
				}
			}
		}
	}

	private void createMembership(Enrollmentaccesslist2 access, Course course, User user) {
		CourseMember member = CourseMember.Factory.newInstance();
		member.setCourse(course);
		member.setUser(user);
		if (ImportUtil.toBoolean(access.getAccepted())) {
			securityService.setPermissions(user, course, LectureAclEntry.COURSE_PARTICIPANT);
			member.setMemberType(CourseMemberType.PARTICIPANT);
		} else {
			member.setMemberType(CourseMemberType.ASPIRANT);
		}
		courseMemberDao.create(member);
	}

	public LegacyDao getLegacyDao() {
		return legacyDao;
	}

	public void setLegacyDao(LegacyDao legacyDao) {
		this.legacyDao = legacyDao;
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
	
	

}
