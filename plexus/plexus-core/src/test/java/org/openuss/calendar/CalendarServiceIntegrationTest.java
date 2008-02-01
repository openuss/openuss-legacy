// OpenUSS - Open Source University Support System
package org.openuss.calendar;
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */

import java.util.Date;
import org.openuss.TestUtility;
import org.openuss.foundation.DomainObject;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.lecture.*;

/**
 * JUnit Test for Spring Hibernate CalendarService class.
 * @see org.openuss.calendar.CalendarService
 */
public class CalendarServiceIntegrationTest extends CalendarServiceIntegrationTestBase {
	
	public SecurityService securityService;
	
	private DomainObject generateDomainObject(){
		DomainObject domainObject = new DefaultDomainObject(TestUtility.unique());
		securityService.createObjectIdentity(domainObject, null);
		//securityService.setPermissions(user, domainId, LectureAclEntry.ASSIST);		
		
		return domainObject;
	}
	
	public void testCreateCalendar() {
		DomainObject domainObjectCourse = generateDomainObject();
		
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setId(domainObjectCourse.getId());
				
		try {
			calendarService.createCalendar(courseInfo);
		} catch (CalendarApplicationException e){
			System.out.println(e);
		}
		
		
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
}