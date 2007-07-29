// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.lecture.Course;
import org.openuss.lecture.Department;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.LectureBuilder;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.security.Membership;


/**
 * JUnit Test for Spring Hibernate DesktopDao class.
 * @see org.openuss.desktop.DesktopDao
 * @author Ingo Dueppe, Ron Haus
 */
public class DesktopDaoTest extends DesktopDaoTestBase {
	
	private TestUtility testUtility;
	
	private InstituteDao instituteDao;
	
	private UniversityDao universityDao;
	
	private LectureBuilder lectureBuilder;

	
	
	
	public void testDesktopDaoCreate() {
		Desktop desktop = createDesktop();
		assertNotNull(desktop.getId());
		
		//Synchronize with Database
		flush();
	}
	
	@SuppressWarnings( { "unchecked" })
	public void testFindByUniversity() {
		
		//Create a default University
		University university = testUtility.createUniqueUniversityInDB();
		assertNotNull(university.getId());
		
		//Create 3 Desktops
		List<Desktop> desktops = new ArrayList<Desktop>(3);
		for(int i = 0; i<3; i++) {
			desktops.add(createDesktop());
		}
		
		//Create Desktop-University Link for 2 Desktops
		for(int i = 0; i<desktops.size()-1; i++) {
			desktops.get(i).getUniversities().add(university);
		}
		
		//Synchronize with Database
		flush();
		
		//Test findByUniversity
		Collection desktops2 = desktopDao.findByUniversity(university);
		assertEquals(2, desktops2.size());
	}

	@SuppressWarnings( { "unchecked" })
	public void testFindByDepartment() {
		
		
		//Create a default Department
		Department department = testUtility.createUniqueDepartmentInDB();
		assertNotNull(department.getId());
		
		//Create 3 Desktops
		List<Desktop> desktops = new ArrayList<Desktop>(3);
		for(int i = 0; i<3; i++) {
			desktops.add(createDesktop());
		}
		
		//Create Desktop-Department Link for 2 Desktops
		for(int i = 0; i<desktops.size()-1; i++) {
			desktops.get(i).getDepartments().add(department);
		}
		
		//Synchronize with Database
		flush();
		
		//Test findByDepartment
		Collection desktops2 = desktopDao.findByDepartment(department);
		assertEquals(2, desktops2.size());
	}
	
	@SuppressWarnings( { "unchecked" })
	public void testFindByInstitute() {
		
	}
	
	@SuppressWarnings( { "unchecked" })
	public void testFindByCourseType() {
		
	}
	
	@SuppressWarnings( { "unchecked" })
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
		removeDesktopAndUser(desktopThree);
		removeDesktopAndUser(desktopTwo);
		removeDesktopAndUser(desktopOne);
		commit();
		
		lectureBuilder.remove();
		testUtility.removeUser();
		commit();
	}

	
	public void testDesktopDaoToDesktopInfo() {
		
		// Create a complete Desktop
		Desktop desktop = Desktop.Factory.newInstance();
		
		desktop.setUser(testUtility.createUniqueUserInDB());
		
		Department department = testUtility.createUniqueDepartmentInDB();
		Department department2 = testUtility.createUniqueDepartmentInDB();
		desktop.getUniversities().add(department.getUniversity());
		desktop.getUniversities().add(department2.getUniversity());
		desktop.getDepartments().add(department);
		desktop.getDepartments().add(department2);
		
		desktopDao.create(desktop);
		
		//Synchronize with Database
		flush();		
		
		// Test ValueObject
		DesktopInfo desktopInfo = desktopDao.toDesktopInfo(desktop);
		
		assertEquals(desktop.getId(), desktopInfo.getId());
		assertEquals(desktop.getUser().getId(), desktopInfo.getUserId());
		assertEquals(desktop.getUniversities().get(0).getId(), (Long) desktopInfo.getUniversityIds().get(0));
		assertEquals(desktop.getUniversities().get(1).getId(), (Long) desktopInfo.getUniversityIds().get(1));
		assertEquals(desktop.getDepartments().get(0).getId(), (Long) desktopInfo.getDepartmentIds().get(0));
		assertEquals(desktop.getDepartments().get(1).getId(), (Long) desktopInfo.getDepartmentIds().get(1));
	}
	
	@SuppressWarnings( { "unchecked" })
	public void testDesktopDaoDesktopInfoToEntity() {
		
		// Create a complete Desktop
		Desktop desktop = Desktop.Factory.newInstance();
		
		desktop.setUser(testUtility.createUniqueUserInDB());
		
		Department department = testUtility.createUniqueDepartmentInDB();
		Department department2 = testUtility.createUniqueDepartmentInDB();
		desktop.getUniversities().add(department.getUniversity());
		desktop.getUniversities().add(department2.getUniversity());
		desktop.getDepartments().add(department);
		desktop.getDepartments().add(department2);
		
		desktopDao.create(desktop);
		
		//Synchronize with Database
		flush();
		
		// Create the corresponding ValueObject
		DesktopInfo desktopInfo = new DesktopInfo();
		desktopInfo.setId(desktop.getId());
		
		// Test toEntity
		Desktop desktop2 = desktopDao.desktopInfoToEntity(desktopInfo);

		assertEquals(desktop.getId(), desktop2.getId());
		assertEquals(desktop.getUser().getId(), desktop2.getUser().getId());
		assertEquals(desktop.getUniversities().get(0).getId(), desktop2.getUniversities().get(0).getId());
		assertEquals(desktop.getUniversities().get(1).getId(), desktop2.getUniversities().get(1).getId());
		assertEquals(desktop.getDepartments().get(0).getId(), desktop2.getDepartments().get(0).getId());
		assertEquals(desktop.getDepartments().get(1).getId(), desktop2.getDepartments().get(1).getId());
		
		// Create a new ValueObject
		DesktopInfo desktopInfo2 = new DesktopInfo();
		desktopInfo2.setUserId(testUtility.createUniqueUserInDB().getId());
		List universityIds = new ArrayList();
		universityIds.add(department.getUniversity().getId());
		universityIds.add(department2.getUniversity().getId());
		desktopInfo2.setUniversityIds(universityIds);
		List departmentIds = new ArrayList();
		departmentIds.add(department.getId());
		departmentIds.add(department2.getId());
		desktopInfo2.setDepartmentIds(departmentIds);
		
		// Test toEntity
		Desktop desktop3 = desktopDao.desktopInfoToEntity(desktopInfo2);

		assertEquals(desktopInfo2.getUserId(), desktop3.getUser().getId());
		assertEquals(desktopInfo2.getUniversityIds().get(0), desktop3.getUniversities().get(0).getId());
		assertEquals(desktopInfo2.getUniversityIds().get(1), desktop3.getUniversities().get(1).getId());
		assertEquals(desktopInfo2.getDepartmentIds().get(0), desktop3.getDepartments().get(0).getId());
		assertEquals(desktopInfo2.getDepartmentIds().get(1), desktop3.getDepartments().get(1).getId());
		
	}
	
	
	
	private void removeDesktopAndUser(Desktop desktop) {
		desktopDao.remove(desktop);
		testUtility.removeUser(desktop.getUser());
	}

	private Desktop createDesktop() {
		Desktop desktopOne = Desktop.Factory.newInstance();
		desktopOne.setUser(testUtility.createUserInDB());
		desktopDao.create(desktopOne);
		return desktopOne;
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
}