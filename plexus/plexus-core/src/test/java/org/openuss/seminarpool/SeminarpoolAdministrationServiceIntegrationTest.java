// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
import org.openuss.lecture.Course;
import org.openuss.lecture.University;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

/**
 * JUnit Test for Spring Hibernate SeminarpoolAdministrationService class.
 * @see org.openuss.seminarpool.SeminarpoolAdministrationService
 */
public class SeminarpoolAdministrationServiceIntegrationTest extends SeminarpoolAdministrationServiceIntegrationTestBase {
	

	
	public void testCreateSeminarpool () {
		logger.debug("----> BEGIN access to create test <---- ");
		
		User user = testUtility.createUniqueUserInDB();
		University university= testUtility.createUniqueUniversityInDB();
		
		flush();
		
		//Create SeminarpoolInfo
		SeminarpoolInfo seminarpoolInfo = new SeminarpoolInfo();
		seminarpoolInfo.setName(testUtility.unique("name"));
		seminarpoolInfo.setDescription(testUtility.unique("description"));
		seminarpoolInfo.setMaxSeminarAllocations(1);
		seminarpoolInfo.setPassword(testUtility.unique("passwort"));
		seminarpoolInfo.setPriorities(3);
		seminarpoolInfo.setAccessType(SeminarpoolAccessType.OPEN);
		seminarpoolInfo.setRegistrationStartTime(new Timestamp(123456L));
		seminarpoolInfo.setRegistrationEndTime(new Timestamp(123456L));
		seminarpoolInfo.setSeminarpoolStatus(SeminarpoolStatus.REVIEWPHASE);
		seminarpoolInfo.setShortcut(testUtility.unique("shortcut"));
		seminarpoolInfo.setUniversityId(university.getId());
		
		
		//Test
		Long seminarpoolId = this.getSeminarpoolAdministrationService().createSeminarpool(seminarpoolInfo, user.getId());
		assertNotNull(seminarpoolId);
		
		SeminarpoolDao seminarpoolDao = (SeminarpoolDao) this.getApplicationContext().getBean("seminarpoolDao");
		Seminarpool seminarpoolTest = seminarpoolDao.load(seminarpoolId);
		assertNotNull(seminarpoolTest);
		assertEquals(seminarpoolId, seminarpoolTest.getId());
		assertEquals(seminarpoolInfo.getDescription(), seminarpoolTest.getDescription());
		assertEquals(seminarpoolInfo.getShortcut(), seminarpoolTest.getShortcut());
		assertEquals(seminarpoolInfo.getPassword(), seminarpoolTest.getPassword());
		assertEquals(seminarpoolInfo.getName(), seminarpoolTest.getName());
		assertEquals(seminarpoolInfo.getMaxSeminarAllocations(), seminarpoolTest.getMaxSeminarAllocations());
		assertEquals(seminarpoolInfo.getPriorities(), seminarpoolTest.getPriorities());
		assertEquals(seminarpoolInfo.getSeminarpoolStatus(), seminarpoolTest.getSeminarpoolStatus());
		assertEquals(seminarpoolInfo.getRegistrationStartTime(), seminarpoolTest.getRegistrationStartTime());
		assertEquals(seminarpoolInfo.getRegistrationEndTime(), seminarpoolTest.getRegistrationEndTime());
		assertEquals(seminarpoolInfo.getUniversityId(), university.getId());

		
		logger.debug("----> END access to create test <---- ");
	}
	
