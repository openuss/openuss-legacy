package org.openuss.system;

/**
 * Global System Property Ids 
 * @author Ingo Dueppe
 */
public interface SystemProperties {
	
	public static final String REPOSITORY_PATH = "repository.path";
	public static final Long REPOSITORY_PATH_ID = 1L;
	
	// E-Mail Configuration Parameter
	public static final String MAIL_FROM_ADDRESS = "mail.from.address";
	public static final String MAIL_FROM_NAME = "mail.from.name";
	public static final String MAIL_HOST_NAME = "mail.host.name";
	public static final String MAIL_HOST_PORT = "mail.host.port";
	public static final String MAIL_HOST_USR = "mail.host.user";
	public static final String MAIL_HOST_PWD = "mail.host.password";
	
	public static final Long MAIL_FROM_ADDRESS_ID = 2L;
	public static final Long MAIL_FROM_NAME_ID = 3L;
	public static final Long MAIL_HOST_PORT_ID = 4L;
	public static final Long MAIL_HOST_USR_ID = 5L;
	public static final Long MAIL_HOST_PWD_ID = 6L;

	// Server Configuration
	public static final String OPENUSS_SERVER_URL = "openuss.server.url";
	public static final Long OPENUSS_SERVER_URL_ID = 7L;

	public static final String COPYRIGHT = "openuss.copyright";
	public static final Long COPYRIGHT_ID = 8L;
	
	// Support System Configuration
	public static final String DOCUMENTATION_URL = "documentation.url";
	public static final Long DOCUMENTATION_URL_ID = 9L;

	public static final String SUPPORT_URL = "support.url";
	public static final Long SUPPORT_URL_ID = 10L;

	public static final String BUGTRACKING_URL ="bugtracking.url";
	public static final Long BUGTRACKING_URL_ID = 11L;
	
	// Impressum
	public static final String IMPRESSUM_TEXT = "impressum.text";
	public static final Long IMPRESSUM_TEXT_ID = 12L;

	public static final String PROVIDER_URL = "provider.url";
	public static final Long PROVIDER_URL_ID = 13L;
	
	public static final String GETTING_STARTED = "getting.started";
	public static final Long GETTING_STARTED_ID = 14L;
	// OpenUSS Server Id 
	/**
	 *  JVM Application Parameter to define the server id.<br/>
	 *  Add <code>-Dopenuss.instance.id</code> to the server jvm startup to define the server id.
	 **/
	public static final String OPENUSS_INSTANCE_ID ="openuss.instance.id";
		
}
