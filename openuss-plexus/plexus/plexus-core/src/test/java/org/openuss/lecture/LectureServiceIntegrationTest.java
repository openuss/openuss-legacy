// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.security.AuthorityDao;
import org.openuss.security.User;
import org.openuss.security.UserDao;

/**
 * JUnit Test for Spring Hibernate LectureService class.
 * 
 * @see org.openuss.lecture.LectureService
 */
public class LectureServiceIntegrationTest extends LectureServiceIntegrationTestBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LectureServiceIntegrationTest.class);

	private UserDao userDao;

	private AuthorityDao authorityDao;

	private User user;

	public LectureServiceIntegrationTest() {
		setDefaultRollback(false);
	}

	public void testUserDaoInjections() {
		assertNotNull(userDao);
		assertNotNull(authorityDao);
	}

	public void testAddSubjectToFaculty() throws LectureException{
		logger.debug("----> add subject <---- ");
		user = testUtility.createSecureContext();
		Faculty faculty = createFaculty();
		lectureService.createFaculty(faculty);

		commit();
		
		Subject subject = Subject.Factory.newInstance(unique("name"), unique("subject"));
		faculty = lectureService.add(faculty.getId(), subject);
		assertTrue(faculty.getSubjects().contains(subject));
		
		commit();
		
		assertNotNull(subject.getId());
		
		lectureService.removeFaculty(faculty.getId());
		setComplete();
	}
	
	
	public void testAddPeriodToFaculty() throws LectureException {
		logger.debug("add period to faculty");
		user = testUtility.createSecureContext();
		
		Faculty faculty = createFaculty();
		lectureService.createFaculty(faculty);
		
		commit();
		
		Period period = Period.Factory.newInstance();
		period.setName("WS06/07");
		period.setDescription("Wintersemester 2006 / 2007");
		
		faculty = lectureService.add(faculty.getId(), period);
		
		assertTrue(faculty.getPeriods().contains(period));
		commit();
		
		assertNotNull(period.getId());
		
		lectureService.removeFaculty(faculty.getId());
		
		setComplete();
	}
	
	private Faculty createFaculty() {
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setName(unique("faculty name"));
		faculty.setShortcut(unique("faculty"));
		faculty.setOwnername("ownername");
		faculty.setEmail("email@faculty");
		faculty.setOwner(user);
		return faculty;
	}
	
	private String unique(String name) {
		return name+" - "+System.currentTimeMillis();
	}
	

	public AuthorityDao getRoleDao() {
		return authorityDao;
	}
	
	public void setRoleDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
	
	public UserDao getUserDao() {
		return userDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}