	public void testUpdateCourse () {
		logger.debug("----> BEGIN access to updateSeminarpool test <---- ");
		
		
		
		
		University university= testUtility.createUniqueUniversityInDB();
		
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
		assertNotNull(seminarpool);
		
		
		flush();
		
		//Create SeminarpoolInfo
		SeminarpoolInfo seminarpoolInfo = new SeminarpoolInfo();
		seminarpoolInfo.setId(seminarpool.getId());
		seminarpoolInfo.setName(testUtility.unique("name"));
		seminarpoolInfo.setDescription(testUtility.unique("description"));
		seminarpoolInfo.setMaxSeminarAllocations(2);
		seminarpoolInfo.setPassword(testUtility.unique("passwort"));
		seminarpoolInfo.setPriorities(4);
		seminarpoolInfo.setRegistrationStartTime(new Timestamp(123456L));
		seminarpoolInfo.setRegistrationEndTime(new Timestamp(123456L));
		seminarpoolInfo.setSeminarpoolStatus(SeminarpoolStatus.REVIEWPHASE);
		seminarpoolInfo.setShortcut(testUtility.unique("shortcut"));
		seminarpoolInfo.setUniversityId(university.getId());
		
		// Test
		this.getSeminarpoolAdministrationService().updateSeminarpool(seminarpoolInfo);
		
		// Load Course
		SeminarpoolDao seminarpoolDao = (SeminarpoolDao) this.getApplicationContext().getBean("seminarpoolDao");
		Seminarpool seminarpoolTest = seminarpoolDao.load(seminarpoolInfo.getId());
		assertNotNull(seminarpoolTest);
		assertEquals(seminarpoolInfo.getDescription(), seminarpoolTest.getDescription());
		assertEquals(seminarpoolInfo.getShortcut(), seminarpoolTest.getShortcut());
		assertEquals(seminarpoolInfo.getPassword(), seminarpoolTest.getPassword());
		assertEquals(seminarpoolInfo.getName(), seminarpoolTest.getName());
		assertEquals(seminarpoolInfo.getMaxSeminarAllocations(), seminarpoolTest.getMaxSeminarAllocations());
		assertEquals(seminarpoolInfo.getPriorities(), seminarpoolTest.getPriorities());
		assertEquals(seminarpoolInfo.getSeminarpoolStatus(), seminarpoolTest.getSeminarpoolStatus());
		assertEquals(seminarpoolInfo.getRegistrationStartTime(), seminarpoolTest.getRegistrationStartTime());
		assertEquals(seminarpoolInfo.getRegistrationEndTime(), seminarpoolTest.getRegistrationEndTime());
		assertEquals(seminarpoolInfo.getUniversityId(), university.getId());
		
		
		
		logger.debug("----> END access to updateSeminarpool test <---- ");
	}
	
	public void testRemoveSeminarpool () {
		logger.debug("----> BEGIN access to removeCourse test <---- ");
		
		
		//Create Seminarpools
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
	

		
		//Synchronize with DB
		flush();
		
		//Test
		SeminarpoolDao seminarpoolDao = (SeminarpoolDao) this.getApplicationContext().getBean("seminarpoolDao");
		Seminarpool seminarpollTest = seminarpoolDao.load(seminarpool.getId());
		assertNotNull(seminarpollTest);
		
		this.getSeminarpoolAdministrationService().removeSeminarpool(seminarpool.getId());
		seminarpollTest = seminarpoolDao.load(seminarpool.getId());
		assertNull(seminarpollTest);
	
		
		logger.debug("----> END access to removeCourse test <---- ");
	}
	
	public void testAddSeminarpoolAdmin(){
		logger.debug("----> BEGIN access to addSeminarpoolAdmin test <---- ");
		
		//Create Seminarpool
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
		//Crate User
		User user = testUtility.createUniqueUserInDB();
		
		Desktop desktop = Desktop.Factory.newInstance();
		desktop.setUser(user);
		DesktopDao desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		desktop.setId(desktopDao.create(desktop).getId());
		
		flush();
		
		//Add User as Admin
		this.getSeminarpoolAdministrationService().addSeminarpoolAdmin(user.getId(), seminarpool.getId());
		
		//Test
		assertEquals(2, seminarpool.getMembership().getMembers().size());
		
		logger.debug("----> END access to addSeminarpoolAdmin test <---- ");
		
	}
	
	public void testRemoveSeminarpoolAdmin(){
		logger.debug("----> BEGIN access to addSeminarpoolAdmin test <---- ");
		
		//Create Seminarpool
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
		//Crate User
		User user = testUtility.createUniqueUserInDB();
		
		Desktop desktop = Desktop.Factory.newInstance();
		desktop.setUser(user);
		DesktopDao desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		desktop.setId(desktopDao.create(desktop).getId());
		
		flush();
		
		//Add User as Admin
		this.getSeminarpoolAdministrationService().addSeminarpoolAdmin(user.getId(), seminarpool.getId());
		
		//Remove User
		
		this.getSeminarpoolAdministrationService().removeSeminarpoolAdmin(user.getId(), seminarpool.getId());
		
		
		//Test
		
		assertEquals(1, seminarpool.getMembership().getMembers().size());	
		
		logger.debug("----> END access to removeSeminarpoolAdmin test <---- ");
	}
	
