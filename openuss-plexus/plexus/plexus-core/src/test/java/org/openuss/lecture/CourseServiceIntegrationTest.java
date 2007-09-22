// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.security.acl.Permission;

/**
 * JUnit Test for Spring Hibernate CourseService class.
 * @see org.openuss.lecture.CourseService
 */
public class CourseServiceIntegrationTest extends CourseServiceIntegrationTestBase {
	
	private InstituteDao instituteDao;
	
	private SecurityService securityService;

	private User user;

	private CourseInfo courseInfo;
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();

		LectureBuilder lectureBuilder = createInstituteStructure(AccessType.APPLICATION);
		
		user = testUtility.createUserInDB();
		Course course = lectureBuilder.getCourse();
		courseInfo = courseService.getCourseInfo(course.getId());
	}

	public void testAspirantToParticipant() {

		courseService.applyUser(courseInfo, user);
	
		List<CourseMemberInfo> aspirants = courseService.getAspirants(courseInfo);
		assertEquals(1, aspirants.size());
		
		courseService.acceptAspirant(aspirants.get(0).getId());

		Collection<CourseMember> emptyAspirants = courseService.getAspirants(courseInfo);
		assertEquals(0, emptyAspirants.size());
		
		List<CourseMemberInfo> participants = courseService.getParticipants(courseInfo);
		assertEquals(1, participants.size());
		
		commit();
		
		Permission permission = securityService.getPermissions(user, courseInfo);
		assertNotNull(permission);
		
		LectureAclEntry acl = new LectureAclEntry();
		acl.setMask(permission.getMask());
		
		assertTrue(acl.isPermitted(LectureAclEntry.COURSE_PARTICIPANT));
	}

	public void testRejectAspirant() {
		courseService.applyUser(courseInfo, user);
		
		List<CourseMemberInfo> aspirants = courseService.getAspirants(courseInfo);
		assertEquals(1, aspirants.size());
		
		courseService.rejectAspirant(aspirants.get(0).getId());
		
		Collection<CourseMember> emptyAspirants = courseService.getAspirants(courseInfo);
		assertEquals(0, emptyAspirants.size());
		
		List<CourseMemberInfo> emptyParticipants = courseService.getParticipants(courseInfo);
		assertEquals(0, emptyParticipants.size());
	}
	
	public void testAspirants() {
		courseService.addAspirant(courseInfo, user);
	
		List<CourseMemberInfo> aspirants = courseService.getAspirants(courseInfo);
		assertEquals(1, aspirants.size());
		
		courseService.removeMember(aspirants.get(0).getId());

		Collection<CourseMember> emptyAspirants = courseService.getAspirants(courseInfo);
		assertEquals(0, emptyAspirants.size());
	}

	public void testAssistants() {
		courseService.addAssistant(courseInfo, user);
		
		List<CourseMemberInfo> assistants = courseService.getAssistants(courseInfo);
		assertEquals(1, assistants.size());
		
		courseService.removeMember(assistants.get(0).getId());
		
		Collection<CourseMember> emptyAssistant = courseService.getAssistants(courseInfo);
		assertEquals(0, emptyAssistant.size());
	}
	
	public void testParticipant() {
		courseService.addParticipant(courseInfo, user);
		
		List<CourseMemberInfo> participant = courseService.getParticipants(courseInfo);
		assertEquals(1, participant.size());
		
		courseService.removeMember(participant.get(0).getId());
		
		Collection<CourseMember> emptyParticipants = courseService.getParticipants(courseInfo);
		assertEquals(0, emptyParticipants.size());
	}
	

	private LectureBuilder createInstituteStructure(AccessType accessType) {
		LectureBuilder lectureBuilder = new LectureBuilder();
		User owner = testUtility.createUserInDB();

		lectureBuilder.createInstitute(owner)
			.addCourseType()
			.addPeriod()
			.addCourse();
		
		Institute institute = lectureBuilder.getInstitute();
		lectureBuilder.getCourse().setAccessType(accessType);
		
		instituteDao.create(institute);
		
		// define security settings
		securityService.createObjectIdentity(institute, null);
		securityService.createObjectIdentity(lectureBuilder.getCourse(), institute);
		
		commit();
		return lectureBuilder;
	}
	
	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}