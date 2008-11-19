package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.security.User;


/**
 * JUnit Test for Spring Hibernate CourseMemberDao class.
 * @see org.openuss.lecture.CourseMemberDao
 */
public class CourseMemberDaoTest extends CourseMemberDaoTestBase {
	
	private InstituteDao instituteDao;
	
	private TestUtility testUtility;
	
	public void testCourseMemberDaoCreate() {
		
		User owner = testUtility.createUniqueUserInDB();
		flush();
		
		User user = testUtility.createUniqueUserInDB();//createUserInDB();
		Course course = testUtility.createUniqueCourseInDB();//lectureBuilder.getCourse();
		
		CourseMember emAspirant = createAspirant(user, course);
		CourseMember emAssistant = createAssistant(owner, course);
		CourseMember emParticipant = createParticipant(testUtility.createUniqueUserInDB(), course);
		
		flush();
		
		Collection<CourseMember> members = courseMemberDao.findByCourse(course);
		assertEquals(3, members.size());
		assertTrue(members.contains(emAssistant));
		assertTrue(members.contains(emAspirant));
		
		Collection<CourseMember> assistants = courseMemberDao.findByType(course, CourseMemberType.ASSISTANT);
		assertEquals(1, assistants.size());
		assertTrue(assistants.contains(emAssistant));
		
		Collection<CourseMember> aspirants = courseMemberDao.findByType(course, CourseMemberType.ASPIRANT);
		assertEquals(1, aspirants.size());
		assertTrue(aspirants.contains(emAspirant));
		
		Collection<CourseMember> participants = courseMemberDao.findByType(course, CourseMemberType.PARTICIPANT);
		assertEquals(1, participants.size());
		assertTrue(participants.contains(emParticipant));
		
		Collection<CourseMember> memberships = courseMemberDao.findByUser(user);
		assertEquals(1, memberships.size());
		
		List<CourseMemberInfo> infos = courseMemberDao.findByType(CourseMemberDao.TRANSFORM_COURSEMEMBERINFO, course, CourseMemberType.ASSISTANT);
		assertEquals(1, infos.size());
		CourseMemberInfo info = infos.get(0);
		assertEquals(course.getId(), info.getCourseId());
		assertEquals(owner.getId(), info.getUserId());
 	}
	
	private CourseMember createParticipant(User user, Course course) {
		CourseMember member = createCourseMember(user, course);
		member.setMemberType(CourseMemberType.PARTICIPANT);
		courseMemberDao.create(member);
		return member;
	}

	private CourseMember createAssistant(User user, Course course) {
		CourseMember member = createCourseMember(user, course);
		member.setMemberType(CourseMemberType.ASSISTANT);
		courseMemberDao.create(member);
		return member;
	}


	private CourseMember createAspirant(User user, Course course) {
		CourseMember member = createCourseMember(user, course);
		member.setMemberType(CourseMemberType.ASPIRANT);
		courseMemberDao.create(member);
		return member;
	}

	private CourseMember createCourseMember(User user, Course course) {
		CourseMember member = new CourseMemberImpl();
		
		CourseMemberPK pk = new CourseMemberPK();
				
		pk.setCourse(course);
		pk.setUser(user);
		member.setCourseMemberPk(pk);
		return member;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}


	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}


	public InstituteDao getInstituteDao() {
		return instituteDao;
	}


	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}
}