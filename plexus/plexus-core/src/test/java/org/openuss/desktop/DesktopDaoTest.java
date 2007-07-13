// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.Collection;

import org.openuss.TestUtility;
import org.openuss.lecture.Course;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.LectureBuilder;
import org.openuss.security.User;


/**
 * JUnit Test for Spring Hibernate DesktopDao class.
 * @see org.openuss.desktop.DesktopDao
 */
public class DesktopDaoTest extends DesktopDaoTestBase {
	
	private User defaultUser;
	
	private TestUtility testUtility;
	
	private InstituteDao instituteDao;
	
	private LectureBuilder lectureBuilder;
	
	public void testDesktopDaoCreate() {
		Desktop desktop = createDesktop();
		assertNotNull(desktop.getId());
		desktopDao.remove(desktop);
	}
	
	
	public void testFindByCourse() {
		// create courses
		Course course = lectureBuilder
			.createInstitute(testUtility.createDefaultUserInDB())
			.addPeriod()
			.addCourseType()
			.addCourse()
			.persist()
			.getCourse();
		commit();
		
		// create desktops
		Desktop desktopOne = createDesktop();
		Desktop desktopTwo = createDesktop();
		Desktop desktopThree = createDesktop();
		

		// create desktop - course link
		desktopTwo.linkCourse(course);
		desktopDao.update(desktopTwo);
		commit();
		
		assertNotNull(course.getId());
		
		// test findByCourse
		Collection<Desktop> desktops = desktopDao.findByCourse(course);
		assertEquals(1, desktops.size());
		assertEquals(desktopTwo,desktops.iterator().next());
		commit();
		
		// clean up
		removeDesktop(desktopThree);
		removeDesktop(desktopTwo);
		removeDesktop(desktopOne);
		commit();
		
		lectureBuilder.remove();
		testUtility.removeUser();
		commit();
	}
	
	private void removeDesktop(Desktop desktop) {
		desktopDao.remove(desktop);
		testUtility.removeUser(desktop.getUser());
	}

	private Desktop createDesktop() {
		Desktop desktopOne = Desktop.Factory.newInstance();
		desktopOne.setUser(testUtility.createUserInDB());
		desktopDao.create(desktopOne);
		return desktopOne;
	}

	public User getDefaultUser() {
		return defaultUser;
	}

	public void setDefaultUser(User defaultUser) {
		this.defaultUser = defaultUser;
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

	public LectureBuilder getLectureBuilder() {
		return lectureBuilder;
	}

	public void setLectureBuilder(LectureBuilder lectureBuilder) {
		this.lectureBuilder = lectureBuilder;
	}
	
}