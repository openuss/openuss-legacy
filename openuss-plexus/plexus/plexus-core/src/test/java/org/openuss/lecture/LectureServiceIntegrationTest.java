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

	public void testAddCourseTypeToInstitute() throws LectureException{
		logger.debug("----> add courseType <---- ");
		user = testUtility.createSecureContext();
		Institute institute = createInstitute();
		lectureService.createInstitute(institute);

		commit();
		
		CourseType courseType = CourseType.Factory.newInstance(unique("name"), unique("courseType"));
		institute = lectureService.add(institute.getId(), courseType);
		assertTrue(institute.getCourseTypes().contains(courseType));
		
		commit();
		
		assertNotNull(courseType.getId());
		
		lectureService.removeInstitute(institute.getId());
		setComplete();
	}
	
	
	public void testAddPeriodToInstitute() throws LectureException {
		logger.debug("add period to institute");
		user = testUtility.createSecureContext();
		
		Institute institute = createInstitute();
		lectureService.createInstitute(institute);
		
		commit();
		
		Period period = Period.Factory.newInstance();
		period.setName("WS06/07");
		period.setDescription("Wintersemester 2006 / 2007");
		
		institute = lectureService.add(institute.getId(), period);
		
		//assertTrue(institute.getPeriods().contains(period));
		commit();
		
		assertNotNull(period.getId());
		
		lectureService.removeInstitute(institute.getId());
		
		setComplete();
	}
	
	private Institute createInstitute() {
		Institute institute = Institute.Factory.newInstance();
		institute.setName(unique("institute name"));
		institute.setShortcut(unique("institute"));
		institute.setOwnerName("ownername");
		institute.setEmail("email@institute");
		//institute.setOwner(user);
		institute.setLocale("de");
		return institute;
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