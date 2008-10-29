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
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.lecture.UniversityInfo;
import org.openuss.security.UserDao;


/**
 * JUnit Test for Spring Hibernate DesktopDao class.
 * @see org.openuss.desktop.DesktopDao
 * @author Ingo Dueppe, Ron Haus
 */
public class DesktopDaoTest extends DesktopDaoTestBase {
	
	private TestUtility testUtility;
	
	private UserDao userDao;
	
	private UniversityDao universityDao;
	
	private DepartmentDao departmentDao;
	
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
		
		List<Desktop> allDesktops = (List<Desktop>)this.getDesktopDao().loadAll();
		List<Desktop> desktopsByUni = new ArrayList<Desktop>();
		for (Desktop desktop : allDesktops) {
			if (desktop.getUniversities().contains(university)) {
				desktopsByUni.add(desktop);
			}
		}
		int sizeOfDesktops = desktopsByUni.size();
		
		//Create 3 Desktops
		List<Desktop> desktops = new ArrayList<Desktop>(3);
		for(int i = 0; i<3; i++) {
			desktops.add(createDesktop());
		}
		
		//Create Desktop-University Link for 2 Desktops
		for(int i = 0; i<desktops.size()-1; i++) {
			desktops.get(i).getUniversities().add(university);
			sizeOfDesktops++;
		}
		
		//Synchronize with Database
		flush();
		
		//Test findByUniversity
		Collection desktops2 = desktopDao.findByUniversity(university);
		assertEquals(sizeOfDesktops, desktops2.size());
	}

	@SuppressWarnings( { "unchecked" })
	public void testFindByDepartment() {
		
		//Create a default Department
		Department department = testUtility.createUniqueDepartmentInDB();
		assertNotNull(department.getId());
		
		List<Desktop> allDesktops = (List<Desktop>)this.getDesktopDao().loadAll();
		List<Desktop> desktopsByDep = new ArrayList<Desktop>();
		for (Desktop desktop : allDesktops) {
			if (desktop.getDepartments().contains(department)) {
				desktopsByDep.add(desktop);
			}
		}
		int sizeOfDesktops = desktopsByDep.size();
		
		//Create 3 Desktops
		List<Desktop> desktops = new ArrayList<Desktop>(3);
		for(int i = 0; i<3; i++) {
			desktops.add(createDesktop());
		}
		
		//Create Desktop-Department Link for 2 Desktops
		for(int i = 0; i<desktops.size()-1; i++) {
			desktops.get(i).getDepartments().add(department);
			sizeOfDesktops++;
		}
		
		//Synchronize with Database
		flush();
		
		//Test findByDepartment
		Collection desktops2 = desktopDao.findByDepartment(department);
		assertEquals(sizeOfDesktops, desktops2.size());
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
		Course course = testUtility.createUniqueCourseInDB();
		
		// create desktops
		Desktop desktopOne = createDesktop();
		Desktop desktopTwo = createDesktop();
		Desktop desktopThree = createDesktop();
		

		// create desktop - course link
		desktopTwo.linkCourse(course);
		desktopDao.update(desktopTwo);
		flush();
		
		assertNotNull(course.getId());
		
		// test findByCourse
		Collection<Desktop> desktops = desktopDao.findByCourse(course);
		assertEquals(1, desktops.size());
		assertEquals(desktopTwo,desktops.iterator().next());
		flush();
		
		// clean up
		removeDesktopAndUser(desktopThree);
		removeDesktopAndUser(desktopTwo);
		removeDesktopAndUser(desktopOne);
		flush();
	}

	
	public void testDesktopDaoToDesktopInfo() {
		
		// Create a complete Desktop
		Desktop desktop = new DesktopImpl();
		
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
		assertEquals(desktop.getUser().getId(), desktopInfo.getUserInfo().getId());
		assertEquals(desktop.getUniversities().get(0).getId(), ((UniversityInfo) desktopInfo.getUniversityInfos().get(0)).getId());
		assertEquals(desktop.getUniversities().get(1).getId(), ((UniversityInfo) desktopInfo.getUniversityInfos().get(1)).getId());
		assertEquals(desktop.getDepartments().get(0).getId(), ((DepartmentInfo) desktopInfo.getDepartmentInfos().get(0)).getId());
		assertEquals(desktop.getDepartments().get(1).getId(), ((DepartmentInfo) desktopInfo.getDepartmentInfos().get(1)).getId());
		//TODO Test also Institutes, CourseTypes and Courses
	}
	
	@SuppressWarnings( { "unchecked" })
	public void testDesktopDaoDesktopInfoToEntity() {
		
		// Create a complete Desktop
		Desktop desktop = new DesktopImpl();
		
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
		desktopInfo2.setUserInfo(this.userDao.toUserInfo(testUtility.createUniqueUserInDB()));
		List universities = new ArrayList();
		universities.add(this.universityDao.toUniversityInfo(department.getUniversity()));
		universities.add(this.universityDao.toUniversityInfo(department2.getUniversity()));
		desktopInfo2.setUniversityInfos(universities);
		List departments = new ArrayList();
		departments.add(this.departmentDao.toDepartmentInfo(department));
		departments.add(this.departmentDao.toDepartmentInfo(department2));
		desktopInfo2.setDepartmentInfos(departments);
		
		// Test toEntity
		Desktop desktop3 = desktopDao.desktopInfoToEntity(desktopInfo2);

		assertEquals(desktopInfo2.getUserInfo().getId(), desktop3.getUser().getId());
		assertEquals(((UniversityInfo) desktopInfo2.getUniversityInfos().get(0)).getId(), desktop3.getUniversities().get(0).getId());
		assertEquals(((UniversityInfo) desktopInfo2.getUniversityInfos().get(1)).getId(), desktop3.getUniversities().get(1).getId());
		assertEquals(((DepartmentInfo) desktopInfo2.getDepartmentInfos().get(0)).getId(), desktop3.getDepartments().get(0).getId());
		assertEquals(((DepartmentInfo) desktopInfo2.getDepartmentInfos().get(1)).getId(), desktop3.getDepartments().get(1).getId());
		
	}
	
	private void removeDesktopAndUser(Desktop desktop) {
		desktopDao.remove(desktop);
		testUtility.removeUser(desktop.getUser());
	}

	private Desktop createDesktop() {
		Desktop desktopOne = new DesktopImpl();
		desktopOne.setUser(testUtility.createUniqueUserInDB());
		desktopDao.create(desktopOne);
		return desktopOne;
	}
	
	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}
	
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
}