// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.collaboration;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.security.User;


/**
 * JUnit Test for Spring Hibernate WorkspaceDao class.
 * @see org.openuss.collaboration.WorkspaceDao
 */
public class WorkspaceDaoTest extends WorkspaceDaoTestBase {
	private TestUtility testUtility;
	
	public void testWorkspaceDao() {
		DefaultDomainObject domain = new DefaultDomainObject(TestUtility.unique());
		Workspace workspace = new WorkspaceImpl();
		workspace.setName("test_workspace");
		workspace.setDomainId(domain.getId());
		
		User user = getTestUtility().createUniqueUserInDB();
		Set<User> userList = new HashSet<User>();
		userList.add(user);
		
		workspace.setUser(userList);
		assertNull(workspace.getId());
		workspaceDao.create(workspace);
		assertNotNull(workspace.getId());
		Workspace workspace2 = workspaceDao.load(workspace.getId());
		assertNotNull(workspace2);
		workspace2.setName("test_workspace2");
		workspaceDao.update(workspace2);
		Collection<Workspace> workspaceList1 = workspaceDao.loadAll();
		assertNotNull(workspaceList1);
		assertEquals(1,workspaceList1.size());
		
		List<Workspace> workspaceList2 = workspaceDao.findByDomainId(domain.getId());
		assertNotNull(workspaceList2);
		assertEquals(1,workspaceList2.size());
		assertEquals("test_workspace2", workspaceList2.get(0).getName());
		
//		List<Workspace> workspaceList3 = workspaceDao.findByDomainIdAndUser(domain.getId(), user);
//		assertNotNull(workspaceList3);
//		assertEquals(1,workspaceList3.size());
		
		WorkspaceInfo workspaceInfo = workspaceDao.toWorkspaceInfo(workspace);
		assertNotNull(workspaceInfo);
		assertEquals(workspaceInfo.getId(), workspace.getId());
		assertEquals(workspaceInfo.getDomainId(), workspace.getDomainId());
		assertEquals(workspaceInfo.getName(), workspace.getName());
		
		Workspace workspace3 = new WorkspaceImpl();
		workspaceDao.workspaceInfoToEntity(workspaceInfo, workspace3, false);
		assertNotNull(workspace3);
		assertNotSame(workspaceInfo.getId(),workspace3.getId());
		
		workspaceDao.remove(workspace3);
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}
