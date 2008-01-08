// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.registration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.Institute;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserImpl;
import org.openuss.security.UserInfo;


/**
 * @author Ingo Dueppe
 * @see org.openuss.registration.RegistrationService
 */
public class RegistrationServiceImpl extends org.openuss.registration.RegistrationServiceBase {

	private static final String INSTITUTE_ACTIVATION_COMMAND = "instituteActivationCommand";
	private static final Logger logger = Logger.getLogger(RegistrationServiceImpl.class);
	
	@Override
	protected void handleRegistrateUser(UserInfo user) throws RegistrationException {
		Validate.notNull(user, "User parameter must not be null!");

		// ensure that user will not be activate
		user.setEnabled(false);

		getSecurityService().createUser(user);
		
		getSecurityService().saveUserPreferences(user);
		getSecurityService().saveUserContact(user);
		getSecurityService().saveUserProfile(user);
		
		// asign roles to user
		asignRolesToUser(getSecurityService().getUserObject(user));
	}

	@Override
	protected void handleActivateUserByCode(String code) throws RegistrationException {
		UserActivationCode activateCode = getUserActivationCodeDao().findByCode(code);
		if (activateCode == null) {
			logger.debug("Could not find registration code "+code);
			throw new RegistrationCodeNotFoundException("Could not find registration code "+code);
		}
		if (isExpired(activateCode)){
			logger.debug("Activation code expired!");
			throw new RegistrationCodeExpiredException("activation code expired: " + activateCode);
		}		
		// activate user
		activateCode.getUser().setEnabled(true);
		getSecurityService().saveUser(activateCode.getUser());
	}

	@Override
	protected Timestamp handleGetCreateTime(String activationCode) throws RegistrationCodeNotFoundException{
		UserActivationCode userActivationCode = getUserActivationCodeDao().findByCode(activationCode);
		if (userActivationCode==null) {
			logger.debug("Could not find activation code: " + activationCode);
			throw new RegistrationCodeNotFoundException("Could not find activation code: " + activationCode);
		}
		return userActivationCode.getCreatedAt();		
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
		getSecurityService().addAuthorityToGroup(user, Roles.USER);
		getSecurityService().saveUser(user);
	}

	@Override
	protected String handleGenerateActivationCode(UserInfo user) throws RegistrationException {
		UserActivationCode reg = UserActivationCode.Factory.newInstance();

		// generate new MD5 hash from user id and current time millis
		String code = md5("AC{"+user.getId()+"}"+System.currentTimeMillis())+user.getId();
		
		// store registration code
		reg.setUser(getSecurityService().getUserObject(user));
		reg.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		reg.setCode(code);		
		getUserActivationCodeDao().create(reg);
		
		return code;
	}

	@Override
	protected UserInfo handleLoginUserByActivationCode(String activationCode) throws Exception {
		UserActivationCode code = getUserActivationCodeDao().findByCode(activationCode);
		if (code==null){
			logger.debug("Could not find activation code: " + activationCode);
			throw new RegistrationCodeNotFoundException("Could not find activation code: " + activationCode);
		}

		if (isExpired(code)){
			logger.debug("Activation code expired!");
			throw new RegistrationCodeExpiredException("activation code expired!" + activationCode);
		}
		loginUser(code.getUser());
		return getSecurityService().getUser(code.getUser().getId());
		
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
			user = getSecurityService().getUserObject(getSecurityService().getUserByName(details.getUsername()));
			getSecurityService().setLoginTime(getSecurityService().getUser(user.getId()));			
		}
		return user;
	}

	private boolean isExpired(ActivationCode ac) {
		long createdAt = ac.getCreatedAt().getTime();
		long now = System.currentTimeMillis();
		return ((now-createdAt)/60000) > 30;
	}

	@Override
	protected void handleActivateInstituteByCode(String code) throws Exception {		
		
		InstituteActivationCode activateCode = getInstituteActivationCodeDao().findByActivationCode(code);
		if (activateCode == null) {
			logger.debug("Could not find registration code "+code);
			throw new RegistrationCodeNotFoundException("Could not find registration code "+code);
		}
		if (isExpired(activateCode)){
			logger.debug("Activation code expired!");
			throw new RegistrationCodeExpiredException("activation code expired: " + activateCode);
		}
		if (!activateCode.getInstitute().getDepartment().isEnabled()) {
			logger.debug("Activation not possible because super-ordinate department is disabled!");
			throw new RegistrationParentDepartmentDisabledException("Activation not possible because super-ordinate department is disabled!");
		}
		getCommandService().createOnceCommand(activateCode.getInstitute(), INSTITUTE_ACTIVATION_COMMAND, new Date(System.currentTimeMillis()), null);
	}

	@Override
	protected String handleGenerateInstituteActivationCode(Institute institute) throws Exception {
		InstituteActivationCode instituteActivationCode = InstituteActivationCode.Factory.newInstance();

		// generate new MD5 hash from user id and current time millis
		String code = md5("FA{"+institute.getId()+"}"+System.currentTimeMillis()+""+institute.getId());
		
		// store registration code
		instituteActivationCode.setInstitute(institute);
		instituteActivationCode.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		instituteActivationCode.setCode(code);		
		getInstituteActivationCodeDao().create(instituteActivationCode);
		
		return code;	
	}

	private String md5(String raw) throws RegistrationException {
		try {
		     MessageDigest md = MessageDigest.getInstance("MD5");
		     md.reset();
		     byte[] byteHash = md.digest(raw.getBytes());
		     StringBuffer result = new StringBuffer();
		     for(int i = 0; i < byteHash.length; i++) {
		    	 result.append(Integer.toHexString(0xFF & byteHash[i]));
		     }
		     return result.toString();
		} catch(NoSuchAlgorithmException e) {
		     logger.error("Error while MD5 encoding: ", e);	
		     throw new RegistrationException("Error while MD5 encoding code ");
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	protected void handleRemoveInstituteCodes(Institute institute) throws Exception {
		getInstituteActivationCodeDao().remove(getInstituteActivationCodeDao().findByInstitute(institute));
	}

}