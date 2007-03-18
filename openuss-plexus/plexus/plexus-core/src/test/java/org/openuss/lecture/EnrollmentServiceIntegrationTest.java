// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.security.acl.Permission;

/**
 * JUnit Test for Spring Hibernate EnrollmentService class.
 * @see org.openuss.lecture.EnrollmentService
 */
public class EnrollmentServiceIntegrationTest extends EnrollmentServiceIntegrationTestBase {
	
	private FacultyDao facultyDao;
	
	private TestUtility testUtility;
	
	private SecurityService securityService;
	
	
	public void testAspirantToParticipant() {
		LectureBuilder lectureBuilder = createFacultyStructure(AccessType.APPLICATION);
		
		User user = testUtility.createUserInDB();
		Enrollment enrollment = lectureBuilder.getEnrollment();

		try {
			enrollmentService.applyUser(enrollment, user);
		} catch (EnrollmentApplicationException e) {
			fail(e.getMessage());
		}
	
		List<EnrollmentMemberInfo> aspirants = enrollmentService.getAspirants(enrollment);
		assertEquals(1, aspirants.size());
		
		enrollmentService.acceptAspirant(aspirants.get(0).getId());

		Collection<EnrollmentMember> emptyAspirants = enrollmentService.getAspirants(enrollment);
		assertEquals(0, emptyAspirants.size());
		
		List<EnrollmentMemberInfo> participants = enrollmentService.getParticipants(enrollment);
		assertEquals(1, participants.size());
		
		commit();
		
		Permission permission = securityService.getPermissions(user, enrollment);
		assertNotNull(permission);
		
		LectureAclEntry acl = new LectureAclEntry();
		acl.setMask(permission.getMask());
		
		assertTrue(acl.isPermitted(LectureAclEntry.ENROLLMENT_PARTICIPANT));
	}

	public void testRejectAspirant() {
		LectureBuilder lectureBuilder = createFacultyStructure(AccessType.APPLICATION);
		
		User user = testUtility.createUserInDB();
		Enrollment enrollment = lectureBuilder.getEnrollment();
		
		try {
			enrollmentService.applyUser(enrollment, user);
		} catch (EnrollmentApplicationException e) {
			fail(e.getMessage());
		}
		
		List<EnrollmentMemberInfo> aspirants = enrollmentService.getAspirants(enrollment);
		assertEquals(1, aspirants.size());
		
		enrollmentService.rejectAspirant(aspirants.get(0).getId());
		
		Collection<EnrollmentMember> emptyAspirants = enrollmentService.getAspirants(enrollment);
		assertEquals(0, emptyAspirants.size());
		
		List<EnrollmentMemberInfo> emptyParticipants = enrollmentService.getParticipants(enrollment);
		assertEquals(0, emptyParticipants.size());
	}
	
	public void testAspirants() {
		LectureBuilder lectureBuilder = createFacultyStructure(AccessType.APPLICATION);
		
		User user = testUtility.createUserInDB();
		Enrollment enrollment = lectureBuilder.getEnrollment();
		
		enrollmentService.addAspirant(enrollment, user);
	
		List<EnrollmentMemberInfo> aspirants = enrollmentService.getAspirants(enrollment);
		assertEquals(1, aspirants.size());
		
		enrollmentService.removeMember(aspirants.get(0).getId());

		Collection<EnrollmentMember> emptyAspirants = enrollmentService.getAspirants(enrollment);
		assertEquals(0, emptyAspirants.size());
	}

	public void testAssistants() {
		LectureBuilder lectureBuilder = createFacultyStructure(AccessType.APPLICATION);
		
		User user = testUtility.createUserInDB();
		Enrollment enrollment = lectureBuilder.getEnrollment();
		
		enrollmentService.addAssistant(enrollment, user);
		
		List<EnrollmentMemberInfo> assistants = enrollmentService.getAssistants(enrollment);
		assertEquals(1, assistants.size());
		
		enrollmentService.removeMember(assistants.get(0).getId());
		
		Collection<EnrollmentMember> emptyAssistant = enrollmentService.getAssistants(enrollment);
		assertEquals(0, emptyAssistant.size());
	}
	
	public void testParticipant() {
		LectureBuilder lectureBuilder = createFacultyStructure(AccessType.APPLICATION);
		
		User user = testUtility.createUserInDB();
		Enrollment enrollment = lectureBuilder.getEnrollment();
		
		enrollmentService.addParticipant(enrollment, user);
		
		List<EnrollmentMemberInfo> participant = enrollmentService.getParticipants(enrollment);
		assertEquals(1, participant.size());
		
		enrollmentService.removeMember(participant.get(0).getId());
		
		Collection<EnrollmentMember> emptyParticipants = enrollmentService.getParticipants(enrollment);
		assertEquals(0, emptyParticipants.size());
	}
	

	private LectureBuilder createFacultyStructure(AccessType accessType) {
		LectureBuilder lectureBuilder = new LectureBuilder();
		User owner = testUtility.createUserInDB();

		lectureBuilder.createFaculty(owner)
			.addSubject()
			.addPeriod()
			.addEnrollment();
		
		Faculty faculty = lectureBuilder.getFaculty();
		lectureBuilder.getEnrollment().setAccessType(accessType);
		
		facultyDao.create(faculty);
		
		// define security settings
		securityService.createObjectIdentity(faculty, null);
		securityService.createObjectIdentity(lectureBuilder.getEnrollment(), faculty);
		
		commit();
		return lectureBuilder;
	}
	
	private void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}

	
	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}