	public void testAddSeminar(){
logger.debug("----> BEGIN access to addSeminar test <---- ");
		
		//Create Seminarpool
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
		
		//Create Course
		Course course = testUtility.createUniqueCourseInDB();
		
		flush();
		
		CourseSeminarpoolAllocationInfo seminarpoolAllocation = new CourseSeminarpoolAllocationInfo();
		seminarpoolAllocation.setSeminarpoolId(seminarpool.getId());
		seminarpoolAllocation.setCourseId(course.getId());
		
		CourseGroupInfo courseGroup = new CourseGroupInfo();
		courseGroup.setIsDefault(true);
		courseGroup.setIsTimeSet(false);
		courseGroup.setCapacity(2000);
		courseGroup.setName("Unique Name");
		
		
		CourseScheduleInfo courseSchedule = new CourseScheduleInfo();
		courseSchedule.setDayOfWeek(DayOfWeek.MONDAY);
		courseSchedule.setStartTime(new Date());
		courseSchedule.setEndTime(new Date());
		
		Collection<CourseScheduleInfo> collSchedule = new ArrayList<CourseScheduleInfo>();
		collSchedule.add(courseSchedule);
		courseGroup.setCourseSchedule(collSchedule);
		Collection<CourseGroupInfo> coll = new ArrayList<CourseGroupInfo>();
		coll.add(courseGroup);
		
		seminarpoolAllocation.setId(this.getSeminarpoolAdministrationService().addSeminar(seminarpoolAllocation, coll));
		
		//Test
		assertEquals(1, seminarpool.getCourseSeminarpoolAllocation().size());
		assertNotNull(seminarpoolAllocation.getId());
		
		logger.debug("----> END access to addSeminar test <---- ");
	}
	
	public void testRemoveSeminar(){
		logger.debug("----> BEGIN access to removeSeminar test <---- ");
				
				//Create Seminarpool
				Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
				
				
				
				//Create Course
				Course course = testUtility.createUniqueCourseInDB();
				
				flush();
				
				CourseSeminarpoolAllocationInfo seminarpoolAllocation = new CourseSeminarpoolAllocationInfo();
				seminarpoolAllocation.setSeminarpoolId(seminarpool.getId());
				seminarpoolAllocation.setCourseId(course.getId());
				
				CourseGroupInfo courseGroup = new CourseGroupInfo();
				courseGroup.setIsDefault(true);
				courseGroup.setIsTimeSet(false);
				courseGroup.setCapacity(2000);
				courseGroup.setName("Unique Name");
				
				Collection coll = new ArrayList();
				coll.add(courseGroup);
				
				seminarpoolAllocation.setId(this.getSeminarpoolAdministrationService().addSeminar(seminarpoolAllocation, coll));
	
				
				this.getSeminarpoolAdministrationService().removeSeminar(seminarpoolAllocation);
				
				assertEquals(0, seminarpool.getCourseSeminarpoolAllocation().size());
				
				logger.debug("----> END access to removeSeminar test <---- ");
			}
	public void testFindSeminarpool(){
		logger.debug("----> BEGIN access to findSeminar test <---- ");
		
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
		
		flush();
		
		SeminarpoolInfo seminarpoolInfo = this.getSeminarpoolAdministrationService().findSeminarpool(seminarpool.getId());
		assertEquals(seminarpool.getDescription(),seminarpoolInfo.getDescription());
		
		logger.debug("----> END access to findSeminar test <---- ");
	}
	
	
	public void testGetAllSeminarpools(){
		logger.debug("----> BEGIN access to getAllSeminarpools test <---- ");
		
		//Create Seminarpools
		Seminarpool seminarpool1 = testUtility.createUniqueSeminarpoolinDB();
		Seminarpool seminarpool2 = testUtility.createUniqueSeminarpoolinDB();
		
		flush();
		
		//Test
		List<SeminarpoolInfo> pools = this.getSeminarpoolAdministrationService().getAllSeminarpools();
		Long seminarpool1Id = pools.get(0).getId();
		Long seminarpool2Id = pools.get(1).getId();
		
		assertTrue(seminarpool1Id.equals(seminarpool1.getId()) || seminarpool1Id.equals(seminarpool2.getId()));
		assertTrue(seminarpool2Id.equals(seminarpool1.getId()) || seminarpool2Id.equals(seminarpool2.getId()));
		
		logger.debug("----> END access to getAllSeminarpools test <---- ");
	}
	
