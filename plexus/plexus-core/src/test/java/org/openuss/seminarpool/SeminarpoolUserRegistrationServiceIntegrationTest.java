// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate SeminarpoolUserRegistrationService class.
 * @see org.openuss.seminarpool.SeminarpoolUserRegistrationService
 */
public class SeminarpoolUserRegistrationServiceIntegrationTest extends SeminarpoolUserRegistrationServiceIntegrationTestBase {
	public void testRegisterUserForSeminarpool(){
		logger.debug("----> BEGIN access to registerUserForSeminarpool test <---- ");
		
		
		User user = testUtility.createUniqueUserInDB();
		CourseSeminarpoolAllocation courseSeminarpoolAllocation = testUtility.createCourseSeminarpoolAllocation();
		
		flush();
		
		SeminarUserRegistrationInfo userRegistrationInfo = new SeminarUserRegistrationInfo();
		userRegistrationInfo.setNeededSeminars(1);
		userRegistrationInfo.setSeminarpoolId(courseSeminarpoolAllocation.getSeminarpool().getId());
		userRegistrationInfo.setUserId(user.getId());
		
		List<SeminarPrioritiesInfo> seminarPriorityList = new ArrayList<SeminarPrioritiesInfo>();
		SeminarPrioritiesInfo seminarPrioritiesInfo = new SeminarPrioritiesInfo();
		seminarPrioritiesInfo.setCourseId(courseSeminarpoolAllocation.getCourse().getId());
		seminarPrioritiesInfo.setPriority(1);
		seminarPriorityList.add(seminarPrioritiesInfo);
		
		userRegistrationInfo.setSeminarPriorityList(seminarPriorityList);
	
		userRegistrationInfo.setId(this.getSeminarpoolUserRegistrationService().registerUserForSeminarpool(userRegistrationInfo, null));
		
		
		//Test
		
		SeminarUserRegistrationDao seminarUserRegistrationDao = (SeminarUserRegistrationDao) this.getApplicationContext().getBean("seminarUserRegistrationDao");
		SeminarUserRegistration sur = seminarUserRegistrationDao.load(userRegistrationInfo.getId());
		
		assertEquals(userRegistrationInfo.getNeededSeminars(),sur.getNeededSeminars());
		assertEquals(userRegistrationInfo.getSeminarPriorityList().size(),sur.getSeminarPriority().size());
		assertEquals(seminarPrioritiesInfo.getPriority(),sur.getSeminarPriority().iterator().next().getPriority());
		
		logger.debug("----> END access to registerUserForSeminarpool test <---- ");
	}
	
	public void testUnregisterUserFromSeminar(){
logger.debug("----> BEGIN access to unregisterUserForSeminarpool test <---- ");
		
		
		User user = testUtility.createUniqueUserInDB();
		CourseSeminarpoolAllocation courseSeminarpoolAllocation = testUtility.createCourseSeminarpoolAllocation();
		
		flush();
		
		SeminarUserRegistrationInfo userRegistrationInfo = new SeminarUserRegistrationInfo();
		userRegistrationInfo.setNeededSeminars(1);
		userRegistrationInfo.setSeminarpoolId(courseSeminarpoolAllocation.getSeminarpool().getId());
		userRegistrationInfo.setUserId(user.getId());
		
		List<SeminarPrioritiesInfo> seminarPriorityList = new ArrayList<SeminarPrioritiesInfo>();
		SeminarPrioritiesInfo seminarPrioritiesInfo = new SeminarPrioritiesInfo();
		seminarPrioritiesInfo.setCourseId(courseSeminarpoolAllocation.getCourse().getId());
		seminarPrioritiesInfo.setPriority(1);
		seminarPriorityList.add(seminarPrioritiesInfo);
		
		userRegistrationInfo.setSeminarPriorityList(seminarPriorityList);
	
		userRegistrationInfo.setId(this.getSeminarpoolUserRegistrationService().registerUserForSeminarpool(userRegistrationInfo, null));
		
		
		//Test
		
		this.getSeminarpoolUserRegistrationService().unregisterUserFromSeminar(userRegistrationInfo);
		
		SeminarUserRegistrationDao seminarUserRegistrationDao = (SeminarUserRegistrationDao) this.getApplicationContext().getBean("seminarUserRegistrationDao");
		SeminarUserRegistration sur = seminarUserRegistrationDao.load(userRegistrationInfo.getId());
		
		assertNull(sur);
		
		logger.debug("----> END access to unregisterUserForSeminarpool test <---- ");
	}
	
