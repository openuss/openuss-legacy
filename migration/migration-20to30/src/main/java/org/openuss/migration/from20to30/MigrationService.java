package org.openuss.migration.from20to30;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openuss.migration.legacy.dao.LegacyDao;
import org.openuss.migration.legacy.domain.Student2;
import org.openuss.migration.legacy.domain.Studentinformation2;
import org.openuss.registration.RegistrationService;
import org.openuss.security.SecurityService;
import org.openuss.security.User;

/**
 * This Service migrate data from openuss 2.0 to openuss-plexus 3.0
 *  
 * @author Ingo Dueppe
 *
 */
public class MigrationService {

	private static final Logger logger = Logger.getLogger(MigrationService.class);
	
	/**
	 * Maps students legacy id to to new user objects
	 */
	private Map<String, User> id2users = new HashMap<String, User>();
	private Map<String, User> email2users = new HashMap<String, User>();
	
	private LegacyDao legacyDao;
	
	private SecurityService securityService;
	private RegistrationService registrationService;
	
	public void migrateStudents() {
		Collection<Student2> students2 = legacyDao.loadAllStudents();
		
		for (Student2 student2 : students2) {
			String email = student2.getEmailaddress();
			if (email2users.containsKey(email)) {
				logger.debug("email already in use "+email);
				User user = email2users.get(email);
				id2users.put(student2.getId(), user);
			} else {
				User user = transformStudent2User(student2);
				email2users.put(email, user);
				id2users.put(student2.getId(), user);
			}
		}
	}
	
	@SuppressWarnings({"deprecation" })
	private User transformStudent2User(Student2 student) {
		logger.trace("create "+student.getLastname()+"(" +student.getEmailaddress()+")");
		Studentinformation2 info = student.getStudentinformations().iterator().next();
		
		// User
		User user = User.Factory.newInstance();
		user.setUsername(student.getUusername());
		user.setPassword(student.getPpassword());
		user.setEmail(student.getEmailaddress());
		user.setEnabled(false);

		// User Contact
		user.getContact().setFirstName(student.getFirstname());
		user.getContact().setLastName(student.getLastname());
		user.getContact().setAddress(student.getAddress());
		user.getContact().setCity(student.getCity());
		user.getContact().setPostcode(student.getPostcode());
		user.getContact().setCountry(student.getLand());
		user.getContact().setSmsEmail(student.getEmailsmsgatewayaddress());
		user.getContact().setTelephone(student.getTelephone());
		
		// User Preferences
		user.getPreferences().setLocale(student.getLocale());
		
		// User Profile
		user.getProfile().setAgeGroup(student.getYyear());
		user.getProfile().setMatriculation(student.getPersonalid());
		user.getProfile().setStudies(student.getStudies());
		user.getProfile().setPortrait(info.getTtext());
		
		user.getProfile().setAddressPublic(Util.toBoolean(info.getAddress()));
		user.getProfile().setTelephonePublic(Util.toBoolean(info.getTelephone()));
		user.getProfile().setImagePublic(Util.toBoolean(info.getImage()));
		user.getProfile().setEmailPublic(Util.toBoolean(info.getEmail()));
		user.getProfile().setPortraitPublic(Util.toBoolean(info.getDescription()));
		user.getProfile().setProfilePublic(Util.toBoolean(info.getIspublic()));
		
		return user;
	}
	

	public LegacyDao getLegacyDao() {
		return legacyDao;
	}

	public void setLegacyDao(LegacyDao legacyDao) {
		this.legacyDao = legacyDao;
	}


	public SecurityService getSecurityService() {
		return securityService;
	}


	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}


	public RegistrationService getRegistrationService() {
		return registrationService;
	}


	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	

}
