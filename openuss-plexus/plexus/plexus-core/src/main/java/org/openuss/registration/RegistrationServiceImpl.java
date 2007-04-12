// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.registration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;
import org.openuss.registration.ActivationCode;
import org.openuss.registration.RegistrationCodeExpiredException;
import org.openuss.registration.RegistrationCodeNotFoundException;
import org.openuss.registration.RegistrationException;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserImpl;


/**
 * @author Ingo Dueppe
 * @see org.openuss.registration.RegistrationService
 */
public class RegistrationServiceImpl extends org.openuss.registration.RegistrationServiceBase {

	private static final Logger logger = Logger.getLogger(RegistrationServiceImpl.class);
	
	@Override
	protected void handleRegistrateUser(User user, boolean assistant) throws RegistrationException {
		// check arguments
		if (user == null)
			throw new IllegalArgumentException("User parameter must not be null!");
		if (user.getPreferences() == null)
			throw new IllegalArgumentException("Associated UserPerferences must not be null!");
		if (user.getContact() == null)
			throw new IllegalArgumentException("Associated Person must not be null!");
		// ensure that user will not be activate
		user.setEnabled(false);

		getSecurityService().createUser(user);
		
		// asign roles to user
		asignRolesToUser(user);
	}

	@Override
	protected void handleActivateUserByCode(String code) throws RegistrationException {
		ActivationCode activateCode = getActivationCodeDao().findByCode(code);
		if (activateCode == null) {
			logger.debug("Could not find registration code "+code);
			throw new RegistrationCodeNotFoundException("Could not find registration code "+code);
		}
		// activate user
		activateCode.getUser().setEnabled(true);
		getSecurityService().saveUser(activateCode.getUser());
		// remove regCode
		getActivationCodeDao().remove(activateCode);
	}

	@Override
	protected Timestamp handleGetCreateTime(String activationCode) throws RegistrationCodeNotFoundException{
		ActivationCode aC = getActivationCodeDao().findByCode(activationCode);
		if (aC==null) {
			logger.debug("Could not find activation code: " + activationCode);
			throw new RegistrationCodeNotFoundException("Could not find activation code: " + activationCode);
		}
		return aC.getCreatedAt();		
	}
	
	/**
	 * Convenience method to asign <code>user</code> and
	 * <code>assistant</code> roles to a user.
	 * 
	 * @param user
	 * @param assistant
	 *            wether or not to asign the assistant role to the user
	 */
	private void asignRolesToUser(User user) {
		// assign roles to user
		final SecurityService securityService = getSecurityService();
		
		securityService.addAuthorityToGroup(user, Roles.USER);
		securityService.saveUser(user);
	}

	@Override
	protected String handleGenerateActivationCode(User user) throws RegistrationException {
		ActivationCode reg = ActivationCode.Factory.newInstance();

		// generate new MD5 hash from user id and current time millis

		String input = user.getId()+""+System.currentTimeMillis();
		MessageDigest md;
		byte[] byteHash;
		StringBuffer resultString = new StringBuffer();
		try {
		     md = MessageDigest.getInstance("MD5");
		     md.reset();
		     md.update(input.getBytes());
		     byteHash = md.digest();
		     for(int i = 0; i < byteHash.length; i++) {
		    	 resultString.append(Integer.toHexString(0xFF & byteHash[i]));
		     }
		} catch(NoSuchAlgorithmException e) {
		     logger.error("Error while MD5 encoding: ", e);	
		     throw new RegistrationException("Error while MD5 encoding code ");
		}
		
		String code = "AC"+resultString.toString();
		
		// store registration code
		reg.setUser(user);
		reg.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		reg.setCode(code);		
		getActivationCodeDao().create(reg);
		
		return code;
	}

	@Override
	protected User handleLoginUserByActivationCode(String activationCode) throws Exception {
		ActivationCode code = getActivationCodeDao().findByCode(activationCode);
		if (code==null){
			logger.debug("Could not find activation code: " + activationCode);
			throw new RegistrationCodeNotFoundException("Could not find activation code: " + activationCode);
		}

		if (isExpired(code)){
			logger.debug("Activation code expired!");
			throw new RegistrationCodeExpiredException("activation code expired!" + activationCode);
		}
		loginUser(code.getUser());
		getActivationCodeDao().remove(code);
		return code.getUser();
		
	}

	private User loginUser(User user) {
		UserImpl u = (UserImpl) user;
		
		// TODO check if user account is not locked or expired
		final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), "[Protected]", u.getAuthorities());
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authRequest);
		
		if (authRequest.getPrincipal() instanceof User) {
		
			logger.debug("Principal is: "+authRequest.getPrincipal());
			User details = (User) authRequest.getPrincipal();
			user = getSecurityService().getUserByName(details.getUsername());
			getSecurityService().setLoginTime(user);			
		}
		return user;
	}

	private boolean isExpired(ActivationCode ac) {
		long createdAt = ac.getCreatedAt().getTime();
		long now = System.currentTimeMillis();
		return ((now-createdAt)/60000) > 15;
	}

}