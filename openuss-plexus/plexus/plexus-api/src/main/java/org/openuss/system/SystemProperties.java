package org.openuss.system;

public interface SystemProperties {
	
	public static final String REPOSITORY_PATH = "repository.path";
	
	// E-MAIL Configuration Parameter
	public static final String MAIL_FROM_ADDRESS = "mail.from.address";
	public static final String MAIL_FROM_NAME = "mail.from.name";
	public static final String MAIL_HOST_NAME = "mail.host.name";
	public static final String MAIL_HOST_PORT = "mail.host.port";
	public static final String MAIL_HOST_USR = "mail.host.user";
	public static final String MAIL_HOST_PWD = "mail.host.password";
	
	// SERVER Configuration
	public static final String OPENUSS_SERVER_URL = "openuss.server.url";
	public static final String COPYRIGHT = "openuss.copyright";
	
	// COMMUNITY Configuration
	public static final String COMMUNITY_COURSE_ID = "community.course.id";
	
	// Support System Configuration
	public static final String DOCUMENTATION_URL = "documentation.url";
	public static final String SUPPORT_URL = "support.url";
	public static final String BUGTRACKING_URL ="bugtracking.url";
	
	// Impressum
	public static final String IMPRESSUM_TEXT = "impressum.text";
	public static final String PROVIDER_URL = "provider.url";
		
}
