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
		Seminarpool seminarpool = testUtility.createUniqueSeminarpoolinDB();
		seminarpool.setMaxSeminarAllocations(2);
		seminarpool.setPriorities(3);
		
		flush();
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
		           
		seminarpoolAllocation1.setId(adminservice.addSeminar(seminarpoolAllocation1, coll1));
		
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
		
		seminarpoolAllocation2.setId(adminservice.addSeminar(seminarpoolAllocation2, coll2));
		
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
		
		seminarpoolAllocation3.setId(adminservice.addSeminar(seminarpoolAllocation3, coll2));
		
		SeminarUserRegistrationInfo userRegistrationInfo1;
		List<SeminarPriority> seminarPriorityList;
		SeminarPrioritiesInfo seminarPrioritiesInfo1;
		
		//Register User1
		SeminarUserRegistration userRegistration = SeminarUserRegistration.Factory.newInstance();
		seminarPriorityList = new ArrayList<SeminarPriority>();
		SeminarPriority seminarPriorities1 = SeminarPriority.Factory.newInstance();
		SeminarPriority seminarPriorities2 = SeminarPriority.Factory.newInstance();
		SeminarPriority seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration.setNeededSeminars(2);
		userRegistration.setSeminarpool(seminarpool);
		userRegistration.setUser(user1);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(1);
		seminarPriorities1.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(2);
		seminarPriorities2.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities2);
		
		seminarPriorities3.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation3.getId()));
		seminarPriorities3.setPriority(3);
		seminarPriorities3.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities3);
		
		userRegistration.setSeminarPriority(seminarPriorityList);
		userRegistration.setId(userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration),null));
		surilist.add(userRegistration);
		
		//Register User2
		userRegistration = SeminarUserRegistration.Factory.newInstance();
		seminarPriorityList = new ArrayList<SeminarPriority>();
		seminarPriorities1 = SeminarPriority.Factory.newInstance();
		seminarPriorities2 = SeminarPriority.Factory.newInstance();
		seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration.setNeededSeminars(2);
		userRegistration.setSeminarpool(seminarpool);
		userRegistration.setUser(user2);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(1);
		seminarPriorities1.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(3);
		seminarPriorities2.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities2);
		
		seminarPriorities3.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation3.getId()));
		seminarPriorities3.setPriority(2);
		seminarPriorities3.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities3);
		
		userRegistration.setSeminarPriority(seminarPriorityList);
		userRegistration.setId(userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration),null));
		surilist.add(userRegistration);
		
		//Register User3
		userRegistration = SeminarUserRegistration.Factory.newInstance();
		seminarPriorityList = new ArrayList<SeminarPriority>();
		seminarPriorities1 = SeminarPriority.Factory.newInstance();
		seminarPriorities2 = SeminarPriority.Factory.newInstance();
		seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration.setNeededSeminars(2);
		userRegistration.setSeminarpool(seminarpool);
		userRegistration.setUser(user3);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(1);
		seminarPriorities1.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(2);
		seminarPriorities2.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities2);
		
		userRegistration.setSeminarPriority(seminarPriorityList);
		userRegistration.setId(userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration),null));
		surilist.add(userRegistration);
		
		//Register User4
		userRegistration = SeminarUserRegistration.Factory.newInstance();
		seminarPriorityList = new ArrayList<SeminarPriority>();
		seminarPriorities1 = SeminarPriority.Factory.newInstance();
		seminarPriorities2 = SeminarPriority.Factory.newInstance();
		seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration.setNeededSeminars(2);
		userRegistration.setSeminarpool(seminarpool);
		userRegistration.setUser(user4);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(3);
		seminarPriorities1.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(2);
		seminarPriorities2.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities2);
		
		seminarPriorities3.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation3.getId()));
		seminarPriorities3.setPriority(1);
		seminarPriorities3.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities3);
		
		userRegistration.setSeminarPriority(seminarPriorityList);
		userRegistration.setId(userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration),null));
		surilist.add(userRegistration);
		
		//Register User5
		userRegistration = SeminarUserRegistration.Factory.newInstance();
		seminarPriorityList = new ArrayList<SeminarPriority>();
		seminarPriorities1 = SeminarPriority.Factory.newInstance();
		seminarPriorities2 = SeminarPriority.Factory.newInstance();
		seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration.setNeededSeminars(2);
		userRegistration.setSeminarpool(seminarpool);
		userRegistration.setUser(user5);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(1);
		seminarPriorities1.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(3);
		seminarPriorities2.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities2);
		
		userRegistration.setSeminarPriority(seminarPriorityList);
		userRegistration.setId(userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration),null));
		surilist.add(userRegistration);
		
		//Register User6
		userRegistration = SeminarUserRegistration.Factory.newInstance();
		seminarPriorityList = new ArrayList<SeminarPriority>();
		seminarPriorities1 = SeminarPriority.Factory.newInstance();
		seminarPriorities2 = SeminarPriority.Factory.newInstance();
		seminarPriorities3 = SeminarPriority.Factory.newInstance();
		
		userRegistration.setNeededSeminars(1);
		userRegistration.setSeminarpool(seminarpool);
		userRegistration.setUser(user6);
		
		
		seminarPriorities1.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation1.getId()));
		seminarPriorities1.setPriority(2);
		seminarPriorities1.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities1);
		
		seminarPriorities2.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation2.getId()));
		seminarPriorities2.setPriority(3);
		seminarPriorities2.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities2);
		
		seminarPriorities3.setCourseSeminarPoolAllocation(courseSeminarpoolAllocationDao.load(seminarpoolAllocation3.getId()));
		seminarPriorities3.setPriority(1);
		seminarPriorities3.setSeminarUserRegistration(userRegistration);
		seminarPriorityList.add(seminarPriorities3);
		
		userRegistration.setSeminarPriority(seminarPriorityList);
		userRegistration.setId(userregservice.registerUserForSeminarpool(seminarUserRegistrationDao.toSeminarUserRegistrationInfo(userRegistration),null));
		surilist.add(userRegistration);
	
		flush();
		

		seminarpool.setSeminarUserRegistration(surilist);
	
		seminarpoolDao.update(seminarpool);

		
		this.getSeminarpoolAllocationService().generateAllocation(seminarpool.getId());
	}
	
}