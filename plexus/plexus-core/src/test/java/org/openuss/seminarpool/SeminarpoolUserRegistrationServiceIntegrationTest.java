// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.util.ArrayList;
import java.util.List;

import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate SeminarpoolUserRegistrationService class.
 * @see org.openuss.seminarpool.SeminarpoolUserRegistrationService
 */
public class SeminarpoolUserRegistrationServiceIntegrationTest extends SeminarpoolUserRegistrationServiceIntegrationTestBase {
	
	private SeminarUserRegistrationInfo userRegistrationInfo;
	private SeminarPrioritiesInfo seminarPrioritiesInfo;
	private SeminarCondition seminarCondition;
	private User user;
	private CourseSeminarpoolAllocation courseSeminarpoolAllocation;
	private SeminarUserConditionValueInfo sucvi;
	private List<SeminarUserConditionValueInfo> lsucvi;
	
	public void setUpTest(){
		user = testUtility.createUniqueUserInDB();
		courseSeminarpoolAllocation = testUtility.createCourseSeminarpoolAllocation();
	    seminarCondition = SeminarCondition.Factory.newInstance();
		seminarCondition.setConditionDescription("ConditionDescription");
		seminarCondition.setFieldDescription("FieldDescription");
		seminarCondition.setFieldType(ConditionType.CHECKBOX);
		seminarCondition.setSeminarpool(courseSeminarpoolAllocation.getSeminarpool());
//		Create SeminarCondtion		
		SeminarConditionDao seminarConditionDao = (SeminarConditionDao) this.getApplicationContext().getBean("seminarConditionDao");
		seminarConditionDao.create(seminarCondition);
//		Add Condition to Seminarpool
		courseSeminarpoolAllocation.getSeminarpool().addCondition(seminarCondition);
		SeminarpoolDao seminarpoolDao = (SeminarpoolDao) this.getApplicationContext().getBean("seminarpoolDao");
		seminarpoolDao.update(courseSeminarpoolAllocation.getSeminarpool());

		flush();
		
		
	    userRegistrationInfo = new SeminarUserRegistrationInfo();
		userRegistrationInfo.setNeededSeminars(1);
		userRegistrationInfo.setSeminarpoolId(courseSeminarpoolAllocation.getSeminarpool().getId());
		userRegistrationInfo.setUserId(user.getId());
		
		List<SeminarPrioritiesInfo> seminarPriorityList = new ArrayList<SeminarPrioritiesInfo>();
		seminarPrioritiesInfo = new SeminarPrioritiesInfo();
		seminarPrioritiesInfo.setCourseId(courseSeminarpoolAllocation.getId());
		seminarPrioritiesInfo.setPriority(1);
		seminarPriorityList.add(seminarPrioritiesInfo);
		
		userRegistrationInfo.setSeminarPriorityList(seminarPriorityList);
		sucvi = new SeminarUserConditionValueInfo();
		sucvi.setConditionValue("My value");
		sucvi.setSeminarConditionId(seminarCondition.getId());
		lsucvi = new ArrayList<SeminarUserConditionValueInfo>();
		lsucvi.add(sucvi);
	}
	
	private void tearDownTest(){
		userRegistrationInfo = null;
		seminarPrioritiesInfo = null;
		courseSeminarpoolAllocation = null;
		lsucvi = null;
		seminarCondition = null;
		user = null;
		sucvi = null;
		
	}
	
	public void testRegisterUserForSeminarpool(){
		logger.debug("----> BEGIN access to registerUserForSeminarpool test <---- ");
		setUpTest();
		
		
		Long id = this.getSeminarpoolUserRegistrationService().registerUserForSeminarpool(userRegistrationInfo, null);
		//Test
		
		SeminarUserRegistrationDao seminarUserRegistrationDao = (SeminarUserRegistrationDao) this.getApplicationContext().getBean("seminarUserRegistrationDao");
		SeminarUserRegistration sur = seminarUserRegistrationDao.load(id);
		
		assertEquals(userRegistrationInfo.getNeededSeminars(),sur.getNeededSeminars());
		assertEquals(userRegistrationInfo.getSeminarPriorityList().size(),sur.getSeminarPriority().size());
		assertEquals(seminarPrioritiesInfo.getPriority(),sur.getSeminarPriority().iterator().next().getPriority());
		tearDownTest();
		logger.debug("----> END access to registerUserForSeminarpool test <---- ");
	}
	
	
	public void testUnregisterUserFromSeminar(){
		logger.debug("----> BEGIN access to unregisterUserForSeminarpool test <---- ");
		setUpTest();
		Long id = getSeminarpoolUserRegistrationService().registerUserForSeminarpool(userRegistrationInfo, null);
		userRegistrationInfo.setId(id);
		//Test
		this.getSeminarpoolUserRegistrationService().unregisterUserFromSeminar(userRegistrationInfo);
		SeminarUserRegistrationDao seminarUserRegistrationDao = (SeminarUserRegistrationDao) this.getApplicationContext().getBean("seminarUserRegistrationDao");
		SeminarUserRegistration sur = seminarUserRegistrationDao.load(id);
		assertNull(sur);
		tearDownTest();
		logger.debug("----> END access to unregisterUserForSeminarpool test <---- ");
	}
	
