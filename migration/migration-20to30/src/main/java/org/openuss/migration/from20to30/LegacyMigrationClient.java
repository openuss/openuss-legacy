package org.openuss.migration.from20to30;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Client to migrate OpenUSS 2.0 Database to OpenUSS 3.0 
 *  
 * @author Ingo Dueppe
 *
 */
public class LegacyMigrationClient {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(LegacyMigrationClient.class);

	public static void main(String[] args) {
		logger.info("initializing legacy registration");
		ApplicationContext context = new ClassPathXmlApplicationContext(getConfigLocations());
		
		MigrationService ms = (MigrationService) context.getBean("migrationService");
		ms.importData();
	}
	
	protected static String[] getConfigLocations() {
		return new String[] {
			"classpath*:applicationContext.xml",
//			"classpath*:applicationContext-activation.xml",
//			"classpath*:applicationContext-cache.xml",
			"classpath*:applicationContext-entities.xml",
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-legacy.xml",
//			"classpath*:applicationContext-adapters.xml",
//			"classpath*:applicationContext-commands.xml",
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-newsletter.xml",
//			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-resources.xml",
//			"classpath*:applicationContext-aop.xml",
			"classpath*:applicationContext-migration.xml",
			"classpath*:migrationSecurity.xml"};
	}	
	

}
