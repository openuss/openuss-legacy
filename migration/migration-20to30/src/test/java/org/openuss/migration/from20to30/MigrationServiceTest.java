package org.openuss.migration.from20to30;

import org.springframework.test.AbstractTransactionalSpringContextTests;

public class MigrationServiceTest extends AbstractTransactionalSpringContextTests{

	private UserImport userImport;
	
	private LectureImport lectureImport;
	
	private DesktopImport desktopImport;
	
	public void testMigration() {
//		userImport.importUsers();
//		lectureImport.importLecture();
//		desktopImport.importDesktops();
//		
//		setComplete();
//		endTransaction();
//		startNewTransaction();
	}
	
	
	public UserImport getUserImport() {
		return userImport;
	}

	public void setUserImport(UserImport migrationService) {
		this.userImport = migrationService;
	}

	protected String[] getConfigLocations() {
		return new String[] {
			"classpath*:applicationContext.xml",
			"classpath*:applicationContext-activation.xml",
			"classpath*:applicationContext-cache.xml",
			"classpath*:applicationContext-entities.xml",
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-legacy.xml",
			"classpath*:applicationContext-adapters.xml",
//			"classpath*:applicationContext-commands.xml",
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-newsletter.xml",
//			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-resources.xml",
			"classpath*:applicationContext-aop.xml",
			"classpath*:applicationContext-migration.xml",
			"classpath*:testSecurity.xml"};
	}


	public LectureImport getLectureImport() {
		return lectureImport;
	}


	public void setLectureImport(LectureImport lectureImport) {
		this.lectureImport = lectureImport;
	}


	public DesktopImport getDesktopImport() {
		return desktopImport;
	}


	public void setDesktopImport(DesktopImport desktopImport) {
		this.desktopImport = desktopImport;
	}
}
