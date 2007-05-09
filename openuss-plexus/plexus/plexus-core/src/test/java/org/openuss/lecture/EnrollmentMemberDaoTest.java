// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.security.User;


/**
 * JUnit Test for Spring Hibernate EnrollmentMemberDao class.
 * @see org.openuss.lecture.EnrollmentMemberDao
 */
public class EnrollmentMemberDaoTest extends EnrollmentMemberDaoTestBase {
	
	private FacultyDao facultyDao;
	
	private TestUtility testUtility;
	
	public void testEnrollmentMemberDaoCreate() {
		LectureBuilder lectureBuilder = new LectureBuilder();
		User owner = testUtility.createUserInDB();

		lectureBuilder.createFaculty(owner)
			.addSubject()
			.addPeriod()
			.addEnrollment();
		
		Faculty faculty = lectureBuilder.getFaculty();
		
		facultyDao.create(faculty);
		commit();
		
		User user = testUtility.createUserInDB();
		Enrollment enrollment = lectureBuilder.getEnrollment();
		
		EnrollmentMember emAspirant = createAspirant(user, enrollment);
		EnrollmentMember emAssistant = createAssistant(owner, enrollment);
		EnrollmentMember emParticipant = createParticipant(testUtility.createUserInDB(), enrollment);
		
		commit();
		
		Collection<EnrollmentMember> members = enrollmentMemberDao.findByEnrollment(enrollment);
		assertEquals(3, members.size());
		assertTrue(members.contains(emAssistant));
		assertTrue(members.contains(emAspirant));
		
		Collection<EnrollmentMember> assistants = enrollmentMemberDao.findByType(enrollment, EnrollmentMemberType.ASSISTANT);
		assertEquals(1, assistants.size());
		assertTrue(assistants.contains(emAssistant));
		
		Collection<EnrollmentMember> aspirants = enrollmentMemberDao.findByType(enrollment, EnrollmentMemberType.ASPIRANT);
		assertEquals(1, aspirants.size());
		assertTrue(aspirants.contains(emAspirant));
		
		Collection<EnrollmentMember> participants = enrollmentMemberDao.findByType(enrollment, EnrollmentMemberType.PARTICIPANT);
		assertEquals(1, participants.size());
		assertTrue(participants.contains(emParticipant));
		
		Collection<EnrollmentMember> memberships = enrollmentMemberDao.findByUser(user);
		assertEquals(1, memberships.size());
		
		List<EnrollmentMemberInfo> infos = enrollmentMemberDao.findByType(EnrollmentMemberDao.TRANSFORM_ENROLLMENTMEMBERINFO, enrollment, EnrollmentMemberType.ASSISTANT);
		assertEquals(1, infos.size());
		EnrollmentMemberInfo info = infos.get(0);
		assertEquals(enrollment.getId(), info.getEnrollmentId());
		assertEquals(owner.getId(), info.getUserId());
 	}
	
	private EnrollmentMember createParticipant(User user, Enrollment enrollment) {
		EnrollmentMember member = createEnrollmentMember(user, enrollment);
		member.setMemberType(EnrollmentMemberType.PARTICIPANT);
		enrollmentMemberDao.create(member);
		return member;
	}

	private EnrollmentMember createAssistant(User user, Enrollment enrollment) {
		EnrollmentMember member = createEnrollmentMember(user, enrollment);
		member.setMemberType(EnrollmentMemberType.ASSISTANT);
		enrollmentMemberDao.create(member);
		return member;
	}


	private EnrollmentMember createAspirant(User user, Enrollment enrollment) {
		EnrollmentMember member = createEnrollmentMember(user, enrollment);
		member.setMemberType(EnrollmentMemberType.ASPIRANT);
		enrollmentMemberDao.create(member);
		return member;
	}

	private EnrollmentMember createEnrollmentMember(User user, Enrollment enrollment) {
		EnrollmentMember member = EnrollmentMember.Factory.newInstance();
		member.setEnrollment(enrollment);
		member.setUser(user);
		return member;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}


	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}


	public FacultyDao getFacultyDao() {
		return facultyDao;
	}


	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}
}