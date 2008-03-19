package org.openuss.framework.web.jsf.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;

import org.acegisecurity.Authentication;
import org.acegisecurity.acl.AclEntry;
import org.acegisecurity.acl.AclManager;
import org.acegisecurity.acl.basic.BasicAclEntry;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;

/**
 * Acegi ACL utility class
 * 
 * @author Ingo Dueppe
 */
public class AcegiUtils {

	private static final Logger logger = Logger.getLogger(AcegiUtils.class);

	/**
	 * Checks if the current user has the needed permission on the domain object
	 * @param domainObject
	 * @param requiredIntegers
	 * @return
	 */
	public static boolean hasPermission(Object domainObject, Integer[] requiredIntegers) {
		AclEntry[] acls = getAclEntries(domainObject);
		return checkPermission(requiredIntegers, acls);
	}

	/**
	 * Checks if one of the acls entries has the required permissions.
	 * 
	 * @param requiredIntegers
	 * @param acls
	 */
	private static boolean checkPermission(Integer[] requiredIntegers, AclEntry[] acls) {
		if ((acls != null) && acls.length > 0) {
			// Locate processable AclEntrys 
			for (AclEntry aclEntry : acls) {
				if (aclEntry instanceof BasicAclEntry) {
					BasicAclEntry processableAcl = (BasicAclEntry) aclEntry;
					for (Integer required : requiredIntegers) {
						if (processableAcl.isPermitted(required)) {
							if (logger.isDebugEnabled()) {
								logger.debug("Including tag body as found permission: " + Arrays.toString(requiredIntegers)	+ " due to AclEntry: '" + processableAcl + "'");
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Parse Integer objects
	 * @param integersString
	 * @return
	 * @throws NumberFormatException
	 */
	public static Integer[] parseIntegersString(String integersString) throws NumberFormatException {
		final Set<Integer> integers = new HashSet<Integer>();
		final StringTokenizer tokenizer;
		tokenizer = new StringTokenizer(integersString, ",", false);
	
		while (tokenizer.hasMoreTokens()) {
			String integer = tokenizer.nextToken();
			integers.add(new Integer(integer));
		}
	
		return (Integer[]) integers.toArray(new Integer[integers.size()]); 
	}

	/**
	 * Returns all acl entries of the current authority on the domain object
	 * @param domainObject
	 * @return
	 */
	public static AclEntry[] getAclEntries(Object domainObject) {
		AclManager aclManager = getAclManager();

		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AclEntry[] acls = aclManager.getAcls(domainObject, auth);
	
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication: '" + auth + "' has: " + ((acls == null) ? 0 : acls.length)
					+ " AclEntrys for domain object: '" + domainObject + "' from AclManager: ' "
					+ aclManager.toString() + "'");
		}
		return acls;
	}

	private volatile static AclManager _aclManager;
	
	public static AclManager getAclManager() {
		// FIXME remove dependency to java server faces
		if (_aclManager == null) {
			synchronized (AcegiUtils.class) {
				FacesContext facesContext = FacesUtils.getFacesContext();
				_aclManager = (AclManager) facesContext.getApplication().createValueBinding("#{aclManager}").getValue(facesContext);
			}
		}	
		return _aclManager;
	}
	
	public static void setAclManager(AclManager aclManager) {
		_aclManager = aclManager;
	}
	
}
