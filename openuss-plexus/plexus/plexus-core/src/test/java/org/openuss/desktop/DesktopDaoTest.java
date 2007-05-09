// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.Collection;

import org.openuss.TestUtility;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.FacultyDao;
import org.openuss.lecture.LectureBuilder;
import org.openuss.security.User;


/**
 * JUnit Test for Spring Hibernate DesktopDao class.
 * @see org.openuss.desktop.DesktopDao
 */
public class DesktopDaoTest extends DesktopDaoTestBase {
	
	private User defaultUser;
	
	private TestUtility testUtility;
	
	private FacultyDao facultyDao;
	
	private LectureBuilder lectureBuilder;
	
	public void testDesktopDaoCreate() {
		Desktop desktop = createDesktop();
		assertNotNull(desktop.getId());
		desktopDao.remove(desktop);
	}
	
	
	public void testFindByEnrollment() {
		// create enrollments
		Enrollment enrollment = lectureBuilder
			.createFaculty(testUtility.createDefaultUserInDB())
			.addPeriod()
			.addSubject()
			.addEnrollment()
			.persist()
			.getEnrollment();
		commit();
		
		// create desktops
		Desktop desktopOne = createDesktop();
		Desktop desktopTwo = createDesktop();
		Desktop desktopThree = createDesktop();
		

		// create desktop - enrollment link
		desktopTwo.linkEnrollment(enrollment);
		desktopDao.update(desktopTwo);
		commit();
		
		assertNotNull(enrollment.getId());
		
		// test findByEnrollment
		Collection desktops = desktopDao.findByEnrollment(enrollment);
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

	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}

	public LectureBuilder getLectureBuilder() {
		return lectureBuilder;
	}

	public void setLectureBuilder(LectureBuilder lectureBuilder) {
		this.lectureBuilder = lectureBuilder;
	}
	
}