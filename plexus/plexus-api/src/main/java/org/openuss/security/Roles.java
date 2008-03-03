package org.openuss.security;



/**
 * Defines the global constants for the four major roles.
 * <ol>
 *    <li>ANONYMOUS</li>
 *    <li>USER</li>
 *    <li>ASSISTANT</li>
 *    <li>ADMINISTRATOR</li>
 * </ol>
 *  
 * @author Ingo Dueppe
 */
public final class Roles {

	//	public static final long ASSISTANT_ID = -3L;
	//	public static final Group ASSISTANT = Group.Factory.newInstance();
	
	public static final long ANONYMOUS_ID = -1L;
	public static final long USER_ID = -2L;
	public static final long ADMINISTRATOR_ID = -4L;
	public static final long LDAPUSER_ID = -6L;
	public static final String LDAPUSER_NAME = "LDAPUSER";
	
	public static final Group ANONYMOUS = Group.Factory.newInstance();
	public static final Group USER = Group.Factory.newInstance();
	public static final Group ADMINISTRATOR = Group.Factory.newInstance();
	public static final Group LDAPUSER = Group.Factory.newInstance();

	static {
		ANONYMOUS.setId(ANONYMOUS_ID);
		USER.setId(USER_ID);
		ADMINISTRATOR.setId(ADMINISTRATOR_ID);
		LDAPUSER.setId(LDAPUSER_ID);
		LDAPUSER.setName(LDAPUSER_NAME);
	}
	
}
