package org.openuss.migration.from20to30;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class MigrationServiceTest extends AbstractDependencyInjectionSpringContextTests {

	private MigrationService migrationService;
	

	public void testMigration() {
		migrationService.migrateStudents();
	}
	
	
	public MigrationService getMigrationService() {
		return migrationService;
	}

	public void setMigrationService(MigrationService migrationService) {
		this.migrationService = migrationService;
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
}
