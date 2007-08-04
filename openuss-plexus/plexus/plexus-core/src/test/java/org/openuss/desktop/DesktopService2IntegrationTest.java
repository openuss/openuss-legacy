// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.lecture.Course;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.Period;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate DesktopService2 class.
 * 
 * @see org.openuss.desktop.DesktopService2
 * @author Ron Haus
 */
public class DesktopService2IntegrationTest extends DesktopService2IntegrationTestBase {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DesktopService2IntegrationTest.class);

	private DesktopDao desktopDao;
	
	private UniversityDao universityDao;

	public UniversityDao getUniversityDao() {
		return universityDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}
	
	public DesktopDao getDesktopDao() {
		return desktopDao;
	}

	public void setDesktopDao(DesktopDao desktopDao) {
		this.desktopDao = desktopDao;
	}

	private Desktop createDesktop() {
		Desktop desktop = Desktop.Factory.newInstance();
		desktop.setUser(testUtility.createUserInDB());
		this.desktopDao.create(desktop);
		return desktop;
	}

	public void testFindDesktop() throws Exception {
		logger.info("----> BEGIN access to findDesktop test");

		// Create Desktop
		Desktop desktop = this.createDesktop();

		// Find Desktop
		DesktopInfo desktopInfo = this.getDesktopService2().findDesktop(desktop.getId());
		assertNotNull(desktopInfo);
		assertEquals(desktop.getId(), desktopInfo.getId());

		logger.info("----> END access to findDesktop test");
	}

	public void testFindDesktopByUser() throws Exception {
		logger.info("----> BEGIN access to findDesktopByUser test");

		// Create Desktop
		Desktop desktop = this.createDesktop();

		// Find Desktop
		DesktopInfo desktopInfo = this.getDesktopService2().findDesktopByUser(desktop.getUser().getId());
		assertNotNull(desktopInfo);
		assertEquals(desktop.getId(), desktopInfo.getId());

		logger.info("----> END access to findDesktopByUser test");
	}

	public void testCreateDesktop() throws Exception {
		logger.info("----> BEGIN access to createDesktop test");

		// Create User
		User user = testUtility.createUniqueUserInDB();

		// Create Desktop
		Long desktopId = this.getDesktopService2().createDesktop(user.getId());
		assertNotNull(desktopId);

		// Synchronize with Database
		flush();

		// Find just created Desktop
		Desktop desktop = this.getDesktopDao().load(desktopId);
		assertNotNull(desktop);
		assertEquals(desktopId, desktop.getId());

		logger.info("----> END access to createDesktop test");
	}

	@SuppressWarnings( { "unchecked" })
	public void testUpdateDesktop() throws Exception {
		logger.info("----> BEGIN access to updateDesktop test");

		// Create Desktop
		Desktop desktop = this.createDesktop();

		// Create DesktopInfo
		DesktopInfo desktopInfo = this.getDesktopDao().toDesktopInfo(desktop);
		University university = testUtility.createUniqueUniversityInDB();
		desktopInfo.setUniversityInfos(new ArrayList());
		desktopInfo.getUniversityInfos().add(this.getUniversityDao().toUniversityInfo(university));

		// Update Desktop
		this.getDesktopService2().updateDesktop(desktopInfo);

		// Synchronize with Database
		flush();

		assertNotNull(desktop.getUniversities());
		assertTrue(desktop.getUniversities().contains(university));

		logger.info("----> END access to updateDesktop test");
	}

	public void testLinkUniversity() throws Exception {
		logger.info("----> BEGIN access to linkUniversity test");

		// Create Desktop
		Desktop desktop = this.createDesktop();
		int sizeBefore = desktop.getUniversities().size();

		// Create University
		University university = testUtility.createUniqueUniversityInDB();

		// Link
		this.getDesktopService2().linkUniversity(desktop.getId(), university.getId());

		// Synchronize with Database
		flush();

		// Test
		assertEquals(sizeBefore + 1, desktop.getUniversities().size());
		assertTrue(desktop.getUniversities().contains(university));

		logger.info("----> END access to linkUniversity test");
	}

	public void testLinkDepartment() throws Exception {
		logger.info("----> BEGIN access to linkDepartment test");

		// Create Desktop
		Desktop desktop = this.createDesktop();
		int sizeBefore = desktop.getDepartments().size();

		// Create Department
		Department department = testUtility.createUniqueDepartmentInDB();

		// Link
		this.getDesktopService2().linkDepartment(desktop.getId(), department.getId());

		// Synchronize with Database
		flush();

		// Test
		assertEquals(sizeBefore + 1, desktop.getDepartments().size());
		assertTrue(desktop.getDepartments().contains(department));

		logger.info("----> END access to linkDepartment test");
	}

	public void testLinkInstitute() throws Exception {
		logger.info("----> BEGIN access to linkInstitute test");

		// TODO Implement me!

		logger.info("----> END access to linkInstitute test");
	}

	public void testLinkCourseType() throws Exception {
		logger.info("----> BEGIN access to linkCourseType test");

		// TODO Implement me!

		logger.info("----> END access to linkCourseType test");
	}

	public void testLinkCourse() throws Exception {
		logger.info("----> BEGIN access to unlinkCourse test");

		// TODO Implement me!

		logger.info("----> END access to unlinkCourse test");
	}

	public void testUnlinkUniversity() throws Exception {
		logger.info("----> BEGIN access to unlinkUniversity test");

		// Create Desktop
		Desktop desktop = this.createDesktop();

		// Create University
		University university = testUtility.createUniqueUniversityInDB();

		// Link
		desktop.getUniversities().add(university);
		int sizeBefore = desktop.getUniversities().size();

		// Synchronize with Database
		flush();

		// Unlink
		this.getDesktopService2().unlinkUniversity(desktop.getId(), university.getId());

		// Test
		assertEquals(sizeBefore - 1, desktop.getUniversities().size());
		assertFalse(desktop.getUniversities().contains(university));

		logger.info("----> END access to unlinkUniversity test");
	}

	public void testUnlinkDepartment() throws Exception {
		logger.info("----> BEGIN access to unlinkDepartment test");

		// Create Desktop
		Desktop desktop = this.createDesktop();
		
		// Create Department
		Department department = testUtility.createUniqueDepartmentInDB();

		// Link
		desktop.getDepartments().add(department);
		int sizeBefore = desktop.getDepartments().size();
		
		// Synchronize with Database
		flush();
		
		// Unlink
		this.getDesktopService2().unlinkDepartment(desktop.getId(), department.getId());

		// Test
		assertEquals(sizeBefore - 1, desktop.getDepartments().size());
		assertFalse(desktop.getDepartments().contains(department));

		logger.info("----> END access to unlinkDepartment test");
	}

	public void testUnlinkInstitute() throws Exception {
		logger.info("----> BEGIN access to unlinkInstitute test");

		// TODO Implement me!

		logger.info("----> END access to unlinkInstitute test");
	}

	public void testUnlinkCourseType() throws Exception {
		logger.info("----> BEGIN access to unlinkCourseType test");

		// TODO Implement me!

		logger.info("----> END access to unlinkCourseType test");
	}

	public void testUnlinkCourse() throws Exception {
		logger.info("----> BEGIN access to unlinkCourse test");

		// TODO Implement me!

		logger.info("----> END access to unlinkCourse test");
	}
	
	@SuppressWarnings( { "unchecked" })
	public void testFindLinkedDepartmentsByUserAndUniversity () {
		logger.info("----> BEGIN access to findLinkedDepartmentsByUserAndUniversity(userId, universityId) test");
		
		//Create Universities
		University university1 = testUtility.createUniqueUniversityInDB();
		University university2 = testUtility.createUniqueUniversityInDB();
		
		//Create Desktop
		Desktop desktop = this.createDesktop();
		
		//Create Departments
		Department department1 = testUtility.createUniqueDepartmentInDB();
		department1.setUniversity(university1);
		
		Department department2 = testUtility.createUniqueDepartmentInDB();
		department2.setUniversity(university2);
		
		Department department3 = testUtility.createUniqueDepartmentInDB();
		department3.setUniversity(university1);
		
		//Link
		desktop.getDepartments().add(department1);
		desktop.getDepartments().add(department2);
		desktop.getDepartments().add(department3);
		flush();
		
		//Test
		try {
			List<DepartmentInfo> linkedDepartments = 
				this.getDesktopService2().findLinkedDepartmentsByUserAndUniversity(desktop.getUser().getId(), university1.getId());
			assertNotNull(linkedDepartments);
			assertEquals(2, linkedDepartments.size());
			
		} catch (DesktopException dexc) {}
		
		logger.info("----> END access to findLinkedDepartmentsByUserAndUniversity(userId, universityId) test");
	}
	
	@SuppressWarnings( { "unchecked" })
	public void testFindAdditionalDepartmentsByUserAndUniversity () {
		logger.info("----> BEGIN access to findLinkedInstitutesByUserAndUniversity(userId, universityId) test");
		
		//Create Courses
		Course course1 = testUtility.createUniqueCourseInDB();
		Period period1 = testUtility.createUniquePeriodInDB();
		course1.setPeriod(period1);
		//course1.getPeriod().getInstitute().setActivePeriod(period1);
		
		Course course2 = testUtility.createUniqueCourseInDB();
		Period period2 = testUtility.createUniquePeriodInDB();
		course2.setPeriod(period2);
		
		Course course3 = testUtility.createUniqueCourseInDB();
		Period period3 = testUtility.createUniquePeriodInDB();
		course3.setPeriod(period3);
		
		logger.info("----> END access to findLinkedInstitutesByUserAndUniversity(userId, universityId) test");
	}
	
	@SuppressWarnings( { "unchecked" })
	public void testFindLinkedInstitutesByUserAndUniversity () {
		logger.info("----> BEGIN access to findLinkedInstitutesByUserAndUniversity(userId, universityId) test");
		
		
		logger.info("----> END access to findLinkedInstitutesByUserAndUniversity(userId, universityId) test");
	}
	
	public void testUnlinkAllFromUniversity() throws Exception {
		logger.info("----> BEGIN access to unlinkAllFromUniversity test");

		// Create two Desktops
		Desktop desktop = this.createDesktop();
		Desktop desktop2 = this.createDesktop();

		// Create two Universities
		University university = testUtility.createUniqueUniversityInDB();
		University university2 = testUtility.createUniqueUniversityInDB();

		// Link
		desktop.getUniversities().add(university);
		desktop.getUniversities().add(university2);
		int sizeBefore = desktop.getUniversities().size();
		desktop2.getUniversities().add(university);
		desktop2.getUniversities().add(university2);
		int sizeBefore2 = desktop2.getUniversities().size();

		// Synchronize with Database
		flush();

		// Unlink
		this.getDesktopService2().unlinkAllFromUniversity(university.getId());

		// Test
		assertEquals(sizeBefore - 1, desktop.getUniversities().size());
		assertEquals(sizeBefore2 - 1, desktop2.getUniversities().size());
		assertFalse(desktop.getUniversities().contains(university));
		assertFalse(desktop2.getUniversities().contains(university));

		logger.info("----> END access to unlinkAllFromUniversity test");
	}

	public void testUnlinkAllFromDepartment() throws Exception {
		logger.info("----> BEGIN access to unlinkAllFromDepartment test");

		// Create two Desktops
		Desktop desktop = this.createDesktop();
		Desktop desktop2 = this.createDesktop();

		// Create two Departments
		Department department = testUtility.createUniqueDepartmentInDB();
		Department department2 = testUtility.createUniqueDepartmentInDB();

		// Link
		desktop.getDepartments().add(department);
		desktop.getDepartments().add(department2);
		int sizeBefore = desktop.getDepartments().size();
		desktop2.getDepartments().add(department);
		desktop2.getDepartments().add(department2);
		int sizeBefore2 = desktop2.getDepartments().size();

		// Synchronize with Database
		flush();

		// Unlink
		this.getDesktopService2().unlinkAllFromDepartment(department.getId());

		// Test
		assertEquals(sizeBefore - 1, desktop.getDepartments().size());
		assertEquals(sizeBefore2 - 1, desktop2.getDepartments().size());
		assertFalse(desktop.getDepartments().contains(department));
		assertFalse(desktop2.getDepartments().contains(department));

		logger.info("----> END access to unlinkAllFromDepartment test");
	}

	public void testUnlinkAllFromInstitute() throws Exception {
		logger.info("----> BEGIN access to unlinkAllFromInstitute test");

		// TODO Implement me!

		logger.info("----> END access to unlinkAllFromInstitute test");
	}

	public void testUnlinkAllFromCourseType() throws Exception {
		logger.info("----> BEGIN access to unlinkAllFromCourseType test");

		// TODO Implement me!

		logger.info("----> END access to unlinkAllFromCourseType test");
	}

	public void testUnlinkAllFromCourse() throws Exception {
		logger.info("----> BEGIN access to unlinkAllFromCourse test");

		// TODO Implement me!

		logger.info("----> END access to unlinkAllFromCourse test");
	}

}