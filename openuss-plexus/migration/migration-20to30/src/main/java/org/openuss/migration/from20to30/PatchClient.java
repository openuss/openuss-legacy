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
public class PatchClient {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(PatchClient.class);

	public static void main(String[] args) {
		logger.info("initializing legacy registration");
		ApplicationContext context = new ClassPathXmlApplicationContext(getConfigLocations());
		
		PatchCoursePermission patchCourse = (PatchCoursePermission) context.getBean("patchCourse");
		patchCourse.patch();
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
			"classpath*:migrationSecurity.xml",
			"classpath*:patch.xml"};
	}	
	

}
