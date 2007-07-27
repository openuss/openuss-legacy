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
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.security.Membership;
import org.openuss.security.User;


/**
 * JUnit Test for Spring Hibernate DesktopDao class.
 * @see org.openuss.desktop.DesktopDao
 */
public class DesktopDaoTest extends DesktopDaoTestBase {
	
	private User defaultUser;
	
	private TestUtility testUtility;
	
	private InstituteDao instituteDao;
	
	private UniversityDao universityDao;
	
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
	
	public UniversityDao getUniversityDao() {
		return universityDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}

	public LectureBuilder getLectureBuilder() {
		return lectureBuilder;
	}

	public void setLectureBuilder(LectureBuilder lectureBuilder) {
		this.lectureBuilder = lectureBuilder;
	}
	
	public void testDesktopDaoToDesktopInfo() {
		
		// Create a complete Desktop
		Desktop desktop = Desktop.Factory.newInstance();
		
		desktop.setUser(testUtility.createUserInDB());
		
		University university = University.Factory.newInstance();
		university.setName(testUtility.unique("testUniversity"));
		university.setShortcut(testUtility.unique("testU"));
		desktop.getUniversities().add(university);

		University university2 = University.Factory.newInstance();
		university2.setName(testUtility.unique("testUniversity"));
		university2.setShortcut(testUtility.unique("testU"));
		desktop.getUniversities().add(university2);
		
		desktopDao.create(desktop);
		
		// Test ValueObject
		DesktopInfo desktopInfo = desktopDao.toDesktopInfo(desktop);
		
		assertEquals(desktop.getId(), desktopInfo.getId());
		assertEquals(desktop.getUser().getId(), desktopInfo.getUserId());
		assertEquals(desktop.getUniversities().get(0).getId(), (Long) desktopInfo.getUniversityIds().get(0));
		assertEquals(desktop.getUniversities().get(1).getId(), (Long) desktopInfo.getUniversityIds().get(1));
	}
	
	public void testDesktopDaoDesktopInfoToEntity() {
		
		// Create a complete Desktop
		Desktop desktop = Desktop.Factory.newInstance();
		
		desktop.setUser(testUtility.createUserInDB());
		
		University university = University.Factory.newInstance();
		university.setName(testUtility.unique("testUniversity"));
		university.setShortcut(testUtility.unique("testU"));
		university.setDescription("This is a test University");
		Membership membership = Membership.Factory.newInstance();
		membership.getMembers().add(testUtility.createUserInDB());
		university.setMembership(membership);
		desktop.getUniversities().add(university);

		University university2 = University.Factory.newInstance();
		university2.setName(testUtility.unique("testUniversity2"));
		university2.setShortcut(testUtility.unique("testU2"));
		university2.setDescription("This is a test University2");
		Membership membership2 = Membership.Factory.newInstance();
		membership2.getMembers().add(testUtility.createUserInDB());
		university2.setMembership(membership2);
		desktop.getUniversities().add(university2);
		
		universityDao.create(university);
		universityDao.create(university2);
		
		desktopDao.create(desktop);
		
		// Create the corresponding ValueObject
		DesktopInfo desktopInfo = new DesktopInfo();
		desktopInfo.setId(desktop.getId());
		
		// Test toEntity
		Desktop desktop2 = desktopDao.desktopInfoToEntity(desktopInfo);

		assertEquals(desktop.getId(), desktop2.getId());
		assertEquals(desktop.getUser().getId(), desktop2.getUser().getId());
		assertEquals(desktop.getUniversities().get(0).getId(), desktop2.getUniversities().get(0).getId());
		assertEquals(desktop.getUniversities().get(1).getId(), desktop2.getUniversities().get(1).getId());
		
	}
	
}