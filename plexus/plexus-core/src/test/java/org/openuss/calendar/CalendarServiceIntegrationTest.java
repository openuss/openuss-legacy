// OpenUSS - Open Source University Support System
package org.openuss.calendar;
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.groups.GroupInfo;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;

/**
 * JUnit Test for Spring Hibernate CalendarService class.
 * @see org.openuss.calendar.CalendarService
 */
public class CalendarServiceIntegrationTest extends CalendarServiceIntegrationTestBase {
	
	public SecurityService securityService;
	
	
	private UserInfo getTestUserInfo() {
		DomainObject domainObjectUser = generateDomainObject();
		
		UserInfo userInfo = new UserInfo();
		userInfo.setId(domainObjectUser.getId());
		userInfo.setUsername("El Matatdor");
		userInfo.setFirstName("Hans");
		userInfo.setLastName("Peter");
		userInfo.setPassword("pwd");
		userInfo.setEmail("ElMatador@Stierkampf.de");
				
		return userInfo;
	}
	
	private DomainObject generateDomainObject(){
		DomainObject domainObject = new DefaultDomainObject(TestUtility.unique());
		securityService.createObjectIdentity(domainObject, null);
		//securityService.setPermissions(user, domainId, LectureAclEntry.ASSIST);		
		
		return domainObject;
	}
	
	public void testCreateCalendar() {
		
		// Creating a calendar for a course
		DomainObject domainObjectCourse = generateDomainObject();
		
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setId(domainObjectCourse.getId());
		
		try {
			calendarService.createCalendar(courseInfo);
		} catch (CalendarApplicationException e){
			System.out.println(e);
		}
		
		// Creating a calendar for a group
		DomainObject domainObjectGroup = generateDomainObject(); 

		AccessType accessType = AccessType.OPEN;
		
		GroupInfo groupInfo = new GroupInfo();
		// TODO AccessType setzen
		// groupInfo.setAccessType(accessType);
		groupInfo.setDescription("Beschreibung");
		groupInfo.setChat(false);
		groupInfo.setForum(false);
		groupInfo.setId(domainObjectGroup.getId());
		groupInfo.setName("Tolle Gruppe");
		groupInfo.setShortcut("Gruppen-Shortcut");
		groupInfo.setNewsletter(false);
		
		try {
			calendarService.createCalendar(groupInfo);
		} catch (CalendarApplicationException e) {
			System.out.println(e);
		}
		
		// Creating a calendar for a user
		// TODO test the creation of a calendar for a user with createCalendar
		UserInfo userInfo = getTestUserInfo();
		
		try {
			calendarService.createCalendar(userInfo);
		} catch (CalendarApplicationException e) {
			System.out.println(e);
		}
		
	}
	
	public void testCalendarAdministration() {
		/* Complete Test 1)
	 	 * 1 - createUserCalendar / creatCalendar for userInfo object
		 * 2 - createAppointment
		 * 3 - getSingleAppointments / getAllUserAppointments -> not null
		 * 4 - updateAppointment
		 * 5 - getAppointment
		 * 6 - deleteAppointment
		 * 7 - createSerialAppointment
		 * 8 - getSerialAppointments / getAllUserAppointments -> not null
		 * 9 - updateSerialAppointment
		 *10 - getAppointment
		 *11 - deleteSerialAppointment
		 *12 - getSerialAppointments / getAllUserAppointments -> null
		 *13 - deleteCalendar
		 *     End :)
		 */ 
	}
	
	public void testSubsciption() {
		/* Complete Test 2)
		 * requires group + groupCalendar + appointments AND course + courseCalendar + appointments
		 * 1 - createUserCalendar / creatCalendar for userInfo object
		 * 2 - getUserCalendars -> not null, but only userCalendar !
		 * 3 - addSubscription(group)
		 * 4 - getAllUserAppointments -> not null
		 * 5 - getUserCalendars -> user+groupCalendar
		 * 6 - endSubscription(group)
		 * 7 - getAllUserAppointments -> null
		 * 8 - getUserCalendars -> only userCalendar
		 * 9 - addSubscription(course)
		 *10 - getAllUserAppointments -> not null
		 *11 - getUserCalendars -> user+courseCalendar
		 *12 - endSubscription(course)
		 *13 - getAllUserAppointments -> null
		 *14 - getUserCalendars -> only userCalendar
		 *15 - deleteCalendar
		 *     End :)
		 */
		
	}
	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
}