// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate DepartmentService class.
 * @see org.openuss.lecture.DepartmentService
 */
public class DepartmentServiceIntegrationTest extends DepartmentServiceIntegrationTestBase {
	
	public void testCreateDepartment() {
		logger.info("----> BEGIN access to create(Department) test");

		//Create new DepartmentInfo object
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setName(testUtility.unique("testDepartment"));
		departmentInfo.setShortcut(testUtility.unique("testD"));
		departmentInfo.setDescription("This is a test Department");
		University uni = testUtility.createDefaultUniversityWithDefaultUser();
		departmentInfo.setUniversityId(uni.getId());
		departmentInfo.setDepartmentType(0);
		
		//Create a User
		User owner = testUtility.createUserInDB();
		
		//Create Entity
		Long departmentId = departmentService.create(departmentInfo, owner.getId());
		assertNotNull(departmentId);

		//Synchronize with Database
		SessionFactory sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		Session session = sessionFactory.getCurrentSession();
		session.flush();
		
		logger.info("----> END access to create(Department) test");
	}
}