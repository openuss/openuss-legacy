package org.openuss.framework.web.jsf.util;

import org.acegisecurity.Authentication;
import org.acegisecurity.acl.AclEntry;
import org.acegisecurity.acl.AclManager;

public class MockAclManager implements AclManager {

	public AclEntry[] getAcls(Object domainInstance) {
		return null;
	}

	public AclEntry[] getAcls(Object domainInstance, Authentication authentication) {
		return null;
	}

}