	public void testGetRegistrations(){
		logger.debug("----> BEGIN access to getRegistrations test <---- ");
		
		
		
		logger.debug("----> END access to getRegistrations test <---- ");
	}
	
	// <----- missing Tests ----->
	
	public void testAddSeminarCondition(){
		logger.debug("----> BEGIN access to addSeminarCondition test <---- ");
		
		//Create Seminarpool
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
		
		flush();
		
		SeminarConditionInfo seminarConditionInfo = new SeminarConditionInfo();
		seminarConditionInfo.setConditionDescription("ConditionDescription");
		seminarConditionInfo.setFieldDescription("FieldDescription");
		seminarConditionInfo.setFieldType(ConditionType.CHECKBOX);
		seminarConditionInfo.setSeminarpoolId(seminarpool.getId());
		
		seminarConditionInfo.setId(this.seminarpoolAdministrationService.addConditionToSeminarpool(seminarConditionInfo));
		//Test
		assertEquals(1, seminarpool.getSeminarCondition().size());
		assertNotNull(seminarConditionInfo.getId());
		
		logger.debug("----> END access to addSeminarCondition test <---- ");
	}
	
	public void testFindSeminarpoolsByUniversity(){
		Seminarpool seminarpool1 = testUtility.createUniqueSeminarpoolinDB();
		List <SeminarpoolInfo> sem = this.getSeminarpoolAdministrationService().findSeminarpoolsByUniversity(seminarpool1.getUniversity().getId());
		assertEquals(1, sem.size());
	}
	
	public void testRemoveSeminarCondition(){
logger.debug("----> BEGIN access to removeSeminarCondition test <---- ");
		
		//Create Seminarpool
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
		
		flush();
		
		SeminarConditionInfo seminarConditionInfo = new SeminarConditionInfo();
		seminarConditionInfo.setConditionDescription("ConditionDescription");
		seminarConditionInfo.setFieldDescription("FieldDescription");
		seminarConditionInfo.setFieldType(ConditionType.CHECKBOX);
		seminarConditionInfo.setSeminarpoolId(seminarpool.getId());
		
		
		seminarConditionInfo.setId(this.seminarpoolAdministrationService.addConditionToSeminarpool(seminarConditionInfo));
		
		//Test
		assertEquals(1, seminarpool.getSeminarCondition().size());
		
		this.seminarpoolAdministrationService.removeConditionFromSeminarpool(seminarConditionInfo);
		
		assertEquals(0, seminarpool.getSeminarCondition().size());
		
		logger.debug("----> END access to removeSeminarCondition test <---- ");
	}
	
	public void testFindSeminarpoolAdministratorsBySeminarpool(){
		logger.debug("----> BEGIN access to testFindSeminarpoolAdministratorsBySeminarpool test <---- ");
		
		//Create Seminarpool
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
		flush();
		List<UserInfo> userInfoList = seminarpoolAdministrationService.findSeminarpoolAdministratorsBySeminarpool(seminarpool.getId());
		assertNotNull(userInfoList);
		assertEquals(userInfoList.size(), 1);
		logger.debug("----> END access to testFindSeminarpoolAdministratorsBySeminarpool test <---- ");
	}
	
	public void testFindConditionById(){
		SeminarCondition seminarCondition = testUtility.createSeminarCondition();
		SeminarConditionInfo newSeminarCondtion = getSeminarpoolAdministrationService().findConditionById(seminarCondition.getId());
		assertNotNull(newSeminarCondtion);
	}
	
	public void testFindConditionBySeminarpoolId(){
		SeminarCondition seminarCondition = testUtility.createSeminarCondition();
		List<SeminarConditionInfo> seminarCondtionInfoList = getSeminarpoolAdministrationService().findConditionBySeminarpool(seminarCondition.getSeminarpool().getId());
		assertNotNull(seminarCondtionInfoList);
	}

	
}