// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.collaboration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.security.Group;
import org.openuss.security.GroupType;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * JUnit Test for Spring Hibernate WorkspaceService class.
 * @see org.openuss.collaboration.WorkspaceService
 */
public class WorkspaceServiceIntegrationTest extends WorkspaceServiceIntegrationTestBase {
	
	private SecurityService securityService;
	
	private DefaultDomainObject defaultDomainObject;
	
	private User user;
	
	private CourseDao courseDao;
	
	private WorkspaceDao workspaceDao;
	
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		defaultDomainObject = createDomainObject();
		user = testUtility.createUserSecureContext();
		securityService.createObjectIdentity(defaultDomainObject, null);
		securityService.setPermissions(user, defaultDomainObject, LectureAclEntry.ASSIST);
	}
	
	public void testCreateAndGetAndRemoveWorkspace(){
		WorkspaceInfo workspaceInfo = createWorkspaceInfo("name");
		assertNull(workspaceInfo.getId());
		workspaceService.createWorkspace(workspaceInfo);
		assertNotNull(workspaceInfo.getId());
		
		WorkspaceInfo loadedWorkspaceInfo = workspaceService.getWorkspace(workspaceInfo.getId());
		assertNotNull(loadedWorkspaceInfo);
		assertEquals(loadedWorkspaceInfo.getId(), workspaceInfo.getId());
		assertEquals(loadedWorkspaceInfo.getDomainId(), workspaceInfo.getDomainId());
		assertEquals(loadedWorkspaceInfo.getName(), workspaceInfo.getName());
		
		try {
			workspaceService.removeWorkspace(workspaceInfo.getId());
		} catch (WorkspaceServiceException e){
			fail(e.getMessage());
		}
	}
	
	private WorkspaceInfo createWorkspaceInfo(String name) {
		WorkspaceInfo workspaceInfo = new WorkspaceInfo();
		workspaceInfo.setName(name);
		workspaceInfo.setDomainId(defaultDomainObject.getId());
			
		return workspaceInfo;
	}
	

	public void testFindAndUpdateWorkspace(){
		Course course = createCourseDomain();
		
		WorkspaceInfo workspaceInfo = createWorkspaceInfo("name");
		workspaceInfo.setDomainId(course.getId());
		assertNull(workspaceInfo.getId());
		workspaceService.createWorkspace(workspaceInfo);
		assertNotNull(workspaceInfo.getId());
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(user.getId());
		workspaceService.updateWorkspaceMembers(userIds, workspaceInfo.getId());
		
		List<UserInfo> userInfoList = workspaceService.findWorkspaceMembers(workspaceInfo.getId());
		UserInfo userInfo = userInfoList.get(0);
		
		List<WorkspaceInfo> workspaceList = workspaceService.findWorkspacesByDomain(course.getId());
		assertNotNull(workspaceList);
		assertEquals(1,workspaceList.size());
		assertEquals(workspaceInfo.getId(),workspaceList.get(0).getId());
		assertEquals(workspaceInfo.getDomainId(),workspaceList.get(0).getDomainId());
		assertEquals(workspaceInfo.getName(),workspaceList.get(0).getName());
		
		List<WorkspaceInfo> workspaceList2 = workspaceService.findWorkspacesByDomainAndUser(course.getId(), userInfo);
		assertNotNull(workspaceList2);
		
		workspaceList.get(0).setName("name2");
		workspaceService.updateWorkspace(workspaceList.get(0));
		WorkspaceInfo workspaceInfo2 = workspaceService.getWorkspace(workspaceList.get(0).getId());
		assertNotNull(workspaceInfo2);
		assertEquals(workspaceInfo2.getId(), workspaceList.get(0).getId());
		assertEquals(workspaceInfo2.getDomainId(), workspaceList.get(0).getDomainId());
		assertEquals(workspaceInfo2.getName(), workspaceList.get(0).getName());
	}
	
	private Course createCourseDomain() {
		Course course = testUtility.createUniqueCourseInDB();
		// Create default Group for Course
		Group participantsGroup = getSecurityService().createGroup("COURSE_" + course.getId() + "_PARTICIPANTS",
				"autogroup_participant_label", null, GroupType.PARTICIPANT);
		participantsGroup.addMember(user);
		Set<Group> groups = course.getGroups();
		if (groups == null) {
			groups = new HashSet<Group>();
		}
		groups.add(participantsGroup);
		course.setGroups(groups);
		getCourseDao().update(course);

		return course;
	}
	
	public void testRemoveUserFromWorkspaces(){
		Course course = createCourseDomain();
		
		WorkspaceInfo workspaceInfo = createWorkspaceInfo("name");
		workspaceInfo.setDomainId(course.getId());
		assertNull(workspaceInfo.getId());
		workspaceService.createWorkspace(workspaceInfo);
		assertNotNull(workspaceInfo.getId());
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(user.getId());
		workspaceService.updateWorkspaceMembers(userIds, workspaceInfo.getId());
		
		workspaceService.removeUserFromWorkspaces(user);
		
		assertEquals(0, workspaceService.findWorkspaceMembers(((WorkspaceInfo)(workspaceService.findWorkspacesByDomain(course.getId()).get(0))).getId()).size());
	}
	
	private DefaultDomainObject createDomainObject() {
		DefaultDomainObject defaultDomainObject = new DefaultDomainObject(TestUtility.unique());
		return defaultDomainObject;
	}
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public WorkspaceDao getWorkspaceDao() {
		return workspaceDao;
	}

	public void setWorkspaceDao(WorkspaceDao workspaceDao) {
		this.workspaceDao = workspaceDao;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

}