	public void testFindConditionValuesByUserAndSeminarpool(){
		setUpTest();
		Long id = getSeminarpoolUserRegistrationService().registerUserForSeminarpool(userRegistrationInfo, lsucvi);
		userRegistrationInfo.setId(id);
//		Find SeminarUserConditionValueInfo 		
		List<SeminarUserConditionValueInfo> list = getSeminarpoolUserRegistrationService().findConditionValuesByUserAndSeminarpool(user.getId(), courseSeminarpoolAllocation.getSeminarpool().getId());
		SeminarUserConditionValueInfo firstElement = list.get(0);
		assertNotNull(firstElement);
//		Test
		assertEquals(firstElement.getSeminarConditionDescription(), seminarCondition.getConditionDescription());
		assertEquals(firstElement.getConditionValue(), sucvi.getConditionValue());
		assertEquals(firstElement.getSeminarConditionType(), seminarCondition.getFieldType());
		assertEquals(firstElement.getSeminarUserRegistrationId(), id);
		assertNotNull(firstElement.getId());		
		tearDownTest();
	}
	
	public void testFindUserConditionValuesBySeminarUserRegistration(){
		setUpTest();
		userRegistrationInfo.setId(this.getSeminarpoolUserRegistrationService().registerUserForSeminarpool(userRegistrationInfo, lsucvi));
//		Find SeminarUserConditionValueInfo 		
		List<SeminarUserConditionValueInfo> list = getSeminarpoolUserRegistrationService().findUserConditionValuesBySeminarUserRegistration(userRegistrationInfo);
		SeminarUserConditionValueInfo firstElement = list.get(0);
		assertNotNull(firstElement);
//		Test
		assertEquals(firstElement.getSeminarConditionDescription(), seminarCondition.getConditionDescription());
		assertEquals(firstElement.getConditionValue(), sucvi.getConditionValue());
		assertEquals(firstElement.getSeminarConditionType(), seminarCondition.getFieldType());
		assertEquals(firstElement.getSeminarUserRegistrationId(), userRegistrationInfo.getId());
		assertNotNull(firstElement.getId());
		tearDownTest();
	}
	
	
	public void testEditUserRegistration(){
		setUpTest();
		userRegistrationInfo.setId(this.getSeminarpoolUserRegistrationService().registerUserForSeminarpool(userRegistrationInfo, lsucvi));
//		Load 		
		SeminarUserRegistrationInfo seminarUserRegistrationInfo = getSeminarpoolUserRegistrationService().findSeminarUserRegistrationById(userRegistrationInfo.getId());
//		Change Values
		seminarUserRegistrationInfo.setNeededSeminars(8);
		((SeminarPrioritiesInfo)seminarUserRegistrationInfo.getSeminarPriorityList().get(0)).setPriority(10);
//		Edit
		getSeminarpoolUserRegistrationService().editUserRegistration(seminarUserRegistrationInfo, null);
//		Load 		
		userRegistrationInfo = getSeminarpoolUserRegistrationService().findSeminarUserRegistrationById(userRegistrationInfo.getId());
//		Test
		assertTrue( 8 == userRegistrationInfo.getNeededSeminars());
		assertTrue( 10 == ((SeminarPrioritiesInfo)seminarUserRegistrationInfo.getSeminarPriorityList().get(0)).getPriority());
		tearDownTest();
	}
	
	public void testFindSeminarUserRegistrationById(){
		// Implizit in der Methode vorher schon getestet
	}

	
	
}