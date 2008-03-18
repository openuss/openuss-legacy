// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopDao;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseTypeDao;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate SeminarpoolAllocationService class.
 * @see org.openuss.seminarpool.SeminarpoolAllocationService
 */
public class SeminarpoolAllocationServiceIntegrationTest extends SeminarpoolAllocationServiceIntegrationTestBase {
	

	
	public void testGenerateAllocation () {
		logger.debug("----> BEGIN access to create test <---- ");
		User user1 = testUtility.createUniqueUserInDB();
		User user2 = testUtility.createUniqueUserInDB();
		User user3 = testUtility.createUniqueUserInDB();
		User user4 = testUtility.createUniqueUserInDB();
		User user5 = testUtility.createUniqueUserInDB();
		User user6 = testUtility.createUniqueUserInDB();
		DesktopDao desktopDao = (DesktopDao) this.getApplicationContext().getBean("desktopDao");
		Desktop desktop1 = Desktop.Factory.newInstance();
		desktop1.setUser(user1);
		desktop1.setId(desktopDao.create(desktop1).getId());
		Desktop desktop2 = Desktop.Factory.newInstance();
		desktop2.setUser(user2);
		desktop2.setId(desktopDao.create(desktop2).getId());
		Desktop desktop3 = Desktop.Factory.newInstance();
		desktop3.setUser(user3);
		desktop3.setId(desktopDao.create(desktop3).getId());
		Desktop desktop4 = Desktop.Factory.newInstance();
		desktop4.setUser(user4);
		desktop4.setId(desktopDao.create(desktop4).getId());
		Desktop desktop5 = Desktop.Factory.newInstance();
		desktop5.setUser(user5);
		desktop5.setId(desktopDao.create(desktop5).getId());
		Desktop desktop6 = Desktop.Factory.newInstance();
		desktop6.setUser(user6);
		desktop6.setId(desktopDao.create(desktop6).getId());
		Course course1 = testUtility.createUniqueCourseInDB();
		Course course2 = testUtility.createUniqueCourseInDB();
		Course course3 = testUtility.createUniqueCourseInDB();
		
		flush();
		
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
		seminarpool.setMaxSeminarAllocations(2);
		seminarpool.setPriorities(3);
		
		List<SeminarUserRegistration> surilist = new ArrayList<SeminarUserRegistration>();
		
		SeminarpoolAdministrationService adminservice = (SeminarpoolAdministrationService) this.getApplicationContext().getBean("seminarpoolAdministrationService");
		SeminarpoolUserRegistrationService userregservice = (SeminarpoolUserRegistrationService) this.getApplicationContext().getBean("seminarpoolUserRegistrationService");
		SeminarpoolDao seminarpoolDao = (SeminarpoolDao) this.getApplicationContext().getBean("seminarpoolDao");
		SeminarUserRegistrationDao seminarUserRegistrationDao = (SeminarUserRegistrationDao) this.getApplicationContext().getBean("seminarUserRegistrationDao");
		CourseSeminarpoolAllocationDao courseSeminarpoolAllocationDao = (CourseSeminarpoolAllocationDao) this.getApplicationContext().getBean("courseSeminarpoolAllocationDao");
		//Course 1
		CourseSeminarpoolAllocationInfo seminarpoolAllocation1 = new CourseSeminarpoolAllocationInfo();
		seminarpoolAllocation1.setSeminarpoolId(seminarpool.getId());
		seminarpoolAllocation1.setCourseId(course1.getId());
		
		CourseGroupInfo courseGroup1 = new CourseGroupInfo();
		courseGroup1.setIsDefault(true);
		courseGroup1.setIsTimeSet(false);
		courseGroup1.setCapacity(3);
		courseGroup1.setName("Unique Name1");
		
		Collection<CourseGroupInfo> coll1 = new ArrayList<CourseGroupInfo>();
		coll1.add(courseGroup1);
		seminarpoolAllocation1.setId(courseSeminarpoolAllocationDao.create(courseSeminarpoolAllocationDao.courseSeminarpoolAllocationInfoToEntity(seminarpoolAllocation1)).getId());
		adminservice.addSeminar(seminarpoolAllocation1, coll1);
		
		//Course 2
		CourseSeminarpoolAllocationInfo seminarpoolAllocation2 = new CourseSeminarpoolAllocationInfo();
		seminarpoolAllocation2.setSeminarpoolId(seminarpool.getId());
		seminarpoolAllocation2.setCourseId(course2.getId());
		
		CourseGroupInfo courseGroup2 = new CourseGroupInfo();
		courseGroup2.setIsDefault(true);
		courseGroup2.setIsTimeSet(false);
		courseGroup2.setCapacity(2);
		courseGroup2.setName("Unique Name2");
		
		CourseGroupInfo courseGroup3 = new CourseGroupInfo();
		courseGroup3.setIsDefault(true);
		courseGroup3.setIsTimeSet(false);
		courseGroup3.setCapacity(3);
		courseGroup3.setName("Unique Name3");
		
		Collection<CourseGroupInfo> coll2 = new ArrayList<CourseGroupInfo>();
		coll2.add(courseGroup2);
		coll2.add(courseGroup3);
		seminarpoolAllocation2.setId(courseSeminarpoolAllocationDao.create(courseSeminarpoolAllocationDao.courseSeminarpoolAllocationInfoToEntity(seminarpoolAllocation2)).getId());
		adminservice.addSeminar(seminarpoolAllocation2, coll2);
		
		//Course 3
		CourseSeminarpoolAllocationInfo seminarpoolAllocation3 = new CourseSeminarpoolAllocationInfo();
		seminarpoolAllocation3.setSeminarpoolId(seminarpool.getId());
		seminarpoolAllocation3.setCourseId(course3.getId());
		
		CourseGroupInfo courseGroup4 = new CourseGroupInfo();
		courseGroup4.setIsDefault(true);
		courseGroup4.setIsTimeSet(false);
		courseGroup4.setCapacity(4);
		courseGroup4.setName("Unique Name2");
		
		
		Collection<CourseGroupInfo> coll3 = new ArrayList<CourseGroupInfo>();
		coll3.add(courseGroup3);
		seminarpoolAllocation3.setId(courseSeminarpoolAllocationDao.create(courseSeminarpoolAllocationDao.courseSeminarpoolAllocationInfoToEntity(seminarpoolAllocation3)).getId());
		adminservice.addSeminar(seminarpoolAllocation3, coll2);
		
		SeminarUserRegistrationInfo userRegistrationInfo1;
		List<SeminarPriority> seminarPriorityList;
		SeminarPrioritiesInfo seminarPrioritiesInfo1;
		
		SeminarUserRegistration userRegistration1 = SeminarUserRegistration.Factory.newInstance();
		SeminarUserRegistration userRegistration2 = SeminarUserRegistration.Factory.newInstance();
		SeminarUserRegistration userRegistration3 = SeminarUserRegistration.Factory.newInstance();
		SeminarUserRegistration userRegistration4 = SeminarUserRegistration.Factory.newInstance();
		SeminarUserRegistration userRegistration5 = SeminarUserRegistration.Factory.newInstance();
		SeminarUserRegistration userRegistration6 = SeminarUserRegistration.Factory.newInstance();
		
		//Register User1
		
		seminarPriorityList = new ArrayList<SeminarPriority>();
		SeminarPriority seminarPriorities1 = SeminarPriority.Factory.newInstance();
		SeminarPriority seminarPriorities2 = SeminarPriority.Factory.newInstance();
		SeminarPriority seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration1.setNeededSeminars(2);
		userRegistration1.setSeminarpool(seminarpool);
		userRegistration1.setUser(user1);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(1);
		seminarPriorities1.setSeminarUserRegistration(userRegistration1);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(2);
		seminarPriorities2.setSeminarUserRegistration(userRegistration1);
		seminarPriorityList.add(seminarPriorities2);
		
		seminarPriorities3.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation3.getId()));
		seminarPriorities3.setPriority(3);
		seminarPriorities3.setSeminarUserRegistration(userRegistration1);
		seminarPriorityList.add(seminarPriorities3);
		
		userRegistration1.setSeminarPriority(seminarPriorityList);
		userRegistration1.setId(seminarUserRegistrationDao.create(userRegistration1).getId());
		userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration1),null);
		surilist.add(userRegistration1);
		
		//Register User2
		userRegistration2 = SeminarUserRegistration.Factory.newInstance();
		seminarPriorityList = new ArrayList<SeminarPriority>();
		seminarPriorities1 = SeminarPriority.Factory.newInstance();
		seminarPriorities2 = SeminarPriority.Factory.newInstance();
		seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration2.setNeededSeminars(2);
		userRegistration2.setSeminarpool(seminarpool);
		userRegistration2.setUser(user2);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(1);
		seminarPriorities1.setSeminarUserRegistration(userRegistration2);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(3);
		seminarPriorities2.setSeminarUserRegistration(userRegistration2);
		seminarPriorityList.add(seminarPriorities2);
		
		seminarPriorities3.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation3.getId()));
		seminarPriorities3.setPriority(2);
		seminarPriorities3.setSeminarUserRegistration(userRegistration2);
		seminarPriorityList.add(seminarPriorities3);
		
		userRegistration2.setSeminarPriority(seminarPriorityList);
		userRegistration2.setId(seminarUserRegistrationDao.create(userRegistration2).getId());
		userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration2),null);
		surilist.add(userRegistration2);
		
		//Register User3
		userRegistration3 = SeminarUserRegistration.Factory.newInstance();
		seminarPriorityList = new ArrayList<SeminarPriority>();
		seminarPriorities1 = SeminarPriority.Factory.newInstance();
		seminarPriorities2 = SeminarPriority.Factory.newInstance();
		seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration3.setNeededSeminars(2);
		userRegistration3.setSeminarpool(seminarpool);
		userRegistration3.setUser(user3);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(1);
		seminarPriorities1.setSeminarUserRegistration(userRegistration3);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(2);
		seminarPriorities2.setSeminarUserRegistration(userRegistration3);
		seminarPriorityList.add(seminarPriorities2);
		
		userRegistration3.setSeminarPriority(seminarPriorityList);
		userRegistration3.setId(seminarUserRegistrationDao.create(userRegistration3).getId());
		userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration3),null);
		surilist.add(userRegistration3);
		
		//Register User4
		userRegistration4 = SeminarUserRegistration.Factory.newInstance();
		seminarPriorityList = new ArrayList<SeminarPriority>();
		seminarPriorities1 = SeminarPriority.Factory.newInstance();
		seminarPriorities2 = SeminarPriority.Factory.newInstance();
		seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration4.setNeededSeminars(2);
		userRegistration4.setSeminarpool(seminarpool);
		userRegistration4.setUser(user4);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(3);
		seminarPriorities1.setSeminarUserRegistration(userRegistration4);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(2);
		seminarPriorities2.setSeminarUserRegistration(userRegistration4);
		seminarPriorityList.add(seminarPriorities2);
		
		seminarPriorities3.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation3.getId()));
		seminarPriorities3.setPriority(1);
		seminarPriorities3.setSeminarUserRegistration(userRegistration4);
		seminarPriorityList.add(seminarPriorities3);
		
		userRegistration4.setSeminarPriority(seminarPriorityList);
		userRegistration4.setId(seminarUserRegistrationDao.create(userRegistration4).getId());
		userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration4),null);
		surilist.add(userRegistration4);
		
		//Register User5
		userRegistration5 = SeminarUserRegistration.Factory.newInstance();
		seminarPriorityList = new ArrayList<SeminarPriority>();
		seminarPriorities1 = SeminarPriority.Factory.newInstance();
		seminarPriorities2 = SeminarPriority.Factory.newInstance();
		seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration5.setNeededSeminars(2);
		userRegistration5.setSeminarpool(seminarpool);
		userRegistration5.setUser(user5);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(1);
		seminarPriorities1.setSeminarUserRegistration(userRegistration5);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(3);
		seminarPriorities2.setSeminarUserRegistration(userRegistration5);
		seminarPriorityList.add(seminarPriorities2);
		
		userRegistration5.setSeminarPriority(seminarPriorityList);
		userRegistration5.setId(seminarUserRegistrationDao.create(userRegistration5).getId());
		userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration5),null);
		surilist.add(userRegistration5);
		
		//Register User6
		userRegistration6 = SeminarUserRegistration.Factory.newInstance();
		seminarPriorityList = new ArrayList<SeminarPriority>();
		seminarPriorities1 = SeminarPriority.Factory.newInstance();
		seminarPriorities2 = SeminarPriority.Factory.newInstance();
		seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration6.setNeededSeminars(1);
		userRegistration6.setSeminarpool(seminarpool);
		userRegistration6.setUser(user6);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(2);
		seminarPriorities1.setSeminarUserRegistration(userRegistration6);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(3);
		seminarPriorities2.setSeminarUserRegistration(userRegistration6);
		seminarPriorityList.add(seminarPriorities2);
		
		seminarPriorities3.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation3.getId()));
		seminarPriorities3.setPriority(1);
		seminarPriorities3.setSeminarUserRegistration(userRegistration6);
		seminarPriorityList.add(seminarPriorities3);
		
		userRegistration6.setSeminarPriority(seminarPriorityList);
		userRegistration6.setId(seminarUserRegistrationDao.create(userRegistration6).getId());
		userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration6),null);
		surilist.add(userRegistration6);
	
		

		seminarpool.setSeminarUserRegistration(surilist);
	
		seminarpoolDao.update(seminarpool);
		
		this.getSeminarpoolAllocationService().generateAllocation(seminarpool.getId());
			
		
	}
	
}