// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openuss.lecture.University;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate DesktopService2 class.
 * @see org.openuss.desktop.DesktopService2
 * @author Ron Haus
 */
public class DesktopService2IntegrationTest extends DesktopService2IntegrationTestBase {	

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DesktopService2IntegrationTest.class);
	
	private DesktopDao desktopDao;
	
	public DesktopDao getDesktopDao() {
		return desktopDao;
	}

	public void setDesktopDao(DesktopDao desktopDao) {
		this.desktopDao = desktopDao;
	}

	private Desktop createDesktop() {
		Desktop desktop = Desktop.Factory.newInstance();
		desktop.setUser(testUtility.createUserInDB());
		this.desktopDao.create(desktop);
		return desktop;
	}
	
	
	
	public void testFindDesktop() throws Exception {
		logger.info("----> BEGIN access to findDesktop test");
		
		//Create Desktop
		Desktop desktop = this.createDesktop();
		
		//Find Desktop
		DesktopInfo desktopInfo = this.getDesktopService2().findDesktop(desktop.getId());
		assertNotNull(desktopInfo);		
		assertEquals(desktop.getId(), desktopInfo.getId());		
		
		logger.info("----> END access to findDesktop test");
	}
	
	public void testFindDesktopByUser() throws Exception {
		logger.info("----> BEGIN access to findDesktopByUser test");
		
		//Create Desktop
		Desktop desktop = this.createDesktop();
		
		//Find Desktop
		DesktopInfo desktopInfo = this.getDesktopService2().findDesktopByUser(desktop.getUser().getId());
		assertNotNull(desktopInfo);		
		assertEquals(desktop.getId(), desktopInfo.getId());		
		
		logger.info("----> END access to findDesktopByUser test");
	}
	
	public void testCreateDesktop() throws Exception {
		logger.info("----> BEGIN access to createDesktop test");
		
		//Create User
		User user = testUtility.createUniqueUserInDB();
		
		//Create Desktop
		Long desktopId = this.getDesktopService2().createDesktop(user.getId());
		assertNotNull(desktopId);
		
		//Synchronize with Database
		flush();
		
		//Find just created Desktop
		Desktop desktop = this.getDesktopDao().load(desktopId);
		assertNotNull(desktop);		
		assertEquals(desktopId, desktop.getId());	
		
		logger.info("----> END access to createDesktop test");
	}
	
	@SuppressWarnings( { "unchecked" })
	public void testUpdateDesktop() throws Exception {
		logger.info("----> BEGIN access to updateDesktop test");
		
		//Create Desktop
		Desktop desktop = this.createDesktop();
		
		//Create DesktopInfo
		DesktopInfo desktopInfo = this.getDesktopDao().toDesktopInfo(desktop);
		University university = testUtility.createUniqueUniversityInDB();	
		desktopInfo.setUniversityIds(new ArrayList());
		desktopInfo.getUniversityIds().add(university.getId());
		
		//Update Desktop
		this.getDesktopService2().updateDesktop(desktopInfo);
		
		//Synchronize with Database
		flush();
		
		assertNotNull(desktop.getUniversities());
		assertTrue(desktop.getUniversities().contains(university));		
		
		logger.info("----> END access to updateDesktop test");
	}
}