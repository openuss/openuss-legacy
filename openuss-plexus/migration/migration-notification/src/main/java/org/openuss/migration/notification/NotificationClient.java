package org.openuss.migration.notification;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Client to notify users and institutes owners about the migration.
 *  
 * @author Ingo Dueppe
 *
 */
public class NotificationClient {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(NotificationClient.class);

	public static void main(String[] args) {
		logger.info("initializing legacy registration");
		
		ApplicationContext context = new ClassPathXmlApplicationContext(getConfigLocations());
		EmailGenerator generator = (EmailGenerator) context.getBean("emailGenerator");
//		generator.generateUserNotificationEmails();
		generator.generateInstituteNotification();
//		if (args.length == 0) {
//			System.out.println("\n Parameters for Notification Client");
//		} else if ("generate_emails".equals(args[0])) {
//			
//		}
	
	}
	
	protected static String[] getConfigLocations() {
		return new String[] {
			"classpath*:datasource.xml",
			"classpath*:applicationContext.xml"
		};
	}	
	

}