	public void testFindConditionValuesByUserAndSeminarpool(){
		User user = testUtility.createUniqueUserInDB();
		CourseSeminarpoolAllocation courseSeminarpoolAllocation = testUtility.createCourseSeminarpoolAllocation();
		
		SeminarCondition seminarCondition = SeminarCondition.Factory.newInstance();
		seminarCondition.setConditionDescription("ConditionDescription");
		seminarCondition.setFieldDescription("FieldDescription");
		seminarCondition.setFieldType(ConditionType.CHECKBOX);
		seminarCondition.setSeminarpool(courseSeminarpoolAllocation.getSeminarpool());
		
		SeminarConditionDao seminarConditionDao = (SeminarConditionDao) this.getApplicationContext().getBean("seminarConditionDao");
		seminarConditionDao.create(seminarCondition);
		
		courseSeminarpoolAllocation.getSeminarpool().addCondition(seminarCondition);
		
		SeminarpoolDao seminarpoolDao = (SeminarpoolDao) this.getApplicationContext().getBean("seminarpoolDao");
		seminarpoolDao.update(courseSeminarpoolAllocation.getSeminarpool());
		
		
		
		flush();
		
		SeminarUserRegistrationInfo userRegistrationInfo = new SeminarUserRegistrationInfo();
		userRegistrationInfo.setNeededSeminars(1);
		userRegistrationInfo.setSeminarpoolId(courseSeminarpoolAllocation.getSeminarpool().getId());
		userRegistrationInfo.setUserId(user.getId());
		
		List<SeminarPrioritiesInfo> seminarPriorityList = new ArrayList<SeminarPrioritiesInfo>();
		SeminarPrioritiesInfo seminarPrioritiesInfo = new SeminarPrioritiesInfo();
		seminarPrioritiesInfo.setCourseId(courseSeminarpoolAllocation.getCourse().getId());
		seminarPrioritiesInfo.setPriority(1);
		seminarPriorityList.add(seminarPrioritiesInfo);
		
		userRegistrationInfo.setSeminarPriorityList(seminarPriorityList);
		
		SeminarUserConditionValueInfo sucvi = new SeminarUserConditionValueInfo();
		sucvi.setConditionValue("My value");
		sucvi.setSeminarConditionId(seminarCondition.getId());
		
		List<SeminarUserConditionValueInfo> lsucvi = new ArrayList<SeminarUserConditionValueInfo>();
		lsucvi.add(sucvi);
	
		userRegistrationInfo.setId(this.getSeminarpoolUserRegistrationService().registerUserForSeminarpool(userRegistrationInfo, lsucvi));
		
		List<SeminarUserConditionValueInfo> list = getSeminarpoolUserRegistrationService().findConditionValuesByUserAndSeminarpool(user.getId(), courseSeminarpoolAllocation.getSeminarpool().getId());
		SeminarUserConditionValueInfo firstElement = list.get(0);
		assertNotNull(firstElement);
		assertEquals(firstElement.getSeminarConditionDescription(), seminarCondition.getConditionDescription());
		assertEquals(firstElement.getConditionValue(), sucvi.getConditionValue());
		assertEquals(firstElement.getSeminarConditionType(), seminarCondition.getFieldType());
		assertEquals(firstElement.getSeminarUserRegistrationId(), userRegistrationInfo.getId());
//		assertNotNull(firstElement.getId());

		
	}
}