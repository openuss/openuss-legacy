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
	
	// Java Mail Properties
	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	public static final String MAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
	public static final String MAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
	public static final String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";

	// Server Configuration
	public static final String OPENUSS_SERVER_URL = "openuss.server.url";

	public static final String COPYRIGHT = "openuss.copyright";
	
	/* URL to start authentication within a lazy session configuration.
	 * value of openuss server url followed by
	 * these information from shibboleth.xml: 
	 * value of handlerURL attribute (e. g. /Shibboleth.sso) within <Sessions> element followed by
	 * value of Location attribute (e. g. /WAYF/zivmiro01.uni-muenster.de) within <SessionInitiator> element followed by
	 * "?target=" (without quotes) for the value of the target url
	 * 
	 * example: https://www.openuss.de/Shibboleth.sso/WAYF/zivmiro01.uni-muenster.de?target=
	 * 
	 * According to that a complete url for initiating a user authentication looks like this 
	 * (value of the target parameter has to be the url, that the user wants to access and 
	 * can be taken from the http request object):
	 * 
	 * example: https://www.openuss.de/Shibboleth.sso/WAYF/zivmiro01.uni-muenster.de?target=https://www.openuss.de/views/secured/myuni/myuni.faces */
	public static final String SHIBBOLETH_START_AUTH_URL = "shibboleth.start.auth.url";
	
	// Support System Configuration
	public static final String DOCUMENTATION_URL = "documentation.url";

	public static final String SUPPORT_URL = "support.url";

	public static final String BUGTRACKING_URL ="bugtracking.url";
	
	// Impressum
	public static final String IMPRESSUM_TEXT = "impressum.text";

	public static final String PROVIDER_URL = "provider.url";
	
	public static final String GETTING_STARTED = "getting.started";
	// OpenUSS Server Id 
	/**
	 *  JVM Application Parameter to define the server id.<br/>
	 *  Add <code>-Dopenuss.instance.id</code> to the server jvm startup to define the server id.
	 **/
	public static final String OPENUSS_INSTANCE_ID ="openuss.instance.id";
		
}
