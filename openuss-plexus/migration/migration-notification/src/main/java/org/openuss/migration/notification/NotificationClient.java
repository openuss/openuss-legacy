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
		EmailSender sender = (EmailSender) context.getBean("emailSender");
		EmailGeneratorPatch patch = (EmailGeneratorPatch) context.getBean("emailGeneratorPatch");

		if (args.length == 0) {
			String help = "Help for Notification Client \n\n" +
					"\t java -jar migration-notification.jar [uisp]\n" +
					"\n" +
					"\t\tu	-	Generate user notification emails in database" +
					"\t\ti	-	Generate institute notification emails in database" +
					"\t\ts	-	Send notification emails " +
					"\t\tp	-	Patch MD5 keys - issue of base64 coded keys";
			System.out.println(help);
		} else {
			if (args[0].contains("u")) {
				generator.generateUserNotificationEmails();
			}
			if (args[0].contains("i")) {
				generator.generateInstituteNotification();
			}
			if (args[0].contains("s")) {
				sender.sendNotifications();
			}
			if (args[0].contains("p")) {
				patch.patchCodes();
			}
		}

	}

	protected static String[] getConfigLocations() {
		return new String[] { 
			"classpath*:dataSource.xml", 
			"classpath*:applicationContext.xml" };
	}

}
