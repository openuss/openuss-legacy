package org.openuss.web.migration;

import java.util.Random;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserContact;

/**
 * Migrates a user 
 * @author Peter Schuh
 *
 */
@Bean(name="migrationUtility", scope=Scope.REQUEST)
@View
public class MigrationUtility extends BaseBean{

	@Property(value="#{centralUserData}")
	CentralUserData centralUserData;
	
	@Property(value="#{securityService}")
	static SecurityService securityService;

	public User migrate(User user, Authentication auth) {
		//Put authentication into SecurityContext, so that SecurityService can find it.
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(auth);
		// Generate random password, so that account is likely not to be used for login.
		Random random = new Random();		
		String password = String.valueOf(random.nextLong())+String.valueOf(random.nextLong());
		securityService.changePassword(password);
		user.setUsername(centralUserData.getUsername());
		user.setEmail(centralUserData.getEmail());
		UserContact userContact = user.getContact();
		userContact.setFirstName(centralUserData.getFirstName());
		userContact.setLastName(centralUserData.getLastName());
		securityService.saveUserContact(userContact);
		securityService.saveUser(user);
		return user;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public CentralUserData getCentralUserData() {
		return centralUserData;
	}

	public void setCentralUserData(CentralUserData centralUserData) {
		this.centralUserData = centralUserData;
	}
}
